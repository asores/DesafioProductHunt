package com.producthuntpost.ui.fragments.detailspost;

import com.producthuntpost.R;
import com.producthuntpost.model.posts.details.Comment;
import com.producthuntpost.model.posts.details.DetailsPostModel;
import com.producthuntpost.service.ServicePost;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailsPresenterImpl implements IDetailsPresenter {
    private IDetailsView mView;
    private DetailsPostModel detailsPostModel;

    @Override
    public void setView(IDetailsView view) {
        this.mView = view;

    }

    @Override
    public void getDetailsPost(int idPostSelect) {
        mView.showLoading();
        ServicePost.getPostDetails(mView.getContext(),idPostSelect,
                new Callback<DetailsPostModel>() {
                    @Override
                    public void onResponse(Response<DetailsPostModel> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            setDetailsPostModel(response.body());
                            mView.setDetailsPost();
                        } else if (response.code() == 401) {
                            mView.hideLoading();
                            mView.showMessageError(mView.getContext().getString(R.string.message_erro));
                        } else {
                            mView.hideLoading();
                            mView.showMessageError(mView.getContext().getString(R.string.message_erro));
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        mView.hideLoading();
                        mView.showMessageError(mView.getContext().getString(R.string.message_erro));
                    }
                }
        );

    }

    @Override
    public DetailsPostModel getDetailsPostModel() {
        return detailsPostModel;
    }

    @Override
    public int getIdUserCreatorPost() {
        return getDetailsPostModel().getPost().getUser().getId();
    }

    @Override
    public String getNameUser() {
        return getDetailsPostModel().getPost().getName();
    }

    @Override
    public String getDescriptionUser() {
        return getDetailsPostModel().getPost().getDescription();
    }

    @Override
    public List<Comment> getComments() {
        return getDetailsPostModel().getPost().getComments();
    }


    public void setDetailsPostModel(DetailsPostModel detailsPostModel) {
        this.detailsPostModel = detailsPostModel;
    }
}
