package com.app.backyard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import adapters.TimeSlotAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.AvailbiltyModel;
import model.BookedSlotsModel;
import model.TransacrionPercentageModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import static adapters.TimeSlotAdapter.temPosition;

/**
 * Created by applify on 11/14/2017.
 */

public class CheckAvailablityActivity extends BaseActivity implements OnDateSelectedListener, OnRangeSelectedListener, OnMonthChangedListener {

    private static final int PAUSED = 1;
    static String mCheckInTime = "";
    static String mCheckOutTime = "";
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

//    @BindView(R.id.ll_pickup)
//    LinearLayout llPickup;
//    @BindView(R.id.img_pickup)
//    ImageView imgPickup;

    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.ll_type)
    LinearLayout llType;

//    @BindView(R.id.txt_address_pickup)
//    TextView txtAddressPickup;
//    @BindView(R.id.ll_deleiver)
//    LinearLayout llDeleiver;
//    @BindView(R.id.ll_deliver_address)
//    LinearLayout llDeliverAddress;
//    @BindView(R.id.img_deleiver)
//    ImageView imgDeleiver;
//    @BindView(R.id.txt_deliver)
//    TextView txtDeliver;
//    @BindView(R.id.txt_address_deleivery)
//    TextView txtAddressDeleivery;

    @BindView(R.id.txt_select_type)
    TextView txtSelectType;
    @BindView(R.id.txt_pickup)
    TextView txtPickup;
    @BindView(R.id.ll_no_tuls)
    LinearLayout llNoTuls;
    @BindView(R.id.txt_no_hrs)
    TextView txtNoHours;
    @BindView(R.id.txt_max_limit)
    TextView txtMaxLimit;
    @BindView(R.id.txt_number)
    TextView txtBookinTimeLable;
    @BindView(R.id.ll_cost)
    LinearLayout llCost;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_book)
    TextView tvBook;
    @BindView(R.id.rv_time_slots)
    RecyclerView rvTimeSlots;
    @BindView(R.id.img_plus)
    ImageView imgPlus;
    @BindView(R.id.img_minus)
    ImageView imgMinus;
    @BindView(R.id.txt_available_time_slot_lable)
    TextView txtAvailableTimeSlotLable;
    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    @BindView(R.id.viewDisount)
    View viewDiscount;
    @BindView(R.id.view_hours)
    View viewHours;


    boolean isRangeEnabled, isDateSelected;
    ViewTulModel.ResponseBean.PreferencesBean prefrencesModel;
    ViewTulModel.ResponseBean.AdditionalPriceBean mViewTulAdditional;
    ViewTulModel.ResponseBean mViewTulModel;
    int mDayCount = 1, mCount = 1, mDeliveryMode, mHours = 1;
    double mTotalPrice = 1.00, mPrice;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    TimeSlotAdapter mAdapter;
    ArrayList<String> mSlotsArray = new ArrayList<>();
    HashMap<String, Integer> map = new HashMap<>();
    String mPlaceStartTime;
    String last = "", next = "";
    int bufferTime = 3;
    private ArrayList<String> mDatesArray = new ArrayList<>();
    private ArrayList<BookedSlotsModel.ResponseBean> mHoursArray = new ArrayList<>();
    private ArrayList<String> mDaysArray = new ArrayList<>();
    private ArrayList<String> mSelectedDays = new ArrayList<>();

    public static void getBookingTime(String checktime) {
        String[] time = checktime.split("-");
        mCheckInTime = time[0].trim();
        mCheckOutTime = time[1].trim();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_check_availbilty;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.select_date);

        txtTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
        txtTime.setPadding(0, mWidth / 52, 0, 0);

        llType.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 32);

        txtSelectType.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
//      txtSelectType.setPadding(0, 0, 0, mHeight / 64);

        txtPickup.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        txtMaxLimit.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        txtBookinTimeLable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtBookinTimeLable.setPadding(0, 0, 0, mHeight / 64);
//        txtPickup.setPadding(mWidth / 32, 0, 0, 0);

