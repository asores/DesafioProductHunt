package com.producthuntpost.ui.fragments.collections;

import com.producthuntpost.model.collections.CollectionsPostModel;

public interface ICollectionPresenter {
    void setView(ICollectionView view);
    void setCollectionDTO(CollectionsPostModel collectionDTO);
    CollectionsPostModel getCollectionDTO();
    void getCollectionToday();
    void getCollectionAll(int page);
    void sortNumberPost();
    void sortTitle();
    void sortCreateUser();
    void checkInternetNotice();
}
