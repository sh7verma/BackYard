package model;


import java.util.List;

public class CreateTulModel extends BaseModel{

    /**
     * response : {"id":24,"title":"Shhshs","user_id":7,"owner":"Rajat","category_id":5,"description":"Xghsh","price":"76","currency":"£","additional_price":{"security_charges":"566","fee":"566"},"address":"Unnamed Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,null,Punjab,India","latitute":"","longitude":"76.7328227","rules":["Hzhz"],"aminities":["Hzhz$#$TUL$#"],"preferences":{"available":"Only Weekends","start_time":"12:36 PM","end_time":"12:37 PM","tull_delivery":"0","delivery_charges":null,"delivery_start_time":null,"delivery_end_time":null},"bank_detail":{"id":20,"user_id":7,"product_id":24,"bank_name":"STRIPE TEST BANK","account_number":"00012345","strip_account":"acct_1BGizcBSg20GFTtn","postal_code":"Ca103","dob":"25-10-2004","currency":"GBP","country_code":"GB","city":"Stsy","address":"Ysusy","first_name":"Rajat","last_name":"Arora","state":"Syysy","sort_code":"108800"},"attachment":[{"id":31,"product_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg","type":"image"}]}
     * code : 403
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

    public static class ResponseBean {
        /**
         * id : 24
         * title : Shhshs
         * user_id : 7
         * owner : Rajat
         * category_id : 5
         * description : Xghsh
         * price : 76
         * currency : £
         * additional_price : {"security_charges":"566","fee":"566"}
         * address : Unnamed Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,null,Punjab,India
         * latitute :
         * longitude : 76.7328227
         * rules : ["Hzhz"]
         * aminities : ["Hzhz$#$TUL$#"]
         * preferences : {"available":"Only Weekends","start_time":"12:36 PM","end_time":"12:37 PM","tull_delivery":"0","delivery_charges":null,"delivery_start_time":null,"delivery_end_time":null}
         * bank_detail : {"id":20,"user_id":7,"product_id":24,"bank_name":"STRIPE TEST BANK","account_number":"00012345","strip_account":"acct_1BGizcBSg20GFTtn","postal_code":"Ca103","dob":"25-10-2004","currency":"GBP","country_code":"GB","city":"Stsy","address":"Ysusy","first_name":"Rajat","last_name":"Arora","state":"Syysy","sort_code":"108800"}
         * attachment : [{"id":31,"product_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg","type":"image"}]
         */

        private int id;
        private String title;
        private int user_id;
        private String owner;
        private int category_id;
        private String description;
        private String price;
        private String currency;
        private AdditionalPriceBean additional_price;
        private String address;
        private String latitute;
        private String longitude;
        private PreferencesBean preferences;
        private BankDetailBean bank_detail;
        private List<String> rules;
        private List<String> aminities;
        private List<AttachmentBean> attachment;

        private String check_in;
        private String check_out;
        private String security;
        private String extra_fee;
        private String discount_percentage;
        private int discount_days;
        private int product_type;

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

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLatitute() {
            return latitute;
        }

        public void setLatitute(String latitute) {
            this.latitute = latitute;
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

        public BankDetailBean getBank_detail() {
            return bank_detail;
        }

        public void setBank_detail(BankDetailBean bank_detail) {
            this.bank_detail = bank_detail;
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

        public List<AttachmentBean> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentBean> attachment) {
            this.attachment = attachment;
        }

        public static class AdditionalPriceBean {
            /**
             * security_charges : 566
             * fee : 566
             */

            private String security_charges;
            private String fee;

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
        }

        public static class PreferencesBean {
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
            private String tull_delivery;
            private Object delivery_charges;
            private Object delivery_start_time;
            private Object delivery_end_time;

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

            public String getTull_delivery() {
                return tull_delivery;
            }

            public void setTull_delivery(String tull_delivery) {
                this.tull_delivery = tull_delivery;
            }

            public Object getDelivery_charges() {
                return delivery_charges;
            }

            public void setDelivery_charges(Object delivery_charges) {
                this.delivery_charges = delivery_charges;
            }

            public Object getDelivery_start_time() {
                return delivery_start_time;
            }

            public void setDelivery_start_time(Object delivery_start_time) {
                this.delivery_start_time = delivery_start_time;
            }

            public Object getDelivery_end_time() {
                return delivery_end_time;
            }

            public void setDelivery_end_time(Object delivery_end_time) {
                this.delivery_end_time = delivery_end_time;
            }
        }

        public static class BankDetailBean {
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
        }

        public static class AttachmentBean {
            /**
             * id : 31
             * product_id : 24
             * attachment : https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg
             * thumbnail : https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg
             * type : image
             */

            private int id;
            private int product_id;
            private String attachment;
            private String thumbnail;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getAttachment() {
                return attachment;
            }

            public void setAttachment(String attachment) {
                this.attachment = attachment;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
