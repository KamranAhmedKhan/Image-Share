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
    String Location;
    String Age;
    String About;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }
    //    int Following;
//    int Followers;
//    int Posts;

    public User() {
    }

    public User(String name, String email, String image, String location, String age, String about) {
        Name = name;
        Email = email;
        Image = image;
        Location = location;
        Age = age;
        About = about;
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

    public static User dummyUser = new User("Kamran", "muhammadkamran@outlook.com", "http://www.tapes.com/images/RITEK%20DVD+.jpg","karachi","12","this is a dummy user");
}
