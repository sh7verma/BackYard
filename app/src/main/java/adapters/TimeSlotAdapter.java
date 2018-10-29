package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.backyard.R;

import java.util.ArrayList;

import static com.app.backyard.CheckAvailablityActivity.getBookingTime;

/**
 * Created by dev on 25/1/18.
 */

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {
    Context context;
    ArrayList<String> mSlotsArray;
   public static int temPosition = -1;

    public TimeSlotAdapter(Context context, ArrayList<String> mSlotsArray) {
        this.context = context;
        this.mSlotsArray = mSlotsArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slots, parent, false);
        ViewHolder vhItem = new TimeSlotAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtTime.setText(mSlotsArray.get(position));
        if (temPosition == position) {
            holder.imgRadio.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_radio_btn_s));
        } else {
            holder.imgRadio.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_radio_btn));
        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temPosition = position;
                getBookingTime(mSlotsArray.get(position));
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mSlotsArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRadio;
        TextView txtTime;
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            imgRadio = (ImageView) itemView.findViewById(R.id.img_radio_btn);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
        }
    }

}
