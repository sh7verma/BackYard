package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by applify on 10/13/2017.
 */

public class TulModel {

    public static ListTulModel mListTul;
    public static PreferencesTul mPrefrencesTul;
    public static BankDetailsTul mBankDetailsTul;
    public static PricingTul mPricingTul;

    public static ListTulModel getListTul() {
        if (mListTul == null)
            mListTul = new ListTulModel();
        return mListTul;
    }

    public static void setListTul(ListTulModel mListTul) {
        TulModel.mListTul = mListTul;
    }

    public static PreferencesTul getPrefrencesTul() {
        if (mPrefrencesTul == null)
            mPrefrencesTul = new PreferencesTul();
        return mPrefrencesTul;
    }

    public static void setPrefrencesTul(PreferencesTul mPrefrencesTul) {
        TulModel.mPrefrencesTul = mPrefrencesTul;
    }

    public static BankDetailsTul getBankDetailsTul() {
        if (mBankDetailsTul == null)
            mBankDetailsTul = new BankDetailsTul();
        return mBankDetailsTul;
    }

    public static void setBankDetailsTul(BankDetailsTul mPrefrencesTul) {
        TulModel.mBankDetailsTul = mBankDetailsTul;
    }

    public static PricingTul getPricingTul() {
        if (mPricingTul == null)
            mPricingTul = new PricingTul();
        return mPricingTul;
    }

    public static void setPricingTul(PricingTul mPricingTul) {
        TulModel.mPricingTul = mPricingTul;
    }

    public static class ListTulModel implements Parcelable {
        public String title;
        public String category;
        public int categoryId;
        public String description;
//        public String price;
//        public String securityFee;
//        public String convienceFee;
        public String address;
        public Double latitude;
        public Double longitude;
        public int buffer;
        public ArrayList<String> rules;
        public  ArrayList<String> amenities;
        public ArrayList<TulImageModel> imagesVideo;
        public ArrayList<String> attachments_ids;
        public Boolean updateData;
        public boolean isEdit;

