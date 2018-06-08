
package com.producthuntpost.model.posts.details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.producthuntpost.model.User;
import com.producthuntpost.model.Post;

public class Comment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("parent_comment_id")
    @Expose
    private Object parentCommentId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;
    @SerializedName("child_comments_count")
    @Expose
    private Integer childCommentsCount;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("subject_type")
    @Expose
    private String subjectType;
    @SerializedName("sticky")
    @Expose
    private Boolean sticky;
    @SerializedName("votes")
    @Expose
    private Integer votes;
    @SerializedName("post")
    @Expose
    private Post post;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("child_comments")
    @Expose
    private List<ChildComment> childComments = null;
    @SerializedName("maker")
    @Expose
    private Boolean maker;
    @SerializedName("hunter")
    @Expose
    private Boolean hunter;
    @SerializedName("live_guest")
    @Expose
    private Boolean liveGuest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Object parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getChildCommentsCount() {
        return childCommentsCount;
    }

    public void setChildCommentsCount(Integer childCommentsCount) {
        this.childCommentsCount = childCommentsCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public Boolean getSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ChildComment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<ChildComment> childComments) {
        this.childComments = childComments;
    }

    public Boolean getMaker() {
        return maker;
    }

    public void setMaker(Boolean maker) {
        this.maker = maker;
    }

    public Boolean getHunter() {
        return hunter;
    }

    public void setHunter(Boolean hunter) {
        this.hunter = hunter;
    }

    public Boolean getLiveGuest() {
        return liveGuest;
    }

    public void setLiveGuest(Boolean liveGuest) {
        this.liveGuest = liveGuest;
    }

}
