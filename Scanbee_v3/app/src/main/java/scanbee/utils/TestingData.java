package scanbee.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by namra on 15/01/17.
 */

public class TestingData {

    public static ArrayList<JSONObject> getDummyCart(int length){

        ArrayList<JSONObject> dummyCart = new ArrayList<JSONObject>();
        String[] colorArray = {"R","G","B","Y"};

        for (int i=0; i<length; i++){
            JSONObject cartItem = new JSONObject();
            Random generator = new Random();
            double number = generator.nextDouble() * 200;
            String formatted = String.format("%.2f", number);
            try {
                cartItem.put("prod_name", "#WHACK Product "+i+1);
                cartItem.put("prod_code", getRandom(colorArray)+"42"+i);
                cartItem.put("prod_qty", 1);
                cartItem.put("prod_price", formatted);
                cartItem.put("prod_content", "50ml");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dummyCart.add(cartItem);
        }
        return dummyCart;
    }

    public static ArrayList<JSONObject> getDummyOrders(int length){

        ArrayList<JSONObject> dummyOrder = new ArrayList<JSONObject>();
        String[] statusArray = {"1","0"};

        for (int i=0; i<length; i++){
            JSONObject orderItem = new JSONObject();
            try {
                orderItem.put("order_id", "ORD3456"+i);
                orderItem.put("order_price", 1442.42);
                orderItem.put("order_info", "Bought by Alfred Nobel at London Museum");
                orderItem.put("order_status", getRandom(statusArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dummyOrder.add(orderItem);
        }
        return dummyOrder;
    }

    public static JSONObject getDummyPayment(){

        JSONObject dummyPayment = new JSONObject();
            try {
                dummyPayment.put("cart_number_items", "456");
                dummyPayment.put("cart_sub_total", "4567.65");
                dummyPayment.put("cart_discount", "456.78");
                dummyPayment.put("cart_tax", "654.45");
                dummyPayment.put("cart_amt_pay", "3546.87");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return dummyPayment;
    }

    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

}
