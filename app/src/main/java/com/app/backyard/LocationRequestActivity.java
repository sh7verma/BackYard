package com.app.backyard;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import utils.GpsStatusDetector;

/**
 * Created by applify on 7/17/2017.
 */

public class LocationRequestActivity extends BaseActivity implements GpsStatusDetector.GpsStatusDetectorCallBack {

    @BindView(R.id.txt_where)
    TextView txtWhere;
    @BindView(R.id.txt_your_location)
    TextView txtYourLocation;
    @BindView(R.id.txt_enable)
    TextView txtEnable;

    private GpsStatusDetector mGpsStatusDetector;

    @Override
    protected int getContentView() {
        return R.layout.activity_locationrequest;
    }

    @Override
    protected void onCreateStuff() {
        mGpsStatusDetector = new GpsStatusDetector(this);

    }

    @Override
    protected void initUI() {

        txtWhere.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.055));
        txtWhere.setPadding(0, mHeight / 32, 0, mHeight / 32);

        txtYourLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtYourLocation.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, 0);

        txtEnable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtEnable.setPadding(mWidth / 16, mWidth / 32, mWidth / 16, mWidth / 32);
    }

    @Override
    protected void initListener() {
        txtEnable.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return LocationRequestActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_enable:
                mGpsStatusDetector.checkGpsStatus(2);
                break;
        }
    }

    @Override
    public void onGpsSettingStatus(boolean enabled) {

        if (enabled) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            showAlert(txtEnable, getString(R.string.please_turn_on_your_gps_to_continue));
        }

    }

    @Override
    public void onGpsAlertCanceledByUser() {
        showAlert(txtEnable, getString(R.string.please_turn_on_your_gps_to_continue));
    }

    @Override
    public void showLocationScreen() {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
    }
}
