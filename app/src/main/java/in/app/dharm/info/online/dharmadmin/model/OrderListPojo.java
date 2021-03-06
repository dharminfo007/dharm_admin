package in.app.dharm.info.online.dharmadmin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderListPojo {

    public String order_total, user, id, order_status;
//    public ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> productArrayList = new ArrayList<HashMap<String,String>>();

    public OrderListPojo() {
    }

    public OrderListPojo(String id, String order_total, String user, String order_status, ArrayList<HashMap<String, String>> productArrayList) {
        this.id = id;
        this.order_total = order_total;
        this.user = user;
        this.order_status = order_status;
        this.productArrayList = productArrayList;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<HashMap<String, String>> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<HashMap<String, String>> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
