package com.example.yu_map.Chat;

public class ChatData {

    String NickName;
    String Message;

    public ChatData(){

    }

    public ChatData(String nickName, String message) {
        NickName = nickName;
        Message = message;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
