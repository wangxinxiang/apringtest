package com.example.wang.entity;

public class Button {

    private String type;
    private String name;
    private Button[] sub_buttom;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button[] getSub_buttom() {
        return sub_buttom;
    }

    public void setSub_buttom(Button[] sub_buttom) {
        this.sub_buttom = sub_buttom;
    }
}
