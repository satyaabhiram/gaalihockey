package com.gaalihockey.message;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType messageType;
    private String value1, value2;

    public MessageType getMessageType() {
        return messageType;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    public Message(MessageType messageType, String value1) {
        this.messageType = messageType;
        this.value1 = value1;
    }

    public Message(MessageType messageType, String value1, String value2) {
        this.messageType = messageType;
        this.value1 = value1;
        this.value2 = value2;
    }
}
