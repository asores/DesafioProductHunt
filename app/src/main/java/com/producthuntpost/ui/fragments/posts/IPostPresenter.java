package com.producthuntpost.ui.fragments.posts;

import com.producthuntpost.model.posts.PostsDTO;

public interface IPostPresenter {
    void setView(IPostView view);
    void setCollectionDTO(PostsDTO postsDTO);
    PostsDTO getPostDTO();
    void getPost(int idCollectionSelect);
    void getPostAll(int page);
    void getPostDay(String day);
    void sortNumberVotes();
    void sortTitle();
    void sortCreateUser();
}
