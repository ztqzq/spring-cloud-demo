package com.qzq.beans;

import lombok.Builder;
import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private int age;

    public int hashCode() {
        return super.hashCode();
    }

    public void setName(String name) {
        this.name = name + "_xxx";
    }
}
