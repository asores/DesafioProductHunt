package com.producthuntpost.ui.fragments;

import android.content.Context;

public interface IBaseView {
    Context getContext();
    void showLoading();
    void hideLoading();
    void showMessageError(String message);
}
