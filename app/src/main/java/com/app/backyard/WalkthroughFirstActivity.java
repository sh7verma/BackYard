package com.app.backyard;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class WalkthroughFirstActivity extends BaseActivity {

    private static final int BACK = 1;
    Timer mTimer;
    @BindView(R.id.txt_hint)
    TextView txtHint;
    @BindView(R.id.img_next)
    ImageView imgNext;


    @Override
    protected int getContentView() {
        return R.layout.activity_walkthrough_first;
    }

    @Override
    protected void initUI() {
        txtHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
    }

    @Override
    protected void onCreateStuff() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mTimer.cancel();
                Intent intent = new Intent(mContext, WalkThroughSecondActivity.class);
                startActivityForResult(intent, BACK);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        }, 7000);
    }

    @Override
    protected void initListener() {
        imgNext.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return WalkthroughFirstActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        mTimer.cancel();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_next:
                mTimer.cancel();
                Intent intent = new Intent(mContext, WalkThroughSecondActivity.class);
                startActivityForResult(intent, BACK);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BACK:
                    if (mTimer != null)
                        mTimer.cancel();

                    mTimer = new Timer();
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mContext, WalkThroughSecondActivity.class);
                            startActivityForResult(intent, BACK);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        }
                    }, 7000);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
