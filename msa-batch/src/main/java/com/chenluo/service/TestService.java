package com.chenluo.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TestService {
    public void test() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();

        ScanResult scan = amazonDynamoDB.scan(new ScanRequest());
        for (Map<String, AttributeValue> item : scan.getItems()) {
            item.get("s").getN();
        }
    }
}
