package com.producthuntpost.ui.fragments.detailspost;

import com.producthuntpost.model.posts.details.Comment;
import com.producthuntpost.model.posts.details.DetailsPostModel;

import java.util.List;

public interface IDetailsPresenter {
    void setView(IDetailsView view);
    void getDetailsPost(int idPostSelect);
    DetailsPostModel getDetailsPostModel();
    int getIdUserCreatorPost();
    String getNameUser();
    String getDescriptionUser();
    List<Comment> getComments();
}
