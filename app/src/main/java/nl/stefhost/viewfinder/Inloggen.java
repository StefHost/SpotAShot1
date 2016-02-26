package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.StringTokenizer;

public class Inloggen extends AppCompatActivity {

    public Context context;
    public android.app.ProgressDialog ProgressDialog;
    public String resultaat;
    public String naam;
    public String wachtwoord;

    com.google.android.gms.gcm.GoogleCloudMessaging GoogleCloudMessaging;
    String RegistrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inloggen);
        context = Inloggen.this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        if (checkPlayServices()) {
            GoogleCloudMessaging = com.google.android.gms.gcm.GoogleCloudMessaging.getInstance(this);
            RegistrationId = getRegistrationId();
            if (RegistrationId.isEmpty()) {
                ProgressDialog = android.app.ProgressDialog.show(this, "Notificaties inschakelen", "Even geduld aub..", true, false);
                new gcm().execute();
            }
        }else{
            Log.d("Viewfinder", "geen Play services");
        }

        TelephonyManager telephonyManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String id = telephonyManager.getDeviceId();

        if (id.equals("357139054066548") || id.equals("000000000000000")){
            EditText editText1 = (EditText) findViewById(R.id.editText1);
            EditText editText2 = (EditText) findViewById(R.id.editText2);
            editText1.setText("Test");
            editText2.setText("test");
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
            }else{
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId() {

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        String registrationId = sharedPreferences.getString("registration_id", "");
        if (registrationId.isEmpty()) {
            return "";
        }
        int registeredVersion = sharedPreferences.getInt("appVersion", Integer.MIN_VALUE);
        int currentVersion = getAppVersion(getApplicationContext());
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private class gcm extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (GoogleCloudMessaging == null) {
                    GoogleCloudMessaging = com.google.android.gms.gcm.GoogleCloudMessaging.getInstance(getApplicationContext());
                }
                RegistrationId = GoogleCloudMessaging.register("747315302291");
                storeRegistrationId(getApplicationContext(), RegistrationId);
            } catch (IOException ex) {
                // IOException
            }
            return null;
        }

        @Override
        protected void onPostExecute(String msg) {
            ProgressDialog.dismiss();
        }

    }

    private void storeRegistrationId(Context context, String RegistrationId) {

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int appVersion = getAppVersion(context);
        editor.putString("registration_id", RegistrationId);
        editor.putInt("appVersion", appVersion);
        editor.apply();
    }


    public void onBackPressed() {
        Intent intent = new Intent(this, Welkom.class);
        startActivity(intent);
        finish();
    }

    public void inloggen(View view){

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);

        naam = editText1.getText().toString();
        wachtwoord = editText2.getText().toString();

        naam = naam.replace(" ", "%20");
        wachtwoord = wachtwoord.replace(" ", "%20");

        try {
            naam = URLEncoder.encode(naam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            wachtwoord = URLEncoder.encode(wachtwoord, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (naam.matches("")){
            _functions.melding("No username", "You didn't provide a username.\nPlease try again.", context);
        }else if (wachtwoord.matches("")){
            _functions.melding("No password", "You didn't provide a password.\nPlease try again.", context);
        }else{
            InputMethodManager InputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager.hideSoftInputFromWindow(editText1.getWindowToken(), 0);

            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                Toast.makeText(this.getApplicationContext(), "No active network connection", Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, "Login", "One moment please..", true, false);
                new inloggen().execute();
            }
        }

    }

    private class inloggen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/inloggen.php?naam="+naam+"&wachtwoord="+wachtwoord+"&gcm="+RegistrationId);
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException");
            }

            if (url != null){
                try{
                    urlConnection = url.openConnection();
                }catch (java.io.IOException e){
                    System.out.println("java.io.IOException");
                }
            }

            if (urlConnection != null){
                try{
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }
            }

            if (inputStream != null){
                resultaat = inputStream.toString();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try{
                    resultaat = bufferedReader.readLine();
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            }else{
                resultaat = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ProgressDialog.dismiss();
            inloggen_klaar();
        }

    }

    public void inloggen_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding("Inloggen niet mogelijk", "Inloggen is op dit moment niet mogelijk..\nProbeer het later nog een keer.", context);
        }else if (resultaat.matches("NAAM")){
            _functions.melding("Wrong username", "You provided a wrong username.\nPlease try again.", context);
        }else if (resultaat.matches("WACHTWOORD")){
            _functions.melding("Wrong password", "You provided a wrong password.\nPlease try again.", context);
        }else{

            StringTokenizer tokens = new StringTokenizer(resultaat, "|");
            String email = tokens.nextToken();
            String wachtwoord = tokens.nextToken();

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("naam", naam);
            editor.putString("email", email);
            editor.putString("wachtwoord", wachtwoord);
            editor.apply();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }

    }

    public void vergeten(View view){
        Toast.makeText(this.getApplicationContext(), "not available", Toast.LENGTH_SHORT).show();
    }
}