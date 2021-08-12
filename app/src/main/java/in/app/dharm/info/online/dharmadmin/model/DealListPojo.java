package in.app.dharm.info.online.dharmadmin.model;

public class DealListPojo {
    String cartoon, deal_amt, deal_in_date, pId, status, user;

    public DealListPojo(String cartoon, String deal_amt, String deal_in_date, String pId, String status, String user) {
        this.cartoon = cartoon;
        this.deal_amt = deal_amt;
        this.deal_in_date = deal_in_date;
        this.pId = pId;
        this.status = status;
        this.user = user;
    }

    public String getCartoon() {
        return cartoon;
    }

    public void setCartoon(String cartoon) {
        this.cartoon = cartoon;
    }

    public String getDeal_amt() {
        return deal_amt;
    }

    public void setDeal_amt(String deal_amt) {
        this.deal_amt = deal_amt;
    }

    public String getDeal_in_date() {
        return deal_in_date;
    }

    public void setDeal_in_date(String deal_in_date) {
        this.deal_in_date = deal_in_date;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
