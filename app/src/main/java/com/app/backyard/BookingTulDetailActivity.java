package com.app.backyard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import adapters.TulDetailImagesAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.CircleTransform;
import customviews.FlowLayout;
import customviews.HeaderView;
import dialog.ShowMoreDialog;
import me.relex.circleindicator.CircleIndicator;
import model.AttachmentModel;
import model.BookTulModel;
import model.ChatDialogModel;
import model.CheckChatModel;
import model.DemoModel;
import model.NearByTulListingModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.FirebaseListeners;

import static android.Manifest.permission.CALL_PHONE;

/**
 * Created by applify on 12/6/2017.
 */

public class BookingTulDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, OnMapReadyCallback {
    private static final int CANCEL = 1;
    private static final int PHONE = 2;
    @BindView(R.id.cl_main)
    CoordinatorLayout clMain;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_header_view)
    HeaderView toolbarHeaderView;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.vp_tul)
    ViewPager vpTul;
    @BindView(R.id.cp_indicator)
    CircleIndicator cpIndicator;
    @BindView(R.id.btn_shortlist)
    LikeButton btnShortlist;

    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;

    @BindView(R.id.ll_data)
    LinearLayout llData;

    @BindView(R.id.txt_tul_title)
    TextView txtTulTitle;
    @BindView(R.id.rl_stars)
    RelativeLayout rlStars;
    @BindView(R.id.ll_stars)
    LinearLayout llStars;
    @BindView(R.id.srb_tul)
    SimpleRatingBar srbTul;

    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.img_call)
    ImageView imgCall;
    @BindView(R.id.img_chat)
    ImageView imgChat;
    @BindView(R.id.txt_owner)
    TextView txtOwner;
    @BindView(R.id.txt_owner_name)
    TextView txtOwnerName;
    @BindView(R.id.ll_profile)
    LinearLayout llProfile;

    @BindView(R.id.tv_about_tul_title)
    TextView tvAboutTulTitle;
    @BindView(R.id.tv_about_tul_detail)
    TextView tvAboutTulDetail;

    @BindView(R.id.tv_amenities_title)
    TextView tvAmenitiestitle;
    @BindView(R.id.fl_interests)
    FlowLayout flInterests;
    @BindView(R.id.view_amenities)
    View viewAmenities;

    @BindView(R.id.tv_address_title)
    TextView tvAddressTitle;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;
    @BindView(R.id.ll_map)
    LinearLayout llMap;

    @BindView(R.id.ll_tul_time)
    LinearLayout llTulTime;

    @BindView(R.id.ll_tul_start_time)
    LinearLayout llTulStartTime;
    @BindView(R.id.tv_tul_start_time)
    TextView tvTulStartTime;
    @BindView(R.id.tv_tul_start_time_no)
    TextView tvTulStartTimeNo;

    @BindView(R.id.ll_tul_end_time)
    LinearLayout llTulEndTime;
    @BindView(R.id.tv_tul_end_time)
    TextView tvTulEndTime;
    @BindView(R.id.tv_tul_end_time_no)
    TextView tvTulEndTimeNo;

    @BindView(R.id.tv_tul_rules)
    TextView tvTulRules;
    @BindView(R.id.tv_tul_additional_prices)
    TextView tvTulAdditionalPrices;
    @BindView(R.id.tv_tul_reviews_rating)
    TextView tvTulReviewsRating;
    @BindView(R.id.tv_tul_cancellation_policies)
    TextView tvTulCancellationPolices;
    @BindView(R.id.tv_tul_preferences)
    TextView tvTulPreferences;
    @BindView(R.id.tv_tul_linked_account2)
    TextView tvTulLinkedAccounthidden;

    @BindView(R.id.ll_check_availbilty)
    LinearLayout llCheckAvailbilty;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_per_day)
    TextView tvPerDay;
    @BindView(R.id.tv_availbilty)
    TextView tvAvailbilty;


    @BindView(R.id.tv_pause_booking)
    TextView tvPauseBooking;
