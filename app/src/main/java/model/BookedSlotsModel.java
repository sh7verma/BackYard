package model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 3/2/18.
 */

public class BookedSlotsModel extends BaseModel implements Parcelable {


    /**
     * response : [{"check_in":"22:00","check_out":"23:00"}]
     * previous :
     * next : 07:00
     * buffer_time : 3
     * code : 201
     */

    private String previous;
    private String next;
    private int buffer_time;
    private int code;
    private List<ResponseBean> response;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getBuffer_time() {
        return buffer_time;
    }

    public void setBuffer_time(int buffer_time) {
        this.buffer_time = buffer_time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * check_in : 22:00
         * check_out : 23:00
         */

        private String check_in;
        private String check_out;

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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.previous);
        dest.writeString(this.next);
        dest.writeInt(this.buffer_time);
        dest.writeInt(this.code);
        dest.writeList(this.response);
    }

    public BookedSlotsModel() {
    }

    protected BookedSlotsModel(Parcel in) {
        this.previous = in.readString();
        this.next = in.readString();
        this.buffer_time = in.readInt();
        this.code = in.readInt();
        this.response = new ArrayList<ResponseBean>();
        in.readList(this.response, ResponseBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<BookedSlotsModel> CREATOR = new Parcelable.Creator<BookedSlotsModel>() {
        @Override
        public BookedSlotsModel createFromParcel(Parcel source) {
            return new BookedSlotsModel(source);
        }

        @Override
        public BookedSlotsModel[] newArray(int size) {
            return new BookedSlotsModel[size];
        }
    };
}
