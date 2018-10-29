package com.app.backyard;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import database.Db;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.FirebaseListeners;
import utils.Utils;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected int mWidth, mHeight;
    protected Context mContext;
    public String errorInternet;
    protected String platformStatus = "2";
    public String errorAPI;
    public String errorAccessToken;
    protected String terminateAccount;
    Utils utils;
    protected Db db;
    Gson mGson = new Gson();
    private Snackbar mSnackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(getContentView());

        utils = new Utils(BaseActivity.this);
        mContext = getContext();
        ButterKnife.bind(this);
        db = new Db(this);
        getDefaults();
        initUI();
        onCreateStuff();
        initListener();
        errorInternet = getResources().getString(R.string.internet);
        errorAPI = getResources().getString(R.string.error);
        errorAccessToken = getResources().getString(R.string.invalid_access_token);
//        terminateAccount = getResources().getString(R.string.terminate_account);

    }

    protected abstract int getContentView();

    protected void showProgress() {
        CustomLoadingDialog.getLoader().showLoader(BaseActivity.this);
    }

    protected void hideProgress() {
        CustomLoadingDialog.getLoader().dismissLoader();
    }

    protected abstract void initUI();

    protected abstract void onCreateStuff();

    protected abstract void initListener();

    protected void getDefaults() {
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        mWidth = display.widthPixels;
        mHeight = display.heightPixels;
        Log.e("Height = ", mHeight + " width " + mWidth);
        utils.setInt("width", mWidth);
        utils.setInt("height", mHeight);
    }

    public void showAlert(View view, String message) {
        mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    protected abstract Context getContext();

    protected void moveToSplash() {
        Toast.makeText(mContext, "Someone else login on another device with your credentials", Toast.LENGTH_LONG).show();
        db.deleteAllTables();
        utils.clear_shf();
        FirebaseListeners.getInstance().removeAllListeners();
        Intent inSplash = new Intent(mContext, SplashActivity.class);
        inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(inSplash);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }


    public boolean connectedToInternet() {
        if ((new Connection_Detector(mContext)).isConnectingToInternet())
            return true;
        else
            return false;
    }

    protected void showInternetAlert(View view) {
        mSnackbar = Snackbar.make(view, R.string.internet, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    public void loginFirst(final Context con) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.Login_First)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent start = new Intent(con, AfterWalkthroughActivity.class);
                        start.putExtra(Constants.REG_GUEST, 1);
                        ((Activity) con).startActivity(start);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

   /* protected void showInternetToast(Context context) {
        Toast.makeText(context, R.string.internet, Toast.LENGTH_SHORT).show();
    }

    protected void showAlertToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }*/


    public void userBlockDialog(final Context con) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.contact_admin_tul)
                .setCancelable(false)
                .setPositiveButton(R.string.contact, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                        String[] recipients = {"info@yourbackyardapp.com"};
                        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "CONTACT");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(intent);
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

    public void placeBlockDialog(final Context con) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.place_is_blocked)
                .setCancelable(false)
                .setPositiveButton(R.string.contact, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                        String[] recipients = {"info@yourbackyardapp.com"};
                        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "CONTACT");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(intent);
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


    public void userBlockDialogUser(final Context con) {
        utils.setInt(Constants.BLOCKSTATUS, 1);
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.contact_admin_user)
                .setCancelable(false)
                .setPositiveButton(R.string.contact, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                        String[] recipients = {"info@yourbackyardapp.com"};
                        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "CONTACT");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(intent);
//                        ((Activity) con).finish();
//                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
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

    public void chatBlockDialogUser(final Context con, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(s)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



}
