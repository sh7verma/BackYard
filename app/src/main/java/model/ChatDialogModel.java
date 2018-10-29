package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by applify on 8/3/2017.
 */

public class ChatDialogModel implements Parcelable {

    private HashMap<String, String> access_token;
    private String chat_dialog_id;
    private String last_message;
    private String last_message_id;
    private String last_message_sender_id;
    private String last_message_time;
    private String participant_ids;
    private String dialog_created_time;
    private HashMap<String, String> delete_dialog_time;
    private HashMap<String, String> platform_status;
    private HashMap<String, String> profile_pic;
    private HashMap<String, String> push_token;
    private HashMap<String, String> unread_count;
    private HashMap<String, String> name;
    private HashMap<String, String> mute;
    private Map<String, String> delete_outer_dialog;

    public ChatDialogModel() {

    }

    public HashMap<String, String> getBlock_status() {
        return block_status;
    }

    public void setBlock_status(HashMap<String, String> block_status) {
        this.block_status = block_status;
    }

    private HashMap<String, String> block_status;

    public HashMap<String, String> getMute() {
        return mute;
    }

    public void setMute(HashMap<String, String> mute) {
        this.mute = mute;
    }

    public HashMap<String, String> getName() {
        return name;
    }

    public void setName(HashMap<String, String> name) {
        this.name = name;
    }

    public String getDialog_created_time() {
        return dialog_created_time;
    }

    public void setDialog_created_time(String dialog_created_time) {
        this.dialog_created_time = dialog_created_time;
    }

    public HashMap<String, String> getAccess_token() {
        return access_token;
    }

    public void setAccess_token(HashMap<String, String> access_token) {
        this.access_token = access_token;
    }

    public String getChat_dialog_id() {
        return chat_dialog_id;
    }

    public void setChat_dialog_id(String chat_dialog_id) {
        this.chat_dialog_id = chat_dialog_id;
    }


    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_message_id() {
        return last_message_id;
    }

    public void setLast_message_id(String last_message_id) {
        this.last_message_id = last_message_id;
    }

    public String getLast_message_sender_id() {
        return last_message_sender_id;
    }

    public void setLast_message_sender_id(String last_message_sender_id) {
        this.last_message_sender_id = last_message_sender_id;
    }

    public String getLast_message_time() {
        return last_message_time;
    }

    public void setLast_message_time(String last_message_time) {
        this.last_message_time = last_message_time;
    }

    public String getParticipant_ids() {
        return participant_ids;
    }

    public void setParticipant_ids(String participant_ids) {
        this.participant_ids = participant_ids;
    }

    public HashMap<String, String> getDelete_dialog_time() {
        return delete_dialog_time;
    }

    public void setDelete_dialog_time(HashMap<String, String> delete_dialog_time) {
        this.delete_dialog_time = delete_dialog_time;
    }

    public HashMap<String, String> getPlatform_status() {
        return platform_status;
    }

    public void setPlatform_status(HashMap<String, String> platform_status) {
        this.platform_status = platform_status;
    }

    public HashMap<String, String> getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(HashMap<String, String> profile_pic) {
        this.profile_pic = profile_pic;
    }

    public Map<String, String> getDelete_outer_dialog() {
        return delete_outer_dialog;
    }

    public void setDelete_outer_dialog(Map<String, String> delete_outer_dialog) {
        this.delete_outer_dialog = delete_outer_dialog;
    }

    public HashMap<String, String> getPush_token() {
        return push_token;
    }

    public void setPush_token(HashMap<String, String> push_token) {
        this.push_token = push_token;
    }

    public HashMap<String, String> getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(HashMap<String, String> unread_count) {
        this.unread_count = unread_count;
    }

    public static ChatDialogModel parseChat(DataSnapshot dataSnapshot) {
        dataSnapshot.getValue();
        ChatDialogModel msg = new ChatDialogModel();
        msg.setChat_dialog_id(dataSnapshot.child("chat_dialog_id").getValue(String.class));
        return msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.access_token);
        dest.writeString(this.chat_dialog_id);
        dest.writeString(this.last_message);
        dest.writeString(this.last_message_id);
        dest.writeString(this.last_message_sender_id);
        dest.writeString(this.last_message_time);
        dest.writeString(this.participant_ids);
        dest.writeString(this.dialog_created_time);
        dest.writeSerializable(this.delete_dialog_time);
        dest.writeSerializable(this.platform_status);
        dest.writeSerializable(this.profile_pic);
        dest.writeSerializable(this.push_token);
        dest.writeSerializable(this.unread_count);
        dest.writeSerializable(this.name);
        dest.writeSerializable(this.mute);
        dest.writeInt(this.delete_outer_dialog.size());
        for (Map.Entry<String, String> entry : this.delete_outer_dialog.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeSerializable(this.block_status);
    }

    protected ChatDialogModel(Parcel in) {
        this.access_token = (HashMap<String, String>) in.readSerializable();
        this.chat_dialog_id = in.readString();
        this.last_message = in.readString();
        this.last_message_id = in.readString();
        this.last_message_sender_id = in.readString();
        this.last_message_time = in.readString();
        this.participant_ids = in.readString();
        this.dialog_created_time = in.readString();
        this.delete_dialog_time = (HashMap<String, String>) in.readSerializable();
        this.platform_status = (HashMap<String, String>) in.readSerializable();
        this.profile_pic = (HashMap<String, String>) in.readSerializable();
        this.push_token = (HashMap<String, String>) in.readSerializable();
        this.unread_count = (HashMap<String, String>) in.readSerializable();
        this.name = (HashMap<String, String>) in.readSerializable();
        this.mute = (HashMap<String, String>) in.readSerializable();
        int delete_outer_dialogSize = in.readInt();
        this.delete_outer_dialog = new HashMap<String, String>(delete_outer_dialogSize);
        for (int i = 0; i < delete_outer_dialogSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.delete_outer_dialog.put(key, value);
        }
        this.block_status = (HashMap<String, String>) in.readSerializable();
    }

    public static final Creator<ChatDialogModel> CREATOR = new Creator<ChatDialogModel>() {
        @Override
        public ChatDialogModel createFromParcel(Parcel source) {
            return new ChatDialogModel(source);
        }

        @Override
        public ChatDialogModel[] newArray(int size) {
            return new ChatDialogModel[size];
        }
    };
}
