package com.ankur.example.common;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.ankur.example.R;
import com.ankur.example.customViews.loader.KProgressHUD;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    @BindView(R.id.cordinatorLayout)
    CoordinatorLayout mCordinatorLayout;
    Toolbar mToolBar;
    @BindView(R.id.noInternetLayout)
    LinearLayout mNoInternetLayout;
    @BindView(R.id.toolbarLeftBtn)
    LinearLayout mToolbarLeftBtn;
    @BindView(R.id.toolbarLeftBtnTitle)
    AppCompatTextView mToolbarLeftBtnTitle;
    @BindView(R.id.toolbarRightBtn)
    LinearLayout mToolbarRightBtn;
    @BindView(R.id.toolbarRightBtnTitle)
    AppCompatTextView mToolbarRightBtnTitle;
    private ViewGroup mViewContainer;
    private AppCompatTextView mActivityTitle;
    private KProgressHUD mHud;

    private Typeface snakeBarTypeFace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBaseContentView();

        initViews();
    }

    protected void setBaseContentView() {
        setContentView(R.layout.activity_base);
    }


    private void initViews() {
        mViewContainer = findViewById(R.id.base_layout_container);
        mActivityTitle = findViewById(R.id.activityTitle);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

    }

    protected void setLayout(int viewGroup) {
        try {
            if (mViewContainer != null && viewGroup != 0) {
                ViewGroup content = (ViewGroup) getLayoutInflater().inflate(viewGroup, null);
                mViewContainer.addView(content);
                content.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                content.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                try {
                    snakeBarTypeFace = Typeface.createFromAsset(getAssets(),
                            "fonts/roboto_regular.ttf");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ButterKnife.bind(this);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setActivityTitle(String activityTitle) {
        mActivityTitle.setText(activityTitle);
    }

    protected void setToolbarLeftButtonTitle(String activityTitle) {
        mToolbarLeftBtnTitle.setText(activityTitle);
    }

    protected void setToolbarRightButtonTitle(String activityTitle) {
    }


    public void showBackBtn() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void hideBackBtn() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void showActivityTitle() {
        if (mActivityTitle != null) {
            mActivityTitle.setVisibility(View.VISIBLE);
        }
    }

    public void hideActivityTitle() {
        if (mActivityTitle != null) {
            mActivityTitle.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showLoading() {
        try {
            if (!isDestroyed()) {
                mHud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.6f).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoading() {
        if (mHud != null && mHud.isShowing() && !isDestroyed()) {
            mHud.dismiss();
        }
    }


    @Override
    public void hideToolBar() {
        if (mToolBar != null) {
            mToolBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToolBar() {
        if (mToolBar != null) {
            mToolBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(mCordinatorLayout, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(BaseActivity.this, R.color.snack_bar_bg_color));
        TextView tv = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.header_text_color));
        if (snakeBarTypeFace != null) {
            tv.setTypeface(snakeBarTypeFace);
        }
        tv.setTextSize(12.5f);
        snackbar.show();

    }

    @Override
    public void onClickToolbarLeftButton() {

    }

    @Override
    public void onClickToolbarRightButton() {

    }


    @Override
    protected void onDestroy() {
        hideLoading();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        hideLoading();
        super.onStop();
    }

    public abstract void onClickBackButton();



    @Override
    public void onBackPressed() {
        onClickBackButton();
    }


    @Override
    public void showNoInternetLayout() {
        if (mNoInternetLayout != null) {
            mViewContainer.setVisibility(View.GONE);
            mNoInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideNoInternetLayout() {
        mViewContainer.setVisibility(View.VISIBLE);
        mNoInternetLayout.setVisibility(View.GONE);
    }

    public void setToolbarColorForHomeScreen() {
        mToolBar.setBackgroundColor(ContextCompat.getColor(BaseActivity.this, R.color.colorPrimary));
    }


    @OnClick({R.id.toolbarLeftBtn, R.id.toolbarRightBtn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.toolbarLeftBtn:
                onClickToolbarLeftButton();
                break;
            case R.id.toolbarRightBtn:
                onClickToolbarRightButton();
                break;
        }
    }

    @Override
    public void showToolBarRightButton(String name) {
        mToolbarRightBtn.setVisibility(View.VISIBLE);
        mToolbarRightBtnTitle.setText(name);

    }

    @Override
    public void showToolBarLeftButton(String name) {
        mToolbarLeftBtn.setVisibility(View.VISIBLE);
        mToolbarLeftBtnTitle.setText(name);

    }
}
