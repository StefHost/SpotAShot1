package nl.stefhost.spotashot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Locale;

public class Registreren extends AppCompatActivity {

    public Context context;
    public android.app.ProgressDialog ProgressDialog;
    public String resultaat;
    public String naam;
    public String wachtwoord;
    public String email;
    public String taal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreren);
        context = Registreren.this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        taal = Locale.getDefault().getLanguage();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        editText1.setTypeface(typeface);
        editText2.setTypeface(typeface);
        editText3.setTypeface(typeface);
        editText4.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Welkom.class);
        startActivity(intent);
        finish();
    }

    public void voorwaarden(View view){
        ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.voorwaarden_1), getString(R.string.een_ogenblik_geduld), true, false);
        new voorwaarden_laden().execute();
    }

    private class voorwaarden_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            String taal = Locale.getDefault().getLanguage();

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/algemene_voorwaarden.php?taal="+taal);
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
            resultaat = resultaat.replace("[enter]", "<br />");

            ProgressDialog.dismiss();

            AlertDialog.Builder builder = new AlertDialog.Builder(Registreren.this);
            builder.setTitle(getString(R.string.voorwaarden_1))
                    .setMessage(Html.fromHtml(resultaat));
            builder.show();

            //textView.setText(Html.fromHtml(resultaat));

        }

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
            _functions.melding(getString(R.string.registreren_foutmelding_1_titel), getString(R.string.registreren_foutmelding_1_tekst), context);
        }else if (wachtwoord.matches("")){
            _functions.melding(getString(R.string.registreren_foutmelding_2_titel), getString(R.string.registreren_foutmelding_2_tekst), context);
        }else if (wachtwoord_herhalen.matches("")){
            _functions.melding(getString(R.string.registreren_foutmelding_3_titel), getString(R.string.registreren_foutmelding_3_tekst), context);
        }else if (!wachtwoord.equals(wachtwoord_herhalen)){
            _functions.melding(getString(R.string.registreren_foutmelding_4_titel), getString(R.string.registreren_foutmelding_4_tekst), context);
        }else if (email.matches("")){
            _functions.melding(getString(R.string.registreren_foutmelding_5_titel), getString(R.string.registreren_foutmelding_5_tekst), context);
        }else if (!voorwaarden){
            _functions.melding(getString(R.string.registreren_foutmelding_6_titel), getString(R.string.registreren_foutmelding_6_tekst), context);
        }else{
            InputMethodManager InputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager.hideSoftInputFromWindow(editText1.getWindowToken(), 0);

            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                Toast.makeText(this.getApplicationContext(), getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.registreren_melding_1), getString(R.string.een_ogenblik_geduld), true, false);
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
                url = new URL(getString(R.string.website_paginas)+"/registreren.php?naam="+naam+"&wachtwoord="+wachtwoord+"&email="+email+"&taal="+taal);
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
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else if (resultaat.matches("NAAM")){
            _functions.melding(getString(R.string.registreren_foutmelding_7_titel), getString(R.string.registreren_foutmelding_7_tekst), context);
        }else if (resultaat.matches("EMAIL")){
            _functions.melding(getString(R.string.registreren_foutmelding_8_titel), getString(R.string.registreren_foutmelding_8_tekst), context);
        }else{
            Intent intent = new Intent(this, Inloggen.class);
            startActivity(intent);
            finish();
        }

    }

}