//    @BindView(R.id.tv_pause)
//    TextView tvPause;

    @BindView(R.id.ll_cancel_booking)
    LinearLayout llCancelBooking;
    @BindView(R.id.tv_cancellation_charges)
    TextView tvCancellationCharges;
    @BindView(R.id.tv_charges)
    TextView tvCharges;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    @BindView(R.id.txt_cancel_lent)
    TextView txtCancelLent;

    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.view6)
    View view6;
    @BindView(R.id.view7)
    View view7;

    @BindView(R.id.txt_discount)
    TextView txtDiscount;

    SupportMapFragment mapFragment;
    @BindView(R.id.tv_see_more)
    TextView tvSeeMore;
    @BindView(R.id.ll_more_tuls)
    LinearLayout llMoreTuls;
    @BindView(R.id.tv_more_tuls)
    TextView tvMoreTuls;
    @BindView(R.id.rv_more_tuls)
    RecyclerView rvMoreTuls;
    boolean calendarTulId = false;
    private boolean isHideToolbarView = false;
    private BookTulModel.ResponseBean mBookingTulModel;
    private ViewTulModel.ResponseBean mViewTulModel;
    private boolean mShowOptions = true;

    private int mReportType;


    @Override
    protected int getContentView() {
        return R.layout.activity_listing_tul_detail;
    }

    @Override
    protected void initUI() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back_w);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        collapsingToolbar.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(this);

        CollapsingToolbarLayout.LayoutParams imgParms = new CollapsingToolbarLayout.LayoutParams(mWidth, mHeight / 3);
        vpTul.setLayoutParams(imgParms);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        txtTulTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtTulTitle.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        srbTul.setPadding(0, 0, mWidth / 32, 0);

        txtOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

        imgCall.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        LinearLayout.LayoutParams nameParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 32);
        llName.setLayoutParams(nameParms);

        txtOwnerName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

        LinearLayout.LayoutParams profileParms = new LinearLayout.LayoutParams(mWidth / 8, mWidth / 8);
        profileParms.setMargins(mWidth / 64, 0, mWidth / 32, 0);
        imgProfile.setLayoutParams(profileParms);


        tvAboutTulTitle.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 64);
        tvAboutTulTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvAboutTulDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvAboutTulDetail.setPadding(mWidth / 32, 0, mWidth / 32, mHeight / 32);

        tvAmenitiestitle.setPadding(mWidth / 32, mHeight / 64, 0, mWidth / 64);
        tvAmenitiestitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvAddressTitle.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 64);
        tvAddressTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulStartTime.setPadding(mWidth / 32, mHeight / 32, 0, 0);
        tvTulStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulStartTimeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvTulStartTimeNo.setPadding(mWidth / 32, 0, 0, mHeight / 32);

        tvTulEndTime.setPadding(0, mHeight / 32, mWidth / 32, 0);
        tvTulEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulEndTimeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvTulEndTimeNo.setPadding(0, 0, mWidth / 32, mHeight / 32);

        llCheckAvailbilty.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);

        tvTulRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulRules.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulAdditionalPrices.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);
        tvTulAdditionalPrices.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvTulReviewsRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulReviewsRating.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulCancellationPolices.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulCancellationPolices.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulPreferences.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulPreferences.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulLinkedAccounthidden.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.055));
        tvTulLinkedAccounthidden.setPadding(mWidth / 32, mHeight / 20, mWidth / 32, mHeight / 20);

        LinearLayout.LayoutParams starParms = new LinearLayout.LayoutParams((int) (mWidth * .4), mHeight / 32);
        rlStars.setLayoutParams(starParms);

        RelativeLayout.LayoutParams rlStarParms = new RelativeLayout.LayoutParams((int) (mWidth * .4), mHeight / 32);
        llStars.setLayoutParams(rlStarParms);

        LinearLayout.LayoutParams mapParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / 4);
        mapParms.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        rlMap.setLayoutParams(mapParms);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        view2.setLayoutParams(layoutParams);
        view3.setLayoutParams(layoutParams);
        view4.setLayoutParams(layoutParams);
        view5.setLayoutParams(layoutParams);
        view6.setLayoutParams(layoutParams);
        view7.setLayoutParams(layoutParams);
        viewAmenities.setLayoutParams(layoutParams);

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPerDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvAvailbilty.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvAvailbilty.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvAvailbilty.setEnabled(false);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 32);

        llCheckAvailbilty.setLayoutParams(layoutParams1);
        llCheckAvailbilty.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);

        /// Cancel Layouts...
        llCancelBooking.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);
        llCancelBooking.setLayoutParams(layoutParams1);

        tvCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvCancellationCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvCancel.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvCancel.setEnabled(false);

        txtCancelLent.setPadding(0, mWidth / 24, 0, mWidth / 24);
        txtCancelLent.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtCancelLent.setLayoutParams(layoutParams1);
        ///

        tvPauseBooking.setPadding(0, mWidth / 24, 0, mWidth / 24);
        tvPauseBooking.setLayoutParams(layoutParams1);

        btnShortlist.setVisibility(View.GONE);
        btnShortlist.setUnlikeDrawableRes(R.mipmap.ic_shortlist);
        btnShortlist.setLikeDrawableRes(R.mipmap.ic_shortlisted);

        tvSeeMore.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvSeeMore.setPadding(0, 0, mWidth / 32, mHeight / 32);

        tvSeeMore.setVisibility(View.GONE);

        llMoreTuls.setVisibility(View.GONE);

        tvMoreTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvMoreTuls.setPadding(mWidth / 32, mHeight / 32, 0, mHeight / 60);

        rvMoreTuls.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        tvPauseBooking.setVisibility(View.GONE);
        llCancelBooking.setVisibility(View.GONE);
        llCheckAvailbilty.setVisibility(View.GONE);

        flInterests.setPadding(mWidth / 64, 0, mWidth / 64, mWidth / 32);

        txtDiscount.setPadding(mWidth / 32, 0, 0, mWidth / 32);
        txtDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

    }

    @Override
    protected void onCreateStuff() {
        imgProfile.setEnabled(false);
        llProfile.setEnabled(false);
        imgChat.setVisibility(View.GONE);
        imgCall.setVisibility(View.GONE);
        if (getIntent().hasExtra("booking_data")) {/// getting data from booking listing
            mShowOptions = false;
            mBookingTulModel = getIntent().getParcelableExtra("booking_data");
            if (connectedToInternet())
                hitAPI();
            else {
                showInternetAlert(llData);
                pbLoad.setVisibility(View.GONE);
            }
            setBookingData();
            hideViews();
        } else if (getIntent().hasExtra("calendar_tul_id")) {
            calendarTulId = true;
            if (connectedToInternet()) {
                svMain.setVisibility(View.INVISIBLE);
                showProgress();
                hitAPI();
            } else
                showInternetAlert(llData);
        } else if (getIntent().hasExtra("booking_id")) {
            calendarTulId = false;
            if (connectedToInternet()) {
                svMain.setVisibility(View.INVISIBLE);
                showProgress();
                hitAPI();
            } else
                showInternetAlert(llData);
        }

        btnShortlist.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (connectedToInternet()) {
                    hitShortListAPI();
                    NearByTulListingModel.ResponseBean tulItem = new NearByTulListingModel.ResponseBean();
                    tulItem.setId(mViewTulModel.getId());
                    tulItem.setTitle(mViewTulModel.getTitle());
                    tulItem.setOwner(mViewTulModel.getOwner());
                    tulItem.setOwner_pic(mViewTulModel.getOwner_pic());
                    tulItem.setPrice(mViewTulModel.getPrice());
                    tulItem.setUser_id(mViewTulModel.getUser_id());
                    tulItem.setRating(mViewTulModel.getRating());
                    tulItem.setAttachment(mViewTulModel.getAttachment());
                    db.addShortListedTul(tulItem);
                    for (AttachmentModel attachmentItem : mViewTulModel.getAttachment()) {
                        db.addShortListAttachment(attachmentItem);
                    }
                } else {
                    showInternetAlert(llData);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (connectedToInternet()) {
                    hitShortListAPI();
                    db.deleteShortListTul(String.valueOf(mViewTulModel.getId()));
                } else {
                    showInternetAlert(llData);
                }
            }
        });

    }


    @Override
    protected void initListener() {
        tvTulRules.setOnClickListener(this);
        tvTulAdditionalPrices.setOnClickListener(this);
        tvTulReviewsRating.setOnClickListener(this);
        tvTulCancellationPolices.setOnClickListener(this);
        tvTulPreferences.setOnClickListener(this);
        tvAvailbilty.setOnClickListener(this);
        tvSeeMore.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        txtCancelLent.setOnClickListener(this);
        llStars.setOnClickListener(this);
        imgChat.setOnClickListener(this);
        imgCall.setOnClickListener(this);
    }


    private void hideViews() {
        llMoreTuls.setVisibility(View.GONE);
        llCheckAvailbilty.setVisibility(View.GONE);
        if (mBookingTulModel.getLender_status() == 0 && mBookingTulModel.getBorrower_status() == 0) {
            if (getIntent().getStringExtra("cancel_status").equals("1")) {
                txtCancelLent.setVisibility(View.VISIBLE);
                mReportType = Constants.LENDER_REPORT;
            } else {
                llCancelBooking.setVisibility(View.VISIBLE);
                mReportType = Constants.USER_REPORT;
            }
        } else
            llCancelBooking.setVisibility(View.GONE);


    }

    private void setBookingData() {
        toolbarHeaderView.bindTo(mBookingTulModel.getTitle());
        txtTulTitle.setText(mBookingTulModel.getTitle());
        if (getIntent().getStringExtra("cancel_status").equals("1")) {
            /// entering by tul lent section.
            txtOwner.setText("RENTED TO");
            txtOwnerName.setText(mBookingTulModel.getBorrower());
            Picasso.with(mContext).load(mBookingTulModel.getBorrower_pic())
                    .resize(mWidth / 8, mWidth / 8).centerCrop().placeholder(R.mipmap.ic_add_image)
                    .transform(new CircleTransform())
                    .into(imgProfile);
        } else {
            txtOwnerName.setText(mBookingTulModel.getOwner());
            Picasso.with(mContext).load(mBookingTulModel.getOwner_pic())
                    .resize(mWidth / 8, mWidth / 8).centerCrop().placeholder(R.mipmap.ic_add_image)
                    .transform(new CircleTransform())
                    .into(imgProfile);
        }
        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) mBookingTulModel.getAttachment()));
        cpIndicator.setViewPager(vpTul);
    }

    private void hitShortListAPI() {
        if (utils.getInt(Constants.ISGUEST, 0) == 1) {
            loginFirst(mContext);
            if (btnShortlist.isLiked()) {
                btnShortlist.setLiked(false);
            } else {
                btnShortlist.setLiked(true);
            }
        } else if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
            userBlockDialog(mContext);
            if (btnShortlist.isLiked()) {
                btnShortlist.setLiked(false);
            } else {
                btnShortlist.setLiked(true);
            }
        } else {
            Call<DemoModel> call;
            if (getIntent().hasExtra("booking_data"))
                call = RetrofitClient.getInstance().shortListTul(utils.getString("access_token", ""), mBookingTulModel.getTool_id());
            else
                call = RetrofitClient.getInstance().shortListTul(utils.getString("access_token", ""), mViewTulModel.getId());
            call.enqueue(new retrofit2.Callback<DemoModel>() {
                @Override
                public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                    if (response.body().response != null) {

                    } else {
                        if (response.body().error.getCode().equals(errorAccessToken)) {
                            moveToSplash();
                        } else {
                            showAlert(llData, response.body().error.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<DemoModel> call, Throwable t) {
                    showAlert(llData, t.getLocalizedMessage());
                }
            });

        }

    }

    private void hitAPI() {
        Call<ViewTulModel> call = null;
        if (getIntent().hasExtra("booking_data"))
            call = RetrofitClient.getInstance().getTulDetailByBookingId(utils.getString("access_token", ""),
                    mBookingTulModel.getBooking_id());
        else if (getIntent().hasExtra("calendar_tul_id"))
            call = RetrofitClient.getInstance().getTulDetailByBookingId(utils.getString("access_token", ""),
                    getIntent().getIntExtra("calendar_tul_id", 0));
        else if (getIntent().hasExtra("booking_id"))
            call = RetrofitClient.getInstance().getTulDetailByBookingId(utils.getString("access_token", ""),
                    Integer.parseInt(getIntent().getStringExtra("booking_id")));
        call.enqueue(new retrofit2.Callback<ViewTulModel>() {
            @Override
            public void onResponse(Call<ViewTulModel> call, Response<ViewTulModel> response) {
                if (response.body().getResponse() != null) {
                    mViewTulModel = response.body().getResponse();
                    tvAvailbilty.setEnabled(true);
                    imgCall.setVisibility(View.VISIBLE);
                    imgChat.setVisibility(View.VISIBLE);
                    if (getIntent().hasExtra("booking_data")) {
                        setTulData(response.body().getResponse());
                        llData.setVisibility(View.VISIBLE);
                        pbLoad.setVisibility(View.GONE);
                        tvCancel.setEnabled(true);
                    } else if (getIntent().hasExtra("calendar_tul_id")) {
                        setFullTulData(response.body().getResponse());
                        hideProgress();
                    } else if (getIntent().hasExtra("booking_id")) {
                        setFullTulData(response.body().getResponse());
                        hideProgress();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        if (getIntent().hasExtra("data")) {
                            pbLoad.setVisibility(View.GONE);
                        } else {
                            hideProgress();
                        }
                        showAlert(llData, response.body().error.getMessage());
                    }
                    if (response.body().error.getCode().equals("401")) {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(BookingTulDetailActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewTulModel> call, Throwable t) {
                showAlert(llData, t.getLocalizedMessage());
                if (getIntent().hasExtra("data")) {
                    pbLoad.setVisibility(View.GONE);
                } else {
                    hideProgress();
                }

            }
        });
    }

    private void setTulData(ViewTulModel.ResponseBean responseBean) {

        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) responseBean.getAttachment()));
        cpIndicator.setViewPager(vpTul);

        tvAddressTitle.setText(responseBean.getAddress());
        if (responseBean.getProduct_type() == 1) {
            try {
                tvTulStartTimeNo.setText(Constants.convertDate(responseBean.getDelivery_date()));
                tvTulEndTimeNo.setText(Constants.convertDate(responseBean.getReturn_date()));
            } catch (Exception e) {
                Log.e("Date Exce = ", e.getLocalizedMessage());
            }
        } else {
            try {
                tvTulStartTimeNo.setText(Constants.convertDate(responseBean.getDelivery_date()));
                tvTulEndTimeNo.setText(Constants.addOneDayToDate(responseBean.getReturn_date()));
            } catch (Exception e) {
                Log.e("Date Exce = ", e.getLocalizedMessage());
            }

        }

        if (getIntent().hasExtra("cancel_status")) {
            if (Double.parseDouble(mViewTulModel.getDiscount().replace(",",".")) != 0) {
                txtDiscount.setVisibility(View.VISIBLE);
                if (getIntent().getStringExtra("cancel_status").equals("2"))
                    txtDiscount.setText(getString(R.string.discount_availed) + mViewTulModel.getCurrency().replace(",",".") + mViewTulModel.getDiscount().replace(",","."));
                else
                    txtDiscount.setText(getString(R.string.discount_offered) + mViewTulModel.getCurrency().replace(",",".")+ mViewTulModel.getDiscount().replace(",","."));
            }
        }


        tvAboutTulTitle.setText(getString(R.string.about_this) + responseBean.getCategory_name());
        tvAboutTulTitle.setAllCaps(true);
        if (responseBean.getCategory_name().equalsIgnoreCase("OTHERS")) {
            tvAboutTulTitle.setText("About This BACKYARD");
        }

        tvAboutTulDetail.setText(responseBean.getDescription());
        tvAboutTulDetail.post(new Runnable() {
            @Override
            public void run() {
                int lineCnt = tvAboutTulDetail.getLineCount();
                if (lineCnt > 3) {
                    makeTextViewResizable(tvAboutTulDetail, 3, "...Read More", true);
                }
            }
        });


        if (responseBean.is_wishlisted() == 1)
            btnShortlist.setLiked(true);
        else
            btnShortlist.setLiked(false);

        /// calculate cancellation charges
        double percentage = Double.parseDouble(mViewTulModel.getCancel_percentage());
        double price = Double.parseDouble(mViewTulModel.getPrice());
        double cancellationCharges = (price * percentage) / 100;
        tvCharges.setText(mViewTulModel.getCurrency() + String.format("%.2f", cancellationCharges));
        ///

        if (responseBean.getAminities().size() > 0) {
            flInterests.removeAllViews();
            for (String item : responseBean.getAminities()) {
                flInterests.addView(inflateView(item));
            }
        } else
            tvAmenitiestitle.setVisibility(View.GONE);

        if (utils.getString("user_id", "").equals(String.valueOf(mViewTulModel.getUser_id()))) {
            btnShortlist.setVisibility(View.GONE);
            mShowOptions = false;
        } else {
            btnShortlist.setVisibility(View.VISIBLE);
            mShowOptions = true;
        }

        mapFragment.getMapAsync(this);
        invalidateOptionsMenu();
    }


    private void setFullTulData(ViewTulModel.ResponseBean responseBean) {
        if (getIntent().hasExtra("calendar_tul_id")) {
            displayProfileData(responseBean.getBorrower(), responseBean.getBorrower_pic(), "RENTED TO");
            if (Double.parseDouble(mViewTulModel.getDiscount()) != 0) {
                txtDiscount.setVisibility(View.VISIBLE);
                txtDiscount.setText(getString(R.string.discount_offered) + mViewTulModel.getCurrency() + mViewTulModel.getDiscount());
            }
        } else if (getIntent().hasExtra("booking_id")) {
            if (getIntent().getStringExtra("path").equals("show_owner")) {
                displayProfileData(responseBean.getOwner(), responseBean.getOwner_pic(), getString(R.string.owner));
            } else {
                displayProfileData(responseBean.getBorrower(), responseBean.getBorrower_pic(), getString(R.string.rented_to));
            }
        }

        svMain.setVisibility(View.VISIBLE);
        pbLoad.setVisibility(View.GONE);
        llData.setVisibility(View.VISIBLE);
        toolbarHeaderView.bindTo(responseBean.getTitle());
        txtTulTitle.setText(responseBean.getTitle());

        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) responseBean.getAttachment()));
        cpIndicator.setViewPager(vpTul);
        tvPrice.setText(responseBean.getCurrency() + responseBean.getPrice());

        tvAddressTitle.setText(responseBean.getAddress());

        tvAboutTulTitle.setText(getString(R.string.about_this)+ responseBean.getCategory_name());
        if (responseBean.getCategory_name().equalsIgnoreCase("OTHERS")) {
            tvAboutTulTitle.setText("About This BACKYARD");
        }
        tvAboutTulTitle.setAllCaps(true);

        tvAboutTulDetail.setText(responseBean.getDescription());
        tvAboutTulDetail.post(new Runnable() {
            @Override
            public void run() {
                int lineCnt = tvAboutTulDetail.getLineCount();
                if (lineCnt > 3) {
                    makeTextViewResizable(tvAboutTulDetail, 3, getString(R.string.read_more), true);
                }
            }
        });

        try {
            if (!getIntent().hasExtra("calendar_tul_id")) {
                tvTulStartTimeNo.setText(Constants.convertDate(responseBean.getDelivery_date()));
                tvTulEndTimeNo.setText(Constants.addOneDayToDate(responseBean.getReturn_date()));
            } else if (getIntent().hasExtra("booking_id")) {
                try {
                    tvTulStartTimeNo.setText(Constants.convertDate(responseBean.getDelivery_date()));
                    tvTulEndTimeNo.setText(Constants.addOneDayToDate(responseBean.getReturn_date()));
                } catch (Exception e) {
                    Log.e("Date = ", e + "");
                }
            } else {
                tvTulStartTimeNo.setText(Constants.convertDate(responseBean.getDelivery_date()));
                tvTulEndTimeNo.setText(Constants.addOneDayToDate(responseBean.getReturn_date()));
            }
        } catch (Exception e) {
            Log.e("Date Exce = ", e.getLocalizedMessage());
        }

        if (responseBean.is_wishlisted() == 1)
            btnShortlist.setLiked(true);
        else
            btnShortlist.setLiked(false);

        /// calculate cancellation charges
        double percentage = Double.parseDouble(mViewTulModel.getCancel_percentage());
        double price = Double.parseDouble(mViewTulModel.getPrice());
        double cancellationCharges = (price * percentage) / 100;
        tvCharges.setText(mViewTulModel.getCurrency() + String.format("%.2f", cancellationCharges));
        ///

        if (responseBean.getAminities().size() > 0) {
            flInterests.removeAllViews();
            for (String item : responseBean.getAminities()) {
                flInterests.addView(inflateView(item));
            }
        } else
            tvAmenitiestitle.setVisibility(View.GONE);

        if (utils.getString("user_id", "").equals(String.valueOf(mViewTulModel.getUser_id()))) {
            btnShortlist.setVisibility(View.GONE);
            mShowOptions = false;
        } else {
            btnShortlist.setVisibility(View.VISIBLE);
            mShowOptions = true;
        }

        mapFragment.getMapAsync(this);
        invalidateOptionsMenu();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            moveBack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Context getContext() {
        return BookingTulDetailActivity.this;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_call:
                if (ContextCompat.checkSelfPermission(BookingTulDetailActivity.this, CALL_PHONE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(BookingTulDetailActivity.this, new String[]{CALL_PHONE}, PHONE);
                } else {
                    onCallCustomer();
                }
                break;
            case R.id.img_chat:
                if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                    if (getIntent().hasExtra("booking_data")) {/// getting data from booking listing
                        if (getIntent().getStringExtra("cancel_status").equals("1")) {
                            /// entering by tul lent section.
                            CustomLoadingDialog.getLoader().showLoader(mContext);
                            int sortArray[] = new int[]{
                                    mViewTulModel.getBorrower_id(), Integer.parseInt(utils.getString("user_id", ""))
                            };
                            Arrays.sort(sortArray);
                            String dialogueId = String.valueOf(sortArray[0]) + "," + String.valueOf(sortArray[1]);
                            checkDialogBorrower(dialogueId, mViewTulModel);
                        } else {
//                            CustomLoadingDialog.getLoader().showLoader(mContext);
                            int sortArray[] = new int[]{
                                    mViewTulModel.getUser_id(), Integer.parseInt(utils.getString("user_id", ""))
                            };
                            Arrays.sort(sortArray);
                            String dialogueId = String.valueOf(sortArray[0]) + "," + String.valueOf(sortArray[1]);
                            if (connectedToInternet()) {
                                CustomLoadingDialog.getLoader().showLoader(mContext);
                                checkDialogmy(dialogueId, mViewTulModel);
                            } else {
                                showInternetAlert(llCancelBooking);
                            }

//                            checkDialogmy(dialogueId, mViewTulModel);
                        }
                    } else if (calendarTulId) {
                        CustomLoadingDialog.getLoader().showLoader(mContext);
                        int sortArray[] = new int[]{
                                mViewTulModel.getBorrower_id(), Integer.parseInt(utils.getString("user_id", ""))
                        };
                        Arrays.sort(sortArray);
                        String dialogueId = String.valueOf(sortArray[0]) + "," + String.valueOf(sortArray[1]);
                        checkDialogBorrower(dialogueId, mViewTulModel);

                    } else if (!calendarTulId) {
                        if (getIntent().getStringExtra("path").equals("show_owner")) {
//                            CustomLoadingDialog.getLoader().showLoader(mContext);
                            int sortArray[] = new int[]{
                                    mViewTulModel.getUser_id(), Integer.parseInt(utils.getString("user_id", ""))
                            };
                            Arrays.sort(sortArray);
                            String dialogueId = String.valueOf(sortArray[0]) + "," + String.valueOf(sortArray[1]);
//                            checkDialogmy(dialogueId, mViewTulModel);

                            if (connectedToInternet()) {
                                CustomLoadingDialog.getLoader().showLoader(mContext);
                                checkDialogmy(dialogueId, mViewTulModel);
                            } else {
                                showInternetAlert(llCancelBooking);
                            }
                        } else {
//                            CustomLoadingDialog.getLoader().showLoader(mContext);
                            int sortArray[] = new int[]{
                                    mViewTulModel.getBorrower_id(), Integer.parseInt(utils.getString("user_id", ""))
                            };
                            Arrays.sort(sortArray);
                            String dialogueId = String.valueOf(sortArray[0]) + "," + String.valueOf(sortArray[1]);

                            if (connectedToInternet()) {
                                CustomLoadingDialog.getLoader().showLoader(mContext);
                                checkDialogBorrower(dialogueId, mViewTulModel);
                            } else {
                                showInternetAlert(llCancelBooking);
                            }
                        }
                    }
                } else {
                    loginFirst(this);
                }
                break;
//            case R.id.tv_pause:
//                Toast.makeText(mContext, "work in progress", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.tv_cancel:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage(getString(R.string.Cancellation_charges_will_be_deducted_from_booking_amount_Do_you_want_to_proceed))
                        .setCancelable(false)
                        .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(mContext, SignatureActivity.class);
                                intent.putExtra("tulData", mBookingTulModel);
                                intent.putExtra("path", "1");
                                intent.putExtra("cancel_status", getIntent().getStringExtra("cancel_status"));
                                intent.putExtra("tool_id", String.valueOf(mBookingTulModel.getTool_id()));
                                intent.putExtra("booking_id", String.valueOf(mBookingTulModel.getBooking_id()));
                                startActivityForResult(intent, CANCEL);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert1 = builder1.create();
                alert1.show();
                break;
            case R.id.txt_cancel_lent:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.cancel_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(mContext, SignatureActivity.class);
                                intent.putExtra("path", "1");
                                intent.putExtra("cancel_status", getIntent().getStringExtra("cancel_status"));
                                intent.putExtra("tool_id", String.valueOf(mBookingTulModel.getTool_id()));
                                intent.putExtra("booking_id", String.valueOf(mBookingTulModel.getBooking_id()));
                                startActivityForResult(intent, CANCEL);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.tv_tul_rules:
                ArrayList<String> mRulesArray = (ArrayList<String>) mViewTulModel.getRules();
                intent = new Intent(this, TulRulesActivity.class);
                intent.putStringArrayListExtra("data", mRulesArray);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_additional_prices:
                ViewTulModel.ResponseBean.AdditionalPriceBean additionalPriceBean = mViewTulModel.getAdditional_price();
                additionalPriceBean.setCurrency(mViewTulModel.getCurrency());
                intent = new Intent(this, TulAdditionalChargesActivity.class);
                intent.putExtra("data", additionalPriceBean);
                intent.putExtra("user_id", mViewTulModel.getUser_id());
                intent.putExtra("type", String.valueOf(mViewTulModel.getProduct_type()));
                intent.putExtra("transaction_percentage", mViewTulModel.getTransaction_percentage());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_reviews_rating:
                intent = new Intent(this, TulReviewRatingsActivity.class);
                intent.putExtra("tool_id", mViewTulModel.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_cancellation_policies:
                intent = new Intent(this, TulCancellationPolicies.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_preferences:
                ViewTulModel.ResponseBean.PreferencesBean prefrencesBean = mViewTulModel.getPreferences();
                intent = new Intent(this, TulViewPreferencesActivity.class);
                intent.putExtra("data", prefrencesBean);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.img_profile:
                /// not implemented break because same functionality in both cases.

            case R.id.ll_profile:
                if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                    if (!utils.getString("user_id", "").equals(mViewTulModel.getUser_id())) {
                        if (Constants.PROFILE_ID != mViewTulModel.getUser_id()) {
                            Constants.TUL_ID = mViewTulModel.getId();
                            intent = new Intent(mContext, OtherUserProfileActivity.class);
                            intent.putExtra("other_user_id", mViewTulModel.getUser_id());
                            intent.putExtra("other_user_name", mViewTulModel.getOwner());
                            intent.putExtra("other_user_pic", mViewTulModel.getOwner_pic());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else {
                            finish();
                            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                        }
                    }
                } else {
                    loginFirst(this);
                }
                break;
        }
    }

    private void displayProfileData(String name, String borrowerPic, String status) {
        txtOwner.setText(status);
        txtOwner.setAllCaps(true);
        txtOwnerName.setText(name);
        Picasso.with(mContext).load(borrowerPic)
                .resize(mWidth / 8, mWidth / 8)
                .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                .transform(new CircleTransform())
                .into(imgProfile);
    }

    View inflateView(final String amenities) {
        View interestChip = LayoutInflater.from(mContext).inflate(R.layout.layout_amenities, null, false);

        FlowLayout.LayoutParams innerParms = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        innerParms.setMargins(mWidth / 32, 0, 0, mWidth / 32);
        final LinearLayout llMain = (LinearLayout) interestChip.findViewById(R.id.ll_main);
        llMain.setLayoutParams(innerParms);

        final TextView txtAmenities = (TextView) interestChip.findViewById(R.id.txt_amenities);
        txtAmenities.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtAmenities.setText(amenities);
        txtAmenities.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);
        txtAmenities.setTextColor(Color.WHITE);

        return interestChip;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("Map Ready = ", "Yes");
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mContext, R.raw.style));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(mViewTulModel.getLatitute()), Double.parseDouble(mViewTulModel.getLongitude()))).zoom(14).build();
        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mViewTulModel.getLatitute())
                , Double.parseDouble(mViewTulModel.getLongitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_loc_pin)));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mViewTulModel != null) {
            if (mShowOptions) {
                menu.add(R.string.report).setIcon(R.mipmap.ic_report).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        alertReportDialog();
                        return true;
                    }
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }


    private void alertReportDialog() {
        if (utils.getInt(Constants.ISGUEST, 0) == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.REPORT);
            builder.setMessage(getString(R.string.report_msg))
                    .setCancelable(false)
                    .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (connectedToInternet()) {
                                hitReportAPI();
                                dialog.cancel();
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            } else
                                showInternetAlert(llData);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else {
            loginFirst(this);
        }
    }

    private void hitReportAPI() {
        if (utils.getInt(Constants.ISGUEST, 0) == 0) {
            if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                userBlockDialog(mContext);
            } else {
                Call<DemoModel> call;
                call = RetrofitClient.getInstance().ReportTul(utils.getString("access_token", ""),
                        mViewTulModel.getId(),
                        mReportType,
                        mViewTulModel.getUser_id(),
                        mViewTulModel.getBorrower_id(),0);
                call.enqueue(new retrofit2.Callback<DemoModel>() {
                    @Override
                    public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                        if (response.body().response != null) {
                            showAlert(llData, response.body().response.getMessage());
                        } else {
                            if (response.body().error.getCode().equals(errorAccessToken)) {
                                moveToSplash();
                            } else {
                                showAlert(llData, response.body().error.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DemoModel> call, Throwable t) {
                        hideProgress();
                        showAlert(llData, t.getLocalizedMessage());
                    }
                });
            }
        } else {
            loginFirst(mContext);
        }
    }


    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                            viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = "";
                    if (lineEndIndex < 13) {
                        text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    } else {
                        text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    }
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                            viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                            viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new BookingTulDetailActivity.MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    new ShowMoreDialog(mContext, mWidth, mHeight, mViewTulModel.getDescription()).showDialog();
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);
        }
        return ssb;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CANCEL) {
                /// remove from booking array
                Intent intent = new Intent();
                intent.putExtra("position", getIntent().getIntExtra("position", 0));
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void onCallCustomer() {
        String phoneNo;
        if (getIntent().hasExtra("booking_data")) {
            if (getIntent().getStringExtra("cancel_status").equals("1"))
                phoneNo = "tel:" + mViewTulModel.getBorrower_country_code() + mViewTulModel.getBorrower_phone_number();
            else
                phoneNo = "tel:" + mViewTulModel.getCountry_code() + mViewTulModel.getPhone_number();
        } else if (getIntent().hasExtra("calendar_tul_id")) {
            phoneNo = "tel:" + mViewTulModel.getBorrower_country_code() + mViewTulModel.getBorrower_phone_number();
        } else {
            if (getIntent().getStringExtra("path").equals("show_owner"))
                phoneNo = "tel:" + mViewTulModel.getCountry_code() + mViewTulModel.getPhone_number();
            else
                phoneNo = "tel:" + mViewTulModel.getBorrower_country_code() + mViewTulModel.getBorrower_phone_number();

        }
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNo));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCallCustomer();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void updateProfileDialogsMy(final String dialogId, final String otherUserId) {
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child(Constants.USER_ENDPOINT).child(utils.getString("user_id", ""))
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);

        mRef.child(Constants.USER_ENDPOINT).child(otherUserId)
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);

        db.addDialog(dialogId);
    }

    //
    private void checkDialogmy(final String dialogueId, final ViewTulModel.ResponseBean responseBean) {

        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

        final Query query = mRef.child(Constants.CHAT_ENDPOINT).orderByChild("participant_ids").equalTo("" + dialogueId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                isChatDisabledMy(responseBean.getId(), dialogueId, responseBean, dataSnapshot);

//                if (dataSnapshot.getValue() == null) {
//                    regitserChatDialogMy(dialogueId, responseBean);
//                } else {
//                    ChatDialogModel mChat = null;
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        mChat = data.getValue(ChatDialogModel.class);
//                    }
//                    Intent inChat = new Intent(mContext, ChatActivity.class);
//                    inChat.putExtra("dialog_id", mChat.getChat_dialog_id());
//                    inChat.putExtra("name", responseBean.getOwner());
//                    inChat.putExtra("other_block_status", mChat.getBlock_status().get(String.valueOf(responseBean.getUser_id())));
//                    inChat.putExtra("my_block_status", mChat.getBlock_status().get(utils.getString("user_id", "")));
//
////                    updateProfileDialogs();
//                    mContext.startActivity(inChat);
//                    CustomLoadingDialog.getLoader().dismissLoader();
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print(databaseError);
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    void isChatDisabledMy(int id, final String dialogueId, final ViewTulModel.ResponseBean responseBean, final DataSnapshot dataSnapshot) {
        Call<CheckChatModel> call = RetrofitClient.getInstance().checkBookingChat(utils.getString("access_token", ""), String.valueOf(id), String.valueOf(responseBean.getUser_id()), utils.getString("user_id",""));
        call.enqueue(new retrofit2.Callback<CheckChatModel>() {
            @Override
            public void onResponse(Call<CheckChatModel> call, Response<CheckChatModel> response) {
                CustomLoadingDialog.getLoader().dismissLoader();
                if (response != null) {
                    if (response.body().getResponse().getIs_chat_block() == 0) {
                        if (dataSnapshot.getValue() == null) {
                            regitserChatDialogMy(dialogueId, responseBean);
                        } else {
                            ChatDialogModel mChat = null;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                mChat = data.getValue(ChatDialogModel.class);
                            }
                            Intent inChat = new Intent(mContext, ChatActivity.class);
                            inChat.putExtra("dialog_id", mChat.getChat_dialog_id());
                            inChat.putExtra("name", responseBean.getOwner());
                            inChat.putExtra("other_block_status", mChat.getBlock_status().get(String.valueOf(responseBean.getUser_id())));
                            inChat.putExtra("my_block_status", mChat.getBlock_status().get(utils.getString("user_id", "")));

                            mContext.startActivity(inChat);
                        }
                    } else {
                        if (response.body().getResponse().getUser_block_status() == 1) {
                            // I am Blocked
                            userBlockDialogUser(mContext);
                        } else if (response.body().getResponse().getOwner_block_status() == 1) {
                            //Other is Blocked
                            chatBlockDialogUser(mContext, getResources().getString(R.string.owner_is_blocked));
                        } else if (response.body().getResponse().getAdmin_pause_status() == 1) {
                            //Place Blocked
                            chatBlockDialogUser(mContext, getResources().getString(R.string.place_is_blocked));
                        }
                        utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getUser_block_status());

                    }
                } else {
                    showAlert(llData, response.body().error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckChatModel> call, Throwable t) {
                CustomLoadingDialog.getLoader().dismissLoader();
                showAlert(llCancelBooking, t.getMessage());
            }
        });
    }


    private void updateProfileDialogsBorrower(final String dialogId, final String otherUserId) {
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child(Constants.USER_ENDPOINT).child(utils.getString("user_id", ""))
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);

        mRef.child(Constants.USER_ENDPOINT).child(otherUserId)
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);

        db.addDialog(dialogId);
    }

    //
    private void checkDialogBorrower(final String dialogueId, final ViewTulModel.ResponseBean responseBean) {

        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

        final Query query = mRef.child(Constants.CHAT_ENDPOINT).orderByChild("participant_ids").equalTo("" + dialogueId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isChatDisabledBorrower(responseBean.getId(), dialogueId, responseBean, dataSnapshot);
//                if (dataSnapshot.getValue() == null) {
//                    regitserChatDialogBorrower(dialogueId, responseBean);
//                } else {
//                    ChatDialogModel mChat = null;
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        mChat = data.getValue(ChatDialogModel.class);
//                    }
//                    Intent inChat = new Intent(mContext, ChatActivity.class);
//                    inChat.putExtra("dialog_id", mChat.getChat_dialog_id());
//                    inChat.putExtra("name", responseBean.getBorrower());
//                    inChat.putExtra("other_block_status", mChat.getBlock_status().get(String.valueOf(responseBean.getBorrower_id())));
//                    inChat.putExtra("my_block_status", mChat.getBlock_status().get(utils.getString("user_id", "")));
//
////                    updateProfileDialogs();
//                    mContext.startActivity(inChat);
//                    CustomLoadingDialog.getLoader().dismissLoader();
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print(databaseError);
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    void isChatDisabledBorrower(int id, final String dialogueId, final ViewTulModel.ResponseBean responseBean, final DataSnapshot dataSnapshot) {
        Call<CheckChatModel> call = RetrofitClient.getInstance().checkBookingChat(utils.getString("access_token", ""), String.valueOf(id),  utils.getString("user_id",""), String.valueOf(responseBean.getBorrower_id()));
        call.enqueue(new retrofit2.Callback<CheckChatModel>() {
            @Override
            public void onResponse(Call<CheckChatModel> call, Response<CheckChatModel> response) {
                CustomLoadingDialog.getLoader().dismissLoader();
                if (response != null) {
                    if (response.body().getResponse().getIs_chat_block() == 0) {
                        if (dataSnapshot.getValue() == null) {
                            regitserChatDialogBorrower(dialogueId, responseBean);
                        } else {
                            ChatDialogModel mChat = null;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                mChat = data.getValue(ChatDialogModel.class);
                            }
                            Intent inChat = new Intent(mContext, ChatActivity.class);
                            inChat.putExtra("dialog_id", mChat.getChat_dialog_id());
                            inChat.putExtra("name", responseBean.getBorrower());
                            inChat.putExtra("other_block_status", mChat.getBlock_status().get(String.valueOf(responseBean.getBorrower_id())));
                            inChat.putExtra("my_block_status", mChat.getBlock_status().get(utils.getString("user_id", "")));

                            mContext.startActivity(inChat);
                        }
                    } else {
                        if (response.body().getResponse().getUser_block_status() == 1) {
                            // I am Blocked
                            userBlockDialogUser(mContext);
                        } else if (response.body().getResponse().getOwner_block_status() == 1) {
                            //Other is Blocked
                            chatBlockDialogUser(mContext, getResources().getString(R.string.owner_is_blocked));
                        } else if (response.body().getResponse().getAdmin_pause_status() == 1) {
                            //Place Blocked
                            chatBlockDialogUser(mContext, getResources().getString(R.string.place_is_blocked));
                        }
                    }
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getUser_block_status());
                } else {
                    showAlert(llData, response.body().error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckChatModel> call, Throwable t) {
                CustomLoadingDialog.getLoader().dismissLoader();
                showAlert(llCancelBooking, t.getMessage());
            }
        });
    }


    public void regitserChatDialogMy(String dialogueId, ViewTulModel.ResponseBean responseBean) {

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        String key = mRef.child(Constants.CHAT_ENDPOINT).push().getKey();

        long time = System.currentTimeMillis();
        Log.e("Current Time = ", time + "");
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        ChatDialogModel mChatDialog = new ChatDialogModel();

        //      particpantId = user_id;
        mChatDialog.setChat_dialog_id(key);
        mChatDialog.setLast_message(Constants.CHAT_REGEX);
        mChatDialog.setLast_message_id("");
        mChatDialog.setLast_message_sender_id("");
        mChatDialog.setLast_message_time(String.valueOf(new Date(time).getTime()));
        mChatDialog.setParticipant_ids(dialogueId);
        mChatDialog.setDialog_created_time(String.valueOf(new Date(time).getTime()));

        /// delete dialog time
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(utils.getString("user_id", ""), String.valueOf(new Date(time).getTime()));
        map.put(String.valueOf(responseBean.getUser_id()), String.valueOf(new Date(time).getTime()));
        mChatDialog.setDelete_dialog_time(map);

        /// access_token
        HashMap<String, String> mapAccessToken = new HashMap<String, String>();
        mapAccessToken.put(utils.getString("user_id", ""), utils.getString("access_token", ""));
        mapAccessToken.put(String.valueOf(responseBean.getUser_id()), responseBean.getAccess_token());
        mChatDialog.setAccess_token(mapAccessToken);

        /// platform status
        HashMap<String, String> mapPlatformStatus = new HashMap<String, String>();
        mapPlatformStatus.put(utils.getString("user_id", ""), String.valueOf(Constants.PLATFORM_STATUS));
        mapPlatformStatus.put(String.valueOf(responseBean.getUser_id()), responseBean.getPlatform_status());
        mChatDialog.setPlatform_status(mapPlatformStatus);

        /// profile_pic
        HashMap<String, String> mapProfilePic = new HashMap<String, String>();
        mapProfilePic.put(utils.getString("user_id", ""), utils.getString("profile_pic", ""));
        mapProfilePic.put(String.valueOf(responseBean.getUser_id()), responseBean.getOwner_pic());
        mChatDialog.setProfile_pic(mapProfilePic);

        /// device_token
        HashMap<String, String> mapDeviceToken = new HashMap<String, String>();
        mapDeviceToken.put(utils.getString("user_id", ""), utils.getString("device_token", ""));
        mapDeviceToken.put(String.valueOf(responseBean.getUser_id()), responseBean.getDevice_token());
        mChatDialog.setPush_token(mapDeviceToken);

        /// unread count
        HashMap<String, String> mapUnreadCount = new HashMap<String, String>();
        mapUnreadCount.put(utils.getString("user_id", ""), "0");
        mapUnreadCount.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setUnread_count(mapUnreadCount);

        /// name
        HashMap<String, String> mapName = new HashMap<String, String>();
        mapName.put(utils.getString("user_id", ""), utils.getString("user_name", ""));
        mapName.put(String.valueOf(responseBean.getUser_id()), responseBean.getOwner());
        mChatDialog.setName(mapName);

        /// mute
        HashMap<String, String> mapMute = new HashMap<String, String>();
        mapMute.put(utils.getString("user_id", ""), Constants.UNMUTE);
        mapMute.put(String.valueOf(responseBean.getUser_id()), Constants.UNMUTE);
        mChatDialog.setMute(mapMute);

        //block status
        HashMap<String, String> blockmap = new HashMap<String, String>();
        blockmap.put(utils.getString("user_id", ""), "0");
        blockmap.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setBlock_status(blockmap);

        Map<String, String> deleteDialogMap = new HashMap<String, String>();
        deleteDialogMap.put(utils.getString("user_id", ""), "0");
        deleteDialogMap.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setDelete_outer_dialog(deleteDialogMap);

        updateProfileDialogsMy(key, String.valueOf(responseBean.getUser_id()));

//        updateToServer(key, responseBean.getUser_id());

        mRef.child(Constants.CHAT_ENDPOINT).child(key).setValue(mChatDialog);
        FirebaseListeners.getInstance().startChatListener(key);
        db.addConversation(mChatDialog);

        dialogApi(key, String.valueOf(responseBean.getBorrower_id()));

        Intent inChat = new Intent(mContext, ChatActivity.class);
        inChat.putExtra("dialog_id", mChatDialog.getChat_dialog_id());
        inChat.putExtra("name", responseBean.getOwner());
        inChat.putExtra("other_block_status", mChatDialog.getBlock_status().get(responseBean.getUser_id()));
        inChat.putExtra("my_block_status", mChatDialog.getBlock_status().get(utils.getString("user_id", "")));


        mContext.startActivity(inChat);

        CustomLoadingDialog.getLoader().dismissLoader();
    }

    public void regitserChatDialogBorrower(String dialogueId, ViewTulModel.ResponseBean responseBean) {

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        String key = mRef.child(Constants.CHAT_ENDPOINT).push().getKey();

        long time = System.currentTimeMillis();
        Log.e("Current Time = ", time + "");
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        ChatDialogModel mChatDialog = new ChatDialogModel();

//      particpantId = user_id;
        mChatDialog.setChat_dialog_id(key);
        mChatDialog.setLast_message(Constants.CHAT_REGEX);
        mChatDialog.setLast_message_id("");
        mChatDialog.setLast_message_sender_id("");
        mChatDialog.setLast_message_time(String.valueOf(new Date(time).getTime()));
        mChatDialog.setParticipant_ids(dialogueId);
        mChatDialog.setDialog_created_time(String.valueOf(new Date(time).getTime()));

        /// delete dialog time
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(utils.getString("user_id", ""), String.valueOf(new Date(time).getTime()));
        map.put(String.valueOf(responseBean.getBorrower_id()), String.valueOf(new Date(time).getTime()));
        mChatDialog.setDelete_dialog_time(map);

        /// access_token
        HashMap<String, String> mapAccessToken = new HashMap<String, String>();
        mapAccessToken.put(utils.getString("user_id", ""), utils.getString("access_token", ""));
        mapAccessToken.put(String.valueOf(responseBean.getBorrower_id()), responseBean.getBorrower_access_token());
        mChatDialog.setAccess_token(mapAccessToken);

        /// platform status
        HashMap<String, String> mapPlatformStatus = new HashMap<String, String>();
        mapPlatformStatus.put(utils.getString("user_id", ""), String.valueOf(Constants.PLATFORM_STATUS));
        mapPlatformStatus.put(String.valueOf(responseBean.getBorrower_id()), responseBean.getBorrower_platform_status());
        mChatDialog.setPlatform_status(mapPlatformStatus);

        /// profile_pic
        HashMap<String, String> mapProfilePic = new HashMap<String, String>();
        mapProfilePic.put(utils.getString("user_id", ""), utils.getString("profile_pic", ""));
        mapProfilePic.put(String.valueOf(responseBean.getBorrower_id()), responseBean.getBorrower_pic());
        mChatDialog.setProfile_pic(mapProfilePic);

        /// device_token
        HashMap<String, String> mapDeviceToken = new HashMap<String, String>();
        mapDeviceToken.put(utils.getString("user_id", ""), utils.getString("device_token", ""));
        mapDeviceToken.put(String.valueOf(responseBean.getBorrower_id()), responseBean.getBorrower_device_token());
        mChatDialog.setPush_token(mapDeviceToken);

        /// unread count
        HashMap<String, String> mapUnreadCount = new HashMap<String, String>();
        mapUnreadCount.put(utils.getString("user_id", ""), "0");
        mapUnreadCount.put(String.valueOf(responseBean.getBorrower_id()), "0");
        mChatDialog.setUnread_count(mapUnreadCount);

        /// name
        HashMap<String, String> mapName = new HashMap<String, String>();
        mapName.put(utils.getString("user_id", ""), utils.getString("user_name", ""));
        mapName.put(String.valueOf(responseBean.getBorrower_id()), responseBean.getBorrower());
        mChatDialog.setName(mapName);

        /// mute
        HashMap<String, String> mapMute = new HashMap<String, String>();
        mapMute.put(utils.getString("user_id", ""), Constants.UNMUTE);
        mapMute.put(String.valueOf(responseBean.getBorrower_id()), Constants.UNMUTE);
        mChatDialog.setMute(mapMute);

        //block status
        HashMap<String, String> blockmap = new HashMap<String, String>();
        blockmap.put(utils.getString("user_id", ""), "0");
        blockmap.put(String.valueOf(responseBean.getBorrower_id()), "0");
        mChatDialog.setBlock_status(blockmap);

        //Delete Dialog status
        Map<String, String> deleteDialogMap = new HashMap<String, String>();
        deleteDialogMap.put(utils.getString("user_id", ""), "0");
        deleteDialogMap.put(String.valueOf(responseBean.getBorrower_id()), "0");
        mChatDialog.setDelete_outer_dialog(deleteDialogMap);

        updateProfileDialogsBorrower(key, String.valueOf(responseBean.getBorrower_id()));

//        updateToServer(key, responseBean.getUser_id());

        mRef.child(Constants.CHAT_ENDPOINT).child(key).setValue(mChatDialog);
        FirebaseListeners.getInstance().startChatListener(key);
        db.addConversation(mChatDialog);

        dialogApi(key, String.valueOf(responseBean.getBorrower_id()));

        Intent inChat = new Intent(mContext, ChatActivity.class);
        inChat.putExtra("dialog_id", mChatDialog.getChat_dialog_id());
        inChat.putExtra("name", responseBean.getBorrower());
        inChat.putExtra("other_block_status", mChatDialog.getBlock_status().get(responseBean.getBorrower_id()));
        inChat.putExtra("my_block_status", mChatDialog.getBlock_status().get(utils.getString("user_id", "")));

        mContext.startActivity(inChat);

        CustomLoadingDialog.getLoader().dismissLoader();
    }

    void dialogApi(String dialogId, String otherUserId) {
        Call<String> call = RetrofitClient.getInstance().updateDialog(utils.getString("access_token", ""), dialogId, otherUserId);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public class MySpannable extends ClickableSpan {
        private boolean isUnderline = false;

        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setTextSize(Constants.dpToPx(12));
            ds.setTypeface(Typeface.DEFAULT_BOLD);
            ds.setColor(Color.parseColor("#000000"));
        }

        @Override
        public void onClick(View widget) {

        }
    }
}
