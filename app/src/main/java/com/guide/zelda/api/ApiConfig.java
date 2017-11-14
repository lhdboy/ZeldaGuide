package com.guide.zelda.api;


import net.orange_box.storebox.annotations.method.KeyByString;

public interface ApiConfig {

    @KeyByString("env")
    int getEnv();

    @KeyByString("env")
    void setEnv(int env);
}
