package com.edu.android_day1229_01_imagehttp.entity;

/**
 * Created by Ming on 2015/12/29.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 混淆 签名
 */
public class ItemShit {
    /**
     * format : image
     * image : app114457519.jpg
     * published_at : 1451303705
     * tag :
     * user : {"avatar_updated_at":1450178207,"last_visited_at":1422498014,"created_at":1422498014,"state":"active","email":"","last_device":"ios_6.0.1","role":"n","login":"李氏控股集团","id":25167622,"icon":"20151215111647.jpg"}
     * image_size : {"s":[220,306,14375],"m":[500,696,68456]}
     * id : 114457519
     * votes : {"down":-385,"up":5041}
     * created_at : 1451300758
     * content : 也许你很牛逼，但我未必吊你！
     * state : publish
     * comments_count : 116
     * allow_comment : true
     * share_count : 1555
     * type : hot
     */

    @SerializedName("items")
    private List<ItemsEntity> items;

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public static class ItemsEntity {
        @SerializedName("format")
        private String format;
        @SerializedName("image")
        private String image;
        /**
         * avatar_updated_at : 1450178207
         * last_visited_at : 1422498014
         * created_at : 1422498014
         * state : active
         * email :
         * last_device : ios_6.0.1
         * role : n
         * login : 李氏控股集团
         * id : 25167622
         * icon : 20151215111647.jpg
         */
        @SerializedName("user")
        private UserEntity user;
        @SerializedName("id")
        private int id;
        @SerializedName("content")
        private String content;

        public void setFormat(String format) {
            this.format = format;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }


        public void setId(int id) {
            this.id = id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFormat() {
            return format;
        }

        public String getImage() {
            return image;
        }

        public UserEntity getUser() {
            return user;
        }


        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public static class UserEntity {
            @SerializedName("login")
            private String login;
            @SerializedName("id")
            private int id;
            @SerializedName("icon")
            private String icon;

            public void setLogin(String login) {
                this.login = login;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLogin() {
                return login;
            }

            public int getId() {
                return id;
            }

            public String getIcon() {
                return icon;
            }
        }

        public static class ImageSizeEntity {
            private List<Integer> s;
            private List<Integer> m;

            public void setS(List<Integer> s) {
                this.s = s;
            }

            public void setM(List<Integer> m) {
                this.m = m;
            }

            public List<Integer> getS() {
                return s;
            }

            public List<Integer> getM() {
                return m;
            }
        }
    }
}
