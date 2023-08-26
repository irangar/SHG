package com.isoft.smarthomegardening;

public class UserProfile {
    String Id, Uname, Email, Pass, EType;

    public UserProfile() {

    }

    public UserProfile(String Id, String Uname, String Email, String Pass, String EType) {
        this.Id = Id;
        this.Uname = Uname;
        this.Email = Email;
        this.Pass = Pass;
        this.EType = EType;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getEType() {
        return EType;
    }

    public void setEType(String EType) {
        this.EType = EType;
    }
}
