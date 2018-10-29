package com.app.backyard;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.CircleTransform;
import model.BookTulModel;
import model.DemoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class RateYourExperienceActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;

    @BindView(R.id.cv_main)
    CardView cvMain;
    @BindView(R.id.cv_lower)
    CardView cvLower;

    @BindView(R.id.tv_rented)
    TextView tvRented;
    @BindView(R.id.ll_card_inner)
    LinearLayout llCardInner;

    @BindView(R.id.img_user)
    ImageView imgUser;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.srb_tul)
    SimpleRatingBar srbTul;

    @BindView(R.id.ed_review)
    EditText edReview;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    BookTulModel.ResponseBean mPendingModels;
    private int mOption = 0;
    private boolean mPushPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_rate_your_experience;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        rlMain.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.RATE_YOUR_EXPERIENCE);

        imgBckImg.setVisibility(View.GONE);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, mHeight / 26, 0, mHeight / 32);
        cvMain.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(mWidth / 6, mWidth / 6);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imgUser.setLayoutParams(layoutParams1);

        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0, mHeight / 60, mHeight / 60, 0);
        layoutParams2.gravity = Gravity.END;
        tvRented.setLayoutParams(layoutParams2);

        llCardInner.setPadding(mWidth / 32, mHeight / 14, mWidth / 32, mHeight / 32);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        tvPrice.setPadding(0, mHeight / 64, 0, mHeight / 22);

        tvRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvRating.setPadding(0, mHeight / 22, 0, mHeight / 32);

        tvRented.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvRented.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        srbTul.setStarSize((float) (mWidth * 0.1));
        srbTul.setPadding(0, mWidth / 42, 0, mWidth / 32);

        edReview.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        edReview.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        edReview.setTypeface(typefaceRegular);

        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / 4);
        layoutParams3.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 32);
        cvLower.setLayoutParams(layoutParams3);

        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams4.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 32);
        tvSubmit.setLayoutParams(layoutParams4);
        tvSubmit.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvSubmit.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);
    }

    @Override
    protected void onCreateStuff() {
        if (getIntent().hasExtra("owner")) {
            // getting data from signature activity
            tvAddress.setText(getIntent().getStringExtra("address"));
            tvPrice.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")) + String.format("%.2f", Double.parseDouble(getIntent().getStringExtra("totalAmount"))));

            if (getIntent().getIntExtra("path", 0) == 1) {
                tvRented.setText("LENT");
                displayProfileData(getIntent().getStringExtra("borrower"), getIntent().getStringExtra("borrower_pic"));
            } else {
                tvRented.setText("RENTED");
                displayProfileData(getIntent().getStringExtra("owner"), getIntent().getStringExtra("ownerPic"));
            }

        } else if (getIntent().hasExtra("data")) {
            /// getting data from landing data API
            mPendingModels = getIntent().getParcelableExtra("data");
           double mEarnedPrice = Double.parseDouble(mPendingModels.getTotal_amount()) -
                    (Double.parseDouble(mPendingModels.getTransaction_fee()) + Double.parseDouble(mPendingModels.getAdditional_charges().getSecurity_charges()));
            if (getIntent().getIntExtra("type", 0) == 5) {
                mOption = 1;
                displayProfileData(mPendingModels.getBorrower(), mPendingModels.getBorrower_pic());
                tvAddress.setText(mPendingModels.getAddress());
                tvPrice.setText(mPendingModels.getCurrency()+ String.format("%.2f",mEarnedPrice));
                tvRented.setText("LENT");

            } else {
                mOption = 2;
                displayProfileData(mPendingModels.getOwner(), mPendingModels.getOwner_pic());
                tvAddress.setText(mPendingModels.getAddress());
                tvPrice.setText(mPendingModels.getCurrency()+ String.format("%.2f", Double.parseDouble(mPendingModels.getTotal_amount())));
                tvRented.setText("RENTED");
            }
        } else {
            ///getting data from push
            mPushPath = true;
            NotificationManager notificationManager = (NotificationManager) mContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            if (connectedToInternet())
                hitPushAPI();
            else {
                Toast.makeText(mContext, errorInternet, Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (getIntent().hasExtra("push_id"))
            hitReadNotificationAPI(getIntent().getStringExtra("push_id"));

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

    private void displayProfileData(String name, String profilePic) {
        tvName.setText(name);
        Picasso.with(mContext).load(profilePic)
                .resize(mWidth / 6, mWidth / 6)
                .transform(new CircleTransform())
                .centerCrop().into(imgUser);
    }

    @Override
    protected void initListener() {
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return RateYourExperienceActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (connectedToInternet()) {
                    if (srbTul.getRating() == 0)
                        showAlert(llCardInner, getString(R.string.Please_provide_rating));
                    else
                        hitAPI();
                } else
                    showAlert(llCardInner, errorInternet);
                break;
        }
    }

    private void hitPushAPI() {
        llMain.setVisibility(View.INVISIBLE);
        showProgress();
        Call<BookTulModel> call = RetrofitClient.getInstance().getPushDeatilBookingId(utils.getString("access_token", ""),
                Integer.parseInt(getIntent().getStringExtra("booking_id")),
                Integer.parseInt(getIntent().getStringExtra("push_type")));
        call.enqueue(new Callback<BookTulModel>() {
            @Override
            public void onResponse(Call<BookTulModel> call, Response<BookTulModel> response) {
                hideProgress();
                llMain.setVisibility(View.VISIBLE);
                if (response.body().getResponse() != null) {
                    setPushData(response.body().getResponse());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BookTulModel> call, Throwable t) {
                hideProgress();
                showAlert(llCardInner, t.getLocalizedMessage());
            }
        });

    }

    private void setPushData(BookTulModel.ResponseBean response) {
        if (getIntent().getStringExtra("path").equals("show_rented")) {
            double mEarnedPrice = Double.parseDouble(response.getTotal_amount()) -
                   (Double.parseDouble(response.getTransaction_fee()) + Double.parseDouble(response.getAdditional_charges().getSecurity_charges()));
            tvRented.setText("LENT");
            mOption = 1;
            displayProfileData(response.getBorrower(), response.getBorrower_pic());
            tvAddress.setText(response.getAddress());
            tvPrice.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,""))+ String.format("%.2f",mEarnedPrice));
            tvRented.setText("LENT");
        } else {
            tvRented.setText("RENTED");
            mOption = 2;
            displayProfileData(response.getOwner(), response.getOwner_pic());
            tvAddress.setText(response.getAddress());
            tvPrice.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,""))+ String.format("%.2f", Double.parseDouble(response.getTotal_amount())));
        }

    }


    private void hitAPI() {
        /// 1 for borrower 2 for lender
        showProgress();

        Call<DemoModel> call;
        if (getIntent().hasExtra("data"))
            call = RetrofitClient.getInstance().feedBackRating(utils.getString("access_token", ""),
                    String.valueOf(mPendingModels.getBooking_id()), String.valueOf(mPendingModels.getTool_id()), (int) srbTul.getRating(),
                    edReview.getText().toString(), mOption);
        else if (getIntent().hasExtra("owner")) {
            call = RetrofitClient.getInstance().feedBackRating(utils.getString("access_token", ""),
                    getIntent().getStringExtra("booking_id"), getIntent().getStringExtra("tool_id"), (int) srbTul.getRating(),
                    edReview.getText().toString(), getIntent().getIntExtra("path", 0));
        } else
            call = RetrofitClient.getInstance().feedBackRating(utils.getString("access_token", ""),
                    getIntent().getStringExtra("booking_id"), getIntent().getStringExtra("tool_id"), (int) srbTul.getRating(),
                    edReview.getText().toString(), mOption);

        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    if (mPushPath) {
                        if (LandingActivity.landingActivity == null) {
                            finish();
                            Intent intent = new Intent(mContext, LandingActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                        } else {
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        }
                    } else {
                        Toast.makeText(mContext, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("date", getIntent().getStringExtra("date"));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(llCardInner, t.getLocalizedMessage());
            }
        });
    }

    private void hitReadNotificationAPI(final String push_id) {

        Call<DemoModel> call = RetrofitClient.getInstance().readNotifications(utils.getString("access_token", ""),
                push_id);
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response != null) {
                    db.updateNotificationReadStatus(Integer.parseInt(getIntent().getStringExtra("push_id")), "1");
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llCardInner, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llCardInner, t.getLocalizedMessage());
                db.updateNotificationReadStatus(Integer.parseInt(push_id), "0");
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
