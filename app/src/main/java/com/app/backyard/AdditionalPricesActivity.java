package com.app.backyard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import butterknife.BindView;
import customviews.MaterialEditText;
import utils.Constants;

public class AdditionalPricesActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.txt_hint_msg)
    TextView txtHintMsg;

    @BindView(R.id.ed_security)
    MaterialEditText edSecurity;
    @BindView(R.id.ed_convience)
    MaterialEditText edConvience;

    @BindView(R.id.txt_done)
    TextView txtDone;

    @Override
    protected int getContentView() {
        return R.layout.activity_additional_prices;
    }

    @Override
    protected void initUI() {


        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        if (getIntent().hasExtra("is_edit"))
            txtToolbarTitle.setText(getResources().getString(R.string.edit_additional_prices));
        else
            txtToolbarTitle.setText(getResources().getString(R.string.add_additional_prices));

        llMain.setPadding(mWidth / 32, 0, mWidth / 32, mWidth / 32);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");

        txtHintMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtHintMsg.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        edSecurity.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edSecurity.setTypeface(typeface);

        edConvience.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edConvience.setTypeface(typeface);

        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtDone.setPadding(0, mWidth / 24, 0, mWidth / 24);

        setData();
    }

    private void setData() {
        edSecurity.setText(getIntent().getStringExtra("security_price"));
        edSecurity.setSelection(edSecurity.getText().toString().length());

        edConvience.setText(getIntent().getStringExtra("convience_price"));
        edConvience.setSelection(edConvience.getText().toString().length());
    }

    @Override
    protected void onCreateStuff() {
        final Drawable img = ContextCompat.getDrawable(mContext, R.drawable.pound);
        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));
        edConvience.setCompoundDrawables(img, null, null, null);
        edSecurity.setCompoundDrawables(img, null, null, null);
    }

    @Override
    protected void initListener() {
        txtDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return AdditionalPricesActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_done:
                if (TextUtils.isEmpty(edSecurity.getText().toString().trim()) || edSecurity.getText().toString().equals(".")) {
                    showAlert(llMain, getString(R.string.security_fee_error));
                } else if (Double.parseDouble(edSecurity.getText().toString().trim()) < Constants.MIN_PRICE) {
                    showAlert(llMain, getString(R.string.error_security_deposite) +   getString(R.string.zero_one));
                }
                else if (Double.parseDouble(edSecurity.getText().toString().trim()) > Constants.MAX_SECURITY_CHARGES) {
                    showAlert(llMain, getString(R.string.error_max_security_price));
                }
                else if (edConvience.getText().toString().trim().length() > 0) {
                    if (edConvience.getText().toString().equals("."))
                        showAlert(llMain, getString(R.string.extra_fee_error));
                    else {
                        if ((Double.parseDouble(edConvience.getText().toString()) < Constants.MIN_PRICE)) {
                            showAlert(llMain, getString(R.string.error_extra_fee_Convience) + utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")) + getString(R.string.zero_one));
                        }
                        if ((Double.parseDouble(edConvience.getText().toString()) > Constants.MAX_PRICE)) {
                            showAlert(llMain, getString(R.string.error_max_fee));
                        } else {
                            Constants.closeKeyboard(mContext, llMain);
                            Intent intent = new Intent();
                            intent.putExtra("security_fee", edSecurity.getText().toString().trim());
                            intent.putExtra("convience_fee", edConvience.getText().toString().trim());
                            setResult(RESULT_OK, intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        }
                    }
                } else {
                    Constants.closeKeyboard(mContext, llMain);
                    Intent intent = new Intent();
                    intent.putExtra("security_fee", edSecurity.getText().toString().trim());
                    intent.putExtra("convience_fee", edConvience.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                break;
            case R.id.img_back:
                Constants.closeKeyboard(mContext, llMain);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
    }
}
