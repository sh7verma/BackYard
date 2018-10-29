package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.backyard.ChatActivity;
import com.app.backyard.R;
import com.app.backyard.SearchChatActivity;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import customviews.CircleTransform;
import model.ChatDialogModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by rajat028 on 13/08/17.
 */

public class SearchChatAdapter extends BaseAdapter {
    private Context con;
    private LayoutInflater contact_inflate;
    private int mHeight;
    private int mWidth;
    private Utils utils;
    ArrayList<ChatDialogModel> mData = new ArrayList<>();
    SearchChatActivity mSearchChatActivity;
    SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    SimpleDateFormat show_format = new SimpleDateFormat("hh:mm aa");
    SimpleDateFormat show_Date_format = new SimpleDateFormat("dd-MM-yyyy");

    @SuppressWarnings("static-access")
    public SearchChatAdapter(Context context, ArrayList<ChatDialogModel> mData, SearchChatActivity mSearchChatActivity) {
        this.mData = mData;
        this.con = context;
        this.contact_inflate = LayoutInflater.from(con);
        utils = new Utils(con);
        mHeight = utils.getInt("height", 0);
        mWidth = utils.getInt("width", 0);
        this.mSearchChatActivity = mSearchChatActivity;
    }

    @Override
    public int getCount() {
        return mData.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @SuppressWarnings("deprecation")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;

        if (convertView == null) {

            convertView = contact_inflate.inflate(R.layout.item_search_chat, null);
            holder = new ViewHolder();

            holder.llMain = (LinearLayout) convertView.findViewById(R.id.ll_main);

            holder.txtName = (TextView) convertView.findViewById(R.id.txt_user_name);
            holder.txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));


            holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
            holder.txtTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

            holder.txtLastMessage = (TextView) convertView.findViewById(R.id.txt_last_message);
            holder.txtLastMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

            holder.txtUnreadCount = (TextView) convertView.findViewById(R.id.txt_unread_count);
            holder.txtUnreadCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

            holder.imgProfilePic = (ImageView) convertView.findViewById(R.id.img_profile);
            holder.imgTick = (ImageView) convertView.findViewById(R.id.img_tick);

            convertView.setTag(holder);

        } else

            holder = (ViewHolder) convertView.getTag();

        final ChatDialogModel chatDialogModel = mData.get(position);

        String particpantIdArray[] = chatDialogModel.getParticipant_ids().split(",");
        String particpantId = null;
        String myId = null;
        for (int i = 0; i < particpantIdArray.length; i++) {
            if (!particpantIdArray[i].equals(utils.getString("user_id", ""))) {
                particpantId = particpantIdArray[i];
            } else {
                myId = particpantIdArray[i];
            }
        }

        final String mOtherUserId = particpantId;

        holder.txtName.setText(chatDialogModel.getName().get(particpantId));
        holder.txtLastMessage.setText(chatDialogModel.getLast_message());

        if (chatDialogModel.getUnread_count().get(myId).equals("0")) {
            holder.txtUnreadCount.setVisibility(View.INVISIBLE);
        } else {
            holder.txtUnreadCount.setVisibility(View.VISIBLE);
            holder.txtUnreadCount.setText(chatDialogModel.getUnread_count().get(myId));
        }

        if (!TextUtils.isEmpty(chatDialogModel.getLast_message_time())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Constants.getLocalTime(Long.parseLong(chatDialogModel.getLast_message_time())));

            Calendar calendarDialog = Calendar.getInstance();
            calendarDialog.setTimeInMillis(Constants.getLocalTime(Long.parseLong(chatDialogModel.getDelete_dialog_time().get(utils.getString("user_id", "")))));

            if (calendar.getTime().after(calendarDialog.getTime())) {
                holder.txtLastMessage.setVisibility(View.VISIBLE);
            } else {
                holder.txtLastMessage.setVisibility(View.INVISIBLE);
            }
