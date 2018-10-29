package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.backyard.BackYardDetailActivity;
import com.app.backyard.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import model.ProfileModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by applify on 10/9/2017.
 */

public class ListedTulAdapter extends RecyclerView.Adapter<ListedTulAdapter.ViewHolder> {

    public static final int TULDETAIL = 3;
    private static final int TYPE_ITEM = 1;
    ArrayList<ProfileModel.ResponseBean.ProductBean> mData;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight;

    public ListedTulAdapter(Context con, ArrayList<ProfileModel.ResponseBean.ProductBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listed_tuls, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (mData.get(position).getAttachment().get(0).getType().equals(Constants.IMAGE_TEXT)) {
            holder.imgVideoPlay.setVisibility(View.GONE);
            Picasso.with(con).load(mData.get(position).getAttachment().get(0).getAttachment())
                    .resize((int) (mWidth), (mHeight / 3) + 5).centerCrop()
                    .placeholder(R.drawable.primary_ripple).into(holder.imgTul, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Log.e("Error = ", "Yes");
                }
            });
        } else {
            holder.imgVideoPlay.setVisibility(View.VISIBLE);
            Picasso.with(con).load(mData.get(position).getAttachment().get(0).getThumbnail())
                    .resize((mWidth), mHeight / 3).centerCrop()
                    .placeholder(R.drawable.primary_ripple).into(holder.imgTul, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Log.e("Error = ", "Yes");
                }
            });
        }

        holder.txtTittle.setText(mData.get(position).getTitle());
        if (!TextUtils.isEmpty(mData.get(position).getTransaction_percentage())) {
            holder.txtPrice.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, ""))
                    + getEarningAmount(mData.get(position).getPrice(),
                    mData.get(position).getTransaction_percentage()));
        } else {
            holder.txtPrice.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")) + mData.get(position).getPrice());
        }

        holder.cvTul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, BackYardDetailActivity.class);
                intent.putExtra("data", mData.get(position));
                intent.putExtra("position", position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                            holder.imgTul, con.getString(R.string.transition));
                    ((Activity) con).startActivityForResult(intent, TULDETAIL, option.toBundle());
                } else {
                    ((Activity) con).startActivityForResult(intent, TULDETAIL);
                    ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    private String getEarningAmount(String price, String transactionPercentage) {
        double i = Double.parseDouble(String.valueOf(price));
        i = i - ((Double.parseDouble(transactionPercentage) * i) / 100);
        return amountConversion(String.valueOf(i));
    }

    public String amountConversion(String am) {
        String amount;
        Double d = Double.parseDouble(am);
        Locale locale = new Locale("en", "UK");
        String pattern = "#0.00";

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        amount = decimalFormat.format(d);
        return amount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTittle, txtPrice;
        CardView cvTul;
        ImageView imgTul, imgVideoPlay;
        View view1;

        public ViewHolder(View itemView) {
            super(itemView);

            LinearLayout.LayoutParams cvParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (mHeight / 3));
            cvParms.setMargins(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
            cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
            cvTul.setLayoutParams(cvParms);
            cvTul.setCardElevation(Constants.dpToPx(5));

            LinearLayout.LayoutParams viewParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            viewParms.setMargins(mWidth / 16, 0, mWidth / 16, 0);
            view1 = (View) itemView.findViewById(R.id.view_1);
            view1.setLayoutParams(viewParms);

            imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

            txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            txtTittle.setPadding(mWidth / 32, 0, mWidth / 5, mWidth / 28);

            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            txtPrice.setPadding(0, 0, mWidth / 32, mWidth / 28);

            imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);
        }
    }
}
