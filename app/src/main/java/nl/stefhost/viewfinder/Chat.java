package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
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

    public ArrayList<String> arrayList_bericht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        nummer = getIntent().getExtras().getString("nummer");
        naam_speler = getIntent().getExtras().getString("naam_speler");
        tegenstander = getIntent().getExtras().getString("tegenstander");
        kleur_speler = getIntent().getExtras().getString("kleur_speler");
        kleur_tegenstander = getIntent().getExtras().getString("kleur_tegenstander");

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText);

        textView1.setTypeface(typeface);
        editText.setTypeface(typeface);

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("herladen", "ja");
        editor.apply();

        ProgressDialog = android.app.ProgressDialog.show(this, "Loading chat", "One moment please..", true, false);
        new chat_laden().execute();
    }

    private class chat_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            arrayList_html = new ArrayList<>();

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/chat_laden.php?nummer="+nummer+"&naam="+naam_speler);
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

        Cursor cursor = SQLiteDatabase.rawQuery("SELECT bericht, afzender, datum FROM chat WHERE nummer='"+nummer+"' ORDER BY id ASC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {

            String bericht = cursor.getString(cursor.getColumnIndex("bericht"));
            String afzender = cursor.getString(cursor.getColumnIndex("afzender"));
            String datum = cursor.getString(cursor.getColumnIndex("datum"));
            if (afzender.equals(naam_speler)) {
                arrayList_bericht.add("["+kleur_speler+"]Jij:[/"+kleur_speler+"] " + bericht);
            }else if (afzender.equals("Viewfinder")){
                arrayList_bericht.add("[grijs]Viewfinder:[/grijs] "+bericht);
            }else{
                arrayList_bericht.add("["+kleur_tegenstander+"]"+tegenstander+":[/"+kleur_tegenstander+"] " + bericht);
            }
            cursor.moveToNext();
        }
        cursor.close();

        ArrayAdapter arrayAdapter = new arrayAdapter_bericht(this, arrayList_bericht);
        final ListView listView = (ListView) findViewById(R.id.listView_chat);
        listView.setAdapter(arrayAdapter);
        listView.setSelection(listView.getCount());

        ProgressDialog.dismiss();

    }

    public void verzenden (View view){
        ProgressDialog = android.app.ProgressDialog.show(this, "Bericht versturen", "Even geduld aub..", true, false);
        bericht = editText.getText().toString();
        bericht = bericht.replace(" ", "%20");
        new chat_versturen().execute();
    }

    private class chat_versturen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            arrayList_html = new ArrayList<>();

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/chat_versturen.php?nummer="+nummer+"&naam="+naam_speler+"&bericht="+bericht+"&ontvanger="+tegenstander);
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

            Calendar calendar = Calendar.getInstance();
            int dag = calendar.get(Calendar.DATE);
            int maand = calendar.get(Calendar.MONTH);
            int jaar = calendar.get(Calendar.YEAR);
            int uren = calendar.get(Calendar.HOUR_OF_DAY);
            int minuten = calendar.get(Calendar.MINUTE);
            if (minuten < 10){
                datum = dag+"-"+maand+"-"+jaar+" - "+uren+":0"+minuten;
            }else{
                datum = dag+"-"+maand+"-"+jaar+" - "+uren+":"+minuten;
            }

            SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
            SQLiteDatabase.execSQL("INSERT INTO chat (nummer, afzender, datum, bericht) VALUES ('"+nummer+"','"+naam_speler+"','"+datum+"','"+bericht+"')");
            editText.setText("");
        }

        ProgressDialog.dismiss();

        ProgressDialog = android.app.ProgressDialog.show(this, "Loading chat", "One moment please..", true, false);
        new chat_laden().execute();

    }

}
