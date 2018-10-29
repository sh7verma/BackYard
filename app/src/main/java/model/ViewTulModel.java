package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by applify on 10/18/2017.
 */

public class ViewTulModel extends BaseModel {


    /**
     * response : {"id":24,"title":"Shhshs","user_id":7,"owner":"Rajat Arora","category_id":5,"description":"Xghsh","price":"76","currency":"£","additional_price":{"security_charges":"566","fee":"566"},"address":"Unnamed Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,null,Punjab,India","latitute":"","longitude":"76.7328227","rules":["Hzhz"],"aminities":["Hzjkhf"],"preferences":{"available":"Only Weekends","start_time":"12:36 PM","end_time":"12:37 PM","tull_delivery":"0","delivery_charges":null,"delivery_start_time":null,"delivery_end_time":null},"bank_detail":{"id":20,"user_id":7,"product_id":24,"bank_name":"STRIPE TEST BANK","account_number":"00012345","strip_account":"acct_1BGizcBSg20GFTtn","postal_code":"Ca103","dob":"25-10-2004","currency":"GBP","country_code":"GB","city":"Stsy","address":"Ysusy","first_name":"Rajat","last_name":"Arora","state":"Syysy","sort_code":"108800"},"attachment":[{"id":31,"product_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg","type":"image"}]}
     * code : 400
     */

    private ResponseBean response;
    private int code;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResponseBean implements Parcelable {
        public static final Creator<ResponseBean> CREATOR = new Creator<ResponseBean>() {
            @Override
            public ResponseBean createFromParcel(Parcel source) {
                return new ResponseBean(source);
            }

            @Override
            public ResponseBean[] newArray(int size) {
                return new ResponseBean[size];
            }
        };
        /**
         * id : 24
         * title : Shhshs
         * user_id : 7
         * owner : Rajat Arora
         * category_id : 5
         * description : Xghsh
         * price : 76
         * currency : £
         * additional_price : {"security_charges":"566","fee":"566"}
         * address : Unnamed Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,null,Punjab,India
         * latitute :
         * longitude : 76.7328227
         * rules : ["Hzhz"]
         * aminities : ["Hzjkhf"]
         * preferences : {"available":"Only Weekends","start_time":"12:36 PM","end_time":"12:37 PM","tull_delivery":"0","delivery_charges":null,"delivery_start_time":null,"delivery_end_time":null}
         * bank_detail : {"id":20,"user_id":7,"product_id":24,"bank_name":"STRIPE TEST BANK","account_number":"00012345","strip_account":"acct_1BGizcBSg20GFTtn","postal_code":"Ca103","dob":"25-10-2004","currency":"GBP","country_code":"GB","city":"Stsy","address":"Ysusy","first_name":"Rajat","last_name":"Arora","state":"Syysy","sort_code":"108800"}
         * attachment : [{"id":31,"product_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg","type":"image"}]
         */

        private int id;
        private String title;
        private int user_id;
        private int rating;
        private String owner;
        private String owner_pic;
        private int category_id;
        private String category_name;
        private String description;
        private String price;
        private String cancel_percentage;
        private String currency;
        private AdditionalPriceBean additional_price;
        private String address;
        private String latitude;
        private String longitude;
        private PreferencesBean preferences;
        private BankDetailBean bank_detail;
        private int is_wishlisted;
        private String borrower_pic;
        private String borrower;
        private String pause_status;
        private String total_earnings;
        private int total_booking;
        private int today_booking;
        private String delivery_date;
        private String return_date;
        private String country_code;
        private String phone_number;
        private String borrower_country_code;
        private String borrower_phone_number;
        private List<String> rules;
        private List<String> aminities;
        private String access_token;
        private String device_token;
        private String platform_status;
        private List<AttachmentModel> attachment;
        private int borrower_id;
        private String borrower_platform_status;
        private int admin_pause_status;
        private String borrower_access_token;
        private String borrower_device_token;
        private String transaction_percentage;
        private int buffer_status;
        private String discount;
        private String check_in;
        private String check_out;
        private String security;
        private String extra_fee;
        private String discount_percentage;
        private int discount_days;
        private int product_type;

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.user_id = in.readInt();
            this.rating = in.readInt();
            this.owner = in.readString();
            this.owner_pic = in.readString();
            this.category_id = in.readInt();
            this.category_name = in.readString();
            this.description = in.readString();
            this.price = in.readString();
            this.cancel_percentage = in.readString();
            this.currency = in.readString();
            this.additional_price = in.readParcelable(AdditionalPriceBean.class.getClassLoader());
            this.address = in.readString();
            this.latitude = in.readString();
            this.longitude = in.readString();
            this.preferences = in.readParcelable(PreferencesBean.class.getClassLoader());
            this.bank_detail = in.readParcelable(BankDetailBean.class.getClassLoader());
            this.is_wishlisted = in.readInt();
            this.borrower_pic = in.readString();
            this.borrower = in.readString();
            this.pause_status = in.readString();
            this.total_earnings = in.readString();
            this.total_booking = in.readInt();
            this.today_booking = in.readInt();
            this.delivery_date = in.readString();
            this.return_date = in.readString();
            this.country_code = in.readString();
            this.phone_number = in.readString();
            this.borrower_country_code = in.readString();
            this.borrower_phone_number = in.readString();
            this.rules = in.createStringArrayList();
            this.aminities = in.createStringArrayList();
            this.access_token = in.readString();
            this.device_token = in.readString();
            this.platform_status = in.readString();
            this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
            this.borrower_id = in.readInt();
            this.borrower_platform_status = in.readString();
            this.admin_pause_status = in.readInt();
            this.borrower_access_token = in.readString();
            this.borrower_device_token = in.readString();
            this.transaction_percentage = in.readString();
            this.buffer_status = in.readInt();
            this.discount = in.readString();
            this.check_in = in.readString();
            this.check_out = in.readString();
            this.security = in.readString();
            this.extra_fee = in.readString();
            this.discount_percentage = in.readString();
            this.discount_days = in.readInt();
            this.product_type = in.readInt();
        }

