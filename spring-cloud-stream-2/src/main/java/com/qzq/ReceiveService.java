package com.qzq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class ReceiveService {

    @StreamListener(Sink.INPUT)
    public void receive(Object payload) {
        String name = payload.getClass().getName();

        System.out.println(name + "  --- > " + new String((byte[]) payload));
    }
}
