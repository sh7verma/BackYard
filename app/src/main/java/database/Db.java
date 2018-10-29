package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.app.backyard.ActiveBookingActivity;
import com.stripe.model.Discount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import interfaces.UpdateDataListener;
import model.AmenitiesModel;
import model.AttachmentModel;
import model.BookTulModel;
import model.BookingDeleteModel;
import model.CardLocalModel;
import model.ChatDialogModel;
import model.ChatMessageModel;
import model.CreateStripeAccountModel;
import model.NearByTulListingModel;
import model.NotificationModel;
import model.ProfileModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by applify on 8/4/2017.
 */

public class Db extends SQLiteOpenHelper {


    public static final String DATABASE = "backyard_local";
    static final int dbversion = 2;
    /// List Table
    static final String LISTEDTUL_TABLE = "lisetdtul_table";
    static final String ID = "id";
    static final String TITLE = "title";
    static final String USER_ID = "user_id";
    static final String PRICE = "price";
    static final String RATING = "rating";
    /// ShortList Table
    static final String SHORTLISTEDTUL_TABLE = "shortlisetdtul_table";
    static final String OWNER = "owner";
    static final String OWNER_PIC = "owner_pic";
    static final String PRIMARY_ID = "primary_id";
    /// attachment Table
    static final String ATTACHMENT_TABLE = "attachment_table";
    static final String TOOl_ID = "tool_id";
    static final String ATTACHMENT = "attachment";
    static final String THUMBNAIL = "thumbnail";
    static final String TYPE = "type";
    /// attachment Table
    static final String SHORTLIST_ATTACHMENT_TABLE = "shortlist_attachment_table";
    /// Card Details
    static final String CARD_DETAILS_TABLE = "card_details_table";
    static final String CARD_NAME = "card_name";
    static final String CARD_NUMBER = "card_number";
    static final String CARD_ID = "card_id";
    static final String EXPIRY_MONTH = "expiry_month";
    static final String EXPIRY_YEAR = "expiry_year";
    //Account Details
    static final String ACCOUNT_DETAIL_TABLE = "account_detail_table";
    static final String ACCOUNT_NAME = "account_name";
    static final String ACCOUNT_COUNTRY_CODE = "account_country_code";
    static final String ACCOUNT_CURRENCY = "account_currency";
    static final String ACCOUNT_NUMBER = "account_number";
    static final String ACCOUNT_SORT_CODE = "account_sort_code";
    static final String ACCOUNT_ADDRESS = "account_address";
    static final String ACCOUNT_CITY = "account_city";
    static final String ACCOUNT_STATE = "account_state";
    static final String ACCOUNT_POSTAL_CODE = "account_postal_code";
    static final String ACCOUNT_DOB = "account_dob";
    static final String ACCOUNT_ID = "account_id";
    static final String ACCOUNT_EARNING = "account_earning";
    static final String ACCOUNT_IS_PRIMARY = "account_is_primary";
    static final String FIRST_NAME = "first_name";
    static final String SWIFT = "swift";
    static final String ACCOUNT_TYPE = "account_type";
    static final String LAST_NAME = "last_name";
    /// Booking Table
    static final String BOOKING_TABLE = "booking_table";
    static final String BOOKING_ID = "booking_id";
    static final String LENDER_STATUS = "lender_status";
    static final String BORROWER_STATUS = "borrower_status";
    static final String TOTAL_AMOUNT = "total_amount";
    static final String DELIVERY_DATE = "delivery_date";
    static final String RETURN_DATE = "return_date";
    static final String HANDOVER_AT = "handover_at";
    static final String LANDER_RECEIVE_AT = "lender_receive_at";
    static final String BORROWER_RECEIVED_AT = "borrower_received_at";
    static final String BORROWER_RETURNED_AT = "borrower_returned_at";
    static final String USER_TYPE = "user_type";
    static final String QUANTITY = "quantity";
    static final String DELIVERY_COST = "delivery_cost";
    static final String BOOKED_AT = "booked_at";
    /// booking attachment Table
    static final String MYBOOKINGS_ATTACHMENT_TABLE = "mybookings_attachment_table";
    /// amenities Table
    static final String AMENITIES_TABLE = "amenities_table";
    static final String CATEGORY_ID = "category_id";
    static final String AMENITY_NAME = "amenity_name";
    /// Lendted Tul Table
    static final String LENDTED_TUL_TABLE = "lented_tul_table";
    static final String BORROWER = "borrower";
    static final String BORROWER_PIC = "borrower_pic";
    /// lented attachment Table
    static final String LENTED_ATTACHMENT_TABLE = "lented_attachment_table";
    Utils utils;

    /// History Lent Table
    static final String HISTORY_LENT_ATTACHMENT_TABLE = "history_lent_attachment_table";

    //History Lent tuls
    static final String HISTORY_LENT_TABLE = "history_lent_table";
    static final String TOOL_ID = "tool_id";
    static final String SECURITY_CHARGES = "security_charges";
    static final String FEE = "fee";
    static final String QUAMTITY = "quantity";
    static final String CURRENCY = "currency";
    static final String SELECTION_DATE = "selected_date";
    static final String REFUND_STATUS = "refund_status";
    static final String CANCELLED_AT = "cancelled_at";
    static final String CANCEL_PERCENTAGE = "cancel_percentage";
    static final String TRANSACTION_FEE = "transaction_fee";
    static final String BORROWER_ID = "borrower_id";

    //History Rented tuls
    static final String HISTORY_RENTED_TABLE = "history_rented_table";
    static final String HISTORY_RENTED_ATTACHMENT_TABLE = "history_rented_attachment_table";

    //Notification Table
    static final String NOTIFICATION_TABLE = "notification_table";
    static final String CREATED_AT = "created_at";
    static final String MESSAGE = "message";
    static final String NOTIFICATION_TYPE = "notification_type";
    static final String READ_STATUS = "read_status";
    static final String USERNAME = "username";
    static final String USER_PIC = "user_pic";

    static final String CONVERSATION_TABLE = "conversation_table";
    static final String CHAT_DIALOG_ID = "chat_dialog_id";
    static final String COMPATIBILITY = "compatibilty";
    static final String DELETE_DIALOG_TIME = "delete_dialog_time";
    static final String LAST_MESSAGE = "last_message";
    static final String LAST_MESSAGE_ID = "last_message_id";
    static final String LAST_MESSAGE_SENDER_ID = "last_message_sender_id";
    static final String LAST_MESSAGE_TIME = "last_message_time";
    static final String PARTICPANT_ID = "participant_id";
    static final String PLATFORM_STATUS = "platform_status";
    static final String PROFILE_PIC = "profile_pic";
    static final String PUSH_TOKEN = "push_token";
    static final String NAME = "name";
    static final String ACCESS_TOKEN = "access_token";
    static final String UNREAD_COUNT = "unread_count";
    static final String DIALOG_TIME = "dialog_time";
    static final String MUTE_STATUS = "mute_status";
    static final String OWN_MUTE_STATUS = "own_mute_status";
    static final String SECURITY_FEE = "security_fee";
    static final String EXTRA_FEE = "extra_fee";

    static final String OTHERBLOCKSTATUS = "other_block_status";
    static final String MYBLOCKSTATUS = "my_block_status";

    /// Dialog Table
    static final String DIALOG_TABLE = "dialog_table";
    static final String DIALOG_ID = "dialog_id";

    /// Message Table
    static final String MESSAGE_TABLE = "message_table";
    static final String MESSAGE_ID = "message_id";
    static final String MESSAGE_TIME = "message_time";
    static final String SENDER_ID = "sender_id";
    static final String MESSAGE_STATUS = "message_status";

    /////addes in listedtul_tabel
    static final String PRODUCT_TYPE = "product_type";
    static final String TRANSACTION_PERCENT = "transaction_percent";
    static final String DISCOUNT = "discount";

    static final String BASE_PRICE = "base_price";
    private UpdateDataListener mUpdateDataListener;

//    delete conversation
    static final String OWN_DELETE_STATUS = "own_delete_status";
    static final String OTHER_DELETE_STATUS = "other_delete_status";

