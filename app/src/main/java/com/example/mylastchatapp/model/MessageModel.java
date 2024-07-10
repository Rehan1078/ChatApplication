package com.example.mylastchatapp.model;

public class MessageModel {
    String msgId;
    String senderID;
    String Message;

    public MessageModel() {
    }

    public MessageModel(String msgId, String senderID, String message) {
        this.msgId = msgId;
        this.senderID = senderID;
        Message = message;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
