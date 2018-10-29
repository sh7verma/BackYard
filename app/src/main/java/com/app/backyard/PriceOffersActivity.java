package com.app.backyard;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class PriceOffersActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;


    @Override
    protected int getContentView() {
        return R.layout.activity_price_offers;
    }

    @Override
    protected void initUI() {
        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.PRICE_OFFERS);
    }

    @Override
    protected void onCreateStuff() {
    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:
                moveBack();
                break;
        }
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
