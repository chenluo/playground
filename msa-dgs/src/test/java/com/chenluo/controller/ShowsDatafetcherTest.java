package com.chenluo.controller;

import com.chenluo.model.DisputeType;
import com.chenluo.model.ShowsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.dgs.codgen.generated.client.Shows2GraphQLQuery;
import com.netflix.dgs.codgen.generated.client.Shows2ProjectionRoot;
import com.netflix.dgs.codgen.generated.client.ShowsGraphQLQuery;
import com.netflix.dgs.codgen.generated.client.ShowsProjectionRoot;
import com.netflix.dgs.codgen.generated.types.Req;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import com.netflix.graphql.dgs.client.codegen.InputValueSerializer;

import graphql.scalars.ExtendedScalars;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

class ShowsDatafetcherTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void call() throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());

        WebClientGraphQLClient client =
                MonoGraphQLClient.createWithWebClient(
                        WebClient.create("http://127.0.0.1:8080/graphql/"));

        ShowsGraphQLQuery req =
                ShowsGraphQLQuery.newRequest()
                        .request(
                                objectMapper.readTree(
                                        objectMapper.writeValueAsString(
                                                new ShowsRequest(
                                                        "1",
                                                        new BigDecimal(2),
                                                        DisputeType.CREATE,
                                                        Instant.now()))))
                        .build();
        ShowsProjectionRoot res = new ShowsProjectionRoot().number();
        Mono<GraphQLResponse> result =
                client.reactiveExecuteQuery(new GraphQLQueryRequest(req, res).serialize());
        GraphQLResponse response = result.block();
        System.out.println(response);
        System.out.println("finished");
    }

    @Test
    public void call2() throws JsonProcessingException {

        WebClientGraphQLClient client =
                MonoGraphQLClient.createWithWebClient(
                        WebClient.create("http://127.0.0.1:8080/graphql/"));

        Shows2GraphQLQuery req =
                Shows2GraphQLQuery.newRequest()
                        .request(Req.newBuilder().id("1").amount(new BigDecimal(2)).build())
                        .build();
        Shows2ProjectionRoot res = new Shows2ProjectionRoot().number();
        Mono<GraphQLResponse> result =
                client.reactiveExecuteQuery(
                        new GraphQLQueryRequest(
                                        req,
                                        res,
                                        Map.of(
                                                BigDecimal.class,
                                                ExtendedScalars.GraphQLBigDecimal.getCoercing()))
                                .serialize());
        GraphQLResponse response = result.block();
        System.out.println(response);
        System.out.println("finished");
    }

    @Test
    public void testManualSerialize() {
        InputValueSerializer serializer =
                new InputValueSerializer(
                        Map.of(BigDecimal.class, ExtendedScalars.GraphQLBigDecimal.getCoercing()));
        System.out.println(
                serializer.serialize(
                        new ShowsRequest(
                                "1", new BigDecimal(2), DisputeType.CREATE, Instant.now())));
    }
}
