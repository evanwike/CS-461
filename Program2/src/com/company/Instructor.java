package com.company;

public abstract class Instructor {
    private String name;
    private String department;


    public Instructor(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}
