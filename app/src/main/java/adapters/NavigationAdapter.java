package adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.backyard.ConversationActivity;
import com.app.backyard.DashBoardActivity;
import com.app.backyard.HistoryActivity;
import com.app.backyard.ListYourTulActivity;
import com.app.backyard.OwnProfileActivity;
import com.app.backyard.PaymentActivity;
import com.app.backyard.R;
import com.app.backyard.SearchActivity;
import com.app.backyard.SearchResultActivity;
import com.app.backyard.SettingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import api.RetrofitClient;
import customviews.CircleTransform;
import database.Db;
import dialog.VerifyEmailDialog;
import model.ChatDialogModel;
import model.TransacrionPercentageModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.Utils;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_BOTTOM = 2;
    private static final int REFRESH_MARKERS = 5;

    private String mNavTitles[];
    private int mIcons[];

    private DrawerLayout dlNavigation;
    private RecyclerView rvMenu;
    private Context mContext;
    private Utils utils;
    private int mWidth, mHeight;
    private Db db;

    public NavigationAdapter(Context con, String Titles[], int Icons[], DrawerLayout drawer, RecyclerView mRecyclerView) {
        mNavTitles = Titles;
        mIcons = Icons;
        mContext = con;
        dlNavigation = drawer;
        rvMenu = mRecyclerView;
        utils = new Utils(mContext);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        db = new Db(mContext);
    }

    @Override
    public NavigationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_header, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_BOTTOM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_bottom, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final NavigationAdapter.ViewHolder holder, final int position) {

        if (holder.Holderid == 0) {
            ////header
            holder.txtName.setText(utils.getString("user_name", ""));
            holder.txtRate.setText(String.valueOf(utils.getInt("rating", 0)));

//            Picasso.with(mContext).load(utils.getString("profile_pic", "")).resize(Constants.dpToPx(40), Constants.dpToPx(40))
//                    .transform(new CircleTransform())
//                    .centerCrop().placeholder(R.mipmap.ic_no_image)
//                    .into(holder.imgProfile);
            if (!TextUtils.isEmpty(utils.getString("profile_pic", ""))) {
                Picasso.with(mContext).load(utils.getString("profile_pic", "")).resize(Constants.dpToPx(40), Constants.dpToPx(40))
                        .transform(new CircleTransform())
                        .centerCrop().placeholder(R.mipmap.ic_no_image)
                        .into(holder.imgProfile);
            } else {
                Picasso.with(mContext).load(R.mipmap.ic_no_image).resize(Constants.dpToPx(40), Constants.dpToPx(40))
                        .transform(new CircleTransform())
                        .centerCrop().placeholder(R.mipmap.ic_no_image)
                        .into(holder.imgProfile);
            }
            holder.llHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OwnProfileActivity.class);
                    ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    dlNavigation.closeDrawer(Gravity.LEFT);
                }
            });

        } else if (holder.Holderid == 1) {
            ///// item
            holder.txtMenu.setText(mNavTitles[position - 1]);
            /// prashnat sir code
            if (mNavTitles[position - 1].equals("Inbox")) {
                Map<String, ChatDialogModel> totalConversationMap = new LinkedHashMap<>();
                ArrayList<String> conversationKeys = new ArrayList<>();

                totalConversationMap = db.getAllConversation();
                for (String chatDialogId : totalConversationMap.keySet()) {
                    ChatDialogModel chatDialogModel = new ChatDialogModel();
                    chatDialogModel = totalConversationMap.get(chatDialogId);
                    if (chatDialogModel.getLast_message().equals(Constants.CHAT_REGEX)) {

                    } else {
                        if (chatDialogModel.getUnread_count().get(utils.getString("user_id", "")) != null) {
                            if (!chatDialogModel.getUnread_count().get(utils.getString("user_id", "")).equals("0")) {
                                conversationKeys.add(chatDialogId);
                            }
                        }
                    }
                }
                Log.d("Unreada", String.valueOf(conversationKeys.size()));

                if (conversationKeys.size() > 0) {
                    holder.txtBageCount.setVisibility(View.VISIBLE);
                    if (conversationKeys.size() > 99) {
                        holder.txtBageCount.setText("99+");
                    } else {
                        holder.txtBageCount.setText(String.valueOf(conversationKeys.size()));
                    }
                } else {
                    holder.txtBageCount.setVisibility(View.GONE);
                }
            } else {
                holder.txtBageCount.setVisibility(View.GONE);
            }
            /////
            holder.imgIcon.setImageResource(mIcons[position - 1]);
            if (position == 1)
                holder.viewTop.setVisibility(View.VISIBLE);
            else
                holder.viewTop.setVisibility(View.GONE);


            if (position == mNavTitles.length && utils.getInt("lender", 0) == 0)
                holder.llItem.setVisibility(View.INVISIBLE);
            else
                holder.llItem.setVisibility(View.VISIBLE);

            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Position = ", position + "");
                    if (position == 2) {
                        if (Constants.TITLE != null) {
                            /// filter applied
                            Intent searchIntent = new Intent(mContext, SearchResultActivity.class);
                            ((Activity) mContext).startActivity(searchIntent);
                            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else {
                            /// filter not applied
                            Intent searchIntent = new Intent(mContext, SearchActivity.class);
                            ((Activity) mContext).startActivity(searchIntent);
                            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        }
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 3) {
                        Intent intent = new Intent(mContext, HistoryActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 4) {
                        Intent intent = new Intent(mContext, ConversationActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 5) {
                        Intent intent = new Intent(mContext, PaymentActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 6) {
                        Intent intent = new Intent(mContext, SettingsActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 7) {
                        Constants.CURRENT_DATE = "";
                        Intent intent = new Intent(mContext, DashBoardActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 1) {
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else {
                        Toast.makeText(mContext, "Work in Progress", Toast.LENGTH_SHORT).show();
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    }
                }
            });
        } else if (holder.Holderid == 2) {
            ///// bottom
            if (utils.getInt("lender", 0) == 1)
                holder.txtBecome.setVisibility(View.GONE);
            else
                holder.txtBecome.setVisibility(View.VISIBLE);

            holder.txtBecome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                        if (utils.getInt(Constants.BLOCKSTATUS, 0) == 0) {
                            if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 1) {
                                Intent intent = new Intent(mContext, ListYourTulActivity.class);
                                mContext.startActivity(intent);
                                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                dlNavigation.closeDrawer(Gravity.LEFT);
                            }
                            else {
                                hitEmailVerifyAPI();
                            }
                        } else {
                            userBlockDialog();
                        }
                    } else {
                        Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void userBlockDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.contact_admin_tul)
                .setCancelable(false)
                .setPositiveButton(R.string.contact, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                        String[] recipients = {"info@yourbackyardapp.com"};
                        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "CONTACT");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        mContext.startActivity(intent);
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void hitEmailVerifyAPI() {
        Call<TransacrionPercentageModel> call = RetrofitClient.getInstance().transactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransacrionPercentageModel>() {
            @Override
            public void onResponse(Call<TransacrionPercentageModel> call, Response<TransacrionPercentageModel> response) {
                if (response.body().getResponse() != null) {
                    Log.e("Transaction %age = ", response.body().getResponse());

                    utils.setString("transaction_percentage", response.body().getResponse());

                    utils.setInt(Constants.ISEMAILSKIP, response.body().getIs_email_skip());
                    utils.setInt(Constants.EMAIL_VERIFY, response.body().getEmail_verify());
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getBlock_status());

                    if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 0) {
                        new VerifyEmailDialog(mContext, mWidth, mContext.getResources().getString(R.string.verify_email)).showDialog();
                    }

                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransacrionPercentageModel> call, Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return mNavTitles.length + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        if (isPositionBottom(position))
            return TYPE_BOTTOM;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionBottom(int position) {
        return position == (mNavTitles.length + 1);
    }

    void dialogLogout() {
        final Dialog phone_dialog = new Dialog(mContext);
        phone_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        phone_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        phone_dialog.setContentView(R.layout.dialog_logout);
        phone_dialog.setCanceledOnTouchOutside(true);

        FrameLayout.LayoutParams phone_params = new FrameLayout.LayoutParams(mWidth - mWidth / 6, (int) (mHeight / 3));
        final LinearLayout main_lay = (LinearLayout) phone_dialog.findViewById(R.id.main_lay);
        main_lay.setLayoutParams(phone_params);
        GradientDrawable roundDrawable = (GradientDrawable) main_lay.getBackground();
        roundDrawable.setCornerRadius(52);

        TextView txtMsg = (TextView) phone_dialog.findViewById(R.id.txt_msg);
        txtMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.050));
        txtMsg.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        TextView txtCancel = (TextView) phone_dialog.findViewById(R.id.txt_cancel);
        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        txtCancel.setPadding(mWidth / 24, mWidth / 32, mWidth / 24, mWidth / 32);

        TextView txtConfirm = (TextView) phone_dialog.findViewById(R.id.txt_confirm);
        txtConfirm.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        txtConfirm.setPadding(mWidth / 24, mWidth / 32, mWidth / 24, mWidth / 32);

        txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.moveToSplash(mContext, utils);
                phone_dialog.dismiss();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_dialog.dismiss();
            }
        });
        phone_dialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        /// header
        LinearLayout llHeader;
        ImageView imgProfile;
        TextView txtName, txtRate;

        /// item
        LinearLayout llItem;
        ImageView imgIcon;
        TextView txtMenu;
        TextView txtBageCount;
        View viewTop;

        /// bottom
        TextView txtBecome;


        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_HEADER) {
                llHeader = (LinearLayout) itemView.findViewById(R.id.ll_header);
                imgProfile = (ImageView) itemView.findViewById(R.id.img_profile);

                txtName = (TextView) itemView.findViewById(R.id.txt_name);
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                txtRate = (TextView) itemView.findViewById(R.id.txt_rate);
                txtRate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
                Holderid = 0;
            } else if (ViewType == TYPE_ITEM) {
                llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
                imgIcon = (ImageView) itemView.findViewById(R.id.img_icon);

                txtMenu = (TextView) itemView.findViewById(R.id.txt_menu);
                txtMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                txtBageCount = (TextView) itemView.findViewById(R.id.txt_bageCount);
                txtBageCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.030));

                viewTop = (View) itemView.findViewById(R.id.view_top);
                Holderid = 1;
            } else if (ViewType == TYPE_BOTTOM) {
                txtBecome = (TextView) itemView.findViewById(R.id.txt_become);
                txtBecome.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
                Holderid = 2;
            }
        }
    }


}
