package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Registreren extends AppCompatActivity {

    public Context context;
    public android.app.ProgressDialog ProgressDialog;
    public String resultaat;
    public String naam;
    public String wachtwoord;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreren);
        context = Registreren.this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Welkom.class);
        startActivity(intent);
        finish();
    }

    public void terug(View view){
        Intent intent = new Intent(this, Welkom.class);
        startActivity(intent);
        finish();
    }

    public void voorwaarden(View view){
        Intent voorwaarden = new Intent(this, Voorwaarden.class);
        startActivity(voorwaarden);
    }

    public void registreren(View view){

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        naam = editText1.getText().toString();
        wachtwoord = editText2.getText().toString();
        String wachtwoord_herhalen = editText3.getText().toString();
        email = editText4.getText().toString();

        naam = naam.replace(" ", "%20");
        wachtwoord = wachtwoord.replace(" ", "%20");
        wachtwoord_herhalen = wachtwoord_herhalen.replace(" ", "%20");

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

        try {
            wachtwoord_herhalen = URLEncoder.encode(wachtwoord_herhalen, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        boolean voorwaarden = checkBox.isChecked();

        if (naam.matches("")){
            _functions.melding("No username", "You didn't provide a username.\nPlease try again.", context);
        }else if (wachtwoord.matches("")){
            _functions.melding("No password", "You didn't provide a password.\nPlease try again.", context);
        }else if (wachtwoord_herhalen.matches("")){
            _functions.melding("Repeat password", "You didn't repeat your password.\nPlease try again.", context);
        }else if (!wachtwoord.equals(wachtwoord_herhalen)){
            _functions.melding("Wrong passwords", "Your passwords do not match.\nPlease try again.", context);
        }else if (email.matches("")){
            _functions.melding("No e-mail address", "You didn't provide an e-mail address.\nPlease try again.", context);
        }else if (!voorwaarden){
            _functions.melding("Terms", "You didn't accept the terms.\nPlease try again.", context);
        }else{
            InputMethodManager InputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager.hideSoftInputFromWindow(editText1.getWindowToken(), 0);

            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                Toast.makeText(this.getApplicationContext(), "No active network connection", Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, "Register", "One moment please..", true, false);
                new registreren().execute();
            }
        }

    }

    private class registreren extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/registreren.php?naam="+naam+"&wachtwoord="+wachtwoord+"&email="+email);
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
            registreren_klaar();
        }

    }

    public void registreren_klaar() {

        if (resultaat.matches("ERROR")){
            _functions.melding("Registreren niet mogelijk", "Registreren is op dit moment niet mogelijk..\nProbeer het later nog een keer.", context);
        }else if (resultaat.matches("NAAM")){
            _functions.melding("Username exists", "This username already exists.\nPlease try again.", context);
        }else if (resultaat.matches("EMAIL")){
            _functions.melding("E-mail exists", "This e-mail address already exists.\nPlease try again.", context);
        }else{
            Intent intent = new Intent(this, Inloggen.class);
            startActivity(intent);
            finish();
        }

    }

}