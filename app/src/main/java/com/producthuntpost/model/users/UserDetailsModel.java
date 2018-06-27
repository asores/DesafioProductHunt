package com.producthuntpost.model.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.producthuntpost.model.User;

import java.io.Serializable;

public class UserDetailsModel implements Serializable {
    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

