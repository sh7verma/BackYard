package com.app.backyard;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.AVLoadingIndicatorView;
import customviews.MaterialEditText;
import model.SignupModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;


public class SignupActivity extends BaseActivity {

    private static final int REGGUEST = 3;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_email_login)
    MaterialEditText edEmailLogin;
    @BindView(R.id.ed_password_login)
    MaterialEditText edPasswordLogin;
    @BindView(R.id.txt_hint_msg)
    TextView txtHintMsg;
    @BindView(R.id.txt_signup)
    TextView txtSignup;
    @BindView(R.id.indicator_loader)
    AVLoadingIndicatorView indicatorLoader;
    @BindView(R.id.rl_signup)
    RelativeLayout rlSignup;

    boolean isLoading;

    @Override
    protected int getContentView() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edEmailLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edEmailLogin.setTypeface(typeface);

        edPasswordLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edPasswordLogin.setTypeface(typeface);

        txtHintMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

        txtSignup.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
    }

    @Override
    protected void onCreateStuff() {
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        ClickableSpan termsCond = new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);    // you can use custom color
                ds.setUnderlineText(false);    // this remove the underline
            }

            @Override
            public void onClick(View widget) {
                // TODO Auto-generated method stub
                Uri uri = Uri.parse("http://yourbackyardapp.com/terems_condition");
                Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(inTerms);
            }
        };

        SpannableString ss = new SpannableString(getString(R.string.By_signing_up_i_agree_to_the_Terms_and_Conditions));
        ss.setSpan(termsCond, ss.length() - 21, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.hint_color_dark)), ss.length() - 21, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD), ss.length() - 21, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtHintMsg.setText(ss);
        txtHintMsg.setMovementMethod(LinkMovementMethod.getInstance());

        edPasswordLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    verifyDetails();
                }
                return true;
            }
        });
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        rlSignup.setOnClickListener(this);

    }

    @Override
    protected Context getContext() {
        return SignupActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (!isLoading) {
                    if (getIntent().hasExtra(Constants.REG_GUEST)) {
                        utils.setInt(Constants.BLOCKSTATUS, 0);
                        utils.setInt(Constants.ISEMAILSKIP, 0);
                        utils.setInt(Constants.ISGUEST, 1);
                        utils.setInt("email_verify", 0);
                    }
                    finish();
                    overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                }
                break;
            case R.id.rl_signup:
                verifyDetails();
                break;
        }
    }


    private void verifyDetails() {
        if (edEmailLogin.getText().toString().trim().isEmpty()) {
            showAlert(rlSignup, getResources().getString(R.string.enter_email));
            isLoading = false;
        } else if (!validateEmail(edEmailLogin.getText())) {
            showAlert(rlSignup, getResources().getString(R.string.enter_valid_email));
            isLoading = false;
        } else if (edPasswordLogin.getText().toString().trim().isEmpty()) {
            showAlert(rlSignup, getResources().getString(R.string.enter_password));
            isLoading = false;
        } else if (edPasswordLogin.getText().toString().trim().length() < 8) {
            showAlert(rlSignup, getResources().getString(R.string.error_password));
            isLoading = false;
        } else {
            if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                Constants.closeKeyboard(this, rlSignup);
                startLoading();
                disableViews();
                hitAPI();
            } else {
                showAlert(rlSignup, errorInternet);
            }
        }
    }

    private void startLoading() {
        rlSignup.setEnabled(false);
        txtSignup.setVisibility(View.INVISIBLE);
        indicatorLoader.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    private void stopLoading() {
        rlSignup.setEnabled(true);
        txtSignup.setVisibility(View.VISIBLE);
        indicatorLoader.setVisibility(View.INVISIBLE);
        isLoading = false;
    }

    private void hitAPI() {
        isLoading = true;
        Call<SignupModel> call = RetrofitClient.getInstance().userSignup(edEmailLogin.getText().toString().trim(),
                edPasswordLogin.getText().toString(), platformStatus, Constants.EMAIL_LOGIN, utils.getString("device_token", ""), Constants.EMPTY,
                Constants.EMPTY, Constants.EMPTY, Constants.EMPTY);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                stopLoading();
                enableViews();
                if (response.body().getResponse() != null) {
                    utils.setString("user_id", String.valueOf(response.body().getResponse().getId()));
                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
                    utils.setInt("status", response.body().getResponse().getStatus());
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());

                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());

                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());

                    isLoading = false;
//                    Intent inStarted = new Intent(mContext, VerifyEmailActivity.class);
//                    startActivity(inStarted);
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                    Intent inStarted = new Intent(mContext, VerifyEmailActivity.class);
                    if (getIntent().hasExtra(Constants.REG_GUEST)) {
                        inStarted.putExtra(Constants.REG_GUEST, 1);
                        startActivityForResult(inStarted, REGGUEST);
                    } else {
                        startActivity(inStarted);
                        finish();
                    }
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(rlSignup, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                enableViews();
                stopLoading();
                showAlert(rlSignup, t.getLocalizedMessage());
            }
        });
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

    @Override
    public void onBackPressed() {
        if (!isLoading) {
            if (getIntent().hasExtra(Constants.REG_GUEST)) {
                utils.setInt(Constants.BLOCKSTATUS, 0);
                utils.setInt(Constants.ISEMAILSKIP, 0);
                utils.setInt(Constants.ISGUEST, 1);
                utils.setInt("email_verify", 0);
            }
        }
        super.onBackPressed();
    }

    boolean validateEmail(CharSequence text) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    private void disableViews() {
        edEmailLogin.setEnabled(false);
        edPasswordLogin.setEnabled(false);
    }

    private void enableViews() {
        edEmailLogin.setEnabled(true);
        edPasswordLogin.setEnabled(true);
    }

}
