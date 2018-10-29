package com.app.backyard;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.JsonObject;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapters.CheckoutCardAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.BookTulModel;
import model.CardLocalModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.CryptLib;
import utils.GpsStatusDetector;

public class TulCheckOutDetailsActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        GpsStatusDetector.GpsStatusDetectorCallBack, OnMapReadyCallback {

    private static final int PAUSED = 2;
    final int LOCATION_CHECK = 1;
    public EditText edCVV;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.ll_delivery_address)
    LinearLayout llDeliveryAddress;
    @BindView(R.id.txt_address_mode)
    TextView txtAddressMode;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.tv_summery_title)
    TextView tvSummeryTitle;
    @BindView(R.id.ll_delivery)
    LinearLayout llDelivery;
    @BindView(R.id.ll_dots)
    LinearLayout llDots;
    @BindView(R.id.img_dot1)
    ImageView imgDot1;
    @BindView(R.id.view_line1)
    View line;
    @BindView(R.id.img_dot2)
    ImageView imgDot2;
    @BindView(R.id.ll_delivery_data)
    LinearLayout llDeliveryData;
    @BindView(R.id.tv_delivery_title)
    TextView tvDeliveryTitle;
    @BindView(R.id.tv_delivery_date)
    TextView tvDeliveryDate;
    @BindView(R.id.tv_return_title)
    TextView tvReturnTitle;
    @BindView(R.id.tv_return_date)
    TextView tvReturnDate;
    @BindView(R.id.tv_tul_title)
    TextView tvTutTitle;
    @BindView(R.id.ll_tul_charges)
    LinearLayout llTutlCharges;
    @BindView(R.id.tv_tul_charges_title)
    TextView tvTulChargesTitle;
    @BindView(R.id.tv_tul_charges)
    TextView tvTulCharges;
    @BindView(R.id.ll_security_charges)
    LinearLayout llSecurityCharges;
    @BindView(R.id.tv_security_charges_title)
    TextView tvSecurityChargesTitle;
    @BindView(R.id.tv_security_charges)
    TextView tvSecurityCharges;
    @BindView(R.id.ll_extra_charges)
    LinearLayout llExtraCharges;
    @BindView(R.id.tv_extra_charges_title)
    TextView tvExtraChargesTitle;
    @BindView(R.id.tv_extra_charges)
    TextView tvExtraCharges;
    @BindView(R.id.ll_delivery_charges)
    LinearLayout llDiscount;
    @BindView(R.id.tv_delivery_charges_title)
    TextView tvDiscountTitle;
    @BindView(R.id.tv_delivery_charges)
    TextView tvDiscount;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.ll_total_amount)
    LinearLayout llTotalAmount;
    @BindView(R.id.tv_total_amount_title)
    TextView tvTotalAmountTitle;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tv_checkout_title)
    TextView tvCheckoutTitle;
    @BindView(R.id.rv_card_details)
    RecyclerView rvCardDetails;
    @BindView(R.id.tv_add_new_card)
    TextView tvAddNewCard;
    @BindView(R.id.tv_proceed_to_pay)
    TextView tvProceedToPay;
    ViewTulModel.ResponseBean.AdditionalPriceBean mViewTulAdditional;
    ViewTulModel.ResponseBean mViewTulModel;
    ArrayList<CardLocalModel.ResponseBean> mArrayListCard = new ArrayList<>();
    CheckoutCardAdapter mAdapter;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    CardLocalModel.ResponseBean cardDetails;
    double mExtraCharges = 0.00, mDiscountPrice = 0.00, mTransactionCharges = 0.00, mTransactionPercentage = 0.00;
