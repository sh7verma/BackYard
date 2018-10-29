package utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.app.backyard.AfterWalkthroughActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import database.Db;

/**
 * Created by kshitij on 21-Apr-17.
 */

public class Constants {

    //KEYS
    public static final String EMAIL_LOGIN = "1";
    public static final String EMPTY = "";
    public static final String FACEBOOK_LOGIN = "2";
    public static final String IMAGE = "1";
    public static final String VIDEO = "2";
    public static final String IMAGE_TEXT = "image";
    public static final String VIDEO_TEXT = "video";
    public static final String POUND = "£";
    public static final String EURO = "€";

    public static final String RULES_REGEX = "-#-TUL-#-";
    public static final String RULES_REGEX_RESPONSE = "$#$TUL$#$";
    public static final double MAX_PRICE = 999.00;
    public static final double MIN_PRICE = 0.0001;
    public static final double MAX_SECURITY_CHARGES = 900000.00;
    public static final double MAX_DISCOUNT = 99;

    public static final String SOMETIMES = "Sometimes";
    public static final String LIKE_VALUE = "3";
    public static final String DONT_LIKE_VALUE = "1";
    public static final String SOMETIMES_VALUE = "2";
    public static final int PLATFORM_STATUS = 0;
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    public static final String TRUE_VALUE = "1";
    public static final String FALSE_VALUE = "2";
    public static final String ONLINE = "1";
    public static final String OFFLINE = "0";
    public static final String BADGE_COUNT = "0";
    public static final String USER_ENDPOINT = "Users";
    public static final String CHAT_ENDPOINT = "Chats";
    public static final String MESSAGES_ENDPOINT = "Messages";
    public static final String NOTIFICATION_ENDPOINT = "Notifications";
    public static final String CHAT_REGEX = "<*&^(BackYard)^&*>";
    public static final int SEND = 0;
    public static final int DELEIVERED = 1;
    public static final int READ = 2;
    public static final String UNMUTE = "1";
    public static final String MUTE = "2";
    public static final String EMAIL_VERIFY = "email_verify";

    public static final String BLOCKSTATUS = "block_status";
    public static final String ISGUEST = "is_guest";
    public static final String ISEMAILSKIP = "is_email_skip";
    public static final String BLOCKEDERROR = "417";
    public static final String REG_GUEST = "regguest";

    public static final String PRIMARY_CURRENCY = "primary_currency";
    public static final String IS_CURRENCY_SELECTED = "is_currency_selected";
    public static final String UNVERIFIED_EMAIL = "unverified_email";

    public static final String KEY = "testing@098";
    public static final String IV = "988776655443322110";

    public static final int LENDER_REPORT = 1;
    public static final int USER_REPORT = 2;
    public static final String BROADCAST = "broadcast";
    public static final String NOTIFICATION = "notification";
    public static final String TRANSCHANGED = "404";

    //live
    public static String STRIPE_LIVE_KEY = "pk_live_lTDfmOQS45JhTWyGJAVJkPy9";
    //dev
//    public static String STRIPE_LIVE_KEY = "pk_test_SJBjfpkUeoU66rSpBGfYFTpu";
    //new dev
 //  public static String STRIPE_LIVE_KEY = "sk_test_9CAdtOtZL4CO8SSoGZ1l8qEb";

    public static int TUL_ID;
    public static int PROFILE_ID;
    public static int CHAT_PROFILE_ID;


    public static String AMENITIES;
    public static String TITLE;
    public static int CATEGORY_ID;
    public static String CATEGORY;
    public static int DISTANCE;
    public static double LATITUDE;
    public static double LONGITUDE;
    public static String ADDRESS;
    public static int MAX_PRICE_SEARCH;
    public static int MIN_PRICE_SEARCH;
    public static int AVAILABILITY;
    public static int FILTER_COUNT;
    public static boolean BEST_RATED;
    public static int PRODUCT_TYPE = 2;

    public static String CATEGORY_TITLE;
    public static int CATEGORY_DISTANCE;
    public static double CATEGORY_LATITUDE;
    public static double CATEGORY_LONGITUDE;
    public static int CATEGORY_CATEGORY_ID;
    public static String CATEGORY_CATEGORY;
    public static String CATEGORY_ADDRESS;
    public static int CATEGORY_MAX_PRICE_SEARCH;
    public static int CATEGORY_MIN_PRICE_SEARCH;
    public static int CATEGORY_AVAILABILTY;
    public static int CATEGORY_FILTER_COUNT;
    public static boolean CATEGORY_BEST_RATED;
    public static String CATEGORY_AMENITIES;
    public static int CATEGORY_PRODUCT_TYPE = 2;
    public static double TRANSACTION_PERCENTAGE;


