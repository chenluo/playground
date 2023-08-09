package com.chenluo.config;

import com.chenluo.data.dto.ConsumedMessage;
import com.chenluo.data.repo.ConsumedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


@Configuration
public class BatchConfig {
    Logger logger = LoggerFactory.getLogger(BatchConfig.class);
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConsumedMessageRepository repository;
    @Autowired
    private Environment environment;

    @Bean
    @StepScope
    public ItemReader reader(@Value("#{jobParameters}") Map<String, String> jobParameters,
                             @Value("#{stepExecutionContext['mod']}") String mod,
                             @Value("#{stepExecutionContext['idx']}") String idx) {
        logger.info("job parameters: {}", jobParameters);
        Iterable<ConsumedMessage> all =
                repository.findConsumedMessagesByIdModN(Integer.parseInt(mod),
                        Integer.parseInt(idx));
        IteratorItemReader<ConsumedMessage> consumedMessageIteratorItemReader =
                new IteratorItemReader<ConsumedMessage>(all);
        logger.info("start");
        all.forEach(m -> logger.info("{} sdf{}", Thread.currentThread().getName(), m.id));
        logger.info("end");
        return consumedMessageIteratorItemReader;
    }

    @Bean
    @StepScope
    public ItemWriter writer() {
        return new ListItemWriter<ConsumedMessage>();
    }

    @Bean
    public ItemProcessor processor() {
        return new ItemProcessor<ConsumedMessage, ConsumedMessage>() {
            @Override
            public ConsumedMessage process(ConsumedMessage item) throws Exception {
                logger.info("{}: msg: {}", Thread.currentThread().getName(), item.id);
                return item;
            }
        };
    }

    @Bean
    //    public Step step1(@Value("#{jobParameters['chunkSize']}") String chunkSize) {
    public Step step1() throws Exception {
        return new StepBuilder("test_step_1", jobRepository).chunk(1, transactionManager)
                .allowStartIfComplete(true).reader(reader(null, null, null)).processor(processor())
                .writer(writer()).build();
    }

    @Bean
    public Step partitionStep() throws Exception {
        return new StepBuilder("partitionStep", jobRepository).partitioner("partition_step",
                partitioner()).step(step1()).taskExecutor(taskExecutor(null)).build();
    }

    @Bean
    public Partitioner partitioner() {
        Partitioner partitioner = new Partitioner() {
            @Override
            public Map<String, ExecutionContext> partition(int gridSize) {
                logger.info("gridSize = {}", gridSize);
                Map<String, ExecutionContext> contextMap = new HashMap<>();
                for (int i = 0; i < gridSize; i++) {
                    ExecutionContext executionContext = new ExecutionContext();
                    executionContext.put("mod", gridSize);
                    executionContext.put("idx", i);
                    contextMap.put(String.valueOf(i), executionContext);
                }
                return contextMap;
            }
        };
        return partitioner;
    }

    @Bean("jobA")
    public Job jobA() throws Exception {
        return new JobBuilder("job", jobRepository).incrementer(new RunIdIncrementer())
                .start(partitionStep()).build();
    }

    @Bean
    @JobScope
    public TaskExecutor taskExecutor(@Value("#{jobParameters['concurrency']}") String concurrency) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
        String concurrency1 = environment.getProperty("concurrency");
        System.out.println(concurrency1);
        ConcurrentTaskExecutor concurrentTaskExecutor = new ConcurrentTaskExecutor(
                Executors.newFixedThreadPool(Integer.parseInt(concurrency)));
        return concurrentTaskExecutor;
    }
}
