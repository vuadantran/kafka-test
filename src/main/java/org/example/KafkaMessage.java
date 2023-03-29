package org.example;

import java.io.Serializable;

public class KafkaMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public KafkaMessage() {

    }

    public KafkaMessage(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "data='" + data + '\'' +
                '}';
    }
}
