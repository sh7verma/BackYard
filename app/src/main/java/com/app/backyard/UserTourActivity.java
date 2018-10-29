package com.app.backyard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.UserTourAdapter;
import butterknife.BindView;
import model.ContentModel;

public class UserTourActivity extends BaseActivity {

    @BindView(R.id.txt_how)
    TextView txtHow;
    @BindView(R.id.txt_side_status)
    TextView txtSideStatus;
    @BindView(R.id.txt_next)
    TextView txtNext;
    @BindView(R.id.ll_lender)
    LinearLayout llLender;
    @BindView(R.id.rv_demo)
    RecyclerView rvDemo;

    private RecyclerView.LayoutManager mLayoutManager;
    private UserTourAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_tour;
    }

    @Override
    protected void initUI() {

        txtHow.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtSideStatus.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtNext.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        mLayoutManager = new LinearLayoutManager(this);
        rvDemo.setLayoutManager(mLayoutManager);
        mAdapter = new UserTourAdapter(mContext, loadUserData());
        rvDemo.setAdapter(mAdapter);
    }

    @Override
    protected void onCreateStuff() {
        rvDemo.stopScroll();
    }

    @Override
    protected void initListener() {
        txtNext.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return UserTourActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_next:
                if (txtNext.getText().toString().equalsIgnoreCase(("NEXT"))) {
                    txtNext.setText("GOT IT");
                    txtSideStatus.setText(getResources().getString(R.string.lender_side));

                    mAdapter = new UserTourAdapter(mContext, loadHostData());
                    rvDemo.setAdapter(mAdapter);
                } else {
                    Intent inStarted = new Intent(mContext, LandingActivity.class);
                    startActivity(inStarted);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                break;
        }
    }

    private ArrayList<ContentModel> loadUserData() {

        ArrayList<ContentModel> loadUserData = new ArrayList<>();

        ContentModel itemContent = new ContentModel();
        itemContent.setTittle(getString(R.string.Search_for_a_Space));
        itemContent.setDescrption(getString(R.string.Search_venues_from_the_given_categories_and_apply_any_desired_filters));

        ContentModel itemContent1 = new ContentModel();
        itemContent1.setTittle(getString(R.string.Book_a_Space));
        itemContent1.setDescrption(getString(R.string.Available_venues_will_be_displayed__Select_the_one_you_find_suitable_and_proceed_to_book));

        ContentModel itemContent2 = new ContentModel();
        itemContent2.setTittle(getString(R.string.Check_Status));
        itemContent2.setDescrption(getString(R.string.Make_online_payment_and_check_the_status_of_the_rented_venues_with_ease));

        loadUserData.add(itemContent);
        loadUserData.add(itemContent1);
        loadUserData.add(itemContent2);
        return loadUserData;
    }

    private ArrayList<ContentModel> loadHostData() {

        ArrayList<ContentModel> loadUserData = new ArrayList<>();

        ContentModel itemContent = new ContentModel();
        itemContent.setTittle(getString(R.string.Become_a_Host));
        itemContent.setDescrption(getString(R.string.Add_your_own_spaces_with_all_the_required_information));

        ContentModel itemContent1 = new ContentModel();
        itemContent1.setTittle(getString(R.string.Check_Status));
        itemContent1.setDescrption(getString(R.string.Track_the_status_of_all_the_rented_venues_with_ease));

        ContentModel itemContent2 = new ContentModel();
        itemContent2.setTittle(getString(R.string.Dashboard));
        itemContent2.setDescrption(getString(R.string.Check_all_your_statistics_and_earnings_effortlessly));

        loadUserData.add(itemContent);
        loadUserData.add(itemContent1);
        loadUserData.add(itemContent2);
        return loadUserData;
    }

}
