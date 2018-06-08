
package com.producthuntpost.model.posts.details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.producthuntpost.model.User;
import com.producthuntpost.model.posts.CurrentUser;
import com.producthuntpost.model.posts.Maker;

public class RelatedPost {

    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_state")
    @Expose
    private String productState;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("ios_featured_at")
    @Expose
    private Object iosFeaturedAt;
    @SerializedName("category_id")
    @Expose
    private Object categoryId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("current_user")
    @Expose
    private CurrentUser currentUser;
    @SerializedName("discussion_url")
    @Expose
    private String discussionUrl;
    @SerializedName("exclusive")
    @Expose
    private Object exclusive;
    @SerializedName("featured")
    @Expose
    private Boolean featured;
    @SerializedName("maker_inside")
    @Expose
    private Boolean makerInside;
    @SerializedName("makers")
    @Expose
    private List<Maker> makers = null;
    @SerializedName("platforms")
    @Expose
    private List<Object> platforms = null;
    @SerializedName("redirect_url")
    @Expose
    private String redirectUrl;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("votes_count")
    @Expose
    private Integer votesCount;

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Object getIosFeaturedAt() {
        return iosFeaturedAt;
    }

    public void setIosFeaturedAt(Object iosFeaturedAt) {
        this.iosFeaturedAt = iosFeaturedAt;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public String getDiscussionUrl() {
        return discussionUrl;
    }

    public void setDiscussionUrl(String discussionUrl) {
        this.discussionUrl = discussionUrl;
    }

    public Object getExclusive() {
        return exclusive;
    }

    public void setExclusive(Object exclusive) {
        this.exclusive = exclusive;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getMakerInside() {
        return makerInside;
    }

    public void setMakerInside(Boolean makerInside) {
        this.makerInside = makerInside;
    }

    public List<Maker> getMakers() {
        return makers;
    }

    public void setMakers(List<Maker> makers) {
        this.makers = makers;
    }

    public List<Object> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Object> platforms) {
        this.platforms = platforms;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

}
