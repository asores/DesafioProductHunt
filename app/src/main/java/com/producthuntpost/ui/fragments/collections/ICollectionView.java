package com.producthuntpost.ui.fragments.collections;


import com.producthuntpost.ui.fragments.IBaseView;

public interface ICollectionView extends IBaseView {
    void getCollectionData();
    void getCollectionFilterDate();
    void getPostOrder();
}
