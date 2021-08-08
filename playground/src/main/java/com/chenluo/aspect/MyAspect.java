package com.chenluo.aspect;

import com.chenluo.annotation.MyAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Pointcut(value = "@annotation(myAnnotation)")
    public void myAnnotationMethod(MyAnnotation myAnnotation) {
    }

    @AfterReturning(value = "myAnnotationMethod(myAnnotation)", returning = "result", argNames = "myAnnotation,result")
    public void afterReturning(MyAnnotation myAnnotation, Object result) {
        logger.info("afterReturning");
    }

    @AfterThrowing(value = "myAnnotationMethod(myAnnotation)", throwing = "e", argNames = "myAnnotation,e")
    public void afterThrowing(MyAnnotation myAnnotation, Throwable e) {
        logger.info("afterThrowing");
    }

    @After(value = "myAnnotationMethod(myAnnotation)", argNames = "myAnnotation")
    public void after(MyAnnotation myAnnotation) {
        logger.info("after");
    }

    @Before(value = "myAnnotationMethod(myAnnotation)", argNames = "myAnnotation")
    public void before(MyAnnotation myAnnotation) {
        logger.info("before");
    }

    @Around(value = "myAnnotationMethod(myAnnotation)", argNames = "joinPoint,myAnnotation")
    public Object around(ProceedingJoinPoint joinPoint, MyAnnotation myAnnotation) throws Throwable {
        Object o = joinPoint.proceed();
        return o;
    }
}
