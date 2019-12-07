package com.ankur.example.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.ankur.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseFragment extends DialogFragment implements IBaseView {

    @BindView(R.id.base_fragment_view_container)
    public ViewGroup mBaseFragmentContainer;
    @BindView(R.id.fragment_view_container)
    ViewGroup mContentContainer;
    @BindView(R.id.noInternetLayout)
    LinearLayout mNoInternetLayout;

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    @Override
    public void showLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    @Override
    public void hideToolBar() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).hideToolBar();
        }
    }

    @Override
    public void showToolBar() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showToolBar();
        }
    }


    @Override
    public void showSnackBar(String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showSnackBar(message);
        }
    }

    @Override
    public void onClickToolbarLeftButton() {

    }

    @Override
    public void onClickToolbarRightButton() {

    }

    @Override
    public void showToolBarRightButton(String name) {

    }

    @Override
    public void showToolBarLeftButton(String name) {

    }


    @Override
    public void showNoInternetLayout() {
        if (mNoInternetLayout != null) {
            mContentContainer.setVisibility(View.GONE);
            mNoInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideNoInternetLayout() {
        mContentContainer.setVisibility(View.VISIBLE);
        mNoInternetLayout.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mContentContainer = view.findViewById(R.id.fragment_view_container);
        mBaseFragmentContainer = view.findViewById(R.id.base_fragment_view_container);
        return view;
    }

    protected void setLayout(LayoutInflater inflater, int layoutID) {
        if (inflater != null && layoutID != 0) {
            mContentContainer.addView(inflater.inflate(layoutID, null));
        }
        ButterKnife.bind(this, mBaseFragmentContainer);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
