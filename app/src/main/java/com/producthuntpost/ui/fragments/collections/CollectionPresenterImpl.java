package com.producthuntpost.ui.fragments.collections;

import android.support.design.widget.Snackbar;

import com.producthuntpost.R;
import com.producthuntpost.model.Collection;
import com.producthuntpost.model.collections.CollectionsPostModel;
import com.producthuntpost.service.ServiceCollection;
import com.producthuntpost.util.Utils;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CollectionPresenterImpl implements ICollectionPresenter {
    private CollectionsPostModel collectionDTO;
    private ICollectionView mView;

    @Override
    public void setView(ICollectionView view) {
        mView = view;

    }

    @Override
    public void getCollectionToday() {
        mView.showLoading();
        ServiceCollection.getTodayCollection(mView.getContext(),
                new Callback<CollectionsPostModel>() {
                    @Override
                    public void onResponse(Response<CollectionsPostModel> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            setCollectionDTO(response.body());
                            mView.getCollectionData();
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
    public void getCollectionAll(final int page) {
        mView.showLoading();
        ServiceCollection.getAllCollection(mView.getContext(), page,
                new Callback<CollectionsPostModel>() {
                    @Override
                    public void onResponse(Response<CollectionsPostModel> response, Retrofit retrofit) {
                        if (response.code() >= 200 && response.code() < 300) {
                            if(page == 1){
                                setCollectionDTO(response.body());
                            }else {
                                List<Collection> collections = getCollectionDTO().getCollections();
                                collections.addAll(response.body().getCollections());
                                collectionDTO.setCollections(collections);
                            }
                            mView.getCollectionData();
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
    public void sortNumberPost() {
        if(getCollectionDTO().getCollections().size() > 0){
            Collections.sort (getCollectionDTO().getCollections(), new Comparator() {
                public int compare(Object o1, Object o2) {
                    Collection c1 = (Collection) o1;
                    Collection c2 = (Collection) o2;
                    return c1.getPostsCount() - c2.getPostsCount();
                }

            });
            mView.getPostOrder();
        }

    }

    @Override
    public void sortTitle() {
        if(getCollectionDTO().getCollections().size() > 0) {
            Collections.sort(getCollectionDTO().getCollections(), new Comparator() {
                public int compare(Object o1, Object o2) {
                    Collection c1 = (Collection) o1;
                    Collection c2 = (Collection) o2;
                    return c1.getName().compareToIgnoreCase(c2.getName());
                }

            });
            mView.getPostOrder();
        }
    }

    @Override
    public void sortCreateUser() {
        if(getCollectionDTO().getCollections().size() > 0) {
            Collections.sort(getCollectionDTO().getCollections(), new Comparator() {
                public int compare(Object o1, Object o2) {
                    Collection c1 = (Collection) o1;
                    Collection c2 = (Collection) o2;
                    return c1.getUser().getName().compareToIgnoreCase(c2.getUser().getName());
                }

            });
            mView.getPostOrder();
        }

    }

   @Override
    public CollectionsPostModel getCollectionDTO() {
        return collectionDTO;
    }

    @Override
    public void setCollectionDTO(CollectionsPostModel collectionDTO) {
        this.collectionDTO = collectionDTO;

    }

    private String getDateTodayFormatter(){
        return Utils.simpleDateFormat().format(new Date());
    }

    private boolean verifyDate(String dateToday) {
        for (Collection c : getCollectionDTO().getCollections()) {
            if (dateToday.equalsIgnoreCase(c.getCreatedAt().split("T")[0])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void checkInternetNotice(){
        if(!Utils.checkInternetConnection(mView.getContext()))
            Snackbar.make(((CollectionFragment)mView).getView(), R.string.message_erro_internet, Snackbar.LENGTH_LONG).show();
    }
}
