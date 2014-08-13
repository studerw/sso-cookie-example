package com.studerw.sso.user;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * user: studerw
 * Date: 8/12/14
 */
public class User implements Serializable{
    private String username;
    private String firstname;
    private String lastname;

    public User(){}

    public User(String username, String firstname, String lastname){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("firstname", firstname)
                .append("lastname", lastname)
                .toString();
    }

    public String getName(){
        return this.getFirstname() + " " + this.getLastname();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
