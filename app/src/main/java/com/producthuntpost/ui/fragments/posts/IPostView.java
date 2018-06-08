package com.producthuntpost.ui.fragments.posts;


import com.producthuntpost.ui.fragments.IBaseView;

public interface IPostView extends IBaseView {
    void getPostData();
    void getPostFilterDate();
    void getPostOrder();
}
