package com.app.backyard;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adapters.ProfileViewpagerAdapter;
import butterknife.BindView;
import fragments.LentTulFragment;
import fragments.RentedTulFragment;
import utils.Constants;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView tvTitle;

    @BindView(R.id.tl_profile)
    TabLayout tabs;
    @BindView(R.id.vp_profile)
    ViewPager pager;
    ProfileViewpagerAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_history;
    }

    @Override
    protected void initUI() {

        tvTitle.setText("HISTORY");
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));

        mAdapter = new ProfileViewpagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new LentTulFragment(), getString(R.string.lent_tuls));
        mAdapter.addFragment(new RentedTulFragment(), getString(R.string.rented_tuls));
        pager.setAdapter(mAdapter);

        tabs.setupWithViewPager(pager);

    }

    @Override
    protected void onCreateStuff() {

        Constants.closeKeyboard(this, pager);

        if (!connectedToInternet())
            showInternetAlert(tabs);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return HistoryActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        Constants.PROFILE_ID = 0;
        super.onDestroy();
    }

}
