package com.chenluo.grpc;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

public class FirstGrpcClient {
    public static void main(String[] args) {
        new FirstGrpcClient().callApi1();
    }

    public void callApi1() {
        Req.Builder req = Req.newBuilder().setId(1).setReq("req1");
        ManagedChannel channel = Grpc.newChannelBuilderForAddress("localhost", 18081, InsecureChannelCredentials.create()).build();
        FirstGrpcServiceGrpc.FirstGrpcServiceBlockingStub blockingStub = FirstGrpcServiceGrpc.newBlockingStub(channel);
        Res res = blockingStub.api2(Req.newBuilder().setId(1).setReq("req1").build());
        System.out.println(res);
    }
}