//            time(localFormat.format(calendar.getTime()));
            holder.txtTime.setText(time(localFormat.format(calendar.getTime())));
        }

        if ((mData.get(position).getProfile_pic().get(particpantId).length() > 0)) {

            Picasso.with(con).load(mData.get(position).getProfile_pic().get(particpantId))
                    .resize(mWidth / 8, mWidth / 8)
                    .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                    .transform(new CircleTransform())
                    .into(holder.imgProfilePic);
        } else {
            Picasso.with(con).load(R.mipmap.ic_small_ph_tul)
                    .resize(mWidth / 8, mWidth / 8)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(holder.imgProfilePic);
        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inChat = new Intent(con, ChatActivity.class);
                inChat.putExtra("dialog_id", mData.get(position).getChat_dialog_id());
                inChat.putExtra("name", mData.get(position).getName().get(mOtherUserId));
                inChat.putExtra("other_block_status", mData.get(position).getBlock_status().get(mOtherUserId));
                inChat.putExtra("my_block_status",mData.get(position).getBlock_status().get(utils.getString("user_id","")));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat option = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(((Activity) con), holder.imgProfilePic, "profile");
                    con.startActivity(inChat, option.toBundle());
                    ((Activity) con).finish();
                } else {
                    con.startActivity(inChat);
                    ((Activity) con).finish();
                    ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            }
        });
        return convertView;
    }


    public class ViewHolder {
        ImageView imgProfilePic, imgTick;
        TextView txtLastMessage, txtTime, txtName, txtUnreadCount;
        LinearLayout llMain;
    }

    public String time(String time) {
        try {
            Log.e("time", time);
            Calendar c = Calendar.getInstance();

            Calendar c1 = Calendar.getInstance(); // today
            c1.add(Calendar.DAY_OF_YEAR, -1);

            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date);

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                //yesterday

                time = "Yesterday, " + show_format.format(date);

            } else if (c.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                //today
                time = "Today, "+show_format.format(date);
            } else {
                time = formateddate(time)+", "+show_format.format(date);
            }
            return time;
        } catch (Exception e) {
            Log.e("Exce = ", e + "");
            return "";
        }
    }

    public String formateddate(String date) {
        Log.e("1", date);
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(date);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTime twodaysago = today.minusDays(2);
        DateTime threedaysago = today.minusDays(3);
        DateTime fourdaysago = today.minusDays(4);
        DateTime fivedaysago = today.minusDays(5);
        DateTime sixdaysago = today.minusDays(6);
        DateTime seveendaysago = today.minusDays(7);

        Log.e("3", dateTime.toLocalDate() + "");

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {

            return "Today ";
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {


            return "Yesterday ";
        } else if (dateTime.toLocalDate().equals(twodaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date , "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        }
        else if (dateTime.toLocalDate().equals(threedaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date , "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        }
        else if (dateTime.toLocalDate().equals(fourdaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date , "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        }
        else if (dateTime.toLocalDate().equals(fivedaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date , "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        }
        else if (dateTime.toLocalDate().equals(sixdaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date , "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        }
        else if (dateTime.toLocalDate().equals(seveendaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date , "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        }else {
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String str1 = show_Date_format.format(date1);
            return str1;
        }
    }

    public static String getDayFromDateString(String stringDate,String dateTimeFormat) {

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int reslut = calendar.get(Calendar.DAY_OF_WEEK);
        String day = "";
        switch (reslut) {
            case Calendar.MONDAY:
                day = "MONDAY";
                break;
            case Calendar.TUESDAY:
                day = "TUESDAY";
                break;
            case Calendar.WEDNESDAY:
                day = "WEDNESDAY";
                break;
            case Calendar.THURSDAY:
                day = "THURSDAY";
                break;
            case Calendar.FRIDAY:
                day = "FRIDAY";
                break;
            case Calendar.SATURDAY:
                day = "SATURDAY";
                break;
            case Calendar.SUNDAY:
                day = "SUNDAY";
                break;
        }

        return day;
    }

}
