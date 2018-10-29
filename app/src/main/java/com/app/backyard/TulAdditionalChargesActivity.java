package com.app.backyard;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import customviews.MediumTextView;
import customviews.SemiboldTextView;
import model.ViewTulModel;
import utils.Constants;

public class TulAdditionalChargesActivity extends BaseActivity {

    @BindView(R.id.txt_toolbar_title)
    SemiboldTextView txtToolbarTitle;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.tv_tul_security_charges)
    MediumTextView tvSecurityCharges;
    @BindView(R.id.tv_tul_security_charges_no)
    SemiboldTextView tvSecurityChargesNo;

    @BindView(R.id.tv_tul_price_per_day)
    MediumTextView tvPricePerDay;
    @BindView(R.id.tv_tul_price_per_day_no)
    SemiboldTextView tvPricePerDayNo;

    @BindView(R.id.tv_tul_price_per_hour)
    MediumTextView tvPricePerHour;
    @BindView(R.id.tv_tul_price_per_hour_no)
    SemiboldTextView tvPricePerHourNo;

    @BindView(R.id.tv_tul_convenience_fee)
    MediumTextView tvTulConvenienceFee;
    @BindView(R.id.tv_tul_convenience_fee_no)
    SemiboldTextView tvTulConvenienceFeeNo;

    @BindView(R.id.ll_extra_earning)
    LinearLayout llExtraEarning;
    @BindView(R.id.tv_extra_earning_hint)
    TextView tvExtraEarningHint;
    @BindView(R.id.tv_extra_earning_value)
    TextView tvExtraEarningValue;
    @BindView(R.id.img_your_earning_info)
    ImageView imgYourEarningInfo;

    @BindView(R.id.ll_your_earning)
    LinearLayout llYourEarning;
    @BindView(R.id.tv_your_earning_hint)
    TextView tvYourEarningHint;
    @BindView(R.id.tv_your_earning_value)
    TextView tvYourEarningValue;
    @BindView(R.id.img_earning_info)
    ImageView imgEarningInfo;

    @BindView(R.id.tv_percent_off)
    TextView tvPercentOff;
    @BindView(R.id.booking_time)
    TextView tvBookingTime;

    @BindView(R.id.ll_hourly)
    LinearLayout llHourly;
    @BindView(R.id.ll_day)
    LinearLayout llDay;

    @BindView(R.id.ll_offer)
    LinearLayout llOffers;

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view_hour)
    View viewHour;
    @BindView(R.id.view_days)
    View viewDay;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view5)
    View view5;

    ViewTulModel.ResponseBean.AdditionalPriceBean mViewTulAdditional;

    String mProductType;
    String time;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_additional_charges;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.price_and_offers);

        tvPricePerDay.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvPricePerDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPricePerDayNo.setPadding(0, mHeight / 25, mWidth / 20, mHeight / 25);
        tvPricePerDayNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPricePerHour.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvPricePerHour.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPricePerHourNo.setPadding(0, mHeight / 25, mWidth / 20, mHeight / 25);
        tvPricePerHourNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvSecurityChargesNo.setPadding(0, mHeight / 25, mWidth / 20, mHeight / 25);
        tvSecurityChargesNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvSecurityCharges.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvSecurityCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvSecurityChargesNo.setPadding(0, mHeight / 25, mWidth / 20, mHeight / 25);
        tvSecurityChargesNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvTulConvenienceFee.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvTulConvenienceFee.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvTulConvenienceFeeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvTulConvenienceFeeNo.setPadding(0, mHeight / 25, mWidth / 20, mHeight / 25);

        tvPercentOff.setPadding(mWidth / 20, mHeight / 25, 0, 0);
        tvPercentOff.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvBookingTime.setPadding(mWidth / 20, 0, 0, mHeight / 25);
        tvBookingTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvExtraEarningHint.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvExtraEarningHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvExtraEarningValue.setPadding(0, mHeight / 25, 0, mHeight / 25);
        tvExtraEarningValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvYourEarningHint.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvYourEarningHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvYourEarningValue.setPadding(0, mHeight / 25, 0, mHeight / 25);
        tvYourEarningValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(mWidth / 20, 0, mWidth / 20, 0);
        view1.setLayoutParams(layoutParams);
        view2.setLayoutParams(layoutParams);
        view3.setLayoutParams(layoutParams);
        view4.setLayoutParams(layoutParams);
        view5.setLayoutParams(layoutParams);
        viewHour.setLayoutParams(layoutParams);
        viewDay.setLayoutParams(layoutParams);
    }

    @Override
    protected void onCreateStuff() {

        mViewTulAdditional = getIntent().getParcelableExtra("data");

        if (getIntent().getIntExtra("user_id", 0) == Integer.parseInt(utils.getString("user_id", ""))) {
            llExtraEarning.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            llYourEarning.setVisibility(View.VISIBLE);
            view5.setVisibility(View.VISIBLE);
        }

        mProductType = getIntent().getStringExtra("type");

        time = mViewTulAdditional.getDiscount_days();

        if (time.equals("0") || time == null || time.equals("")) {
            llOffers.setVisibility(View.GONE);
        }


        if (mProductType.equals("1")) {
            llDay.setVisibility(View.GONE);
            viewDay.setVisibility(View.GONE);

            llHourly.setVisibility(View.VISIBLE);
            viewHour.setVisibility(View.VISIBLE);
            tvPricePerHourNo.setText(mViewTulAdditional.getCurrency()+ mViewTulAdditional.getPrice());

            tvBookingTime.setText(getString(R.string.When_you_book_for_at_least)+" "+  Integer.parseInt(time) + " Hours");

        } else if (mProductType.equals("0")) {

            llHourly.setVisibility(View.GONE);
            viewHour.setVisibility(View.GONE);

            llDay.setVisibility(View.VISIBLE);
            viewDay.setVisibility(View.VISIBLE);
            tvPricePerDayNo.setText(mViewTulAdditional.getCurrency()+ mViewTulAdditional.getPrice());
            tvBookingTime.setText(getString(R.string.When_you_book_for_at_least) +" "+ Integer.parseInt(time) + " Days");

        }
        tvPercentOff.setText("GET " + mViewTulAdditional.getDiscount_percentage() + "% OFF");
        setData();
    }

    private void setData() {
        tvSecurityChargesNo.setText(mViewTulAdditional.getCurrency() + mViewTulAdditional.getSecurity_charges());
        if (!mViewTulAdditional.getFee().equals(""))
            tvTulConvenienceFeeNo.setText(mViewTulAdditional.getCurrency() + mViewTulAdditional.getFee());
        else
            tvTulConvenienceFeeNo.setText("---");

        tvYourEarningValue.setText(mViewTulAdditional.getCurrency() +
                getEarningAmount(mViewTulAdditional.getPrice(), getIntent().getStringExtra("transaction_percentage")));

        if (!TextUtils.isEmpty(mViewTulAdditional.getFee()) && !mViewTulAdditional.getFee().equals("0.00")) {
            tvExtraEarningValue.setText(mViewTulAdditional.getCurrency() +
                    getEarningAmount(mViewTulAdditional.getFee(), getIntent().getStringExtra("transaction_percentage")));
        } else {
            tvExtraEarningValue.setText(mViewTulAdditional.getCurrency() + "0.00");

        }
    }

    private String getEarningAmount(String price, String transactionPercentage) {
        double i = Double.parseDouble(price);
        i = i - ((Double.parseDouble(transactionPercentage) * i) / 100);
        return amountConversion(String.valueOf(i));
    }

    public String amountConversion(String am) {
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
    protected void initListener() {
        imgBack.setOnClickListener(this);
        imgYourEarningInfo.setOnClickListener(this);
        imgEarningInfo.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulAdditionalChargesActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.img_your_earning_info:
                if (!TextUtils.isEmpty(mViewTulAdditional.getFee())) {
                    PopupWindow popupwindow_obj = showEarningPopup(getIntent().getStringExtra("transaction_percentage"), "extra fee");
                    popupwindow_obj.showAsDropDown(imgYourEarningInfo, 5, -20);
                }
                break;
            case R.id.img_earning_info:
                PopupWindow popupwindow_obj = showEarningPopup(getIntent().getStringExtra("transaction_percentage"),
                        "price");
                popupwindow_obj.showAsDropDown(imgEarningInfo, 5, -20);
                break;
        }
    }

    private PopupWindow showEarningPopup(String transaction_percentage, String price) {
        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_dialog_layout, null);

        LinearLayout.LayoutParams arrowParms = new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen._15sdp)), (int) (getResources().getDimension(R.dimen._15sdp)));
        arrowParms.setMargins(0, 0, mWidth / 64, 0);
        arrowParms.gravity = Gravity.RIGHT;
        TextView txtArrow = (TextView) view.findViewById(R.id.txt_arrow1);
        txtArrow.setLayoutParams(arrowParms);

        TextView txtInfo = (TextView) view.findViewById(R.id.txt_info);
        txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
//      txtInfo.setText("This is the amount you will earn after deducting  " + transaction_percentage + "% from your " + price +
//                " Pounds listings.");

        txtInfo.setText(getString(R.string.This_is_the_total)+" "+price+" "+getString(R.string.you_will_earn_after_deduction_of)+" "+transaction_percentage+getString(R.string.percentage_fee_by_BACK_YARD));

        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        return popupWindow;
    }
}
