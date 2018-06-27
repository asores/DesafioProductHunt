
package com.producthuntpost.model.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.producthuntpost.model.Collection;
import com.producthuntpost.model.Post;

import java.io.Serializable;
import java.util.List;

public class PostsDTO implements Serializable {

    @SerializedName("collection")
    @Expose
    private Collection collection;

    @SerializedName("posts")
    @Expose
    private List<Post> posts;

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
