package com.producthuntpost.ui.fragments.detailsuser;

import android.view.View;

import com.makeramen.roundedimageview.RoundedImageView;
import com.producthuntpost.R;
import com.producthuntpost.model.Post;
import com.producthuntpost.model.posts.details.Vote;
import com.producthuntpost.model.users.UserDetailsModel;
import com.producthuntpost.service.ServicePicasso;
import com.producthuntpost.service.ServiceUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserDetailsPresenterImpl implements IUserDetailsPresenter {
    private IUserDetailsView mView;
    private UserDetailsModel userDetailsModel;

    @Override
    public void setView(IUserDetailsView view) {
        this.mView = view;

    }

    @Override
    public void requestUserDetails(int idUser) {
        mView.showLoading();
        ServiceUser.getUserDetails(mView.getContext(),idUser,
                new Callback<UserDetailsModel>() {
                    @Override
                    public void onResponse(Response<UserDetailsModel> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            setUserDetailsModel(response.body());
                            mView.setDetailsUserData();
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
    public UserDetailsModel getUserDetails() {
        return userDetailsModel;
    }

    @Override
    public List<Post> getListPostsUser() {
        return getUserDetails().getUser().getPosts();
    }

    @Override
    public List<Vote> getListVotesUser() {
        return getUserDetails().getUser().getVotes();
    }

    @Override
    public String getNameUser() {
        return getUserDetails().getUser().getName();
    }

    @Override
    public String getHeadLineUser() {
        return getUserDetails().getUser().getHeadline();
    }

    @Override
    public void setAvatarUser(RoundedImageView imgAvatarUser) {
        Picasso picasso = new Picasso.Builder(mView.getContext()).downloader(ServicePicasso.getClientPicasso(mView.getContext())).build();
        picasso.load(getUserDetails().getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(imgAvatarUser);
        imgAvatarUser.setVisibility(View.VISIBLE);
    }


    public void setUserDetailsModel(UserDetailsModel userDetailsModel) {
        this.userDetailsModel = userDetailsModel;
    }
}
