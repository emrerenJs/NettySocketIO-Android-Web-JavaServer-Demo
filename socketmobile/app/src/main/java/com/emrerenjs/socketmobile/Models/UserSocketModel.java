package com.emrerenjs.socketmobile.Models;

import java.io.Serializable;

public class UserSocketModel implements Serializable {
    private String username;
    private String groupname;

    public UserSocketModel(String username, String groupname) {
        this.username = username;
        this.groupname = groupname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
