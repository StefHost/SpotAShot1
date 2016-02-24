package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Spellen extends AppCompatActivity {

    public android.app.ProgressDialog ProgressDialog;
    public Context context;

    public String id;
    public String naam;
    public String resultaat;
    public ArrayList<String> arrayList_html;

    public ArrayList<String> arrayList_id;
    public ArrayList<String> arrayList_naam_speler;
    public ArrayList<String> arrayList_naam_tegenstander;
    public ArrayList<String> arrayList_punten;
    public ArrayList<String> arrayList_kleur_speler;
    public ArrayList<String> arrayList_kleur_tegenstander;
    public ArrayList<String> arrayList_score_speler;
    public ArrayList<String> arrayList_score_tegenstander;
    public ArrayList<String> arrayList_beoordelen_speler;
    public ArrayList<String> arrayList_beoordelen_tegenstander;
    public ArrayList<String> arrayList_chat;
    public ArrayList<String> arrayList_profielfoto;
    public ArrayList<String> arrayList_thema;
    public ArrayList<String> arrayList_status;

    public ArrayList<String> arrayList;
    public ArrayList<String> arrayList_ids = new ArrayList<>();
    public String[] stringArray;
    String kleur;
    public int positie;
    public String verwijder_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spellen);

        context = Spellen.this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam = sharedPreferences.getString("naam", null);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        TextView textViewBoven = (TextView) findViewById(R.id.textview_boven);
        textViewBoven.setTypeface(typeface);
    }

    @Override
    public void onResume() {
        super.onResume();

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            Toast.makeText(this.getApplicationContext(), "No active network connection", Toast.LENGTH_SHORT).show();
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    ProgressDialog = android.app.ProgressDialog.show(context, "Loading games", "One moment please..", true, false);
                    new spellen_laden().execute();
                }
            }, 400);
        }
    }

    String versie;

    private class spellen_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            try{
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                versie = packageInfo.versionName;
            }catch(PackageManager.NameNotFoundException e) {
                throw new RuntimeException("Could not get package name: " + e);
            }

            arrayList_html = new ArrayList<>();

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/spellen_laden.php?naam="+naam+"&versie="+versie);
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
                        if (!(line.equals("0"))){
                            arrayList_html.add(line);
                        }
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
            spellen_laden_klaar();
        }

    }

    public void spellen_laden_klaar() {

        if (resultaat.equals("ERROR")){
            _functions.melding("ERROR", "ERROR", context);
        }else{

            if (arrayList_html != null){
                SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);

                int tellen = 0;
                while (arrayList_html.size() > tellen) {
                    String totaal = arrayList_html.get(tellen);

                    StringTokenizer stringTokenizer = new StringTokenizer(totaal, "|");
                    String id = stringTokenizer.nextToken();
                    String tegenstander = stringTokenizer.nextToken();
                    String punten = stringTokenizer.nextToken();
                    String kleur_speler = stringTokenizer.nextToken();
                    String kleur_tegenstander = stringTokenizer.nextToken();
                    String score_speler = stringTokenizer.nextToken();
                    String score_tegenstander = stringTokenizer.nextToken();
                    String beoordelen_speler = stringTokenizer.nextToken();
                    String beoordelen_tegenstander = stringTokenizer.nextToken();
                    String chat = stringTokenizer.nextToken();
                    String profielfoto = stringTokenizer.nextToken();
                    String thema = stringTokenizer.nextToken();
                    String status = stringTokenizer.nextToken();

                    arrayList_ids.add(id);

                    String id_database;

                    Cursor cursor = SQLiteDatabase.rawQuery("SELECT id2 FROM spellen WHERE id2='"+id+"'", null);
                    if (cursor.moveToFirst()){
                        id_database = cursor.getString(cursor.getColumnIndex("id2"));
                    }else{
                        id_database = "LEEG";
                    }

                    if (!id_database.equals(id)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                        String datum = sdf.format(new Date());
                        Log.d("Viewfinder", "Datum:"+datum);
                        SQLiteDatabase.execSQL("INSERT INTO spellen (id2, tegenstander, punten, kleur_speler, kleur_tegenstander, score_speler, score_tegenstander, beoordelen_speler, beoordelen_tegenstander, chat, profielfoto, thema, status, datum) VALUES ('"+id+"','"+tegenstander+"','"+punten+"','"+kleur_speler+"','"+kleur_tegenstander+"','"+score_speler+"','"+score_tegenstander+"','"+beoordelen_speler+"','"+beoordelen_tegenstander+"','"+chat+"','"+profielfoto+"','"+thema+"','"+status+"','"+datum+"')");
                    }else{
                        SQLiteDatabase.execSQL("UPDATE spellen SET tegenstander='"+tegenstander+"', kleur_speler='"+kleur_speler+"', kleur_tegenstander='"+kleur_tegenstander+"', score_speler='"+score_speler+"', score_tegenstander='"+score_tegenstander+"', beoordelen_speler='"+beoordelen_speler+"', beoordelen_tegenstander='"+beoordelen_tegenstander+"', chat='"+chat+"', profielfoto='"+profielfoto+"', status='"+status+"' WHERE id2='"+id+"' ");
                    }

                    cursor.close();

                    tellen++;
                }
            }

            laad_database();
        }

    }

    public void laad_database(){

        arrayList_id = new ArrayList<>();
        arrayList_naam_speler = new ArrayList<>();
        arrayList_naam_tegenstander = new ArrayList<>();
        arrayList_punten = new ArrayList<>();
        arrayList_kleur_speler = new ArrayList<>();
        arrayList_kleur_tegenstander = new ArrayList<>();
        arrayList_score_speler = new ArrayList<>();
        arrayList_score_tegenstander = new ArrayList<>();
        arrayList_beoordelen_speler = new ArrayList<>();
        arrayList_beoordelen_tegenstander = new ArrayList<>();
        arrayList_chat = new ArrayList<>();
        arrayList_profielfoto = new ArrayList<>();
        arrayList_thema = new ArrayList<>();
        arrayList_status = new ArrayList<>();

        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
        Cursor cursor = SQLiteDatabase.rawQuery("SELECT id2, tegenstander, punten, kleur_speler, kleur_tegenstander, score_speler, score_tegenstander, beoordelen_speler, beoordelen_tegenstander, chat, profielfoto, thema, status FROM spellen ORDER BY datetime(datum) DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String id = cursor.getString(cursor.getColumnIndex("id2"));
            String tegenstander = cursor.getString(cursor.getColumnIndex("tegenstander"));
            String punten = cursor.getString(cursor.getColumnIndex("punten"));
            String kleur_speler = cursor.getString(cursor.getColumnIndex("kleur_speler"));
            String kleur_tegenstander = cursor.getString(cursor.getColumnIndex("kleur_tegenstander"));
            String score_speler = cursor.getString(cursor.getColumnIndex("score_speler"));
            String score_tegenstander = cursor.getString(cursor.getColumnIndex("score_tegenstander"));
            String beoordelen_speler = cursor.getString(cursor.getColumnIndex("beoordelen_speler"));
            String beoordelen_tegenstander = cursor.getString(cursor.getColumnIndex("beoordelen_tegenstander"));
            String chat = cursor.getString(cursor.getColumnIndex("chat"));
            String profielfoto = cursor.getString(cursor.getColumnIndex("profielfoto"));
            String thema = cursor.getString(cursor.getColumnIndex("thema"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            arrayList_id.add(id);
            arrayList_naam_speler.add(naam);
            arrayList_naam_tegenstander.add(tegenstander);
            arrayList_punten.add(punten);
            arrayList_kleur_speler.add(kleur_speler);
            arrayList_kleur_tegenstander.add(kleur_tegenstander);
            arrayList_score_speler.add(score_speler);
            arrayList_score_tegenstander.add(score_tegenstander);
            arrayList_beoordelen_speler.add(beoordelen_speler);
            arrayList_beoordelen_tegenstander.add(beoordelen_tegenstander);
            arrayList_chat.add(chat);
            arrayList_profielfoto.add(profielfoto);
            arrayList_thema.add(thema);
            arrayList_status.add(status);

            cursor.moveToNext();
        }
        cursor.close();
        SQLiteDatabase.close();

        ArrayAdapter arrayAdapter = new arrayAdapter(this, arrayList_naam_speler, arrayList_naam_tegenstander, arrayList_punten, arrayList_kleur_speler, arrayList_kleur_tegenstander, arrayList_score_speler, arrayList_score_tegenstander, arrayList_beoordelen_speler, arrayList_beoordelen_tegenstander, arrayList_chat, arrayList_profielfoto, arrayList_thema, arrayList_status);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                positie = position;
                String kleur_speler = arrayList_kleur_speler.get(position);
                String kleur_tegenstander = arrayList_kleur_tegenstander.get(position);
                id = arrayList_id.get(position);
                if (kleur_speler.equals("0")){

                    arrayList = new ArrayList<>();

                    if (!kleur_tegenstander.equals("blauw")){
                        arrayList.add("Blue");
                    }
                    if (!kleur_tegenstander.equals("groen")){
                        arrayList.add("Green");
                    }
                    if (!kleur_tegenstander.equals("oranje")){
                        arrayList.add("Orange");
                    }
                    if (!kleur_tegenstander.equals("paars")){
                        arrayList.add("Purple");
                    }
                    if (!kleur_tegenstander.equals("rood")){
                        arrayList.add("Red");
                    }

                    stringArray = arrayList.toArray(new String[arrayList.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Play Color")
                            .setItems(stringArray, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String gekozen = stringArray[which];
                                    switch (gekozen){
                                        case "Blue":
                                            kleur = "blauw";
                                            break;
                                        case "Green":
                                            kleur = "groen";
                                            break;
                                        case "Orange":
                                            kleur = "oranje";
                                            break;
                                        case "Purple":
                                            kleur = "paars";
                                            break;
                                        case "Red":
                                            kleur = "rood";
                                            break;
                                    }

                                    ProgressDialog = android.app.ProgressDialog.show(context, "Starting game", "One moment please..", true, false);
                                    new kleur_kiezen().execute();
                                }
                            });
                    builder.show();

                }else if (!kleur_tegenstander.equals("0")) {
                    //if (!arrayList_status.get(position).equals("VERWIJDERD")){
                        start(positie);
                    //}
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v, final int pos, long arg3) {

                Log.d("Viewfinder", ""+arrayList_status.get(pos));

                if (arrayList_punten.get(pos).equals(arrayList_score_speler.get(pos)) || arrayList_punten.get(pos).equals(arrayList_score_tegenstander.get(pos)) || arrayList_status.get(pos).equals("GEANNULEERD") ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Spellen.this);
                    builder.setTitle("Spel verwijderen")
                            .setMessage("Wil je het spel verwijderen?");
                    builder.setPositiveButton("JA", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            verwijder_id = arrayList_id.get(pos);
                            ProgressDialog = android.app.ProgressDialog.show(context, "Spel verwijderen", "One moment please..", true, false);
                            new spel_verwijderen().execute();
                        }
                    });
                    builder.setNegativeButton("NEE", null);
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Spellen.this);
                    builder.setTitle("Spel annuleren")
                            .setMessage("Wil je het spel annuleren?");
                    builder.setPositiveButton("JA", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            verwijder_id = arrayList_id.get(pos);
                            ProgressDialog = android.app.ProgressDialog.show(context, "Spel annuleren", "One moment please..", true, false);
                            new spel_verwijderen().execute();
                        }
                    });
                    builder.setNegativeButton("NEE", null);
                    builder.show();
                }
                return true;
            }
        });

        ProgressDialog.dismiss();
    }

    private class kleur_kiezen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/kleur_kiezen.php?nummer="+id+"&naam="+naam+"&keuze="+kleur);
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
            kleur_kiezen_klaar();
        }

    }

    public void kleur_kiezen_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding("Kleur kiezen niet mogelijk", "Kleur kiezen is op dit moment niet mogelijk..\nProbeer het later nog een keer.", context);
        }else{
            ProgressDialog = android.app.ProgressDialog.show(context, "Reloading games", "One moment please..", true, false);
            new spellen_laden().execute();
        }

    }

    private class spel_verwijderen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/spel_verwijderen.php?nummer="+verwijder_id+"&naam="+naam);
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
            spel_verwijderen_klaar();
        }

    }

    public void spel_verwijderen_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding("Spel verwijderen niet mogelijk", "Spel verwijderen is op dit moment niet mogelijk..\nProbeer het later nog een keer.", context);
        }else{
            SQLiteDatabase SQLiteDatabase = openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
            SQLiteDatabase.execSQL("DELETE FROM spellen WHERE id2='"+verwijder_id+"' ");
            ProgressDialog = android.app.ProgressDialog.show(context, "Reloading games", "One moment please..", true, false);
            new spellen_laden().execute();
        }

    }

    public void start(int positie){
        String id = arrayList_id.get(positie);
        String status = arrayList_status.get(positie);
        Intent intent = new Intent(this, Spel.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("status", status);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}