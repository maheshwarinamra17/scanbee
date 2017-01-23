package scanbee.models;

/**
 * Created by namra on 15/01/17.
 */

public class CartModel {

    private String mProductName;
    private String mProductPrice;
    private String mProductCode;
    private String mProductQty;
    private String mProductContent;

    public CartModel(String productName, String productQty, String productPrice, String productCode, String productContent) {
        mProductName = productName;
        mProductQty = productQty;
        mProductPrice = productPrice;
        mProductCode = productCode;
        mProductContent =  productContent;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public void setmProductQty(String mProductQty) {
        this.mProductQty = mProductQty;
    }

    public void setmProductPrice(String mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public void setmProductCode(String mProductCode) {
        this.mProductCode = mProductCode;
    }

    public String getmProductName() {
        return mProductName;
    }

    public String getmProductQty() {
        return mProductQty;
    }

    public String getmProductPrice() {
        return mProductPrice;
    }

    public String getmProductCode() {
        return mProductCode;
    }

    public String getmProductContent() {
        return mProductContent;
    }

    public void setmProductContent(String mProductContent) {
        this.mProductContent = mProductContent;
    }
}
