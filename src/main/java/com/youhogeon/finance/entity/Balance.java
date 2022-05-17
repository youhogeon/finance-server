package com.youhogeon.finance.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Balance implements Serializable {

    private static final long serialVersionUID = 362498820763168172L;
    
    String code;
    int price, qty;

}
