
package com.producthuntpost.model.posts.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.producthuntpost.model.Post;

public class DetailsPostModel {

    @SerializedName("post")
    @Expose
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
