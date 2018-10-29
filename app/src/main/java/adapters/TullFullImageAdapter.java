package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.app.backyard.PlayVideo;
import com.app.backyard.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import customviews.TouchImageView;
import model.TulImageModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by applify on 10/13/2017.
 */

public class TullFullImageAdapter extends PagerAdapter {
    Context con;
    private int mScreenwidth, mScreenheight;
    private ArrayList<TulImageModel> mData;
    private LayoutInflater inflater;
    Utils utils;

    public TullFullImageAdapter(Context con, ArrayList<TulImageModel> mData) {
        this.con = con;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        utils = new Utils(con);
        this.mScreenwidth = utils.getInt("width", 0);
        this.mScreenheight = utils.getInt("height", 0);
        this.mData = mData;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.e("position", position + "");

        View itemView = inflater.inflate(R.layout.item_full_without_touch, container, false);

        ImageView imgPic = (ImageView) itemView.findViewById(R.id.img_pic);

        RelativeLayout.LayoutParams loader_parms = new RelativeLayout.LayoutParams(mScreenwidth / 7, mScreenwidth / 7);
        loader_parms.addRule(RelativeLayout.CENTER_IN_PARENT);
        final ProgressBar pbLoad = (ProgressBar) itemView.findViewById(R.id.pb_load);
        pbLoad.setLayoutParams(loader_parms);

        ImageView imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

        if (mData.get(position).getType().equals(Constants.VIDEO) || mData.get(position).getType().equals(Constants.VIDEO_TEXT))
            imgVideoPlay.setVisibility(View.VISIBLE);
        else
            imgVideoPlay.setVisibility(View.GONE);

        imgVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, PlayVideo.class);
                intent.putExtra("video_path", mData.get(position).getPath());
                intent.putExtra("video_seek", 0);
                con.startActivity(intent);
            }
        });

        if (mData.get(position).getPath().startsWith("http")) {
            Picasso.with(con).load(mData.get(position).getThumbnails()).
                    into(imgPic, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbLoad.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
//            imgPic.setImageBitmap(getBitmapFromURL(mData.get(position).getThumbnails()));
//            pbLoad.setVisibility(View.GONE);
        } else {
            File mFile = new File(mData.get(position).getThumbnails());
            Picasso.with(con).load(mFile)
                    .into(imgPic, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbLoad.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
//            imgPic.setImageURI(Uri.parse(mData.get(position).getThumbnails()));
//            pbLoad.setVisibility(View.GONE);
        }


        ((ViewPager) container).addView(itemView);
        return itemView;
    }


    public Bitmap getBitmapFromURL(String src) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap myBitmap1 = BitmapFactory.decodeStream(input);

            return myBitmap1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
