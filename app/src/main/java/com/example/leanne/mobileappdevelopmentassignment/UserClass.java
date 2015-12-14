package com.example.leanne.mobileappdevelopmentassignment;

/**
 * Created by Leanne on 15. 12. 6..
 */
public class UserClass {
    public int _id;
    public String userID;
    public String password;
    public String name;
    public String gender;
    public String birthday;
    public String phone;
    public String email;

    public UserClass(int _id, String id, String pw, String name, String gender, String bday, String phone, String email) {
        this._id = _id;
        this.userID = id;
        this.password = pw;
        this.name = name;
        this.gender = gender;
        this.birthday = bday;
        this.phone = phone;
        this.email = email;
    }
}