    // Calender
    public static String CURRENT_DATE = "";
    public static int TYPE = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public static long getLocalTime(long time) {
        long localTime = 0;
        String dateValue = "";
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Calendar calUtc = Calendar.getInstance();

            SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localFormat.setTimeZone(TimeZone.getDefault());
            Calendar calLocal = Calendar.getInstance();

            calUtc.setTimeInMillis(time);
            dateValue = utcFormat.format(calUtc.getTime());
            Date value = utcFormat.parse(dateValue);

            dateValue = localFormat.format(value);
            Date localvalue = localFormat.parse(dateValue);
            localTime = localvalue.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return localTime;
    }


    public static long getUtcTime(long time) {
        long utcTime = 0;
        String dateValue = "";
        try {
// Log.e("local long time",""+time);
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Calendar calUtc = Calendar.getInstance();

            SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localFormat.setTimeZone(TimeZone.getDefault());
            Calendar calLocal = Calendar.getInstance();

            calLocal.setTimeInMillis(time);
            dateValue = localFormat.format(calLocal.getTime());
            Date value = localFormat.parse(dateValue);

            dateValue = utcFormat.format(value);
            calUtc.setTime(utcFormat.parse(dateValue));
            utcTime = calUtc.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return utcTime;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static void closeKeyboard(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context mContext, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    public static void moveToSplash(Context mContext, Utils utils) {
        Toast.makeText(mContext, "Someone else login on another device with your credentials", Toast.LENGTH_LONG).show();
        Db db = new database.Db(mContext);
        db.deleteAllTables();
        utils.clear_shf();
        Intent inSplash = new Intent(mContext, AfterWalkthroughActivity.class);
        inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(inSplash);
    }

    public static String convertDateTime(String endDate)
            throws ParseException {
        SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        DateFormat date_gmt = new SimpleDateFormat("Z", Locale.US);
        String gmt_text = date_gmt.format(calendar.getTime());

        Date utc_date = utc_format.parse(endDate);
        Calendar utc_create = Calendar.getInstance();
        utc_create.setTime(utc_date);

        int hh = 0, mm = 0;
        if (gmt_text.trim().length() == 3) {

        } else {
            hh = Integer.parseInt(gmt_text.substring(1, 3));
            mm = Integer.parseInt(gmt_text.substring(3, 5));

            if (gmt_text.substring(0, 1).equals("+")) {
                utc_create.add(Calendar.HOUR_OF_DAY, hh);
                utc_create.add(Calendar.MINUTE, mm);

            } else if (gmt_text.substring(0, 1).equals("-")) {
                utc_create.add(Calendar.HOUR_OF_DAY, -hh);
                utc_create.add(Calendar.MINUTE, -mm);

            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm aa", Locale.US);
        return dateFormat.format(utc_create.getTime());
    }


    public static String convertTime(String endDate)
            throws ParseException {
        SimpleDateFormat utc_format = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        DateFormat date_gmt = new SimpleDateFormat("Z", Locale.US);
        String gmt_text = date_gmt.format(calendar.getTime());

        Date utc_date = utc_format.parse(endDate);
        Calendar utc_create = Calendar.getInstance();
        utc_create.setTime(utc_date);

        int hh = 0, mm = 0;
        if (gmt_text.trim().length() == 3) {

        } else {
            hh = Integer.parseInt(gmt_text.substring(1, 3));
            mm = Integer.parseInt(gmt_text.substring(3, 5));

            if (gmt_text.substring(0, 1).equals("+")) {
                utc_create.add(Calendar.HOUR_OF_DAY, hh);
                utc_create.add(Calendar.MINUTE, mm);
            } else if (gmt_text.substring(0, 1).equals("-")) {
                utc_create.add(Calendar.HOUR_OF_DAY, -hh);
                utc_create.add(Calendar.MINUTE, -mm);
            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(utc_create.getTime());
    }

    public static String convertDate(String endDate)
            throws ParseException {
      /*  SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        DateFormat date_gmt = new SimpleDateFormat("Z", Locale.US);
        String gmt_text = date_gmt.format(calendar.getTime());

        Date utc_date = utc_format.parse(endDate);
        Calendar utc_create = Calendar.getInstance();
        utc_create.setTime(utc_date);

        int hh = 0, mm = 0;
        if (gmt_text.trim().length() == 3) {

        } else {
            hh = Integer.parseInt(gmt_text.substring(1, 3));
            mm = Integer.parseInt(gmt_text.substring(3, 5));

            if (gmt_text.substring(0, 1).equals("+")) {
                utc_create.add(Calendar.HOUR_OF_DAY, hh);
                utc_create.add(Calendar.MINUTE, mm);

            } else if (gmt_text.substring(0, 1).equals("-")) {
                utc_create.add(Calendar.HOUR_OF_DAY, -hh);
                utc_create.add(Calendar.MINUTE, -mm);

            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        return dateFormat.format(utc_create.getTime());*/

        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US);
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            Date date = parseFormat.parse(endDate);
            System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));

            return displayFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertUTCtoGMT(String inputDate) {
        try {
            SimpleDateFormat utc_format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
            DateFormat date_gmt = new SimpleDateFormat("Z", Locale.US);
            String gmt_text = date_gmt.format(calendar.getTime());

            Date utc_date = utc_format.parse(inputDate);
            Calendar utc_create = Calendar.getInstance();
            utc_create.setTime(utc_date);

            int hh = 0, mm = 0;
            if (gmt_text.trim().length() == 3) {

            } else {
                hh = Integer.parseInt(gmt_text.substring(1, 3));
                mm = Integer.parseInt(gmt_text.substring(3, 5));

                if (gmt_text.substring(0, 1).equals("+")) {
                    utc_create.add(Calendar.HOUR_OF_DAY, hh);
                    utc_create.add(Calendar.MINUTE, mm);

                } else if (gmt_text.substring(0, 1).equals("-")) {
                    utc_create.add(Calendar.HOUR_OF_DAY, -hh);
                    utc_create.add(Calendar.MINUTE, -mm);

                }
            }

            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US);
            return dateFormat.format(utc_create.getTime());
        } catch (Exception e) {
            Log.e("UTC Exce = ", e + "");
        }
        return "";
    }

    public static String convertSelectdDate(String endDate)
            throws ParseException {
       /* SimpleDateFormat utc_format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        DateFormat date_gmt = new SimpleDateFormat("Z", Locale.US);
        String gmt_text = date_gmt.format(calendar.getTime());

        Date utc_date = utc_format.parse(endDate);
        Calendar utc_create = Calendar.getInstance();
        utc_create.setTime(utc_date);

        int hh = 0, mm = 0;
        if (gmt_text.trim().length() == 3) {

        } else {
            hh = Integer.parseInt(gmt_text.substring(1, 3));
            mm = Integer.parseInt(gmt_text.substring(3, 5));

            if (gmt_text.substring(0, 1).equals("+")) {
                utc_create.add(Calendar.HOUR_OF_DAY, hh);
                utc_create.add(Calendar.MINUTE, mm);

            } else if (gmt_text.substring(0, 1).equals("-")) {
                utc_create.add(Calendar.HOUR_OF_DAY, -hh);
                utc_create.add(Calendar.MINUTE, -mm);

            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        return dateFormat.format(utc_create.getTime());*/

        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date = parseFormat.parse(endDate);
            System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));

            return displayFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String displayDateTime(String endDate)
            throws ParseException {

        SimpleDateFormat utc_format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        DateFormat date_gmt = new SimpleDateFormat("Z", Locale.US);
        String gmt_text = date_gmt.format(calendar.getTime());

        Date utc_date = utc_format.parse(endDate);
        Calendar utc_create = Calendar.getInstance();
        utc_create.setTime(utc_date);

        int hh = 0, mm = 0;
        if (gmt_text.trim().length() == 3) {

        } else {
            hh = Integer.parseInt(gmt_text.substring(1, 3));
            mm = Integer.parseInt(gmt_text.substring(3, 5));

            if (gmt_text.substring(0, 1).equals("+")) {
                utc_create.add(Calendar.HOUR_OF_DAY, hh);
                utc_create.add(Calendar.MINUTE, mm);
            } else if (gmt_text.substring(0, 1).equals("-")) {
                utc_create.add(Calendar.HOUR_OF_DAY, -hh);
                utc_create.add(Calendar.MINUTE, -mm);
            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd MMM HH:mm");
        return dateFormat.format(utc_create.getTime());
    }


    public static String convertOnlyTime(String time) {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
            Date date = parseFormat.parse(time);
            System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));

            return displayFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String addOneDayToDate(String inputDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            Date date = dateFormat.parse(inputDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US);
            return format1.format(calendar.getTime());

        } catch (Exception e) {
            Log.e("Add Day Exce = ", e + "");
        }
        return "";
    }

    public static String addOneDayToDateCalendar(String inputDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date = dateFormat.parse(inputDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            return format1.format(calendar.getTime());

        } catch (Exception e) {
            Log.e("Add Day Exce = ", e + "");
        }
        return "";
    }


}
