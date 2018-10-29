package com.app.backyard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.AVLoadingIndicatorView;
import customviews.RoundedTransformation;
import model.DemoModel;
import model.SignupModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class EditProfileActivity extends BaseActivity {
    private static final int EDITPROFILE = 1;


    final int RESULTCODE_COUNTRY = 4;
    private final int PIC = 2;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.ed_first_name)
    EditText edFirstName;
    @BindView(R.id.ed_last_name)
    EditText edLastName;
    @BindView(R.id.rl_update)
    RelativeLayout rlUpdate;
    @BindView(R.id.txt_update)
    TextView txtUpdate;
    @BindView(R.id.indicator_loader)
    AVLoadingIndicatorView indicatorLoader;

    @BindView(R.id.txt_phone_hint)
    TextView txtPhoneHint;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.ed_phone_no)
    EditText edPhoneNo;

    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.view_email)
    View view_email;

    @BindView(R.id.ed_unverified_email)
    EditText ed_unverified_email;
    @BindView(R.id.ll_unverified_email)
    LinearLayout llUnverifiedEmail;

    boolean isLoading;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.isEmpty(utils.getString(Constants.UNVERIFIED_EMAIL, ""))
                    || utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(utils.getString("email", ""))) {
                llUnverifiedEmail.setVisibility(View.GONE);
            } else {
                llUnverifiedEmail.setVisibility(View.VISIBLE);
                ed_unverified_email.setText(utils.getString(Constants.UNVERIFIED_EMAIL, ""));
            }
            edEmail.setText(utils.getString("email", ""));
        }
    };
    private File mFile;
    private String mPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edFirstName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edFirstName.setTypeface(typeface);

        edLastName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edLastName.setTypeface(typeface);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtToolbarTitle.setText(getResources().getString(R.string.edit_profile));
        txtToolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.black_color));

        imgBack.setImageResource(R.mipmap.ic_back_wc);

        edPhoneNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edPhoneNo.setTypeface(typeface);

        txtCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtCode.setTypeface(typeface);

        edEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edEmail.setTypeface(typeface);

        ed_unverified_email.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        ed_unverified_email.setTypeface(typeface);

        txtUpdate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
    }

    @Override
    protected void onCreateStuff() {
        edFirstName.setText(utils.getString("first_name", ""));
        edFirstName.setSelection(edFirstName.getText().toString().length());

        edLastName.setText(utils.getString("last_name", ""));
        edLastName.setSelection(edLastName.getText().toString().length());

        edEmail.setText(utils.getString("email", ""));

        if (utils.getInt("path", 0) == 2) {/// fb login
            edEmail.setVisibility(View.GONE);
            view_email.setVisibility(View.GONE);
        }

        mPath = utils.getString("profile_pic", "");
        getCountryCode();
        if (!TextUtils.isEmpty(utils.getString("country_code", "")))
            txtCode.setText(utils.getString("country_code", ""));
        else
            txtCode.setText("+44");

        edPhoneNo.setText(utils.getString("phone_number", ""));

        if (!TextUtils.isEmpty(utils.getString("profile_pic", "")))
            Picasso.with(mContext).load(utils.getString("profile_pic", ""))
                    .resize(Constants.dpToPx(80), Constants.dpToPx(80)).centerCrop()
                    .transform(new RoundedTransformation(20, 0))
                    .placeholder(R.mipmap.ic_add_image).into(imgProfile, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Log.e("Error = ", "Yes");
                }
            });
        else
            Picasso.with(mContext).load(R.mipmap.ic_add_image).resize(Constants.dpToPx(80), Constants.dpToPx(80)).centerCrop()
                    .into(imgProfile);

        if (TextUtils.isEmpty(utils.getString(Constants.UNVERIFIED_EMAIL, ""))
                || utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(utils.getString("email", ""))) {
            llUnverifiedEmail.setVisibility(View.GONE);
        } else {
            llUnverifiedEmail.setVisibility(View.VISIBLE);
            ed_unverified_email.setText(utils.getString(Constants.UNVERIFIED_EMAIL, ""));
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.EMAIL_VERIFY));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        ed_unverified_email.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return EditProfileActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_unverified_email:
                hitResendApi();
                break;
            case R.id.ll_code:
                Intent inSelectCountry = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(inSelectCountry, RESULTCODE_COUNTRY);
                break;
            case R.id.img_profile:
                Intent inProfile = new Intent(mContext, OptionPhotoSelection.class);
                if (mPath != null) {
                    inProfile.putExtra("visible", "yes");
                    inProfile.putExtra("path", mPath);
                }
                startActivityForResult(inProfile, PIC);
                break;
            case R.id.rl_update:
                if (mFile == null && mPath == null) {
                    showAlert(rlUpdate, getString(R.string.error_pic));
                } else if (edFirstName.getText().toString().trim().length() < 2) {
                    showAlert(rlUpdate, getString(R.string.error_name));
                } else if (edLastName.getText().toString().trim().length() < 2) {
                    showAlert(rlUpdate, getString(R.string.error_name));
                } else if (edEmail.getText().toString().trim().isEmpty()) {
                    showAlert(edEmail, getResources().getString(R.string.enter_email));
                    isLoading = false;
                } else if (!validateEmail(edEmail.getText())) {
                    showAlert(edEmail, getResources().getString(R.string.enter_valid_email));
                    isLoading = false;
                } else {
                    if (connectedToInternet()) {
                        Constants.closeKeyboard(this, rlUpdate);
                        disableViews();
                        isLoading = true;
                        startLoading();
                        hitAPI();
                    } else
                        showInternetAlert(rlUpdate);
                }
                break;
            case R.id.img_back:
                if (!isLoading) {
                    finish();
                    overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                }
                break;
        }
    }

    boolean validateEmail(CharSequence text) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    private void hitAPI() {
        final RequestBody reqFile;
        MultipartBody.Part body;

        if (mFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), mFile);
            body = MultipartBody.Part.createFormData("pic", mFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            body = MultipartBody.Part.createFormData("pic", "", reqFile);
        }

        RequestBody access_token = RequestBody.create(MediaType.parse("text/plain"), utils.getString("access_token", ""));
        RequestBody firstname = RequestBody.create(MediaType.parse("text/plain"), edFirstName.getText().toString().trim());
        RequestBody lastname = RequestBody.create(MediaType.parse("text/plain"), edLastName.getText().toString().trim());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), firstname + " " + lastname);

        RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"),
                edPhoneNo.getText().toString().trim());

        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"),
                utils.getString("country_code", ""));

        RequestBody Is_number_updated;
        if (utils.getString("phone_number", "").equals(edPhoneNo.getText().toString())) {
            Is_number_updated = RequestBody.create(MediaType.parse("text/plain"), "0");
        } else {
            Is_number_updated = RequestBody.create(MediaType.parse("text/plain"), "1");
        }

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edEmail.getText().toString().trim());

        RequestBody email_changed;
        if (utils.getString("email", "").equals(edEmail.getText().toString())) {
            email_changed = RequestBody.create(MediaType.parse("text/plain"), "0");
        } else {
            email_changed = RequestBody.create(MediaType.parse("text/plain"), "1");
        }


        Call<SignupModel> call = RetrofitClient.getInstance().updateProfile(access_token,
                firstname, lastname,
                Is_number_updated, email_changed, email, phone_number, country_code, body);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    isLoading = false;
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("email", response.body().getResponse().getEmail());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setInt("otp", response.body().getResponse().getOtp());
                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());


                    if (!utils.getString("phone_number", "").equals(edPhoneNo.getText().toString())) {
                        Intent verifyIntent = new Intent(mContext, EditVerifyPhoneNumber.class);
                        verifyIntent.putExtra("code", txtCode.getText().toString());
                        verifyIntent.putExtra("phone", edPhoneNo.getText().toString().trim());
                        startActivityForResult(verifyIntent, EDITPROFILE);

                    } else if (utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(edEmail.getText().toString())) {
                        finish();
                        Intent intent = new Intent(mContext, EditVerifyEmail.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        isLoading = false;
                        showAlert(rlUpdate, response.body().error.getMessage());
                    }
                }
                stopLoading();
                enableViews();
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                isLoading = false;
                stopLoading();
                enableViews();
                showAlert(rlUpdate, t.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PIC) {

                Log.e("Path = ", data.getStringExtra("filePath"));
                mPath = data.getStringExtra("filePath");
                mFile = new File(data.getStringExtra("filePath"));
                Picasso.with(mContext).load(mFile)
                        .resize(Constants.dpToPx(80), Constants.dpToPx(80))
                        .transform(new RoundedTransformation(20, 0))
                        .into(imgProfile);
            } else if (requestCode == EDITPROFILE) {
                if (utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(edEmail.getText().toString())) {
                    Intent intent = new Intent(mContext, EditVerifyEmail.class);
                    finish();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } else if (requestCode == RESULTCODE_COUNTRY) {
                txtCode.setText(data.getStringExtra("Country_code"));
            }
        }
    }


    private void disableViews() {
        edFirstName.setEnabled(false);
        edLastName.setEnabled(false);
        edEmail.setEnabled(false);
        imgProfile.setEnabled(false);
    }

    private void enableViews() {
        edFirstName.setEnabled(true);
        edLastName.setEnabled(true);
        edEmail.setEnabled(true);
        imgProfile.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (!isLoading)
            super.onBackPressed();
    }

    private void startLoading() {
        rlUpdate.setEnabled(false);
        txtUpdate.setVisibility(View.INVISIBLE);
        edFirstName.setEnabled(false);
        edLastName.setEnabled(false);
        edEmail.setEnabled(false);
        indicatorLoader.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    private void stopLoading() {
        rlUpdate.setEnabled(true);
        edFirstName.setEnabled(true);
        edLastName.setEnabled(true);
        edEmail.setEnabled(true);
        txtUpdate.setVisibility(View.VISIBLE);
        indicatorLoader.setVisibility(View.INVISIBLE);
        isLoading = false;
    }

    void hitResendApi() {
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().resendEditEmail(utils.getString("access_token", ""), utils.getString(Constants.UNVERIFIED_EMAIL, ""));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                Intent intent = new Intent(mContext, EditVerifyEmail.class);
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(edFirstName, t.getLocalizedMessage());
            }
        });
    }

    void getCountryCode() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso().toUpperCase();
        String[] rl = getResources().getStringArray(R.array.CountryCodes);

        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(countryCode.trim())) {
                txtCode.setText("+" + g[0]);
                utils.setString("country_code", "+" + g[0]);
                break;
            }
        }
    }
}
