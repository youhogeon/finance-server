package com.youhogeon.finance.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Chart implements Serializable{

    private static final long serialVersionUID = 362498820763181265L;
    
    int date, time, open, high, low, close, volume;

}
