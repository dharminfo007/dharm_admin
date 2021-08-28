package in.app.dharm.info.online.dharmadmin.model;

import java.util.ArrayList;

public class AllProductListPojo {
    //Add the type indicators here
    public static final int TEXT_TYPE = 0;
    public static final int IMAGE_TYPE = 0;

    String name;
    String description;
    String tvPiecesPerCartoon;
    String tvStock;
    String tvPrice;
    String in_date;
    String tvOfferDisc;
    String type;
    String id;
    boolean isFav ;
    public ArrayList<String> images;

    public AllProductListPojo() {
    }

    public AllProductListPojo(String name, String description, String tvPiecesPerCartoon, String tvStock,
                           String tvPrice, String in_date, String type, String id,
                           ArrayList<String> images) {
        this.name = name;
        this.description = description;
        this.tvPiecesPerCartoon = tvPiecesPerCartoon;
        this.tvStock = tvStock;
        this.tvPrice = tvPrice;
        this.in_date = in_date;
        this.tvOfferDisc = tvOfferDisc;
        this.type = type;
        this.id = id;
        this.images = images;
    }

    public AllProductListPojo(String name, String description, String tvPiecesPerCartoon, String tvStock,
                           String tvPrice,
                           String in_date, String type, String id, ArrayList<String> images,
                           boolean isFav) {
        this.name = name;
        this.description = description;
        this.tvPiecesPerCartoon = tvPiecesPerCartoon;
        this.tvStock = tvStock;
        this.tvPrice = tvPrice;
        this.in_date = in_date;
        this.type = type;
        this.id = id;
        this.images = images;
        this.isFav = isFav;
    }
    public AllProductListPojo(String tvPiecesPerCartoon, String tvStock,
                              String tvPrice) {
        this.tvPiecesPerCartoon = tvPiecesPerCartoon;
        this.tvStock = tvStock;
        this.tvPrice = tvPrice;
    }
    public String getTvDesc() {
        return description;
    }

    public void setTvDesc(String tvDesc) {
        this.description = tvDesc;
    }

    public String getTvPiecesPerCartoon() {
        return tvPiecesPerCartoon;
    }

    public void setTvPiecesPerCartoon(String tvPiecesPerCartoon) {
        this.tvPiecesPerCartoon = tvPiecesPerCartoon;
    }

    public String getTvStock() {
        return tvStock;
    }

    public void setTvStock(String tvStock) {
        this.tvStock = tvStock;
    }

    public String getTvPrice() {
        return tvPrice;
    }

    public void setTvPrice(String tvPrice) {
        this.tvPrice = tvPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIn_date() {
        return in_date;
    }

    public void setIn_date(String in_date) {
        this.in_date = in_date;
    }

    public String getTvOfferDisc() {
        return tvOfferDisc;
    }

    public void setTvOfferDisc(String tvOfferDisc) {
        this.tvOfferDisc = tvOfferDisc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getListProductImages() {
        return images;
    }

    public void setListProductImages(ArrayList<String> listProductImages) {
        this.images = listProductImages;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
