package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by applify on 10/18/2017.
 */

public class ProfileModel extends BaseModel {


    /**
     * response : {"id":7,"status":2,"platform_status":2,"user_type":1,"access_token":"15f9c02cd9247a236dfa3027dbc7eaa2","email":"rajatarora+15@applify.co","username":"Rajat","phone_number":"9888454536","country_code":"+44","user_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/profile_pic/1508844782.jpg","facebook_id":null,"otp":1111,"product":[{"id":24,"title":"Shhshs","user_id":7,"price":"76","attachment":[{"id":31,"product_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg","type":"image"}]}],"wishlist":[],"product_count":1,"wishlist_count":0}
     * code : 402
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
         * id : 7
         * status : 2
         * platform_status : 2
         * user_type : 1
         * access_token : 15f9c02cd9247a236dfa3027dbc7eaa2
         * email : rajatarora+15@applify.co
         * username : Rajat
         * phone_number : 9888454536
         * country_code : +44
         * user_pic : https://s3.ap-south-1.amazonaws.com/kittydev/backyard/profile_pic/1508844782.jpg
         * facebook_id : null
         * otp : 1111
         * product : [{"id":24,"title":"Shhshs","user_id":7,"price":"76","attachment":[{"id":31,"product_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg","type":"image"}]}]
         * wishlist : []
         * product_count : 1
         * wishlist_count : 0
         */

        private int id;
        private int status;
        private int platform_status;
        private int user_type;
        private String access_token;
        private String email;
        private String username;
        private String phone_number;
        private String country_code;
        private String user_pic;
        private Object facebook_id;
        private int otp;
        private int product_count;
        private int wishlist_count;
        private List<ProductBean> product;
        private List<NearByTulListingModel.ResponseBean> wishlist;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPlatform_status() {
            return platform_status;
        }

        public void setPlatform_status(int platform_status) {
            this.platform_status = platform_status;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public Object getFacebook_id() {
            return facebook_id;
        }

        public void setFacebook_id(Object facebook_id) {
            this.facebook_id = facebook_id;
        }

        public int getOtp() {
            return otp;
        }

        public void setOtp(int otp) {
            this.otp = otp;
        }

        public int getProduct_count() {
            return product_count;
        }

        public void setProduct_count(int product_count) {
            this.product_count = product_count;
        }

        public int getWishlist_count() {
            return wishlist_count;
        }

        public void setWishlist_count(int wishlist_count) {
            this.wishlist_count = wishlist_count;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public List<NearByTulListingModel.ResponseBean> getWishlist() {
            return wishlist;
        }

        public void setWishlist(List<NearByTulListingModel.ResponseBean> wishlist) {
            this.wishlist = wishlist;
        }

        public static class ProductBean implements Parcelable {
            /**
             * id : 24
             * title : Shhshs
             * user_id : 7
             * price : 76
             * attachment : [{"id":31,"product_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools/15089153410.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/backyard/tools_thumbnail/1508915341_thumb0.jpg","type":"image"}]
             */

            private int id;
            private String title;
            private int user_id;
            private String price;
            private List<AttachmentModel> attachment;
            private String product_type;
            private String transaction_percentage;

            public String getTransaction_percentage() {
                return transaction_percentage;
            }

            public void setTransaction_percentage(String transaction_percentage) {
                this.transaction_percentage = transaction_percentage;
            }

            public String getProduct_type() {
                return product_type;
            }

            public void setProduct_type(String product_type) {
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public List<AttachmentModel> getAttachment() {
                return attachment;
            }

            public void setAttachment(List<AttachmentModel> attachment) {
                this.attachment = attachment;
            }


            public ProductBean() {
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
                dest.writeString(this.price);
                dest.writeTypedList(this.attachment);
                dest.writeString(this.product_type);
                dest.writeString(this.transaction_percentage);
            }

            protected ProductBean(Parcel in) {
                this.id = in.readInt();
                this.title = in.readString();
                this.user_id = in.readInt();
                this.price = in.readString();
                this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
                this.product_type = in.readString();
                this.transaction_percentage = in.readString();
            }

            public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
                @Override
                public ProductBean createFromParcel(Parcel source) {
                    return new ProductBean(source);
                }

                @Override
                public ProductBean[] newArray(int size) {
                    return new ProductBean[size];
                }
            };
        }
    }
}
