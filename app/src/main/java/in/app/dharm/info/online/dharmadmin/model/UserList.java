package in.app.dharm.info.online.dharmadmin.model;

public class UserList {
    String address, in_date, phone;

    public UserList(String address, String in_date, String phone) {
        this.address = address;
        this.in_date = in_date;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIn_date() {
        return in_date;
    }

    public void setIn_date(String in_date) {
        this.in_date = in_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
