package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.app.backyard.CategoryTulListAcitivty;
import com.app.backyard.LandingActivity;
import com.app.backyard.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import customviews.RippleBackground;
import database.Db;
import model.CategoriesModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by applify on 9/29/2017.
 */

public class LandingCategoriesAdapter extends RecyclerView.Adapter<LandingCategoriesAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context con;
    private Utils utils;
    private FrameLayout.LayoutParams mainParms,actualParms;
    ArrayList<CategoriesModel.ResponseBean> mData;
    private int mWidth, mHeight;
    Db db;

    public LandingCategoriesAdapter(Context con, ArrayList<CategoriesModel.ResponseBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        db = new Db(con);
        mainParms = new FrameLayout.LayoutParams(0, 0);
        actualParms = new FrameLayout.LayoutParams(Constants.dpToPx(160), Constants.dpToPx(160));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_landing_categories, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ripple, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder.Holderid == 1) {
            holder.txtTittle.setText(mData.get(position - 1).getCategory_name());

            holder.cvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(con, CategoryTulListAcitivty.class);
                    intent.putExtra("id", String.valueOf(mData.get(position - 1).getId()));
                    intent.putExtra("category", mData.get(position - 1).getCategory_name());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                                holder.txtTittle, con.getString(R.string.transition));
                        con.startActivity(intent, option.toBundle());
                    } else {
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    }
                }
            });

            Picasso.with(con).load(mData.get(position - 1).getImage())
                    .resize(Constants.dpToPx(160), Constants.dpToPx(160)).centerCrop()
                    .placeholder(R.drawable.gradient_ripple).into(holder.imgCat);
        } else {
            holder.rippleBackground.startRippleAnimation();
            if (utils.getInt("booking_count", 0) == 0 && utils.getInt("lent_count", 0) == 0) {
                holder.rlRipple.setLayoutParams(mainParms);
                holder.rlRipple.setVisibility(View.GONE);
            } else {
                holder.rlRipple.setLayoutParams(actualParms);
                holder.rlRipple.setVisibility(View.VISIBLE);
            }
            holder.rlRipple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LandingActivity) con).moveToActiveBookings();
                }
            });
            holder.txtNo.setText(String.valueOf(utils.getInt("booking_count", 0)));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView txtTittle, txtNo, txtActive;
        CardView rlRipple;
        CardView cvContent;
        ImageView imgCat;
        RippleBackground rippleBackground;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_HEADER) {

                rlRipple = (CardView) itemView.findViewById(R.id.rl_ripple);

                txtNo = (TextView) itemView.findViewById(R.id.txt_no);
                txtNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.06));
                txtNo.setPadding(0, 0, 0, mWidth / 64);
                txtNo.setVisibility(View.GONE);

                txtActive = (TextView) itemView.findViewById(R.id.txt_active);
                txtActive.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));

                rippleBackground = (RippleBackground) itemView.findViewById(R.id.rippleBackground);
                Holderid = 0;
            } else {
                Holderid = 1;
                txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
                txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
                cvContent = (CardView) itemView.findViewById(R.id.cv_content);

                imgCat = (ImageView) itemView.findViewById(R.id.img_cat);
            }
        }
    }
}
