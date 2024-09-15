package com.chenluo.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class MixinUserDo {
    @JsonIgnore
    int uid;
}
