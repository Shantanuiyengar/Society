package com.shantanu.society.model;

public class MyDataDAO {
    private String name;
    private String desc;
    private String imageurl;


    public MyDataDAO(){

    }
    MyDataDAO(String name, String desc, String imageurl){
        this.desc = desc;
        this.name = name;
        this.imageurl = imageurl;
    }

    public String getName(){
        return this.name;
    }
    public String getDesc(){
        return this.desc;
    }
    public String getImageUrl(){return this.imageurl;}

    public void setName(String name) {
        this.name = name;
    }
    public void setDesc(String desc){this.desc = desc; }
    public void setImage(String imageurl){this.imageurl = imageurl;}
}
