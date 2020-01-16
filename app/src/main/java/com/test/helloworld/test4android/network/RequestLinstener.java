package com.test.helloworld.test4android.network;

public interface RequestLinstener {
    void onSuccess(String response);

    void onFail(Exception e);
}
