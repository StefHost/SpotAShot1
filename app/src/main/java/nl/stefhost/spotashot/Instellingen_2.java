package nl.stefhost.spotashot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;

public class Instellingen_2 extends AppCompatActivity {

    public String naam;
    public String resultaat;
    public String keuze = "";

    public ArrayList<String> arrayList;
    public String[] stringArray;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instellingen_2);

        context = this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        //TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);

        checkBox.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        //textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam = sharedPreferences.getString("naam", "");
        String spel_homescherm = sharedPreferences.getString("spel_homescherm", "");

        naam = naam.replace("%2520", " ");

        String versie_name = BuildConfig.VERSION_NAME;
        textView4.setText(versie_name);

        if (spel_homescherm.equals("") || spel_homescherm.equals("AAN")) {
            checkBox.setChecked(true);
        }

        Spinner spinner = (Spinner) findViewById(R.id.talen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.talen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String lang = "";

                switch (position) {
                    case 0:
                        lang = "nl";
                        break;
                    case 1:
                        lang = "de";
                        break;
                    case 2:
                        lang = "en";
                        break;
                }

                if (userIsInteracting) {
                    Locale myLocale = new Locale(lang);
                    Locale.setDefault(myLocale);
                    android.content.res.Configuration config = new android.content.res.Configuration();
                    config.locale = myLocale;
                    context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                    //finish();
                    recreate();
                }

                SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("reload", "ja");
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    boolean userIsInteracting;

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    public void spel_homescherm(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        if (checkBox.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("spel_homescherm", "AAN");
            editor.apply();
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("spel_homescherm", "UIT");
            editor.apply();
        }
    }


    public void deblokkeren(View view){
        new tegenstander_deblokkeren().execute();
    }

    private class tegenstander_deblokkeren extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_deblokkeren = naam.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/tegenstander_deblokkeren.php?naam="+naam_deblokkeren+"&keuze="+keuze);
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
            tegenstander_deblokkeren_klaar();
        }

    }

    public void tegenstander_deblokkeren_klaar() {

        Log.d("SOS", resultaat);

        if (keuze.equals("")){
            arrayList = new ArrayList<>();

            StringTokenizer tokens = new StringTokenizer(resultaat, ",");
            while (tokens.hasMoreTokens()){
                String gebruiker = tokens.nextToken();
                arrayList.add(gebruiker);
            }

            stringArray = arrayList.toArray(new String[arrayList.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.instellingen_melding_1_titel))
                    .setItems(stringArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            keuze = stringArray[which];
                            new tegenstander_deblokkeren().execute();
                        }
                    });
            builder.show();
        }else{
            keuze = "";
        }


    }

    public void uitloggen(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Instellingen_2.this);
        builder.setTitle(getString(R.string.instellingen_melding_2_titel))
                .setMessage(getString(R.string.instellingen_melding_2_tekst));
        builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("plaatje", null);
                editor.putString("naam", null);
                editor.putString("email", null);
                editor.putString("wachtwoord", null);
                editor.putString("laatste_spel", null);
                editor.putString("uitloggen", "JA");
                editor.putStringSet("adresboek", null);
                editor.apply();

                SQLiteDatabase SQLiteDatabase = Instellingen_2.this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
                SQLiteDatabase.execSQL("delete from spellen");
                SQLiteDatabase.execSQL("delete from chat");

                Intent intent = new Intent(Instellingen_2.this, Welkom.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.nee), null);
        builder.show();

    }

    public void delete_account(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Instellingen_2.this);
        builder.setTitle(getString(R.string.instellingen_melding_3_titel))
                .setMessage(getString(R.string.instellingen_melding_3_tekst));
        builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                new account_verwijderen().execute();
            }
        });
        builder.setNegativeButton(getString(R.string.nee), null);
        builder.show();
    }

    private class account_verwijderen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_account = naam.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/account_verwijderen.php?naam="+naam_account);
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
            account_verwijderen_klaar();
        }

    }

    public void account_verwijderen_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), Instellingen_2.this);
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("plaatje", null);
            editor.putString("naam", null);
            editor.putString("email", null);
            editor.putString("wachtwoord", null);
            editor.putString("laatste_spel", null);
            editor.putString("uitloggen", "JA");
            editor.apply();

            SQLiteDatabase SQLiteDatabase = Instellingen_2.this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
            SQLiteDatabase.execSQL("delete from spellen");
            SQLiteDatabase.execSQL("delete from chat");

            Intent intent = new Intent(Instellingen_2.this, Welkom.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Instellingen_2.this, Instellingen.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

}