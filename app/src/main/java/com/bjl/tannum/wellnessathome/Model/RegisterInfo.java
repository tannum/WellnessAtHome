package com.bjl.tannum.wellnessathome.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by tannum on 1/28/2017 AD.
 */

@IgnoreExtraProperties
public class RegisterInfo {

    public String name;
    public String username;
    public String bio;
    public String email;
    public String tel;

    public RegisterInfo() {
    }

    public RegisterInfo(String bio, String email, String name, String tel, String username) {
        this.bio = bio;
        this.email = email;
        this.name = name;
        this.tel = tel;
        this.username = username;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
