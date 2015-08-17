package com.example.jonas.shoppinglist.domain;

public class Message {
    private String id, date, body, address, type;

    public Message(String id, String date, String address, String body, String type){
        this.id = id;
        this.date = date;
        this.body = body;
        this.address = address;
        this.type = type;
    }

    public String toString(){
        return "id: " + id + ", date: " + date + ", " + type + ": " + address + ", body: " + body;
    }
}
