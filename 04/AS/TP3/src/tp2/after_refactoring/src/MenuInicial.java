package com.rgllm.trader;

import com.rgllm.trader.TraderApp;

public enum MenuInicial {
	
    SIGNIN(1, TraderApp::signIn),
    SIGNUP(2, TraderApp::signUp);

    private int value;
    private Runnable execution;

    private MenuInicial(int val, Runnable toRun) {
        int value = val;
        execution = toRun;
    }
    public void execute() { 
        execution.run();
    }

    static void execute(int code) {
        for (MenuInicial item : values()) {
            if (item.value == code) {
                item.run(); break;
            }
        }
        throw new IllegalArgumentException("unknown code " + code);
    }
    
};
