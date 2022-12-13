package com.chenluo.test;

public class NonCompressedPtrCase {
    // -Xmx4G -Xms4G -verbose:gc -Xloggc:nonCompressedPtr.gc.log -XX:-UseCompressedOops
    public static void main(String[] args) {
        CompressedPtrCase.run();
    }
}