    public Db(Context context) {
        super(context, DATABASE, null, dbversion);
        utils = new Utils(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    void createTables(SQLiteDatabase db){
        String listedtul_qry = "create table if not exists " + LISTEDTUL_TABLE
                + " (" + ID + " TEXT " + " ," + TITLE + " TEXT" + " ," + PRODUCT_TYPE + " TEXT" + " ," + TRANSACTION_PERCENT + " TEXT" + " ,"
                + USER_ID + " TEXT" + " ," + PRICE + " TEXT" + " ," + RATING + " TEXT )";
        db.execSQL(listedtul_qry);

        String attachment_qry = "create table if not exists " + ATTACHMENT_TABLE
                + " (" + ID + " TEXT ," + TOOl_ID + " TEXT ," + ATTACHMENT + " TEXT ," + THUMBNAIL + " TEXT ," + TYPE + " TEXT )";
        db.execSQL(attachment_qry);

        String amenities_qry = "create table if not exists " + AMENITIES_TABLE
                + " (" + ID + " TEXT ," + CATEGORY_ID + " TEXT ," + AMENITY_NAME + " TEXT )";
        db.execSQL(amenities_qry);

        String shortlistattachment_qry = "create table if not exists " + SHORTLIST_ATTACHMENT_TABLE
                + " (" + ID + " TEXT ," + TOOl_ID + " TEXT ," + ATTACHMENT + " TEXT ," + THUMBNAIL + " TEXT ," + TYPE + " TEXT )";
        db.execSQL(shortlistattachment_qry);

        String shortList_qry = "create table if not exists " + SHORTLISTEDTUL_TABLE
                + " (" + ID + " TEXT ," + OWNER + " TEXT ," + OWNER_PIC + " TEXT ," + TITLE + " TEXT ," + PRODUCT_TYPE + " TEXT" + " ," + USER_ID + " TEXT ,"
                + PRICE + " TEXT ," + RATING + " TEXT ," + PRIMARY_ID + " INTEGER PRIMARY KEY )";

        db.execSQL(shortList_qry);

        String booking_query = "create table if not exists " + BOOKING_TABLE
                + " (" + BOOKING_ID + " INTEGER ," + TOOl_ID + " TEXT ," + USER_ID + " TEXT ," + OWNER + " TEXT ,"
                + OWNER_PIC + " TEXT ," + LENDER_STATUS + " TEXT ," + BORROWER_STATUS + " TEXT ," + TITLE + " TEXT ,"
                + TOTAL_AMOUNT + " TEXT ," + DELIVERY_DATE + " TEXT ," + RETURN_DATE + " TEXT ," + HANDOVER_AT + " TEXT ,"
                + LANDER_RECEIVE_AT + " TEXT ," + BORROWER_RECEIVED_AT + " TEXT ," + BORROWER_RETURNED_AT + " TEXT ,"
                + PRICE + " TEXT ," + QUANTITY + " TEXT ," + DELIVERY_COST + " TEXT ," + PRODUCT_TYPE + " TEXT ," + BASE_PRICE + " TEXT ," + BOOKED_AT + " TEXT )";
        db.execSQL(booking_query);


        String mybookingattachment_qry = "create table if not exists " + MYBOOKINGS_ATTACHMENT_TABLE
                + " (" + ID + " TEXT ," + TOOl_ID + " TEXT ," + ATTACHMENT + " TEXT ," + THUMBNAIL + " TEXT ," + TYPE + " TEXT )";
        db.execSQL(mybookingattachment_qry);

        String card_qry = "create table if not exists " + CARD_DETAILS_TABLE
                + " (" + ID + " INTEGER PRIMARY KEY," + CARD_NAME + " TEXT ," + CARD_NUMBER + " TEXT ," + CARD_ID + " TEXT ," + EXPIRY_YEAR + " TEXT ," + EXPIRY_MONTH + " TEXT )";
        db.execSQL(card_qry);

        String account_gry = "create table if not exists " + ACCOUNT_DETAIL_TABLE
                + " (" + ID + " INTEGER PRIMARY KEY," + ACCOUNT_NAME + " TEXT ,"
                + ACCOUNT_ID + " INTEGER ," + ACCOUNT_COUNTRY_CODE + " TEXT ,"
                + ACCOUNT_CURRENCY + " TEXT ," + ACCOUNT_NUMBER + " TEXT ,"
                + ACCOUNT_SORT_CODE + " TEXT ," + ACCOUNT_ADDRESS + " TEXT ,"
                + ACCOUNT_CITY + " TEXT ," + ACCOUNT_STATE + " TEXT ,"
                + ACCOUNT_EARNING + " TEXT ,"
                + ACCOUNT_POSTAL_CODE + " TEXT ,"
                + ACCOUNT_DOB + " TEXT ,"
                + ACCOUNT_IS_PRIMARY + " TEXT ,"
                + FIRST_NAME + " TEXT ,"
                + SWIFT + " TEXT ,"
                + ACCOUNT_TYPE + " TEXT ,"
                + LAST_NAME + " TEXT )";
        db.execSQL(account_gry);

        String tul_lented_query = "create table if not exists " + LENDTED_TUL_TABLE
                + " (" + BOOKING_ID + " INTEGER ," + TOOl_ID + " TEXT ," + USER_ID + " TEXT ," + OWNER + " TEXT ,"
                + OWNER_PIC + " TEXT ," + LENDER_STATUS + " TEXT ," + BORROWER_STATUS + " TEXT ," + TITLE + " TEXT ,"
                + TOTAL_AMOUNT + " TEXT ," + DELIVERY_DATE + " TEXT ," + RETURN_DATE + " TEXT ," + HANDOVER_AT + " TEXT ,"
                + LANDER_RECEIVE_AT + " TEXT ," + BORROWER_RECEIVED_AT + " TEXT ," + BORROWER_RETURNED_AT + " TEXT ,"
                + PRICE + " TEXT ," + QUANTITY + " TEXT ," + DELIVERY_COST + " TEXT ," + BOOKED_AT + " TEXT ," + BORROWER + " TEXT ,"
                + PRODUCT_TYPE + " TEXT ," + TRANSACTION_PERCENT + " TEXT ," + BASE_PRICE + " TEXT ,"
                + BORROWER_PIC + " TEXT ," + TRANSACTION_FEE + " TEXT ," + SECURITY_FEE + " TEXT ," + EXTRA_FEE + " TEXT ," + BORROWER_ID + " TEXT )";
        db.execSQL(tul_lented_query);


        String lented_attachement_qry = "create table if not exists " + LENTED_ATTACHMENT_TABLE
                + " (" + ID + " TEXT ," + TOOl_ID + " TEXT ," + ATTACHMENT + " TEXT ," + THUMBNAIL + " TEXT ," + TYPE + " TEXT )";
        db.execSQL(lented_attachement_qry);

        String history_lent_qry = "create table if not exists " + HISTORY_LENT_TABLE
                + " (" + ID + " INTEGER PRIMARY KEY," + TOOL_ID + " TEXT ,"
                + USER_ID + " TEXT ," + OWNER + " TEXT ,"
                + FIRST_NAME + " TEXT ," + LAST_NAME + " TEXT ,"
                + OWNER_PIC + " TEXT ," + BOOKING_ID + " TEXT ,"
                + LENDER_STATUS + " TEXT ," + BORROWER_STATUS + " TEXT ,"
                + TITLE + " TEXT ,"
                + SECURITY_CHARGES + " TEXT ,"
                + FEE + " TEXT ,"
                + TOTAL_AMOUNT + " TEXT ,"
                + DELIVERY_COST + " TEXT ,"
                + PRICE + " TEXT ,"
                + QUAMTITY + " TEXT ,"
                + CURRENCY + " TEXT ,"
                + DELIVERY_DATE + " TEXT ,"
                + RETURN_DATE + " TEXT ,"
                + SELECTION_DATE + " TEXT ,"
                + REFUND_STATUS + " TEXT ,"
                + BORROWER + " TEXT ,"
                + BORROWER_PIC + " TEXT ,"
                + CANCELLED_AT + " TEXT ,"
                + PRODUCT_TYPE + " TEXT ,"
                + DISCOUNT + " TEXT ,"
                + TRANSACTION_PERCENT + " TEXT ,"
                + CANCEL_PERCENTAGE + " TEXT )";
        db.execSQL(history_lent_qry);

        String history_lent_attachment_qry = "create table if not exists " + HISTORY_LENT_ATTACHMENT_TABLE
                + " (" + ID + " TEXT ," + TOOl_ID + " TEXT ," + ATTACHMENT + " TEXT ," + THUMBNAIL + " TEXT ," + TYPE + " TEXT )";

        db.execSQL(history_lent_attachment_qry);


        String history_rented_qry = "create table if not exists " + HISTORY_RENTED_TABLE
                + " (" + ID + " INTEGER PRIMARY KEY," + TOOL_ID + " TEXT ,"
                + USER_ID + " TEXT ," + OWNER + " TEXT ,"
                + FIRST_NAME + " TEXT ," + LAST_NAME + " TEXT ,"
                + OWNER_PIC + " TEXT ," + BOOKING_ID + " TEXT ,"
                + LENDER_STATUS + " TEXT ," + BORROWER_STATUS + " TEXT ,"
                + TITLE + " TEXT ,"
                + SECURITY_CHARGES + " TEXT ,"
                + FEE + " TEXT ,"
                + TOTAL_AMOUNT + " TEXT ,"
                + DELIVERY_COST + " TEXT ,"
                + PRICE + " TEXT ,"
                + QUAMTITY + " TEXT ,"
                + CURRENCY + " TEXT ,"
                + DELIVERY_DATE + " TEXT ,"
                + RETURN_DATE + " TEXT ,"
                + SELECTION_DATE + " TEXT ,"
                + REFUND_STATUS + " TEXT ,"
                + BORROWER + " TEXT ,"
                + BORROWER_PIC + " TEXT ,"
                + CANCELLED_AT + " TEXT ,"
                + PRODUCT_TYPE + " TEXT ,"
                + DISCOUNT + " TEXT ,"
                + TRANSACTION_PERCENT + " TEXT ,"
                + CANCEL_PERCENTAGE + " TEXT )";
        db.execSQL(history_rented_qry);

        String history_rented_attachment_qry = "create table if not exists " + HISTORY_RENTED_ATTACHMENT_TABLE
                + " (" + ID + " TEXT ," + TOOl_ID + " TEXT ," + ATTACHMENT + " TEXT ," + THUMBNAIL + " TEXT ," + TYPE + " TEXT )";

        db.execSQL(history_rented_attachment_qry);

        String notification_qry = "create table if not exists " + NOTIFICATION_TABLE
                + " (" + ID + " TEXT ,"
                + NOTIFICATION_TYPE + " TEXT ,"
                + BOOKING_ID + " TEXT ,"
                + TOOL_ID + " TEXT ,"
                + MESSAGE + " TEXT ,"
                + CREATED_AT + " TEXT ,"
                + READ_STATUS + " TEXT ,"
                + USERNAME + " TEXT ,"
                + FIRST_NAME + " TEXT ,"
                + LAST_NAME + " TEXT ,"
                + USER_PIC + " TEXT )";
        db.execSQL(notification_qry);

//        String conversation_qry = "create table if not exists " + CONVERSATION_TABLE
//                + " (" + CHAT_DIALOG_ID + " TEXT " + " ," + COMPATIBILITY + " TEXT" + " ,"
//                + DELETE_DIALOG_TIME + " TEXT" + " ," + LAST_MESSAGE + " TEXT"
//                + " ," + LAST_MESSAGE_ID + " TEXT" + " ," + LAST_MESSAGE_SENDER_ID
//                + " TEXT" + " ," + LAST_MESSAGE_TIME + " TEXT" + " ," + PLATFORM_STATUS + " TEXT ,"
//                + PROFILE_PIC + " TEXT ," + PUSH_TOKEN + " TEXT ," +
//                ACCESS_TOKEN + " TEXT ," + UNREAD_COUNT + " TEXT ," + NAME + " TEXT ," + PARTICPANT_ID + " TEXT ,"
//                + DIALOG_TIME + " TEXT ," + MUTE_STATUS + " TEXT ," + OWN_MUTE_STATUS + " TEXT ," + OTHERBLOCKSTATUS + " TEXT ," + MYBLOCKSTATUS + " TEXT )";
//        db.execSQL(conversation_qry);

        String conversation_qry = "create table if not exists " + CONVERSATION_TABLE
                + " (" + CHAT_DIALOG_ID + " TEXT " + " ," + COMPATIBILITY + " TEXT" + " ,"
                + DELETE_DIALOG_TIME + " TEXT" + " ," + LAST_MESSAGE + " TEXT"
                + " ," + LAST_MESSAGE_ID + " TEXT" + " ," + LAST_MESSAGE_SENDER_ID
                + " TEXT" + " ," + LAST_MESSAGE_TIME + " TEXT" + " ," + PLATFORM_STATUS + " TEXT ,"
                + PROFILE_PIC + " TEXT ," + PUSH_TOKEN + " TEXT ," +
                ACCESS_TOKEN + " TEXT ," + UNREAD_COUNT + " TEXT ," + NAME + " TEXT ," + PARTICPANT_ID + " TEXT ,"
                + DIALOG_TIME + " TEXT ," + MUTE_STATUS + " TEXT ," + OWN_MUTE_STATUS + " TEXT ,"
                + OTHERBLOCKSTATUS + " TEXT ," + MYBLOCKSTATUS + " TEXT ," + OWN_DELETE_STATUS + " TEXT ,"
                + OTHER_DELETE_STATUS + " TEXT )";
        db.execSQL(conversation_qry);

        String dialog_qry = "create table if not exists " + DIALOG_TABLE
                + " (" + USER_ID + " TEXT " + " ," + DIALOG_ID + " TEXT )";
        db.execSQL(dialog_qry);

        String msg_qry = "create table if not exists " + MESSAGE_TABLE
                + " (" + CHAT_DIALOG_ID + " TEXT ," + MESSAGE + " TEXT ," + MESSAGE_STATUS + " INTEGER ,"
                + MESSAGE_TIME + " TEXT ," + SENDER_ID + " TEXT ," + MESSAGE_ID + " TEXT )";
        db.execSQL(msg_qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            dropAllTables(db);
            createTables(db);
    }

    private void dropAllTables(SQLiteDatabase db) {
        try {
            String dropConversation = "DROP TABLE IF EXISTS " + LISTEDTUL_TABLE;
            db.execSQL(dropConversation);

            String dropMessage = "DROP TABLE IF EXISTS  " + ATTACHMENT_TABLE;
            db.execSQL(dropMessage);

            String shortListMessage = "DROP TABLE IF EXISTS  " + SHORTLIST_ATTACHMENT_TABLE;
            db.execSQL(shortListMessage);

            String shortListTulMessage = "DROP TABLE IF EXISTS  " + SHORTLISTEDTUL_TABLE;
            db.execSQL(shortListTulMessage);

            String cardMessage = "DROP TABLE IF EXISTS  " + CARD_DETAILS_TABLE;
            db.execSQL(cardMessage);

            String accountMessage = "DROP TABLE IF EXISTS  " + ACCOUNT_DETAIL_TABLE;
            db.execSQL(accountMessage);

            String myBookings = "DROP TABLE IF EXISTS  " + BOOKING_TABLE;
            db.execSQL(myBookings);

            String myBookingsAttachment = "DROP TABLE IF EXISTS  " + MYBOOKINGS_ATTACHMENT_TABLE;
            db.execSQL(myBookingsAttachment);

            String lentedTulTable = "DROP TABLE IF EXISTS  " + LENDTED_TUL_TABLE;
            db.execSQL(lentedTulTable);

            String lentedTulAttachment = "DROP TABLE IF EXISTS  " + LENTED_ATTACHMENT_TABLE;
            db.execSQL(lentedTulAttachment);

            String historyRent = "DROP TABLE IF EXISTS  " + HISTORY_RENTED_TABLE;
            db.execSQL(historyRent);

            String historyRentAttachment = "DROP TABLE IF EXISTS  " + HISTORY_RENTED_ATTACHMENT_TABLE;
            db.execSQL(historyRentAttachment);

            String historyLent = "DROP TABLE IF EXISTS  " + HISTORY_LENT_TABLE;
            db.execSQL(historyLent);

            String historyLentAttachment = "DROP TABLE IF EXISTS  " + HISTORY_LENT_ATTACHMENT_TABLE;
            db.execSQL(historyLentAttachment);

            String deleteNotifications = "DROP TABLE IF EXISTS  " + NOTIFICATION_TABLE;
            db.execSQL(deleteNotifications);

            String dropConversationChatting = "DROP TABLE IF EXISTS " + CONVERSATION_TABLE;
            db.execSQL(dropConversationChatting);

            String dropMessageChatting = "DROP TABLE IF EXISTS " + MESSAGE_TABLE;
            db.execSQL(dropMessageChatting);

            String dropDialogChatting = "DROP TABLE IF EXISTS " + DIALOG_TABLE;
            db.execSQL(dropDialogChatting);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void addListedTul(ProfileModel.ResponseBean.ProductBean listedTulModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TITLE, listedTulModel.getTitle());
            values.put(PRODUCT_TYPE, listedTulModel.getProduct_type());
            values.put(TRANSACTION_PERCENT, listedTulModel.getTransaction_percentage());
            values.put(USER_ID, listedTulModel.getUser_id());
            values.put(PRICE, listedTulModel.getPrice());
            values.put(RATING, Constants.EMPTY);

            data = getReadableDatabase().rawQuery("Select * from " + LISTEDTUL_TABLE + " where "
                    + ID + " = '" + listedTulModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(LISTEDTUL_TABLE, values, ID + " = '" + listedTulModel.getId() + "'", null);
            } else {
                values.put(ID, listedTulModel.getId());
                db.insertOrThrow(LISTEDTUL_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addShortListedTul(NearByTulListingModel.ResponseBean shortListedTulModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(OWNER, shortListedTulModel.getOwner());
            values.put(OWNER_PIC, shortListedTulModel.getOwner_pic());
            values.put(TITLE, shortListedTulModel.getTitle());
            values.put(PRODUCT_TYPE, shortListedTulModel.getProduct_type());
            values.put(USER_ID, shortListedTulModel.getUser_id());
            values.put(PRICE, shortListedTulModel.getPrice());
            values.put(RATING, shortListedTulModel.getRating());

            data = getReadableDatabase().rawQuery("Select * from " + SHORTLISTEDTUL_TABLE + " where "
                    + ID + " = '" + shortListedTulModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(SHORTLISTEDTUL_TABLE, values, ID + " = '" + shortListedTulModel.getId() + "'", null);
            } else {
                values.put(ID, shortListedTulModel.getId());
                db.insertOrThrow(SHORTLISTEDTUL_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addMyBooking(BookTulModel.ResponseBean bookTulModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, bookTulModel.getTool_id());
            values.put(USER_ID, bookTulModel.getUser_id());
            values.put(OWNER, bookTulModel.getOwner());
            values.put(OWNER_PIC, bookTulModel.getOwner_pic());
            values.put(LENDER_STATUS, bookTulModel.getLender_status());
            values.put(BORROWER_STATUS, bookTulModel.getBorrower_status());
            values.put(TITLE, bookTulModel.getTitle());
            values.put(TOTAL_AMOUNT, bookTulModel.getTotal_amount());
            values.put(DELIVERY_DATE, bookTulModel.getDelivery_date());
            values.put(RETURN_DATE, bookTulModel.getReturn_date());
            values.put(HANDOVER_AT, bookTulModel.getHandover_at());
            values.put(LANDER_RECEIVE_AT, bookTulModel.getLander_received_at());
            values.put(BORROWER_RECEIVED_AT, bookTulModel.getBorrower_received_at());
            values.put(BORROWER_RETURNED_AT, bookTulModel.getBorrower_received_at());
            values.put(PRICE, bookTulModel.getPrice());
            values.put(QUANTITY, bookTulModel.getQuantity());
            values.put(DELIVERY_COST, bookTulModel.getDelivery_cost());
            values.put(PRODUCT_TYPE, bookTulModel.getProduct_type());
            values.put(BASE_PRICE, bookTulModel.getBase_price());
            values.put(BOOKED_AT, bookTulModel.getBooked_at());

            data = getReadableDatabase().rawQuery("Select * from " + BOOKING_TABLE + " where "
                    + BOOKING_ID + " = '" + bookTulModel.getBooking_id() + "'", null);
            if (data.getCount() > 0) {
                db.update(BOOKING_TABLE, values, BOOKING_ID + " = '" + bookTulModel.getBooking_id() + "'", null);
            } else {
                values.put(BOOKING_ID, bookTulModel.getBooking_id());
                db.insertOrThrow(BOOKING_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }


    public void addMyBooking(BookTulModel.ResponseBean bookTulModel, ActiveBookingActivity activeBookingActivity) {

        if (mUpdateDataListener == null)
            mUpdateDataListener = activeBookingActivity;

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, bookTulModel.getTool_id());
            values.put(USER_ID, bookTulModel.getUser_id());
            values.put(OWNER, bookTulModel.getOwner());
            values.put(OWNER_PIC, bookTulModel.getOwner_pic());
            values.put(LENDER_STATUS, bookTulModel.getLender_status());
            values.put(BORROWER_STATUS, bookTulModel.getBorrower_status());
            values.put(TITLE, bookTulModel.getTitle());
            values.put(TOTAL_AMOUNT, bookTulModel.getTotal_amount());
            values.put(DELIVERY_DATE, bookTulModel.getDelivery_date());
            values.put(RETURN_DATE, bookTulModel.getReturn_date());
            values.put(HANDOVER_AT, bookTulModel.getHandover_at());
            values.put(LANDER_RECEIVE_AT, bookTulModel.getLander_received_at());
            values.put(BORROWER_RECEIVED_AT, bookTulModel.getBorrower_received_at());
            values.put(BORROWER_RETURNED_AT, bookTulModel.getBorrower_received_at());
            values.put(PRICE, bookTulModel.getPrice());
            values.put(QUANTITY, bookTulModel.getQuantity());
            values.put(DELIVERY_COST, bookTulModel.getDelivery_cost());
            values.put(PRODUCT_TYPE, bookTulModel.getProduct_type());
            values.put(BASE_PRICE, bookTulModel.getBase_price());
            values.put(BOOKED_AT, bookTulModel.getBooked_at());

            data = getReadableDatabase().rawQuery("Select * from " + BOOKING_TABLE + " where "
                    + BOOKING_ID + " = '" + bookTulModel.getBooking_id() + "'", null);
            if (data.getCount() > 0) {
                db.update(BOOKING_TABLE, values, BOOKING_ID + " = '" + bookTulModel.getBooking_id() + "'", null);
            } else {
                values.put(BOOKING_ID, bookTulModel.getBooking_id());
                db.insertOrThrow(BOOKING_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }


    public void addLentedTuls(BookTulModel.ResponseBean bookTulModel, ActiveBookingActivity activeBookingActivity) {

        if (mUpdateDataListener == null)
            mUpdateDataListener = activeBookingActivity;

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, bookTulModel.getTool_id());
            values.put(USER_ID, bookTulModel.getUser_id());
            values.put(OWNER, bookTulModel.getOwner());
            values.put(OWNER_PIC, bookTulModel.getOwner_pic());
            values.put(LENDER_STATUS, bookTulModel.getLender_status());
            values.put(BORROWER_STATUS, bookTulModel.getBorrower_status());
            values.put(TITLE, bookTulModel.getTitle());
            values.put(TOTAL_AMOUNT, bookTulModel.getTotal_amount());
            values.put(DELIVERY_DATE, bookTulModel.getDelivery_date());
            values.put(RETURN_DATE, bookTulModel.getReturn_date());
            values.put(HANDOVER_AT, bookTulModel.getHandover_at());
            values.put(LANDER_RECEIVE_AT, bookTulModel.getLander_received_at());
            values.put(BORROWER_RECEIVED_AT, bookTulModel.getBorrower_received_at());
            values.put(BORROWER_RETURNED_AT, bookTulModel.getBorrower_received_at());
            values.put(PRICE, bookTulModel.getPrice());
            values.put(QUANTITY, bookTulModel.getQuantity());
            values.put(DELIVERY_COST, bookTulModel.getDelivery_cost());
            values.put(BOOKED_AT, bookTulModel.getBooked_at());
            values.put(BORROWER, bookTulModel.getBorrower());
            values.put(PRODUCT_TYPE, bookTulModel.getProduct_type());
            values.put(TRANSACTION_PERCENT, bookTulModel.getTransaction_percentage());
            values.put(BASE_PRICE, bookTulModel.getBase_price());
            values.put(BORROWER_PIC, bookTulModel.getBorrower_pic());
            values.put(TRANSACTION_FEE, bookTulModel.getTransaction_fee());

            BookTulModel.ResponseBean.AdditionalChargesBean additionalChargesBean = bookTulModel.getAdditional_charges();
            values.put(SECURITY_FEE, additionalChargesBean.getSecurity_charges());
            if (!TextUtils.isEmpty(additionalChargesBean.getFee()))
                values.put(EXTRA_FEE, additionalChargesBean.getFee());
            else
                values.put(EXTRA_FEE, "0.00");

            values.put(BASE_PRICE, bookTulModel.getBase_price());
            values.put(BORROWER_ID, bookTulModel.getBorrower_id());

            data = getReadableDatabase().rawQuery("Select * from " + LENDTED_TUL_TABLE + " where "
                    + BOOKING_ID + " = '" + bookTulModel.getBooking_id() + "'", null);
            if (data.getCount() > 0) {
                db.update(LENDTED_TUL_TABLE, values, BOOKING_ID + " = '" + bookTulModel.getBooking_id() + "'", null);
            } else {
                values.put(BOOKING_ID, bookTulModel.getBooking_id());
                db.insertOrThrow(LENDTED_TUL_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addNotification(NotificationModel.ResponseBean responseBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(BOOKING_ID, responseBean.getBooking_id());
            values.put(CREATED_AT, responseBean.getCreated_at());
            values.put(FIRST_NAME, responseBean.getFirst_name());
            values.put(LAST_NAME, responseBean.getLast_name());
            values.put(MESSAGE, responseBean.getMessage());
            values.put(NOTIFICATION_TYPE, responseBean.getNotification_type());
            values.put(READ_STATUS, responseBean.getRead_status());
            values.put(TOOl_ID, responseBean.getTool_id());
            values.put(USERNAME, responseBean.getUsername());
            values.put(USER_PIC, responseBean.getUser_pic());

            data = getReadableDatabase().rawQuery("Select * from " + NOTIFICATION_TABLE + " where "
                    + ID + " = '" + responseBean.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(NOTIFICATION_TABLE, values, ID + " = '" + responseBean.getId() + "'", null);
            } else {
                values.put(ID, responseBean.getId());
                db.insertOrThrow(NOTIFICATION_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }


    public void addAttachment(AttachmentModel attachmentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, attachmentModel.getProduct_id());
            values.put(ATTACHMENT, attachmentModel.getAttachment());
            values.put(THUMBNAIL, attachmentModel.getThumbnail());
            values.put(TYPE, attachmentModel.getType());
            data = getReadableDatabase().rawQuery("Select * from " + ATTACHMENT_TABLE + " where "
                    + ID + " = '" + attachmentModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(ATTACHMENT_TABLE, values, ID + " = '" + attachmentModel.getId() + "'", null);
            } else {
                values.put(ID, attachmentModel.getId());
                db.insertOrThrow(ATTACHMENT_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addShortListAttachment(AttachmentModel attachmentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, attachmentModel.getProduct_id());
            values.put(ATTACHMENT, attachmentModel.getAttachment());
            values.put(THUMBNAIL, attachmentModel.getThumbnail());
            values.put(TYPE, attachmentModel.getType());
            data = getReadableDatabase().rawQuery("Select * from " + SHORTLIST_ATTACHMENT_TABLE + " where "
                    + ID + " = '" + attachmentModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(SHORTLIST_ATTACHMENT_TABLE, values, ID + " = '" + attachmentModel.getId() + "'", null);
            } else {
                values.put(ID, attachmentModel.getId());
                db.insertOrThrow(SHORTLIST_ATTACHMENT_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addMyBookingsAttachment(AttachmentModel attachmentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, attachmentModel.getProduct_id());
            values.put(ATTACHMENT, attachmentModel.getAttachment());
            values.put(THUMBNAIL, attachmentModel.getThumbnail());
            values.put(TYPE, attachmentModel.getType());
            data = getReadableDatabase().rawQuery("Select * from " + MYBOOKINGS_ATTACHMENT_TABLE + " where "
                    + ID + " = '" + attachmentModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(MYBOOKINGS_ATTACHMENT_TABLE, values, ID + " = '" + attachmentModel.getId() + "'", null);
            } else {
                values.put(ID, attachmentModel.getId());
                db.insertOrThrow(MYBOOKINGS_ATTACHMENT_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }


    public void addLentedTulAttachment(AttachmentModel attachmentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, attachmentModel.getProduct_id());
            values.put(ATTACHMENT, attachmentModel.getAttachment());
            values.put(THUMBNAIL, attachmentModel.getThumbnail());
            values.put(TYPE, attachmentModel.getType());
            data = getReadableDatabase().rawQuery("Select * from " + LENTED_ATTACHMENT_TABLE + " where "
                    + ID + " = '" + attachmentModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(LENTED_ATTACHMENT_TABLE, values, ID + " = '" + attachmentModel.getId() + "'", null);
            } else {
                values.put(ID, attachmentModel.getId());
                db.insertOrThrow(LENTED_ATTACHMENT_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addAmenities(AmenitiesModel.ResponseBean amenitiesItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(AMENITY_NAME, amenitiesItem.getAmenity_name());
            values.put(CATEGORY_ID, amenitiesItem.getCategory_id());
            data = getReadableDatabase().rawQuery("Select * from " + AMENITIES_TABLE + " where "
                    + ID + " = '" + amenitiesItem.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(AMENITIES_TABLE, values, ID + " = '" + amenitiesItem.getId() + "'", null);
            } else {
                values.put(ID, amenitiesItem.getId());
                db.insertOrThrow(AMENITIES_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    /// shubham
    public void addHistoryLendTul(BookTulModel.ResponseBean model) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOL_ID, model.getTool_id());
            values.put(USER_ID, model.getUser_id());
            values.put(OWNER, model.getOwner());
            values.put(FIRST_NAME, model.getFirst_name());
            values.put(LAST_NAME, model.getLast_name());
            values.put(OWNER_PIC, model.getOwner_pic());
            values.put(BOOKING_ID, model.getBooking_id());
            values.put(LENDER_STATUS, model.getLender_status());
            values.put(BORROWER_STATUS, model.getBorrower_status());
            values.put(TITLE, model.getTitle());
            if (model.getAdditional_charges() != null) {
                values.put(SECURITY_CHARGES, model.getAdditional_charges().getSecurity_charges());
                values.put(FEE, model.getAdditional_charges().getFee());
            } else {
                values.put(SECURITY_CHARGES, 0);
                values.put(FEE, 0);
            }
            values.put(TOTAL_AMOUNT, model.getTotal_amount());
            values.put(DELIVERY_COST, model.getDelivery_cost());
            values.put(PRICE, model.getPrice());
            values.put(QUAMTITY, model.getQuantity());
            values.put(CURRENCY, model.getCurrency());
            values.put(DELIVERY_DATE, model.getDelivery_date());
            values.put(RETURN_DATE, model.getReturn_date());
            values.put(SELECTION_DATE, model.getSelected_date());
            values.put(REFUND_STATUS, model.getRefund_status());
            values.put(BORROWER, model.getBorrower());
            values.put(BORROWER_PIC, model.getBorrower_pic());
            values.put(CANCELLED_AT, model.getCancelled_at());
            values.put(PRODUCT_TYPE, model.getProduct_type());
            values.put(DISCOUNT, model.getDiscount());
            values.put(TRANSACTION_PERCENT, model.getTransaction_percentage());
            values.put(CANCEL_PERCENTAGE, model.getCancel_percentage());

            data = getReadableDatabase().rawQuery("Select * from " + HISTORY_LENT_TABLE + " where "
                    + BOOKING_ID + " = '" + model.getBooking_id() + "'", null);
            if (data.getCount() > 0) {
                db.update(HISTORY_LENT_TABLE, values, BOOKING_ID + " = '" + model.getBooking_id() + "'", null);
            } else {
                values.put(BOOKING_ID, model.getBooking_id());
                db.insertOrThrow(HISTORY_LENT_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }


    public void addHistoryRentedTul(BookTulModel.ResponseBean model) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOL_ID, model.getTool_id());
            values.put(USER_ID, model.getUser_id());
            values.put(OWNER, model.getOwner());
            values.put(FIRST_NAME, model.getFirst_name());
            values.put(LAST_NAME, model.getLast_name());
            values.put(OWNER_PIC, model.getOwner_pic());
            values.put(BOOKING_ID, model.getBooking_id());
            values.put(LENDER_STATUS, model.getLender_status());
            values.put(BORROWER_STATUS, model.getBorrower_status());
            values.put(TITLE, model.getTitle());
            if (model.getAdditional_charges() != null) {
                values.put(SECURITY_CHARGES, model.getAdditional_charges().getSecurity_charges());
                values.put(FEE, model.getAdditional_charges().getFee());
            } else {
                values.put(SECURITY_CHARGES, 0);
                values.put(FEE, 0);
            }
            values.put(TOTAL_AMOUNT, model.getTotal_amount());
            values.put(DELIVERY_COST, model.getDelivery_cost());
            values.put(PRICE, model.getPrice());
            values.put(QUAMTITY, model.getQuantity());
            values.put(CURRENCY, model.getCurrency());
            values.put(DELIVERY_DATE, model.getDelivery_date());
            values.put(RETURN_DATE, model.getReturn_date());
            values.put(SELECTION_DATE, model.getSelected_date());
            values.put(REFUND_STATUS, model.getRefund_status());
            values.put(BORROWER, model.getBorrower());
            values.put(BORROWER_PIC, model.getBorrower_pic());
            values.put(CANCELLED_AT, model.getCancelled_at());
            values.put(PRODUCT_TYPE, model.getProduct_type());
            values.put(DISCOUNT, model.getDiscount());
            values.put(TRANSACTION_PERCENT, model.getTransaction_percentage());
            values.put(CANCEL_PERCENTAGE, model.getCancel_percentage());

            data = getReadableDatabase().rawQuery("Select * from " + HISTORY_RENTED_TABLE + " where "
                    + BOOKING_ID + " = '" + model.getBooking_id() + "'", null);
            if (data.getCount() > 0) {
                db.update(HISTORY_RENTED_TABLE, values, BOOKING_ID + " = '" + model.getBooking_id() + "'", null);
            } else {
                values.put(BOOKING_ID, model.getBooking_id());
                db.insertOrThrow(HISTORY_RENTED_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addHistoryRentedAttachment(AttachmentModel attachmentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, attachmentModel.getProduct_id());
            values.put(ATTACHMENT, attachmentModel.getAttachment());
            values.put(THUMBNAIL, attachmentModel.getThumbnail());
            values.put(TYPE, attachmentModel.getType());
            data = getReadableDatabase().rawQuery("Select * from " + HISTORY_RENTED_ATTACHMENT_TABLE + " where "
                    + ID + " = '" + attachmentModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(HISTORY_RENTED_ATTACHMENT_TABLE, values, ID + " = '" + attachmentModel.getId() + "'", null);
            } else {
                values.put(ID, attachmentModel.getId());
                db.insertOrThrow(HISTORY_RENTED_ATTACHMENT_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void addHistoryLentAttachment(AttachmentModel attachmentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(TOOl_ID, attachmentModel.getProduct_id());
            values.put(ATTACHMENT, attachmentModel.getAttachment());
            values.put(THUMBNAIL, attachmentModel.getThumbnail());
            values.put(TYPE, attachmentModel.getType());
            data = getReadableDatabase().rawQuery("Select * from " + HISTORY_LENT_ATTACHMENT_TABLE + " where "
                    + ID + " = '" + attachmentModel.getId() + "'", null);
            if (data.getCount() > 0) {
                db.update(HISTORY_LENT_ATTACHMENT_TABLE, values, ID + " = '" + attachmentModel.getId() + "'", null);
            } else {
                values.put(ID, attachmentModel.getId());
                db.insertOrThrow(HISTORY_LENT_ATTACHMENT_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }
    ////


    public ArrayList<ProfileModel.ResponseBean.ProductBean> getAllListedTuls() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<ProfileModel.ResponseBean.ProductBean> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + LISTEDTUL_TABLE + " ORDER BY " + ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                ProfileModel.ResponseBean.ProductBean toolModel = new ProfileModel.ResponseBean.ProductBean();
                toolModel.setId(Integer.parseInt(cur.getString(0)));
                toolModel.setTitle(cur.getString(1));
                toolModel.setProduct_type(cur.getString(2));
                toolModel.setTransaction_percentage(cur.getString(3));
                toolModel.setUser_id(Integer.parseInt(cur.getString(4)));
                toolModel.setPrice(cur.getString(5));
                toolModel.setAttachment(getAttachments(cur.getString(0)));
                mArrayListedTul.add(toolModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public ArrayList<String> getAllShortListedTulIds() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<String> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + SHORTLISTEDTUL_TABLE + " ORDER BY " + ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                mArrayListedTul.add(cur.getString(0));
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public ArrayList<NearByTulListingModel.ResponseBean> getAllShortListedTuls() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<NearByTulListingModel.ResponseBean> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + SHORTLISTEDTUL_TABLE + " ORDER BY " + PRIMARY_ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                NearByTulListingModel.ResponseBean toolModel = new NearByTulListingModel.ResponseBean();
                toolModel.setId(Integer.parseInt(cur.getString(0)));
                toolModel.setOwner(cur.getString(1));
                toolModel.setOwner_pic(cur.getString(2));
                toolModel.setTitle(cur.getString(3));
                toolModel.setProduct_type(cur.getString(4));
                toolModel.setUser_id(Integer.parseInt(cur.getString(5)));
                toolModel.setPrice(cur.getString(6));
                toolModel.setRating(Integer.parseInt(cur.getString(7)));
                toolModel.setAttachment(getShortListedAttachments(cur.getString(0)));
                mArrayListedTul.add(toolModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public ArrayList<BookingDeleteModel> getAllBookingIds() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<BookingDeleteModel> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + BOOKING_TABLE + " ORDER BY " + BOOKING_ID + " DESC";
            cur = db.rawQuery(qry, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                BookingDeleteModel deleteItem = new BookingDeleteModel();
                deleteItem.setBookingId(cur.getInt(0));
                deleteItem.setTulId(cur.getString(1));
                mArrayListedTul.add(deleteItem);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public ArrayList<BookTulModel.ResponseBean> getAllBookingsTuls() {

//        String booking_query = "create table if not exists " + BOOKING_TABLE
//                + " (" + BOOKING_ID + " INTEGER ," + TOOl_ID + " TEXT ," + USER_ID + " TEXT ," + OWNER + " TEXT ,"
//                + OWNER_PIC + " TEXT ," + LENDER_STATUS + " TEXT ," + BORROWER_STATUS + " TEXT ," + TITLE + " TEXT ,"
//                + TOTAL_AMOUNT + " TEXT ," + DELIVERY_DATE + " TEXT ," + RETURN_DATE + " TEXT ," + HANDOVER_AT + " TEXT ,"
//                + LANDER_RECEIVE_AT + " TEXT ," + BORROWER_RECEIVED_AT + " TEXT ," + BORROWER_RETURNED_AT + " TEXT ,"
//                + PRICE + " TEXT ," + QUANTITY + " TEXT ," + DELIVERY_COST + " TEXT ," + BOOKED_AT + " TEXT )";


        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<BookTulModel.ResponseBean> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + BOOKING_TABLE + " ORDER BY " + BOOKING_ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                BookTulModel.ResponseBean toolModel = new BookTulModel.ResponseBean();
                toolModel.setBooking_id(cur.getInt(0));
                toolModel.setTool_id(Integer.parseInt(cur.getString(1)));
                toolModel.setUser_id(Integer.parseInt(cur.getString(2)));
                toolModel.setOwner(cur.getString(3));
                toolModel.setOwner_pic(cur.getString(4));
                toolModel.setLender_status(Integer.parseInt(cur.getString(5)));
                toolModel.setBorrower_status(Integer.parseInt(cur.getString(6)));
                toolModel.setTitle(cur.getString(7));
                toolModel.setTotal_amount(cur.getString(8));
                toolModel.setDelivery_date(cur.getString(9));
                toolModel.setReturn_date(cur.getString(10));
                toolModel.setHandover_at(cur.getString(11));
                toolModel.setLander_received_at(cur.getString(12));
                toolModel.setBorrower_received_at(cur.getString(13));
                toolModel.setBorrower_returned_at(cur.getString(14));
                toolModel.setPrice(cur.getString(15));
                toolModel.setQuantity(Integer.parseInt(cur.getString(16)));
                toolModel.setDelivery_cost(Integer.parseInt(cur.getString(17)));
                toolModel.setProduct_type(cur.getString(18));
                toolModel.setBase_price(cur.getString(19));
                toolModel.setBooked_at(cur.getString(20));
                toolModel.setAttachment(getMyBookingsAttachments(cur.getString(1)));
                mArrayListedTul.add(toolModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public ArrayList<BookingDeleteModel> getAllLendedTulIds() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<BookingDeleteModel> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + LENDTED_TUL_TABLE + " ORDER BY " + BOOKING_ID + " DESC";
            cur = db.rawQuery(qry, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                BookingDeleteModel deleteItem = new BookingDeleteModel();
                deleteItem.setBookingId(cur.getInt(0));
                deleteItem.setTulId(cur.getString(1));
                mArrayListedTul.add(deleteItem);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public ArrayList<BookTulModel.ResponseBean> getAllLentedTuls() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<BookTulModel.ResponseBean> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + LENDTED_TUL_TABLE + " ORDER BY " + BOOKING_ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                BookTulModel.ResponseBean toolModel = new BookTulModel.ResponseBean();
                toolModel.setBooking_id(cur.getInt(0));
                toolModel.setTool_id(Integer.parseInt(cur.getString(1)));
                toolModel.setUser_id(Integer.parseInt(cur.getString(2)));
                toolModel.setOwner(cur.getString(3));
                toolModel.setOwner_pic(cur.getString(4));
                toolModel.setLender_status(Integer.parseInt(cur.getString(5)));
                toolModel.setBorrower_status(Integer.parseInt(cur.getString(6)));
                toolModel.setTitle(cur.getString(7));
                toolModel.setTotal_amount(cur.getString(8));
                toolModel.setDelivery_date(cur.getString(9));
                toolModel.setReturn_date(cur.getString(10));
                toolModel.setHandover_at(cur.getString(11));
                toolModel.setLander_received_at(cur.getString(12));
                toolModel.setBorrower_received_at(cur.getString(13));
                toolModel.setBorrower_returned_at(cur.getString(14));
                toolModel.setPrice(cur.getString(15));
                toolModel.setQuantity(Integer.parseInt(cur.getString(16)));
                toolModel.setDelivery_cost(Integer.parseInt(cur.getString(17)));
                toolModel.setBooked_at(cur.getString(18));
                toolModel.setBorrower(cur.getString(19));
                toolModel.setProduct_type(cur.getString(20));
                toolModel.setTransaction_percentage(cur.getString(21));
                toolModel.setBase_price(cur.getString(22));
                toolModel.setBorrower_pic(cur.getString(23));

                toolModel.setTransaction_fee(cur.getString(24));
                BookTulModel.ResponseBean.AdditionalChargesBean additionalChargesBean = new BookTulModel.ResponseBean.AdditionalChargesBean();
                additionalChargesBean.setSecurity_charges(cur.getString(25));
                additionalChargesBean.setFee(cur.getString(26));
                toolModel.setAdditional_charges(additionalChargesBean);

                toolModel.setBorrower_id(Integer.parseInt(cur.getString(27)));

                toolModel.setAttachment(getLentedAttachments(cur.getString(1)));
                mArrayListedTul.add(toolModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public ArrayList<BookTulModel.ResponseBean> getAllHistoryRentedTuls() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<BookTulModel.ResponseBean> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + HISTORY_RENTED_TABLE + " ORDER BY " + ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                BookTulModel.ResponseBean toolModel = new BookTulModel.ResponseBean();
                BookTulModel.ResponseBean.AdditionalChargesBean additionalChargesBean = new BookTulModel.ResponseBean.AdditionalChargesBean();
                toolModel.setTool_id(Integer.parseInt(cur.getString(1)));
                toolModel.setUser_id(Integer.parseInt(cur.getString(2)));
                toolModel.setOwner(cur.getString(3));
                toolModel.setFirst_name(cur.getString(4));
                toolModel.setLast_name(cur.getString(5));
                toolModel.setOwner_pic(cur.getString(6));
                toolModel.setBooking_id(Integer.parseInt(cur.getString(7)));
                toolModel.setLender_status(Integer.parseInt(cur.getString(8)));
                toolModel.setBorrower_status(Integer.parseInt(cur.getString(9)));
                toolModel.setTitle(cur.getString(10));
                additionalChargesBean.setSecurity_charges(cur.getString(11));
                additionalChargesBean.setFee(cur.getString(12));
                toolModel.setAdditional_charges(additionalChargesBean);
                toolModel.setTotal_amount(cur.getString(13));
                toolModel.setDelivery_cost(Integer.parseInt(cur.getString(14)));
                toolModel.setPrice(cur.getString(15));
                toolModel.setQuantity(Integer.parseInt(cur.getString(16)));
                toolModel.setCurrency(cur.getString(17));
                toolModel.setDelivery_date(cur.getString(18));
                toolModel.setReturn_date(cur.getString(19));
                toolModel.setSelected_date(cur.getString(20));
                toolModel.setRefund_status(Integer.parseInt(cur.getString(21)));
                toolModel.setBorrower(cur.getString(22));
                toolModel.setBorrower_pic(cur.getString(23));
                toolModel.setCancelled_at(cur.getString(24));
                toolModel.setAttachment(getHistoryRentedAttachments(cur.getString(1)));
                toolModel.setProduct_type(cur.getString(25));
                toolModel.setDiscount(cur.getString(26));
                toolModel.setTransaction_percentage(cur.getString(27));
                toolModel.setCancel_percentage(cur.getString(28));
                mArrayListedTul.add(toolModel);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }

    public ArrayList<BookTulModel.ResponseBean> getAllHistoryLendTuls() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<BookTulModel.ResponseBean> mArrayListedTul = new ArrayList<>();
        try {
            String qry = "select * from " + HISTORY_LENT_TABLE + " ORDER BY " + ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                BookTulModel.ResponseBean toolModel = new BookTulModel.ResponseBean();
                BookTulModel.ResponseBean.AdditionalChargesBean additionalChargesBean = new BookTulModel.ResponseBean.AdditionalChargesBean();

                toolModel.setTool_id(Integer.parseInt(cur.getString(1)));
                toolModel.setUser_id(Integer.parseInt(cur.getString(2)));
                toolModel.setOwner(cur.getString(3));
                toolModel.setFirst_name(cur.getString(4));
                toolModel.setLast_name(cur.getString(5));
                toolModel.setOwner_pic(cur.getString(6));
                toolModel.setBooking_id(Integer.parseInt(cur.getString(7)));
                toolModel.setLender_status(Integer.parseInt(cur.getString(8)));
                toolModel.setBorrower_status(Integer.parseInt(cur.getString(9)));
                toolModel.setTitle(cur.getString(10));

                additionalChargesBean.setSecurity_charges(cur.getString(11));
                additionalChargesBean.setFee(cur.getString(12));
                toolModel.setAdditional_charges(additionalChargesBean);

                toolModel.setTotal_amount(cur.getString(13));
                toolModel.setDelivery_cost(Integer.parseInt(cur.getString(14)));
                toolModel.setPrice(cur.getString(15));
                toolModel.setQuantity(Integer.parseInt(cur.getString(16)));
                toolModel.setCurrency(cur.getString(17));
                toolModel.setDelivery_date(cur.getString(18));
                toolModel.setReturn_date(cur.getString(19));
                toolModel.setSelected_date(cur.getString(20));
                toolModel.setRefund_status(Integer.parseInt(cur.getString(21)));
                toolModel.setBorrower(cur.getString(22));
                toolModel.setBorrower_pic(cur.getString(23));
                toolModel.setCancelled_at(cur.getString(24));
                toolModel.setAttachment(getHistoryLentAttachments(cur.getString(1)));
                toolModel.setProduct_type(cur.getString(25));
                toolModel.setDiscount(cur.getString(26));
                toolModel.setTransaction_percentage(cur.getString(27));
                toolModel.setCancel_percentage(cur.getString(28));
                mArrayListedTul.add(toolModel);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }

    public ArrayList<NotificationModel.ResponseBean> getAllNotifications() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<NotificationModel.ResponseBean> mArrayListedTul = new ArrayList<>();

        try {
            String qry = "select * from " + NOTIFICATION_TABLE;
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                NotificationModel.ResponseBean notificationModel = new NotificationModel.ResponseBean();

                notificationModel.setId(Integer.parseInt(cur.getString(0)));
                notificationModel.setNotification_type(Integer.parseInt(cur.getString(1)));
                notificationModel.setBooking_id(Integer.parseInt(cur.getString(2)));
                notificationModel.setTool_id(Integer.parseInt(cur.getString(3)));
                notificationModel.setMessage(cur.getString(4));
                notificationModel.setCreated_at(cur.getString(5));
                notificationModel.setRead_status(Integer.parseInt(cur.getString(6)));
                notificationModel.setUsername(cur.getString(7));
                notificationModel.setFirst_name(cur.getString(8));
                notificationModel.setLast_name(cur.getString(9));
                notificationModel.setUser_pic(cur.getString(10));
                mArrayListedTul.add(notificationModel);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayListedTul;
    }


    public int getAllUnReadNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        try {
            String qry = "select * from " + NOTIFICATION_TABLE + " where " + READ_STATUS + " = 0";
            cur = db.rawQuery(qry, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                cur.moveToNext();
            }
            return cur.getCount();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return 0;
    }

    public ArrayList<AttachmentModel> getAttachments(String tul_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AttachmentModel> mArrayAttachments = new ArrayList<>();
        try {
            String qry = "select * from " + ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tul_id + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AttachmentModel attachmentModel = new AttachmentModel();
                attachmentModel.setId(Integer.parseInt(cur.getString(0)));
                attachmentModel.setProduct_id(Integer.parseInt(cur.getString(1)));
                attachmentModel.setAttachment(cur.getString(2));
                attachmentModel.setThumbnail(cur.getString(3));
                attachmentModel.setType(cur.getString(4));
                mArrayAttachments.add(attachmentModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAttachments;
    }

    public ArrayList<AttachmentModel> getShortListedAttachments(String tul_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AttachmentModel> mArrayAttachments = new ArrayList<>();
        try {
            String qry = "select * from " + SHORTLIST_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tul_id + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AttachmentModel attachmentModel = new AttachmentModel();
                attachmentModel.setId(Integer.parseInt(cur.getString(0)));
                attachmentModel.setProduct_id(Integer.parseInt(cur.getString(1)));
                attachmentModel.setAttachment(cur.getString(2));
                attachmentModel.setThumbnail(cur.getString(3));
                attachmentModel.setType(cur.getString(4));
                mArrayAttachments.add(attachmentModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAttachments;
    }


    public ArrayList<AmenitiesModel.ResponseBean> getAmenities(String categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AmenitiesModel.ResponseBean> mArrayAmenities = new ArrayList<>();
        try {
            String qry = "select * from " + AMENITIES_TABLE + " where " + CATEGORY_ID + " = '" + categoryId + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AmenitiesModel.ResponseBean amenitiesItem = new AmenitiesModel.ResponseBean();
                amenitiesItem.setId(Integer.parseInt(cur.getString(0)));
                amenitiesItem.setAmenity_name(cur.getString(2));
                amenitiesItem.setCategory_id(Integer.parseInt(cur.getString(1)));
                mArrayAmenities.add(amenitiesItem);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAmenities;
    }

    public ArrayList<AmenitiesModel.ResponseBean> getAllAmenities() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AmenitiesModel.ResponseBean> mArrayAmenities = new ArrayList<>();
        try {
            String qry = "select * from " + AMENITIES_TABLE;
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AmenitiesModel.ResponseBean amenitiesItem = new AmenitiesModel.ResponseBean();
                amenitiesItem.setId(Integer.parseInt(cur.getString(0)));
                amenitiesItem.setAmenity_name(cur.getString(2));
                amenitiesItem.setCategory_id(Integer.parseInt(cur.getString(1)));
                mArrayAmenities.add(amenitiesItem);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAmenities;
    }


    public ArrayList<AttachmentModel> getMyBookingsAttachments(String tul_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AttachmentModel> mArrayAttachments = new ArrayList<>();
        try {
            String qry = "select * from " + MYBOOKINGS_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tul_id + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AttachmentModel attachmentModel = new AttachmentModel();
                attachmentModel.setId(Integer.parseInt(cur.getString(0)));
                attachmentModel.setProduct_id(Integer.parseInt(cur.getString(1)));
                attachmentModel.setAttachment(cur.getString(2));
                attachmentModel.setThumbnail(cur.getString(3));
                attachmentModel.setType(cur.getString(4));
                mArrayAttachments.add(attachmentModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAttachments;
    }


    public ArrayList<AttachmentModel> getLentedAttachments(String tul_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AttachmentModel> mArrayAttachments = new ArrayList<>();
        try {
            String qry = "select * from " + MYBOOKINGS_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tul_id + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AttachmentModel attachmentModel = new AttachmentModel();
                attachmentModel.setId(Integer.parseInt(cur.getString(0)));
                attachmentModel.setProduct_id(Integer.parseInt(cur.getString(1)));
                attachmentModel.setAttachment(cur.getString(2));
                attachmentModel.setThumbnail(cur.getString(3));
                attachmentModel.setType(cur.getString(4));
                mArrayAttachments.add(attachmentModel);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAttachments;
    }


    public ArrayList<AttachmentModel> getHistoryLentAttachments(String tul_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AttachmentModel> mArrayAttachments = new ArrayList<>();
        try {
            String qry = "select * from " + HISTORY_LENT_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tul_id + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AttachmentModel attachmentModel = new AttachmentModel();
                attachmentModel.setId(Integer.parseInt(cur.getString(0)));
                attachmentModel.setProduct_id(Integer.parseInt(cur.getString(1)));
                attachmentModel.setAttachment(cur.getString(2));
                attachmentModel.setThumbnail(cur.getString(3));
                attachmentModel.setType(cur.getString(4));
                mArrayAttachments.add(attachmentModel);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAttachments;
    }


    public ArrayList<AttachmentModel> getHistoryRentedAttachments(String tul_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<AttachmentModel> mArrayAttachments = new ArrayList<>();
        try {
            String qry = "select * from " + HISTORY_RENTED_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tul_id + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                AttachmentModel attachmentModel = new AttachmentModel();
                attachmentModel.setId(Integer.parseInt(cur.getString(0)));
                attachmentModel.setProduct_id(Integer.parseInt(cur.getString(1)));
                attachmentModel.setAttachment(cur.getString(2));
                attachmentModel.setThumbnail(cur.getString(3));
                attachmentModel.setType(cur.getString(4));
                mArrayAttachments.add(attachmentModel);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayAttachments;
    }


    public void deleteAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String dropConversation = "Delete from  " + LISTEDTUL_TABLE;
            db.execSQL(dropConversation);

            String dropMessage = "Delete from  " + ATTACHMENT_TABLE;
            db.execSQL(dropMessage);

            String shortListMessage = "Delete from  " + SHORTLIST_ATTACHMENT_TABLE;
            db.execSQL(shortListMessage);

            String shortListTulMessage = "Delete from  " + SHORTLISTEDTUL_TABLE;
            db.execSQL(shortListTulMessage);

            String cardMessage = "Delete from  " + CARD_DETAILS_TABLE;
            db.execSQL(cardMessage);

            String accountMessage = "Delete from  " + ACCOUNT_DETAIL_TABLE;
            db.execSQL(accountMessage);

            String myBookings = "Delete from  " + BOOKING_TABLE;
            db.execSQL(myBookings);

            String myBookingsAttachment = "Delete from  " + MYBOOKINGS_ATTACHMENT_TABLE;
            db.execSQL(myBookingsAttachment);

            String lentedTulTable = "Delete from  " + LENDTED_TUL_TABLE;
            db.execSQL(lentedTulTable);

            String lentedTulAttachment = "Delete from  " + LENTED_ATTACHMENT_TABLE;
            db.execSQL(lentedTulAttachment);


            String historyRent = "Delete from  " + HISTORY_RENTED_TABLE;
            db.execSQL(historyRent);

            String historyRentAttachment = "Delete from  " + HISTORY_RENTED_ATTACHMENT_TABLE;
            db.execSQL(historyRentAttachment);

            String historyLent = "Delete from  " + HISTORY_LENT_TABLE;
            db.execSQL(historyLent);

            String historyLentAttachment = "Delete from  " + HISTORY_LENT_ATTACHMENT_TABLE;
            db.execSQL(historyLentAttachment);

            String notification = "Delete from  " + NOTIFICATION_TABLE;
            db.execSQL(notification);

            String dropConversationChatting = "Delete from " + CONVERSATION_TABLE;
            db.execSQL(dropConversationChatting);

            String dropMessageChatting = "Delete from " + MESSAGE_TABLE;
            db.execSQL(dropMessageChatting);

            String dropDialogChatting = "Delete from " + DIALOG_TABLE;
            db.execSQL(dropDialogChatting);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTul(String tulId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + LISTEDTUL_TABLE + " where " + ID + " = '" + tulId + "'";
            db.execSQL(delteConversationDialog);

            String deleteDialog = "Delete from " + ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tulId + "'";
            db.execSQL(deleteDialog);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTulAttachemnetById(String tulId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String deleteDialog = "Delete from " + ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tulId + "'";
            db.execSQL(deleteDialog);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }


    public void deleteShortListTul(String tulId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + SHORTLISTEDTUL_TABLE + " where " + ID + " = '" + tulId + "'";
            db.execSQL(delteConversationDialog);

            String deleteDialog = "Delete from " + SHORTLIST_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tulId + "'";
            db.execSQL(deleteDialog);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteBooking(int bookingId, String tulId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + BOOKING_TABLE + " where " + BOOKING_ID + " = '" + bookingId + "'";
            db.execSQL(delteConversationDialog);

            String deleteDialog = "Delete from " + MYBOOKINGS_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tulId + "'";
            db.execSQL(deleteDialog);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteBookingByPush(int bookingId, String tulId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + BOOKING_TABLE + " where " + BOOKING_ID + " = '" + bookingId + "'";
            db.execSQL(delteConversationDialog);
            String deleteDialog = "Delete from " + MYBOOKINGS_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tulId + "'";
            db.execSQL(deleteDialog);

            int count = utils.getInt("booking_count", 0) - 1;
            utils.setInt("booking_count", count);

            if (mUpdateDataListener != null)
                mUpdateDataListener.updateBookingData();

            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }


    public void deleteLendedTul(int bookingId, String tulId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + LENDTED_TUL_TABLE + " where " + BOOKING_ID + " = '" + bookingId + "'";
            db.execSQL(delteConversationDialog);

            String deleteDialog = "Delete from " + LENTED_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tulId + "'";
            db.execSQL(deleteDialog);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteLendedTulByPush(int bookingId, String tulId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + LENDTED_TUL_TABLE + " where " + BOOKING_ID + " = '" + bookingId + "'";
            db.execSQL(delteConversationDialog);

            String deleteDialog = "Delete from " + LENTED_ATTACHMENT_TABLE + " where " + TOOl_ID + " = '" + tulId + "'";
            db.execSQL(deleteDialog);

            int count = utils.getInt("lent_count", 0) - 1;
            utils.setInt("lent_count", count);

            if (mUpdateDataListener != null)
                mUpdateDataListener.updateTulLentData();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<CardLocalModel.ResponseBean> getAllCards() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<CardLocalModel.ResponseBean> mArrayList = new ArrayList<>();
        try {
            String qry = "select * from " + CARD_DETAILS_TABLE + " ORDER BY " + ID + " DESC";
            cur = db.rawQuery(qry, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
//                String card_qry = "create table if not exists " + CARD_DETAILS_TABLE
//                        + " (" + ID + " INTEGER PRIMARY KEY," + CARD_NAME + " TEXT ," + CARD_NUMBER + " TEXT ," + CARD_ID + " TEXT ," + EXPIRY_YEAR + " TEXT ," + EXPIRY_MONTH + " TEXT )";
//                db.execSQL(card_qry);

                CardLocalModel.ResponseBean cardModel = new CardLocalModel.ResponseBean();
                cardModel.setName_on_card(cur.getString(1));
                cardModel.setCard_number(cur.getString(2));
                cardModel.setId(cur.getInt(3));
                cardModel.setExpiry_year(Integer.parseInt(cur.getString(4)));
                cardModel.setExpiry_month(Integer.parseInt(cur.getString(5)));
                mArrayList.add(cardModel);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayList;
    }


    public int getCard(String cardno) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        try {
            String qry = "select * from " + CARD_DETAILS_TABLE + " where " + CARD_NUMBER + " = '" + cardno + "'";
            cur = db.rawQuery(qry, null);
            return cur.getCount();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return 0;
    }


    public ArrayList<CreateStripeAccountModel.ResponseBean> getAllAccounts() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<CreateStripeAccountModel.ResponseBean> mArrayList = new ArrayList<>();
        try {
            String qry = "select * from " + ACCOUNT_DETAIL_TABLE + " ORDER BY " + ACCOUNT_ID + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {

                CreateStripeAccountModel.ResponseBean model = new CreateStripeAccountModel.ResponseBean();
                model.setAccount_holder_name(cur.getString(1));
                model.setAccountId(cur.getInt(2));
                model.setCountry_code(cur.getString(3));
                model.setCurrency(cur.getString(4));
                model.setAccount_number(cur.getString(5));
                model.setSort_code(cur.getString(6));
                model.setAddress(cur.getString(7));
                model.setCity(cur.getString(8));
                model.setState(cur.getString(9));
                model.setEarning(cur.getString(10));
                model.setPostal_code(cur.getString(11));
                model.setDob(cur.getString(12));
                model.setIsPrimary(Integer.parseInt(cur.getString(13)));
                model.setFirst_name(cur.getString(14));
                model.setSwift(cur.getString(15));
                model.setAccount_type(cur.getString(16));
                model.setLast_name(cur.getString(17));
                mArrayList.add(model);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return mArrayList;
    }

    public void addCard(CardLocalModel.ResponseBean cardModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {

            ContentValues values = new ContentValues();

            values.put(CARD_NAME, cardModel.getName_on_card());
            values.put(CARD_ID, cardModel.getId());
            values.put(EXPIRY_YEAR, cardModel.getExpiry_year());
            values.put(EXPIRY_MONTH, cardModel.getExpiry_month());

            data = getReadableDatabase().rawQuery("Select * from " + CARD_DETAILS_TABLE + " where "
                    + CARD_NUMBER + " = '" + cardModel.getCard_number() + "'", null);
            if (data.getCount() > 0) {
                db.update(CARD_DETAILS_TABLE, values, CARD_NUMBER + " = '" + cardModel.getCard_number() + "'", null);
            } else {
                values.put(CARD_NUMBER, cardModel.getCard_number());
                db.insertOrThrow(CARD_DETAILS_TABLE, null, values);
            }

            Log.d("CardData", String.valueOf(data.getCount()));
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }

    }

    public void addAccount(CreateStripeAccountModel.ResponseBean model) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();

            values.put(ACCOUNT_NAME, model.getFirst_name() + " " + model.getLast_name());
            values.put(ACCOUNT_COUNTRY_CODE, model.getCountry_code());
            values.put(ACCOUNT_CURRENCY, model.getCurrency());
            values.put(ACCOUNT_NUMBER, model.getAccount_number());
            values.put(ACCOUNT_SORT_CODE, model.getSort_code());
            values.put(ACCOUNT_ADDRESS, model.getAddress());
            values.put(ACCOUNT_CITY, model.getCity());
            values.put(ACCOUNT_STATE, model.getState());
            values.put(ACCOUNT_POSTAL_CODE, model.getPostal_code());
            values.put(ACCOUNT_DOB, model.getDob());
            values.put(ACCOUNT_EARNING, model.getEarning());
            values.put(ACCOUNT_IS_PRIMARY, String.valueOf(model.getIsPrimary()));
            values.put(FIRST_NAME, model.getFirst_name());
            values.put(SWIFT, model.getSwift());
            values.put(ACCOUNT_TYPE, model.getAccount_type());
            values.put(LAST_NAME, model.getLast_name());

            data = getReadableDatabase().rawQuery("Select * from " + ACCOUNT_DETAIL_TABLE + " where "
                    + ACCOUNT_ID + " = '" + model.getAccountId() + "'", null);
            if (data.getCount() > 0) {
                db.update(ACCOUNT_DETAIL_TABLE, values, ACCOUNT_ID + " = '" + model.getAccountId() + "'", null);
            } else {
                values.put(ACCOUNT_ID, model.getAccountId());
                db.insertOrThrow(ACCOUNT_DETAIL_TABLE, null, values);
            }
            Log.d("AccountData", String.valueOf(data.getCount()));
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {

            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public void removeAccount(CreateStripeAccountModel.ResponseBean model) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.delete(ACCOUNT_DETAIL_TABLE, ACCOUNT_ID + " = " + String.valueOf(model.getAccountId()), null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void removeCard(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.delete(CARD_DETAILS_TABLE, CARD_ID + " = " + id, null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updatePrimaryAccount(int accountId, int primaryValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ACCOUNT_IS_PRIMARY, primaryValue);
            db.update(ACCOUNT_DETAIL_TABLE, cv, ACCOUNT_ID + " = '" + accountId + "'", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }


    public void updateLentStatus(int bookingId, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(LENDER_STATUS, status);
            if (status.equals("1"))
                cv.put(HANDOVER_AT, date);
            else
                cv.put(LANDER_RECEIVE_AT, date);
            db.update(LENDTED_TUL_TABLE, cv, BOOKING_ID + " = '" + bookingId + "'", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void updateBorrowerStatus(int bookingId, String receivedTime, String status) {
        /// status 1 for received
        /// status 2 for returned
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(BORROWER_STATUS, status);
            if (status.equals("1"))
                cv.put(BORROWER_RECEIVED_AT, receivedTime);
            else if (status.equals("2"))
                cv.put(BORROWER_RETURNED_AT, receivedTime);
            db.update(BOOKING_TABLE, cv, BOOKING_ID + " = '" + bookingId + "'", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }


    public void updateBorrowerStatusByPush(int bookingId, String status, String date) {
        /// status 1 for received
        /// status 2 for returned
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(BORROWER_STATUS, status);
            cv.put(BORROWER_RETURNED_AT, date);
            db.update(BOOKING_TABLE, cv, BOOKING_ID + " = '" + bookingId + "'", null);

            if (mUpdateDataListener != null) {
                mUpdateDataListener.updateBookingData();
                mUpdateDataListener.updateTulLentData();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void updateLentStatusByPush(int bookingId, String status, String date) {
        /// update lented status in borrower table
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(LENDER_STATUS, status);
            cv.put(LANDER_RECEIVE_AT, date);
            db.update(LENDTED_TUL_TABLE, cv, BOOKING_ID + " = '" + bookingId + "'", null);
            if (mUpdateDataListener != null) {
                mUpdateDataListener.updateBookingData();
                mUpdateDataListener.updateTulLentData();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void updateNotificationReadStatus(int id, String status) {
        /// status 1 for Read
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(READ_STATUS, status);
            db.update(NOTIFICATION_TABLE, cv, ID + " = '" + id + "'", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void addMessage(ChatMessageModel chatMessageModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(CHAT_DIALOG_ID, chatMessageModel.getChat_dialog_id());
            values.put(MESSAGE, chatMessageModel.getMessage());
            values.put(MESSAGE_STATUS, chatMessageModel.getMessage_status());
            values.put(MESSAGE_TIME, chatMessageModel.getMessage_time());
            values.put(SENDER_ID, chatMessageModel.getSender_id());

            data = getReadableDatabase().rawQuery("Select * from " + MESSAGE_TABLE + " where "
                    + MESSAGE_ID + " = '" + chatMessageModel.getMessage_id() + "'", null);
            if (data.getCount() > 0) {
                db.update(MESSAGE_TABLE, values, MESSAGE_ID + " = '" + chatMessageModel.getMessage_id() + "'", null);
            } else {
                values.put(MESSAGE_ID, chatMessageModel.getMessage_id());
                db.insertOrThrow(MESSAGE_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public LinkedHashMap<String, ChatMessageModel> getAllMessages(String dialogId, String lastTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        LinkedHashMap<String, ChatMessageModel> messageMap = new LinkedHashMap<>();
        List<ChatMessageModel> messageList = new ArrayList<>();
        try {
            String qry = "select * from " + MESSAGE_TABLE + " where " + CHAT_DIALOG_ID + " = '" + dialogId + "'";
            cur = db.rawQuery(qry, null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                ChatMessageModel chatMessageItem = new ChatMessageModel();
                chatMessageItem.setChat_dialog_id(cur.getString(0));
                chatMessageItem.setMessage(cur.getString(1));
                chatMessageItem.setMessage_status(cur.getInt(2));
                chatMessageItem.setMessage_time(cur.getString(3));
                chatMessageItem.setSender_id(cur.getString(4));
                chatMessageItem.setMessage_id(cur.getString(5));
                if (Long.parseLong(cur.getString(3)) > Long.parseLong(lastTime)) {
                    messageList.add(chatMessageItem);
                }
                cur.moveToNext();
            }
            if (messageList.size() > 0) {
//                Collections.reverse(messageList);
                messageMap = createHeader(messageList);
            }
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return messageMap;
    }

    LinkedHashMap<String, ChatMessageModel> createHeader(List<ChatMessageModel> ChatMessageModel) {
        LinkedHashMap<String, ChatMessageModel> mMessageList = new LinkedHashMap<>();
        SimpleDateFormat chat_date_format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat today_date_format = new SimpleDateFormat("hh:mm aa", Locale.US);
        SimpleDateFormat show_dateheader_format = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        Calendar cal = Calendar.getInstance();
        String today = chat_date_format.format(cal.getTime());

        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -1);
        String yesterday = chat_date_format.format(cal1.getTime());

        String lastHeader = "";
        for (ChatMessageModel message : ChatMessageModel) {
            Calendar calDb = Calendar.getInstance();
            long timeinMillis;
            try {
                timeinMillis = Long.parseLong(message.getMessage_time());
            } catch (Exception e) {
                timeinMillis = calDb.getTimeInMillis();
            }
            calDb.setTimeInMillis(timeinMillis);
            String dbDate = chat_date_format.format(calDb.getTime());

            if (!TextUtils.equals(lastHeader, dbDate)) {
                lastHeader = dbDate;

                ChatMessageModel mMessage = new ChatMessageModel();
                mMessage.setIs_header(true);

                if (dbDate.equals(today)) {
                    mMessage.setShow_HeaderText("Today");
                    mMessage.setShow_message_datetime(today_date_format.format(calDb.getTime()));
                } else if (dbDate.equals(yesterday)) {
                    mMessage.setShow_HeaderText("Yesterday");
                    mMessage.setShow_message_datetime(today_date_format.format(calDb.getTime()));
                } else {
                    mMessage.setShow_HeaderText(show_dateheader_format.format(calDb.getTime()));
                    mMessage.setShow_message_datetime(today_date_format.format(calDb.getTime()));
                }
                mMessageList.put(lastHeader, mMessage);
            }
            ChatMessageModel mMessage = message;
            mMessage.setIs_header(false);
            mMessage.setShow_message_datetime(today_date_format.format(calDb.getTime()));

            mMessageList.put(message.getMessage_id(), mMessage);
        }

        return mMessageList;
    }

    public void addConversation(ChatDialogModel chatDialogModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            String particpantIdArray[] = chatDialogModel.getParticipant_ids().split(",");
            String particpantId = null;
            for (int i = 0; i < particpantIdArray.length; i++) {

                if (!particpantIdArray[i].equals(utils.getString("user_id", "")))
                    particpantId = particpantIdArray[i];
            }
            ContentValues values = new ContentValues();

            values.put(CHAT_DIALOG_ID, chatDialogModel.getChat_dialog_id());//0
            values.put(DELETE_DIALOG_TIME, chatDialogModel.getDelete_dialog_time().get(utils.getString("user_id", "")));//2
            values.put(LAST_MESSAGE, chatDialogModel.getLast_message());//3
            values.put(LAST_MESSAGE_ID, chatDialogModel.getLast_message_id());//4
            values.put(LAST_MESSAGE_SENDER_ID, chatDialogModel.getLast_message_sender_id());//5
            values.put(LAST_MESSAGE_TIME, chatDialogModel.getLast_message_time());//6
            values.put(PLATFORM_STATUS, chatDialogModel.getPlatform_status().get(particpantId));//7
            values.put(PROFILE_PIC, chatDialogModel.getProfile_pic().get(particpantId));//8
            values.put(PUSH_TOKEN, chatDialogModel.getPush_token().get(particpantId));//9
            values.put(ACCESS_TOKEN, chatDialogModel.getAccess_token().get(particpantId));//10
            values.put(UNREAD_COUNT, chatDialogModel.getUnread_count().get(utils.getString("user_id", "")));//11
            values.put(NAME, chatDialogModel.getName().get(particpantId));//12
            values.put(DIALOG_TIME, chatDialogModel.getDialog_created_time());//14
            values.put(MUTE_STATUS, chatDialogModel.getMute().get(particpantId));//15
            values.put(OWN_MUTE_STATUS, chatDialogModel.getMute().get(utils.getString("user_id", "")));//16
            values.put(OTHERBLOCKSTATUS, chatDialogModel.getBlock_status().get(particpantId));//17
            values.put(MYBLOCKSTATUS, chatDialogModel.getBlock_status().get(utils.getString("user_id", "")));//18
            if (chatDialogModel.getDelete_outer_dialog() != null) {
                values.put(OWN_DELETE_STATUS, chatDialogModel.getDelete_outer_dialog().get(utils.getString("user_id", "")));//19
                values.put(OTHER_DELETE_STATUS, chatDialogModel.getDelete_outer_dialog().get(particpantId));//20
            } else {
                values.put(OWN_DELETE_STATUS, "0");//19
                values.put(OTHER_DELETE_STATUS, "0");//20
            }
            data = getReadableDatabase().rawQuery("Select * from " + CONVERSATION_TABLE + " where "
                    + PARTICPANT_ID + " = '" + particpantId + "'", null);
            if (data.getCount() > 0) {
                db.update(CONVERSATION_TABLE, values, PARTICPANT_ID + " = '" + particpantId + "'", null);
            } else {
                values.put(PARTICPANT_ID, particpantId);//13
                db.insertOrThrow(CONVERSATION_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }


    public Map<String, ChatDialogModel> getAllConversation() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        Map<String, ChatDialogModel> conversationMap = new LinkedHashMap<>();
        try {
            String qry = "select * from " + CONVERSATION_TABLE + " ORDER BY " + LAST_MESSAGE_TIME + " DESC";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {

                ChatDialogModel chatDialogItem = new ChatDialogModel();
                chatDialogItem.setChat_dialog_id(cur.getString(0));
                /// access_token
                HashMap<String, String> tempMap = new HashMap<>();
                tempMap.put(utils.getString("user_id", ""), utils.getString("access_token", ""));
                tempMap.put(cur.getString(13), cur.getString(10));
                chatDialogItem.setAccess_token(tempMap);

                /// delete dialog time
                HashMap<String, String> tempMap1 = new HashMap<>();
                tempMap1.put(utils.getString("user_id", ""), cur.getString(2));
                tempMap1.put(cur.getString(13), "");
                chatDialogItem.setDelete_dialog_time(tempMap1);

                /// name
                HashMap<String, String> tempMap2 = new HashMap<>();
                tempMap2.put(utils.getString("user_id", ""), utils.getString("user_name", ""));
                tempMap2.put(cur.getString(13), cur.getString(12));
                chatDialogItem.setName(tempMap2);

                /// platform status
                HashMap<String, String> tempMap3 = new HashMap<>();
                tempMap3.put(utils.getString("user_id", ""), String.valueOf(Constants.PLATFORM_STATUS));
                tempMap3.put(cur.getString(13), cur.getString(7));
                chatDialogItem.setPlatform_status(tempMap3);

                /// profile_pic
                HashMap<String, String> tempMap4 = new HashMap<>();
                tempMap4.put(utils.getString("user_id", ""), utils.getString("profile_pic", ""));
                tempMap4.put(cur.getString(13), cur.getString(8));
                chatDialogItem.setProfile_pic(tempMap4);

                /// push_token
                HashMap<String, String> tempMap5 = new HashMap<>();
                tempMap5.put(utils.getString("user_id", ""), utils.getString("device_token", ""));
                tempMap5.put(cur.getString(13), cur.getString(9));
                chatDialogItem.setPush_token(tempMap5);

                /// unread Count
                HashMap<String, String> tempMap6 = new HashMap<>();
                tempMap6.put(utils.getString("user_id", ""), cur.getString(11));
                tempMap6.put(cur.getString(13), "0");
                chatDialogItem.setUnread_count(tempMap6);

                /// mute status
                HashMap<String, String> tempMap7 = new HashMap<>();
                tempMap7.put(utils.getString("user_id", ""), cur.getString(16));
                tempMap7.put(cur.getString(13), cur.getString(15));
                chatDialogItem.setMute(tempMap7);

                ///block status
                HashMap<String, String> tempMapblock = new HashMap<>();
                tempMapblock.put(cur.getString(13), cur.getString(17));
                tempMapblock.put(utils.getString("user_id", ""), cur.getString(18));
                chatDialogItem.setBlock_status(tempMapblock);

                ///delete dialog status
                HashMap<String, String> tempMapDeleteDialog = new HashMap<>();
                tempMapDeleteDialog.put(utils.getString("user_id", ""), cur.getString(19));
                tempMapDeleteDialog.put(cur.getString(13), cur.getString(20));
                chatDialogItem.setDelete_outer_dialog(tempMapDeleteDialog);

                chatDialogItem.setLast_message(cur.getString(3));
                chatDialogItem.setLast_message_id(cur.getString(4));
                chatDialogItem.setLast_message_sender_id(cur.getString(5));
                chatDialogItem.setLast_message_time(cur.getString(6));
                chatDialogItem.setDialog_created_time(cur.getString(14));
                chatDialogItem.setParticipant_ids(utils.getString("user_id", "") + "," + cur.getString(13));
                conversationMap.put(cur.getString(0), chatDialogItem);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return conversationMap;
    }


    public Map<String, ChatDialogModel> getConversationById(String dialogId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        Map<String, ChatDialogModel> conversationMap = new LinkedHashMap<>();
        try {
            String qry = "select * from " + CONVERSATION_TABLE + " where " + CHAT_DIALOG_ID + " = '" + dialogId + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {

                ChatDialogModel chatDialogItem = new ChatDialogModel();
                chatDialogItem.setChat_dialog_id(cur.getString(0));

                /// access_token
                HashMap<String, String> tempMap = new HashMap<>();
                tempMap.put(utils.getString("user_id", ""), utils.getString("access_token", ""));
                tempMap.put(cur.getString(13), cur.getString(10));
                chatDialogItem.setAccess_token(tempMap);

                /// delete dialog time
                HashMap<String, String> tempMap1 = new HashMap<>();
                tempMap1.put(utils.getString("user_id", ""), cur.getString(2));
                tempMap1.put(cur.getString(13), "");
                chatDialogItem.setDelete_dialog_time(tempMap1);

                /// name
                HashMap<String, String> tempMap2 = new HashMap<>();
                tempMap2.put(utils.getString("user_id", ""), utils.getString("name", ""));
                tempMap2.put(cur.getString(13), cur.getString(12));
                chatDialogItem.setName(tempMap2);

                /// platform status
                HashMap<String, String> tempMap3 = new HashMap<>();
                tempMap3.put(utils.getString("user_id", ""), String.valueOf(Constants.PLATFORM_STATUS));
                tempMap3.put(cur.getString(13), cur.getString(7));
                chatDialogItem.setPlatform_status(tempMap3);

                /// profile_pic
                HashMap<String, String> tempMap4 = new HashMap<>();
                tempMap4.put(utils.getString("user_id", ""), utils.getString("profile_pic", ""));
                tempMap4.put(cur.getString(13), cur.getString(8));
                chatDialogItem.setProfile_pic(tempMap4);

                /// push_token
                HashMap<String, String> tempMap5 = new HashMap<>();
                tempMap5.put(utils.getString("user_id", ""), utils.getString("device_token", ""));
                tempMap5.put(cur.getString(13), cur.getString(9));
                chatDialogItem.setPush_token(tempMap5);

                /// unread Count
                HashMap<String, String> tempMap6 = new HashMap<>();
                tempMap6.put(utils.getString("user_id", ""), cur.getString(11));
                tempMap6.put(cur.getString(13), "0");
                chatDialogItem.setUnread_count(tempMap6);

                /// mute status
                HashMap<String, String> tempMap7 = new HashMap<>();
                tempMap7.put(utils.getString("user_id", ""), cur.getString(16));
                tempMap7.put(cur.getString(13), cur.getString(15));
                chatDialogItem.setMute(tempMap7);

                ///block_status
                HashMap<String, String> tempMapblock = new HashMap<>();
                tempMapblock.put(cur.getString(13), cur.getString(17));
                tempMapblock.put(utils.getString("user_id", ""), cur.getString(18));
                chatDialogItem.setBlock_status(tempMapblock);

                ///delete dialog status
                HashMap<String, String> tempMapDeleteDialog = new HashMap<>();
                tempMapDeleteDialog.put(utils.getString("user_id", ""), cur.getString(19));
                tempMapDeleteDialog.put(cur.getString(13), cur.getString(20));
                chatDialogItem.setDelete_outer_dialog(tempMapDeleteDialog);

                chatDialogItem.setLast_message(cur.getString(3));
                chatDialogItem.setLast_message_id(cur.getString(4));
                chatDialogItem.setLast_message_sender_id(cur.getString(5));
                chatDialogItem.setLast_message_time(cur.getString(6));
                chatDialogItem.setDialog_created_time(cur.getString(14));
                chatDialogItem.setParticipant_ids(utils.getString("user_id", "") + "," + cur.getString(13));
                conversationMap.put(cur.getString(0), chatDialogItem);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return conversationMap;
    }



    public Map<String, ChatDialogModel> getConversationByParticpantId(String particpantID) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        Map<String, ChatDialogModel> conversationMap = new LinkedHashMap<>();
        try {
            String qry = "select * from " + CONVERSATION_TABLE + " where " + PARTICPANT_ID + " = '" + particpantID + "'";
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {

                ChatDialogModel chatDialogItem = new ChatDialogModel();
                chatDialogItem.setChat_dialog_id(cur.getString(0));

                /// access_token
                HashMap<String, String> tempMap = new HashMap<>();
                tempMap.put(utils.getString("user_id", ""), utils.getString("access_token", ""));
                tempMap.put(cur.getString(13), cur.getString(10));
                chatDialogItem.setAccess_token(tempMap);

                /// delete dialog time
                HashMap<String, String> tempMap1 = new HashMap<>();
                tempMap1.put(utils.getString("user_id", ""), cur.getString(2));
                tempMap1.put(cur.getString(13), "");
                chatDialogItem.setDelete_dialog_time(tempMap1);

                /// name
                HashMap<String, String> tempMap2 = new HashMap<>();
                tempMap2.put(utils.getString("user_id", ""), utils.getString("name", ""));
                tempMap2.put(cur.getString(13), cur.getString(12));
                chatDialogItem.setName(tempMap2);

                /// platform status
                HashMap<String, String> tempMap3 = new HashMap<>();
                tempMap3.put(utils.getString("user_id", ""), String.valueOf(Constants.PLATFORM_STATUS));
                tempMap3.put(cur.getString(13), cur.getString(7));
                chatDialogItem.setPlatform_status(tempMap3);

                /// profile_pic
                HashMap<String, String> tempMap4 = new HashMap<>();
                tempMap4.put(utils.getString("user_id", ""), utils.getString("profile_pic", ""));
                tempMap4.put(cur.getString(13), cur.getString(8));
                chatDialogItem.setProfile_pic(tempMap4);

                /// push_token
                HashMap<String, String> tempMap5 = new HashMap<>();
                tempMap5.put(utils.getString("user_id", ""), utils.getString("device_token", ""));
                tempMap5.put(cur.getString(13), cur.getString(9));
                chatDialogItem.setPush_token(tempMap5);

                /// unread Count
                HashMap<String, String> tempMap6 = new HashMap<>();
                tempMap6.put(utils.getString("user_id", ""), cur.getString(11));
                tempMap6.put(cur.getString(13), "0");
                chatDialogItem.setUnread_count(tempMap6);

                /// mute status
                HashMap<String, String> tempMap7 = new HashMap<>();
                tempMap7.put(utils.getString("user_id", ""), cur.getString(16));
                tempMap7.put(cur.getString(13), cur.getString(15));
                chatDialogItem.setMute(tempMap7);

                ///block_status
                HashMap<String, String> tempMapblock = new HashMap<>();
                tempMapblock.put(utils.getString("user_id", ""), cur.getString(18));
                tempMapblock.put(cur.getString(13), cur.getString(17));
                chatDialogItem.setBlock_status(tempMapblock);

                chatDialogItem.setLast_message(cur.getString(3));
                chatDialogItem.setLast_message_id(cur.getString(4));
                chatDialogItem.setLast_message_sender_id(cur.getString(5));
                chatDialogItem.setLast_message_time(cur.getString(6));
                chatDialogItem.setDialog_created_time(cur.getString(14));
                chatDialogItem.setParticipant_ids(utils.getString("user_id", "") + "," + cur.getString(13));
                conversationMap.put(cur.getString(0), chatDialogItem);
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return conversationMap;
    }

    public void addDialog(String dialog_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data = null;
        try {
            ContentValues values = new ContentValues();
            values.put(USER_ID, utils.getString("user_id", ""));
            data = getReadableDatabase().rawQuery("Select * from " + DIALOG_TABLE + " where "
                    + DIALOG_ID + " = '" + dialog_id + "'", null);
            if (data.getCount() > 0) {
                db.update(DIALOG_TABLE, values, DIALOG_ID + " = '" + dialog_id + "'", null);
            } else {
                values.put(DIALOG_ID, dialog_id);
                db.insertOrThrow(DIALOG_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (data != null)
                data.close();
        }
    }

    public ArrayList<String> getDialog(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = null;
        ArrayList<String> dialogList = new ArrayList<>();
        try {
//            String qry = "select * from " + DIALOG_TABLE + " where " + USER_ID + " = '" + userId + "'";
            String qry = "select * from " + DIALOG_TABLE;
            cur = db.rawQuery(qry, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                dialogList.add(cur.getString(1));
                cur.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Exception", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null)
                cur.close();
        }
        return dialogList;
    }

    public void deleteConversationById(String dialog_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + CONVERSATION_TABLE + " where " + CHAT_DIALOG_ID + " = '" + dialog_id + "'";
            db.execSQL(delteConversationDialog);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteDialog(String dialogId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteConversationDialog = "Delete from " + CONVERSATION_TABLE + " where " + CHAT_DIALOG_ID + " = '" + dialogId + "'";
            db.execSQL(delteConversationDialog);

            String deleteDialog = "Delete from " + DIALOG_TABLE + " where " + DIALOG_ID + " = '" + dialogId + "'";
            db.execSQL(deleteDialog);

            String delteMessageDialog = "Delete from " + MESSAGE_TABLE + " where " + CHAT_DIALOG_ID + " = '" + dialogId + "'";
            db.execSQL(delteMessageDialog);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteAllMessages(String dialogId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String delteMessageDialog = "Delete from " + MESSAGE_TABLE + " where " + CHAT_DIALOG_ID + " = '" + dialogId + "'";
            db.execSQL(delteMessageDialog);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Exception = ", e + "");
        } finally {
            db.endTransaction();
        }
    }

}
