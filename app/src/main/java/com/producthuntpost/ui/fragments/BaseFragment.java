package com.producthuntpost.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.producthuntpost.R;

public class BaseFragment extends Fragment implements IBaseView {

    private ProgressDialog loading;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading() {
        if (!getActivity().isFinishing()) {
            loading = new ProgressDialog(getContext());
            loading.setCancelable(false);
            loading.setMessage(getString(R.string.message_loading));
            loading.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void showMessageError(String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(getContext().getString(R.string.title_alert))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", null).create().show();
    }
}
