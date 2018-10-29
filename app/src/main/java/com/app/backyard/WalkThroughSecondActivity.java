package com.app.backyard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalkThroughSecondActivity extends BaseActivity {

    @BindView(R.id.rl_toolbar_signup_container)
    RelativeLayout rlToolBar;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.bt_walk_through_second_join_us)
    TextView btJoinUs;

    @BindView(R.id.ll_walk_through_second_main_container)
    LinearLayout llMainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_walk_through_second;
    }

    @Override
    protected void initUI() {

//        rlToolBar.setPadding(mWidth / 30, 0, mWidth / 30, 0);

        imgBack.setImageResource(R.mipmap.ic_back_blue);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(mWidth / 20, mWidth / 35, mWidth / 20, 0);
        llMainContainer.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsButton.setMargins(0, mWidth / 36, 0, mWidth / 20);
        btJoinUs.setLayoutParams(layoutParamsButton);
        btJoinUs.setPadding(0, mHeight / 36, 0, mHeight / 36);
        btJoinUs.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        btJoinUs.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return WalkThroughSecondActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.bt_walk_through_second_join_us:
                Intent start = new Intent(mContext, AfterWalkthroughActivity.class);
                start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(start);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