//    String mBufferAvailability;
    private ArrayList<String> mSelectedDates = new ArrayList<>();
    private double mTotalPrice;
    private double totalAmount;
    private int mDeliveryMode;
    private String mStartTime;
    private String mQuantity;
    private String mEndTime, mCheckOutTime, mCheckInTime;
    private double mLatitude, mDeliveryLatitude;
    private double mLongitude, mDeliveryLongitude;
    private String mAddress, mAddressDelivery;
    private int LOCATION = 3;
    private GpsStatusDetector mGpsStatusDetector;
    private boolean isAddressSelected;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_check_out_details;
    }

    @Override
    protected void initUI() {
        edCVV = new EditText(this);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.048));
        txtToolbarTitle.setText(R.string.SUMMARY_CHECKOUT);

        llDeliveryAddress.setPadding(mWidth / 24, 0, mWidth / 24, mHeight / 32);

        tvAddAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvAddAddress.setPadding(mWidth / 24, mWidth / 52, mWidth / 24, mWidth / 52);

        tvSummeryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.048));
        tvSummeryTitle.setPadding(0, mHeight / 32, 0, mHeight / 32);

        llDelivery.setPadding(mWidth / 24, 0, mWidth / 24, mHeight / 32);

        llDots.setPadding(0, mWidth / 76, 0, 0);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mWidth / 46, mWidth / 46);
        imgDot1.setLayoutParams(layoutParams);
        imgDot2.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(2, mHeight / 17);
        layoutParams1.gravity = Gravity.CENTER;
        line.setLayoutParams(layoutParams1);

        llDeliveryData.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        LinearLayout.LayoutParams layoutParamsView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        layoutParamsView.setMargins(mWidth / 24, 0, mWidth / 24, 0);

        view1.setLayoutParams(layoutParamsView);
        view2.setLayoutParams(layoutParamsView);


        tvDeliveryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvDeliveryDate.setPadding(0, 0, 0, mHeight / 42);
        tvDeliveryDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

        tvReturnTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvReturnDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

        imgLocation.setPadding(0, mHeight / 64, mWidth / 64, mHeight / 64);

        txtAddressMode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        txtAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        txtAddress.setPadding(0, mHeight / 64, 0, mHeight / 32);

        tvTutTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvTutTitle.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        tvTulChargesTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvTulChargesTitle.setPadding(mWidth / 24, mHeight / 32, 0, mHeight / 32);

        tvTulCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvTulCharges.setPadding(0, mHeight / 32, mWidth / 24, mHeight / 32);

        tvSecurityChargesTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvSecurityChargesTitle.setPadding(mWidth / 24, 0, 0, mHeight / 32);

        tvSecurityCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvSecurityCharges.setPadding(0, 0, mWidth / 24, mHeight / 32);

        tvExtraChargesTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvExtraChargesTitle.setPadding(mWidth / 24, 0, 0, mHeight / 32);

        tvExtraCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvExtraCharges.setPadding(0, 0, mWidth / 24, mHeight / 32);

        tvDiscountTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvDiscountTitle.setPadding(mWidth / 24, 0, 0, mHeight / 32);

        tvDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvDiscount.setPadding(0, 0, mWidth / 24, mHeight / 32);

        tvTotalAmountTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvTotalAmountTitle.setPadding(mWidth / 24, mHeight / 32, 0, mHeight / 32);

        tvTotalAmount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.036));
        tvTotalAmount.setPadding(0, mHeight / 32, mWidth / 24, mHeight / 32);

        tvCheckoutTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.048));
        tvCheckoutTitle.setPadding(0, mHeight / 32, 0, mHeight / 32);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(mWidth / 24, mHeight / 52, mWidth / 24, mHeight / 52);

        tvAddNewCard.setLayoutParams(layoutParams2);
        tvAddNewCard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvAddNewCard.setPadding(mWidth / 24, mWidth / 52, mWidth / 24, mWidth / 52);

        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams3.setMargins(mWidth / 24, mHeight / 52, mWidth / 24, mHeight / 52);

        tvProceedToPay.setLayoutParams(layoutParams3);
        tvProceedToPay.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvProceedToPay.setPadding(0, mHeight / 46, 0, mHeight / 46);
        rvCardDetails.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        rvCardDetails.setNestedScrollingEnabled(false);
        rvCardDetails.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    protected void onCreateStuff() {
        mViewTulAdditional = getIntent().getParcelableExtra("data_price");
        mViewTulModel = getIntent().getParcelableExtra("tul_data");
        mSelectedDates = getIntent().getStringArrayListExtra("selected_dates");
        mTotalPrice = getIntent().getDoubleExtra("tul_price", 0);
        mDeliveryMode = getIntent().getIntExtra("delivery_mode", 0);
        mStartTime = getIntent().getStringExtra("start_time");
        mEndTime = getIntent().getStringExtra("end_time");
        mQuantity = getIntent().getStringExtra("quantity");
        mCheckInTime = getIntent().getStringExtra("check_in_time");
        mCheckOutTime = getIntent().getStringExtra("check_out_time");


        if (mViewTulModel.getProduct_type() == 0) {
            mQuantity = String.valueOf(mSelectedDates.size());
        }

        setData();

        svMain.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        svMain.setFocusable(true);
        svMain.setFocusableInTouchMode(true);
        svMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    @Override
    protected void initListener() {
        tvAddNewCard.setOnClickListener(this);
        tvAddAddress.setOnClickListener(this);
        llLocation.setOnClickListener(this);
        tvProceedToPay.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulCheckOutDetailsActivity.this;
    }


    public void setData() {
        double extraCharges = 0;
        mArrayListCard.clear();
        mArrayListCard.addAll(db.getAllCards());
        if (mArrayListCard.size() > 0)
            mArrayListCard.get(0).setIs_selected(true);
        mAdapter = new CheckoutCardAdapter(this, mArrayListCard);
        rvCardDetails.setAdapter(mAdapter);
        try {
            if (mViewTulModel.getProduct_type() == 1) {
                tvDeliveryDate.setText(Constants.convertSelectdDate(mSelectedDates.get(0)) + " (" + mCheckInTime.trim() + ")");
                tvReturnDate.setText(Constants.convertSelectdDate(mSelectedDates.get(0)) + " (" + mCheckOutTime.trim() + ")");
            } else {
                tvDeliveryDate.setText(Constants.convertSelectdDate(mSelectedDates.get(0)));
                if (mSelectedDates.size() > 1)
                    tvReturnDate.setText(addOneDayToDate(mSelectedDates.get(mSelectedDates.size() - 1)));
                else
                    tvReturnDate.setText(addOneDayToDate(mSelectedDates.get(0)));
            }
        } catch (Exception e) {
            Log.e("Date Exce  = ", e + "");
        }
        tvTutTitle.setText(getIntent().getStringExtra("tool_title"));
        displayPrices();

    }


    private void displayPrices() {

        if (mViewTulModel.getProduct_type() == 1) {
            tvTulChargesTitle.setText(getString(R.string.Place_Charges_per_hour_x) + mQuantity +" " +getString(R.string.hours));
        } else {
            if (mSelectedDates.size() == 1)
                tvTulChargesTitle.setText(getString(R.string.Place_Charges_per_day_x) + mSelectedDates.size()+" " +getString(R.string.days));
            else
                tvTulChargesTitle.setText(getString(R.string.Place_Charges_per_day_x) + mSelectedDates.size() +" " +getString(R.string.days));
        }

        mTransactionPercentage = Double.parseDouble(mViewTulModel.getTransaction_percentage());

        if (getIntent().getIntExtra("show_discount", 0) == 1) {
            llDiscount.setVisibility(View.VISIBLE);
            double discountPercentage = Double.parseDouble(mViewTulModel.getDiscount_percentage());
            mDiscountPrice = (mTotalPrice * discountPercentage) / 100;
            tvDiscount.setText(mViewTulModel.getCurrency() + String.format("%.2f", mDiscountPrice));
        } else {
            llDiscount.setVisibility(View.GONE);
        }

        tvTulCharges.setText(mViewTulModel.getCurrency()+ String.format("%.2f", mTotalPrice));

        mTransactionCharges = (mTotalPrice * mTransactionPercentage) / 100;

        tvSecurityCharges.setText(mViewTulModel.getCurrency() +
                String.format("%.2f", Double.parseDouble(mViewTulAdditional.getSecurity_charges())));

        if (!TextUtils.isEmpty(mViewTulAdditional.getFee()) && !mViewTulAdditional.getFee().equals("0.00")) {
            mExtraCharges = Double.parseDouble(mViewTulAdditional.getFee());
            tvExtraCharges.setText(mViewTulModel.getCurrency() + String.format("%.2f", mExtraCharges));
            mTransactionCharges = mTransactionCharges + (mExtraCharges * mTransactionPercentage) / 100;

        } else {
            mExtraCharges = 0.00;
            tvExtraCharges.setText(mViewTulModel.getCurrency()+ "0.00");
        }
        Log.e("mTransactionCharges", mTransactionCharges + "");

        if (llDiscount.getVisibility() == View.GONE)
            totalAmount = mTotalPrice + Double.parseDouble(mViewTulAdditional.getSecurity_charges()) + mExtraCharges;
        else
            totalAmount = mTotalPrice - mDiscountPrice + Double.parseDouble(mViewTulAdditional.getSecurity_charges()) + mExtraCharges;

        tvTotalAmount.setText(mViewTulModel.getCurrency() +String.format("%.2f", totalAmount));
    }

//    private void shubhamCode() {
//        if (mViewTulModel.getProduct_type() == 1) {
//            tvTulChargesTitle.setText("Backyard Charges (per hour) x " + mQuantity + " hours");
//        } else {
//            if (mSelectedDates.size() == 1)
//                tvTulChargesTitle.setText("Backyard Charges (per day) x " + mSelectedDates.size() + " day");
//            else
//                tvTulChargesTitle.setText("Backyard Charges (per day) x " + mSelectedDates.size() + " days");
//        }
//        tvTulCharges.setText(Constants.POUND + " " +
//                String.format("%.2f", mTotalPrice));
//        if(TextUtils.isEmpty(mViewTulAdditional.getSecurity_charges())){
//            mViewTulAdditional.setSecurity_charges("0.00");
//        }
//        tvSecurityCharges.setText(Constants.POUND + " " +
//                String.format("%.2f", Double.parseDouble(mViewTulAdditional.getSecurity_charges())));
//
//        if (!TextUtils.isEmpty(mViewTulAdditional.getFee())) {
//            tvExtraCharges.setText(Constants.POUND + " " +
//                    String.format("%.2f", Double.parseDouble(mViewTulAdditional.getFee())));
//            extraCharges = Double.parseDouble(mViewTulAdditional.getFee());
//        } else {
//            tvExtraCharges.setText(Constants.POUND + " 0.0");
//        }
//        mDiscountPrice = 0.0;
//
//        if (!TextUtils.isEmpty(String.valueOf(mViewTulModel.getDiscount_days())) && !TextUtils.isEmpty(mViewTulModel.getDiscount_percentage()) && Integer.parseInt(mQuantity) >= mViewTulModel.getDiscount_days()) {
//            mDiscountPrice = (mTotalPrice * Float.parseFloat(mViewTulModel.getDiscount_percentage())) / 100;
//            tvDiscount.setText(Constants.POUND +" "+ mDiscountPrice);
//            totalAmount = mTotalPrice - mDiscountPrice + Double.parseDouble(mViewTulAdditional.getSecurity_charges()) + extraCharges;
//            tvTotalAmount.setText(Constants.POUND + " " + String.format("%.2f", totalAmount));
//        } else {
//            tvDiscount.setText(Constants.POUND + " " + mDiscountPrice);
//            totalAmount = mTotalPrice + Double.parseDouble(mViewTulAdditional.getSecurity_charges()) + extraCharges;
//            tvTotalAmount.setText(Constants.POUND + " " + String.format("%.2f", totalAmount));
//        }
//    }

    private String addOneDayToDate(String inputDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date = dateFormat.parse(inputDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            String formatted = format1.format(calendar.getTime());
            return formatted;
        } catch (Exception e) {
            Log.e("Add Day Exce = ", e + "");
        }
        return "";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
            case R.id.tv_add_new_card:
                Intent card = new Intent(mContext, AddPaymentCardActivity.class);
                card.putExtra("selected_dates", mSelectedDates);
                card.putExtra("quantity", getIntent().getStringExtra("quantity"));
                card.putExtra("delivery_mode", mDeliveryMode);
                card.putExtra("start_time", mStartTime);
                card.putExtra("end_time", mEndTime);
                card.putExtra("extra_charges", String.valueOf(mExtraCharges));
                card.putExtra("transaction_fee", String.valueOf(mTransactionCharges));
                card.putExtra("checkout", true);
                card.putExtra("tul_data", mViewTulModel);
                card.putExtra("address", mAddressDelivery);
                card.putExtra("latitude", String.valueOf(mDeliveryLatitude));
                card.putExtra("longitude", String.valueOf(mDeliveryLongitude));
                card.putExtra("check_in_time", mCheckInTime.trim());
                card.putExtra("check_out_time", mCheckOutTime.trim());
                card.putExtra("quantity", mQuantity);
                card.putExtra("buffer_available",getIntent().getStringExtra("buffer_available"));
                card.putExtra("tul_price", String.valueOf(totalAmount));
                card.putExtra("base_price", String.valueOf(mTotalPrice));
                card.putExtra("discount_price", String.valueOf(mDiscountPrice));


                startActivityForResult(card, PAUSED);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_add_address:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, LOCATION);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_proceed_to_pay:
                if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 1) {
                    for (CardLocalModel.ResponseBean cardItem : mArrayListCard) {
                        if (cardItem.is_selected()) {
                            cardDetails = cardItem;
                            break;
                        }
                    }
                    if (cardDetails == null)
                        showAlert(llDelivery, "Please add card details");
                    else if (TextUtils.isEmpty(edCVV.getText().toString().trim()))
                        showAlert(llDelivery, "Please enter CVV number");
                    else {
                        if (connectedToInternet()) {
                            String cardNo = "";
                            try {
                                CryptLib cryptLib = new CryptLib();
                                cardNo = cryptLib.decryptSimple(cardDetails.getCard_number(), Constants.KEY, Constants.IV);
                            } catch (Exception e) {
                                Log.e("Exce  = ", e + "");
                            }
                            Card mStripeCard = new Card(cardNo.replaceAll(" ", ""), cardDetails.getExpiry_month(),
                                    cardDetails.getExpiry_year(), edCVV.getText().toString());
                            if (!mStripeCard.validateCVC()) {
                                showAlert(llDelivery, "Invalid cvv number");
                            } else if (!mStripeCard.validateCard()) {
                                showAlert(llDelivery, "Invalid card");
                            } else {
                                hitStripeAPI(mStripeCard);
                            }
                        } else {
                            showInternetAlert(llDelivery);
                        }
                    }
                }else {
                    verifyEmailDialog();

                }

                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();
    }

    private void verifyEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.verify_email_2)
                .setCancelable(false)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for (CardLocalModel.ResponseBean cardItem : mArrayListCard) {
                            if (cardItem.is_selected()) {
                                cardDetails = cardItem;
                                break;
                            }
                        }
                        if (cardDetails == null)
                            showAlert(llDelivery, getString(R.string.Please_add_card_details));
                        else if (TextUtils.isEmpty(edCVV.getText().toString().trim()))
                            showAlert(llDelivery, getString(R.string.Please_enter_CVV_number));
                        else {
                            if (connectedToInternet()) {
                                String cardNo = "";
                                try {
                                    CryptLib cryptLib = new CryptLib();
                                    cardNo = cryptLib.decryptSimple(cardDetails.getCard_number(), Constants.KEY, Constants.IV);
                                } catch (Exception e) {
                                    Log.e("Exce  = ", e + "");
                                }
                                Card mStripeCard = new Card(cardNo.replaceAll(" ", ""), cardDetails.getExpiry_month(),
                                        cardDetails.getExpiry_year(), edCVV.getText().toString());
                                if (!mStripeCard.validateCVC()) {
                                    showAlert(llDelivery, getString(R.string.Invalid_cvv_number));
                                } else if (!mStripeCard.validateCard()) {
                                    showAlert(llDelivery, getString(R.string.Invalid_card));
                                } else {
                                    hitStripeAPI(mStripeCard);
                                }
                            } else {
                                showInternetAlert(llDelivery);
                            }
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CHECK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        onCreateStuff();
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(llDelivery, getString(R.string.Sorry_unable_to_fetch_your_location));
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getSubLocality() + " , " + addresses.get(0).getSubAdminArea();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            txtAddress.setText(address + "," + city + "," + state + "," + country);
            mLatitude = latitude;
            mLongitude = longitude;
            mDeliveryLatitude = latitude;
            mDeliveryLongitude = longitude;
            mAddress = address + "," + city + "," + state + "," + country;
            mAddressDelivery = address + "," + city + "," + state + "," + country;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == LOCATION) {
                int dis = (int) Math.round(distance(mLatitude, mLongitude, mDeliveryLatitude, mDeliveryLongitude));
                if (dis > 15) {
                    alertDistanceDialog();
                } else {
                    isAddressSelected = true;
                    txtAddress.setText(data.getStringExtra("address"));
                    mAddressDelivery = data.getStringExtra("address");
                    mDeliveryLatitude = Double.parseDouble(data.getStringExtra("latitude"));
                    mDeliveryLongitude = Double.parseDouble(data.getStringExtra("longitude"));
                }
            } else if (requestCode == PAUSED) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (mGpsStatusDetector != null)
                    mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onGpsSettingStatus(boolean enabled) {
        if (enabled)
            buildGoogleApiClient();
    }

    @Override
    public void onGpsAlertCanceledByUser() {

    }

    @Override
    public void showLocationScreen() {

    }


    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double dist;
        Location locationA = new Location("Location A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);

        Location locationB = new Location("Location B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lon2);
        dist = locationA.distanceTo(locationB) / 1000;
        return (dist);
    }


    private void alertDistanceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Your_selected_location_is_not_in_range_Please_choose_another_address)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void hitStripeAPI(Card mStripeCard) {
        showProgress();
        Stripe stripe = null;
        try {
            stripe = new Stripe(Constants.STRIPE_LIVE_KEY);
            stripe.createToken(mStripeCard, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    showAlert(llDelivery, error.getLocalizedMessage());
                    hideProgress();
                }

                @Override
                public void onSuccess(Token token) {
                    Log.e("stripe token", "is " + token);
                    hitBookAPI(token.getId());
                }
            });
        } catch (AuthenticationException e) {
            showAlert(llDelivery, e.getLocalizedMessage());
        }
    }


    private void hitBookAPI(String token) {
        String deliverydate, returndate;
        deliverydate = mSelectedDates.get(0) + " " + mStartTime;

        if (mSelectedDates.size() > 0) {
            returndate = mSelectedDates.get(mSelectedDates.size() - 1) + " " + mEndTime;
        } else {
            returndate = mSelectedDates.get(0)
                    + " " + mEndTime;
        }

        StringBuilder mBuilder = new StringBuilder();
        for (String days : mSelectedDates) {
            mBuilder.append(days + ",");
        }

        Log.d("time", deliverydate);

        String selectedDates = mBuilder.toString().substring(0, mBuilder.toString().length() - 1);

        if (mViewTulModel.getProduct_type() == 1) {
            deliverydate = mSelectedDates.get(0) + " " + mCheckInTime.trim();
            returndate = mSelectedDates.get(0) + " " + mCheckOutTime.trim();
        }

        if (mViewTulModel.getProduct_type() == 0) {
            mCheckInTime = mViewTulModel.getPreferences().getStart_time().trim();
            mCheckOutTime = mViewTulModel.getPreferences().getEnd_time().trim();
            mQuantity = "1";
        }
        String savecard = "0";

        String deliverytype;
        if (mDeliveryMode == 1)
            deliverytype = "2";
        else
            deliverytype = "1";

        if (TextUtils.isEmpty(mViewTulAdditional.getSecurity_charges())) {
            mViewTulAdditional.setSecurity_charges("0.00");
        }

        JsonObject additionalPrice = new JsonObject();
        additionalPrice.addProperty("security_charges", mViewTulAdditional.getSecurity_charges());
        additionalPrice.addProperty("fee", mViewTulAdditional.getFee());
        additionalPrice.addProperty("discount_percentage", mViewTulAdditional.getDiscount_percentage());
        additionalPrice.addProperty("discount_days", mViewTulAdditional.getDiscount_days());
        additionalPrice.addProperty("price", mViewTulAdditional.getPrice());

        Call<BookTulModel> call = RetrofitClient.getInstance().bookATul(utils.getString("access_token", ""),
                mViewTulModel.getId(), additionalPrice.toString(), mViewTulModel.getPrice(), String.format("%.2f",mTotalPrice), deliverydate, returndate, mViewTulAdditional.getSecurity_charges(),
                String.format("%.2f",totalAmount), mViewTulModel.getAddress(), String.valueOf(mDeliveryLatitude),
                String.valueOf(mDeliveryLongitude), selectedDates, token, deliverytype, getIntent().getStringExtra("delivery_charges"),
                mQuantity, mViewTulAdditional.getFee(), savecard, cardDetails.getCard_number(),
                cardDetails.getName_on_card(), String.valueOf(cardDetails.getExpiry_month()), String.valueOf(cardDetails.getExpiry_year()),
                mGson.toJson(mViewTulModel.getPreferences()), mViewTulModel.getAddress(), mCheckInTime.trim(), mCheckOutTime.trim(), String.format("%.2f", mDiscountPrice),
                String.format("%.2f", mTransactionCharges), String.format("%.2f", mExtraCharges), mViewTulAdditional.getDiscount_days(),
                mViewTulAdditional.getDiscount_percentage(), getIntent().getStringExtra("buffer_available"));

        call.enqueue(new Callback<BookTulModel>() {
            @Override
            public void onResponse(Call<BookTulModel> call, Response<BookTulModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    Intent inSplash = new Intent(mContext, ActiveBookingActivity.class);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(inSplash);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                } else {
                    try {
                        if (response.body().error.getCode().equals(errorAccessToken)) {
                            moveToSplash();
                        } else if (response.body().error.getCode().equals(getString(R.string.pause_error))) {
                            alertPauseDialog(getString(R.string.Alert), response.body().error.getMessage());
                        } else if (response.body().error.getCode().equals(getString(R.string.payment_exceed_code))) {
                            alertPaymentDialog(response.body().error.getMessage());
                        } else if (response.body().error.getCode().equals(getString(R.string.edit_error))) {
                            alertPauseDialog(getString(R.string.Details_Changed), response.body().error.getMessage());
                        } else {
                            showAlert(llDelivery, response.body().error.getMessage());
                        }
                    } catch (Exception e) {
                        Toast.makeText(TulCheckOutDetailsActivity.this, R.string.This_Place_has_been_removed_by_owner, Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(mContext, LandingActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<BookTulModel> call, Throwable t) {
                showAlert(llDelivery, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void alertPauseDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertPaymentDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.Payment_Exceed).setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        moveBack();
    }
}