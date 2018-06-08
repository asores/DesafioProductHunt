
package com.producthuntpost.model.posts;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentUser implements Serializable {

    @SerializedName("voted_for_post")
    @Expose
    private Boolean votedForPost;
    @SerializedName("commented_on_post")
    @Expose
    private Boolean commentedOnPost;

    public Boolean getVotedForPost() {
        return votedForPost;
    }

    public void setVotedForPost(Boolean votedForPost) {
        this.votedForPost = votedForPost;
    }

    public Boolean getCommentedOnPost() {
        return commentedOnPost;
    }

    public void setCommentedOnPost(Boolean commentedOnPost) {
        this.commentedOnPost = commentedOnPost;
    }

}
