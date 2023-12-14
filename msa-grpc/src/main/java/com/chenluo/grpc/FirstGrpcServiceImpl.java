package com.chenluo.grpc;

import io.grpc.stub.StreamObserver;

public class FirstGrpcServiceImpl extends FirstGrpcServiceGrpc.FirstGrpcServiceImplBase {

    @Override
    public void api1(Req request, StreamObserver<Res> responseObserver) {
        System.out.println("response");
        System.out.println(request);
        Res res = Res.newBuilder().setId(request.getId()).setReq(request.getReq()).setRes("res:" + request.getReq()).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void api2(Req request, StreamObserver<Res> responseObserver) {
        for (int i = 0; i < 10; i++) {
            responseObserver.onNext(Res.newBuilder().setId(request.getId()).setReq(request.getReq()).setRes(i + ":" + request.getReq()).build());
        }
    }

    @Override
    public StreamObserver<Req> api4(StreamObserver<Res> responseObserver) {
        return new StreamObserver<Req>() {
            @Override
            public void onNext(Req value) {
                responseObserver.onNext(Res.newBuilder().setReq(value.getReq()).setRes(value.getReq() + " res").setId(value.getId()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };
    }

}
