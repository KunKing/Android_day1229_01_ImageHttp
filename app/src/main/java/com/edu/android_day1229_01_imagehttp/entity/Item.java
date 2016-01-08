package com.edu.android_day1229_01_imagehttp.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ming on 2015/12/29.
 */
public class Item {

    private long userId;
    private String userIcon;
    private String userName;
    private String content;
    private String image;

    public Item(JSONObject object) throws JSONException {
        if (!object.isNull("user")) {
            userIcon = object.getJSONObject("user").getString("icon");
            userName = object.getJSONObject("user").getString("login");
            userId =  object.getJSONObject("user").getLong("id");
        }
        if (!object.isNull("image")) {
            image = object.getString("image");
        }
        content = object.getString("content");
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Item{" +
                "userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
