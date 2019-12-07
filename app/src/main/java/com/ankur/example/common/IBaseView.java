package com.ankur.example.common;

/**
 * Created by ankur.khandelwal on 27-07-2017.
 */

public interface IBaseView {

    void showToolBar();

    void hideToolBar();

    void showLoading();

    void hideLoading();

    void showNoInternetLayout();

    void hideNoInternetLayout();

    void showSnackBar(String message);

    void onClickToolbarLeftButton();

    void onClickToolbarRightButton();

    void showToolBarRightButton(String name);

    void showToolBarLeftButton(String name);

}
