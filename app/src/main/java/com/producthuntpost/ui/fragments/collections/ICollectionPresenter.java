package com.producthuntpost.ui.fragments.collections;

import com.producthuntpost.model.collections.CollectionsPostDTO;

public interface ICollectionPresenter {
    void setView(ICollectionView view);
    void setCollectionDTO(CollectionsPostDTO collectionDTO);
    CollectionsPostDTO getCollectionDTO();
    void getCollectionToday();
    void getCollectionAll(int page);
    void sortNumberPost();
    void sortTitle();
    void sortCreateUser();
    void checkInternetNotice();
}
