package com.app.backyard;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.BoldTextView;
import customviews.MediumTextView;
import customviews.SemiboldTextView;
import model.AttachmentModel;
import model.CreateTulModel;
import model.ProfileModel;
import model.TransacrionPercentageModel;
import model.TulImageModel;
import model.TulModel;
import model.ViewTulModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.MainApplication;
import video.VideoCompressListener;
import video.VideoCompressor;
import videoUtils.SGLog;
import videoUtils.Worker;

public class ListYourTulActivity extends BaseActivity {

    private static final int VIEWSCHANGE = 1;

    @BindView(R.id.ll_heading_container)
    LinearLayout llHeadingContainer;
    @BindView(R.id.rl_main_container)
    RelativeLayout rlMainContainer;
    @BindView(R.id.tv_detail)
    MediumTextView tvDetail;
    @BindView(R.id.tv_title)
    SemiboldTextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imgBckImg;


    @BindView(R.id.tv_first)
    BoldTextView tvFirst;
    @BindView(R.id.tv_second)
    BoldTextView tvSecond;
    @BindView(R.id.tv_third)
    BoldTextView tvThird;
    @BindView(R.id.tv_price)
    BoldTextView tvPrice;

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.viewprice)
    View viewPrice;

    @BindView(R.id.ll_list_main_container)
    LinearLayout llListMainContainer;

    @BindView(R.id.ll_first_container)
    LinearLayout llFirstContainer;
    @BindView(R.id.ll_second_container)
    LinearLayout llSecondContainer;
    @BindView(R.id.ll_third_container)
    LinearLayout llThirdContainer;
    @BindView(R.id.ll_fourth_container)
    LinearLayout llFourthContainer;

    @BindView(R.id.ll_list_container)
    LinearLayout llListContainer;

    @BindView(R.id.img_dot1)
    RelativeLayout imgDot1;
    @BindView(R.id.img_dot2)
    RelativeLayout imgDot2;
    @BindView(R.id.img_dot3)
    RelativeLayout imgDot3;
    @BindView(R.id.img_dot4)
    RelativeLayout imgDot4;


    @BindView(R.id.view_line1)
    View viewLine1;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.view_line3)
    View viewLine3;

    @BindView(R.id.bt_done)
    Button btDone;

    @BindView(R.id.txt_primary)
    TextView txtPrimary;


    @BindView(R.id.tv_buffer)
    TextView tvBuffer;
    @BindView(R.id.img_buffer)
    ImageView imgBuffer;
    @BindView(R.id.ll_buffer)
    LinearLayout llBuffer;
    @BindView(R.id.view_buffer)
    View viewBuffer;

    @BindView(R.id.ll_buffer_selection)
    LinearLayout llBufferSelection;

    TulModel mTulModel;
    TulModel.ListTulModel mListTulModel;
    TulModel.PreferencesTul mPreferencesTul;
    TulModel.BankDetailsTul mBankDetailsTul;
    TulModel.PricingTul mPricingTul;

    ProgressDialog progDailog;
    /// edit variables
    ViewTulModel.ResponseBean mViewTulModel;
    BottomSheet bottomSheet;
    private boolean isEdit, isVideoCompressed;


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
        return R.layout.activity_list_your_tul;
    }

    @Override
    protected void initUI() {

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        LinearLayout.LayoutParams layoutParamscircleline3 = new LinearLayout.LayoutParams(mWidth / 200, (mWidth / 9) + Constants.dpToPx(4));
        layoutParamscircleline3.gravity = Gravity.CENTER;
        viewLine3.setLayoutParams(layoutParamscircleline3);

        Typeface typefacetitle = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/bold.ttf");
        tvTitle.setTypeface(typefacetitle);

        Typeface typefaceDetail = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/semibold.ttf");
        tvDetail.setTypeface(typefaceDetail);
        tvFirst.setTypeface(typefaceDetail);
        tvSecond.setTypeface(typefaceDetail);
        tvThird.setTypeface(typefaceDetail);
        btDone.setTypeface(typefaceDetail);

        Typeface typefaceMedium = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/regular.ttf");
        tvDetail.setTypeface(typefaceMedium);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(mWidth / 10, 0, mWidth / 10, 0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.bt_done);
        rlMainContainer.setLayoutParams(layoutParams);

        tvPrice.setTypeface(typefaceDetail);

        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.055));

        tvDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvDetail.setPadding(0, 0, 0, mHeight / 40);

        tvFirst.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvSecond.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvThird.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        btDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        LinearLayout.LayoutParams layoutParamsview = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Constants.dpToPx(1));
        layoutParamsview.setMargins(0, 0, 0, mWidth / 15);
        view1.setLayoutParams(layoutParamsview);
        view2.setLayoutParams(layoutParamsview);
        view3.setLayoutParams(layoutParamsview);

        txtPrimary.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtPrimary.setVisibility(View.GONE);

        llFirstContainer.setPadding(0, mWidth / 40, 0, mWidth / 12);
        llSecondContainer.setPadding(0, mWidth / 40, 0, mWidth / 12);
        llThirdContainer.setPadding(0, mWidth / 40, 0, mWidth / 12);

        RelativeLayout.LayoutParams layoutParamsmainContainer = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsmainContainer.setMargins(0, mWidth / 55, 0, mWidth / 35);
        layoutParamsmainContainer.addRule(RelativeLayout.BELOW, R.id.ll_heading_container);
        llListMainContainer.setLayoutParams(layoutParamsmainContainer);

        LinearLayout.LayoutParams layoutParamscircle = new LinearLayout.LayoutParams(mWidth / 10, mWidth / 10);
        layoutParamscircle.gravity = Gravity.CENTER;
        imgDot1.setLayoutParams(layoutParamscircle);
        imgDot2.setLayoutParams(layoutParamscircle);
        imgDot3.setLayoutParams(layoutParamscircle);

        LinearLayout.LayoutParams layoutParamscircleline = new LinearLayout.LayoutParams(mWidth / 200, mWidth / 8);
        layoutParamscircleline.gravity = Gravity.CENTER;
        viewLine1.setLayoutParams(layoutParamscircleline);
        viewLine2.setLayoutParams(layoutParamscircleline);

        RelativeLayout.LayoutParams layoutParamsbutton = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsbutton.setMargins(mWidth / 25, 0, mWidth / 25, mWidth / 25);
        layoutParamsbutton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        btDone.setLayoutParams(layoutParamsbutton);
        btDone.setPadding(0, mWidth / 20, 0, mWidth / 20);

        imgDot4.setLayoutParams(layoutParamscircle);
        llFourthContainer.setPadding(0, mWidth / 40, 0, mWidth / 12);
        viewPrice.setLayoutParams(layoutParamsview);

        tvBuffer.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreateStuff() {

        if (getIntent().hasExtra("tul_data")) {


            tvDetail.setText(getResources().getString(R.string.Edit_basic_detail_of_your_tul));

            isEdit = true;
            mViewTulModel = getIntent().getParcelableExtra("tul_data");

            /// setting tul data
            mListTulModel = new TulModel.ListTulModel();
            mListTulModel.title = mViewTulModel.getTitle();
            mListTulModel.category = mViewTulModel.getCategory_name();
            mListTulModel.categoryId = mViewTulModel.getCategory_id();
            mListTulModel.description = mViewTulModel.getDescription();
            mListTulModel.amenities = (ArrayList<String>) mViewTulModel.getAminities();
            mListTulModel.address = mViewTulModel.getAddress();
            mListTulModel.latitude = Double.parseDouble(mViewTulModel.getLatitute());
            mListTulModel.longitude = Double.parseDouble(mViewTulModel.getLongitude());
            mListTulModel.rules = (ArrayList<String>) mViewTulModel.getRules();
            mListTulModel.updateData = true;
            mListTulModel.isEdit = true;

            mListTulModel.buffer = mViewTulModel.getBuffer_status();
            if (mViewTulModel.getProduct_type() == 1) {
                llBuffer.setVisibility(View.VISIBLE);
                // 1 buffer checked  / buffer not allowed
                // 0 buffer unchecked / buffer  allowed
                if (mListTulModel.buffer == 1) {
                    llBufferSelection.setBackgroundResource(R.drawable.checkbox_checked_black);
                } else {
                    llBufferSelection.setBackgroundResource(R.drawable.checkbox_unchecked_white);
                }
            }


            mListTulModel.imagesVideo = new ArrayList<>();
            for (AttachmentModel attachmentModel : mViewTulModel.getAttachment()) {
                TulImageModel tulImageModel = new TulImageModel();
                tulImageModel.setId(attachmentModel.getId());
                tulImageModel.setPath(attachmentModel.getAttachment());
                tulImageModel.setEdit(true);
                if (attachmentModel.getType().equals(Constants.IMAGE_TEXT)) {
                    tulImageModel.setType(Constants.IMAGE);
                    tulImageModel.setThumbnails(attachmentModel.getAttachment());
                } else {
                    tulImageModel.setType(Constants.VIDEO);
                    tulImageModel.setThumbnails(attachmentModel.getThumbnail());
                }
                mListTulModel.imagesVideo.add(tulImageModel);
            }
            TulModel.setListTul(mListTulModel);

            mPreferencesTul = new TulModel.PreferencesTul();
            mPreferencesTul.isEdit = true;
            mPreferencesTul.availbiltyMode = mViewTulModel.getPreferences().getAvailable();
            mPreferencesTul.startTime = mViewTulModel.getPreferences().getStart_time();
            mPreferencesTul.endTime = mViewTulModel.getPreferences().getEnd_time();
            mPreferencesTul.hrsStartTime = mViewTulModel.getPreferences().getStart_time();
//            mPreferencesTul.hrsEndTime = mViewTulModel.getPreferences().getEnd_time();

            mPreferencesTul.updateData = true;

            if (mViewTulModel.getProduct_type() == 0) {
                mPreferencesTul.bookingAvailbiltyFor = getResources().getString(R.string.daily);
            } else if (mViewTulModel.getProduct_type() == 1) {
                mPreferencesTul.bookingAvailbiltyFor = getResources().getString(R.string.hourly);

            }

            TulModel.setPrefrencesTul(mPreferencesTul);

            llSecondContainer.setEnabled(true);
            llFourthContainer.setEnabled(false);

            mBankDetailsTul = new TulModel.BankDetailsTul();
            mBankDetailsTul.countryCode = mViewTulModel.getBank_detail().getCountry_code();
            mBankDetailsTul.currency = mViewTulModel.getBank_detail().getCurrency();
            mBankDetailsTul.accountNo = mViewTulModel.getBank_detail().getAccount_number();
            mBankDetailsTul.sortCode = mViewTulModel.getBank_detail().getSort_code();
            mBankDetailsTul.firstName = mViewTulModel.getBank_detail().getFirst_name();
            mBankDetailsTul.lastName = mViewTulModel.getBank_detail().getLast_name();
            mBankDetailsTul.address = mViewTulModel.getBank_detail().getAddress();
            mBankDetailsTul.city = mViewTulModel.getBank_detail().getCity();
            mBankDetailsTul.state = mViewTulModel.getBank_detail().getState();
            mBankDetailsTul.postalCode = mViewTulModel.getBank_detail().getPostal_code();
            mBankDetailsTul.dob = mViewTulModel.getBank_detail().getDob();
            mBankDetailsTul.updateData = false;

            TulModel.setBankDetailsTul(mBankDetailsTul);

            mPricingTul = new TulModel.PricingTul();
            mPricingTul.isEdit = true;

            mPricingTul.pricePerDay = mViewTulModel.getPrice();
            mPricingTul.securityCharges = mViewTulModel.getAdditional_price().getSecurity_charges();
            mPricingTul.extraFee = mViewTulModel.getAdditional_price().getFee();
            mPricingTul.pricePerHour = mViewTulModel.getPrice();

            mPricingTul.discountPercentage = mViewTulModel.getDiscount_percentage();

            mPricingTul.noBookings = String.valueOf(mViewTulModel.getDiscount_days());


            double i = Double.parseDouble(mViewTulModel.getPrice());

            double tran = Double.parseDouble(mViewTulModel.getTransaction_percentage());
            i = i - ((tran * i) / 100);
            mPricingTul.yourEarning = amountConversion(String.valueOf(i));

            if (!TextUtils.isEmpty(mViewTulModel.getExtra_fee())) {
                double j = Double.parseDouble(mViewTulModel.getExtra_fee());
                j = j - ((tran * j) / 100);
                mPricingTul.yourExtraEarning = amountConversion(String.valueOf( j));
            } else {
                mPricingTul.yourExtraEarning = "0.00";
            }
            TulModel.setPricingTul(mPricingTul);

            enableViewFirst();
            enableViewSecond();
            enableViewThird();
            enableViewFourth();

            btDone.setVisibility(View.VISIBLE);
        } else {
            tvDetail.setText(getResources().getString(R.string.add_basic_detail_of_your_tul));
            mListTulModel = TulModel.getListTul();
            mPreferencesTul = TulModel.getPrefrencesTul();
            mBankDetailsTul = TulModel.getBankDetailsTul();
            mPricingTul = TulModel.getPricingTul();

            llSecondContainer.setEnabled(false);
            llFourthContainer.setEnabled(false);
            llThirdContainer.setEnabled(false);
            llFourthContainer.setEnabled(false);
        }

    }

    @Override
    protected void initListener() {
        btDone.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
        llFirstContainer.setOnClickListener(this);
        llSecondContainer.setOnClickListener(this);
        llThirdContainer.setOnClickListener(this);
        llFourthContainer.setOnClickListener(this);
        llBuffer.setOnClickListener(this);
        viewBuffer.setOnClickListener(this);

        SpannableString ss = new SpannableString("Tick this box only when requested by the guest. Check the FAQs for more info!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                try {
                    Uri uri = Uri.parse("http://yourbackyardapp.com/faq");
                    Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(inTerms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
//              ds.setColor(getResources().getColor(R.color.com_facebook_blue));
            }
        };
        ss.setSpan(clickableSpan, 58, 62, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvBuffer.setText(ss);
        tvBuffer.setMovementMethod(LinkMovementMethod.getInstance());
        tvBuffer.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    public void disableViewFirst() {
        imgDot1.setBackgroundResource(R.drawable.white_circle);
        viewLine1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_color));
    }

    public void disableViewSecond() {
        imgDot2.setBackgroundResource(R.drawable.white_circle);
        viewLine2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_color));
    }

    public void disableViewThird() {
        imgDot3.setBackgroundResource(R.drawable.white_circle);
        viewLine3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_color));
    }

    public void disableViewFourth() {
        imgDot4.setBackgroundResource(R.drawable.white_circle);
    }

    public void enableViewFirst() {
        imgDot1.setBackgroundResource(R.drawable.circle_checked);
        viewLine1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black_color));
    }

    public void enableViewSecond() {
        imgDot2.setBackgroundResource(R.drawable.circle_checked);
        viewLine2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black_color));
    }

    public void enableViewThird() {
        imgDot3.setBackgroundResource(R.drawable.circle_checked);
        viewLine3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black_color));
    }

    public void enableViewFourth() {
        imgDot4.setBackgroundResource(R.drawable.circle_checked);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_buffer:
                if (mListTulModel.buffer == 1) {
                    llBufferSelection.setBackgroundResource(R.drawable.checkbox_unchecked_white);
                    mListTulModel.buffer = 0;
                } else {
                    llBufferSelection.setBackgroundResource(R.drawable.checkbox_checked_black);
                    mListTulModel.buffer = 1;
                }

                break;
            case R.id.img_back:
                if (mListTulModel.title != null || mPreferencesTul.availbiltyMode != null
                        || mBankDetailsTul.countryCode != null || mPricingTul.yourEarning != null) {
                    alertDiscardDialog();
                } else
                    moveBack();
                break;
            case R.id.bt_done:
                if (connectedToInternet()) {
                    if (mListTulModel.title == null)
                        showAlert(llListContainer, getString(R.string.Please_List_Your_Tul));
                    else if (mPreferencesTul.availbiltyMode == null)
                        showAlert(llListContainer, getString(R.string.Please_add_Preferences_for_your_tul));

                    else {
                        if (isEdit) {
                            String videoPath = "";
                            for (TulImageModel imageModel : mListTulModel.imagesVideo) {
                                if (!imageModel.isEdit()) {
                                    if (imageModel.getType().equals(Constants.VIDEO)) {
                                        videoPath = imageModel.getPath();
                                        break;
                                    }
                                }
                            }
                            if (TextUtils.isEmpty(videoPath)) {
                                hitTimeZoneApiAPI("");
                            } else {
                                compressVideo(videoPath);
                            }
                        } else {
                            String videoPath = "";
                            for (TulImageModel imageModel : mListTulModel.imagesVideo) {
                                if (imageModel.getType().equals(Constants.VIDEO)) {
                                    videoPath = imageModel.getPath();
                                    break;
                                }
                            }
                            if (TextUtils.isEmpty(videoPath)) {
                                hitTimeZoneApiAPI("");
                            } else {
                                compressVideo(videoPath);
                            }
                        }
                    }
                } else
                    showInternetAlert(llListContainer);
                break;
            case R.id.ll_first_container:
                if (!isEdit) {
                    hitTranApi();
                } else {
                    Intent intentList = new Intent(mContext, AddTulActivity.class);
                    if (isEdit)
                        intentList.putExtra("disable_category", "yes");
                    intentList.putExtra("BACKTOLANDING", "yes");
                    startActivityForResult(intentList, VIEWSCHANGE);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                break;
            case R.id.ll_second_container:
                Intent intentPrefrences = new Intent(mContext, PreferencesActivity.class);
                intentPrefrences.putExtra("BACKTOLANDING", "yes");
                startActivityForResult(intentPrefrences, VIEWSCHANGE);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.ll_third_container:
                if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly)) && !TextUtils.isEmpty(mPreferencesTul.hrsStartTime)) {
                    Intent intentPrice = new Intent(mContext, AddPriceActivity.class);
                    intentPrice.putExtra("BACKTOLANDING", "yes");
                    startActivityForResult(intentPrice, VIEWSCHANGE);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
                    Intent intentPrice = new Intent(mContext, AddPriceActivity.class);
                    intentPrice.putExtra("BACKTOLANDING", "yes");
                    startActivityForResult(intentPrice, VIEWSCHANGE);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }

                break;

            case R.id.ll_fourth_container:
                if (isEdit) {
                    Toast toast;
                    toast = Toast.makeText(mContext, R.string.Primary_account_is_already_linked_with_the_Place__You_can_change_your_primary_account_from_the_Payments_section, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Intent intentBank = new Intent(mContext, AddBankDetailActivity.class);
                    intentBank.putExtra("BACKTOLANDING", "yes");
                    startActivityForResult(intentBank, VIEWSCHANGE);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                break;
        }
    }

    void hitTranApi() {
        showProgress();
        Call<TransacrionPercentageModel> call = RetrofitClient.getInstance().transactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransacrionPercentageModel>() {
            @Override
            public void onResponse(Call<TransacrionPercentageModel> call, Response<TransacrionPercentageModel> response) {
                try {
                    if (response != null) {
                        hideProgress();
                        Constants.TRANSACTION_PERCENTAGE = Double.parseDouble(response.body().getResponse());
                        bottomSheet = new BottomSheet.Builder(ListYourTulActivity.this, R.style.BottomSheet_Dialog)
                                .title(R.string.booking_availability)
                                .sheet(R.menu.menu_booking).listener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case R.id.item_hour:
                                                if (mPreferencesTul.bookingAvailbiltyFor != null && !mPreferencesTul.bookingAvailbiltyFor.equalsIgnoreCase(getResources().getString(R.string.hourly))) {
                                                    alertclearDialog();
                                                } else {
                                                    mPreferencesTul.bookingAvailbiltyFor = getResources().getString(R.string.hourly);
                                                    Intent intentList = new Intent(mContext, AddTulActivity.class);
                                                    intentList.putExtra("BACKTOLANDING", "yes");
                                                    startActivityForResult(intentList, VIEWSCHANGE);
                                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                                }
                                                break;
                                            case R.id.item_day:
                                                if (mPreferencesTul.bookingAvailbiltyFor != null && !mPreferencesTul.bookingAvailbiltyFor.equalsIgnoreCase(getResources().getString(R.string.daily))) {
                                                    alertclearDialog();
                                                } else {
                                                    mPreferencesTul.bookingAvailbiltyFor = getResources().getString(R.string.daily);
                                                    clearPrefPrice();
                                                    Intent intent = new Intent(mContext, AddTulActivity.class);
                                                    intent.putExtra("BACKTOLANDING", "yes");
                                                    startActivityForResult(intent, VIEWSCHANGE);
                                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                                }
                                                break;
                                        }
                                    }
                                }).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(ListYourTulActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<TransacrionPercentageModel> call, Throwable t) {
                hideProgress();
                showAlert(txtPrimary, t.getLocalizedMessage());

            }
        });
    }

    private void hitTransactionAPI() {
        Call<TransacrionPercentageModel> call = RetrofitClient.getInstance().transactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransacrionPercentageModel>() {
            @Override
            public void onResponse(Call<TransacrionPercentageModel> call, Response<TransacrionPercentageModel> response) {
                if (response.body().getResponse() != null) {
                    Log.e("Transaction %age = ", response.body().getResponse());
                    Constants.TRANSACTION_PERCENTAGE = Double.parseDouble(response.body().getResponse());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llListContainer, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransacrionPercentageModel> call, Throwable t) {
                showAlert(llListContainer, t.getLocalizedMessage());
            }
        });

    }

    void clearPrefPrice() {
        mPreferencesTul.startTime = null;
        mPreferencesTul.endTime = null;
        mPreferencesTul.availbiltyMode = null;
        mPreferencesTul.hrsStartTime = null;
//        mPreferencesTul.hrsEndTime=null;

        mPricingTul.securityCharges = null;
        mPricingTul.pricePerDay = null;
        mPricingTul.noBookings = null;
        mPricingTul.pricePerHour = null;
        mPricingTul.yourEarning = null;
        mPricingTul.yourExtraEarning = null;
        mPricingTul.discountPercentage = null;
        mPricingTul.extraFee = null;
    }

    private void hitTimeZoneApiAPI(final String videoPath) {
        showProgress();
        getTimeZone(videoPath, String.valueOf(mListTulModel.latitude), String.valueOf(mListTulModel.longitude));
    }

    private void hitAPI(String videoPath, String timeZone) {
//        showProgress();

        String price = "0";
        String product_type = "";
        String check_in = "00:00";
        String check_out = "00:00";


        if (TextUtils.isEmpty(mPricingTul.securityCharges)) {
            mPricingTul.securityCharges = "0.00";
        }
        if (TextUtils.isEmpty(mPricingTul.extraFee)) {
            mPricingTul.extraFee = "0.00";
        }

        if (TextUtils.isEmpty(mPricingTul.noBookings)) {
            mPricingTul.noBookings = "0";
            mPricingTul.discountPercentage = "0";
        }

        if (TextUtils.isEmpty(mPricingTul.discountPercentage)) {
            mPricingTul.noBookings = "0";
            mPricingTul.discountPercentage = "0";
        }


        if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
            price = mPricingTul.pricePerDay;
            check_in = mPreferencesTul.startTime;
            check_out = mPreferencesTul.endTime;
            product_type = "0";
        } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
            price = mPricingTul.pricePerHour;
            check_in = mPreferencesTul.hrsStartTime;
