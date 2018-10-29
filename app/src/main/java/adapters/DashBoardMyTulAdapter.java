package adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.backyard.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.DashboardMyTulModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 5/12/17.
 */

public class DashBoardMyTulAdapter extends RecyclerView.Adapter<DashBoardMyTulAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    ArrayList<DashboardMyTulModel.ResponseBean> mData;
    LinearLayout.LayoutParams cvParms,lineParams;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight, mTulImageHeight, mTulImageWidth;

    public DashBoardMyTulAdapter(Context con, ArrayList<DashboardMyTulModel.ResponseBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);


        mTulImageHeight = mHeight / 3;
        mTulImageWidth = mWidth - (mWidth / 64);

        cvParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTulImageHeight);

        lineParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        lineParams.setMargins(0, 0, 0, mHeight / 32);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_tullist, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (mData.get(position).getAttachment().get(0).getType().equals(Constants.IMAGE_TEXT)) {
            holder.imgVideoPlay.setVisibility(View.GONE);
            Picasso.with(con).load(mData.get(position).getAttachment().get(0).getAttachment())
                    .resize(mTulImageWidth, mTulImageHeight).centerCrop().placeholder(R.drawable.primary_ripple)
                    .into(holder.imgTul, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("Success = ", "Yes");
                        }

                        @Override
                        public void onError() {
                            Log.e("Error = ", "Yes");
                        }
                    });
        } else if (mData.get(position).getAttachment().get(0).getType().equals(Constants.VIDEO_TEXT)) {
            holder.imgVideoPlay.setVisibility(View.VISIBLE);
            Picasso.with(con).load(mData.get(position).getAttachment().get(0).getThumbnail())
                    .resize(mTulImageWidth, mTulImageHeight)
                    .centerCrop().placeholder(R.drawable.primary_ripple)
                    .into(holder.imgTul, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("Success = ", "Yes");
                        }

                        @Override
                        public void onError() {
                            Log.e("Error = ", "Yes");
                        }
                    });
        }

        holder.tvTittle.setText(mData.get(position).getTitle());
        holder.tvPrice.setText(mData.get(position).getCurrency() + " " + mData.get(position).getPrice());

        holder.tvBookedTul.setText(String.valueOf(mData.get(position).getTotal_bookings()));
        holder.tvTotalEarnings.setText(mData.get(position).getCurrency() + " "+mData.get(position).getTotal_earning());
        holder.tvShortlisted.setText(" "+mData.get(position).getWishlist_count()+" Shortlisted");
        holder.tvViews.setText(" "+mData.get(position).getViews()+" Views");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTittle, tvPrice, tvBookedTitle, tvTotalEarningTitle,
                tvBookedTul, tvTotalEarnings,
                tvShortlisted, tvViews;
        CardView cvTul;
        ImageView imgTul, imgVideoPlay;
        View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);

            cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
            cvTul.setLayoutParams(cvParms);

            imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

            tvTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            tvTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
            tvTittle.setPadding(mWidth / 32, 0, mWidth / 6, mWidth / 28);

            tvPrice = (TextView) itemView.findViewById(R.id.txt_price);
            tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
            tvPrice.setPadding(0, 0, mWidth / 32, mWidth / 32);

            imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

            tvBookedTitle = (TextView) itemView.findViewById(R.id.tv_booked_till_title);
            tvBookedTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
            tvBookedTitle.setPadding(0, mWidth / 42, 0, mWidth / 52);

            tvTotalEarningTitle = (TextView) itemView.findViewById(R.id.tv_total_earnings_title);
            tvTotalEarningTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
            tvTotalEarningTitle.setPadding(0, mWidth / 42, 0, mWidth / 52);

            tvBookedTul = (TextView) itemView.findViewById(R.id.tv_tul_booked);
            tvBookedTul.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

            tvTotalEarnings = (TextView) itemView.findViewById(R.id.tv_total_earnings);
            tvTotalEarnings.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

            tvShortlisted = (TextView) itemView.findViewById(R.id.tv_shortlisted);
            tvShortlisted.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
            tvShortlisted.setPadding(0, mHeight / 32, 0, mHeight / 32);

            tvViews = (TextView) itemView.findViewById(R.id.tv_views);
            tvViews.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
            tvViews.setPadding(0, mHeight / 32, 0, mHeight / 32);

            viewLine = itemView.findViewById(R.id.view);
            viewLine.setLayoutParams(lineParams);

        }
    }
}