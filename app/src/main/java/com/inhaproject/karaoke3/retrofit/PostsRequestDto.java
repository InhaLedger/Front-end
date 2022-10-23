package com.inhaproject.karaoke3.retrofit;

import com.google.gson.annotations.SerializedName;


public class PostsRequestDto {

    public static class Create {

        public Create(Long userId, String title, String body) {
            this.userId = userId;
            this.title = title;
            this.body = body;
        }

        @SerializedName("userId")
        private final Long userId;

        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private final String title;

        @SerializedName("body")
        private final String body;

        @Override
        public String toString() {
            return "PostResult{" +
                    "userId=" + userId +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", bodyValue='" + body + '\'' +
                    '}';
        }
    }
}