//            check_out = mPreferencesTul.hrsEndTime;
            check_out = "00:00";
            product_type = "1";
        }

        JsonObject additionalPrice = new JsonObject();
        additionalPrice.addProperty("security_charges", mPricingTul.securityCharges);
        additionalPrice.addProperty("fee", mPricingTul.extraFee);
        additionalPrice.addProperty("discount_percentage", mPricingTul.discountPercentage);
        additionalPrice.addProperty("discount_days", mPricingTul.noBookings);
        additionalPrice.addProperty("price", price);

        JsonObject preferences = new JsonObject();
        preferences.addProperty("available", mPreferencesTul.availbiltyMode);
        preferences.addProperty("start_time", check_in);
        preferences.addProperty("end_time", check_out);

        RequestBody reqFileVideo, reqFileThumb;
        MultipartBody.Part bodyVideo, bodyThumb;
        File mFileVideo, mFileThumb;

        reqFileVideo = RequestBody.create(MediaType.parse("video/*"), "");
        bodyVideo = MultipartBody.Part.createFormData("video", "", reqFileVideo);

        reqFileThumb = RequestBody.create(MediaType.parse("image/*"), "");
        bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", "", reqFileThumb);

        String rules = "";
        if (mListTulModel.rules != null && mListTulModel.rules.size() > 0) {
            StringBuilder mBuilder = new StringBuilder();
            for (String ruless : mListTulModel.rules) {
                mBuilder.append(ruless + Constants.RULES_REGEX_RESPONSE);
            }
            String mRules = mBuilder.toString();
            rules = mRules.substring(0, mRules.length() - 9);
            Log.e("Rules = ", rules);
        }

        String amenitiesValue;
        if (mListTulModel.amenities.size() > 0) {
            StringBuilder mBuilderAmenities = new StringBuilder();
            for (String amenities : mListTulModel.amenities) {
                mBuilderAmenities.append(amenities + Constants.RULES_REGEX_RESPONSE);
            }
            amenitiesValue = mBuilderAmenities.toString().substring(0, mBuilderAmenities.toString().length() - 9);
        } else
            amenitiesValue = "";
        Log.e("Amenities = ", amenitiesValue);

        Collections.reverse(mListTulModel.imagesVideo);

        ArrayList<MultipartBody.Part> imagesArray = new ArrayList<>();
        for (TulImageModel imageModel : mListTulModel.imagesVideo) {
            if (imageModel.getType().equals(Constants.IMAGE)) {
                File mFileImage = new File(imageModel.getPath());
                imagesArray.add(prepareFilePart(mFileImage));
            } else {
                mFileVideo = new File(videoPath);
                mFileThumb = new File(imageModel.getThumbnails());

                reqFileVideo = RequestBody.create(MediaType.parse("video/*"), mFileVideo);
                reqFileThumb = RequestBody.create(MediaType.parse("image/*"), mFileThumb);

                bodyVideo = MultipartBody.Part.createFormData("video", mFileVideo.getName(), reqFileVideo);
                bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", mFileThumb.getName(), reqFileThumb);
            }
        }
