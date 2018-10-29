package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dev on 3/2/18.
 */

public class TransacrionPercentageModel extends BaseModel implements Parcelable {

    /**
     * response : 10
     * code : 111
     */

    private String response;
    private int code;
    private int is_email_skip;
    private int email_verify;
    private int block_status;

    public int getIs_email_skip() {
        return is_email_skip;
    }

    public void setIs_email_skip(int is_email_skip) {
        this.is_email_skip = is_email_skip;
    }

    public int getEmail_verify() {
        return email_verify;
    }

    public void setEmail_verify(int email_verify) {
        this.email_verify = email_verify;
    }

    public int getBlock_status() {
        return block_status;
    }

    public void setBlock_status(int block_status) {
        this.block_status = block_status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TransacrionPercentageModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.response);
        dest.writeInt(this.code);
        dest.writeInt(this.is_email_skip);
        dest.writeInt(this.email_verify);
        dest.writeInt(this.block_status);
    }

    protected TransacrionPercentageModel(Parcel in) {
        this.response = in.readString();
        this.code = in.readInt();
        this.is_email_skip = in.readInt();
        this.email_verify = in.readInt();
        this.block_status = in.readInt();
    }

    public static final Creator<TransacrionPercentageModel> CREATOR = new Creator<TransacrionPercentageModel>() {
        @Override
        public TransacrionPercentageModel createFromParcel(Parcel source) {
            return new TransacrionPercentageModel(source);
        }

        @Override
        public TransacrionPercentageModel[] newArray(int size) {
            return new TransacrionPercentageModel[size];
        }
    };
}