        public int getAdmin_pause_status() {
            return admin_pause_status;
        }

        public void setAdmin_pause_status(int admin_pause_status) {
            this.admin_pause_status = admin_pause_status;
        }

        public int getBuffer_status() {
            return buffer_status;
        }

        public void setBuffer_status(int buffer_status) {
            this.buffer_status = buffer_status;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getTransaction_percentage() {
            return transaction_percentage;
        }

        public void setTransaction_percentage(String transaction_percentage) {
            this.transaction_percentage = transaction_percentage;
        }

        public String getCheck_in() {
            return check_in;
        }

        public void setCheck_in(String check_in) {
            this.check_in = check_in;
        }

        public String getCheck_out() {
            return check_out;
        }

        public void setCheck_out(String check_out) {
            this.check_out = check_out;
        }

        public String getSecurity() {
            return security;
        }

        public void setSecurity(String security) {
            this.security = security;
        }

        public String getExtra_fee() {
            return extra_fee;
        }

        public void setExtra_fee(String extra_fee) {
            this.extra_fee = extra_fee;
        }

        public String getDiscount_percentage() {
            return discount_percentage;
        }

        public void setDiscount_percentage(String discount_percentage) {
            this.discount_percentage = discount_percentage;
        }

        public int getDiscount_days() {
            return discount_days;
        }

        public void setDiscount_days(int discount_days) {
            this.discount_days = discount_days;
        }

        public int getProduct_type() {
            return product_type;
        }

        public void setProduct_type(int product_type) {
            this.product_type = product_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getOwner_pic() {
            return owner_pic;
        }

        public void setOwner_pic(String owner_pic) {
            this.owner_pic = owner_pic;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getPause_status() {
            return pause_status;
        }

        public void setPause_status(String pause_status) {
            this.pause_status = pause_status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCancel_percentage() {
            return cancel_percentage;
        }

        public void setCancel_percentage(String cancel_percentage) {
            this.cancel_percentage = cancel_percentage;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public AdditionalPriceBean getAdditional_price() {
            return additional_price;
        }

        public void setAdditional_price(AdditionalPriceBean additional_price) {
            this.additional_price = additional_price;

        }

        public int is_wishlisted() {
            return is_wishlisted;
        }

        public void setIs_wishlisted(int is_wishlisted) {
            this.is_wishlisted = is_wishlisted;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLatitute() {
            return latitude;
        }

        public void setLatitute(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public PreferencesBean getPreferences() {
            return preferences;
        }

        public void setPreferences(PreferencesBean preferences) {
            this.preferences = preferences;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getBorrower_country_code() {
            return borrower_country_code;
        }

        public void setBorrower_country_code(String borrower_country_code) {
            this.borrower_country_code = borrower_country_code;
        }

        public String getBorrower_phone_number() {
            return borrower_phone_number;
        }

        public void setBorrower_phone_number(String borrower_phone_number) {
            this.borrower_phone_number = borrower_phone_number;
        }

        public BankDetailBean getBank_detail() {
            return bank_detail;
        }

        public void setBank_detail(BankDetailBean bank_detail) {
            this.bank_detail = bank_detail;
        }

        public String getBorrower_pic() {
            return borrower_pic;
        }

        public void setBorrower_pic(String borrower_pic) {
            this.borrower_pic = borrower_pic;
        }

        public String getBorrower() {
            return borrower;
        }

        public void setBorrower(String borrower) {
            this.borrower = borrower;
        }

        public String getTotal_earnings() {
            return total_earnings;
        }

        public void setTotal_earnings(String total_earnings) {
            this.total_earnings = total_earnings;
        }

        public int getTotal_booking() {
            return total_booking;
        }

        public void setTotal_booking(int total_booking) {
            this.total_booking = total_booking;
        }

        public int getToday_booking() {
            return today_booking;
        }

        public void setToday_booking(int today_booking) {
            this.today_booking = today_booking;
        }

        public String getDelivery_date() {
            return delivery_date;
        }

        public void setDelivery_date(String delivery_date) {
            this.delivery_date = delivery_date;
        }

        public String getReturn_date() {
            return return_date;
        }

        public void setReturn_date(String return_date) {
            this.return_date = return_date;
        }

        public List<String> getRules() {
            return rules;
        }

        public void setRules(List<String> rules) {
            this.rules = rules;
        }

        public List<String> getAminities() {
            return aminities;
        }

        public void setAminities(List<String> aminities) {
            this.aminities = aminities;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getPlatform_status() {
            return platform_status;
        }

        public void setPlatform_status(String platform_status) {
            this.platform_status = platform_status;
        }

        public int getBorrower_id() {
            return borrower_id;
        }

        public void setBorrower_id(int borrower_id) {
            this.borrower_id = borrower_id;
        }

        public String getBorrower_platform_status() {
            return borrower_platform_status;
        }

        public void setBorrower_platform_status(String borrower_platform_status) {
            this.borrower_platform_status = borrower_platform_status;
        }

        public String getBorrower_access_token() {
            return borrower_access_token;
        }

        public void setBorrower_access_token(String borrower_access_token) {
            this.borrower_access_token = borrower_access_token;
        }

        public String getBorrower_device_token() {
            return borrower_device_token;
        }

        public void setBorrower_device_token(String borrower_device_token) {
            this.borrower_device_token = borrower_device_token;
        }

        public List<AttachmentModel> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentModel> attachment) {
            this.attachment = attachment;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeInt(this.user_id);
            dest.writeInt(this.rating);
            dest.writeString(this.owner);
            dest.writeString(this.owner_pic);
            dest.writeInt(this.category_id);
            dest.writeString(this.category_name);
            dest.writeString(this.description);
            dest.writeString(this.price);
            dest.writeString(this.cancel_percentage);
            dest.writeString(this.currency);
            dest.writeParcelable(this.additional_price, flags);
            dest.writeString(this.address);
            dest.writeString(this.latitude);
            dest.writeString(this.longitude);
            dest.writeParcelable(this.preferences, flags);
            dest.writeParcelable(this.bank_detail, flags);
            dest.writeInt(this.is_wishlisted);
            dest.writeString(this.borrower_pic);
            dest.writeString(this.borrower);
            dest.writeString(this.pause_status);
            dest.writeString(this.total_earnings);
            dest.writeInt(this.total_booking);
            dest.writeInt(this.today_booking);
            dest.writeString(this.delivery_date);
            dest.writeString(this.return_date);
            dest.writeString(this.country_code);
            dest.writeString(this.phone_number);
            dest.writeString(this.borrower_country_code);
            dest.writeString(this.borrower_phone_number);
            dest.writeStringList(this.rules);
            dest.writeStringList(this.aminities);
            dest.writeString(this.access_token);
            dest.writeString(this.device_token);
            dest.writeString(this.platform_status);
            dest.writeTypedList(this.attachment);
            dest.writeInt(this.borrower_id);
            dest.writeString(this.borrower_platform_status);
            dest.writeInt(this.admin_pause_status);
            dest.writeString(this.borrower_access_token);
            dest.writeString(this.borrower_device_token);
            dest.writeString(this.transaction_percentage);
            dest.writeInt(this.buffer_status);
            dest.writeString(this.discount);
            dest.writeString(this.check_in);
            dest.writeString(this.check_out);
            dest.writeString(this.security);
            dest.writeString(this.extra_fee);
            dest.writeString(this.discount_percentage);
            dest.writeInt(this.discount_days);
            dest.writeInt(this.product_type);
        }

        public static class AdditionalPriceBean implements Parcelable {
            /**
             * security_charges : 566
             * fee : 566
             */

            private String security_charges;
            private String fee;
            private String discount_percentage;
            private String discount_days;
            private String price;
            private String currency;

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }


            public AdditionalPriceBean() {
            }

            public String getDiscount_percentage() {
                return discount_percentage;
            }

            public void setDiscount_percentage(String discount_percentage) {
                this.discount_percentage = discount_percentage;
            }

            public String getDiscount_days() {
                return discount_days;
            }

            public void setDiscount_days(String discount_days) {
                this.discount_days = discount_days;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSecurity_charges() {
                return security_charges;
            }

            public void setSecurity_charges(String security_charges) {
                this.security_charges = security_charges;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.security_charges);
                dest.writeString(this.fee);
                dest.writeString(this.discount_percentage);
                dest.writeString(this.discount_days);
                dest.writeString(this.price);
                dest.writeString(this.currency);
            }

            protected AdditionalPriceBean(Parcel in) {
                this.security_charges = in.readString();
                this.fee = in.readString();
                this.discount_percentage = in.readString();
                this.discount_days = in.readString();
                this.price = in.readString();
                this.currency = in.readString();
            }

            public static final Creator<AdditionalPriceBean> CREATOR = new Creator<AdditionalPriceBean>() {
                @Override
                public AdditionalPriceBean createFromParcel(Parcel source) {
                    return new AdditionalPriceBean(source);
                }

                @Override
                public AdditionalPriceBean[] newArray(int size) {
                    return new AdditionalPriceBean[size];
                }
            };
        }

        public static class PreferencesBean implements Parcelable {
            public static final Creator<PreferencesBean> CREATOR = new Creator<PreferencesBean>() {
                @Override
                public PreferencesBean createFromParcel(Parcel source) {
                    return new PreferencesBean(source);
                }

                @Override
                public PreferencesBean[] newArray(int size) {
                    return new PreferencesBean[size];
                }
            };
            /**
             * available : Only Weekends
             * start_time : 12:36 PM
             * end_time : 12:37 PM
             * tull_delivery : 0
             * delivery_charges : null
             * delivery_start_time : null
             * delivery_end_time : null
             */

            private String available;
            private String start_time;
            private String end_time;

            public PreferencesBean() {
            }

            protected PreferencesBean(Parcel in) {
                this.available = in.readString();
                this.start_time = in.readString();
                this.end_time = in.readString();
            }

            public String getAvailable() {
                return available;
            }

            public void setAvailable(String available) {
                this.available = available;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.available);
                dest.writeString(this.start_time);
                dest.writeString(this.end_time);
            }
        }

        public static class BankDetailBean implements Parcelable {
            public static final Creator<BankDetailBean> CREATOR = new Creator<BankDetailBean>() {
                @Override
                public BankDetailBean createFromParcel(Parcel source) {
                    return new BankDetailBean(source);
                }

                @Override
                public BankDetailBean[] newArray(int size) {
                    return new BankDetailBean[size];
                }
            };
            /**
             * id : 20
             * user_id : 7
             * product_id : 24
             * bank_name : STRIPE TEST BANK
             * account_number : 00012345
             * strip_account : acct_1BGizcBSg20GFTtn
             * postal_code : Ca103
             * dob : 25-10-2004
             * currency : GBP
             * country_code : GB
             * city : Stsy
             * address : Ysusy
             * first_name : Rajat
             * last_name : Arora
             * state : Syysy
             * sort_code : 108800
             */

            private int id;
            private int user_id;
            private int product_id;
            private String bank_name;
            private String account_number;
            private String strip_account;
            private String postal_code;
            private String dob;
            private String currency;
            private String country_code;
            private String city;
            private String address;
            private String first_name;
            private String last_name;
            private String state;
            private String sort_code;
            private String swift;
            private String account_type;

            public BankDetailBean() {
            }

            protected BankDetailBean(Parcel in) {
                this.id = in.readInt();
                this.user_id = in.readInt();
                this.product_id = in.readInt();
                this.bank_name = in.readString();
                this.account_number = in.readString();
                this.strip_account = in.readString();
                this.postal_code = in.readString();
                this.dob = in.readString();
                this.currency = in.readString();
                this.country_code = in.readString();
                this.city = in.readString();
                this.address = in.readString();
                this.first_name = in.readString();
                this.last_name = in.readString();
                this.state = in.readString();
                this.sort_code = in.readString();
                this.swift = in.readString();
                this.account_type = in.readString();
            }

            public String getSwift() {
                return swift;
            }

            public void setSwift(String swift) {
                this.swift = swift;
            }

            public String getAccount_type() {
                return account_type;
            }

            public void setAccount_type(String account_type) {
                this.account_type = account_type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getAccount_number() {
                return account_number;
            }

            public void setAccount_number(String account_number) {
                this.account_number = account_number;
            }

            public String getStrip_account() {
                return strip_account;
            }

            public void setStrip_account(String strip_account) {
                this.strip_account = strip_account;
            }

            public String getPostal_code() {
                return postal_code;
            }

            public void setPostal_code(String postal_code) {
                this.postal_code = postal_code;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getCountry_code() {
                return country_code;
            }

            public void setCountry_code(String country_code) {
                this.country_code = country_code;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getSort_code() {
                return sort_code;
            }

            public void setSort_code(String sort_code) {
                this.sort_code = sort_code;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeInt(this.user_id);
                dest.writeInt(this.product_id);
                dest.writeString(this.bank_name);
                dest.writeString(this.account_number);
                dest.writeString(this.strip_account);
                dest.writeString(this.postal_code);
                dest.writeString(this.dob);
                dest.writeString(this.currency);
                dest.writeString(this.country_code);
                dest.writeString(this.city);
                dest.writeString(this.address);
                dest.writeString(this.first_name);
                dest.writeString(this.last_name);
                dest.writeString(this.state);
                dest.writeString(this.sort_code);
                dest.writeString(this.swift);
                dest.writeString(this.account_type);
            }
        }
    }
}
