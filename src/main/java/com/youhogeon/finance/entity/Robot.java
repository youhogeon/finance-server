package com.youhogeon.finance.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Robot implements Serializable {

    private static final long serialVersionUID = 362498820763168172L;

    private String auth;
    private long balance;
    private int id;

    public Robot(String auth){
        this.auth = auth;
    }

}
