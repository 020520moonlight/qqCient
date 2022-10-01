package main.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 封装网络传输数据
 */
public class Message implements Serializable {
    private static final long serializableUUID = 2l;
    private String sender;//发送者
    private String reciever;
    private String content;
    private String sendTime;
    private String messageType;


    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }


}
