package com.example.appthesis;

public class Message {
    public  String message;
    public  String message_code;

    public Message(String message, String message_code) {
        this.message = message;
        this.message_code = message_code;
    }

    public void setMessage_code(String message_code) {
        this.message_code = message_code;
    }
    public String getMessage() {
        return message;
    }

    public String getMessage_code() {
        return message_code;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
