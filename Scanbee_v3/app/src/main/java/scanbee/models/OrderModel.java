package scanbee.models;

/**
 * Created by namra on 15/01/17.
 */

public class OrderModel {

    private String mOrderId;
    private String mOrderInfo;
    private String mOrderTime;
    private String mOrderPrice;
    private int mOrderStatus;
    private String mOrderCustomer;

    public OrderModel(
            int orderStatus,
            String orderPrice,
            String orderTime,
            String orderInfo,
            String orderCustomer,
            String orderId) {

        mOrderId =  orderId;
        mOrderInfo =  orderInfo;
        mOrderTime = orderTime;
        mOrderPrice = orderPrice;
        mOrderStatus =  orderStatus;
        mOrderCustomer = orderCustomer;

    }

    public String getmOrderId() {
        return mOrderId;
    }

    public String getmOrderInfo() {
        return mOrderInfo;
    }

    public String getmOrderTime() {
        return mOrderTime;
    }

    public String getmOrderPrice() {
        return mOrderPrice;
    }

    public int getmOrderStatus() {
        return mOrderStatus;
    }

    public String getmOrderCustomer() {
        return mOrderCustomer;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public void setmOrderInfo(String mOrderInfo) {
        this.mOrderInfo = mOrderInfo;
    }

    public void setmOrderTime(String mOrderTime) {
        this.mOrderTime = mOrderTime;
    }

    public void setmOrderPrice(String mOrderPrice) {
        this.mOrderPrice = mOrderPrice;
    }

    public void setmOrderStatus(int mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }

    public void setmOrderCustomer(String mOrderCustomer) {
        this.mOrderCustomer = mOrderCustomer;
    }
}
