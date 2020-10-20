package com.example.fastfoodie.models;


import android.net.Uri;

public class FirebaseUserModel {
    private String username;
    private Uri photo_url;
    private String uid;

    private static FirebaseUserModel userModel = null;

    private FirebaseUserModel(){

    }

    public static FirebaseUserModel getUserInstanceModel(){
        if(userModel == null){
            return new FirebaseUserModel();
        }
        else
        {
            return userModel;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Uri getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(Uri photo_url) {
        this.photo_url = photo_url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
