package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.backyard.ActiveBookingActivity;
import com.app.backyard.OtherUserProfileActivity;
import com.app.backyard.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import customviews.CircleTransform;
import model.BookTulModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 14/11/17.
 */

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int VIEW_PROG = 2;
    private static final int TYPE_HEADER = 0;
    Typeface typeface, typefaceRegular;
    private Context mContext;
    private Utils utils;
    private int mWidth, mHeight;
    private ArrayList<BookTulModel.ResponseBean> mData = new ArrayList<>();

    public MyBookingAdapter(Context con, ArrayList<BookTulModel.ResponseBean> mData) {
        this.mContext = con;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        this.mData = mData;
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/semibold.ttf");
        typefaceRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/regular.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_booking, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == VIEW_PROG) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_header, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder.Holderid == 0) {
            holder.llTulLent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActiveBookingActivity) mContext).lent();
                }
            });
            holder.tvMyBookingCount.setText(String.valueOf(utils.getInt("booking_count", 0)));
            holder.tvLentCount.setText(String.valueOf(utils.getInt("lent_count", 0)));

        } else if (holder.Holderid == 1) {
            position = position - 1;
            holder.tvName.setText(mData.get(position).getOwner());

            Picasso.with(mContext).load(mData.get(position).getOwner_pic())
                    .resize(mWidth / 7, mWidth / 7).transform(new CircleTransform())
                    .centerCrop().into(holder.imgUser);

            holder.tvTulname.setText(mData.get(position).getTitle());

            holder.tvTulPrice.setText(mData.get(position).getCurrency()
                    + String.format(Locale.getDefault(),"%.2f", Double.parseDouble(mData.get(position).getTotal_amount().replace(",","."))));

            try {
                holder.tvDate.setText(Constants.convertUTCtoGMT(mData.get(position).getBooked_at()));

                if (mData.get(position).getProduct_type().equals("1")) {
                    holder.tvBookingDate.setText(Constants.convertDate(mData.get(position).getDelivery_date()));
                    holder.tvReturnDate.setText(Constants.convertDate(mData.get(position).getReturn_date()));
                } else {
                    holder.tvBookingDate.setText(Constants.convertDate(mData.get(position).getDelivery_date()));
                    holder.tvReturnDate.setText(Constants.addOneDayToDate(mData.get(position).getReturn_date()));
                }

            } catch (ParseException e) {
                Log.e("Date Exce = ", e.getLocalizedMessage());
            }

            if (mData.get(position).getLender_status() == 0 && mData.get(position).getBorrower_status() == 0)
                holder.tvCancel.setVisibility(View.VISIBLE);
            else
                holder.tvCancel.setVisibility(View.GONE);

            try {
                if (mData.get(position).getBorrower_status() == 0) {
                    /// both show black box

                    /// recieve checks
                    holder.imgTulRecieved.setImageResource(R.mipmap.ic_mark_rec);
                    holder.tvTulRecieved.setTypeface(typefaceRegular);
                    holder.tvtulRecievedDate.setVisibility(View.INVISIBLE);

                    /// returned checks
                    holder.imgTulReturned.setImageResource(R.mipmap.ic_mark_rec);
                    holder.tvTulReturned.setTypeface(typefaceRegular);
                    holder.tvTulReturnedDate.setVisibility(View.INVISIBLE);
                } else if (mData.get(position).getBorrower_status() == 1) {
                    /// recived but not returned
                    /// recieve checks
                    holder.imgTulRecieved.setImageResource(R.mipmap.ic_checked);
                    holder.tvTulRecieved.setTypeface(typeface);
                    holder.tvtulRecievedDate.setVisibility(View.VISIBLE);
                    holder.tvtulRecievedDate.setText(Constants.convertUTCtoGMT(mData.get(position).getBorrower_received_at()));

                    /// returned checks
                    holder.imgTulReturned.setImageResource(R.mipmap.ic_mark_rec);
                    holder.tvTulReturned.setTypeface(typefaceRegular);
                    holder.tvTulReturnedDate.setVisibility(View.INVISIBLE);
                } else if (mData.get(position).getBorrower_status() == 2) {
                    /// recived and returned
                    /// recieve checks
                    holder.imgTulRecieved.setImageResource(R.mipmap.ic_checked);
                    holder.tvTulRecieved.setTypeface(typeface);
                    holder.tvtulRecievedDate.setVisibility(View.VISIBLE);
                    holder.tvtulRecievedDate.setText(Constants.convertUTCtoGMT(mData.get(position).getBorrower_received_at()));

                    /// returned checksconvertUTCtoGMT
                    holder.imgTulReturned.setImageResource(R.mipmap.ic_checked);
                    holder.tvTulReturned.setTypeface(typeface);
                    holder.tvTulReturnedDate.setVisibility(View.VISIBLE);
                    holder.tvTulReturnedDate.setText(Constants.convertUTCtoGMT(mData.get(position).getBorrower_returned_at()));
                }
            } catch (Exception e) {
                Log.e("Exce  = ", e + "");
            }

            final int finalPosition = position;
            holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActiveBookingActivity) mContext).cancelTulBooking(String.valueOf(mData.get(finalPosition).getTool_id()),
                            String.valueOf(mData.get(finalPosition).getBooking_id()), finalPosition);
                }
            });

            holder.rlTulReceived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /// auto check IN

