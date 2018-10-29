package model;

/**
 * Created by dev on 20/9/18.
 */

public class DeleteAccountModel extends BaseModel {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public class Response {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
