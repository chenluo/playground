package com.chenluo.grpc;

import com.chenluo.grpc.firstgrpc.FirstGrpc;
import com.chenluo.grpc.firstgrpc.Req;
import com.chenluo.grpc.firstgrpc.Res;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

public class FirstGrpcImpl extends FirstGrpc {
    @Override
    public void api1(RpcController controller, Req request, RpcCallback<Res> done) {

    }
}
