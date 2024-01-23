package com.chenluo.controller;

import com.chenluo.model.ShowsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.dgs.codgen.generated.types.Req;
import com.netflix.dgs.codgen.generated.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ShowsDatafetcher {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Show> shows =
            List.of(
                    new Show("Stranger Things", 2016, List.of(), new BigDecimal(1)),
                    new Show("Ozark", 2017, List.of(), new BigDecimal(1)),
                    new Show("The Crown", 2016, List.of(), new BigDecimal(1)),
                    new Show("Dead to Me", 2019, List.of(), new BigDecimal(1)),
                    new Show("Orange is the New Black", 2013, List.of(), new BigDecimal(1)));

    @DgsQuery
    public List<Show> shows(@InputArgument Object request) throws JsonProcessingException {
        if (request == null) {
            return shows;
        }
        ShowsRequest showsRequest =
                objectMapper.readValue(
                        objectMapper.writeValueAsString(request), ShowsRequest.class);
        return shows.stream()
                .filter(s -> s.getTitle().contains(showsRequest.id))
                .collect(Collectors.toList());
    }

    @DgsQuery
    public List<Show> shows2(@InputArgument Req request) throws JsonProcessingException {
        if (request == null) {
            return shows;
        }
        return shows.stream()
                .filter(s -> s.getTitle().contains(request.getId()))
                .collect(Collectors.toList());
    }
}
