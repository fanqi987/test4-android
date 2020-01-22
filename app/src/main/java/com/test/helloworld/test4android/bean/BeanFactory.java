package com.test.helloworld.test4android.bean;

import com.test.helloworld.test4android.R;

import java.util.ArrayList;
import java.util.List;

public class BeanFactory {


    private static BeanFactory factory;

    private BeanFactory() {
    }

    public static BeanFactory getInstance() {
        if (factory == null) {
            factory = new BeanFactory();
        }
        return factory;
    }

    public List<Bean> getBeans() {
        return createBeans();
    }

    private List<Bean> createBeans() {
        List<Bean> beans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            beans.add(new Bean(R.mipmap.ic_launcher, "实体" + i));
        }
        return beans;
    }
}
