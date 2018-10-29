package com.app.backyard;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.AmenitiesAdapter;
import butterknife.BindView;
import model.AmenitiesModel;

public class AmenitiesActivity extends BaseActivity {

    @BindView(R.id.lv_amenities)
    ListView lvAmenities;

    @BindView(R.id.img_back)
    ImageView imgBckImg;

    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.tv_amenties_detail)
    TextView txtRulesDetail;

    @BindView(R.id.txt_no_amenities)
    TextView txtNoAmenities;

    @BindView(R.id.txt_done)
    TextView txtDone;

    ArrayList<AmenitiesModel.ResponseBean> mAmenitiesArrayList = new ArrayList<>();
    ArrayList<String> mSelectedAmenities = new ArrayList<>();
    ArrayList<String> mTempAmenities = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_amenities;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.AMENITIES);

        txtRulesDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        txtRulesDetail.setPadding(mWidth / 16, 0, mWidth / 15, mWidth / 32);

        txtNoAmenities.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));

        LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param3.setMargins(mWidth / 24, mWidth / 24, mWidth / 24, mWidth / 24);
        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtDone.setPadding(0, mWidth / 24, 0, mWidth / 24);
        txtDone.setLayoutParams(param3);

        lvAmenities.setPadding(mWidth / 24, 0, mWidth / 24, 0);

    }

    @Override
    protected void onCreateStuff() {
        mSelectedAmenities.clear();
        mSelectedAmenities.addAll(getIntent().getStringArrayListExtra("amenities_array"));
        getIntent().getIntExtra("search_activity", 0);

        if (getIntent().getIntExtra("search_activity", 0) == 1) {
            txtToolbarTitle.setText(R.string.amenities);
            txtRulesDetail.setVisibility(View.GONE);
        }
        getAmenities();

        if (mAmenitiesArrayList.size() > 0) {
            txtDone.setVisibility(View.VISIBLE);
            txtNoAmenities.setVisibility(View.GONE);
            lvAmenities.setAdapter(new AmenitiesAdapter(this, mAmenitiesArrayList));
        } else {
            txtNoAmenities.setVisibility(View.VISIBLE);
            txtDone.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
        txtDone.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return AmenitiesActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;

            case R.id.txt_done:
                ArrayList<String> amenitiesNamesList = new ArrayList<>();
                for (int i = 0; i < mAmenitiesArrayList.size(); i++) {
                    if (mAmenitiesArrayList.get(i).isChecked()) {
                        amenitiesNamesList.add(mAmenitiesArrayList.get(i).getAmenity_name());
                    }
                }
//                if (amenitiesNamesList.size() > 0) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("amenities_array", amenitiesNamesList);
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
//                }
//                else {
//                    showAlert(txtDone, "Please enter Amenities.");
//                }
                break;
        }
    }

    private void getAmenities() {
        if (getIntent().hasExtra("category_id")) {
            for (AmenitiesModel.ResponseBean amenitiesItem : db.getAmenities(String.valueOf(getIntent().getIntExtra("category_id", 0)))) {
                AmenitiesModel.ResponseBean amenitiesModel = new AmenitiesModel.ResponseBean();
                amenitiesModel.setAmenity_name(amenitiesItem.getAmenity_name());
                if (mSelectedAmenities.contains(amenitiesItem.getAmenity_name()))
                    amenitiesModel.setChecked(true);
                else
                    amenitiesModel.setChecked(false);
                mAmenitiesArrayList.add(amenitiesModel);
            }
        } else {
            mAmenitiesArrayList.clear();
            for (AmenitiesModel.ResponseBean amenitiesItem : db.getAllAmenities()) {
                AmenitiesModel.ResponseBean amenitiesModel = new AmenitiesModel.ResponseBean();
                amenitiesModel.setAmenity_name(amenitiesItem.getAmenity_name());
                if (mSelectedAmenities.contains(amenitiesItem.getAmenity_name()))
                    amenitiesModel.setChecked(true);
                else
                    amenitiesModel.setChecked(false);

                if (!mTempAmenities.contains(amenitiesItem.getAmenity_name())) {
                    mTempAmenities.add(amenitiesItem.getAmenity_name());
                    mAmenitiesArrayList.add(amenitiesModel);
                }
            }
        }
    }
}
