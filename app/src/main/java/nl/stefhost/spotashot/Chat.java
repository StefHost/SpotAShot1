package nl.stefhost.spotashot;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Chat extends AppCompatActivity {

    public String resultaat;
    public String nummer;
    public String naam_speler;
    public String tegenstander;
    public String bericht;
    public String kleur_speler;
    public String kleur_tegenstander;

    public EditText editText;

    public ArrayList<String> arrayList_html;
    public android.app.ProgressDialog ProgressDialog;

    public ArrayList<String> arrayList_bericht; public ArrayList<String> arrayList_datum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        nummer = getIntent().getExtras().getString("nummer");

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam_speler = sharedPreferences.getString("naam", null);

        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
        Cursor cursor = SQLiteDatabase.rawQuery("SELECT tegenstander, kleur_speler, kleur_tegenstander FROM spellen WHERE id2='"+nummer+"'", null);

        if(cursor != null && cursor.moveToFirst()){
            tegenstander = cursor.getString(cursor.getColumnIndex("tegenstander"));
            kleur_speler = cursor.getString(cursor.getColumnIndex("kleur_speler"));
            kleur_tegenstander = cursor.getString(cursor.getColumnIndex("kleur_tegenstander"));
            cursor.close();

            if (kleur_speler.equals("0")){
                kleur_speler = "grijs";
            }
            if (kleur_tegenstander.equals("0")){
                kleur_tegenstander = "grijs";
            }

            if (getSupportActionBar() != null){
                getSupportActionBar().hide();
            }

            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
            TextView textView1 = (TextView) findViewById(R.id.textView1);
            editText = (EditText) findViewById(R.id.editText);

            textView1.setTypeface(typeface);
            editText.setTypeface(typeface);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("herladen", "ja");
            editor.putString("huidige_chat", nummer);
            editor.apply();

            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                Toast.makeText(this.getApplicationContext(), getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.chat_melding_1), getString(R.string.een_ogenblik_geduld), true, false);
                new chat_laden().execute();
            }

            // notificaties cancellen
            Set<String> stringSet = sharedPreferences.getStringSet("notificaties_chat_"+nummer, new HashSet<String>());
            for (String string : stringSet) {
                int id = Integer.parseInt(string);
                Log.d("SAS",""+id);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(id);
            }
            editor.putStringSet("notificaties_chat_"+nummer, null);
            editor.apply();
        }else{
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bericht = editText.getText().toString();
        bericht = bericht.replace(" ", "%20");

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_chat", "");
        editor.putString("huidige_bericht", bericht);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        String huidige_bericht = sharedPreferences.getString("huidige_bericht", "");
        huidige_bericht = huidige_bericht.replace("%20", " ");
        editText.append(huidige_bericht);
    }

    private class chat_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            arrayList_html = new ArrayList<>();

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_chat = naam_speler.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/chat_laden.php?nummer="+nummer+"&naam="+naam_chat);
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

                String line;
                try{
                    while ((line = bufferedReader.readLine()) != null) {
                        arrayList_html.add(line);
                    }

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
            chat_laden_klaar();
        }

    }

    private void chat_laden_klaar() {

        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);

        int tellen = 0;
        while (arrayList_html.size() > tellen) {
            String totaal = arrayList_html.get(tellen);

            StringTokenizer stringTokenizer = new StringTokenizer(totaal, "|");
            String afzender = stringTokenizer.nextToken();
            String datum = stringTokenizer.nextToken();
            String bericht = stringTokenizer.nextToken();

            SQLiteDatabase.execSQL("INSERT INTO chat (nummer, afzender, datum, bericht) VALUES ('"+nummer+"','"+afzender+"','"+datum+"','"+bericht+"')");
            tellen++;
        }

        arrayList_bericht = new ArrayList<>();
        arrayList_datum = new ArrayList<>();

        Cursor cursor = SQLiteDatabase.rawQuery("SELECT bericht, afzender, datum FROM chat WHERE nummer='"+nummer+"' ORDER BY id ASC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {

            String bericht = cursor.getString(cursor.getColumnIndex("bericht"));
            String afzender = cursor.getString(cursor.getColumnIndex("afzender"));
            String datum = cursor.getString(cursor.getColumnIndex("datum"));

            bericht = bericht.replace("[haakje]", "'");

            if (afzender.equals(naam_speler)) {
                arrayList_bericht.add("["+kleur_speler+"]"+getString(R.string.chat_3)+"[/"+kleur_speler+"] " + bericht);
            }else{
                arrayList_bericht.add("["+kleur_tegenstander+"]"+tegenstander+":[/"+kleur_tegenstander+"] " + bericht);
            }
            arrayList_datum.add(datum);

            cursor.moveToNext();
        }
        cursor.close();

        ArrayAdapter arrayAdapter = new arrayAdapter_bericht(this, arrayList_bericht, arrayList_datum);
        final ListView listView = (ListView) findViewById(R.id.listView_chat);
        listView.setAdapter(arrayAdapter);
        listView.setSelection(listView.getCount());

        ProgressDialog.dismiss();

    }

    public void verzenden (View view){
        bericht = editText.getText().toString();
        bericht = bericht.replace(" ", "%20");
        bericht = bericht.replace("'", "[haakje]");

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            Toast.makeText(this.getApplicationContext(), getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
        }else{
            ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.chat_melding_2), getString(R.string.een_ogenblik_geduld), true, false);
            new chat_versturen().execute();
        }

    }

    private class chat_versturen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            arrayList_html = new ArrayList<>();

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String tegenstander_chat = tegenstander.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/chat_versturen.php?nummer="+nummer+"&naam="+naam_speler+"&bericht="+bericht+"&ontvanger="+tegenstander_chat);
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
            chat_versturen_klaar();
        }

    }

    private void chat_versturen_klaar() {

        String datum;

        if (resultaat.equals("OK")){

            String bericht = editText.getText().toString();
            bericht = bericht.replace("'", "[haakje]");

            Calendar calendar = Calendar.getInstance();
            int dag = calendar.get(Calendar.DATE);
            int maand = calendar.get(Calendar.MONTH)+1;
            int jaar = calendar.get(Calendar.YEAR);
            int uren = calendar.get(Calendar.HOUR_OF_DAY);
            int minuten = calendar.get(Calendar.MINUTE);

            String dag_ = "";
            String maand_ = "";
            String uren_ = "";
            String minuten_ = "";

            if (dag < 10) {
                dag_ = "0"+dag;
            }else{
                dag_ = ""+dag;
            }

            if (maand < 10) {
                maand_ = "0"+maand;
            }else{
                maand_ = ""+maand;
            }

            if (uren < 10) {
                uren_ = "0"+uren;
            }else{
                uren_ = ""+uren;
            }

            if (minuten < 10) {
                minuten_ = "0"+minuten;
            }else{
                minuten_ = ""+minuten;
            }

            datum = dag_+"-"+maand_+"-"+jaar+" - "+uren_+":"+minuten_;

            SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
            SQLiteDatabase.execSQL("INSERT INTO chat (nummer, afzender, datum, bericht) VALUES ('"+nummer+"','"+naam_speler+"','"+datum+"','"+bericht+"')");
            editText.setText("");
        }

        ProgressDialog.dismiss();

        ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.chat_melding_1), getString(R.string.een_ogenblik_geduld), true, false);
        new chat_laden().execute();

    }

}
