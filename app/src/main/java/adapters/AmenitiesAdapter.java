package adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.backyard.R;

import java.util.ArrayList;

import model.AmenitiesModel;
import utils.Utils;

/**
 * Created by dev on 16/10/17.
 */

public class AmenitiesAdapter extends BaseAdapter {
    LayoutInflater mLayoutInflater;
    int mScreenWidth, mScreenheight;
    Context mContext;
    Utils utils;
    ArrayList<AmenitiesModel.ResponseBean> mLanguageList;

    public AmenitiesAdapter(Context context, ArrayList<AmenitiesModel.ResponseBean> mLanguageList) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        utils = new Utils(mContext);
        mScreenWidth = utils.getInt("width", 0);
        mScreenheight = utils.getInt("height", 0);
        this.mLanguageList = mLanguageList;
    }

    @Override
    public int getCount() {
        return mLanguageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLanguageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertedView, final ViewGroup parent) {
        final MyHolder holder;
        if (convertedView == null) {

            holder = new MyHolder();
            convertedView = mLayoutInflater.inflate(R.layout.item_amenities, null);

            holder.textView = (TextView) convertedView.findViewById(R.id.tv_amenity_name);
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenWidth * 0.04));
            holder.textView.setPadding(0, mScreenheight / 25, 0, mScreenheight / 25);

            holder.view = convertedView.findViewById(R.id.view1);

            holder.llSelection = (LinearLayout) convertedView.findViewById(R.id.ll_selection);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (mScreenWidth * 0.06), (int) (mScreenWidth * 0.06));
            lp.setMargins(0, 0, mScreenWidth / 25, 0);
            holder.llSelection.setLayoutParams(lp);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            holder.view.setLayoutParams(layoutParams);

            convertedView.setTag(holder);

        } else
            holder = (MyHolder) convertedView.getTag();

        holder.textView.setText(mLanguageList.get(position).getAmenity_name());

        convertedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLanguageList.get(position).isChecked()) {
                    mLanguageList.get(position).setChecked(true);
                } else {
                    mLanguageList.get(position).setChecked(false);
                }
                notifyDataSetChanged();
            }
        });

        if (!mLanguageList.get(position).isChecked()) {
            holder.llSelection.setBackgroundResource(R.drawable.checkbox_unchecked);
        } else {
            holder.llSelection.setBackgroundResource(R.drawable.checkbox_checked);
        }

        return convertedView;
    }

    static class MyHolder {
        View view;
        TextView textView;
        LinearLayout llSelection;

    }

}

