package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.app.backyard.DashBoardActivity;
import com.app.backyard.R;

import java.util.ArrayList;

import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import calenderViews.CalendarCustomView;
import database.Db;
import model.DashboardDatesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.Utils;

/**
 * Created by dev on 27/11/17.
 */

public class CalendarFragment extends Fragment {

    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;
    Db db;
    ArrayList<DashboardDatesModel.ResponseBean> mDatesModels = new ArrayList<>();
    private DashBoardActivity mDashBoardActivity;

    @BindView(R.id.custom_calendar)
    CalendarCustomView customCalendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(CalendarFragment.this, view);
        mContext = getActivity();
        mDashBoardActivity = (DashBoardActivity) getActivity();
        util = new Utils(mContext);
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        db = new Db(mContext);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            hitAPI();
        } else {
            Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
        }

        super.onActivityCreated(savedInstanceState);
    }

    void hitAPI() {
        CustomLoadingDialog.getLoader().showLoader(mContext);
        Call<DashboardDatesModel> call = RetrofitClient.getInstance().getDashBoardDates(util.getString("access_token", ""));
        call.enqueue(new Callback<DashboardDatesModel>() {
            @Override
            public void onResponse(Call<DashboardDatesModel> call, Response<DashboardDatesModel> response) {
                CustomLoadingDialog.getLoader().dismissLoader();
                if (response.body().getResponse() != null) {
                    mDatesModels.clear();
                    mDatesModels.addAll(response.body().getResponse());
                    customCalendar.addEvents(mDatesModels);
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, util);
                    } else {
                        mDashBoardActivity.showAlert(customCalendar, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardDatesModel> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

}
