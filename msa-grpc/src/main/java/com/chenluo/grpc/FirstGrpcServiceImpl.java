package com.chenluo.grpc;

import io.grpc.stub.StreamObserver;

public class FirstGrpcServiceImpl extends FirstGrpcServiceGrpc.FirstGrpcServiceImplBase {

    @Override
    public void api2(Req request, StreamObserver<Res> responseObserver) {
        System.out.println("response");
        System.out.println(request);
        Res res = Res.newBuilder().setId(request.getId()).setReq(request.getReq()).setRes("res:" + request.getReq()).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