//        String dis = mListTulModel.description;
//        String dis1 = mListTulModel.description;
//
//        for (int i = 0; i < dis.length(); i++) {
//            if (dis1.endsWith("\n")) {
//                dis1 = dis1.substring(0, dis1.length() - 2);
//            } else {
//                break;
//            }
//        }
//        dis=dis1;
//        for (int i = 0; i < dis.length(); i++) {
//            if (dis1.startsWith("\n")) {
//                dis1 = dis1.substring(2,dis1.length());
//            } else {
//                break;
//            }
//        }
//        mListTulModel.description = dis1;

        Call<CreateTulModel> call = RetrofitClient.getInstance().createTul(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(mListTulModel.title),
                createPartFromString(String.valueOf(mListTulModel.categoryId)),
                createPartFromString(mListTulModel.description),
                createPartFromString(price),
                createPartFromString(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,""))),
                createPartFromString(String.valueOf(additionalPrice)),
                createPartFromString(mListTulModel.address),
                createPartFromString(String.valueOf(mListTulModel.latitude)),
                createPartFromString(String.valueOf(mListTulModel.longitude)),
                createPartFromString(String.valueOf(rules)),
                createPartFromString(amenitiesValue),
                createPartFromString(String.valueOf(preferences)),
                createPartFromString(utils.getString("accountId", "")),
                createPartFromString(timeZone),
                createPartFromString(check_in),
                createPartFromString(check_out),
                createPartFromString(mPricingTul.securityCharges),
                createPartFromString(mPricingTul.extraFee),
                createPartFromString(mPricingTul.discountPercentage),
                createPartFromString(mPricingTul.noBookings),
                createPartFromString(String.valueOf(Constants.TRANSACTION_PERCENTAGE)),
                createPartFromString(product_type),
                createPartFromString(utils.getString(Constants.PRIMARY_CURRENCY, "")),
                imagesArray, bodyVideo, bodyThumb);
        call.enqueue(new Callback<CreateTulModel>() {
            @Override
            public void onResponse(Call<CreateTulModel> call, Response<CreateTulModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    clearData();
                    utils.setString("visible_primary", "");
                    addToLocal(response.body().getResponse());
                    utils.setInt("lender", 1);
                    utils.setString(Constants.IS_CURRENCY_SELECTED, "1");
                    utils.setInt(Constants.BLOCKSTATUS, 0);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else if (response.body().error.getCode().equals(Constants.TRANSCHANGED)) {
                        hitTransactionAPI();
                        alertTransFeeDialog();
                    } else {
                        showAlert(llListContainer, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateTulModel> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void hitEditAPI(String videoPath, String timeZone) {
        String price = "0";
        String product_type = "";
        String check_in = "00:00";
        String check_out = "00:00";


        if (TextUtils.isEmpty(mPricingTul.securityCharges)) {
            mPricingTul.securityCharges = "0.00";
        }

        if (TextUtils.isEmpty(mPricingTul.extraFee)) {
            mPricingTul.extraFee = "0.00";
        }

        if (TextUtils.isEmpty(mPricingTul.noBookings)) {
            mPricingTul.noBookings = "0";
            mPricingTul.discountPercentage = "0";
        }

        if (TextUtils.isEmpty(mPricingTul.discountPercentage)) {
            mPricingTul.noBookings = "0";
            mPricingTul.discountPercentage = "0";
        }


        if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.daily))) {
            price = mPricingTul.pricePerDay;
            check_in = mPreferencesTul.startTime;
            check_out = mPreferencesTul.endTime;
            product_type = "0";
        } else if (mPreferencesTul.bookingAvailbiltyFor.equals(getResources().getString(R.string.hourly))) {
            price = mPricingTul.pricePerHour;
            check_in = mPreferencesTul.hrsStartTime;
//          check_out = mPreferencesTul.hrsEndTime;
            check_out = "00:00";
            product_type = "1";
        }

        JsonObject additionalPrice = new JsonObject();
        additionalPrice.addProperty("security_charges", mPricingTul.securityCharges);
        additionalPrice.addProperty("fee", mPricingTul.extraFee);
        additionalPrice.addProperty("discount_percentage", mPricingTul.discountPercentage);
        additionalPrice.addProperty("discount_days", mPricingTul.noBookings);
        additionalPrice.addProperty("price", price);

        JsonObject preferences = new JsonObject();
        preferences.addProperty("available", mPreferencesTul.availbiltyMode);
        preferences.addProperty("start_time", check_in);
        preferences.addProperty("end_time", check_out);

        RequestBody reqFileVideo, reqFileThumb;
        MultipartBody.Part bodyVideo, bodyThumb;
        File mFileVideo, mFileThumb;

        reqFileVideo = RequestBody.create(MediaType.parse("video/*"), "");
        bodyVideo = MultipartBody.Part.createFormData("video", "", reqFileVideo);

        reqFileThumb = RequestBody.create(MediaType.parse("image/*"), "");
        bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", "", reqFileThumb);

        String rules = "";
        if (mListTulModel.rules != null && mListTulModel.rules.size() > 0) {
            StringBuilder mBuilder = new StringBuilder();
            for (String ruless : mListTulModel.rules) {
                mBuilder.append(ruless + Constants.RULES_REGEX_RESPONSE);
            }
            String mRules = mBuilder.toString();
            rules = mRules.substring(0, mRules.length() - 9);
            Log.e("Rules = ", rules);
        }

        String amenitiesValue;
        if (mListTulModel.amenities.size() > 0) {
            StringBuilder mBuilderAmenities = new StringBuilder();
            for (String amenities : mListTulModel.amenities) {
                mBuilderAmenities.append(amenities + Constants.RULES_REGEX_RESPONSE);
            }
            amenitiesValue = mBuilderAmenities.toString().substring(0, mBuilderAmenities.toString().length() - 9);
        } else
            amenitiesValue = "";
        Log.e("Amenities = ", amenitiesValue);

        StringBuilder mBuilderAttachmentIds = new StringBuilder();
        String mAttachmentIds = "";
        if (mListTulModel.attachments_ids != null && mListTulModel.attachments_ids.size() > 0) {
            for (String ids : mListTulModel.attachments_ids) {
                mBuilderAttachmentIds.append(ids + ",");
            }
            String attachmentIds = mBuilderAttachmentIds.toString();
            mAttachmentIds = attachmentIds.substring(0, attachmentIds.length() - 1);
        }
        Log.e("mAttachmentIds = ", mAttachmentIds);

        ArrayList<MultipartBody.Part> imagesArray = new ArrayList<>();
        Collections.reverse(mListTulModel.imagesVideo);
        for (TulImageModel imageModel : mListTulModel.imagesVideo) {
            if (!imageModel.isEdit()) {
                if (imageModel.getType().equals(Constants.IMAGE)) {
                    File mFileImage = new File(imageModel.getPath());
                    imagesArray.add(prepareFilePart(mFileImage));
                } else {
                    mFileVideo = new File(videoPath);
                    mFileThumb = new File(imageModel.getThumbnails());

                    reqFileVideo = RequestBody.create(MediaType.parse("video/*"), mFileVideo);
                    reqFileThumb = RequestBody.create(MediaType.parse("image/*"), mFileThumb);

                    bodyVideo = MultipartBody.Part.createFormData("video", mFileVideo.getName(), reqFileVideo);
                    bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", mFileThumb.getName(), reqFileThumb);
                }
            }
        }
//        if (mPricingTul.noBookings == null && mPricingTul.discountPercentage == null) {
//            mPricingTul.noBookings = "0";
//            mPricingTul.discountPercentage = "0";
//        }

        //buffer status: 0 if buffer allowed, 1 if no buffer allowed.
        // 1 checked
        // 0 unchecked

//        String dis = mListTulModel.description;
//        String dis1 = mListTulModel.description;
//
//        for (int i = 0; i < dis.length(); i++) {
//            if (dis.endsWith("\n")) {
//                dis1 = dis.substring(0, dis1.length() - 2);
//            } else {
//                break;
//            }
//        }
//        mListTulModel.description = dis1;

        Call<ViewTulModel> call = RetrofitClient.getInstance().editTul(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(mListTulModel.title),
                createPartFromString(String.valueOf(mListTulModel.categoryId)),
                createPartFromString(mListTulModel.description),
                createPartFromString(amenitiesValue),
                createPartFromString(price),
                createPartFromString(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,""))),
                createPartFromString(String.valueOf(additionalPrice)),
                createPartFromString(mListTulModel.address),
                createPartFromString(String.valueOf(mListTulModel.latitude)),
                createPartFromString(String.valueOf(mListTulModel.longitude)),
                createPartFromString(String.valueOf(rules)),
                createPartFromString(String.valueOf(preferences)),
                createPartFromString(String.valueOf(mViewTulModel.getId())),
                createPartFromString(mAttachmentIds),
                createPartFromString(timeZone),
                createPartFromString(check_in),
                createPartFromString(check_out),
                createPartFromString(mPricingTul.securityCharges),
                createPartFromString(mPricingTul.extraFee),
                createPartFromString(mPricingTul.discountPercentage),
                createPartFromString(mPricingTul.noBookings),
                createPartFromString(product_type),
                createPartFromString(String.valueOf(Constants.TRANSACTION_PERCENTAGE)),
                imagesArray, bodyVideo, bodyThumb, createPartFromString(String.valueOf(mListTulModel.buffer)));

        call.enqueue(new Callback<ViewTulModel>() {
            @Override
            public void onResponse(Call<ViewTulModel> call, Response<ViewTulModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    db.deleteTulAttachemnetById(String.valueOf(mViewTulModel.getId()));
                    clearData();
                    utils.setString("visible_primary", "");
                    addToLocalByEdit(response.body().getResponse());
                    utils.setInt("lender", 1);
                    utils.setInt(Constants.BLOCKSTATUS, 0);
                    Intent intent = new Intent();
                    intent.putExtra("tul_data", response.body().getResponse());
                    setResult(RESULT_OK, intent);
                    if (Double.parseDouble(response.body().getResponse().getTransaction_percentage()) == (Constants.TRANSACTION_PERCENTAGE)) {
                        finish();
                    } else {
                        Constants.TRANSACTION_PERCENTAGE = Double.parseDouble(response.body().getResponse().getTransaction_percentage());
                        alertEditTransFeeDialog();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else {
                        showAlert(llListContainer, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewTulModel> call, Throwable t) {
                hideProgress();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == VIEWSCHANGE) {

                if (mListTulModel.title != null) {
                    mListTulModel.updateData = true;
                    enableViewFirst();
                    llSecondContainer.setEnabled(true);
                } else {
                    mListTulModel.updateData = false;
                    disableViewFirst();
                    llSecondContainer.setEnabled(false);
                }

                if (mPreferencesTul.availbiltyMode != null) {
                    mPreferencesTul.updateData = true;
                    enableViewSecond();
                    llSecondContainer.setEnabled(true);
                    llThirdContainer.setEnabled(true);
                } else {
                    mPreferencesTul.updateData = false;
                    disableViewSecond();
                    llThirdContainer.setEnabled(false);
                }
                if (mPricingTul.yourEarning != null) {
                    enableViewThird();
                    llThirdContainer.setEnabled(true);
                    llFourthContainer.setEnabled(true);
                } else {
                    disableViewThird();
                    llFourthContainer.setEnabled(false);
                }

                if (mBankDetailsTul.accountNo != null) {
                    mBankDetailsTul.updateData = true;
                    enableViewFourth();
                    llFourthContainer.setEnabled(true);
                } else {
                    mBankDetailsTul.updateData = false;
                    disableViewFourth();
                }

                if (mListTulModel.title != null && mPreferencesTul.availbiltyMode != null
                        && mPricingTul.securityCharges != null)
                    btDone.setVisibility(View.VISIBLE);
                else
                    btDone.setVisibility(View.INVISIBLE);

                if (utils.getString("visible_primary", "").equals("yes")) {
                    txtPrimary.setVisibility(View.VISIBLE);
                    llFourthContainer.setEnabled(false);
                } else {
                    txtPrimary.setVisibility(View.GONE);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private MultipartBody.Part prepareFilePart(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), mFile);
        return MultipartBody.Part.createFormData("images[]", mFile.getName(), requestFile);
    }

    private RequestBody createPartFromString(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    private void addToLocal(CreateTulModel.ResponseBean response) {
        ProfileModel.ResponseBean.ProductBean toolsModel = new ProfileModel.ResponseBean.ProductBean();
        toolsModel.setTitle(response.getTitle());
        toolsModel.setId(response.getId());
        toolsModel.setUser_id(response.getUser_id());
        toolsModel.setPrice(response.getPrice());
        toolsModel.setTransaction_percentage(String.valueOf(Constants.TRANSACTION_PERCENTAGE));
        toolsModel.setProduct_type(String.valueOf(response.getProduct_type()));

        db.addListedTul(toolsModel);

        for (CreateTulModel.ResponseBean.AttachmentBean attachmentBean : response.getAttachment()) {
            AttachmentModel profileAttachmentBean = new AttachmentModel();
            profileAttachmentBean.setId(attachmentBean.getId());
            profileAttachmentBean.setProduct_id(attachmentBean.getProduct_id());
            profileAttachmentBean.setAttachment(attachmentBean.getAttachment());
            profileAttachmentBean.setThumbnail(attachmentBean.getThumbnail());
            profileAttachmentBean.setType(attachmentBean.getType());
            db.addAttachment(profileAttachmentBean);
        }
    }

    private void addToLocalByEdit(ViewTulModel.ResponseBean response) {
        ProfileModel.ResponseBean.ProductBean toolsModel = new ProfileModel.ResponseBean.ProductBean();
        toolsModel.setTitle(response.getTitle());
        toolsModel.setId(response.getId());
        toolsModel.setUser_id(response.getUser_id());
        toolsModel.setPrice(response.getPrice());
        toolsModel.setProduct_type(String.valueOf(response.getProduct_type()));
        toolsModel.setTransaction_percentage(String.valueOf(response.getTransaction_percentage()));

        db.addListedTul(toolsModel);

        for (AttachmentModel attachmentBean : response.getAttachment()) {
            AttachmentModel profileAttachmentBean = new AttachmentModel();
            profileAttachmentBean.setId(attachmentBean.getId());
            profileAttachmentBean.setProduct_id(attachmentBean.getProduct_id());
            profileAttachmentBean.setAttachment(attachmentBean.getAttachment());
            profileAttachmentBean.setThumbnail(attachmentBean.getThumbnail());
            profileAttachmentBean.setType(attachmentBean.getType());
            db.addAttachment(profileAttachmentBean);
        }
    }


    private void clearData() {
        mTulModel = new TulModel();
        mListTulModel.title = null;
        mListTulModel.isEdit = false;

        mPreferencesTul.availbiltyMode = null;
        mPreferencesTul.bookingAvailbiltyFor = null;
        mPreferencesTul.hrsStartTime = null;
//        mPreferencesTul.hrsEndTime = null;
        mPreferencesTul.startTime = null;
        mPreferencesTul.endTime = null;
        mPreferencesTul.isEdit = false;

        mPricingTul.pricePerDay = null;
        mPricingTul.discountPercentage = null;
        mPricingTul.extraFee = null;
        mPricingTul.yourExtraEarning = null;
        mPricingTul.yourEarning = null;
        mPricingTul.pricePerHour = null;
        mPricingTul.securityCharges = null;
        mPricingTul.noBookings = null;
        mPricingTul.isEdit = false;

        mBankDetailsTul.accountNo = null;

        TulModel.setListTul(mListTulModel);
        TulModel.setPrefrencesTul(mPreferencesTul);
        TulModel.setBankDetailsTul(mBankDetailsTul);
        TulModel.setPricingTul(mPricingTul);
    }

    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.all_data_discard)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListTulModel.title = null;
                        mListTulModel.isEdit = false;
                        utils.setString("visible_primary", "");

                        mPreferencesTul.availbiltyMode = null;
                        mPreferencesTul.bookingAvailbiltyFor = null;
                        mPreferencesTul.isEdit = false;

                        mPricingTul.yourEarning = null;
                        mPricingTul.pricePerHour = null;
                        mPricingTul.pricePerDay = null;
                        mPricingTul.isEdit = false;

                        mBankDetailsTul.accountNo = null;
                        TulModel.setListTul(mListTulModel);
                        TulModel.setPrefrencesTul(mPreferencesTul);
                        TulModel.setBankDetailsTul(mBankDetailsTul);
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

    private void alertclearDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ALERT_If_You_change_Your_Booking_Availability_Your_Price_And_Preferences_will_be_Reset)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearPrefPrice();
                        if (mPreferencesTul.bookingAvailbiltyFor != null && !mPreferencesTul.bookingAvailbiltyFor.equalsIgnoreCase(getResources().getString(R.string.hourly))) {
                            mPreferencesTul.bookingAvailbiltyFor = getResources().getString(R.string.hourly);
                            Intent intentList = new Intent(mContext, AddTulActivity.class);
                            intentList.putExtra("BACKTOLANDING", "yes");
                            startActivityForResult(intentList, VIEWSCHANGE);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else if (mPreferencesTul.bookingAvailbiltyFor != null && !mPreferencesTul.bookingAvailbiltyFor.equalsIgnoreCase(getResources().getString(R.string.daily))) {
                            mPreferencesTul.bookingAvailbiltyFor = getResources().getString(R.string.daily);
                            clearPrefPrice();
                            Intent intent = new Intent(mContext, AddTulActivity.class);
                            intent.putExtra("BACKTOLANDING", "yes");
                            startActivityForResult(intent, VIEWSCHANGE);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        }

                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        bottomSheet.cancel();
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertEditTransFeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Admin_Percentage)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertTransFeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Admin_Percentage)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearData();
                        disableViewFirst();
                        disableViewSecond();
                        disableViewThird();
                        btDone.setVisibility(View.GONE);
                        llSecondContainer.setEnabled(false);
                        llThirdContainer.setEnabled(false);
                        llFourthContainer.setEnabled(false);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        if (mListTulModel.title != null || mPreferencesTul.availbiltyMode != null
                || mBankDetailsTul.accountNo != null) {
            alertDiscardDialog();
        } else
            moveBack();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void compressVideo(final String path) {

        progDailog = ProgressDialog.show(this, getString(R.string.Please_wait), getString(R.string.Compressing_Video), true);
        progDailog.setCancelable(false);

        VideoCompressor.compress(this, path, new VideoCompressListener() {
            @Override
            public void onSuccess(final String outputFile, String filename, long duration) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        SGLog.e("video compress success:" + outputFile);
                        progDailog.dismiss();
                        if (isVideoCompressed) {
                            hitTimeZoneApiAPI(outputFile);
                        } else {
                            hitTimeZoneApiAPI(path);
                        }
//                        hitAPI(outputFile);
                    }
                });
            }

            @Override
            public void onFail(final String reason) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "video compress failed:" + reason, Toast.LENGTH_SHORT).show();
                        SGLog.e("video compress failed:" + reason);
                        progDailog.dismiss();
                    }
                });
            }

            @Override
            public void onProgress(final int progress) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        isVideoCompressed = true;
                        SGLog.e("video compress progress:" + progress);
                    }
                });
            }
        });
    }

    public void getTimeZone(final String path, String Lat, String Lan) {
        String url = "https://maps.googleapis.com/maps/api/timezone/json?location=" + Lat + "," + Lan + "&timestamp=1458000000&key=AIzaSyBrXxd8VbVmxSK1GtEQB0iUJI-xYCCxpN8";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response iss:: " + response);
                try {
                    if (isEdit)
                        hitEditAPI(path, response.getString("timeZoneId"));
                    else
                        hitAPI(path, response.getString("timeZoneId"));
                } catch (Exception e) {
                    Log.e("Exce  = ", e + "");
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
//                    Toast.makeText(this, "Check Internet Connection !", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        MainApplication.getInstance().addToRequestQueue(jsonObjReq, "jreq");
    }

}

