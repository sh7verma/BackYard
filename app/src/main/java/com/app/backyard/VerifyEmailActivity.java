package com.app.backyard;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import api.RetrofitClient;
import butterknife.BindView;
import model.DemoModel;
import model.ProfileModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

/**
 * Created by dev on 23/11/17.
 */

public class VerifyEmailActivity extends BaseActivity {

    private static final int REGGUEST = 3;

    @BindView(R.id.txt_verify_email_header)
    TextView txtVerifyEmailHeader;
    @BindView(R.id.ll_outer)
    LinearLayout llOuter;
    @BindView(R.id.ll_fields)
    LinearLayout llFields;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.txt_resend)
    TextView txtResend;
    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.txt_skip_email)
    TextView txtSkipEmail;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            utils.setInt(Constants.EMAIL_VERIFY, 1);
            utils.setInt(Constants.ISEMAILSKIP, 0);

            Intent inStarted = new Intent(mContext, CreateProfileActivity.class);
            if (getIntent().hasExtra(Constants.REG_GUEST)) {
                inStarted.putExtra(Constants.REG_GUEST, 1);
                startActivityForResult(inStarted, REGGUEST);
            } else {
                inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                inStarted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(inStarted);
            }
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_verify_email;
    }

    @Override
    protected void initUI() {

        txtVerifyEmailHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtVerifyEmailHeader.setPadding(mWidth / 32, mHeight / 28, 0, mHeight / 28);

        llOuter.setPadding(mWidth / 28, 0, mWidth / 28, mWidth / 24);

        llFields.setPadding(mWidth / 28, mHeight / 9, mWidth / 28, 0);

        txtDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtDesc.setPadding(mWidth / 32, mWidth / 18, mWidth / 32, 0);

        LinearLayout.LayoutParams lpResend = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpResend.setMargins(0, mWidth / 64, 0, mWidth / 26);
        txtResend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtResend.setPadding(mWidth / 26, mWidth / 28, mWidth / 26, mWidth / 28);
        txtResend.setLayoutParams(lpResend);

        txtLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtLogin.setPadding(0, mHeight / 32, 0, mHeight / 32);

        txtSkipEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtSkipEmail.setPadding(0, mHeight / 32, 0, mHeight / 32);

        imgClose.setPadding(mWidth / 40, mWidth / 40, mWidth / 40, mWidth / 40);
        imgClose.setLayoutParams(lpResend);
    }

    @Override
    protected void onCreateStuff() {
    }

    @Override
    protected void initListener() {
        txtResend.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        txtSkipEmail.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return VerifyEmailActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        utils.setInt("inside_verify", 1);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_resend:
                if (connectedToInternet()) {
                    hitApi();
                } else {
                    showAlert(txtResend, getString(R.string.internet));
                }
                break;
            case R.id.txt_login:
                Intent in = new Intent(VerifyEmailActivity.this, LoginActivity.class);
                if (getIntent().hasExtra(Constants.REG_GUEST)) {
                    in.putExtra(Constants.REG_GUEST, 1);
                    startActivityForResult(in, REGGUEST);
                } else {
                    startActivity(in);
                }
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.img_close:
                if (getIntent().hasExtra(Constants.REG_GUEST)) {
                    utils.setInt(Constants.ISGUEST, 1);
                    utils.setInt(Constants.BLOCKSTATUS, 0);
                    utils.setInt(Constants.ISEMAILSKIP, 0);
                    utils.setInt("email_verify", 0);
                }
                finish();
                overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                break;
            case R.id.txt_skip_email:
                if (connectedToInternet()) {
                    hitSkipEmailApi();
                } else {
                    showInternetAlert(llFields);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().hasExtra(Constants.REG_GUEST)) {
            utils.setInt(Constants.ISGUEST, 1);
            utils.setInt(Constants.BLOCKSTATUS, 0);
            utils.setInt(Constants.ISEMAILSKIP, 0);
            utils.setInt("email_verify", 0);
        }
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    void hitApi() {
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().resendEmail(utils.getString("access_token", ""));
        call.enqueue(new Callback<DemoModel>() {

            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(txtResend, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.EMAIL_VERIFY));
    }

    void hitSkipEmailApi() {
        showProgress();
        Call<ProfileModel> call = RetrofitClient.getInstance().skipEmailverification(utils.getString("access_token", ""));
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    utils.setInt(Constants.EMAIL_VERIFY, 0);
                    utils.setInt(Constants.ISEMAILSKIP, 1);

                    Intent intent = new Intent(mContext, CreateProfileActivity.class);
                    if (getIntent().hasExtra(Constants.REG_GUEST)) {
                        intent.putExtra(Constants.REG_GUEST, 1);
                        startActivityForResult(intent, REGGUEST);
                    } else {
                        startActivity(intent);
                        finish();
                    }
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llFields, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                hideProgress();
                showAlert(txtResend, t.getLocalizedMessage());
            }
        });
    }


    @Override
    public void onStop() {
        utils.setInt("inside_verify", 0);
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REGGUEST) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

}
