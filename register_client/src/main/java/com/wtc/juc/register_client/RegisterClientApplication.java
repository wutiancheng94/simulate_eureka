package com.wtc.juc.register_client;

import com.wtc.juc.register_client.main.RegisterClient;

public class RegisterClientApplication {

    public static void main(String[] args) {
        RegisterClient registerClient = new RegisterClient();
        //模拟请求注册
        registerClient.start();

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