//        txtAddressPickup.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
//        txtAddressPickup.setPadding(mWidth / 32, 0, 0, mHeight / 64);
//
//        txtDeliver.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
//        txtDeliver.setPadding(mWidth / 32, 0, 0, 0);
//
//        txtAddressDeleivery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
//        txtAddressDeleivery.setPadding(mWidth / 32, 0, 0, 0);

        ///calendar views...
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnRangeSelectedListener(this);

        calendarView.setOnMonthChangedListener(this);
        calendarView.setTileSize(mWidth / 7);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendarView.state().edit().setMinimumDate(calendar.getTime()).commit();
        ////----- End

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvTotal.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvBook.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        llNoTuls.setPadding(mWidth / 32, mHeight / 42, mWidth / 32, mHeight / 42);

        txtNoHours.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtNoHours.setPadding(mWidth / 52, mHeight / 64, mWidth / 52, mHeight / 64);
        txtNoHours.setTypeface(typefaceRegular);

        imgPlus.setPadding(mWidth / 32, 0, mWidth / 32, 0);
        imgMinus.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 64);
        llCost.setLayoutParams(layoutParams);

        llCost.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);
        txtNoHours.setText(String.valueOf(mHours));

        txtAvailableTimeSlotLable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtAvailableTimeSlotLable.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);
        txtAvailableTimeSlotLable.setTypeface(typeface);

        mAdapter = new TimeSlotAdapter(this, mSlotsArray);
        rvTimeSlots.setLayoutManager(new LinearLayoutManager(this));
        rvTimeSlots.setAdapter(mAdapter);
    }

    @Override
    protected void onCreateStuff() {

        mViewTulModel = getIntent().getParcelableExtra("tul_data");
        prefrencesModel = mViewTulModel.getPreferences();
        mViewTulAdditional = mViewTulModel.getAdditional_price();
        mViewTulAdditional.setCurrency(mViewTulModel.getCurrency());
        mDaysArray.clear();

        String time[] = mViewTulModel.getPreferences().getStart_time().split(":");
        mPlaceStartTime = time[0].trim();

        if (prefrencesModel.getAvailable().equals("Only Weekends")) {
            mDaysArray.add("Saturday");
            mDaysArray.add("Sunday");
        } else if (prefrencesModel.getAvailable().equals("Weekdays")) {
            mDaysArray.add("Monday");
            mDaysArray.add("Tuesday");
            mDaysArray.add("Wednesday");
            mDaysArray.add("Thursday");
            mDaysArray.add("Friday");
        }


        if (mViewTulModel.getProduct_type() == 1) {
            calendarView.addDecorator(new PrimeDayDisableDecorator());
        }

        mPrice = Double.parseDouble(mViewTulModel.getPrice());
        setData();

        txtNoHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    String ss = s.toString();
                    mCount = Integer.parseInt(ss);
                    mTotalPrice = mCount * mPrice * mDayCount;
                    tvPrice.setText(mViewTulAdditional.getCurrency() + String.format("%.2f", mTotalPrice));
                } else {
                    tvPrice.setText(mViewTulAdditional.getCurrency() + "0.0");
                    mTotalPrice = 1;
                    mCount = 0;
                }
                tvTotal.setText(" Total");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (connectedToInternet()) {
            if (mViewTulModel.getProduct_type() == 0) {
                hitAPI();
            } else {
                hitTransactionAPI();
            }
        } else
            showInternetAlert(llCost);
    }

    void makeSlotsBooked(String arrBookedSlot[], int k, int startTime) {

        if (mSlotsArray.size() > 0 || mSlotsArray != null) {
            mSlotsArray.clear();
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ArrayList<String> mSlots = new ArrayList<>();

        ArrayList<String> mSlotsREMOVE = new ArrayList<>();
        ArrayList<String> finalarr = new ArrayList<>();
        Date initial = new Date(2018, 3, 1, startTime, 0);
        Date temo = new Date();

        ArrayList<String> slotsArrayList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initial);
        slotsArrayList.add(sdf.format(initial));

        for (int i = 0; i < 25; i++) {
            calendar.add(Calendar.HOUR, 1);
            temo = calendar.getTime();
            slotsArrayList.add(sdf.format(temo));
            if (sdf.format(temo).equals("00:00")) {
                break;
            }
        }
        for (int i = 0; i < slotsArrayList.size(); i++) {
            map.put(slotsArrayList.get(i), i);
            mSlots.add(slotsArrayList.get(i));
        }
//        for (int j = 0; j < arrBookedSlot.length; j++) {
//            String time[] = arrBookedSlot[j].split("-");
//            if (map.get(time[0]) == null) {
//                for (int i = 0; i < map.get(time[1].trim()); i++) {
//                    if (!mSlotsREMOVE.contains(mSlots.get(i))) {
//                        mSlotsREMOVE.add(mSlots.get(i));
//                    }
//                }
//            } else if (map.get(time[0]) >= 3) {
//                for (int i = map.get(time[0].trim()); i < map.get(time[1].trim()); i++) {
//                    if (!mSlotsREMOVE.contains(mSlots.get(i))) {
//                        mSlotsREMOVE.add(mSlots.get(i));
//                    }
//                }
//            }
//        }

        for (int j = 0; j < arrBookedSlot.length; j++) {
            String time[] = arrBookedSlot[j].split("-");
            for (int i = map.get(time[0].trim()); i < map.get(time[1].trim()); i++) {
                if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                    mSlotsREMOVE.add(mSlots.get(i));
                }
            }
        }
        for (int i = 0; i < mSlotsREMOVE.size(); i++) {
            System.out.println("REMOVE:  " + mSlotsREMOVE.get(i));
        }

        for (int i = 0; i < mSlots.size(); i++) {
//            if(i+k<=mSlots.size()-1) {
//                if(mSlotsREMOVE.contains(mSlots.get(i)) || mSlotsREMOVE.contains(mSlots.get(i+k))){
//                    finalarr.add(mSlots.get(i)+" - "+mSlots.get(i+k)+" (BOOKED)");
//                }else{
//                    finalarr.add(mSlots.get(i)+" - "+mSlots.get(i+k));
//                }
//            }
            if ((i + k) < mSlots.size()) {
                if (!mSlotsREMOVE.contains(mSlots.get(i)) && !mSlotsREMOVE.contains(mSlots.get(i + k))) {
                    int state = 0;
                    for (int qq = 0; qq < arrBookedSlot.length; qq++) {
                        if (arrBookedSlot[qq].endsWith(mSlots.get(i + k))) {
                            state = 1;
                            break;
                        }
                    }
                    if (state == 0)
                        finalarr.add(mSlots.get(i) + " - " + mSlots.get(i + k));
                } else {
                    int status = 0;
                    for (int g = 0; g < arrBookedSlot.length; g++) {
                        if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                            if (mSlotsREMOVE.contains(mSlots.get(i + k)) && !arrBookedSlot[g].startsWith(mSlots.get(i + k))) {
                                String time[] = arrBookedSlot[g].split("-");
                                ArrayList<String> mREMOVE = new ArrayList<>();
                                for (int s = map.get(time[0].trim()); s < map.get(time[1].trim()); s++) {
                                    mREMOVE.add(mSlots.get(s));
                                }
                                if (mREMOVE.contains(mSlots.get(i + k))) {
                                    status = 0;
                                    break;
                                } else {
                                    status = 1;
                                }
                            } else {
                                status = 1;
                                break;
                            }
                        } else {
                            status = 0;
                            break;
                        }
                    }
                    if (status == 1) {
                        finalarr.add(mSlots.get(i) + " - " + mSlots.get(i + k));
                    }
                }
            }
        }

        for (int i = 0; i < finalarr.size(); i++) {
            Log.e("slot", "" + finalarr.get(i));
            mSlotsArray.add(finalarr.get(i));
        }
    }

    void makeSlots(int slot, int start_time) {
        if (mSlotsArray.size() > 0 || mSlotsArray != null) {
            mSlotsArray.clear();
        }
        Date initial = new Date(2018, 3, 1, start_time, 0);
        Date temo = new Date();
        ArrayList<String> slotsArrayList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initial);
        slotsArrayList.add(sdf.format(initial));


        for (int i = 0; i < 25; i++) {
            calendar.add(Calendar.HOUR, 1);
            temo = calendar.getTime();
            slotsArrayList.add(sdf.format(temo));
            if (sdf.format(temo).equals("00:00")) {
                break;
            }
        }
        for (int i = 0; i < slotsArrayList.size(); i++) {
            if (i < (slotsArrayList.size() - slot)) {
                mSlotsArray.add(slotsArrayList.get(i) + " - " + slotsArrayList.get(i + slot));
                System.out.println(slotsArrayList.get(i) + " - " + slotsArrayList.get(i + slot));
            }
        }
    }

    private void hitAPI() {
        showProgress();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        System.out.println("CHECK DATE " + dateFormat.format(date));
        String selectedDate = dateFormat.format(date);

        Call<AvailbiltyModel> call = RetrofitClient.getInstance().checkAvailbilty(utils.getString("access_token", ""),
                mViewTulModel.getId(), selectedDate);
        call.enqueue(new Callback<AvailbiltyModel>() {
            @Override
            public void onResponse(Call<AvailbiltyModel> call, Response<AvailbiltyModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    mDatesArray.clear();
                    mDatesArray.addAll(response.body().getResponse());
                    calendarView.addDecorator(new PrimeDayDisableDecorator());

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(getString(R.string.pause_error))) {
                        alertPauseDialog(response.body().error.getMessage());
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else {
                        showAlert(llCost, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<AvailbiltyModel> call, Throwable t) {
                showAlert(llCost, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void hitHoursAPI() {
        showProgress();
        temPosition = -1;
        mCheckInTime = null;
        mHours = 1;
        txtNoHours.setText(String.valueOf(mHours));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date date = new Date();

        String selectedDate = dateFormat.format(date);

        if (mViewTulModel.getProduct_type() == 1) {
            selectedDate = String.valueOf(dateFormat.format(calendarView.getSelectedDate().getDate()));
        }


        Call<BookedSlotsModel> call = RetrofitClient.getInstance().checkTimeAvailbilty(utils.getString("access_token", ""),
                mViewTulModel.getId(), selectedDate);
        call.enqueue(new Callback<BookedSlotsModel>() {
            @Override
            public void onResponse(Call<BookedSlotsModel> call, Response<BookedSlotsModel> response) {
                hideProgress();
                Log.d("RESSSPONNNNN", String.valueOf(response.body().getResponse()));
                if (response.body().getResponse() != null) {
                    mHoursArray.clear();
                    mHoursArray.addAll(response.body().getResponse());
//                        if (selected.equals(todayDate) && Integer.parseInt(mPlaceStartTime) < t) {
//                            mHoursArray.add(bean);
//                        }
//                    String[] Arr = new String[mHoursArray.size()];
//                    for (int i = 0; i < mHoursArray.size(); i++) {
//                        Arr[i] = mHoursArray.get(i).getCheck_in() + "-" + mHoursArray.get(i).getCheck_out();
//                    }
//                        makeSlotsBooked(Arr, mHours, Integer.parseInt(mPlaceStartTime));

                    bufferTime = response.body().getBuffer_time();
                    last = response.body().getPrevious();
                    next = response.body().getNext();

                    arr(mHoursArray, bufferTime, mHours, last, next);

                    mAdapter.notifyDataSetChanged();

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(getString(R.string.pause_error))) {
                        alertPauseDialog(response.body().error.getMessage());
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else {
                        showAlert(llCost, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BookedSlotsModel> call, Throwable t) {
                showAlert(llCost, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void setData() {

        txtAvailableTimeSlotLable.setVisibility(View.GONE);
        rvTimeSlots.setVisibility(View.GONE);
        imgPlus.setEnabled(false);
        imgMinus.setEnabled(false);

        txtTime.setText(getString(R.string.Check_In)+" "+ prefrencesModel.getStart_time() + " | "+getString(R.string.Check_Out)
                +" "+ prefrencesModel.getEnd_time());
        tvPrice.setText(mViewTulModel.getCurrency() + String.format("%.2f", Double.parseDouble(mViewTulModel.getPrice())));

        txtSelectType.setText("GET " + mViewTulModel.getDiscount_percentage() + " % OFF");

        if (mViewTulModel.getDiscount_days() == 0) {
            llType.setVisibility(View.GONE);
            viewDiscount.setVisibility(View.GONE);
        }

        if (mViewTulModel.getProduct_type() == 0) {

            calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
            llNoTuls.setVisibility(View.GONE);
            viewHours.setVisibility(View.GONE);
            txtPickup.setText(getString(R.string.When_You_Book_for_at_least)+" "+ mViewTulModel.getDiscount_days()+" "+getString(R.string.days));
            tvTotal.setText(" "+getString(R.string.per_day));
            txtTime.setVisibility(View.VISIBLE);

        } else if (mViewTulModel.getProduct_type() == 1) {
            calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
            llNoTuls.setVisibility(View.VISIBLE);
            viewHours.setVisibility(View.VISIBLE);
            txtPickup.setText(getString(R.string.When_You_Book_for_at_least)+" "+ mViewTulModel.getDiscount_days()+" "+" Hours");
            tvTotal.setText(" "+getString(R.string.per_hour));
            txtTime.setVisibility(View.GONE);
        }

        int maxSlots = 24 - Integer.parseInt(mPlaceStartTime);
        txtMaxLimit.setText(getString(R.string.You_can_book_maximum_of)+" "+maxSlots+" "+getString(R.string.hours));

    }

    int getmaxHours() {
        int maxSlots = 1;
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
        Date date = new Date();
        String time1 = String.valueOf(sdf1.format(date.getTime()));
        final int t = Integer.parseInt(time1) + 1;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String todayDate = dateFormat.format(date);
        String selected = String.valueOf(dateFormat.format(calendarView.getSelectedDate().getDate()));

        if (selected.equals(todayDate)) {
            maxSlots = 24 - t;
        } else {
            maxSlots = 24 - Integer.parseInt(mPlaceStartTime);
        }
        return maxSlots;
    }

    @Override
    protected void initListener() {
        tvBook.setOnClickListener(this);
        imgMinus.setOnClickListener(this);
        imgPlus.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return CheckAvailablityActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_book:
                if (utils.getInt(Constants.BLOCKSTATUS, 0) == 0) {
                    if (mSelectedDays.size() == 0)
                        showAlert(llCost, getString(R.string.Please_select_at_least_one_date));
                    else if (mSlotsArray.size() == 0 && mViewTulModel.getProduct_type() == 1) {
                        showAlert(llCost, getString(R.string.Their_are_no_available_time_slots_of) + mHours + getString(R.string.hours_for_this_Date));
                    } else if (mCheckInTime == null && mViewTulModel.getProduct_type() == 1) {
                        showAlert(llCost, getString(R.string.Please_select_Booking_Time));
                    } else {

                        Intent intent = new Intent(mContext, TulCheckOutDetailsActivity.class);
                        intent.putExtra("selected_dates", mSelectedDays);
                        if (mViewTulModel.getProduct_type() == 1) {
                            intent.putExtra("quantity", txtNoHours.getText().toString());
                        } else if (mViewTulModel.getProduct_type() == 0) {
                            intent.putExtra("quantity", 1);
                        }
                        intent.putExtra("start_time", prefrencesModel.getStart_time());
                        intent.putExtra("end_time", prefrencesModel.getEnd_time());
                        intent.putExtra("check_in_time", mCheckInTime);
                        intent.putExtra("check_out_time", mCheckOutTime);
                        intent.putExtra("tul_price", mTotalPrice);
                        intent.putExtra("tool_title", mViewTulModel.getTitle());
                        intent.putExtra("tul_data", mViewTulModel);
                        intent.putExtra("data_price", mViewTulModel.getAdditional_price());

                        if (mViewTulModel.getProduct_type() == 0)///days
                        {
                            intent.putExtra("buffer_available", "");

                            if (mDayCount >= mViewTulModel.getDiscount_days())
                                intent.putExtra("show_discount", 1);
                            else
                                intent.putExtra("show_discount", 0);
                        } else {
                            intent.putExtra("buffer_available", String.valueOf(getBufferAvailability(mHoursArray, mCheckInTime, mCheckOutTime)));

                            if (mHours >= mViewTulModel.getDiscount_days())
                                intent.putExtra("show_discount", 1);
                            else
                                intent.putExtra("show_discount", 0);
                        }

                        startActivityForResult(intent, PAUSED);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }
                } else {
                    userBlockDialog(mContext);
                }
                break;
            case R.id.img_minus:
                if (mHours > 1) {
                    mHours = mHours - 1;
                    txtNoHours.setText(String.valueOf(mHours));
                    arr(mHoursArray, bufferTime, mHours, last, next);
                    temPosition = -1;
                    mCheckInTime = null;
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.img_plus:
//
//                int maxSlots = 1;
//                SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
//                Date date = new Date();
//                String time1 = String.valueOf(sdf1.format(date.getTime()));
//                final int t = Integer.parseInt(time1) + 1;
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//                String todayDate = dateFormat.format(date);
//                String selected = String.valueOf(dateFormat.format(calendarView.getSelectedDate().getDate()));
//
//                if (selected.equals(todayDate)) {
//                    maxSlots = 24 - t;
//                    if (maxSlots > 12) {
//                        maxSlots = 12;
//                    }
//                } else {
//                    maxSlots = 24 - Integer.parseInt(mPlaceStartTime);
//                    if (maxSlots > 12) {
//                        maxSlots = 12;
//                    }
//                }
                if (mHours < getmaxHours()) {
                    mHours = mHours + 1;
                    txtNoHours.setText(String.valueOf(mHours));
                    arr(mHoursArray, bufferTime, mHours, last, next);
                    temPosition = -1;
                    mCheckInTime = null;
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.getDate());

        if (!isDateSelected) {
            mSelectedDays.clear();
            mSelectedDays.add(dateFormat.format(calendar.getTime()));
            isDateSelected = true;
        } else {
            isDateSelected = false;
            mSelectedDays.clear();
        }
        if (isRangeEnabled) {
            isRangeEnabled = false;
            widget.clearSelection();
            widget.setSelectedDate(date);
        }

        mTotalPrice = mPrice;
        mDayCount = 1;
        mTotalPrice = mDayCount * mPrice * mCount;
        tvPrice.setText(mViewTulModel.getCurrency() + String.format("%.2f", mTotalPrice));
        tvTotal.setText(" "+getString(R.string.total));

        if (mViewTulModel.getProduct_type() == 1) {
            isDateSelected = false;
            if (connectedToInternet()) {
                hitHoursAPI();
                imgMinus.setEnabled(true);
                imgPlus.setEnabled(true);
                txtMaxLimit.setText(getString(R.string.You_can_book_maximum_of)+" "+getmaxHours()+ " hours");
                txtAvailableTimeSlotLable.setVisibility(View.VISIBLE);
                rvTimeSlots.setVisibility(View.VISIBLE);
            } else
                showInternetAlert(llCost);
        }
    }

    @Override
    public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
        if (!isRangeEnabled) {
            isRangeEnabled = true;
            mDayCount = dates.size();
            mTotalPrice = mDayCount * mPrice * mCount;
            tvPrice.setText(mViewTulModel.getCurrency() + String.format("%.2f", mTotalPrice));
            tvTotal.setText(" "+getString(R.string.total));
        }
        isDateSelected = false;
        mSelectedDays.clear();
        for (CalendarDay dayItem : dates) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dayItem.getDate());
            mSelectedDays.add(dateFormat.format(calendar.getTime()));
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        widget.getCalendarContentDescription();
    }

    @Override
    public void onBackPressed() {
        moveBack();
        super.onBackPressed();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PAUSED) {
                /// booking paused
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alertPauseDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.booking_paused).setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    void arr(ArrayList<BookedSlotsModel.ResponseBean> arrListBookedSlot, int bufferTime, int k, String last, String next) {


        if (mViewTulModel.getBuffer_status() == 1) {
            last = "";
        }

        if (arrListBookedSlot.size() > 0) {
            txtMaxLimit.setVisibility(View.GONE);
        } else {
            txtMaxLimit.setVisibility(View.VISIBLE);
        }

        if (last.equals("23:59")) {
            last = "24:00";
        }
        if (next.equals("23:59")) {
            next = "24:00";
        }

        for (int i = 0; i < arrListBookedSlot.size(); i++) {
            if (arrListBookedSlot.get(i).getCheck_out().equals("23:59")) {
                arrListBookedSlot.get(i).setCheck_out("24:00");
            }
        }

        if (mSlotsArray.size() > 0 || mSlotsArray != null) {
            mSlotsArray.clear();
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ArrayList<String> mSlots = new ArrayList<>();
        final String selected = String.valueOf(dateFormat.format(calendarView.getSelectedDate().getDate()));
        ArrayList<String> mSlotsREMOVE = new ArrayList<>();
        ArrayList<String> finalarr = new ArrayList<>();
        Date initial = new Date(2018, 3, 1, 0, 0);
        Date temo = new Date();

        ArrayList<String> slotsArrayList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initial);
        slotsArrayList.add(sdf.format(initial));

        for (int i = 0; i < 25; i++) {
            calendar.add(Calendar.HOUR, 1);
            temo = calendar.getTime();
            if (sdf.format(temo).equals("00:00")) {
                slotsArrayList.add("24:00");
                break;
            } else {
                slotsArrayList.add(sdf.format(temo));
            }
        }
        for (int i = 0; i < slotsArrayList.size(); i++) {
            map.put(slotsArrayList.get(i), i);
            mSlots.add(slotsArrayList.get(i));
        }

        for (int i = 0; i < map.get(mPlaceStartTime + ":00"); i++) {
            mSlotsREMOVE.add(mSlots.get(i));
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String[] time1 = String.valueOf(sdf1.format(date.getTime())).split(":");

        int t = 0;
        if (Integer.parseInt(time1[1]) > 30) {
            t = Integer.parseInt(time1[0]) + 1;
        } else if (Integer.parseInt(time1[1]) < 30) {
            t = Integer.parseInt(time1[0]);
        }

        if (t < 10) {
            time1[0] = String.valueOf("0" + t);
        } else {
            time1[0] = String.valueOf(t);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        final String todayDate = dateFormat.format(date);

        if (selected.equals(todayDate)) {
            for (int i = 0; i < map.get(time1[0] + ":00"); i++) {
                if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                    mSlotsREMOVE.add(mSlots.get(i));
                }
            }
        }


        if (!TextUtils.isEmpty(last)) {
            if (last.equals("00:00")) {
                for (int i = 0; i < 3; i++) {
                    if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                        mSlotsREMOVE.add(mSlots.get(i));
                    }
                }
            } else {
                if (map.get(last) + bufferTime > 24) {
                    for (int i = 0; i < (map.get(last) + bufferTime) - 24; i++) {
                        if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                            mSlotsREMOVE.add(mSlots.get(i));
                        }
                    }
                }
            }
        }

        for (int j = 0; j < arrListBookedSlot.size(); j++) {
            for (int i = map.get(arrListBookedSlot.get(j).getCheck_in().trim()); i < map.get(arrListBookedSlot.get(j).getCheck_out().trim()); i++) {
                for (int p = i - bufferTime; p < i; p++) {
                    if (p >= 0) {
                        if (!mSlotsREMOVE.contains(mSlots.get(p))) {
                            mSlotsREMOVE.add(mSlots.get(p));
                        }
                    }
                }
                mSlotsREMOVE.add(mSlots.get(i));
            }
            if (mViewTulModel.getBuffer_status() == 1) {
                if (j != arrListBookedSlot.size() - 1) {
                    for (int i = map.get(arrListBookedSlot.get(j).getCheck_out().trim()); i < map.get(arrListBookedSlot.get(j).getCheck_out().trim()) + bufferTime; i++) {
                        if (i < map.size())
                            if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                                mSlotsREMOVE.add(mSlots.get(i));
                            }
                    }
                } else {
                    String s = arrListBookedSlot.get(arrListBookedSlot.size() - 1).getCheck_out();
                    String[] strings = arrListBookedSlot.get(arrListBookedSlot.size() - 1).getCheck_out().split(":");
                    if ((Integer.parseInt(strings[0].trim()) < 21)) {
                        for (int i = map.get(arrListBookedSlot.get(j).getCheck_out().trim()); i < map.get(arrListBookedSlot.get(j).getCheck_out().trim()) + bufferTime; i++) {
                            if (i < map.size())
                                if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                                    mSlotsREMOVE.add(mSlots.get(i));
                                }
                        }
                    }
                }
            } else {
                for (int i = map.get(arrListBookedSlot.get(j).getCheck_out().trim()); i < map.get(arrListBookedSlot.get(j).getCheck_out().trim()) + bufferTime; i++) {
                    if (i < map.size())
                        if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                            mSlotsREMOVE.add(mSlots.get(i));
                        }
                }
            }
        }

        if (!TextUtils.isEmpty(next)) {
            if (next.equals("00:00")) {
                for (int i = 24; i >= 24 - bufferTime; i--) {
                    if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                        mSlotsREMOVE.add(mSlots.get(i));
                    }
                }
            } else {
                if (map.get(next) - bufferTime < 0) {
                    for (int i = 24; i >= 24 - (bufferTime - map.get(next)); i--) {
                        if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                            mSlotsREMOVE.add(mSlots.get(i));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < mSlotsREMOVE.size(); i++) {
            System.out.println("REMOVE:  " + mSlotsREMOVE.get(i));
        }

        for (int i = 0; i < mSlots.size(); i++) {
            if ((i + k) < mSlots.size()) {
                if (!mSlotsREMOVE.contains(mSlots.get(i)) || !mSlotsREMOVE.contains(mSlots.get(i + k))) {
                    int state = 0;
                    for (int qq = 0; qq < arrListBookedSlot.size(); qq++) {
                        if (arrListBookedSlot.get(qq).getCheck_out().trim().endsWith(mSlots.get(i + k))) {
                            state = 1;
                            break;
                        }
                    }
                    if (state == 0) {
                        int sta = 0;
                        for (int m = map.get(mSlots.get(i)); m < map.get(mSlots.get(i + k)); m++) {
                            if (mSlotsREMOVE.contains(mSlots.get(m))) {
                                sta = 1;
                                break;
                            }
                        }
                        if (sta == 0) {
                            finalarr.add(mSlots.get(i) + " - " + mSlots.get(i + k));
                        }
                    }

                } else {
                    int status = 0;
                    ArrayList<String> mREMOVE = new ArrayList<>();
                    for (int g = 0; g < arrListBookedSlot.size(); g++) {
                        if (!mSlotsREMOVE.contains(mSlots.get(i))) {
                            if (mSlotsREMOVE.contains(mSlots.get(i + k)) && !arrListBookedSlot.get(g).getCheck_in().trim().startsWith(mSlots.get(i + k))) {
                                for (int s = map.get(arrListBookedSlot.get(g).getCheck_in().trim()) - bufferTime; s < map.get(arrListBookedSlot.get(g).getCheck_out().trim()) + bufferTime; s++) {
                                    if (s >= 0 && s <= 24)
                                        mREMOVE.add(mSlots.get(s));
                                }
                                if (mREMOVE.contains(mSlots.get(i + k))) {
                                    status = 0;
                                    break;
                                } else {
                                    status = 1;
                                }
                            } else {
                                if (arrListBookedSlot.get(g).getCheck_in().trim().startsWith(mSlots.get(i + k))) {
                                    for (int s = map.get(arrListBookedSlot.get(g).getCheck_in().trim()) - bufferTime; s <= map.get(arrListBookedSlot.get(g).getCheck_out().trim()) + bufferTime; s++) {
                                        if (s >= 0 && s <= 24)
                                            mREMOVE.add(mSlots.get(s));
                                    }
                                    if (mREMOVE.contains(mSlots.get(i + k))) {
                                        status = 0;
                                        break;
                                    } else {
                                        status = 1;
                                    }
                                }
                            }
                        } else {
                            status = 0;
                            break;
                        }
                    }

                    if (status == 1) {
                        finalarr.add(mSlots.get(i) + " - " + mSlots.get(i + k));
                    }
                }
            }
        }
        if (finalarr.size() > 0) {
            if (finalarr.get(finalarr.size() - 1).endsWith("24:00")) {
                String j[] = finalarr.get(finalarr.size() - 1).split(" - ");
                finalarr.remove(finalarr.size() - 1);
                finalarr.add(j[0] + " - " + "23:59");
            }
        }
        if (finalarr.size() == 0) {
            txtAvailableTimeSlotLable.setText(R.string.NO_TIME_SLOTS_AVAILABLE);
        } else {
            txtAvailableTimeSlotLable.setText(R.string.AVAILABLE_TIME_SLOTS);
        }
        for (int i = 0; i < finalarr.size(); i++) {
            mSlotsArray.add(finalarr.get(i));
        }

    }

    int getBufferAvailability(ArrayList<BookedSlotsModel.ResponseBean> arrListBookedSlot, String mCheckInTime, String mCheckOutTime) {
        // 0 if buffer should be added
        // 1 if buffer should not be added
        if (mViewTulModel.getBuffer_status() == 1) {
            if (arrListBookedSlot.size() > 0) {
                String[] buffStart = arrListBookedSlot.get(0).getCheck_in().split(":");
                String[] buffEnd = arrListBookedSlot.get(arrListBookedSlot.size() - 1).getCheck_out().split(":");
                ArrayList<Integer> integers = new ArrayList<>();
                for (int i = Integer.parseInt(buffStart[0].trim()); i < Integer.parseInt(buffEnd[0].trim()); i++) {
                    integers.add(i);
                }
                String[] checkout = mCheckOutTime.split(":");
                String[] checkin = mCheckInTime.split(":");
                if (integers.contains(Integer.parseInt(checkout[0].trim()))) {
                    if (Integer.parseInt(checkout[0].trim()) == (integers.get(integers.size() - 1))) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (Integer.parseInt(checkout[0].trim()) > integers.get(integers.size() - 1)) {
                        return 1;
                    } else {
                        if (Integer.parseInt(checkin[0].trim()) - bufferTime < 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    private void hitTransactionAPI() {
        Call<TransacrionPercentageModel> call = RetrofitClient.getInstance().transactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransacrionPercentageModel>() {
            @Override
            public void onResponse(Call<TransacrionPercentageModel> call, Response<TransacrionPercentageModel> response) {
                if (response.body().getResponse() != null) {

                    utils.setString("transaction_percentage", response.body().getResponse());

                    utils.setInt(Constants.ISEMAILSKIP, response.body().getIs_email_skip());
                    utils.setInt(Constants.EMAIL_VERIFY, response.body().getEmail_verify());
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getBlock_status());

                    if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                        userBlockDialogUser(mContext);
                    }

                } else {
                    Toast.makeText(mContext, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransacrionPercentageModel> call, Throwable t) {

            }
        });
    }

    private class PrimeDayDisableDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(day.getDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            if (mDatesArray.contains(dateFormat.format(calendar.getTime()))) {
                return true;
            } else {
                if (mDaysArray.size() > 0) {
                    if (mDaysArray.contains(dayFormat.format(calendar.getTime()))) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }

    }


}
