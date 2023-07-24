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
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
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

    @Bean
    @StepScope
    public ItemReader reader(@Value("#{jobParameters}") Map<String, String> jobParameters) {
        logger.info("job parameters: {}", jobParameters);
        Iterable<ConsumedMessage> all = repository.findAll();
        IteratorItemReader<ConsumedMessage> consumedMessageIteratorItemReader =
                new IteratorItemReader<ConsumedMessage>(all);
        logger.info("start");
        all.forEach(m -> logger.info("{} sdf{}", Thread.currentThread().getName(), m.id));
        logger.info("end");
        return consumedMessageIteratorItemReader;
    }

    @Bean
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
    @JobScope
    public Step step1(@Value("#{jobParameters['chunkSize']}") String chunkSize) {
        return new StepBuilder("test_step_1", jobRepository).chunk(Integer.parseInt(chunkSize),
                        transactionManager).allowStartIfComplete(true).reader(reader(null))
                .processor(processor()).writer(writer()).taskExecutor(taskExecutor()).build();
    }

    @Bean
    public Job jobA() {
        return new JobBuilder("job", jobRepository).incrementer(new RunIdIncrementer())
                .flow(step1(null)).end().build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
        ConcurrentTaskExecutor concurrentTaskExecutor =
                new ConcurrentTaskExecutor(Executors.newFixedThreadPool(16));
        return concurrentTaskExecutor;
    }
}
