package com.crystalnet.imageshare.Model;

import com.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by root on 05/01/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
    String Name;
    String Email;
    String Image;
//    int Following;
//    int Followers;
//    int Posts;

    public User() {
    }

    public User(String name, String email, String image) {
        Name = name;
        Email = email;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public static User dummyUser = new User("Kamran", "muhammadkamran@outlook.com", "http://www.tapes.com/images/RITEK%20DVD+.jpg");
}
