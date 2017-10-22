package nl.stefhost.spotashot;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.NotificationManager;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class Spellen extends AppCompatActivity {

    public android.app.ProgressDialog ProgressDialog;
    public Context context;

    public String id;
    public String naam;
    public String resultaat;
    public String tegenstander;
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
    public ArrayList<String> arrayList_speltype;
    public ArrayList<String> arrayList_beurt;
    public ArrayList<String> arrayList_taal;
    public ArrayList<String> arrayList_nummer;

    public ArrayList<String> arrayList;
    public ArrayList<String> arrayList_ids = new ArrayList<>();
    public String[] stringArray;
    String kleur;
    public int positie;
    public String verwijder_id;
    public String toestemming_profielfoto;

    private static final int WRITE_EXTERNAL_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spellen);

        context = Spellen.this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam = sharedPreferences.getString("naam", "");
        naam = naam.replace("%2520", " ");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        TextView textViewBoven = (TextView) findViewById(R.id.textview_boven);
        if (textViewBoven != null) {
            textViewBoven.setTypeface(typeface);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            toestemming_profielfoto = "nee";
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
        }else{
            toestemming_profielfoto = "ja";
        }

        String tip1 = sharedPreferences.getString("tip_1", "0");
        if (!tip1.equals("JA")){
            int aantal = Integer.parseInt(tip1);
            aantal++;
            if (aantal == 3){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tip_1", "JA");
                editor.apply();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.dialog_tips_nieuw, null);
                builder.setView(view);
                TextView textView = (TextView) view.findViewById(R.id.textView);
                textView.setText(getString(R.string.tip_1));
                builder.setPositiveButton(getString(R.string.ok), null);
                builder.setCancelable(false);
                builder.show();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tip_1", ""+aantal);
                editor.apply();
            }
        }

        // notificaties cancellen
        Set<String> stringSet = sharedPreferences.getStringSet("notificaties_spellen", new HashSet<String>());
        for (String string : stringSet) {
            int nummer = Integer.parseInt(string);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(nummer);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("notificaties_spellen", null);
        editor.apply();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toestemming_profielfoto = "ja";
                }else{
                    toestemming_profielfoto = "nee";
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.spellen_melding_11), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
                }
            }, 400);
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_1), getString(R.string.een_ogenblik_geduld), true, false);
                    new spellen_laden().execute();
                }
            }, 400);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidig_spel", "");
        editor.apply();
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

            String taal = Locale.getDefault().getLanguage();
            naam = naam.replace(" ", "%20");

            arrayList_html = new ArrayList<>();

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/spellen_laden.php?naam="+naam+"&versie="+versie+"&taal="+taal);
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
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
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
                    String speltype = stringTokenizer.nextToken();
                    String beurt = stringTokenizer.nextToken();
                    String taal = stringTokenizer.nextToken();

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
                        SQLiteDatabase.execSQL("INSERT INTO spellen (id2, tegenstander, punten, kleur_speler, kleur_tegenstander, score_speler, score_tegenstander, beoordelen_speler, beoordelen_tegenstander, chat, profielfoto, thema, status, datum, speltype, beurt, taal) VALUES ('"+id+"','"+tegenstander+"','"+punten+"','"+kleur_speler+"','"+kleur_tegenstander+"','"+score_speler+"','"+score_tegenstander+"','"+beoordelen_speler+"','"+beoordelen_tegenstander+"','"+chat+"','"+profielfoto+"','"+thema+"','"+status+"','"+datum+"', '"+speltype+"', '"+beurt+"', '"+taal+"')");
                    }else if (status.equals("VERWIJDERD")) {
                        SQLiteDatabase.execSQL("DELETE FROM spellen WHERE id2='"+id+"'");
                    }else{
                        SQLiteDatabase.execSQL("UPDATE spellen SET tegenstander='"+tegenstander+"', kleur_speler='"+kleur_speler+"', kleur_tegenstander='"+kleur_tegenstander+"', score_speler='"+score_speler+"', score_tegenstander='"+score_tegenstander+"', beoordelen_speler='"+beoordelen_speler+"', beoordelen_tegenstander='"+beoordelen_tegenstander+"', chat='"+chat+"', profielfoto='"+profielfoto+"', thema='"+thema+"', status='"+status+"', beurt='"+beurt+"', punten='"+punten+"' WHERE id2='"+id+"' ");
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
        arrayList_speltype = new ArrayList<>();
        arrayList_beurt = new ArrayList<>();
        arrayList_taal = new ArrayList<>();
        arrayList_nummer = new ArrayList<>();

        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);

        Cursor cursor1 = SQLiteDatabase.rawQuery("SELECT id2 FROM spellen", null);
        int aantal_spellen1 = cursor1.getCount();
        cursor1.close();

        Cursor cursor2 = SQLiteDatabase.rawQuery("SELECT id2 FROM spellen WHERE beurt='3' OR beurt='4'", null);
        int aantal_spellen2 = cursor2.getCount();
        cursor2.close();

        int tellen = 1;

        //Cursor cursor = SQLiteDatabase.rawQuery("SELECT id2, tegenstander, punten, kleur_speler, kleur_tegenstander, score_speler, score_tegenstander, beoordelen_speler, beoordelen_tegenstander, chat, profielfoto, thema, status, speltype, beurt, taal FROM spellen ORDER BY datetime(datum) DESC", null);
        Cursor cursor = SQLiteDatabase.rawQuery("SELECT id2, tegenstander, punten, kleur_speler, kleur_tegenstander, score_speler, score_tegenstander, beoordelen_speler, beoordelen_tegenstander, chat, profielfoto, thema, status, speltype, beurt, taal FROM spellen ORDER BY beurt ASC", null);
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
            String speltype = cursor.getString(cursor.getColumnIndex("speltype"));
            String beurt = cursor.getString(cursor.getColumnIndex("beurt"));
            String taal = cursor.getString(cursor.getColumnIndex("taal"));

            naam = naam.replace("%20", " ");

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
            arrayList_speltype.add(speltype);
            arrayList_beurt.add(beurt);
            arrayList_taal.add(taal);
            arrayList_nummer.add(""+tellen);

            tellen++;

            cursor.moveToNext();
        }
        cursor.close();
        SQLiteDatabase.close();

        ArrayAdapter arrayAdapter = new arrayAdapter(this, arrayList_id, arrayList_naam_speler, arrayList_naam_tegenstander, arrayList_punten, arrayList_kleur_speler, arrayList_kleur_tegenstander, arrayList_score_speler, arrayList_score_tegenstander, arrayList_beoordelen_speler, arrayList_beoordelen_tegenstander, arrayList_chat, arrayList_profielfoto, arrayList_thema, arrayList_status, toestemming_profielfoto, arrayList_speltype, arrayList_beurt, arrayList_taal, arrayList_nummer, aantal_spellen1, aantal_spellen2);
        final ListView listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        if (listView != null) {
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
                            arrayList.add(getString(R.string.kleur_1));
                        }
                        if (!kleur_tegenstander.equals("groen")){
                            arrayList.add(getString(R.string.kleur_2));
                        }
                        if (!kleur_tegenstander.equals("oranje")){
                            arrayList.add(getString(R.string.kleur_3));
                        }
                        if (!kleur_tegenstander.equals("paars")){
                            arrayList.add(getString(R.string.kleur_4));
                        }
                        if (!kleur_tegenstander.equals("rood")){
                            arrayList.add(getString(R.string.kleur_5));
                        }

                        stringArray = arrayList.toArray(new String[arrayList.size()]);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(getString(R.string.nieuw_spel_opties_2))
                                .setItems(stringArray, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String gekozen = stringArray[which];

                                        if (gekozen.equals(getString(R.string.kleur_1))){
                                            kleur = "blauw";
                                        }else if (gekozen.equals(getString(R.string.kleur_2))){
                                            kleur = "groen";
                                        }else if (gekozen.equals(getString(R.string.kleur_3))){
                                            kleur = "oranje";
                                        }else if (gekozen.equals(getString(R.string.kleur_4))){
                                            kleur = "paars";
                                        }else{
                                            kleur = "rood";
                                        }

                                        ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_2), getString(R.string.een_ogenblik_geduld), true, false);
                                        new kleur_kiezen().execute();
                                    }
                                });
                        builder.show();

                    }else if (!kleur_tegenstander.equals("0")) {
                        start(positie);
                    }
                }
            });
        }

        ProgressDialog.dismiss();
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = info.position;

        if (view.getId() == R.id.listView) {
            MenuInflater inflater = getMenuInflater();

            if (arrayList_punten.get(position).equals(arrayList_score_speler.get(position)) || arrayList_punten.get(position).equals(arrayList_score_tegenstander.get(position))) {
                inflater.inflate(R.menu.spellen_2, contextMenu);
            }else if (arrayList_status.get(position).equals("GEANNULEERD")){
                inflater.inflate(R.menu.spellen_2, contextMenu);
            }else{
                inflater.inflate(R.menu.spellen_1, contextMenu);
                MenuItem menuItem2 = contextMenu.findItem(R.id.menu_spellen_2);
                MenuItem menuItem3 = contextMenu.findItem(R.id.menu_spellen_3);
                if (Locale.getDefault().getLanguage().equals("nl") || Locale.getDefault().getLanguage().equals("de")){
                    menuItem2.setTitle(arrayList_naam_tegenstander.get(position) + " " + getString(R.string.spellen_menu_2));
                    menuItem3.setTitle(arrayList_naam_tegenstander.get(position) + " " + getString(R.string.spellen_menu_3));
                }else{
                    menuItem2.setTitle(getString(R.string.spellen_menu_2_1) + " " + arrayList_naam_tegenstander.get(position) + " " + getString(R.string.spellen_menu_2_2));
                    menuItem3.setTitle(getString(R.string.spellen_menu_3) + " " + arrayList_naam_tegenstander.get(position));
                }
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;

        AlertDialog.Builder builder = new AlertDialog.Builder(Spellen.this);

        switch(item.getItemId()) {
            case R.id.menu_spellen_1:
                builder.setTitle(getString(R.string.spellen_melding_3_titel))
                        .setMessage(getString(R.string.spellen_melding_3_tekst));
                builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        verwijder_id = arrayList_id.get(position);
                        ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_4), getString(R.string.een_ogenblik_geduld), true, false);
                        new spel_verwijderen().execute();
                    }
                });
                builder.setNegativeButton(getString(R.string.nee), null);
                builder.show();
                return true;

            case R.id.menu_spellen_2:
                SharedPreferences sharedPreferences = this.getSharedPreferences("opties", Context.MODE_PRIVATE);
                Set<String> stringSet1 = sharedPreferences.getStringSet("adresboek", new HashSet<String>());

                ArrayList<String> arrayList_vrienden = new ArrayList<>();
                for (String string : stringSet1) {
                    string = string.replace("%20", " ");
                    arrayList_vrienden.add(string);
                }

                String naam = arrayList_naam_tegenstander.get(position);
                arrayList_vrienden.add(naam);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("adresboek", new HashSet<>(arrayList_vrienden));
                editor.apply();

                Toast toast = Toast.makeText(this.getApplicationContext(), naam + " " + getString(R.string.nieuw_spel_melding_2), Toast.LENGTH_SHORT);
                toast.show();
                return true;

            case R.id.menu_spellen_3:
                tegenstander = arrayList_naam_tegenstander.get(position);
                builder.setTitle(getString(R.string.spellen_melding_5_titel))
                        .setMessage(getString(R.string.spellen_melding_5_tekst1)+" "+tegenstander+" "+getString(R.string.spellen_melding_5_tekst2));
                builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tegenstander = arrayList_naam_tegenstander.get(position);
                        ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_6), getString(R.string.een_ogenblik_geduld), true, false);
                        new tegenstander_blokkeren().execute();
                    }
                });
                builder.setNegativeButton(getString(R.string.nee), null);
                builder.show();
                return true;

            case R.id.menu_spellen_4:
                builder.setTitle(getString(R.string.spellen_melding_7_titel))
                        .setMessage(getString(R.string.spellen_melding_7_tekst));
                builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        verwijder_id = arrayList_id.get(position);
                        ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_8), getString(R.string.een_ogenblik_geduld), true, false);
                        new spel_verwijderen().execute();
                    }
                });
                builder.setNegativeButton(getString(R.string.nee), null);
                builder.show();
                return true;

            case R.id.menu_spellen_5:
                Intent intent = new Intent(this, Nieuw_spel.class);

                intent.putExtra("punten", arrayList_punten.get(position));
                intent.putExtra("kleur", arrayList_kleur_speler.get(position));
                intent.putExtra("thema", arrayList_thema.get(position));
                intent.putExtra("speltype", arrayList_speltype.get(position));
                intent.putExtra("tegenstander", arrayList_naam_tegenstander.get(position));

                intent.putExtra("muntjes", getIntent().getExtras().getString("muntjes"));
                intent.putExtra("themas", getIntent().getExtras().getString("themas"));
                intent.putExtra("thema1a", getIntent().getExtras().getString("thema1a"));
                intent.putExtra("thema1b", getIntent().getExtras().getString("thema1b"));
                intent.putExtra("thema2a", getIntent().getExtras().getString("thema2a"));
                intent.putExtra("thema2b", getIntent().getExtras().getString("thema2b"));
                intent.putExtra("thema3a", getIntent().getExtras().getString("thema3a"));
                intent.putExtra("thema3b", getIntent().getExtras().getString("thema3b"));
                intent.putExtra("thema4a", getIntent().getExtras().getString("thema4a"));
                intent.putExtra("thema4b", getIntent().getExtras().getString("thema4b"));
                intent.putExtra("thema5a", getIntent().getExtras().getString("thema5a"));
                intent.putExtra("thema5b", getIntent().getExtras().getString("thema5b"));

                startActivity(intent);
                finish();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private class kleur_kiezen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_kleur = naam.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/kleur_kiezen.php?nummer="+id+"&naam="+naam_kleur+"&keuze="+kleur);
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
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else{
            ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_9), getString(R.string.een_ogenblik_geduld), true, false);
            new spellen_laden().execute();
        }

    }

    private class spel_verwijderen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            String laatste_spel = sharedPreferences.getString("laatste_spel", "");

            String naam_verwijder = naam.replace(" ", "%20");

            if (laatste_spel.equals(verwijder_id)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("laatste_spel", "");
                editor.apply();
            }

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/spel_verwijderen.php?nummer="+verwijder_id+"&naam="+naam_verwijder);
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
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else{
            SQLiteDatabase SQLiteDatabase = openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
            SQLiteDatabase.execSQL("DELETE FROM spellen WHERE id2='"+verwijder_id+"' ");

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("popup_1"+verwijder_id);
            editor.apply();

            ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_9), getString(R.string.een_ogenblik_geduld), true, false);
            new spellen_laden().execute();
        }

    }

    private class tegenstander_blokkeren extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_blokkeren = naam.replace(" ", "%20");
            String tegenstander_blokkeren = tegenstander.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/tegenstander_blokkeren.php?naam="+naam_blokkeren+"&tegenstander="+tegenstander_blokkeren);
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
            tegenstander_blokkeren_klaar();
        }

        public void tegenstander_blokkeren_klaar(){

            if (resultaat.matches("ERROR")) {
                _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
            }else{
                Toast toast = Toast.makeText(context, getString(R.string.spellen_melding_10), Toast.LENGTH_SHORT);
                toast.show();
            }

        }

    }


    public void start(int positie){
        String id = arrayList_id.get(positie);
        String status = arrayList_status.get(positie);
        String thema = arrayList_thema.get(positie);

        if (!status.equals("GEANNULEERD")) {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.spelbalk);
            Intent intent = new Intent(this, Spel.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("status", status);
            bundle.putString("thema", thema);
            intent.putExtras(bundle);
            if (Build.VERSION.SDK_INT > 21) {
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, relativeLayout, "Spel");
                startActivity(intent, activityOptions.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    public void chat (View view){
        String status = arrayList_status.get(positie);
        if (!status.equals("GEANNULEERD")) {
            String id = (String) view.getTag();
            Intent intent = new Intent(Spellen.this, Chat.class);
            intent.putExtra("nummer", id);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void profiel_laden(View view) {

    }

}