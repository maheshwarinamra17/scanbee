package com.scanbee.servercommunication;

/**
 * Created by kshitij on 4/20/2016.
 */
public class WebServiceUrl {
    public static final String BASE_URL = "http://52.4.78.14/sbapi/";
//    public static final String BASE_URL = "http://192.168.0.106:3000/sbapi/";
    public static final String GET_RASPBERRY_DATA = "/data_file.json";
    public static final String GENERATE_DUMMY_ORDER = "generate_dummy_order";
    public static final String GENERATE_ORDER_ID = "generate_order_id";
    public static final String GET_ORDER_DATA = "get_order_data";
    public static final String UPDATE_ORDER_DATA = "update_order";
    public static final String GET_USER_LOGIN = "user_login";
    public static final String GET_USER_LOGOUT = "user_logout";
    public static final String GET_CUSTOMER_DATA = "get_customer_data";
    public static final String GENERATE_PAYMENT_DATA = "generate_payment_data";
    public static final String CANCEL_ORDER = "cancel_order";

}
