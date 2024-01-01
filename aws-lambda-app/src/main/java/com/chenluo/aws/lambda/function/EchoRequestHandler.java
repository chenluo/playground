package com.chenluo.aws.lambda.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

// # From other terminal use cURL to send a request.
// curl -XPOST "http://localhost:8080/2015-03-31/functions/function/invocations" -d
// '{"msg":"content"}'
public class EchoRequestHandler implements RequestHandler<EchoRequest, EchoResponse> {
    @Override
    public EchoResponse handleRequest(EchoRequest input, Context context) {
        System.out.println("entered: %s".formatted(input.msg()));
        return new EchoResponse(input.msg());
    }
}
