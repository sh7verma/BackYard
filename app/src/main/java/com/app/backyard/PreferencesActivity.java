package com.app.backyard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import dialog.PreferencesAvailableOnDialog;
import model.CreateStripeAccountModel;
import model.TulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PreferencesActivity extends BaseActivity {
    private static final int MOVENEXT = 7;
    private static final int HOURS = 1;
    private static final int ENDTIMEHOURS = 2;
    private static final int DAYS = 0;

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.tv_preferences_detail)
    TextView tvPreferencesDetail;

//    @BindView(R.id.tv_preferences_available_for)
//    TextView tvPreferencesBookingAvailableFor;

    @BindView(R.id.tv_preferences_start_time_hint)
    TextView tvPreferencesStartTimeLabel;
    @BindView(R.id.tv_preferences_start_time)
    TextView tvPreferencesStartTime;

    @BindView(R.id.tv_preferences_end_time_hint)
    TextView tvPreferencesEndTimeLabel;
    @BindView(R.id.tv_preferences_end_time)
    TextView tvPreferencesEndTime;

    @BindView(R.id.tv_hrs_hint)
    TextView tvHrsStartLabel;
    @BindView(R.id.tv_hrs_time)
    TextView tvHrsStartTime;

    @BindView(R.id.tv_hrs_end_hint)
    TextView tvHrsEndLabel;
    @BindView(R.id.tv_hrs_end_time)
    TextView tvHrsEndTime;

    @BindView(R.id.tv_preferences_available_hint)
    TextView tvPreferencesAvailableLabel;
    @BindView(R.id.tv_preferences_available)
    TextView tvPreferencesAvailable;

    @BindView(R.id.ll_preferences_buttons_container)
    LinearLayout llBtContainer;

    @BindView(R.id.bt_save)
    Button btSave;

    @BindView(R.id.bt_discard)
    Button btDiscard;

//    @BindView(R.id.ll_available_for)
//    LinearLayout llAvailableFor;

    @BindView(R.id.ll_available)
    LinearLayout llAvailable;

    @BindView(R.id.rl_preferences_main_container)
    RelativeLayout rlMainContainer;

    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.ll_hrs_start_time)
    LinearLayout llHrsStartTime;
    @BindView(R.id.ll_hrs_end_time)
    LinearLayout llHrsEndTime;

//    @BindView(R.id.view1)
//    View view1;

    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view5)
    View view6;

    @BindView(R.id.view_end)
    View viewEnd;

    TulModel.PreferencesTul mPreferencesTul;
    TulModel.PricingTul mPricingTul;
    ProgressDialog progDailog;

    boolean mDiscountChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_preferences;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/semibold.ttf");

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtToolbarTitle.setText(R.string.PREFERENCES);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL);
        imgBckImg.setLayoutParams(param);

        tvPreferencesDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPreferencesDetail.setPadding(mWidth / 16, 0, mWidth / 15, mWidth / 32);

        tvPreferencesAvailable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesAvailable.setPadding(0, mWidth / 64, 0, 0);

//      tvPreferencesBookingAvailableFor.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
//      tvPreferencesBookingAvailableFor.setPadding(0, mWidth / 64, 0, 0);

        tvPreferencesStartTimeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPreferencesStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesStartTime.setPadding(0, mWidth / 64, 0, 0);

        tvPreferencesEndTimeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPreferencesEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesEndTime.setPadding(0, mWidth / 64, 0, 0);

        tvHrsStartLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvHrsStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvHrsStartTime.setPadding(0, mWidth / 64, 0, 0);

        tvHrsEndLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvHrsEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvHrsEndTime.setPadding(0, mWidth / 64, 0, 0);

        btDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btDiscard.setPadding(0, mWidth / 32, 0, mWidth / 32);

        btSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        btSave.setPadding(0, mWidth / 32, 0, mWidth / 32);

        btDiscard.setTypeface(typeface);
        btSave.setTypeface(typeface);

        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llBtContainer.setLayoutParams(param1);

