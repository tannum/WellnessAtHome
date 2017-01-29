package com.bjl.tannum.wellnessathome.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by tannum on 1/27/2017 AD.
 */

@IgnoreExtraProperties
public class UserInfo {

    public String username;
    public String email;


    public UserInfo() {
    }

    public UserInfo(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
