package com.example.tt;

public class studentdatabase {
    String id;
    String name;
    String email;
    String mobile;
    String regd;

    public studentdatabase(){

    }
    public String getMobile(){return mobile;}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getRegd() {
        return regd;
    }

    public studentdatabase(String id, String name,String regd, String email, String mobile){
        this.id= id;
        this.name=name;
        this.email=email;
        this.mobile=mobile;
        this.regd=regd;
    }
}
