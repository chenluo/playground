package com.chenluo.pattern.strategy;

import org.springframework.stereotype.Component;

@Component
public class NormalStrategy implements Strategy {

    @Override
    public String doExecute() {
        return this.getClass().getName();
    }
}