//        llAvailableFor.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llStartTime.setPadding(0, mWidth / 28, 0, mWidth / 28);
        llEndTime.setPadding(0, mWidth / 28, 0, mWidth / 28);
        llHrsStartTime.setPadding(0, mWidth / 28, 0, mWidth / 28);
        llHrsEndTime.setPadding(0, mWidth / 28, 0, mWidth / 28);
        llAvailable.setPadding(0, mWidth / 32, 0, mWidth / 32);

        LinearLayout.LayoutParams param5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        param5.setMargins(mWidth / 15, 0, mWidth / 15, 0);
        rlMainContainer.setBackgroundColor(getResources().getColor(R.color.white_color));
        rlMainContainer.setLayoutParams(param5);
    }

    @Override
    protected void onCreateStuff() {
        mPreferencesTul = TulModel.getPrefrencesTul();
//        if (mPreferencesTul.isEdit) {
        mPricingTul = TulModel.getPricingTul();
//        }
        setData();
    }

    @Override
    protected void initListener() {
        llAvailable.setOnClickListener(this);
        llStartTime.setOnClickListener(this);
        llEndTime.setOnClickListener(this);
        llHrsStartTime.setOnClickListener(this);
//        llHrsEndTime.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btDiscard.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {

        if (mPreferencesTul.updateData) {
            setData();
            mPreferencesTul.updateData = false;
        }
        super.onResume();
    }

    private void setData() {
        if (mPreferencesTul.availbiltyMode != null) {
            tvPreferencesAvailable.setText(mPreferencesTul.availbiltyMode);

            if (mPreferencesTul.hrsStartTime != null) {
                tvHrsStartTime.setText(mPreferencesTul.hrsStartTime);

            }
            if (mPreferencesTul.startTime != null) {
                tvPreferencesStartTime.setText(mPreferencesTul.startTime);
                tvPreferencesEndTime.setText(mPreferencesTul.endTime);
                tvPreferencesDetail.setText(R.string.Set_preferences_like_availability_check_in_and_check_out);
            }
        }
        setView();
    }

    void setView() {
        if (mPreferencesTul.bookingAvailbiltyFor.equalsIgnoreCase(getResources().getString(R.string.hourly))) {
            visibleHourlyView();
        } else if (mPreferencesTul.bookingAvailbiltyFor.equalsIgnoreCase(getResources().getString(R.string.daily))) {
            visibleDailyViews();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                mPreferencesTul.updateData = true;
                TulModel.setPrefrencesTul(mPreferencesTul);
                moveBack();
                break;
            case R.id.bt_discard:

                if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
                    alertDiscardDialog();
                } else {
                    alertDayDialog();
                }
                break;
            case R.id.bt_save:
                if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
                    if (TextUtils.isEmpty(tvPreferencesStartTime.getText().toString())) {
                        showAlert(llAvailable, getString(R.string.error_start_time));
                        return;
                    } else if (TextUtils.isEmpty(tvPreferencesEndTime.getText().toString())) {
                        showAlert(llAvailable, getString(R.string.error_end_time));
                        return;
                    }
                } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
                    if (TextUtils.isEmpty(tvHrsStartTime.getText().toString())) {
                        showAlert(llAvailable, getString(R.string.error_hrs_start_time));
                        return;
                    }
                }

                if (mPreferencesTul.isEdit &&
                        mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))
                        && !TextUtils.isEmpty(mPreferencesTul.hrsStartTime) &&
                        !mPreferencesTul.hrsStartTime.equals(tvHrsStartTime.getText().toString())) {
                    alertDiscountDialog();
                    return;
                } else if (mPreferencesTul.isEdit && mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly)) && TextUtils.isEmpty(mPreferencesTul.hrsStartTime)) {
                    mDiscountChanged = true;
                }

                moveToNext();
                break;
            case R.id.ll_start_time: {
                selectOptions(getHours(), DAYS, tvHrsStartTime.getText().toString());
                break;
            }
            case R.id.ll_hrs_start_time: {
                selectOptions(getHours(), HOURS, tvHrsStartTime.getText().toString());
                break;
            }
            case R.id.ll_hrs_end_time: {
//                if (TextUtils.isEmpty(tvHrsStartTime.getText().toString())) {
//                    showAlert(llAvailable, getString(R.string.error_hrs_start_time));
//                } else {
//                    String[] time = tvHrsStartTime.getText().toString().split(":");
//                    int startTime = Integer.parseInt(time[0]) + 1;
//                    selectOptions(getEndHours(startTime), ENDTIMEHOURS, tvHrsEndTime.getText().toString());
//                }
//                break;
            }

            /*case R.id.ll_end_time: {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(mContext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                tvPreferencesEndTime.setText(updateTime(hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                tpd.show();
                break;
            }*/

            case R.id.ll_available:
                new PreferencesAvailableOnDialog(mContext, mWidth, tvPreferencesAvailable.getText().toString()).showDialog();
                break;
        }
    }

    public void setResultOfAvailableOn(String result) {
        tvPreferencesAvailable.setText(result);
    }

