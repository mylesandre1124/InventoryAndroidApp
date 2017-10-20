package com.mylesandre1124.inventoryandroid.models;


import java.lang.reflect.Field;

/**
 * Created by Myles on 3/4/17.
 */
public class Employee {

    private int empId;
    private String username;
    private String name;
    private String password;
    private int admin;


    public Employee() {
    }

    public Employee(int empId, String username, String name, String password, int admin) {
        this.empId = empId;
        this.username = username;
        this.name = name;
        this.password = password;
        this.admin = admin;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public boolean isNull()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields) {
            try {
                if(field.get(this) == null)
                {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
