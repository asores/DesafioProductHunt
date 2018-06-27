package com.producthuntpost.ui.fragments.posts;

import com.producthuntpost.model.posts.PostsModel;

public interface IPostPresenter {
    void setView(IPostView view);
    void setCollectionDTO(PostsModel postsModel);
    PostsModel getPostDTO();
    void getPost(int idCollectionSelect);
    void getPostAll(int page);
    void getPostDay(String day);
    void sortNumberVotes();
    void sortTitle();
    void sortCreateUser();
}