        public ListTulModel() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.category);
            dest.writeInt(this.categoryId);
            dest.writeString(this.description);
            dest.writeString(this.address);
            dest.writeValue(this.latitude);
            dest.writeValue(this.longitude);
            dest.writeInt(this.buffer);
            dest.writeStringList(this.rules);
            dest.writeStringList(this.amenities);
            dest.writeTypedList(this.imagesVideo);
            dest.writeStringList(this.attachments_ids);
            dest.writeValue(this.updateData);
            dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        }

        protected ListTulModel(Parcel in) {
            this.title = in.readString();
            this.category = in.readString();
            this.categoryId = in.readInt();
            this.description = in.readString();
            this.address = in.readString();
            this.latitude = (Double) in.readValue(Double.class.getClassLoader());
            this.longitude = (Double) in.readValue(Double.class.getClassLoader());
            this.buffer = in.readInt();
            this.rules = in.createStringArrayList();
            this.amenities = in.createStringArrayList();
            this.imagesVideo = in.createTypedArrayList(TulImageModel.CREATOR);
            this.attachments_ids = in.createStringArrayList();
            this.updateData = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.isEdit = in.readByte() != 0;
        }

        public static final Creator<ListTulModel> CREATOR = new Creator<ListTulModel>() {
            @Override
            public ListTulModel createFromParcel(Parcel source) {
                return new ListTulModel(source);
            }

            @Override
            public ListTulModel[] newArray(int size) {
                return new ListTulModel[size];
            }
        };
    }

    public static class PreferencesTul implements Parcelable {
        public String availbiltyMode;
        public String startTime;
        public String endTime;
        public String bookingAvailbiltyFor;
        public String hrsStartTime;
        public String hrsEndTime;
        public String deliveryCharges;
        public String startPickUpTime;
        public String endPickUpTime;
        public boolean deleiveryAvailable;
        public boolean updateData;
        public boolean isEdit;

        public PreferencesTul() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.availbiltyMode);
            dest.writeString(this.startTime);
            dest.writeString(this.endTime);
            dest.writeString(this.bookingAvailbiltyFor);
            dest.writeString(this.hrsStartTime);
            dest.writeString(this.hrsEndTime);
            dest.writeString(this.deliveryCharges);
            dest.writeString(this.startPickUpTime);
            dest.writeString(this.endPickUpTime);
            dest.writeByte(this.deleiveryAvailable ? (byte) 1 : (byte) 0);
            dest.writeByte(this.updateData ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        }

        protected PreferencesTul(Parcel in) {
            this.availbiltyMode = in.readString();
            this.startTime = in.readString();
            this.endTime = in.readString();
            this.bookingAvailbiltyFor = in.readString();
            this.hrsStartTime = in.readString();
            this.hrsEndTime = in.readString();
            this.deliveryCharges = in.readString();
            this.startPickUpTime = in.readString();
            this.endPickUpTime = in.readString();
            this.deleiveryAvailable = in.readByte() != 0;
            this.updateData = in.readByte() != 0;
            this.isEdit = in.readByte() != 0;
        }

        public static final Creator<PreferencesTul> CREATOR = new Creator<PreferencesTul>() {
            @Override
            public PreferencesTul createFromParcel(Parcel source) {
                return new PreferencesTul(source);
            }

            @Override
            public PreferencesTul[] newArray(int size) {
                return new PreferencesTul[size];
            }
        };
    }

    public static class PricingTul implements Parcelable {
        public String pricePerHour;
        public String pricePerDay;
        public String securityCharges;
        public String yourEarning;
        public String yourExtraEarning;
        public String extraFee;
        public String noBookings;
        public String discountPercentage;
        public boolean isEdit;

        public PricingTul() {
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pricePerHour);
            dest.writeString(this.pricePerDay);
            dest.writeString(this.securityCharges);
            dest.writeString(this.yourEarning);
            dest.writeString(this.yourExtraEarning);
            dest.writeString(this.extraFee);
            dest.writeString(this.noBookings);
            dest.writeString(this.discountPercentage);
            dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        }

        protected PricingTul(Parcel in) {
            this.pricePerHour = in.readString();
            this.pricePerDay = in.readString();
            this.securityCharges = in.readString();
            this.yourEarning = in.readString();
            this.yourExtraEarning = in.readString();
            this.extraFee = in.readString();
            this.noBookings = in.readString();
            this.discountPercentage = in.readString();
            this.isEdit = in.readByte() != 0;
        }

        public static final Creator<PricingTul> CREATOR = new Creator<PricingTul>() {
            @Override
            public PricingTul createFromParcel(Parcel source) {
                return new PricingTul(source);
            }

            @Override
            public PricingTul[] newArray(int size) {
                return new PricingTul[size];
            }
        };
    }

    public static class BankDetailsTul implements Parcelable {
        public String countryCode;
        public String currency;
        public String accountNo;
        public String sortCode;
        public String firstName;
        public String lastName;
        public String address;
        public String city;
        public String state;
        public String postalCode;
        public String dob;
        public boolean updateData;
        public String documentPath;
        public File documnetFile;
        public String swift;
        public String account_type;
        public BankDetailsTul() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.countryCode);
            dest.writeString(this.currency);
            dest.writeString(this.accountNo);
            dest.writeString(this.sortCode);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.address);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.postalCode);
            dest.writeString(this.dob);
            dest.writeByte(this.updateData ? (byte) 1 : (byte) 0);
            dest.writeString(this.documentPath);
            dest.writeSerializable(this.documnetFile);
            dest.writeString(this.swift);
            dest.writeString(this.account_type);
        }

        protected BankDetailsTul(Parcel in) {
            this.countryCode = in.readString();
            this.currency = in.readString();
            this.accountNo = in.readString();
            this.sortCode = in.readString();
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.address = in.readString();
            this.city = in.readString();
            this.state = in.readString();
            this.postalCode = in.readString();
            this.dob = in.readString();
            this.updateData = in.readByte() != 0;
            this.documentPath = in.readString();
            this.documnetFile = (File) in.readSerializable();
            this.swift = in.readString();
            this.account_type = in.readString();
        }

        public static final Creator<BankDetailsTul> CREATOR = new Creator<BankDetailsTul>() {
            @Override
            public BankDetailsTul createFromParcel(Parcel source) {
                return new BankDetailsTul(source);
            }

            @Override
            public BankDetailsTul[] newArray(int size) {
                return new BankDetailsTul[size];
            }
        };
    }

}
