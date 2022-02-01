/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyComplexServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(MyComplexServiceImpl.class);

    public void call() {
        logger.warn("called");
    }
}
