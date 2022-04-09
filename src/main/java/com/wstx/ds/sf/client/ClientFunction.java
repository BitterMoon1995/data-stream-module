package com.wstx.ds.sf.client;

import com.wstx.ds.sf.codec.SyncFrameCodec;
import com.wstx.ds.sf.msg.SyncFrame;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

class ClientFunction {

    public static byte[] getEncodedBA(String vsn){

        SyncFrame syncFrame = new SyncFrame("01131723",System.currentTimeMillis(),
                vsn, "0x01010001", "0x0101",
                "0x01010001",100, "0xFF","0xFF",
                34.56485,104.26587, 490.5,
                50.5f,60.5f,124);

        SyncFrameCodec codec = new SyncFrameCodec();
        ByteBuf buf = codec.iEncode(syncFrame);

        int len = buf.readableBytes();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = buf.readByte();
        }
        return bytes;
    }

    public static byte[] genSfBytes(SyncFrame syncFrame) throws IOException, InterruptedException {
        SyncFrameCodec codec = new SyncFrameCodec();
        ByteBuf buf = codec.iEncode(syncFrame);

        int len = buf.readableBytes();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = buf.readByte();
        }

        return bytes;
    }
}
