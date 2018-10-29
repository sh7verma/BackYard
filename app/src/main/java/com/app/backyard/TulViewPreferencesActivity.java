package com.app.backyard;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import model.ViewTulModel;
import utils.Constants;

import static com.app.backyard.R.id.view4;

public class TulViewPreferencesActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.tv_preferences_detail)
    TextView tvPreferencesDetail;

    @BindView(R.id.tv_preferences_available_hint)
    TextView tvPreferencesAvailableLabel;
    @BindView(R.id.tv_preferences_available)
    TextView tvPreferencesAvailable;

    @BindView(R.id.tv_preferences_start_time_hint)
    TextView tvPreferencesStartTimeLabel;
    @BindView(R.id.tv_preferences_start_time)
    TextView tvPreferencesStartTime;

    @BindView(R.id.tv_preferences_end_time_hint)
    TextView tvPreferencesEndTimeLabel;
    @BindView(R.id.tv_preferences_end_time)
    TextView tvPreferencesEndTime;

    @BindView(R.id.ll_available)
    LinearLayout llAvailable;

    @BindView(R.id.rl_preferences_main_container)
    RelativeLayout rlMainContainer;

    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;

    ViewTulModel.ResponseBean.PreferencesBean prefrencesModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_view_preferences;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.PREFERENCES);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL);
        imgBckImg.setLayoutParams(param);

        tvPreferencesDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPreferencesDetail.setPadding(mWidth / 16, 0, mWidth / 15, mWidth / 32);

        tvPreferencesAvailable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesAvailable.setPadding(0, mWidth / 64, 0, 0);

        tvPreferencesStartTimeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPreferencesStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesStartTime.setPadding(0, mWidth / 64, 0, 0);

        tvPreferencesEndTimeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPreferencesEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesEndTime.setPadding(0, mWidth / 64, 0, 0);

        llAvailable.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llStartTime.setPadding(0, mWidth / 28, 0, mWidth / 28);

        llEndTime.setPadding(0, mWidth / 28, 0, mWidth / 28);

        LinearLayout.LayoutParams param5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        param5.setMargins(mWidth / 15, 0, mWidth / 15, 0);
        rlMainContainer.setBackgroundColor(getResources().getColor(R.color.white_color));
        rlMainContainer.setLayoutParams(param5);

        setData();
    }


    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
    }

    private void setData() {
        prefrencesModel = getIntent().getParcelableExtra("data");
        if(prefrencesModel.getEnd_time().equals("00:00")){
            llEndTime.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            tvPreferencesStartTimeLabel.setText(R.string.START_TIME);
        }else {
            llEndTime.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            tvPreferencesStartTimeLabel.setText(R.string.CHECK_IN);
        }

        tvPreferencesAvailable.setText(prefrencesModel.getAvailable());
        tvPreferencesStartTime.setText(prefrencesModel.getStart_time());
        tvPreferencesEndTime.setText(prefrencesModel.getEnd_time());
    }


    @Override
    protected Context getContext() {
        return TulViewPreferencesActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
