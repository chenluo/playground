package com.chenluo.base.impl;

import com.chenluo.base.spec.BaseService;

public class BaseServiceImpl implements BaseService {
    @Override
    public String init() {
        return "inited from" + BaseServiceImpl.class.getCanonicalName();
    }
}