//                    if (mData.get(finalPosition).getLender_status() == 1 && mData.get(finalPosition).getBorrower_status() == 0) {
//                        /// change borrow status to 1
//                        ((ActiveBookingActivity) mContext).receivedTul(String.valueOf(mData.get(finalPosition).getTool_id()),
//                                String.valueOf(mData.get(finalPosition).getBooking_id()), finalPosition);
//                    } else if (mData.get(finalPosition).getLender_status() == 0) {
//                        /// show toast message.
//                        Toast.makeText(mContext, "It seems Lender has not handed over this Place yet, Please contact Lender.", Toast.LENGTH_SHORT).show();
//                    }
                }
            });

            holder.llTulReturned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(finalPosition).getBorrower_status() == 1) {
                        /// change borrow status to 2
                        ((ActiveBookingActivity) mContext).returnTul(String.valueOf(mData.get(finalPosition).getTool_id()),
                                String.valueOf(mData.get(finalPosition).getBooking_id()), finalPosition, mData.get(finalPosition).getOwner(),
                                mData.get(finalPosition).getOwner_pic(), mData.get(finalPosition).getTotal_amount(), mData.get(finalPosition).getAddress(),
                                mData.get(finalPosition).getUser_id(), mData.get(finalPosition).getBorrower_id());
                    }else if(mData.get(finalPosition).getBorrower_status() == 0){
                        Toast.makeText(mContext, R.string.You_cannot_check_out_before_the_scheduled_booking_date_and_time, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActiveBookingActivity) mContext).moveToTulDetailBooking(finalPosition);
                }
            });
            holder.rlProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.PROFILE_ID = mData.get(finalPosition).getUser_id();
                    Intent intent = new Intent(mContext, OtherUserProfileActivity.class);
                    intent.putExtra("other_user_id", mData.get(finalPosition).getUser_id());
                    intent.putExtra("other_user_name", mData.get(finalPosition).getOwner());
                    intent.putExtra("other_user_pic", mData.get(finalPosition).getOwner_pic());
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return mData.get(position - 1) != null ? TYPE_ITEM : VIEW_PROG;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        CardView cvMain;
        LinearLayout llMain, llDelivery, llDots, llDeliveryData, llInner, llClick, llTulReturned;
        RelativeLayout rlTulReceived, rlProfile;
        ImageView imgUser, imgDot1, imgDot2, imgTulRecieved, imgTulReturned;
        TextView tvOwner, tvName, tvDate, tvTulname, tvTulPrice, tvReturnDate, tvReturnTitle, tvCancel, txtBookings,
                tvBookingTitle, tvBookingDate, tvTulRecieved, tvTulReturned, tvtulRecievedDate, tvTulReturnedDate;
        View line, view1;
        ProgressBar progressBar;

        LinearLayout llMyBooking, llTulLent;
        TextView tvMyBookingCount, tvMy, tvBooking, tvLentCount, tvLent, tvtul;

        Space space;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_HEADER) {

                llMyBooking = (LinearLayout) itemView.findViewById(R.id.ll_my_booking);
                llMyBooking.setPadding(mWidth / 24, mHeight / 42, 0, mHeight / 42);
                llMyBooking.setBackground(ContextCompat.getDrawable(mContext, R.drawable.white_color_stroke));

                tvMyBookingCount = (TextView) itemView.findViewById(R.id.tv_my_booking_count);
                tvMyBookingCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.08));

                tvMy = (TextView) itemView.findViewById(R.id.tv_my);
                tvMy.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                tvBooking = (TextView) itemView.findViewById(R.id.tv_booking);
                tvBooking.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                llTulLent = (LinearLayout) itemView.findViewById(R.id.ll_tul_lent);
                llTulLent.setPadding(mWidth / 24, mHeight / 42, 0, mHeight / 42);
                llTulLent.setBackground(ContextCompat.getDrawable(mContext, R.drawable.black_color_white_stroke));

                tvLentCount = (TextView) itemView.findViewById(R.id.tv_my_lent_count);
                tvLentCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.08));

                tvLent = (TextView) itemView.findViewById(R.id.tv_lent);
                tvLent.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                tvtul = (TextView) itemView.findViewById(R.id.tv_tul);
                tvtul.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                tvtul.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));
                tvLentCount.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));
                tvLent.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));

                tvBooking.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                tvMyBookingCount.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                tvMy.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mWidth / 32, ViewGroup.LayoutParams.MATCH_PARENT);
                space = (Space) itemView.findViewById(R.id.space);
                space.setLayoutParams(layoutParams);

                Holderid = 0;
            } else if (viewType == TYPE_ITEM) {
                llInner = (LinearLayout) itemView.findViewById(R.id.ll_inner);
                llInner.setPadding(0, mWidth / 32, 0, mWidth / 32);

                llClick = (LinearLayout) itemView.findViewById(R.id.ll_click);

                rlProfile = (RelativeLayout) itemView.findViewById(R.id.rl_profile);

                cvMain = (CardView) itemView.findViewById(R.id.cv_main);
                cvMain.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

                llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
                llMain.setPadding(0, mWidth / 32, 0, mWidth / 32);

                RelativeLayout.LayoutParams profileParms = new RelativeLayout.LayoutParams(mWidth / 7, mWidth / 7);
                profileParms.addRule(RelativeLayout.CENTER_VERTICAL);
                imgUser = (ImageView) itemView.findViewById(R.id.img_user);
                imgUser.setPadding(mWidth / 32, 0, 0, 0);
                imgUser.setLayoutParams(profileParms);

                tvOwner = (TextView) itemView.findViewById(R.id.tv_owner);
                tvOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvOwner.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, 0);

                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
                tvName.setPadding(mWidth / 32, 0, mWidth / 32, 0);

                tvDate = (TextView) itemView.findViewById(R.id.tv_date);
                tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvDate.setPadding(mWidth / 32, 0, mWidth / 32, 0);

                tvTulname = (TextView) itemView.findViewById(R.id.tv_tul_name);
                tvTulname.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvTulname.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

                tvTulPrice = (TextView) itemView.findViewById(R.id.tv_tul_price);
                tvTulPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvTulPrice.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

                llDots = (LinearLayout) itemView.findViewById(R.id.ll_dots);
                llDots.setPadding(mWidth / 32, mWidth / 76, 0, 0);

                imgDot1 = (ImageView) itemView.findViewById(R.id.img_dot1);
                imgDot2 = (ImageView) itemView.findViewById(R.id.img_dot2);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mWidth / 46, mWidth / 46);
                imgDot1.setLayoutParams(layoutParams);
                imgDot2.setLayoutParams(layoutParams);

                view1 = itemView.findViewById(R.id.view1);
                view1.setPadding(0, mWidth / 42, 0, mWidth / 42);

                line = itemView.findViewById(R.id.view_line1);

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(2, mHeight / 20);
                layoutParams1.gravity = Gravity.CENTER;
                line.setLayoutParams(layoutParams1);

                llDeliveryData = (LinearLayout) itemView.findViewById(R.id.ll_delivery_data);
                llDeliveryData.setPadding(mWidth / 24, 0, mWidth / 24, 0);

                tvBookingTitle = (TextView) itemView.findViewById(R.id.tv_delivery_title);
                tvBookingTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                tvBookingDate = (TextView) itemView.findViewById(R.id.tv_delivery_date);
                tvBookingDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvBookingDate.setPadding(0, 0, 0, mHeight / 64);

                tvReturnTitle = (TextView) itemView.findViewById(R.id.tv_return_title);
                tvReturnTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                tvReturnDate = (TextView) itemView.findViewById(R.id.tv_return_date);
                tvReturnDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

                rlTulReceived = (RelativeLayout) itemView.findViewById(R.id.rl_tul_received);
                rlTulReceived.setPadding(0, mHeight / 32, 0, 0);

                tvTulRecieved = (TextView) itemView.findViewById(R.id.tv_tul_recieved);
                tvTulRecieved.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                tvtulRecievedDate = (TextView) itemView.findViewById(R.id.tv_tul_recieved_date);
                tvtulRecievedDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvtulRecievedDate.setPadding(0, 0, 0, mWidth / 64);

                llTulReturned = (LinearLayout) itemView.findViewById(R.id.ll_tul_returned);

                tvTulReturned = (TextView) itemView.findViewById(R.id.tv_tul_returned);
                tvTulReturned.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                tvTulReturnedDate = (TextView) itemView.findViewById(R.id.tv_tul_returned_date);
                tvTulReturnedDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvTulReturnedDate.setPadding(0, 0, 0, mWidth / 64);

                imgTulRecieved = (ImageView) itemView.findViewById(R.id.img_tul_received_check);
                imgTulRecieved.setPadding(mWidth / 32, 0, mWidth / 76, 0);

                imgTulReturned = (ImageView) itemView.findViewById(R.id.img_tul_rreturned_check);
                imgTulReturned.setPadding(mWidth / 32, 0, mWidth / 76, 0);

                tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel);

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams3.setMargins(0, 0, mWidth / 32, 0);
                tvCancel.setLayoutParams(layoutParams3);

                tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvCancel.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);

                Holderid = 1;
            } else {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
                Holderid = 2;
            }
        }
    }
}