package com.chenluo.serializer;

import com.chenluo.model.inheitance.json.Child1;
import com.chenluo.model.inheitance.json.Child2;
import com.chenluo.model.inheitance.json.ObjectContainer;
import com.chenluo.model.inheitance.json.Parent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.util.ArrayList;
import java.util.List;

public class InheritSerializer {
    private static final ObjectMapper typedMapper = new ObjectMapper();
    private static final ObjectMapper rawMapper = new ObjectMapper();

    static {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.chenluo.model.")
                .allowIfSubType("java.util.ArrayList")
                .build();
        typedMapper.activateDefaultTyping(ptv);
    }

    public static void main(String[] args) throws JsonProcessingException {
        InheritSerializer sut = new InheritSerializer();
        sut.test();

    }

    public void test() throws JsonProcessingException {
        List<Parent> list = new ArrayList<>();
        list.add(new Child1("parent", "child1"));
        list.add(new Child2("parent", "child2"));
        ObjectContainer toSerialized = new ObjectContainer(list);

        String typedJsonString = typedMapper.writeValueAsString(toSerialized);
        System.out.println(typedJsonString);
        typedMapper.readValue(typedJsonString, ObjectContainer.class);

        String rawJsonString = rawMapper.writeValueAsString(toSerialized);
        System.out.println(rawJsonString);
        rawMapper.readValue(rawJsonString, ObjectContainer.class);
    }

}
