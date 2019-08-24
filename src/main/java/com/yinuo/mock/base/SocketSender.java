package com.yinuo.mock.base;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketSender {
    private final String ip;
    private final Integer port;
    private final Socket socket;

    public SocketSender(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        this.socket = new Socket();
    }

    public void connect(int timeOut) throws SocketServiceException {
        try {
            socket.connect(new InetSocketAddress(ip, port), timeOut);
            socket.setSoTimeout(timeOut);
        } catch (IOException e) {
            throw new SocketServiceException(String.format("连接[%s:%d] 超时 %d ms", ip, port, timeOut));
        }
    }

    public byte[] send(byte[] data, int receiveLength) throws SocketServiceException {
        send(data);
        return receive(receiveLength);
    }



    public void send(byte[] data) throws SocketServiceException {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            throw new SocketServiceException("发送消息失败",e);
        }
    }

    public byte[] receive(int length) throws SocketServiceException {
        try {
            InputStream inputStream = socket.getInputStream();
            return IOUtils.readFully(inputStream,length);
        } catch (IOException e) {
            throw new SocketServiceException("接收消息失败",e);
        }
    }
}
