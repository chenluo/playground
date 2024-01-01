package com.chenluo.grpc;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class FirstGrpcClient {
    public static void main(String[] args) {
        FirstGrpcClient client = new FirstGrpcClient();
        //        client.callApi1();
        //        client.callApi2();
        client.callApi4();
    }

    public void callApi1() {
        ManagedChannel channel =
                Grpc.newChannelBuilderForAddress(
                                "localhost", 18081, InsecureChannelCredentials.create())
                        .build();
        FirstGrpcServiceGrpc.FirstGrpcServiceBlockingStub blockingStub =
                FirstGrpcServiceGrpc.newBlockingStub(channel);
        Res res = blockingStub.api1(Req.newBuilder().setId(1).setReq("req1").build());
        System.out.println(res);
    }

    public void callApi2() {
        ManagedChannel channel =
                Grpc.newChannelBuilderForAddress(
                                "localhost", 18081, InsecureChannelCredentials.create())
                        .build();
        FirstGrpcServiceGrpc.FirstGrpcServiceBlockingStub blockingStub =
                FirstGrpcServiceGrpc.newBlockingStub(channel);
        blockingStub
                .api2(Req.newBuilder().setId(2).setReq("req").build())
                .forEachRemaining(System.out::println);
    }

    public void callApi4() {
        ManagedChannel channel =
                Grpc.newChannelBuilderForAddress(
                                "localhost", 18081, InsecureChannelCredentials.create())
                        .build();
        FirstGrpcServiceGrpc.FirstGrpcServiceStub stub = FirstGrpcServiceGrpc.newStub(channel);
        final int count = 100;
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<Req> requestStream =
                stub.api4(
                        new StreamObserver<Res>() {
                            @Override
                            public void onNext(Res value) {
                                System.out.println(value);
                            }

                            @Override
                            public void onError(Throwable t) {
                                System.out.println(t);
                                latch.countDown();
                            }

                            @Override
                            public void onCompleted() {
                                System.out.println("client complete");
                                latch.countDown();
                            }
                        });
        for (int i = 0; i < count; i++) {
            requestStream.onNext(Req.newBuilder().setId(i).setReq("req" + i).build());
        }
        requestStream.onCompleted();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("completed");
    }
}
