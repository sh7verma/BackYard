package com.app.backyard;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

import butterknife.BindView;
import customviews.FlowLayout;
import dialog.SearchAvailbiltyDialog;
import utils.Constants;


public class SearchActivity extends BaseActivity {

    static final int CATEGORY = 1;
    static final int AMENITIES = 2;
    static final int LOCATION = 3;

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.img_cross)
    ImageView imgCross;

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    @BindView(R.id.ll_upper)
    LinearLayout llUpper;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.tv_categories)
    TextView tvCategories;
    @BindView(R.id.tv_availbilty_modes)
    TextView tvAvailbiltyModes;
    @BindView(R.id.tv_location_modes)
    TextView tvLocationMode;

    @BindView(R.id.tv_best_rating)
    TextView tvBestRating;
    @BindView(R.id.sc_rated)
    SwitchCompat scRated;

    @BindView(R.id.txt_amenities)
    TextView txtAmenities;
    @BindView(R.id.fl_interests)
    FlowLayout flInterests;

    @BindView(R.id.txt_pricing_option)
    TextView txtPricingOption;

    @BindView(R.id.ll_selection_pricing_option)
    LinearLayout llSelectionPricing;

    @BindView(R.id.ll_hourly)
    LinearLayout llHourly;
    @BindView(R.id.img_hourly)
    ImageView imgHourly;
    @BindView(R.id.txt_hourly)
    TextView txtHourly;
    @BindView(R.id.ll_day)
    LinearLayout llDay;
    @BindView(R.id.txt_day)
    TextView txtDay;
    @BindView(R.id.img_day)
    ImageView imgDay;
    @BindView(R.id.ll_both)
    LinearLayout llBoth;
    @BindView(R.id.txt_both)
    TextView txtBoth;
    @BindView(R.id.img_both)
    ImageView imgBoth;

    @BindView(R.id.ll_price_range)
    LinearLayout llPriceRange;
    @BindView(R.id.tv_price_lable)
    TextView tvPriceLable;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.seekbar_price)
    RangeSeekBar seekBar;

    @BindView(R.id.ll_distance_range)
    LinearLayout llDistanceRange;
    @BindView(R.id.tv_distance_label)
    TextView tvDistanceLabel;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.seekbar_distance)
    RangeSeekBar seekBarDistance;

    @BindView(R.id.tv_done)
    TextView tvDone;

    private String mSelectedCat = "", mAddress = "";
    private int mSelectedCatId, mAvailbiltyMode = 0,
            minPrice = 0, maxPrice = 1000, mDistance = 101, mCount, mProductType = 2;
    private double mLatitude = 0.0, mLongitude = 0.0;
    private ArrayList<String> mAmenitiesArray = new ArrayList<>();
    private boolean mCategoryPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }


    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/regular.ttf");

        llUpper.setPadding(mWidth / 26, mHeight / 42, mWidth / 26, mHeight / 42);

        tvToolbar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, mHeight / 42);

        edSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        edSearch.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        edSearch.setLayoutParams(layoutParams);
        edSearch.setCompoundDrawablePadding(mWidth / 32);
        edSearch.setTypeface(typeface);

        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams3.setMargins(0, 0, 0, mHeight / 42);

        tvCategories.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvCategories.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvCategories.setLayoutParams(layoutParams3);
        tvCategories.setCompoundDrawablePadding(mWidth / 32);

        tvAvailbiltyModes.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvAvailbiltyModes.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvAvailbiltyModes.setLayoutParams(layoutParams3);
        tvAvailbiltyModes.setCompoundDrawablePadding(mWidth / 32);

        tvLocationMode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvLocationMode.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvLocationMode.setCompoundDrawablePadding(mWidth / 32);

        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams5.setMargins(0, mHeight / 42, 0, mHeight / 42);

        tvBestRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        txtAmenities.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtAmenities.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 52);

        tvBestRating.setPadding(mWidth / 32, mHeight / 32, 0, mHeight / 52);

        llPriceRange.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        tvPriceLable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ 0 + "-" + "999+");

        txtPricingOption.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtPricingOption.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        llSelectionPricing.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        txtHourly.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtBoth.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        seekBar.setPadding(mWidth / 24, mWidth / 32, mWidth / 24, mWidth / 32);

        llDistanceRange.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, 0);

        tvDistanceLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvDistance.setText("100+ Miles/Km");

        seekBarDistance.setPadding(mWidth / 24, 0, mWidth / 24, mWidth / 32);

        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams4.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 42);

        tvDone.setPadding(0, mWidth / 26, 0, mWidth / 26);
        tvDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvDone.setLayoutParams(layoutParams4);

        flInterests.setPadding(0, 0, mWidth / 64, mWidth / 32);
    }


    @Override
    protected void onCreateStuff() {
        seekBar.setNotifyWhileDragging(true);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                minPrice = (int) minValue;
                maxPrice = (int) maxValue;
                if (maxPrice == 1000) {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minValue + "-" + "999+");
                } else {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minValue + "-" + maxValue);
                }

                if (minPrice == 1000) {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ "999-999+");
                }
            }
        });

        seekBarDistance.setNotifyWhileDragging(true);
        seekBarDistance.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                tvDistance.setText(maxValue + " Miles/Km");
                mDistance = (int) maxValue;
                if (mDistance == 101) {
                    tvDistance.setText("100+ Miles/Km");
                } else {
                    tvDistance.setText(maxValue + " Miles/Km");
                }
            }
        });

        scRated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        setPrefilledData();
    }

    private void setPrefilledData() {

        if (getIntent().hasExtra("path")) {
            mCategoryPath = true;
            tvCategories.setText(getIntent().getStringExtra("category"));
            tvCategories.setEnabled(false);
            mSelectedCatId = getIntent().getIntExtra("category_id", 0);

            Constants.CATEGORY_CATEGORY_ID = mSelectedCatId;
            Constants.CATEGORY_CATEGORY = getIntent().getStringExtra("category");

            if (Constants.CATEGORY_TITLE != null) {
                edSearch.setText(Constants.CATEGORY_TITLE);
                edSearch.setSelection(edSearch.getText().toString().trim().length());

                tvCategories.setText(Constants.CATEGORY_CATEGORY);
                Constants.CATEGORY_TITLE = edSearch.getText().toString().toLowerCase().trim();

                mSelectedCatId = Constants.CATEGORY_CATEGORY_ID;
                mSelectedCat = Constants.CATEGORY_CATEGORY;
                maxPrice = Constants.CATEGORY_MAX_PRICE_SEARCH;
                minPrice = Constants.CATEGORY_MIN_PRICE_SEARCH;
                mAvailbiltyMode = Constants.CATEGORY_AVAILABILTY;
                mDistance = Constants.CATEGORY_DISTANCE;
                mLatitude = Constants.CATEGORY_LATITUDE;
                mLongitude = Constants.CATEGORY_LONGITUDE;
                mProductType = Constants.CATEGORY_PRODUCT_TYPE;
                mAddress = Constants.CATEGORY_ADDRESS;

                tvLocationMode.setText(mAddress);

                if (Constants.CATEGORY_BEST_RATED)
                    scRated.setChecked(true);
                else
                    scRated.setChecked(false);

//              0 for pickup, 1 for delivery,2 for both
//              1 for weekdays, 2 for weekend

                if (mAvailbiltyMode == 1)
                    tvAvailbiltyModes.setText("Weekdays");
                else if (mAvailbiltyMode == 2)
                    tvAvailbiltyModes.setText("Only Weekends");
                else
                    tvAvailbiltyModes.setText("Both");

                seekBar.setSelectedMaxValue(maxPrice);
                seekBar.setSelectedMinValue(minPrice);

                seekBarDistance.setSelectedMaxValue(mDistance);


                if (maxPrice == 1000) {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minPrice + "-" + "999+");
                } else {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minPrice + "-" + maxPrice);
                }
                if (minPrice == 1000) {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ "999-999+");
                }
                if (mDistance == 101) {
                    tvDistance.setText("100 Miles/Km");
                } else {
                    tvDistance.setText(mDistance + " Miles/Km");
                }


                mAmenitiesArray.clear();
                if (!TextUtils.isEmpty(Constants.CATEGORY_AMENITIES)) {
                    String[] tempAmenities = Constants.CATEGORY_AMENITIES.split(Constants.RULES_REGEX);
                    for (String amenities : tempAmenities) {
                        mAmenitiesArray.add(amenities);
                    }
                    if (mAmenitiesArray.size() > 0) {
                        flInterests.removeAllViews();
                        for (String item : mAmenitiesArray) {
                            flInterests.addView(inflateView(item));
                        }
                    }
                }

                if (mProductType == 1) {
                    imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                    imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                } else if (mProductType == 0) {
                    imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                    imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                } else {
                    mProductType = 2;
                    imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                }
            }
        } else {
            if (Constants.TITLE != null) {
                edSearch.setText(Constants.TITLE);
                edSearch.setSelection(edSearch.getText().toString().trim().length());
                tvCategories.setText(Constants.CATEGORY);

                Constants.TITLE = edSearch.getText().toString().toLowerCase().trim();

                mSelectedCat = Constants.CATEGORY;
                mSelectedCatId = Constants.CATEGORY_ID;
                maxPrice = Constants.MAX_PRICE_SEARCH;
                minPrice = Constants.MIN_PRICE_SEARCH;
                mAvailbiltyMode = Constants.AVAILABILITY;
                mLatitude = Constants.LATITUDE;
                mLongitude = Constants.LONGITUDE;
                mDistance = Constants.DISTANCE;
                mProductType = Constants.PRODUCT_TYPE;
                mAddress = Constants.ADDRESS;

                tvLocationMode.setText(mAddress);

                mAmenitiesArray.clear();
                if (!TextUtils.isEmpty(Constants.AMENITIES)) {
                    String[] tempAmenities = Constants.AMENITIES.split(Constants.RULES_REGEX);
                    for (String amenities : tempAmenities) {
                        mAmenitiesArray.add(amenities);
                    }
                    if (mAmenitiesArray.size() > 0) {
                        flInterests.removeAllViews();
                        for (String item : mAmenitiesArray) {
                            flInterests.addView(inflateView(item));
                        }
                    }
                }
                if (Constants.BEST_RATED)
                    scRated.setChecked(true);
                else
                    scRated.setChecked(false);

//            0 for pickup, 1 for delivery,2 for both
//            1 for weekdays, 2 for weekend

                if (mAvailbiltyMode == 1)
                    tvAvailbiltyModes.setText("Weekdays");
                else if (mAvailbiltyMode == 2)
                    tvAvailbiltyModes.setText("Only Weekends");
                else
                    tvAvailbiltyModes.setText("Both");

                seekBar.setSelectedMaxValue(maxPrice);
                seekBar.setSelectedMinValue(minPrice);
                seekBarDistance.setSelectedMaxValue(mDistance);

                if (maxPrice == 1000) {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minPrice + "-" + "999+");
                } else {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minPrice + "-" + maxPrice);
                }
                if (minPrice == 1000) {
                    tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ "999-999+");
                }
                if (mDistance == 101) {
                    tvDistance.setText("100+ Miles/Km");
                } else {
                    tvDistance.setText(mDistance + " Miles/Km");
                }

                if (mProductType == 1) {
                    imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                    imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                } else if (mProductType == 0) {
                    imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                    imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                } else {
                    mProductType = 2;
                    imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                    imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                }
            }
        }


    }

    @Override
    protected void initListener() {
        tvAvailbiltyModes.setOnClickListener(this);
        tvCategories.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        imgCross.setOnClickListener(this);
        txtAmenities.setOnClickListener(this);
        tvLocationMode.setOnClickListener(this);
        llBoth.setOnClickListener(this);
        llDay.setOnClickListener(this);
        llHourly.setOnClickListener(this);
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    Constants.closeKeyboard(SearchActivity.this, llUpper);
                }
                return false;
            }
        });
    }

    @Override
    protected Context getContext() {
        return SearchActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_amenities:
                Intent amenitiesIntent = new Intent(mContext, AmenitiesActivity.class);
                amenitiesIntent.putStringArrayListExtra("amenities_array", mAmenitiesArray);
                if (mSelectedCatId != 0)
                    amenitiesIntent.putExtra("category_id", mSelectedCatId);
                amenitiesIntent.putExtra("search_activity", 1);
                startActivityForResult(amenitiesIntent, AMENITIES);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.img_cross:
                Constants.closeKeyboard(this, llUpper);
                moveBack();
                break;
            case R.id.tv_clear:
                Constants.closeKeyboard(this, llUpper);
                tempResetValues();
                break;
            case R.id.tv_categories:
                Intent catIntent = new Intent(mContext, SelectCategoryActivity.class);
                catIntent.putExtra("selected_category", mSelectedCat);
                catIntent.putExtra("selected_categoryId", mSelectedCatId);
                startActivityForResult(catIntent, CATEGORY);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_availbilty_modes:
                new SearchAvailbiltyDialog(mContext, mWidth, tvAvailbiltyModes.getText().toString(), 1).showDialog();
                break;
            case R.id.tv_done:
                if (connectedToInternet()) {
                    mCount = 0;
                    if (minPrice == 1000)
                        minPrice = 999;
                    if (!TextUtils.isEmpty(edSearch.getText().toString().trim()))
                        mCount++;
                    if (mAvailbiltyMode != 0)
                        mCount++;
                    if (mAmenitiesArray.size() > 0)
                        mCount++;
                    if (scRated.isChecked())
                        mCount++;
                    if (minPrice != 0 || maxPrice != 1000)
                        mCount++;
                    if (mDistance != 101)
                        mCount++;
                    if (mProductType != 2)
                        mCount++;
                    if (!TextUtils.isEmpty(mAddress))
                        mCount++;

                    String amenitiesValue;
                    if (mAmenitiesArray.size() > 0) {
                        StringBuilder mBuilderAmenities = new StringBuilder();
                        for (String amenities : mAmenitiesArray) {
                            mBuilderAmenities.append(amenities + Constants.RULES_REGEX);
                        }
                        amenitiesValue = mBuilderAmenities.toString().substring(0, mBuilderAmenities.toString().length() - 9);
                    } else
                        amenitiesValue = "";
                    Log.e("Amenities = ", amenitiesValue);
                    if (mCategoryPath) {
                        Constants.CATEGORY_TITLE = edSearch.getText().toString().trim();
                        Constants.CATEGORY_CATEGORY_ID = mSelectedCatId;
                        Constants.CATEGORY_CATEGORY = mSelectedCat;
                        Constants.CATEGORY_MAX_PRICE_SEARCH = maxPrice;
                        Constants.CATEGORY_MIN_PRICE_SEARCH = minPrice;
                        Constants.CATEGORY_AVAILABILTY = mAvailbiltyMode;
                        Constants.CATEGORY_AMENITIES = amenitiesValue;
                        Constants.CATEGORY_DISTANCE = mDistance;
                        Constants.CATEGORY_LATITUDE = mLatitude;
                        Constants.CATEGORY_LONGITUDE = mLongitude;
                        Constants.CATEGORY_ADDRESS = mAddress;
                        Constants.CATEGORY_FILTER_COUNT = mCount;
                        Constants.CATEGORY_PRODUCT_TYPE = mProductType;


                        if (scRated.isChecked())
                            Constants.CATEGORY_BEST_RATED = true;
                        else
                            Constants.CATEGORY_BEST_RATED = false;

                    } else {

                        if (mSelectedCatId != 0 && !getIntent().hasExtra("path"))
                            mCount++;

                        Constants.TITLE = edSearch.getText().toString().trim();
                        Constants.CATEGORY_ID = mSelectedCatId;
                        Constants.CATEGORY = mSelectedCat;
                        Constants.MAX_PRICE_SEARCH = maxPrice;
                        Constants.MIN_PRICE_SEARCH = minPrice;
                        Constants.AVAILABILITY = mAvailbiltyMode;
                        Constants.AMENITIES = amenitiesValue;
                        Constants.DISTANCE = mDistance;
                        Constants.LATITUDE = mLatitude;
                        Constants.LONGITUDE = mLongitude;
                        Constants.ADDRESS = mAddress;
                        Constants.FILTER_COUNT = mCount;
                        Constants.PRODUCT_TYPE = mProductType;

                        if (scRated.isChecked())
                            Constants.BEST_RATED = true;
                        else
                            Constants.BEST_RATED = false;
                    }
                    if (getIntent().hasExtra("path")) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        moveBack();
                    } else {
                        Intent searchResult = new Intent(mContext, SearchResultActivity.class);
                        startActivity(searchResult);
                        finish();
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
                } else
                    showAlert(llMain, errorInternet);
                break;

            case R.id.ll_both:
                imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                mProductType = 2;
                break;

            case R.id.ll_hourly:
                imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                mProductType = 1;
                break;

            case R.id.ll_day:
                imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));
                imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
                mProductType = 0;
                break;

            case R.id.tv_location_modes:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, LOCATION);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CATEGORY:
                    if (mSelectedCatId != data.getIntExtra("catId", 0)) {
                        mAmenitiesArray.clear();
                        flInterests.removeAllViews();
                        for (String item : mAmenitiesArray) {
                            flInterests.addView(inflateView(item));
                        }
                    }
                    tvCategories.setText(data.getStringExtra("catName"));
                    mSelectedCat = Constants.EMPTY;
                    mSelectedCat = data.getStringExtra("catName");
                    mSelectedCatId = data.getIntExtra("catId", 0);
                    break;
                case AMENITIES:
                    mAmenitiesArray.clear();
                    if (data.getStringArrayListExtra("amenities_array") != null) {
                        mAmenitiesArray.addAll(data.getStringArrayListExtra("amenities_array"));
                        if (mAmenitiesArray.size() > 0) {
                            flInterests.removeAllViews();
                            for (String item : mAmenitiesArray) {
                                flInterests.addView(inflateView(item));
                            }
                        } else {
                            flInterests.removeAllViews();
                        }
                    } else {
                        flInterests.removeAllViews();
                    }
                    break;
                case LOCATION:
                    mLatitude = Double.parseDouble(data.getStringExtra("latitude"));
                    mLongitude = Double.parseDouble(data.getStringExtra("longitude"));
                    mAddress = data.getStringExtra("address");
                    tvLocationMode.setText(mAddress);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setResultOfAvailableOn(String result, int availability) {
        mAvailbiltyMode = availability;
        tvAvailbiltyModes.setText(result);
    }

    private void alertDialogClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to clear filters?")
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetValues();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void resetValues() {
        Constants.TITLE = null;
        edSearch.setText("");
        if (!getIntent().hasExtra("path")) {
            tvCategories.setText("");
            mSelectedCatId = 0;
            mSelectedCat = "";
        }
        tvAvailbiltyModes.setText("");
        scRated.setChecked(false);
        mAmenitiesArray.clear();
        flInterests.removeAllViews();
        for (String item : mAmenitiesArray) {
            flInterests.addView(inflateView(item));
        }

        mAvailbiltyMode = 0;
        minPrice = 0;
        maxPrice = 999;
        mDistance = 100;

        seekBar.setSelectedMaxValue(maxPrice);
        seekBar.setSelectedMinValue(minPrice);
        seekBarDistance.setSelectedMaxValue(mDistance);

        tvDistance.setText(mDistance + " Miles/Km");
        tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minPrice + "-" + maxPrice);
    }

    private void tempResetValues() {
//        edSearch.setText("");
//        if (!getIntent().hasExtra("path")) {
//            tvCategories.setText("");
//            mSelectedCatId = 0;
//            mSelectedCat = "";
//        }
//        tvAvailbiltyModes.setText("");
//        scRated.setChecked(false);+" "+
//        minPrice = 0;
//        maxPrice = 999;
//        mDistance = 100;
//
//        mAddress = "";
//        mLatitude = 0.0;
//        mLongitude = 0.0;
//
//        seekBar.setSelectedMaxValue(maxPrice);
//        seekBar.setSelectedMinValue(minPrice);
//        seekBarDistance.setSelectedMaxValue(mDistance);
//        tvLocationMode.setText(mAddress);
//        tvDistance.setText(mDistance + " Miles");
//        tvPrice.setText(Constants.POUND + "/" + Constants.EURO + " " + minPrice + "-" + maxPrice);

        if (mCategoryPath) {
            edSearch.setText("");
            if (!getIntent().hasExtra("path")) {
                tvCategories.setText("");
                mSelectedCatId = 0;
                mSelectedCat = "";
            }
            tvAvailbiltyModes.setText("");
            scRated.setChecked(false);
            minPrice = 0;
            maxPrice = 1000;
            mDistance = 101;
            mAddress = "";
            mLatitude = 0.0;
            mLongitude = 0.0;
            mProductType = 2;

            seekBar.setSelectedMaxValue(maxPrice);
            seekBar.setSelectedMinValue(minPrice);
            seekBarDistance.setSelectedMaxValue(100);

            imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
            imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
            imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));

            tvLocationMode.setText(mAddress);
            tvDistance.setText("100 Miles/Km");
            tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minPrice + "-" + maxPrice);
        } else {
            edSearch.setText("");
            if (!getIntent().hasExtra("path")) {
                tvCategories.setText("");
                mSelectedCatId = 0;
                mSelectedCat = "";
            }
            tvAvailbiltyModes.setText("");
            scRated.setChecked(false);
            minPrice = 0;
            maxPrice = 999;
            mDistance = 100;
            mAddress = "";
            mLatitude = 0.0;
            mLongitude = 0.0;
            mProductType = 0;

            seekBar.setSelectedMaxValue(maxPrice);
            seekBar.setSelectedMinValue(minPrice);
            seekBarDistance.setSelectedMaxValue(100);

            imgDay.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
            imgHourly.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn));
            imgBoth.setImageDrawable(getResources().getDrawable(R.mipmap.ic_radio_btn_s));

            tvLocationMode.setText(mAddress);
            tvDistance.setText("100 Miles/Km");
            tvPrice.setText(Constants.POUND + "/" + Constants.EURO +" "+ minPrice + "-" + maxPrice);
        }
    }


    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    View inflateView(final String amenities) {
        View interestChip = LayoutInflater.from(mContext).inflate(R.layout.layout_amenities, null, false);

        FlowLayout.LayoutParams innerParms = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        innerParms.setMargins(mWidth / 32, 0, 0, mWidth / 32);
        final LinearLayout llMain = (LinearLayout) interestChip.findViewById(R.id.ll_main);
        llMain.setLayoutParams(innerParms);

        final TextView txtAmenities = (TextView) interestChip.findViewById(R.id.txt_amenities);
        txtAmenities.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtAmenities.setText(amenities);
        txtAmenities.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);
        txtAmenities.setTextColor(Color.WHITE);

        return interestChip;
    }


}
