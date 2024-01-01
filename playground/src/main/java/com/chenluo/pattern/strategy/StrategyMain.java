package com.chenluo.pattern.strategy;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class StrategyMain {
    private final Map<String, Strategy> strategyMap;
    private final Logger logger = LoggerFactory.getLogger(StrategyMain.class);

    @Autowired
    public StrategyMain(Map<String, Strategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.scan("com.chenluo.pattern");
        //        annotationConfigApplicationContext.register(PatterConfig.class);
        annotationConfigApplicationContext.refresh();
        Object strategy = annotationConfigApplicationContext.getBean("normalStrategy");
        Map<String, Strategy> map = Maps.newHashMap();
        map.put(strategy.getClass().getTypeName(), (Strategy) strategy);

        new StrategyMain(map).run();
    }

    public void run() {
        logger.info("{}", strategyMap);
    }
}
