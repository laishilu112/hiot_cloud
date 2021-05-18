package com.huatec.hiot_cloud.test.networktest;

import java.io.Serializable;

/**
 * 学生实体类
 */
class Student implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * id
     */
    private int id;

    /**
     * 身高
     */
    private int heigh;

    /**
     * 是否毕业
     */
    private boolean graudated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeigh() {
        return heigh;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

    public boolean isGraudated() {
        return graudated;
    }

    public void setGraudated(boolean graudated) {
        this.graudated = graudated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
