package com.rgllm.trader;

import java.util.Comparator;

public class ComparatorUser implements Comparator<User>{    
    @Override
    public int compare(User u1,User u2){
        return u1.compareTo(u2);
    }
}