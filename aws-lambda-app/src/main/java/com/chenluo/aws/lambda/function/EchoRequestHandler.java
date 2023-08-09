package com.chenluo.aws.lambda.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EchoRequestHandler implements RequestHandler<EchoRequest, EchoResponse> {
    @Override
    public EchoResponse handleRequest(EchoRequest input, Context context) {
        return new EchoResponse(input.msg());
    }
}
