package nl.stefhost.spotashot;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;
import org.json.JSONObject;

public class Instellingen extends AppCompatActivity {

    public Switch Switch1;
    public Switch Switch2;
    public Switch Switch3;
    public Switch Switch4;
    public Switch Switch5;
    //public Switch Switch6;

    public CheckBox CheckBox;

    public String naam;
    public String resultaat;

    IInAppBillingService mService;

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instellingen);

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Switch1 = (Switch) findViewById(R.id.switch1);
        Switch2 = (Switch) findViewById(R.id.switch2);
        Switch3 = (Switch) findViewById(R.id.switch3);
        Switch4 = (Switch) findViewById(R.id.switch4);
        Switch5 = (Switch) findViewById(R.id.switch5);
        CheckBox = (CheckBox) findViewById(R.id.checkBox);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        Switch1.setTypeface(typeface);
        Switch2.setTypeface(typeface);
        Switch3.setTypeface(typeface);
        Switch4.setTypeface(typeface);
        Switch5.setTypeface(typeface);
        CheckBox.setTypeface(typeface);

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        String notificaties_1 = sharedPreferences.getString("notificaties_1", "");
        String notificaties_2 = sharedPreferences.getString("notificaties_2", "");
        String notificaties_3 = sharedPreferences.getString("notificaties_3", "");
        String notificaties_4 = sharedPreferences.getString("notificaties_4", "");
        String notificaties_5 = sharedPreferences.getString("notificaties_5", "");
        String geluid = sharedPreferences.getString("geluid", "");

        naam = sharedPreferences.getString("naam", "");
        naam = naam.replace("%2520", " ");

        if (notificaties_1.equals("") || notificaties_1.equals("AAN")){
            Switch1.setChecked(true);
        }
        if (notificaties_2.equals("") || notificaties_2.equals("AAN")){
            Switch2.setChecked(true);
        }
        if (notificaties_3.equals("") || notificaties_3.equals("AAN")) {
            Switch3.setChecked(true);
        }
        if (notificaties_4.equals("") || notificaties_4.equals("AAN")) {
            Switch4.setChecked(true);
        }
        if (notificaties_5.equals("") || notificaties_5.equals("AAN")) {
            Switch5.setChecked(true);
        }

        if (geluid.equals("") || geluid.equals("AAN")) {
            CheckBox.setChecked(true);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    public void notificaties(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);

        String tag = (String) view.getTag();
        if (tag.equals("1")){
            if (Switch1.isChecked()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_1", "AAN");
                editor.apply();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_1", "UIT");
                editor.apply();
            }
        }else if (tag.equals("2")){
            if (Switch2.isChecked()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_2", "AAN");
                editor.apply();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_2", "UIT");
                editor.apply();
            }
        }else if (tag.equals("3")){
            if (Switch3.isChecked()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_3", "AAN");
                editor.apply();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_3", "UIT");
                editor.apply();
            }
        }else if (tag.equals("4")){
            if (Switch4.isChecked()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_4", "AAN");
                editor.apply();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_4", "UIT");
                editor.apply();
            }
        }else if (tag.equals("5")){
            if (Switch5.isChecked()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_5", "AAN");
                editor.apply();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("notificaties_5", "UIT");
                editor.apply();
            }
        }
    }

    public void geluid(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        if (CheckBox.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("geluid", "AAN");
            editor.apply();
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("geluid", "UIT");
            editor.apply();
        }
    }

    public void instellingen_2(View view){
        Intent intent = new Intent(Instellingen.this, Instellingen_2.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    public void kopen(View view){
        Bundle buyIntentBundle = null;

        try {
            buyIntentBundle = mService.getBuyIntent(3, getPackageName(), "reclame", "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

        try {
            startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Log.d("TestCollectie", "You have bought the " + sku + ". Excellent choice, adventurer!");

                }
                catch (JSONException e) {
                    Log.d("TestCollectie", "Failed to parse purchase data.");
                    e.printStackTrace();
                }
            }
        }
    }

}