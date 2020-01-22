package com.test.helloworld.test4android.bean;

import androidx.annotation.NonNull;

public class Province {

    public Province() {

    }


    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    @NonNull
    @Override
    public String toString() {
        return "id: " + id + " 名字：" + name + " \n";
    }
}
