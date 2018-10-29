package com.app.backyard;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.MaterialEditText;
import model.CreateStripeAccountModel;
import model.TulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class AddPriceActivity extends BaseActivity {

    private static final int MOVENEXT = 7;


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.tv_price_detail)
    TextView tvPriceDetail;

    @BindView(R.id.ll_inner)
    LinearLayout llInner;
    @BindView(R.id.ed_price_per_day)
    MaterialEditText edPricePerDay;
    @BindView(R.id.ed_price_per_hour)
    MaterialEditText edPricePerHour;
    @BindView(R.id.ed_security_charges)
    MaterialEditText edSecurityCharges;
    @BindView(R.id.ed_extra_fee)
    MaterialEditText edExtraFee;
    @BindView(R.id.ed_no_hours_discount)
    MaterialEditText edNoHoursDiscount;

    @BindView(R.id.ed_no_days_discount)
    MaterialEditText edNoDaysDiscount;
    @BindView(R.id.ed_discount_percentage)
    MaterialEditText edDiscountPercentage;
    @BindView(R.id.ed_your_earning)
    MaterialEditText edYourEarning;
    @BindView(R.id.img_your_earning_info)
    ImageView imgYourEarningInfo;
    @BindView(R.id.img_extra_earning_info)
    ImageView imgExtraEarningInfo;
    @BindView(R.id.ed_extra_earning)
    MaterialEditText edYourExtraEarning;

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view_days)
    View viewDays;
    @BindView(R.id.view_hours)
    View viewHours;

    @BindView(R.id.ll_price_buttons_container)
    LinearLayout llBtContainer;

    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_discard)
    Button btDiscard;

    ProgressDialog progDailog;

    double mTransactionPercentage;
    int maxDiscountDays;

    TulModel.PricingTul mPricingTul;
    TulModel.PreferencesTul mPreferencesTul;

    public static String amountConversion(String am) {
        String amount;
        Double d = Double.parseDouble(am);
        Locale locale = new Locale("en", "UK");
        String pattern = "#0.00";

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        amount = decimalFormat.format(d);
        return amount;

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_price;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtToolbarTitle.setText("PRICING");

        llInner.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");

        tvPriceDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPriceDetail.setPadding(mWidth / 16, 0, mWidth / 15, mWidth / 32);

        edPricePerDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edPricePerDay.setTypeface(typeface);

        edPricePerHour.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edPricePerHour.setTypeface(typeface);

        edYourEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edYourEarning.setTypeface(typeface);

        edSecurityCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edSecurityCharges.setTypeface(typeface);

        edExtraFee.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edExtraFee.setTypeface(typeface);

        edYourExtraEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edYourExtraEarning.setTypeface(typeface);

        edNoDaysDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edNoDaysDiscount.setTypeface(typeface);

        edNoHoursDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edNoHoursDiscount.setTypeface(typeface);

        edDiscountPercentage.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edDiscountPercentage.setTypeface(typeface);

        btDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btDiscard.setPadding(0, mWidth / 32, 0, mWidth / 32);

        btSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        btSave.setPadding(0, mWidth / 32, 0, mWidth / 32);

        btDiscard.setTypeface(typeface);
        btSave.setTypeface(typeface);

        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llBtContainer.setLayoutParams(param1);
    }


    @Override
    protected void onCreateStuff() {

        mPricingTul = TulModel.getPricingTul();
        mPreferencesTul = TulModel.getPrefrencesTul();

        mTransactionPercentage = Constants.TRANSACTION_PERCENTAGE;
        setData();


//        final Drawable img = ContextCompat.getDrawable(mContext, R.drawable.pound);
//        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));

        final Drawable img;
        if (utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")).equals(Constants.POUND)) {
            img = ContextCompat.getDrawable(mContext, R.drawable.pound);
        } else {
            img = ContextCompat.getDrawable(mContext, R.drawable.euro);
        }

        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));

        edYourEarning.setCompoundDrawables(img, null, null, null);

        edPricePerDay.setCompoundDrawables(img, null, null, null);
        edPricePerHour.setCompoundDrawables(img, null, null, null);
        edYourEarning.setCompoundDrawables(img, null, null, null);
        edSecurityCharges.setCompoundDrawables(img, null, null, null);
        edExtraFee.setCompoundDrawables(img, null, null, null);
        edYourExtraEarning.setCompoundDrawables(img, null, null, null);
    }

    @Override
    protected void initListener() {
        btSave.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btDiscard.setOnClickListener(this);

        imgExtraEarningInfo.setOnClickListener(this);
        imgYourEarningInfo.setOnClickListener(this);

        edYourEarning.setEnabled(false);
        edYourExtraEarning.setEnabled(false);
        imgYourEarningInfo.setEnabled(false);
        imgExtraEarningInfo.setEnabled(false);

        edPricePerDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edPricePerDay.setText(s);
                    edPricePerDay.setSelection(s.length());
                }
                if (s.length() > 0) {
                    imgYourEarningInfo.setEnabled(true);

                    double i = Double.parseDouble(String.valueOf(s));
                    i = i - ((mTransactionPercentage * i) / 100);
                    edYourEarning.setText(amountConversion(String.valueOf(i)));
                } else {
                    edYourEarning.setText("");
                    imgYourEarningInfo.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edPricePerHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edPricePerHour.setText(s);
                    edPricePerHour.setSelection(s.length());
                }
                if (s.length() > 0) {
                    imgYourEarningInfo.setEnabled(true);
                    double i = Double.parseDouble(String.valueOf(s));
                    i = i - ((mTransactionPercentage * i) / 100);
                    edYourEarning.setText(amountConversion(String.valueOf(i)));
                } else {
                    edYourEarning.setText("");
                    imgYourEarningInfo.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edExtraFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edExtraFee.setText(s);
                    edExtraFee.setSelection(s.length());
                }
                if (s.length() > 0) {
                    imgExtraEarningInfo.setEnabled(true);
                    double i = Double.parseDouble(String.valueOf(s));
                    i = i - ((mTransactionPercentage * i) / 100);
                    edYourExtraEarning.setText(amountConversion(String.valueOf( i)));
                } else {
                    edYourExtraEarning.setText("");
                    imgExtraEarningInfo.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edDiscountPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edDiscountPercentage.setText(s);
                    edDiscountPercentage.setSelection(s.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edSecurityCharges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edSecurityCharges.setText(s);
                    edSecurityCharges.setSelection(s.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        if (mPricingTul.pricePerHour != null || mPricingTul.pricePerDay != null) {

            edPricePerHour.setText(mPricingTul.pricePerHour);
            edPricePerDay.setText(mPricingTul.pricePerDay);
            edSecurityCharges.setText(mPricingTul.securityCharges);

            edNoDaysDiscount.setText(mPricingTul.noBookings);

            edNoHoursDiscount.setText(mPricingTul.noBookings);

            edExtraFee.setText(mPricingTul.extraFee);
            edYourEarning.setText(mPricingTul.yourEarning);
            edYourExtraEarning.setText(mPricingTul.yourExtraEarning);
            edDiscountPercentage.setText(mPricingTul.discountPercentage);

        }
        if (mPreferencesTul.bookingAvailbiltyFor != null) {
            setView();
        }


    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_discard:
                alertDiscardDialog();
                break;
            case R.id.bt_save:

                if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
                    if (TextUtils.isEmpty(edPricePerDay.getText().toString()) || edPricePerDay.getText().toString().equals(".")) {
                        showAlert(llInner, getString(R.string.error_price_per_day));
                        return;
                    } else if (Double.parseDouble(edPricePerDay.getText().toString().trim()) < Constants.MIN_PRICE) {
                        showAlert(llInner, getString(R.string.min_price));
                        return;
                    } else if (Double.parseDouble(edPricePerDay.getText().toString().trim()) > Constants.MAX_PRICE) {
                        showAlert(llInner, getString(R.string.error_max_day_price));
                        return;
                    } else if (!TextUtils.isEmpty(edNoDaysDiscount.getText().toString()) || !TextUtils.isEmpty(edDiscountPercentage.getText().toString())) {
//                        if (!mPricingTul.isEdit) {
                        if (TextUtils.isEmpty(edNoDaysDiscount.getText().toString())) {
                            showAlert(llBtContainer, getString(R.string.Enter_days_for_discount));
                            return;
                        } else if (Double.parseDouble(edNoDaysDiscount.getText().toString().trim()) < 2) {
                            showAlert(llBtContainer, getString(R.string.Number_of_Days_for_Discount_should_be_more_than_1));
                            return;
                        } else if (TextUtils.isEmpty(edDiscountPercentage.getText().toString()) || edDiscountPercentage.getText().toString().equals(".")) {
                            showAlert(llBtContainer, getString(R.string.error_discount_percentage));
                            return;
                        } else if (Double.parseDouble(edDiscountPercentage.getText().toString().trim()) < 0.1) {
                            showAlert(llBtContainer, getString(R.string.Discount_should_be_greater_than_0_one));
                            return;
                        } else if (Double.parseDouble(edDiscountPercentage.getText().toString().trim()) > Constants.MAX_DISCOUNT) {
                            showAlert(llBtContainer, getString(R.string.error_max_discount));
                            return;
                        }
//                        }
                    }
                } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
                    if (TextUtils.isEmpty(edPricePerHour.getText().toString()) || edPricePerHour.getText().toString().equals(".")) {
                        showAlert(llBtContainer, getString(R.string.error_price_per_hour));
                        return;
                    } else if (Double.parseDouble(edPricePerHour.getText().toString().trim()) < Constants.MIN_PRICE) {
                        showAlert(llInner, getString(R.string.min_price));
                        return;
                    } else if (Double.parseDouble(edPricePerHour.getText().toString().trim()) > Constants.MAX_PRICE) {
                        showAlert(llInner, getString(R.string.error_max_hourly_price));
                        return;
                    } else if (!TextUtils.isEmpty(edNoHoursDiscount.getText().toString()) || !TextUtils.isEmpty(edDiscountPercentage.getText().toString())) {
//                        if (!mPricingTul.isEdit) {
                        if (TextUtils.isEmpty(edNoHoursDiscount.getText().toString())) {
                            showAlert(llBtContainer, getString(R.string.Enter_hours_for_discount));
                            return;
                        } else if (Double.parseDouble(edNoHoursDiscount.getText().toString().trim()) < 2) {
                            showAlert(llBtContainer, getString(R.string.Number_of_hours_for_Discount_should_be_more_than_1));
                            return;
                        } else if (Double.parseDouble(edNoHoursDiscount.getText().toString().trim()) > maxDiscountDays) {
                            showAlert(llBtContainer, getString(R.string.Number_of_hours_for_Discount_should_be_less_than) + (maxDiscountDays + 1));
                            return;
                        } else if (TextUtils.isEmpty(edDiscountPercentage.getText().toString()) || edDiscountPercentage.getText().toString().equals(".")) {
                            showAlert(llBtContainer, getString(R.string.error_discount_percentage));
                            return;
                        } else if (Double.parseDouble(edDiscountPercentage.getText().toString().trim()) < 0.1) {
                            showAlert(llBtContainer, getString(R.string.Discount_should_be_greater_than_0));
                            return;
                        } else if (Double.parseDouble(edDiscountPercentage.getText().toString().trim()) > Constants.MAX_DISCOUNT) {
                            showAlert(llBtContainer, getString(R.string.error_max_discount));
                            return;
                        }
//                        }
                    }
                } else if (Double.parseDouble(edSecurityCharges.getText().toString().trim()) > Constants.MAX_SECURITY_CHARGES) {
                    showAlert(llInner, getString(R.string.error_max_security_price));
                    return;
                } else if (TextUtils.isEmpty(edExtraFee.getText().toString())) {
                    showAlert(llBtContainer, getString(R.string.error_extra_fees));
                    return;
                } else if (Double.parseDouble(edExtraFee.getText().toString().trim()) > 999) {
                    showAlert(llBtContainer, getString(R.string.Extra_free_should_be_less_than_999));
                    return;
                } else if (edExtraFee.getText().toString().equals(".")) {
                    showAlert(llInner, getString(R.string.Enter_valid_extra_fee));
                    return;
                }
                moveToNext();
                break;
            case R.id.img_back:
                moveBack();
                break;
            case R.id.img_extra_earning_info:
                PopupWindow popupwindow_obj = popupDisplay("extra fee");
                popupwindow_obj.showAsDropDown(imgExtraEarningInfo, -40, -10);
                break;
            case R.id.img_your_earning_info:
//                if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
                PopupWindow popupwindow_obj2 = popupDisplay("price");
                popupwindow_obj2.showAsDropDown(imgYourEarningInfo, -40, -10);
//
//                } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
//                    PopupWindow popupwindow_obj2 = popupDisplay(edPricePerHour.getText().toString());
//                    popupwindow_obj2.showAsDropDown(imgYourEarningInfo, -40, -10);
//
//                }
                break;
        }
    }

    void setView() {
        if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
            edPricePerHour.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            edNoHoursDiscount.setVisibility(View.GONE);
            viewHours.setVisibility(View.GONE);

            edNoDaysDiscount.setFloatingLabelText(getString(R.string.NO_OF_DAYS_FOR_DISCOUNT));
            edNoDaysDiscount.setHint(getString(R.string.NO_OF_DAYS_FOR_DISCOUNT));
        } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
            edPricePerDay.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            edNoDaysDiscount.setVisibility(View.GONE);
            viewDays.setVisibility(View.GONE);

            edNoHoursDiscount.setFloatingLabelText(getString(R.string.NO_OF_HOURS_FOR_DISCOUNT));
            edNoHoursDiscount.setHint(getString(R.string.NO_OF_HOURS_FOR_DISCOUNT));

            String[] t = mPreferencesTul.hrsStartTime.split(":");
            maxDiscountDays = 24 - Integer.parseInt(t[0].trim());


        }
    }


    private void moveToNext() {
        mPricingTul.pricePerDay = edPricePerDay.getText().toString();
        mPricingTul.pricePerHour = edPricePerHour.getText().toString();
        mPricingTul.securityCharges = edSecurityCharges.getText().toString();
        mPricingTul.yourEarning = edYourEarning.getText().toString();
        mPricingTul.extraFee = edExtraFee.getText().toString();

        if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
            mPricingTul.noBookings = edNoDaysDiscount.getText().toString();
        } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
            mPricingTul.noBookings = edNoHoursDiscount.getText().toString();
        }

        mPricingTul.yourExtraEarning = edYourExtraEarning.getText().toString();
        mPricingTul.discountPercentage = edDiscountPercentage.getText().toString();

        TulModel.setPricingTul(mPricingTul);

        if (mPricingTul.isEdit) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            if (connectedToInternet())
                getPrimaryDialog();
            else
                showAlert(llInner, errorInternet);
        }
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

                        mBankDetailsTul.swift = response.body().getResponse().getSwift();
                        mBankDetailsTul.account_type = response.body().getResponse().getAccount_type();

                        TulModel.setBankDetailsTul(mBankDetailsTul);
                        moveToList();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llBtContainer, response.body().error.getMessage());
                    }
                }
                progDailog.dismiss();
            }

            @Override
            public void onFailure(Call<CreateStripeAccountModel> call, Throwable t) {
                showAlert(llBtContainer, t.getLocalizedMessage());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == MOVENEXT) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPricingTul.pricePerDay = null;
                        mPricingTul.pricePerHour = null;
                        mPricingTul.yourEarning = null;
                        mPricingTul.discountPercentage = null;
                        mPricingTul.extraFee = null;
                        mPricingTul.securityCharges = null;

                        mPricingTul.yourExtraEarning = null;
                        mPricingTul.noBookings = null;

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    private void infoDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
//    public void showDialog(Context context, int x, int y) {
    // x -->  X-Cordinate
    // y -->  Y-Cordinate
//        Dialog dialog  = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.info_dialog_layout);
//        dialog.setCanceledOnTouchOutside(true);
//
//        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
//        wmlp.x = (int) x;
//        wmlp.y = (int) y;
//
//        dialog.show();

// where u want show on
//view click event popupwindow.showAsDropDown(view, x, y);


//    }

    @SuppressLint("SetTextI18n")
    public PopupWindow popupDisplay(String price) {

        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_dialog_layout, null);

        TextView textView = (TextView) view.findViewById(R.id.txt_info);

        textView.setText(getString(R.string.This_is_the_total)+" "+price+" "+getString(R.string.you_will_earn_after_deduction_of)+" "+Constants.TRANSACTION_PERCENTAGE +getString(R.string.percent_fee_by_BACK_YARD));

        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        return popupWindow;
    }


}