//    public void setResultOfBookingAvailabilityFor(String result) {
//        tvPreferencesBookingAvailableFor.setText(result);
//        if (result.equalsIgnoreCase(getResources().getString(R.string.both))) {
//            visiblebothViews();
//        } else if (result.equalsIgnoreCase(getResources().getString(R.string.hourly))) {
//            visibleHourlyView();
//        } else if (result.equalsIgnoreCase(getResources().getString(R.string.daily))) {
//            visibleDailyViews();
//        }
//    }

    void visibleDailyViews() {
        llEndTime.setVisibility(View.VISIBLE);
        llStartTime.setVisibility(View.VISIBLE);
        llHrsStartTime.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.VISIBLE);
    }

    void visibleHourlyView() {
        llEndTime.setVisibility(View.GONE);
        llStartTime.setVisibility(View.GONE);
        llHrsStartTime.setVisibility(View.VISIBLE);
        view4.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
    }

    void visiblebothViews() {
        llEndTime.setVisibility(View.VISIBLE);
        llStartTime.setVisibility(View.VISIBLE);
        llHrsStartTime.setVisibility(View.VISIBLE);
        view4.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.VISIBLE);
    }

    private void alertDiscountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_discount)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mPricingTul.discountPercentage = null;
                        mPricingTul.noBookings = null;
                        TulModel.setPricingTul(mPricingTul);

                        mDiscountChanged = true;
                        moveToNext();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == MOVENEXT) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else if (requestCode == DAYS) {
                tvPreferencesStartTime.setText(data.getStringExtra("selected_data"));
                tvPreferencesEndTime.setText(data.getStringExtra("selected_data") + " (NEXT DAY)");
            } else if (requestCode == HOURS) {
                tvHrsStartTime.setText(data.getStringExtra("selected_data"));
            } else if (requestCode == ENDTIMEHOURS) {
                tvHrsEndTime.setText(data.getStringExtra("selected_data"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();


        return aTime;
    }

    private void moveToNext() {
        mPreferencesTul.availbiltyMode = tvPreferencesAvailable.getText().toString();
        mPreferencesTul.startTime = tvPreferencesStartTime.getText().toString();
        mPreferencesTul.endTime = tvPreferencesEndTime.getText().toString();
        mPreferencesTul.hrsStartTime = tvHrsStartTime.getText().toString();
//      mPreferencesTul.hrsEndTime = tvHrsEndTime.getText().toString();
//      mPreferencesTul.bookingAvailbiltyFor = tvPreferencesBookingAvailableFor.getText().toString();

        TulModel.setPrefrencesTul(mPreferencesTul);


        if (mDiscountChanged && mPreferencesTul.isEdit) {
            Intent intent = new Intent(mContext, AddPriceActivity.class);
            startActivityForResult(intent, MOVENEXT);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else if (mPreferencesTul.isEdit) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent(mContext, AddPriceActivity.class);
            startActivityForResult(intent, MOVENEXT);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//            if (connectedToInternet())
//                getPrimaryDialog();
//            else
//                showAlert(llAvailable, errorInternet);
        }
    }

    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPreferencesTul.availbiltyMode = null;
                        mPreferencesTul.endTime = null;
                        mPreferencesTul.startTime = null;
                        mPreferencesTul.hrsStartTime = null;
//                      mPreferencesTul.hrsEndTime = null;
                        TulModel.setPrefrencesTul(mPreferencesTul);
                        mPricingTul.noBookings = null;
                        mPricingTul.discountPercentage = null;
                        TulModel.setPricingTul(mPricingTul);
                        moveBack();
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

    private void alertDayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_days_message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPreferencesTul.availbiltyMode = null;
                        mPreferencesTul.endTime = null;
                        mPreferencesTul.startTime = null;
                        mPreferencesTul.hrsStartTime = null;
//                        mPreferencesTul.hrsEndTime = null;
                        TulModel.setPrefrencesTul(mPreferencesTul);
                        moveBack();
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

    @Override
    public void onBackPressed() {
        mPreferencesTul.updateData = true;
        TulModel.setPrefrencesTul(mPreferencesTul);
        moveBack();
    }

    private void moveBack() {
        if (getIntent().hasExtra("BACKTOLANDING")) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void getPrimaryDialog() {
        progDailog = ProgressDialog.show(this, getString(R.string.Please_wait), getString(R.string.We_are_fetching_your_primary_account), true);
        progDailog.setCancelable(false);
        Call<CreateStripeAccountModel> call = RetrofitClient.getInstance()
                .getPrimaryAccount(utils.getString("access_token", ""));
        call.enqueue(new Callback<CreateStripeAccountModel>() {
            @Override
            public void onResponse(Call<CreateStripeAccountModel> call, Response<CreateStripeAccountModel> response) {

                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().getAccountId() == 0) {
                        Log.e("No Account = ", "Yes");
                        utils.setString("visible_primary", "no");
                        Intent intent = new Intent(mContext, AddBankDetailActivity.class);
                        startActivityForResult(intent, MOVENEXT);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        Log.e("No Account = ", "NO");
                        utils.setString("accountId", response.body().getResponse().getAccount());
                        TulModel.BankDetailsTul mBankDetailsTul = TulModel.getBankDetailsTul();
                        mBankDetailsTul.countryCode = response.body().getResponse().getCountry_code();
                        mBankDetailsTul.currency = response.body().getResponse().getCurrency();
                        mBankDetailsTul.accountNo = response.body().getResponse().getAccount_number();
                        mBankDetailsTul.sortCode = response.body().getResponse().getSort_code();
                        mBankDetailsTul.firstName = response.body().getResponse().getFirst_name();
                        mBankDetailsTul.lastName = response.body().getResponse().getLast_name();
                        mBankDetailsTul.address = response.body().getResponse().getAddress();
                        mBankDetailsTul.city = response.body().getResponse().getCity();
                        mBankDetailsTul.state = response.body().getResponse().getState();
                        mBankDetailsTul.postalCode = response.body().getResponse().getPostal_code();
                        mBankDetailsTul.dob = response.body().getResponse().getDob();
                        TulModel.setBankDetailsTul(mBankDetailsTul);
                        moveToList();
                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llAvailable, response.body().error.getMessage());
                    }
                }
                progDailog.dismiss();
            }

            @Override
            public void onFailure(Call<CreateStripeAccountModel> call, Throwable t) {
                showAlert(llAvailable, t.getLocalizedMessage());
                progDailog.dismiss();
            }
        });
    }

    private void moveToList() {
        utils.setString("visible_primary", "yes");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    void selectOptions(ArrayList<String> mData, int intentType, String selected) {
        Intent in = new Intent(mContext, OptionSelection.class);
        in.putExtra("selected", selected);
        in.putStringArrayListExtra("data", mData);
        startActivityForResult(in, intentType);
    }

    private ArrayList<String> getHours() {
        ArrayList<String> month = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                month.add("0" + i + ":00");
            } else {
                month.add(i + ":00");
            }
        }
        return month;
    }

    private ArrayList<String> getEndHours(int startTime) {
        ArrayList<String> month = new ArrayList<>();
        for (int i = startTime; i <= 23; i++) {
            if (i < 10) {
                month.add("0" + i + ":00");
            } else {
                month.add(i + ":00");
            }
        }
        if (startTime == 24) {
            month.add("00:00");
        }
        return month;
    }


}