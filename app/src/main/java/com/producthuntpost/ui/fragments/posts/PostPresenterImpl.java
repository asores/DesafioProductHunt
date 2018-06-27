package com.producthuntpost.ui.fragments.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;

import com.producthuntpost.R;
import com.producthuntpost.model.Post;
import com.producthuntpost.model.posts.PostsModel;
import com.producthuntpost.service.ServicePost;
import com.producthuntpost.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PostPresenterImpl implements IPostPresenter {
    private PostsModel postsAllDTO;
    private PostsModel postsTodayDTO;
    private IPostView mView;
    private boolean isShowMessageFilterDate = false;

    @Override
    public void setView(IPostView view) {
        mView = view;
    }


    @Override
    public void getPost(final int idCollectionSelect) {
        mView.showLoading();
        ServicePost.getPost(mView.getContext(), idCollectionSelect,
                new Callback<PostsModel>() {
                    @Override
                    public void onResponse(Response<PostsModel> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            postsAllDTO = response.body();
                            setCollectionDTO(response.body());
                            verifyDate();
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
    public void getPostAll(final int page) {
        mView.showLoading();
        ServicePost.getPostAll(mView.getContext(), page,
                new Callback<PostsModel>() {
                    @Override
                    public void onResponse(Response<PostsModel> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            if(page == 1){
                                postsTodayDTO.getCollection().setPosts(response.body().getPosts());
                            }else {
                                List<Post> posts = getPostDTO().getCollection().getPosts();
                                posts.addAll(response.body().getPosts());
                                postsTodayDTO.getCollection().setPosts(posts);
                            }

                            mView.getPostData();
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
    public void getPostDay(String day) {
        mView.showLoading();
        ServicePost.getPostDay(mView.getContext(), day,
                new Callback<PostsModel>() {
                    @Override
                    public void onResponse(Response<PostsModel> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            postsTodayDTO.getCollection().setPosts(response.body().getPosts());

                            //Checks whether the user has already been shown the message about posts with date filter.
                            if (isShowMessageFilterDate) {
                                mView.getPostData();
                            } else {
                                alertMessageFilterDate();
                            }

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
    public void sortNumberVotes() {
        if (getPostDTO().getCollection().getPosts().size() > 0) {
            Collections.sort(getPostDTO().getCollection().getPosts(), new Comparator() {
                public int compare(Object o1, Object o2) {
                    Post c1 = (Post) o1;
                    Post c2 = (Post) o2;
                    return c1.getVotesCount() - c2.getVotesCount();
                }

            });
            mView.getPostOrder();
        }

    }

    @Override
    public void sortTitle() {
        if (getPostDTO().getCollection().getPosts().size() > 0) {
            Collections.sort(getPostDTO().getCollection().getPosts(), new Comparator() {
                public int compare(Object o1, Object o2) {
                    Post c1 = (Post) o1;
                    Post c2 = (Post) o2;
                    return c1.getName().compareToIgnoreCase(c2.getName());
                }

            });
            mView.getPostOrder();
        }
    }

    @Override
    public void sortCreateUser() {
        if (getPostDTO().getCollection().getPosts().size() > 0) {
            Collections.sort(getPostDTO().getCollection().getPosts(), new Comparator() {
                public int compare(Object o1, Object o2) {
                    Post c1 = (Post) o1;
                    Post c2 = (Post) o2;
                    return c1.getUser().getName().compareToIgnoreCase(c2.getUser().getName());
                }

            });
            mView.getPostOrder();
        }

    }

    @Override
    public PostsModel getPostDTO() {
        return postsTodayDTO;
    }


    @Override
    public void setCollectionDTO(PostsModel postsModel) {
        this.postsTodayDTO = postsModel;
    }

    private void verifyDate() {
        String dateToday = Utils.getDateTodayFormatter();
        List<Post> post = new ArrayList<>();
        for (Post c : getPostDTO().getCollection().getPosts()) {
            if (dateToday.equalsIgnoreCase(c.getDay())) {
                post.add(c);
            }
        }

        if (post.size() == 0) {
            setCollectionDTO(postsAllDTO);
            mView.getPostData();
            Snackbar.make(((PostFragment) mView).getView(), R.string.message_date_alert, Snackbar.LENGTH_LONG).show();
        } else {
            postsTodayDTO.getCollection().setPosts(post);
            setCollectionDTO(postsTodayDTO);
            mView.getPostData();
        }

    }

    private void alertMessageFilterDate() {
        mView.hideLoading();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mView.getContext())
                .setCancelable(false)
                .setTitle(mView.getContext().getString(R.string.title_dialog))
                .setMessage(mView.getContext().getString(R.string.message_filter_date))
                .setPositiveButton(mView.getContext().getString(R.string.label_button_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isShowMessageFilterDate = true;
                        mView.getPostData();
                    }
                });
        alertDialog.create().show();
    }
}
