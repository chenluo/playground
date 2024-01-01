package com.chenluo;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

public class AnnotationTest {

    @Test
    public void testAnnotation() {
        for (Method method : Child.class.getMethods()) {
            if (method.isAnnotationPresent(Transactional.class)) {
                System.out.println("found in class");
            }
        }

        for (Class<?> interfaces : Child.class.getInterfaces()) {
            for (Method method : interfaces.getMethods()) {
                if (method.isAnnotationPresent(Transactional.class)) {
                    System.out.println("found in interface");
                }
            }
        }

        for (Class<?> interfaces : Child.class.getInterfaces()) {
            for (Class<?> anInterface : interfaces.getInterfaces()) {
                for (Method method : anInterface.getMethods()) {
                    if (method.isAnnotationPresent(Transactional.class)) {
                        System.out.println("found in grand interface");
                    }
                }
            }
        }
    }

    interface GrandParent {
        @Transactional
        public void foo();
    }

    interface Parent extends GrandParent {
        public void foo();
    }

    class Child implements Parent {

        @Override
        public void foo() {}
    }
}
