/*
package com.scanbee.scanbee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scanbee.fragment.CartItemFragment;
import com.scanbee.servercommunication.WebRequest;
import com.scanbee.servercommunication.WebServiceCall;
import com.scanbee.servercommunication.WebServiceUrl;
import com.scanbee.sharedpref.ReadPref;
import com.scanbee.sharedpref.SavePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class MainActivity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    Toolbar toolbar;
    SavePref savePref;
    ReadPref readPref;
    ProgressDialog progressDialog;
    TextView idTv, totalIitemSelectedTv, custNameTv, circleImgTv;
    Button continue_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        savePref=new SavePref(getApplicationContext());
        readPref=new ReadPref(getApplicationContext());
        progressDialog = new ProgressDialog(MainActivity1.this);
        setUpToolBar();
        setUpUi();
        new FetchData().execute();
    }

    public void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setUpUi() {
        idTv = (TextView) findViewById(R.id.idTv);
        totalIitemSelectedTv = (TextView) findViewById(R.id.item_selectedTv);
        custNameTv = (TextView) findViewById(R.id.custNameTv);
        circleImgTv = (TextView) findViewById(R.id.circleImgTv);
        continue_btn = (Button) findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(this);
        System.out.println("orderId==========>>>>>>>> :"+readPref.getOrderId());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            Toast.makeText(MainActivity1.this, "delete item", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity1.this,CartItemFragment.class);
        startActivity(intent);
    }

    private class FetchData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Loading...");
            //show dialog
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            WebRequest webRequest= new WebRequest();

            forecastJsonStr=webRequest.makeWebServiceCall(WebServiceUrl.BASE_URL+WebServiceUrl.GENERATE_DUMMY_ORDER,webRequest.GET);
            System.out.println("response===>>>>"+forecastJsonStr);
             return forecastJsonStr;

        }

        @Override
        protected void onPostExecute(String result) {

            parseJson(result);
            progressDialog.dismiss();
            new GetOrderIdAsynctask().execute();

            super.onPostExecute(result);
            //tvWeatherJson.setText(s);
            Log.i("json", result);
        }
    }
    public void setDataOnUi(){

    }

    public void parseJson(String json) {

            JSONObject mainObj = null;
            try {
                mainObj = new JSONObject(json);
                String massage=mainObj.optString("message");
                System.out.println(massage);
 int status=mainObj.optInt("status");
                if(massage.equals(massage)){

                }

               JSONObject orderDataObj= mainObj.optJSONObject("order_data");
                String timeStamp= orderDataObj.optString("timestamp","2016-04-12 23:09:11");
                System.out.println(timeStamp);
                JSONArray barcodeDataArray=orderDataObj.optJSONArray("barcode_data");
                for (int i=0;i<barcodeDataArray.length();i++){
                    ArrayList<Integer> idList=new ArrayList<Integer>();
                    idList.add(i);
                }
                JSONArray quantArray=orderDataObj.optJSONArray("quant_array");
                for (int i=0;i<barcodeDataArray.length();i++){
                    ArrayList<Integer> quantList=new ArrayList<Integer>();
                    quantList.add(i);
                }
                        int machineId=orderDataObj.optInt("machine_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
}
    private String createUserDataJson() {

        JSONObject obj = new JSONObject();
        JSONArray barcodeArray=new JSONArray();
        JSONArray quantArray=new JSONArray();
        try {
{
	"timestamp": "2016-04-12 23:09:11",
	"barcode_data": ["13095949", "57840055", "91749284", "84865601"],
	"quant_array": [2, 4, 3, 4],
	"machine_id": "21"
}

            obj.put("timestamp","2016-04-12 23:09:11");
            barcodeArray.put("13095949");
            barcodeArray.put("57840055");
            barcodeArray.put("91749284");
            barcodeArray.put("84865601");
            obj.put("barcode_data", barcodeArray);

            quantArray.put(2);
            quantArray.put(4);
            quantArray.put(3);
            quantArray.put(4);
            obj.put("quant_array",quantArray);
            obj.put("machine_id","21");

            System.out.println("json object is----------   " + obj);
            //return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
    private class GetOrderIdAsynctask extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity1.this);
            progressDialog.setMessage("postRequest...");
            //show dialog
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
              String param=createUserDataJson();

            WebServiceCall webServiceCal=new WebServiceCall();
            String response=  webServiceCal.excutePost(WebServiceUrl.BASE_URL+WebServiceUrl.GENERATE_ORDER_ID,param);
            System.out.println("PostData responce===>>>>"+response);
            return response ;

        }

        @Override
        protected void onPostExecute(String result) {
             progressDialog.dismiss();
            System.out.println("PostData responce===>>>>"+result);
            parseJson1(result);
           // new GetOrderIdAsynctask().execute();

            super.onPostExecute(result);
            //tvWeatherJson.setText(s);
            Log.i("json", result);
        }
    }
      public void parseJson1(String result){
          try {
              JSONObject newObj=new JSONObject(result);
              int status=newObj.optInt("status");
              String message=newObj.optString("Success");
              int order_id=newObj.optInt("order_id");
              savePref.saveOrderId(order_id);
              idTv.setText(String.valueOf(order_id));
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }
}
*/
