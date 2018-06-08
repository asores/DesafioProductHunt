package com.producthuntpost.ui.fragments.detailspost;

import com.producthuntpost.R;
import com.producthuntpost.model.posts.details.Comment;
import com.producthuntpost.model.posts.details.DetailsPostDTO;
import com.producthuntpost.service.ServicePost;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailsPresenterImpl implements IDetailsPresenter {
    private IDetailsView mView;
    private DetailsPostDTO detailsPostDTO;

    @Override
    public void setView(IDetailsView view) {
        this.mView = view;

    }

    @Override
    public void getDetailsPost(int idPostSelect) {
        mView.showLoading();
        ServicePost.getPostDetails(mView.getContext(),idPostSelect,
                new Callback<DetailsPostDTO>() {
                    @Override
                    public void onResponse(Response<DetailsPostDTO> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            setDetailsPostDTO(response.body());
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
    public DetailsPostDTO getDetailsPostDTO() {
        return detailsPostDTO;
    }

    @Override
    public int getIdUserCreatorPost() {
        return getDetailsPostDTO().getPost().getUser().getId();
    }

    @Override
    public String getNameUser() {
        return getDetailsPostDTO().getPost().getName();
    }

    @Override
    public String getDescriptionUser() {
        return getDetailsPostDTO().getPost().getDescription();
    }

    @Override
    public List<Comment> getComments() {
        return getDetailsPostDTO().getPost().getComments();
    }


    public void setDetailsPostDTO(DetailsPostDTO detailsPostDTO) {
        this.detailsPostDTO = detailsPostDTO;
    }
}
