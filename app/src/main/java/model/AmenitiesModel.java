package model;

import java.util.List;

/**
 * Created by dev on 16/10/17.
 */

public class AmenitiesModel extends BaseModel {

    /**
     * response : [{"id":1,"category_id":1,"amenity_name":"Dining Table"},{"id":2,"category_id":1,"amenity_name":"Coffee Table"},{"id":3,"category_id":1,"amenity_name":"Chairs"},{"id":4,"category_id":1,"amenity_name":"Sunbeds"},{"id":5,"category_id":1,"amenity_name":"Garden gazebo / parasol"},{"id":6,"category_id":1,"amenity_name":"Charcoal BBQ grill"},{"id":7,"category_id":1,"amenity_name":"Gas BBQ grill"},{"id":8,"category_id":1,"amenity_name":"Electric BBQ grill"},{"id":9,"category_id":1,"amenity_name":"Kitchen cooking hobs"},{"id":10,"category_id":1,"amenity_name":"Plates, glasses and cutlery"},{"id":11,"category_id":1,"amenity_name":"Fridge and freezer"},{"id":12,"category_id":1,"amenity_name":"Bathroom"},{"id":13,"category_id":1,"amenity_name":"Loudspeakers"},{"id":14,"category_id":1,"amenity_name":"Wi-fi"},{"id":15,"category_id":1,"amenity_name":"Parking"},{"id":18,"category_id":2,"amenity_name":"Dining Table"},{"id":19,"category_id":2,"amenity_name":"Coffee Table"},{"id":20,"category_id":2,"amenity_name":"Chairs"},{"id":21,"category_id":2,"amenity_name":"Sunbeds"},{"id":22,"category_id":2,"amenity_name":"Garden gazebo / parasol"},{"id":23,"category_id":2,"amenity_name":"Charcoal BBQ grill"},{"id":24,"category_id":2,"amenity_name":"Gas BBQ grill"},{"id":25,"category_id":2,"amenity_name":"Electric BBQ grill"},{"id":26,"category_id":2,"amenity_name":"Kitchen cooking hobs"},{"id":27,"category_id":2,"amenity_name":"Plates, glasses and cutlery"},{"id":28,"category_id":2,"amenity_name":"Fridge and freezer"},{"id":29,"category_id":2,"amenity_name":"Bathroom"},{"id":30,"category_id":2,"amenity_name":"Loudspeakers"},{"id":31,"category_id":2,"amenity_name":"Wi-fi"},{"id":32,"category_id":2,"amenity_name":"Parking"},{"id":33,"category_id":2,"amenity_name":"Kids pool toys"},{"id":34,"category_id":2,"amenity_name":"Towels"},{"id":35,"category_id":2,"amenity_name":"Shower"},{"id":36,"category_id":2,"amenity_name":"Changing room"},{"id":38,"category_id":6,"amenity_name":"Dining Table"},{"id":39,"category_id":6,"amenity_name":"Coffee Table"},{"id":40,"category_id":6,"amenity_name":"Chairs"},{"id":41,"category_id":6,"amenity_name":"Sunbeds"},{"id":42,"category_id":6,"amenity_name":"Garden gazebo / parasol"},{"id":43,"category_id":6,"amenity_name":"Charcoal BBQ grill"},{"id":44,"category_id":6,"amenity_name":"Gas BBQ grill"},{"id":45,"category_id":6,"amenity_name":"Electric BBQ grill"},{"id":46,"category_id":6,"amenity_name":"Kitchen cooking hobs"},{"id":47,"category_id":6,"amenity_name":"Plates, glasses and cutlery"},{"id":48,"category_id":6,"amenity_name":"Fridge and freezer"},{"id":49,"category_id":6,"amenity_name":"Bathroom"},{"id":50,"category_id":6,"amenity_name":"Loudspeakers"},{"id":51,"category_id":6,"amenity_name":"Wi-fi"},{"id":52,"category_id":6,"amenity_name":"Parking"},{"id":53,"category_id":6,"amenity_name":"Outdoor heaters"}]
     * code : 400
     */

    private int code;
    private List<ResponseBean> response;

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
         * id : 1
         * category_id : 1
         * amenity_name : Dining Table
         */

        private int id;
        private int category_id;
        private String amenity_name;
        private boolean checked;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getAmenity_name() {
            return amenity_name;
        }

        public void setAmenity_name(String amenity_name) {
            this.amenity_name = amenity_name;
        }
    }
}
