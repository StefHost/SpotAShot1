package nl.stefhost.spotashot;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import nl.stefhost.spotashot.functies.download;
import nl.stefhost.spotashot.functies.laad_plaatje_textview;

public class Spel extends AppCompatActivity {

    public Context context;
    public Activity activity;
    public String resultaat;

    public String id;
    public String naam_speler;
    public String naam_tegenstander;
    public String punten;
    public String kleur_speler;
    public String kleur_tegenstander;
    public String score_speler;
    public String score_tegenstander;
    public String beoordelen_speler;
    public String beoordelen_tegenstander;
    public String chat;
    public String profielfoto;
    public String muntjes;
    public String muntjes_nieuw;
    public String thema;

    public String onderwerp1;
    public String onderwerp2;
    public String onderwerp3;
    public String onderwerp4;
    public String onderwerp5;
    public String onderwerp6;
    public String onderwerp7;
    public String onderwerp8;
    public String onderwerp9;

    public String onderwerp1_nummer;
    public String onderwerp2_nummer;
    public String onderwerp3_nummer;
    public String onderwerp4_nummer;
    public String onderwerp5_nummer;
    public String onderwerp6_nummer;
    public String onderwerp7_nummer;
    public String onderwerp8_nummer;
    public String onderwerp9_nummer;

    public String kleur1;
    public String kleur2;
    public String kleur3;
    public String kleur4;
    public String kleur5;
    public String kleur6;
    public String kleur7;
    public String kleur8;
    public String kleur9;

    public int tijd1_uren;
    public int tijd2_uren;
    public int tijd3_uren;
    public int tijd4_uren;
    public int tijd5_uren;
    public int tijd6_uren;
    public int tijd7_uren;
    public int tijd8_uren;
    public int tijd9_uren;
    public int tijd_beurt_uren;

    public int tijd1_minuten;
    public int tijd2_minuten;
    public int tijd3_minuten;
    public int tijd4_minuten;
    public int tijd5_minuten;
    public int tijd6_minuten;
    public int tijd7_minuten;
    public int tijd8_minuten;
    public int tijd9_minuten;
    public int tijd_beurt_minuten;

    public int tijd1_seconden;
    public int tijd2_seconden;
    public int tijd3_seconden;
    public int tijd4_seconden;
    public int tijd5_seconden;
    public int tijd6_seconden;
    public int tijd7_seconden;
    public int tijd8_seconden;
    public int tijd9_seconden;
    public int tijd_beurt_seconden;

    public float vakje1_aan;
    public float vakje2_aan;
    public float vakje3_aan;
    public float vakje4_aan;
    public float vakje5_aan;
    public float vakje6_aan;
    public float vakje7_aan;
    public float vakje8_aan;
    public float vakje9_aan;

    public String html_kleur_speler;
    public String html_kleur_tegenstander;
    public String nummer;
    public String animatie;
    public String aantal;
    public String gewonnen;
    public String status;
    public String speltype;
    public String beurt;
    public String beurt_tellen;

    public Handler handler;

    public ImageView imageview_foto_tegenstander;

    public ImageView imageview_punten_speler_1;
    public ImageView imageview_punten_speler_2;
    public ImageView imageview_punten_speler_3;
    public ImageView imageview_punten_tegenstander_1;
    public ImageView imageview_punten_tegenstander_2;
    public ImageView imageview_punten_tegenstander_3;

    public ImageView imageview_beoordelen_speler;
    public ImageView imageview_beoordelen_tegenstander;
    public ImageView imageview_beurt_speler;
    public ImageView imageview_beurt_tegenstander;
    public TextView rondje_1;
    public TextView rondje_2;

    public ImageView imageview_tijd_1_aan;
    public ImageView imageview_tijd_1_uit;
    public ImageView imageview_tijd_2_aan;
    public ImageView imageview_tijd_2_uit;
    public ImageView imageview_tijd_3_aan;
    public ImageView imageview_tijd_3_uit;
    public ImageView imageview_tijd_4_aan;
    public ImageView imageview_tijd_4_uit;
    public ImageView imageview_tijd_5_aan;
    public ImageView imageview_tijd_5_uit;
    public ImageView imageview_tijd_6_aan;
    public ImageView imageview_tijd_6_uit;
    public ImageView imageview_tijd_7_aan;
    public ImageView imageview_tijd_7_uit;
    public ImageView imageview_tijd_8_aan;
    public ImageView imageview_tijd_8_uit;
    public ImageView imageview_tijd_9_aan;
    public ImageView imageview_tijd_9_uit;

    public TextView textview_naam_speler;
    public TextView textview_naam_tegenstander;
    public TextView textview_chat;

    public TextView textview_achtergrond_1;
    public TextView textview_achtergrond_2;
    public TextView textview_achtergrond_3;
    public TextView textview_achtergrond_4;
    public TextView textview_achtergrond_5;
    public TextView textview_achtergrond_6;
    public TextView textview_achtergrond_7;
    public TextView textview_achtergrond_8;
    public TextView textview_achtergrond_9;

    public TextView textview_achtergrond_1v;
    public TextView textview_achtergrond_2v;
    public TextView textview_achtergrond_3v;
    public TextView textview_achtergrond_4v;
    public TextView textview_achtergrond_5v;
    public TextView textview_achtergrond_6v;
    public TextView textview_achtergrond_7v;
    public TextView textview_achtergrond_8v;
    public TextView textview_achtergrond_9v;

    public TextView textview_schaduw_1;
    public TextView textview_schaduw_2;
    public TextView textview_schaduw_3;
    public TextView textview_schaduw_4;
    public TextView textview_schaduw_5;
    public TextView textview_schaduw_6;
    public TextView textview_schaduw_7;
    public TextView textview_schaduw_8;
    public TextView textview_schaduw_9;

    public TextView textview_spelvak_1;
    public TextView textview_spelvak_2;
    public TextView textview_spelvak_3;
    public TextView textview_spelvak_4;
    public TextView textview_spelvak_5;
    public TextView textview_spelvak_6;
    public TextView textview_spelvak_7;
    public TextView textview_spelvak_8;
    public TextView textview_spelvak_9;

    public TextView textview_kruis_1;
    public TextView textview_kruis_2;
    public TextView textview_kruis_3;
    public TextView textview_kruis_4;
    public TextView textview_kruis_5;
    public TextView textview_kruis_6;
    public TextView textview_kruis_7;
    public TextView textview_kruis_8;
    public TextView textview_kruis_9;

    public TextView textview_gewonnen;

    public RelativeLayout BeoordelenSpeler;
    public RelativeLayout BeoordelenTegenstander;

    public Handler handler1;
    public Handler handler2;

    public int speler_aan;
    public int speler_uit;
    public int tegenstander_aan;
    public int tegenstander_uit;

    public int kleur_Speler;
    public int kleur_Tegenstander;
    public int goed_Speler;
    public int goed_Tegenstander;
    public int achtergrond_speler;
    public int achtergrond_tegenstander;

    public ImageView[] imageViewPuntenSpeler;
    public ImageView[] imageViewPuntenTegenstander;

    public Drawable kruis_speler;
    public Drawable kruis_tegenstander;
    public Drawable rondje_aan_speler;
    public Drawable rondje_aan_tegenstander;

    public String animatie_bezig = "nee";
    public ArrayList<String> arrayList_vrienden;
    public android.app.ProgressDialog ProgressDialog;

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spel);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        String reclame = getString(R.string.reclame);

        if (reclame.equals("ja")) {

            AdRequest adRequest1 = new AdRequest.Builder().build();
            AdView mAdView = (AdView) findViewById(R.id.adView);
            if (mAdView != null) {
                mAdView.loadAd(adRequest1);
            }

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
            AdRequest adRequest2 = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest2);
        }

        context = Spel.this;
        activity = this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        id = getIntent().getExtras().getString("id");

        status = getIntent().getExtras().getString("status");
        if (status == null){
            status = "OK";
        }
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam_speler = sharedPreferences.getString("naam", "");
        naam_speler = naam_speler.replace("%2520", " ");

        // laatste spel bovenaan
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String datum = sdf.format(new Date());
        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
        SQLiteDatabase.execSQL("UPDATE spellen SET datum='"+datum+"' WHERE id2='"+id+"' ");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_spel", id);
        editor.apply();

        imageview_foto_tegenstander = (ImageView) findViewById(R.id.foto_tegenstander);

        imageview_punten_speler_1 = (ImageView) findViewById(R.id.punten_speler_1);
        imageview_punten_speler_2 = (ImageView) findViewById(R.id.punten_speler_2);
        imageview_punten_speler_3 = (ImageView) findViewById(R.id.punten_speler_3);
        imageview_punten_tegenstander_1 = (ImageView) findViewById(R.id.punten_tegenstander_1);
        imageview_punten_tegenstander_2 = (ImageView) findViewById(R.id.punten_tegenstander_2);
        imageview_punten_tegenstander_3 = (ImageView) findViewById(R.id.punten_tegenstander_3);

        imageview_beoordelen_speler = (ImageView) findViewById(R.id.beoordelen_speler);
        imageview_beoordelen_tegenstander = (ImageView) findViewById(R.id.beoordelen_tegenstander);
        imageview_beurt_speler = (ImageView) findViewById(R.id.beurt_speler);
        imageview_beurt_tegenstander = (ImageView) findViewById(R.id.beurt_tegenstander);
        rondje_1 = (TextView) findViewById(R.id.rondje_1);
        rondje_2 = (TextView) findViewById(R.id.rondje_2);

        imageview_tijd_1_aan = (ImageView) findViewById(R.id.tijd_1_aan);
        imageview_tijd_1_uit = (ImageView) findViewById(R.id.tijd_1_uit);
        imageview_tijd_2_aan = (ImageView) findViewById(R.id.tijd_2_aan);
        imageview_tijd_2_uit = (ImageView) findViewById(R.id.tijd_2_uit);
        imageview_tijd_3_aan = (ImageView) findViewById(R.id.tijd_3_aan);
        imageview_tijd_3_uit = (ImageView) findViewById(R.id.tijd_3_uit);
        imageview_tijd_4_aan = (ImageView) findViewById(R.id.tijd_4_aan);
        imageview_tijd_4_uit = (ImageView) findViewById(R.id.tijd_4_uit);
        imageview_tijd_5_aan = (ImageView) findViewById(R.id.tijd_5_aan);
        imageview_tijd_5_uit = (ImageView) findViewById(R.id.tijd_5_uit);
        imageview_tijd_6_aan = (ImageView) findViewById(R.id.tijd_6_aan);
        imageview_tijd_6_uit = (ImageView) findViewById(R.id.tijd_6_uit);
        imageview_tijd_7_aan = (ImageView) findViewById(R.id.tijd_7_aan);
        imageview_tijd_7_uit = (ImageView) findViewById(R.id.tijd_7_uit);
        imageview_tijd_8_aan = (ImageView) findViewById(R.id.tijd_8_aan);
        imageview_tijd_8_uit = (ImageView) findViewById(R.id.tijd_8_uit);
        imageview_tijd_9_aan = (ImageView) findViewById(R.id.tijd_9_aan);
        imageview_tijd_9_uit = (ImageView) findViewById(R.id.tijd_9_uit);

        textview_naam_speler = (TextView) findViewById(R.id.naam_speler);
        textview_naam_tegenstander = (TextView) findViewById(R.id.naam_tegenstander);
        textview_chat = (TextView) findViewById(R.id.chat);

        textview_achtergrond_1 = (TextView) findViewById(R.id.achtergrond_1);
        textview_achtergrond_2 = (TextView) findViewById(R.id.achtergrond_2);
        textview_achtergrond_3 = (TextView) findViewById(R.id.achtergrond_3);
        textview_achtergrond_4 = (TextView) findViewById(R.id.achtergrond_4);
        textview_achtergrond_5 = (TextView) findViewById(R.id.achtergrond_5);
        textview_achtergrond_6 = (TextView) findViewById(R.id.achtergrond_6);
        textview_achtergrond_7 = (TextView) findViewById(R.id.achtergrond_7);
        textview_achtergrond_8 = (TextView) findViewById(R.id.achtergrond_8);
        textview_achtergrond_9 = (TextView) findViewById(R.id.achtergrond_9);

        textview_achtergrond_1v = (TextView) findViewById(R.id.achtergrond_1v);
        textview_achtergrond_2v = (TextView) findViewById(R.id.achtergrond_2v);
        textview_achtergrond_3v = (TextView) findViewById(R.id.achtergrond_3v);
        textview_achtergrond_4v = (TextView) findViewById(R.id.achtergrond_4v);
        textview_achtergrond_5v = (TextView) findViewById(R.id.achtergrond_5v);
        textview_achtergrond_6v = (TextView) findViewById(R.id.achtergrond_6v);
        textview_achtergrond_7v = (TextView) findViewById(R.id.achtergrond_7v);
        textview_achtergrond_8v = (TextView) findViewById(R.id.achtergrond_8v);
        textview_achtergrond_9v = (TextView) findViewById(R.id.achtergrond_9v);

        textview_schaduw_1 = (TextView) findViewById(R.id.schaduw_1);
        textview_schaduw_2 = (TextView) findViewById(R.id.schaduw_2);
        textview_schaduw_3 = (TextView) findViewById(R.id.schaduw_3);
        textview_schaduw_4 = (TextView) findViewById(R.id.schaduw_4);
        textview_schaduw_5 = (TextView) findViewById(R.id.schaduw_5);
        textview_schaduw_6 = (TextView) findViewById(R.id.schaduw_6);
        textview_schaduw_7 = (TextView) findViewById(R.id.schaduw_7);
        textview_schaduw_8 = (TextView) findViewById(R.id.schaduw_8);
        textview_schaduw_9 = (TextView) findViewById(R.id.schaduw_9);

        textview_spelvak_1 = (TextView) findViewById(R.id.spelvak_1);
        textview_spelvak_2 = (TextView) findViewById(R.id.spelvak_2);
        textview_spelvak_3 = (TextView) findViewById(R.id.spelvak_3);
        textview_spelvak_4 = (TextView) findViewById(R.id.spelvak_4);
        textview_spelvak_5 = (TextView) findViewById(R.id.spelvak_5);
        textview_spelvak_6 = (TextView) findViewById(R.id.spelvak_6);
        textview_spelvak_7 = (TextView) findViewById(R.id.spelvak_7);
        textview_spelvak_8 = (TextView) findViewById(R.id.spelvak_8);
        textview_spelvak_9 = (TextView) findViewById(R.id.spelvak_9);

        textview_kruis_1 = (TextView) findViewById(R.id.kruis_1);
        textview_kruis_2 = (TextView) findViewById(R.id.kruis_2);
        textview_kruis_3 = (TextView) findViewById(R.id.kruis_3);
        textview_kruis_4 = (TextView) findViewById(R.id.kruis_4);
        textview_kruis_5 = (TextView) findViewById(R.id.kruis_5);
        textview_kruis_6 = (TextView) findViewById(R.id.kruis_6);
        textview_kruis_7 = (TextView) findViewById(R.id.kruis_7);
        textview_kruis_8 = (TextView) findViewById(R.id.kruis_8);
        textview_kruis_9 = (TextView) findViewById(R.id.kruis_9);

        textview_gewonnen = (TextView) findViewById(R.id.gewonnen);

        BeoordelenSpeler = (RelativeLayout) findViewById(R.id.BeoordelenSpeler);
        BeoordelenTegenstander = (RelativeLayout) findViewById(R.id.BeoordelenTegenstander);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        textview_naam_speler.setTypeface(typeface);
        textview_naam_tegenstander.setTypeface(typeface);
        textview_chat.setTypeface(typeface);
        textview_gewonnen.setTypeface(typeface);
        textview_spelvak_1.setTypeface(typeface);
        textview_spelvak_2.setTypeface(typeface);
        textview_spelvak_3.setTypeface(typeface);
        textview_spelvak_4.setTypeface(typeface);
        textview_spelvak_5.setTypeface(typeface);
        textview_spelvak_6.setTypeface(typeface);
        textview_spelvak_7.setTypeface(typeface);
        textview_spelvak_8.setTypeface(typeface);
        textview_spelvak_9.setTypeface(typeface);

        handler = new Handler();
        handler.postDelayed(timer, 1000);

        // notificaties cancellen
        Set<String> stringSet = sharedPreferences.getStringSet("notificaties_spel_"+id, new HashSet<String>());
        for (String string : stringSet) {
            int nummer = Integer.parseInt(string);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(nummer);
        }
        editor.putStringSet("notificaties_spel_"+id, null);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        String upload = sharedPreferences.getString("upload", "");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_spel", id);
        editor.apply();

        if (upload.equals("NEE")) {
            _functions.melding(getString(R.string.spel_melding_1_titel), getString(R.string.spel_melding_1_tekst), context);
            editor.putString("upload", "");
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else{
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.animatie_rotatie);
            ImageView imageView = (ImageView) findViewById(R.id.laden);
            imageView.startAnimation(animation);
            new spel_laden().execute();
        }

        editor.putString("herladen", "nee");
        editor.apply();

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_spel", "0");
        editor.apply();
    }

    private class spel_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            String taal = Locale.getDefault().getLanguage();
            String naam_laden = naam_speler.replace(" ", "%20");

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/spel_laden.php?nummer="+id+"&naam="+naam_laden+"&taal="+taal);
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
            spel_laden_klaar();
        }

    }

    public void spel_laden_klaar(){


        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.INVISIBLE);

        if (resultaat.matches("ERROR")) {
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else{
            StringTokenizer tokens = new StringTokenizer(resultaat, "|");
            Log.d("Viewfinder", "klaar: "+resultaat);

            naam_tegenstander = tokens.nextToken();
            punten = tokens.nextToken();
            kleur_speler = tokens.nextToken();
            kleur_tegenstander = tokens.nextToken();
            score_speler = tokens.nextToken();
            score_tegenstander = tokens.nextToken();
            beoordelen_speler = tokens.nextToken();
            beoordelen_tegenstander = tokens.nextToken();
            chat = tokens.nextToken();

            onderwerp1 = tokens.nextToken();
            onderwerp2 = tokens.nextToken();
            onderwerp3 = tokens.nextToken();
            onderwerp4 = tokens.nextToken();
            onderwerp5 = tokens.nextToken();
            onderwerp6 = tokens.nextToken();
            onderwerp7 = tokens.nextToken();
            onderwerp8 = tokens.nextToken();
            onderwerp9 = tokens.nextToken();

            onderwerp1 = onderwerp1.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp2 = onderwerp2.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp3 = onderwerp3.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp4 = onderwerp4.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp5 = onderwerp5.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp6 = onderwerp6.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp7 = onderwerp7.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp8 = onderwerp8.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");
            onderwerp9 = onderwerp9.replace("[a1]","á").replace("[a2]","ä").replace("[i1]","í").replace("[i2]","ï").replace("[e1]","é").replace("[e2]","ë").replace("[o1]","ó").replace("[o2]","ö").replace("[u1]","ú").replace("[u2]","ü").replace("[puntjes]","…").replace("[s]","ß");

            kleur1 = tokens.nextToken();
            kleur2 = tokens.nextToken();
            kleur3 = tokens.nextToken();
            kleur4 = tokens.nextToken();
            kleur5 = tokens.nextToken();
            kleur6 = tokens.nextToken();
            kleur7 = tokens.nextToken();
            kleur8 = tokens.nextToken();
            kleur9 = tokens.nextToken();

            StringTokenizer tijd_1 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_2 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_3 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_4 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_5 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_6 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_7 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_8 = new StringTokenizer(tokens.nextToken(), ":");
            StringTokenizer tijd_9 = new StringTokenizer(tokens.nextToken(), ":");

            tijd1_uren = Integer.parseInt(tijd_1.nextToken());
            tijd1_minuten = Integer.parseInt(tijd_1.nextToken());
            tijd1_seconden = Integer.parseInt(tijd_1.nextToken());
            tijd2_uren = Integer.parseInt(tijd_2.nextToken());
            tijd2_minuten = Integer.parseInt(tijd_2.nextToken());
            tijd2_seconden = Integer.parseInt(tijd_2.nextToken());
            tijd3_uren = Integer.parseInt(tijd_3.nextToken());
            tijd3_minuten = Integer.parseInt(tijd_3.nextToken());
            tijd3_seconden = Integer.parseInt(tijd_3.nextToken());
            tijd4_uren = Integer.parseInt(tijd_4.nextToken());
            tijd4_minuten = Integer.parseInt(tijd_4.nextToken());
            tijd4_seconden = Integer.parseInt(tijd_4.nextToken());
            tijd5_uren = Integer.parseInt(tijd_5.nextToken());
            tijd5_minuten = Integer.parseInt(tijd_5.nextToken());
            tijd5_seconden = Integer.parseInt(tijd_5.nextToken());
            tijd6_uren = Integer.parseInt(tijd_6.nextToken());
            tijd6_minuten = Integer.parseInt(tijd_6.nextToken());
            tijd6_seconden = Integer.parseInt(tijd_6.nextToken());
            tijd7_uren = Integer.parseInt(tijd_7.nextToken());
            tijd7_minuten = Integer.parseInt(tijd_7.nextToken());
            tijd7_seconden = Integer.parseInt(tijd_7.nextToken());
            tijd8_uren = Integer.parseInt(tijd_8.nextToken());
            tijd8_minuten = Integer.parseInt(tijd_8.nextToken());
            tijd8_seconden = Integer.parseInt(tijd_8.nextToken());
            tijd9_uren = Integer.parseInt(tijd_9.nextToken());
            tijd9_minuten = Integer.parseInt(tijd_9.nextToken());
            tijd9_seconden = Integer.parseInt(tijd_9.nextToken());

            animatie = tokens.nextToken();
            aantal = tokens.nextToken();
            gewonnen = tokens.nextToken();
            profielfoto = tokens.nextToken();

            onderwerp1_nummer = tokens.nextToken();
            onderwerp2_nummer = tokens.nextToken();
            onderwerp3_nummer = tokens.nextToken();
            onderwerp4_nummer = tokens.nextToken();
            onderwerp5_nummer = tokens.nextToken();
            onderwerp6_nummer = tokens.nextToken();
            onderwerp7_nummer = tokens.nextToken();
            onderwerp8_nummer = tokens.nextToken();
            onderwerp9_nummer = tokens.nextToken();

            speltype = tokens.nextToken();
            beurt = tokens.nextToken();

            StringTokenizer tijd_beurt = new StringTokenizer(tokens.nextToken(), ":");

            muntjes = tokens.nextToken();
            thema = tokens.nextToken();
            beurt_tellen = tokens.nextToken();

            tijd_beurt_uren = Integer.parseInt(tijd_beurt.nextToken());
            tijd_beurt_minuten = Integer.parseInt(tijd_beurt.nextToken());
            tijd_beurt_seconden = Integer.parseInt(tijd_beurt.nextToken());

            File opslag = Environment.getExternalStorageDirectory();
            String tegenstander_foto = naam_tegenstander.replace(" ", "%20");
            File bestand_profielfoto = new File(opslag,"/Spotashot/profielfotos/"+tegenstander_foto+"_"+profielfoto+".jpg");
            if(bestand_profielfoto.exists()){
                final Bitmap myBitmap = BitmapFactory.decodeFile(bestand_profielfoto.getAbsolutePath());
                imageview_foto_tegenstander.post(new Runnable() {
                    public void run() {
                        imageview_foto_tegenstander.setImageBitmap(myBitmap);
                    }
                });
            }

            kleur_Speler = 0;
            int kleur_Speler_uit = 0;
            int oog_speler = 0;
            int oog_speler_uit = 0;

            switch (kleur_speler){
                case "groen":
                    kleur_Speler = R.drawable.kleur_1;
                    kleur_Speler_uit = R.drawable.kleur_1_uit;
                    oog_speler = R.drawable.oog_1;
                    oog_speler_uit = R.drawable.oog_1_uit;
                    html_kleur_speler = "#598527";
                    achtergrond_speler = R.drawable.achtergrond_3_groen;
                    speler_aan = R.drawable.groen;
                    speler_uit = R.drawable.groen_uit;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.cancel_groen);
                    goed_Speler = R.drawable.goed_1;
                    break;
                case "blauw":
                    kleur_Speler = R.drawable.kleur_2;
                    kleur_Speler_uit = R.drawable.kleur_2_uit;
                    oog_speler = R.drawable.oog_2;
                    oog_speler_uit = R.drawable.oog_2_uit;
                    html_kleur_speler = "#0072bc";
                    achtergrond_speler = R.drawable.achtergrond_3_blauw;
                    speler_aan = R.drawable.blauw;
                    speler_uit = R.drawable.blauw_uit;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.cancel_blauw);
                    goed_Speler = R.drawable.goed_2;
                    break;
                case "paars":
                    kleur_Speler = R.drawable.kleur_3;
                    kleur_Speler_uit = R.drawable.kleur_3_uit;
                    oog_speler = R.drawable.oog_3;
                    oog_speler_uit = R.drawable.oog_3_uit;
                    html_kleur_speler = "#755295";
                    achtergrond_speler = R.drawable.achtergrond_3_paars;
                    speler_aan = R.drawable.paars;
                    speler_uit = R.drawable.paars_uit;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.cancel_paars);
                    goed_Speler = R.drawable.goed_3;
                    break;
                case "oranje":
                    kleur_Speler = R.drawable.kleur_4;
                    kleur_Speler_uit = R.drawable.kleur_4_uit;
                    oog_speler = R.drawable.oog_4;
                    oog_speler_uit = R.drawable.oog_4_uit;
                    html_kleur_speler = "#f58040";
                    achtergrond_speler = R.drawable.achtergrond_3_oranje;
                    speler_aan = R.drawable.oranje;
                    speler_uit = R.drawable.oranje_uit;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.cancel_oranje);
                    goed_Speler = R.drawable.goed_4;
                    break;
                case "rood":
                    kleur_Speler = R.drawable.kleur_5;
                    kleur_Speler_uit = R.drawable.kleur_5_uit;
                    oog_speler = R.drawable.oog_5;
                    oog_speler_uit = R.drawable.oog_5_uit;
                    html_kleur_speler = "#970a0e";
                    achtergrond_speler = R.drawable.achtergrond_3_rood;
                    speler_aan = R.drawable.rood;
                    speler_uit = R.drawable.rood_uit;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.cancel_rood);
                    goed_Speler = R.drawable.goed_5;
                    break;
            }

            kleur_Tegenstander = 0;
            int kleur_Tegenstander_uit = 0;
            int oog_tegenstander = 0;
            int oog_tegenstander_uit = 0;

            switch (kleur_tegenstander){
                case "groen":
                    kleur_Tegenstander = R.drawable.kleur_1;
                    kleur_Tegenstander_uit = R.drawable.kleur_1_uit;
                    oog_tegenstander = R.drawable.oog_1;
                    oog_tegenstander_uit = R.drawable.oog_1_uit;
                    html_kleur_tegenstander = "#598527";
                    achtergrond_tegenstander = R.drawable.achtergrond_3_groen;
                    tegenstander_aan = R.drawable.groen;
                    tegenstander_uit = R.drawable.groen_uit;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.cancel_groen);
                    goed_Tegenstander = R.drawable.goed_1;
                    break;
                case "blauw":
                    kleur_Tegenstander = R.drawable.kleur_2;
                    kleur_Tegenstander_uit = R.drawable.kleur_2_uit;
                    oog_tegenstander = R.drawable.oog_2;
                    oog_tegenstander_uit = R.drawable.oog_2_uit;
                    html_kleur_tegenstander = "#0072bc";
                    achtergrond_tegenstander = R.drawable.achtergrond_3_blauw;
                    tegenstander_aan = R.drawable.blauw;
                    tegenstander_uit = R.drawable.blauw_uit;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.cancel_blauw);
                    goed_Tegenstander = R.drawable.goed_2;
                    break;
                case "paars":
                    kleur_Tegenstander = R.drawable.kleur_3;
                    kleur_Tegenstander_uit = R.drawable.kleur_3_uit;
                    oog_tegenstander = R.drawable.oog_3;
                    oog_tegenstander_uit = R.drawable.oog_3_uit;
                    html_kleur_tegenstander = "#755295";
                    achtergrond_tegenstander = R.drawable.achtergrond_3_paars;
                    tegenstander_aan = R.drawable.paars;
                    tegenstander_uit = R.drawable.paars_uit;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.cancel_paars);
                    goed_Tegenstander = R.drawable.goed_3;
                    break;
                case "oranje":
                    kleur_Tegenstander = R.drawable.kleur_4;
                    kleur_Tegenstander_uit = R.drawable.kleur_4_uit;
                    oog_tegenstander = R.drawable.oog_4;
                    oog_tegenstander_uit = R.drawable.oog_4_uit;
                    html_kleur_tegenstander = "#f58040";
                    achtergrond_tegenstander = R.drawable.achtergrond_3_oranje;
                    tegenstander_aan = R.drawable.oranje;
                    tegenstander_uit = R.drawable.oranje_uit;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.cancel_oranje);
                    goed_Tegenstander = R.drawable.goed_4;
                    break;
                case "rood":
                    kleur_Tegenstander = R.drawable.kleur_5;
                    kleur_Tegenstander_uit = R.drawable.kleur_5_uit;
                    oog_tegenstander = R.drawable.oog_5;
                    oog_tegenstander_uit = R.drawable.oog_5_uit;
                    html_kleur_tegenstander = "#970a0e";
                    achtergrond_tegenstander = R.drawable.achtergrond_3_rood;
                    tegenstander_aan = R.drawable.rood;
                    tegenstander_uit = R.drawable.rood_uit;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.cancel_rood);
                    goed_Tegenstander = R.drawable.goed_5;
                    break;
            }

            rondje_aan_speler = context.getResources().getDrawable(context.getResources().getIdentifier("rondje_aan_"+kleur_speler, "drawable", context.getPackageName()));
            //rondje_uit_speler = context.getResources().getDrawable(context.getResources().getIdentifier("rondje_uit_"+kleur_speler, "drawable", context.getPackageName()));
            rondje_aan_tegenstander = context.getResources().getDrawable(context.getResources().getIdentifier("rondje_aan_"+kleur_tegenstander, "drawable", context.getPackageName()));
            //rondje_uit_tegenstander = context.getResources().getDrawable(context.getResources().getIdentifier("rondje_uit_"+kleur_tegenstander, "drawable", context.getPackageName()));

            imageViewPuntenSpeler = new ImageView[3];
            imageViewPuntenSpeler[0] = imageview_punten_speler_1;
            imageViewPuntenSpeler[1] = imageview_punten_speler_2;
            imageViewPuntenSpeler[2] = imageview_punten_speler_3;

            imageViewPuntenTegenstander = new ImageView[3];
            imageViewPuntenTegenstander[0] = imageview_punten_tegenstander_1;
            imageViewPuntenTegenstander[1] = imageview_punten_tegenstander_2;
            imageViewPuntenTegenstander[2] = imageview_punten_tegenstander_3;

            int tellen = 0;
            int score_Speler = Integer.parseInt(score_speler);
            int score_Tegenstander = Integer.parseInt(score_tegenstander);
            int score = Integer.parseInt(punten);

            while (tellen < score){
                imageViewPuntenSpeler[tellen].setImageResource(oog_speler_uit);
                tellen++;
            }
            tellen = 0;
            while (tellen < score_Speler){
                imageViewPuntenSpeler[tellen].setImageResource(oog_speler);
                tellen++;
            }

            tellen = 0;
            while (tellen < score){
                imageViewPuntenTegenstander[tellen].setImageResource(oog_tegenstander_uit);
                tellen++;
            }
            tellen = 0;
            while (tellen < score_Tegenstander){
                imageViewPuntenTegenstander[tellen].setImageResource(oog_tegenstander);
                tellen++;
            }

            Animation myAnimation = AnimationUtils.loadAnimation(context, R.anim.animatie_oneindig);

            if (Integer.parseInt(beoordelen_speler) > 0){
                imageview_beoordelen_speler.setImageResource(R.drawable.beoordelen);
                imageview_beoordelen_speler.startAnimation(myAnimation);

                /*SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String tip5 = sharedPreferences.getString("tip_5", "");

                if (!tip5.equals("JA")){
                    editor.putString("tip_5", "JA");
                    editor.apply();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View view = getLayoutInflater().inflate(R.layout.dialog_tips, null);
                    builder.setView(view);
                    TextView textView = (TextView) view.findViewById(R.id.textView);
                    textView.setText(getString(R.string.tip_5));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    builder.setCancelable(false);
                    builder.show();
                }*/

            }else{
                imageview_beoordelen_speler.setImageResource(0);
            }

            if (Integer.parseInt(beoordelen_tegenstander) > 0){
                imageview_beoordelen_tegenstander.setImageResource(R.drawable.beoordelen);
                //imageview_beoordelen_tegenstander.startAnimation(myAnimation);
            }else{
                imageview_beoordelen_tegenstander.setImageResource(0);
            }

            textview_naam_speler.setText(naam_speler);
            textview_naam_tegenstander.setText(naam_tegenstander);
            textview_chat.setText(chat);

            textview_spelvak_1.setRotationY(0f);
            textview_spelvak_2.setRotationY(0f);
            textview_spelvak_3.setRotationY(0f);
            textview_spelvak_4.setRotationY(0f);
            textview_spelvak_5.setRotationY(0f);
            textview_spelvak_6.setRotationY(0f);
            textview_spelvak_7.setRotationY(0f);
            textview_spelvak_8.setRotationY(0f);
            textview_spelvak_9.setRotationY(0f);

            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.achtergrond_123);
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.achtergrond_456);
            LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.achtergrond_789);
            LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.achtergrond_147);
            LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.achtergrond_258);
            LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.achtergrond_369);
            linearLayout1.setBackgroundColor(0);
            linearLayout2.setBackgroundColor(0);
            linearLayout3.setBackgroundColor(0);
            linearLayout4.setBackgroundColor(0);
            linearLayout5.setBackgroundColor(0);
            linearLayout6.setBackgroundColor(0);

            textview_spelvak_1.setText(onderwerp1);
            textview_spelvak_2.setText(onderwerp2);
            textview_spelvak_3.setText(onderwerp3);
            textview_spelvak_4.setText(onderwerp4);
            textview_spelvak_5.setText(onderwerp5);
            textview_spelvak_6.setText(onderwerp6);
            textview_spelvak_7.setText(onderwerp7);
            textview_spelvak_8.setText(onderwerp8);
            textview_spelvak_9.setText(onderwerp9);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int breedte = metrics.widthPixels;
            float dpi  = getResources().getDisplayMetrics().density;
            float breedte_totaal = breedte - (90 * dpi);
            float breedte_vakje = breedte_totaal / 3;

            //Log.d("SAS", "Breedte: "+breedte+"\ndpi: "+dpi+"\nbreedte_totaal: "+breedte_totaal+"\nbreedte_vakje: "+breedte_vakje);

            ImageView beoordelen_1 = (ImageView) findViewById(R.id.beoordelen_1);
            ImageView beoordelen_2 = (ImageView) findViewById(R.id.beoordelen_2);
            ImageView beoordelen_3 = (ImageView) findViewById(R.id.beoordelen_3);
            ImageView beoordelen_4 = (ImageView) findViewById(R.id.beoordelen_4);
            ImageView beoordelen_5 = (ImageView) findViewById(R.id.beoordelen_5);
            ImageView beoordelen_6 = (ImageView) findViewById(R.id.beoordelen_6);
            ImageView beoordelen_7 = (ImageView) findViewById(R.id.beoordelen_7);
            ImageView beoordelen_8 = (ImageView) findViewById(R.id.beoordelen_8);
            ImageView beoordelen_9 = (ImageView) findViewById(R.id.beoordelen_9);

            vakje1_aan = breedte_vakje / 24 * tijd1_uren;
            vakje2_aan = breedte_vakje / 24 * tijd2_uren;
            vakje3_aan = breedte_vakje / 24 * tijd3_uren;
            vakje4_aan = breedte_vakje / 24 * tijd4_uren;
            vakje5_aan = breedte_vakje / 24 * tijd5_uren;
            vakje6_aan = breedte_vakje / 24 * tijd6_uren;
            vakje7_aan = breedte_vakje / 24 * tijd7_uren;
            vakje8_aan = breedte_vakje / 24 * tijd8_uren;
            vakje9_aan = breedte_vakje / 24 * tijd9_uren;

            if (tijd1_uren == 0.0 && tijd1_minuten == 0.0 && tijd1_seconden == 0.0){
                imageview_tijd_1_uit.setVisibility(View.INVISIBLE);
                vakje1_aan = 0;
                beoordelen_1.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_1_uit.setVisibility(View.VISIBLE);
                beoordelen_1.setVisibility(View.VISIBLE);
            }
            if (tijd2_uren == 0.0 && tijd2_minuten == 0.0 && tijd2_seconden == 0.0){
                imageview_tijd_2_uit.setVisibility(View.INVISIBLE);
                vakje2_aan = 0;
                beoordelen_2.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_2_uit.setVisibility(View.VISIBLE);
                beoordelen_2.setVisibility(View.VISIBLE);
            }
            if (tijd3_uren == 0.0 && tijd3_minuten == 0.0 && tijd3_seconden == 0.0){
                imageview_tijd_3_uit.setVisibility(View.INVISIBLE);
                vakje3_aan = 0;
                beoordelen_3.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_3_uit.setVisibility(View.VISIBLE);
                beoordelen_3.setVisibility(View.VISIBLE);
            }
            if (tijd4_uren == 0.0 && tijd4_minuten == 0.0 && tijd4_seconden == 0.0){
                imageview_tijd_4_uit.setVisibility(View.INVISIBLE);
                vakje4_aan = 0;
                beoordelen_4.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_4_uit.setVisibility(View.VISIBLE);
                beoordelen_4.setVisibility(View.VISIBLE);
            }
            if (tijd5_uren == 0.0 && tijd5_minuten == 0.0 && tijd5_seconden == 0.0){
                imageview_tijd_5_uit.setVisibility(View.INVISIBLE);
                vakje5_aan = 0;
                beoordelen_5.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_5_uit.setVisibility(View.VISIBLE);
                beoordelen_5.setVisibility(View.VISIBLE);
            }
            if (tijd6_uren == 0.0 && tijd6_minuten == 0.0 && tijd6_seconden == 0.0){
                imageview_tijd_6_uit.setVisibility(View.INVISIBLE);
                vakje6_aan = 0;
                beoordelen_6.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_6_uit.setVisibility(View.VISIBLE);
                beoordelen_6.setVisibility(View.VISIBLE);
            }
            if (tijd7_uren == 0.0 && tijd7_minuten == 0.0 && tijd7_seconden == 0.0){
                imageview_tijd_7_uit.setVisibility(View.INVISIBLE);
                vakje7_aan = 0;
                beoordelen_7.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_7_uit.setVisibility(View.VISIBLE);
                beoordelen_7.setVisibility(View.VISIBLE);
            }
            if (tijd8_uren == 0.0 && tijd8_minuten == 0.0 && tijd8_seconden == 0.0){
                imageview_tijd_8_uit.setVisibility(View.INVISIBLE);
                vakje8_aan = 0;
                beoordelen_8.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_8_uit.setVisibility(View.VISIBLE);
                beoordelen_8.setVisibility(View.VISIBLE);
            }
            if (tijd9_uren == 0.0 && tijd9_minuten == 0.0 && tijd9_seconden == 0.0){
                imageview_tijd_9_uit.setVisibility(View.INVISIBLE);
                vakje9_aan = 0;
                beoordelen_9.setVisibility(View.INVISIBLE);
            }else{
                imageview_tijd_9_uit.setVisibility(View.VISIBLE);
                beoordelen_9.setVisibility(View.VISIBLE);
            }

            imageview_tijd_1_aan.getLayoutParams().width = (int)vakje1_aan;
            imageview_tijd_2_aan.getLayoutParams().width = (int)vakje2_aan;
            imageview_tijd_3_aan.getLayoutParams().width = (int)vakje3_aan;
            imageview_tijd_4_aan.getLayoutParams().width = (int)vakje4_aan;
            imageview_tijd_5_aan.getLayoutParams().width = (int)vakje5_aan;
            imageview_tijd_6_aan.getLayoutParams().width = (int)vakje6_aan;
            imageview_tijd_7_aan.getLayoutParams().width = (int)vakje7_aan;
            imageview_tijd_8_aan.getLayoutParams().width = (int)vakje8_aan;
            imageview_tijd_9_aan.getLayoutParams().width = (int)vakje9_aan;

            imageview_tijd_1_aan.requestLayout();
            imageview_tijd_2_aan.requestLayout();
            imageview_tijd_3_aan.requestLayout();
            imageview_tijd_4_aan.requestLayout();
            imageview_tijd_5_aan.requestLayout();
            imageview_tijd_6_aan.requestLayout();
            imageview_tijd_7_aan.requestLayout();
            imageview_tijd_8_aan.requestLayout();
            imageview_tijd_9_aan.requestLayout();

            if (gewonnen.equals(naam_speler)){
                textview_gewonnen.setBackgroundColor(Color.parseColor("#80197b30"));
                textview_gewonnen.setVisibility(View.VISIBLE);
                textview_gewonnen.setText(getString(R.string.spel_1));
            }
            if (gewonnen.equals(naam_tegenstander)){
                textview_gewonnen.setBackgroundColor(Color.parseColor("#80ed1c24"));
                textview_gewonnen.setVisibility(View.VISIBLE);
                textview_gewonnen.setText(getString(R.string.spel_2));
            }

            //tijd voor beurt
            float vakje1_aan = breedte_vakje / 24 * tijd_beurt_uren;
            float vakje1_uit = breedte_vakje / 24 * (24-tijd_beurt_uren);

            ImageView imageview_tijd_aan = (ImageView) findViewById(R.id.tijd_aan);
            ImageView imageview_tijd_uit = (ImageView) findViewById(R.id.tijd_uit);
            imageview_tijd_aan.getLayoutParams().width = (int)vakje1_aan;
            imageview_tijd_uit.getLayoutParams().width = (int)vakje1_uit;
            imageview_tijd_aan.requestLayout();
            imageview_tijd_uit.requestLayout();

            if (beurt.equals("1") || beurt.equals("2")) {

                switch (kleur_speler) {
                    case "groen":
                        imageview_tijd_aan.setImageResource(R.drawable.groen);
                        imageview_tijd_uit.setImageResource(R.drawable.groen_uit);
                        break;
                    case "blauw":
                        imageview_tijd_aan.setImageResource(R.drawable.blauw);
                        imageview_tijd_uit.setImageResource(R.drawable.blauw_uit);
                        break;
                    case "paars":
                        imageview_tijd_aan.setImageResource(R.drawable.paars);
                        imageview_tijd_uit.setImageResource(R.drawable.paars_uit);
                        break;
                    case "oranje":
                        imageview_tijd_aan.setImageResource(R.drawable.oranje);
                        imageview_tijd_uit.setImageResource(R.drawable.oranje_uit);
                        break;
                    case "rood":
                        imageview_tijd_aan.setImageResource(R.drawable.rood);
                        imageview_tijd_uit.setImageResource(R.drawable.rood_uit);
                        break;
                }

            }else{

                switch (kleur_tegenstander) {
                    case "groen":
                        imageview_tijd_aan.setImageResource(R.drawable.groen);
                        imageview_tijd_uit.setImageResource(R.drawable.groen_uit);
                        break;
                    case "blauw":
                        imageview_tijd_aan.setImageResource(R.drawable.blauw);
                        imageview_tijd_uit.setImageResource(R.drawable.blauw_uit);
                        break;
                    case "paars":
                        imageview_tijd_aan.setImageResource(R.drawable.paars);
                        imageview_tijd_uit.setImageResource(R.drawable.paars_uit);
                        break;
                    case "oranje":
                        imageview_tijd_aan.setImageResource(R.drawable.oranje);
                        imageview_tijd_uit.setImageResource(R.drawable.oranje_uit);
                        break;
                    case "rood":
                        imageview_tijd_aan.setImageResource(R.drawable.rood);
                        imageview_tijd_uit.setImageResource(R.drawable.rood_uit);
                        break;
                }

            }

            TextView textView_tijd = (TextView) findViewById(R.id.tijd);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
            Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
            textView_tijd.setTypeface(typeface);
            textview_chat.setTypeface(typeface_bold);

            TextView textViewMuntjes = (TextView) findViewById(R.id.textViewMuntjes);
            ImageView imageViewBeurtSkippen = (ImageView) findViewById(R.id.skip_beurt);
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.munt);

            textViewMuntjes.setText(muntjes);

            if (beurt.equals("1") || beurt.equals("2")) {
                imageview_beurt_speler.setImageResource(R.drawable.beurt_aan);
                imageview_beurt_tegenstander.setImageResource(R.drawable.beurt_uit);
                textview_naam_speler.setTextColor(Color.parseColor("#ffffff"));
                textview_naam_tegenstander.setTextColor(Color.parseColor("#555555"));
                textViewMuntjes.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                //textViewMuntjes.setTextSize(20);
                //textViewMuntjes.setTextColor(Color.parseColor("#ffffff"));
                imageViewBeurtSkippen.setImageResource(R.drawable.beurt_skippen_aan);
                if (beurt.equals("1")){
                    rondje_1.setBackgroundResource(R.drawable.vierkant_grijs);
                    rondje_2.setBackgroundResource(R.drawable.vierkant_grijs);
                }else{
                    rondje_1.setBackground(rondje_aan_speler);
                    rondje_2.setBackgroundResource(R.drawable.vierkant_grijs);
                }

            }else{
                imageview_beurt_speler.setImageResource(R.drawable.beurt_uit);
                imageview_beurt_tegenstander.setImageResource(R.drawable.beurt_aan);
                textview_naam_speler.setTextColor(Color.parseColor("#555555"));
                textview_naam_tegenstander.setTextColor(Color.parseColor("#ffffff"));
                //textViewMuntjes.setCompoundDrawables(null, null, null, null);
                //textViewMuntjes.setText("beurt tegenstander");
                //textViewMuntjes.setTextSize(12);
                //textViewMuntjes.setTextColor(Color.parseColor("#555555"));
                imageViewBeurtSkippen.setImageResource(R.drawable.beurt_skippen_uit);

                if (beurt.equals("3")){
                    rondje_1.setBackgroundResource(R.drawable.vierkant_grijs);
                    rondje_2.setBackgroundResource(R.drawable.vierkant_grijs);
                }else{
                    rondje_1.setBackground(rondje_aan_tegenstander);
                    rondje_2.setBackgroundResource(R.drawable.vierkant_grijs);
                }
            }

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("laatste_spel", id);
            editor.apply();

            kleuren_kruizen();
        }

    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            tijd_beurt_seconden = tijd_beurt_seconden -1;

            if (tijd_beurt_seconden < 0){
                tijd_beurt_minuten = tijd_beurt_minuten -1;
                tijd_beurt_seconden = 59;
            }
            if (tijd_beurt_minuten < 0){
                tijd_beurt_uren = tijd_beurt_uren -1;
                tijd_beurt_minuten = 59;
            }
            if (tijd_beurt_uren < 1){
                tijd_beurt_uren = 0;
            }

            String uren;
            String minuten;
            String seconden;

            if (tijd_beurt_uren < 10){
                uren = "0"+tijd_beurt_uren;
            }else{
                uren = ""+tijd_beurt_uren;
            }
            if (tijd_beurt_minuten < 10){
                minuten = "0"+tijd_beurt_minuten;
            }else{
                minuten = ""+tijd_beurt_minuten;
            }
            if (tijd_beurt_seconden < 10){
                seconden = "0"+tijd_beurt_seconden;
            }else{
                seconden = ""+tijd_beurt_seconden;
            }

            String tijd = uren+":"+minuten+":"+seconden;
            TextView textView_tijd = (TextView) findViewById(R.id.tijd);
            textView_tijd.setText(tijd);
            handler.postDelayed(timer, 1000);
        }
    };

    public void kleuren_kruizen(){

        String tijd1 = ""+tijd1_uren+tijd1_minuten+tijd1_seconden;
        String tijd2 = ""+tijd2_uren+tijd2_minuten+tijd2_seconden;
        String tijd3 = ""+tijd3_uren+tijd3_minuten+tijd3_seconden;
        String tijd4 = ""+tijd4_uren+tijd4_minuten+tijd4_seconden;
        String tijd5 = ""+tijd5_uren+tijd5_minuten+tijd5_seconden;
        String tijd6 = ""+tijd6_uren+tijd6_minuten+tijd6_seconden;
        String tijd7 = ""+tijd7_uren+tijd7_minuten+tijd7_seconden;
        String tijd8 = ""+tijd8_uren+tijd8_minuten+tijd8_seconden;
        String tijd9 = ""+tijd9_uren+tijd9_minuten+tijd9_seconden;

        String[] kleuren = {kleur1, kleur2, kleur3, kleur4, kleur5, kleur6, kleur7, kleur8, kleur9};
        String[] tijden = {tijd1, tijd2, tijd3, tijd4, tijd5, tijd6, tijd7, tijd8, tijd9};
        TextView[] spelvakken = {textview_spelvak_1, textview_spelvak_2, textview_spelvak_3, textview_spelvak_4, textview_spelvak_5, textview_spelvak_6, textview_spelvak_7, textview_spelvak_8, textview_spelvak_9};
        TextView[] achtergronden = {textview_achtergrond_1, textview_achtergrond_2, textview_achtergrond_3, textview_achtergrond_4, textview_achtergrond_5, textview_achtergrond_6, textview_achtergrond_7, textview_achtergrond_8, textview_achtergrond_9};
        TextView[] achtergronden_verticaal = {textview_achtergrond_1v, textview_achtergrond_2v, textview_achtergrond_3v, textview_achtergrond_4v, textview_achtergrond_5v, textview_achtergrond_6v, textview_achtergrond_7v, textview_achtergrond_8v, textview_achtergrond_9v};
        ImageView[] tijden_aan = {imageview_tijd_1_aan, imageview_tijd_2_aan, imageview_tijd_3_aan, imageview_tijd_4_aan, imageview_tijd_5_aan, imageview_tijd_6_aan, imageview_tijd_7_aan, imageview_tijd_8_aan, imageview_tijd_9_aan};
        ImageView[] tijden_uit = {imageview_tijd_1_uit, imageview_tijd_2_uit, imageview_tijd_3_uit, imageview_tijd_4_uit, imageview_tijd_5_uit, imageview_tijd_6_uit, imageview_tijd_7_uit, imageview_tijd_8_uit, imageview_tijd_9_uit};
        TextView[] kruizen = {textview_kruis_1, textview_kruis_2, textview_kruis_3, textview_kruis_4, textview_kruis_5, textview_kruis_6, textview_kruis_7, textview_kruis_8, textview_kruis_9};
        String[] onderwerpen = {onderwerp1_nummer, onderwerp2_nummer, onderwerp3_nummer, onderwerp4_nummer, onderwerp5_nummer, onderwerp6_nummer, onderwerp7_nummer, onderwerp8_nummer, onderwerp9_nummer};

        int aantal_fotos = 0;
        int aantal_vakken_over = 0;
        int tellen = 0;
        while (tellen < 9){

            String kleur = kleuren[tellen];
            final TextView spelvak = spelvakken[tellen];
            TextView achtergrond = achtergronden[tellen];
            TextView achtergrond_verticaal = achtergronden_verticaal[tellen];
            ImageView tijd_aan = tijden_aan[tellen];
            ImageView tijd_uit = tijden_uit[tellen];
            TextView kruis = kruizen[tellen];
            String onderwerp = onderwerpen[tellen];
            String tijd = tijden[tellen];

            switch (kleur){

                case "0":
                    spelvak.setBackgroundResource(R.drawable.kleur_grijs);
                    achtergrond.setBackgroundColor(0);
                    achtergrond_verticaal.setBackgroundResource(0);
                    tijd_aan.setImageResource(R.drawable.grijs);
                    tijd_uit.setImageResource(R.drawable.grijs_uit);
                    kruis.setBackground(null);
                    aantal_vakken_over++;
                    break;
                case "1":
                    aantal_fotos++;
                    if (tijd.equals("000")){
                        spelvak.setBackgroundResource(goed_Speler);
                    }else{
                        spelvak.setBackgroundResource(kleur_Speler);
                    }
                    achtergrond.setBackgroundColor(0);
                    achtergrond_verticaal.setBackgroundResource(0);
                    tijd_aan.setImageResource(speler_aan);
                    tijd_uit.setImageResource(speler_uit);
                    kruis.setBackground(null);
                    break;
                case "2":
                    aantal_fotos++;
                    if (tijd.equals("000")){
                        spelvak.setBackgroundResource(goed_Tegenstander);
                    }else{
                        spelvak.setBackgroundResource(kleur_Tegenstander);
                    }
                    achtergrond.setBackgroundColor(0);
                    achtergrond_verticaal.setBackgroundResource(0);
                    tijd_aan.setImageResource(tegenstander_aan);
                    tijd_uit.setImageResource(tegenstander_uit);
                    kruis.setBackground(null);
                    break;
                case "3":
                    aantal_fotos++;
                    new laad_plaatje_textview(spelvak).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp + ".jpg");
                    spelvak.setText("");
                    spelvak.setRotationY(180f);
                    achtergrond.setBackgroundResource(achtergrond_speler);
                    kruis.setBackground(null);
                    break;
                case "4":
                    aantal_fotos++;
                    new laad_plaatje_textview(spelvak).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp + ".jpg");
                    spelvak.setText("");
                    spelvak.setRotationY(180f);
                    achtergrond.setBackgroundResource(achtergrond_tegenstander);
                    kruis.setBackground(null);
                    break;
                case "5":
                    spelvak.setBackgroundResource(R.drawable.kleur_grijs);
                    achtergrond.setBackgroundColor(0);
                    achtergrond_verticaal.setBackgroundResource(0);
                    tijd_aan.setImageResource(R.drawable.grijs);
                    tijd_uit.setImageResource(R.drawable.grijs_uit);
                    kruis.setBackground(kruis_speler);
                    aantal_vakken_over++;
                    break;
                case "6":
                    spelvak.setBackgroundResource(R.drawable.kleur_grijs);
                    achtergrond.setBackgroundColor(0);
                    achtergrond_verticaal.setBackgroundResource(0);
                    tijd_aan.setImageResource(R.drawable.grijs);
                    tijd_uit.setImageResource(R.drawable.grijs_uit);
                    kruis.setBackground(kruis_tegenstander);
                    aantal_vakken_over++;
                    break;
            }

            tellen++;
        }

        ImageView imageView = (ImageView) findViewById(R.id.laden);
        imageView.clearAnimation();
        imageView.setVisibility(View.INVISIBLE);

        if (animatie.equals("NS")){
            animatie_bezig = "ja";
            handler1 = new Handler();
            handler1.post(animatie_fadein);
        }else{
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.VISIBLE);
            imageview_tijd_1_aan.getLayoutParams().width = (int)vakje1_aan;
            imageview_tijd_2_aan.getLayoutParams().width = (int)vakje2_aan;
            imageview_tijd_3_aan.getLayoutParams().width = (int)vakje3_aan;
            imageview_tijd_4_aan.getLayoutParams().width = (int)vakje4_aan;
            imageview_tijd_5_aan.getLayoutParams().width = (int)vakje5_aan;
            imageview_tijd_6_aan.getLayoutParams().width = (int)vakje6_aan;
            imageview_tijd_7_aan.getLayoutParams().width = (int)vakje7_aan;
            imageview_tijd_8_aan.getLayoutParams().width = (int)vakje8_aan;
            imageview_tijd_9_aan.getLayoutParams().width = (int)vakje9_aan;
            imageview_tijd_1_aan.requestLayout();
            imageview_tijd_2_aan.requestLayout();
            imageview_tijd_3_aan.requestLayout();
            imageview_tijd_4_aan.requestLayout();
            imageview_tijd_5_aan.requestLayout();
            imageview_tijd_6_aan.requestLayout();
            imageview_tijd_7_aan.requestLayout();
            imageview_tijd_8_aan.requestLayout();
            imageview_tijd_9_aan.requestLayout();

            if (animatie.equals("NO1")) {
                animatie_bezig = "ja";
                handler1 = new Handler();
                handler1.post(animatie_geendrieopeenrij);
            }else if (animatie.equals("NO2")){
                animatie_bezig = "ja";
                handler1 = new Handler();
                handler1.post(animatie_allevakkengespeeld);
            }else if (animatie.equals("FI")){
                animatie_bezig = "ja";
                handler1 = new Handler();
                handler1.post(animatie_fadein);
            }else if (animatie.contains("G") || animatie.contains("V")){
                animatie_bezig = "ja";
                handler1 = new Handler();
                handler1.post(animatie_gewonnen_verloren);
            }else{
                //animatie_bezig = "ja";
                handler1 = new Handler();
                handler1.post(start_animaties);

                SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String popup = sharedPreferences.getString("popup_2", "");
                if ((!popup.equals("JA")) && (beurt.equals("1") || beurt.equals("2"))){
                    editor.putString("popup_2", "JA");
                    editor.apply();

                    Home.popup_test(activity, "popup_3", "popup_3");
                    Home.popup_test(activity, "popup_2", "popup_2");
                }
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String popup1 = sharedPreferences.getString("popup_1"+id, "");
        if ((!popup1.equals("JA")) && (beurt.equals("3") || beurt.equals("4"))){
            editor.putString("popup_1"+id, "JA");
            editor.apply();

            Home.popup_test(this, "popup_1", "leeg");
        }

        String popup2 = sharedPreferences.getString("popup_4", "");
        if ((!popup2.equals("JA")) && (beurt.equals("2"))){
            editor.putString("popup_4", "JA");
            editor.apply();

            Home.popup_test(activity, "popup_5", "popup_5");
            Home.popup_test(activity, "popup_4", "popup_4");
        }else if (beurt.equals("2")){
            String popup10 = sharedPreferences.getString("popup_10", "0");
            if (!popup10.equals("JA")) {
                Home.popup_test(activity, "popup_10", "popup_4");
            }
        }

        if ((beurt_tellen.equals("5") || beurt_tellen.equals("6")) && (beurt.equals("1") || beurt.equals("2"))) {
            String popup3 = sharedPreferences.getString("popup_8", "0");
            if (!popup3.equals("JA")) {
                editor.putString("popup_8", "JA");
                editor.apply();
                Home.popup_test(this, "popup_8", "popup_8");
            }
        }

        if (aantal_vakken_over == 0 && beurt.equals("2")){
            Home.popup_test(activity, "popup_12", "leeg");
        }

    }

    private Runnable spel_herladen = new Runnable() {
        @Override
        public void run() {
            spel_herladen();
        }
    };

    private Runnable animatie_fadein = new Runnable() {
        @Override
        public void run() {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.INVISIBLE);

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_fadein);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    handler1.postDelayed(animatie_fadeout, 1000);
                }
            });

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.VISIBLE);
            //textView.setBackgroundColor(Color.parseColor("#000000"));
            textView.setBackgroundResource(0);

            if (animatie.equals("NS")){
                textView.setText(getString(R.string.spel_3));
            }else{
                textView.setText(getString(R.string.spel_4));
            }
            textView.startAnimation(animation);
        }
    };

    private Runnable animatie_fadeout = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_fadeout);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    handler1.post(animatie_slideup);
                }
            });

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.startAnimation(animation);
        }
    };

    private Runnable animatie_slideup = new Runnable() {
        @Override
        public void run() {

            Animation slide_up = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
            slide_up.setFillAfter(true);
            slide_up.setFillBefore(true);

            slide_up.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    animatie_bezig = "nee";

                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup = sharedPreferences.getString("popup_2", "");
                    if ((!popup.equals("JA")) && (beurt.equals("1") || beurt.equals("2"))){
                        editor.putString("popup_2", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_3", "popup_3");
                        Home.popup_test(activity, "popup_2", "popup_2");
                    }

                }
            });

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout.startAnimation(slide_up);
        }
    };


    private Runnable animatie_drieopeenrij = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
            animation.setFillAfter(true);

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.VISIBLE);

            int aantal_munten;
            if (thema.equals("binnen")){
                aantal_munten = 25;
            }else if(thema.equals("buiten")){
                aantal_munten = 50;
            }else if(thema.equals("natuur")){
                aantal_munten = 60;
            }else if(thema.equals("stedelijk")){
                aantal_munten = 60;
            }else{
                aantal_munten = 40;
            }

            if (animatie.contains("s")) {
                textView.setBackgroundResource(kleur_Speler);
                muntjes_nieuw = String.valueOf(Integer.parseInt(muntjes) +aantal_munten);

                Drawable drawable1 = getResources().getDrawable(R.drawable.oog_klein);
                drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
                ImageSpan imageSpan1 = new ImageSpan(drawable1, ImageSpan.ALIGN_BASELINE);

                Drawable drawable2 = getResources().getDrawable(R.drawable.munt_klein);
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                ImageSpan imageSpan2 = new ImageSpan(drawable2, ImageSpan.ALIGN_BASELINE);

                String string = getString(R.string.spel_5)+"\n\n[1]\n\n[2] +"+aantal_munten;
                SpannableString spannableString = new SpannableString(string);
                int plaats_1 = string.indexOf("[1]");
                int plaats_2 = string.indexOf("[2]");
                spannableString.setSpan(imageSpan1, plaats_1, plaats_1+3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(imageSpan2, plaats_2, plaats_2+3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                textView.setText(spannableString);
            }else{
                textView.setBackgroundResource(kleur_Tegenstander);
                textView.setText(getString(R.string.spel_5));
            }

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    handler1 = new Handler();
                    handler1.postDelayed(animatie_score, 1000);
                }
            });

            textView.startAnimation(animation);
        }
    };

    private Runnable animatie_score = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_score);

            final TextView textView = (TextView) findViewById(R.id.tekst);

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    textView.setVisibility(View.INVISIBLE);
                    handler1 = new Handler();
                    handler1.post(animatie_punt);
                }
            });

            if (animatie.contains("s")) {
                Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.animatie_oneindig);
                TextView textViewMuntjes = (TextView) findViewById(R.id.textViewMuntjes);
                textViewMuntjes.startAnimation(animation2);
                textViewMuntjes.setText(muntjes_nieuw);
            }

            textView.startAnimation(animation);
        }
    };

    private Runnable animatie_punt = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_punt);
            animation.setFillBefore(true);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    if (animatie.contains("s")) {
                        TextView textViewMuntjes = (TextView) findViewById(R.id.textViewMuntjes);
                        textViewMuntjes.clearAnimation();
                    }
                    animatie_bezig = "nee";
                    spel_herladen();

                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup = sharedPreferences.getString("popup_9", "");
                    if (!popup.equals("JA")){
                        editor.putString("popup_9", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_9", "leeg");
                    }
                }
            });

            if (animatie.contains("s")){
                int score_Speler = Integer.parseInt(score_speler);
                imageViewPuntenSpeler[score_Speler].setImageResource(kleur_Speler);
                imageViewPuntenSpeler[score_Speler].startAnimation(animation);
            }else{
                int score_Tegenstander = Integer.parseInt(score_tegenstander);
                imageViewPuntenTegenstander[score_Tegenstander].setImageResource(kleur_Tegenstander);
                imageViewPuntenTegenstander[score_Tegenstander].startAnimation(animation);
            }

        }
    };

    private Runnable animatie_geendrieopeenrij = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
            animation.setFillAfter(true);

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getString(R.string.spel_6));
            //textView.setBackgroundColor(Color.parseColor("#464646"));
            textView.setBackgroundResource(R.drawable.kleur_grijs);

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    animatie_bezig = "nee";
                    handler1 = new Handler();
                    handler1.postDelayed(spel_herladen, 1000);

                    String reclame = getString(R.string.reclame);

                    if (reclame.equals("ja")) {
                        if (mInterstitialAd.isLoaded()) {
                            Log.d("SAS", "ja");
                            mInterstitialAd.show();
                        }else{
                            Log.d("SAS", "nee");
                        }
                    }
                }
            });

            textView.startAnimation(animation);
        }
    };

    private Runnable animatie_allevakkengespeeld = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
            animation.setFillAfter(true);

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getString(R.string.spel_7));
            //textView.setBackgroundColor(Color.parseColor("#464646"));
            textView.setBackgroundResource(R.drawable.kleur_grijs);

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    animatie_bezig = "nee";
                    handler1 = new Handler();
                    handler1.postDelayed(spel_herladen, 1000);

                    String reclame = getString(R.string.reclame);

                    if (reclame.equals("ja")) {
                        if (mInterstitialAd.isLoaded()) {
                            Log.d("SAS", "ja");
                            mInterstitialAd.show();
                        }else{
                            Log.d("SAS", "nee");
                        }
                    }
                }
            });

            textView.startAnimation(animation);
        }
    };

    private Runnable animatie_gewonnen_verloren = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
            animation.setFillAfter(true);

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.VISIBLE);

            int aantal_munten;
            if (thema.equals("binnen")){
                aantal_munten = 50;
            }else if(thema.equals("buiten")){
                aantal_munten = 75;
            }else if(thema.equals("natuur")){
                aantal_munten = 80;
            }else if(thema.equals("stedelijk")){
                aantal_munten = 80;
            }else{
                aantal_munten = 65;
            }

            if (animatie.contains("G")) {

                textView.setBackgroundResource(kleur_Speler);
                muntjes_nieuw = String.valueOf(Integer.parseInt(muntjes) +aantal_munten);

                Drawable drawable1 = getResources().getDrawable(R.drawable.oog_klein);
                drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
                ImageSpan imageSpan1 = new ImageSpan(drawable1, ImageSpan.ALIGN_BASELINE);

                Drawable drawable2 = getResources().getDrawable(R.drawable.munt_klein);
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                ImageSpan imageSpan2 = new ImageSpan(drawable2, ImageSpan.ALIGN_BASELINE);

                String string = getString(R.string.spel_8)+"\n\n[1]\n\n[2] +"+aantal_munten;
                SpannableString spannableString = new SpannableString(string);
                int plaats_1 = string.indexOf("[1]");
                int plaats_2 = string.indexOf("[2]");
                spannableString.setSpan(imageSpan1, plaats_1, plaats_1+3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(imageSpan2, plaats_2, plaats_2+3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                textView.setText(spannableString);

            }else{
                textView.setBackgroundResource(kleur_Tegenstander);
                textView.setText(getString(R.string.spel_9));
            }

            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    animatie_bezig = "nee";

                    if (animatie.contains("G")) {
                        if (animatie.contains("B")) {
                            Home.popup_test(activity, "popup_13", "leeg");
                        }
                    }else{
                        if (animatie.contains("B")) {
                            Home.popup_test(activity, "popup_14", "leeg");
                        }
                    }
                    /*Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.animatie_kort);
                    TextView textViewMuntjes = (TextView) findViewById(R.id.textViewMuntjes);
                    textViewMuntjes.startAnimation(animation2);
                    textViewMuntjes.setText(muntjes_nieuw);*/
                }
            });

            textView.startAnimation(animation);
        }
    };

    public void spel_herladen() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animatie_rotatie);
        ImageView imageView = (ImageView) findViewById(R.id.laden);
        imageView.startAnimation(animation);
        new spel_laden().execute();
    }

    private Runnable start_animaties = new Runnable() {
        @Override
        public void run() {

            int aantal_munten;
            if (thema.equals("binnen")){
                aantal_munten = 10;
            }else if(thema.equals("buiten")){
                aantal_munten = 20;
            }else if(thema.equals("natuur")){
                aantal_munten = 25;
            }else if(thema.equals("stedelijk")){
                aantal_munten = 25;
            }else{
                aantal_munten = 15;
            }

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.INVISIBLE);

            // Muntjes animatie
            Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.animatie);
            final TextView textViewMuntjes = (TextView) findViewById(R.id.textViewMuntjes);

            TextView textView1 = (TextView)findViewById(R.id.munt_1);
            TextView textView2 = (TextView)findViewById(R.id.munt_2);
            TextView textView3 = (TextView)findViewById(R.id.munt_3);
            TextView textView4 = (TextView)findViewById(R.id.munt_4);
            TextView textView5 = (TextView)findViewById(R.id.munt_5);
            TextView textView6 = (TextView)findViewById(R.id.munt_6);
            TextView textView7 = (TextView)findViewById(R.id.munt_7);
            TextView textView8 = (TextView)findViewById(R.id.munt_8);
            TextView textView9 = (TextView)findViewById(R.id.munt_9);

            Animation animatie1 = AnimationUtils.loadAnimation(context, R.anim.animatie_munt);
            animatie1.setFillAfter(true);

            Animation myAnimation = AnimationUtils.loadAnimation(context, R.anim.animatie_kort);

            myAnimation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                    animatie_bezig = "ja";
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    animatie_bezig = "nee";
                    handler2 = new Handler();
                    handler2.post(herlaad_spel);
                }
            });

            if (animatie.contains("1")){
                textview_spelvak_1.startAnimation(myAnimation);
                if (kleur1.equals("1")) {
                    textView1.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView1.setText("+"+aantal_munten);
                    textView1.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur1.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("2")){
                textview_spelvak_2.startAnimation(myAnimation);
                if (kleur2.equals("1")) {
                    textView2.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView2.setText("+"+aantal_munten);
                    textView2.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur2.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("3")){
                textview_spelvak_3.startAnimation(myAnimation);
                if (kleur3.equals("1")) {
                    textView3.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView3.setText("+"+aantal_munten);
                    textView3.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur3.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("4")){
                textview_spelvak_4.startAnimation(myAnimation);
                if (kleur4.equals("1")) {
                    textView4.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView4.setText("+"+aantal_munten);
                    textView4.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur4.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("5")){
                textview_spelvak_5.startAnimation(myAnimation);
                if (kleur5.equals("1")) {
                    textView5.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView5.setText("+"+aantal_munten);
                    textView5.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur5.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("6")){
                textview_spelvak_6.startAnimation(myAnimation);
                if (kleur6.equals("1")) {
                    textView6.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView6.setText("+"+aantal_munten);
                    textView6.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur6.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("7")){
                textview_spelvak_7.startAnimation(myAnimation);
                if (kleur7.equals("1")) {
                    textView7.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView7.setText("+"+aantal_munten);
                    textView7.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur7.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("8")){
                textview_spelvak_8.startAnimation(myAnimation);
                if (kleur8.equals("1")) {
                    textView8.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView8.setText("+"+aantal_munten);
                    textView8.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur8.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }
            if (animatie.contains("9")){
                textview_spelvak_9.startAnimation(myAnimation);
                if (kleur9.equals("1")) {
                    textView9.setBackground(ContextCompat.getDrawable(context, R.drawable.munt_spel));
                    textView9.setText("+"+aantal_munten);
                    textView9.startAnimation(animatie1);
                    textViewMuntjes.startAnimation(animation2);
                }else if (kleur9.equals("0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String popup11 = sharedPreferences.getString("popup_11", "");
                    if (!popup11.equals("JA")) {
                        editor.putString("popup_11", "JA");
                        editor.apply();

                        Home.popup_test(activity, "popup_11", "leeg");
                    }
                }
            }

            if (animatie.contains("a")){
                // 3 op een rij 1
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.achtergrond_123);
                final Animation slide_left = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_left);
                slide_left.setFillAfter(true);
                slide_left.setFillBefore(true);
                slide_left.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                        animatie_bezig = "ja";
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        handler1 = new Handler();
                        handler1.postDelayed(animatie_drieopeenrij, 500);
                    }
                });

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        linearLayout.startAnimation(slide_left);
                        if (animatie.contains("s")) {
                            textview_achtergrond_1.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_2.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_3.setBackgroundResource(achtergrond_speler);
                        }else{
                            textview_achtergrond_1.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_2.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_3.setBackgroundResource(achtergrond_tegenstander);
                        }
                    }
                }, 1000);

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 0.8f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_2, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 0.8f);
                ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 0.8f);
                ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(textview_spelvak_3, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 0.8f);
                ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                objectAnimator4.setDuration(1000);
                objectAnimator5.setDuration(500);
                objectAnimator6.setDuration(500);
                objectAnimator7.setDuration(1000);
                objectAnimator8.setDuration(500);
                objectAnimator9.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6).with(objectAnimator7).with(objectAnimator8).with(objectAnimator9);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        objectAnimator3.setDuration(500);
                        objectAnimator4.setDuration(500);
                        objectAnimator5.setDuration(500);
                        objectAnimator6.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6);
                        animatorSet.start();
                        textview_spelvak_1.setText("");
                        textview_spelvak_1.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_2.setText("");
                        textview_spelvak_2.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_3.setText("");
                        textview_spelvak_3.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_3).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp3_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_2).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp2_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_1).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp1_nummer + ".jpg");
                    }
                }, 500);
            }
            else if (animatie.contains("b")){
                // 3 op een rij 2
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.achtergrond_456);
                final Animation slide_left = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_left);
                slide_left.setFillAfter(true);
                slide_left.setFillBefore(true);
                slide_left.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                        animatie_bezig = "ja";
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        handler1 = new Handler();
                        handler1.postDelayed(animatie_drieopeenrij, 500);
                    }
                });
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        linearLayout.startAnimation(slide_left);
                        if (animatie.contains("s")) {
                            textview_achtergrond_4.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_5.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_6.setBackgroundResource(achtergrond_speler);
                        }else{
                            textview_achtergrond_4.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_5.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_6.setBackgroundResource(achtergrond_tegenstander);
                        }
                    }
                }, 1000);

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_4, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 0.8f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_5, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 0.8f);
                ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 0.8f);
                ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(textview_spelvak_6, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 0.8f);
                ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                objectAnimator4.setDuration(1000);
                objectAnimator5.setDuration(500);
                objectAnimator6.setDuration(500);
                objectAnimator7.setDuration(1000);
                objectAnimator8.setDuration(500);
                objectAnimator9.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6).with(objectAnimator7).with(objectAnimator8).with(objectAnimator9);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        objectAnimator3.setDuration(500);
                        objectAnimator4.setDuration(500);
                        objectAnimator5.setDuration(500);
                        objectAnimator6.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6);
                        animatorSet.start();
                        textview_spelvak_4.setText("");
                        textview_spelvak_4.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_5.setText("");
                        textview_spelvak_5.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_6.setText("");
                        textview_spelvak_6.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_6).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp6_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_5).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp5_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_4).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp4_nummer + ".jpg");
                    }
                }, 500);
            }
            else if (animatie.contains("c")){
                // 3 op een rij 3
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.achtergrond_789);
                final Animation slide_left = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_left);
                slide_left.setFillAfter(true);
                slide_left.setFillBefore(true);
                slide_left.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                        animatie_bezig = "ja";
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        handler1 = new Handler();
                        handler1.postDelayed(animatie_drieopeenrij, 500);
                    }
                });
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        linearLayout.startAnimation(slide_left);
                        if (animatie.contains("s")) {
                            textview_achtergrond_7.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_8.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_9.setBackgroundResource(achtergrond_speler);
                        }else{
                            textview_achtergrond_7.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_8.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_9.setBackgroundResource(achtergrond_tegenstander);
                        }
                    }
                }, 1000);

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_7, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 0.8f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_8, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 0.8f);
                ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 0.8f);
                ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(textview_spelvak_9, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 0.8f);
                ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                objectAnimator4.setDuration(1000);
                objectAnimator5.setDuration(500);
                objectAnimator6.setDuration(500);
                objectAnimator7.setDuration(1000);
                objectAnimator8.setDuration(500);
                objectAnimator9.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6).with(objectAnimator7).with(objectAnimator8).with(objectAnimator9);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        objectAnimator3.setDuration(500);
                        objectAnimator4.setDuration(500);
                        objectAnimator5.setDuration(500);
                        objectAnimator6.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6);
                        animatorSet.start();
                        textview_spelvak_7.setText("");
                        textview_spelvak_7.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_8.setText("");
                        textview_spelvak_8.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_9.setText("");
                        textview_spelvak_9.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_9).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp9_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_8).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp8_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_7).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp7_nummer + ".jpg");
                    }
                }, 500);
            }
            else if (animatie.contains("d")){
                // 3 op een rij 4
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.achtergrond_147);
                final Animation slide_left = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
                slide_left.setFillAfter(true);
                slide_left.setFillBefore(true);
                slide_left.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                        animatie_bezig = "ja";
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        handler1 = new Handler();
                        handler1.postDelayed(animatie_drieopeenrij, 500);
                    }
                });
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        linearLayout.startAnimation(slide_left);
                        if (animatie.contains("s")) {
                            textview_achtergrond_1v.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_4v.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_7v.setBackgroundResource(achtergrond_speler);
                        }else{
                            textview_achtergrond_1v.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_4v.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_7v.setBackgroundResource(achtergrond_tegenstander);
                        }
                    }
                }, 1000);

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 0.8f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_4, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 0.8f);
                ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 0.8f);
                ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(textview_spelvak_7, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 0.8f);
                ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                objectAnimator4.setDuration(1000);
                objectAnimator5.setDuration(500);
                objectAnimator6.setDuration(500);
                objectAnimator7.setDuration(1000);
                objectAnimator8.setDuration(500);
                objectAnimator9.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6).with(objectAnimator7).with(objectAnimator8).with(objectAnimator9);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        objectAnimator3.setDuration(500);
                        objectAnimator4.setDuration(500);
                        objectAnimator5.setDuration(500);
                        objectAnimator6.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6);
                        animatorSet.start();
                        textview_spelvak_1.setText("");
                        textview_spelvak_1.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_4.setText("");
                        textview_spelvak_4.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_7.setText("");
                        textview_spelvak_7.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_7).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp7_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_4).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp4_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_1).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp1_nummer + ".jpg");
                    }
                }, 500);
            }
            else if (animatie.contains("e")){
                // 3 op een rij 5
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.achtergrond_258);
                final Animation slide_left = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
                slide_left.setFillAfter(true);
                slide_left.setFillBefore(true);
                slide_left.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                        animatie_bezig = "ja";
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        handler1 = new Handler();
                        handler1.postDelayed(animatie_drieopeenrij, 500);
                    }
                });
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        linearLayout.startAnimation(slide_left);
                        if (animatie.contains("s")) {
                            textview_achtergrond_2v.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_5v.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_8v.setBackgroundResource(achtergrond_speler);
                        }else{
                            textview_achtergrond_2v.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_5v.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_8v.setBackgroundResource(achtergrond_tegenstander);
                        }
                    }
                }, 1000);

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_2, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 0.8f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_5, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 0.8f);
                ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 0.8f);
                ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(textview_spelvak_8, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 0.8f);
                ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                objectAnimator4.setDuration(1000);
                objectAnimator5.setDuration(500);
                objectAnimator6.setDuration(500);
                objectAnimator7.setDuration(1000);
                objectAnimator8.setDuration(500);
                objectAnimator9.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6).with(objectAnimator7).with(objectAnimator8).with(objectAnimator9);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        objectAnimator3.setDuration(500);
                        objectAnimator4.setDuration(500);
                        objectAnimator5.setDuration(500);
                        objectAnimator6.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6);
                        animatorSet.start();
                        textview_spelvak_2.setText("");
                        textview_spelvak_2.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_5.setText("");
                        textview_spelvak_5.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_8.setText("");
                        textview_spelvak_8.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_8).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp8_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_5).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp5_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_2).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp2_nummer + ".jpg");
                    }
                }, 500);
            }
            else if (animatie.contains("f")){
                // 3 op een rij 6
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.achtergrond_369);
                final Animation slide_left = AnimationUtils.loadAnimation(context, R.anim.animatie_slide_up);
                slide_left.setFillAfter(true);
                slide_left.setFillBefore(true);
                slide_left.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                        animatie_bezig = "ja";
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        handler1 = new Handler();
                        handler1.postDelayed(animatie_drieopeenrij, 500);
                    }
                });
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        linearLayout.startAnimation(slide_left);
                        if (animatie.contains("s")) {
                            textview_achtergrond_3v.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_6v.setBackgroundResource(achtergrond_speler);
                            textview_achtergrond_9v.setBackgroundResource(achtergrond_speler);
                        }else{
                            textview_achtergrond_3v.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_6v.setBackgroundResource(achtergrond_tegenstander);
                            textview_achtergrond_9v.setBackgroundResource(achtergrond_tegenstander);
                        }
                    }
                }, 1000);

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_3, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 0.8f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_6, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 0.8f);
                ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 0.8f);
                ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(textview_spelvak_9, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 0.8f);
                ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                objectAnimator4.setDuration(1000);
                objectAnimator5.setDuration(500);
                objectAnimator6.setDuration(500);
                objectAnimator7.setDuration(1000);
                objectAnimator8.setDuration(500);
                objectAnimator9.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6).with(objectAnimator7).with(objectAnimator8).with(objectAnimator9);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 1.0f);
                        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        objectAnimator3.setDuration(500);
                        objectAnimator4.setDuration(500);
                        objectAnimator5.setDuration(500);
                        objectAnimator6.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5).with(objectAnimator6);
                        animatorSet.start();
                        textview_spelvak_3.setText("");
                        textview_spelvak_3.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_6.setText("");
                        textview_spelvak_6.setBackgroundColor(Color.parseColor("#464646"));
                        textview_spelvak_9.setText("");
                        textview_spelvak_9.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_9).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp9_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_6).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp6_nummer + ".jpg");
                        new laad_plaatje_textview(textview_spelvak_3).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp3_nummer + ".jpg");
                    }
                }, 500);
            }else{
                animatie_bezig = "nee";
                handler2 = new Handler();
                handler2.post(herlaad_spel);
            }

        }
    };

    private Runnable herlaad_spel = new Runnable() {
        @Override
        public void run() {
            if (!aantal.equals("1")){
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_rotatie);
                ImageView imageView = (ImageView) findViewById(R.id.laden);
                imageView.startAnimation(animation);
                new spel_laden().execute();
            }
        }

    };

    float x1;
    float x2;
    float y1;
    float y2;

    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){

            case MotionEvent.ACTION_DOWN:{
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:{
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                if (x2 > x1){
                    if (x2 - x1 > 300 && animatie_bezig.equals("nee")){
                        kleuren_of_plaatjes();
                    }
                }
                if (x2 < x1){
                    if (x1 - x2 > 300 && animatie_bezig.equals("nee")){
                        kleuren_of_plaatjes();
                    }
                }
                break;
            }
        }
        return true;
    }

    boolean plaatjes = false;

    public void kleuren_of_plaatjes(){

        if (!plaatjes){

            if (kleur1.equals("1") || kleur1.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_1.setText("");
                        textview_spelvak_1.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_1).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp1_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur2.equals("1") || kleur2.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_2, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_2.setText("");
                        textview_spelvak_2.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_2).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp2_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur3.equals("1") || kleur3.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_3, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_3.setText("");
                        textview_spelvak_3.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_3).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp3_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur4.equals("1") || kleur4.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_4, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_4.setText("");
                        textview_spelvak_4.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_4).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp4_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur5.equals("1") || kleur5.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_5, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_5.setText("");
                        textview_spelvak_5.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_5).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp5_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur6.equals("1") || kleur6.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_6, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_6.setText("");
                        textview_spelvak_6.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_6).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp6_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur7.equals("1") || kleur7.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_7, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_7.setText("");
                        textview_spelvak_7.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_7).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp7_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur8.equals("1") || kleur8.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_8, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_8.setText("");
                        textview_spelvak_8.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_8).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp8_nummer + ".jpg");
                    }
                }, 500);
            }
            if (kleur9.equals("1") || kleur9.equals("2")){
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_9, "rotationY", 0.0f, 180f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_9.setText("");
                        textview_spelvak_9.setBackgroundColor(Color.parseColor("#464646"));
                        new laad_plaatje_textview(textview_spelvak_9).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp9_nummer + ".jpg");
                    }
                }, 500);
            }

            plaatjes = true;
        }else{

            if (kleur1.equals("1") || kleur1.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_1, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_1.setText(onderwerp1);
                    }
                }, 500);
            }
            if (kleur2.equals("1") || kleur2.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_2, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_2, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_2.setText(onderwerp2);
                    }
                }, 500);
            }
            if (kleur3.equals("1") || kleur3.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_3, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_3, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_3.setText(onderwerp3);
                    }
                }, 500);
            }
            if (kleur4.equals("1") || kleur4.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_4, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_4, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_4.setText(onderwerp4);
                    }
                }, 500);
            }
            if (kleur5.equals("1") || kleur5.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_5, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_5, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_5.setText(onderwerp5);
                    }
                }, 500);
            }
            if (kleur6.equals("1") || kleur6.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_6, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_6, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_6.setText(onderwerp6);
                    }
                }, 500);
            }
            if (kleur7.equals("1") || kleur7.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_7, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_7, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_7.setText(onderwerp7);
                    }
                }, 500);
            }
            if (kleur8.equals("1") || kleur8.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_8, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_8, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_8.setText(onderwerp8);
                    }
                }, 500);
            }
            if (kleur9.equals("1") || kleur9.equals("2")) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_9, "rotationY", 180f, 0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 0.8f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 0.8f);
                objectAnimator1.setDuration(1000);
                objectAnimator2.setDuration(500);
                objectAnimator3.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
                animatorSet.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleX", 1.0f);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textview_spelvak_9, "scaleY", 1.0f);
                        objectAnimator1.setDuration(500);
                        objectAnimator2.setDuration(500);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator1).with(objectAnimator2);
                        animatorSet.start();
                        textview_spelvak_9.setText(onderwerp9);
                    }
                }, 500);
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    kleuren_kruizen();
                }
            }, 500);

            plaatjes = false;
        }

    }

    public void foto(View view){

        HashMap<String, String> kleuren = new HashMap<>();
        kleuren.put("1", kleur1);
        kleuren.put("2", kleur2);
        kleuren.put("3", kleur3);
        kleuren.put("4", kleur4);
        kleuren.put("5", kleur5);
        kleuren.put("6", kleur6);
        kleuren.put("7", kleur7);
        kleuren.put("8", kleur8);
        kleuren.put("9", kleur9);

        HashMap<String, String> alle_onderwerpen = new HashMap<>();
        alle_onderwerpen.put("1", onderwerp1);
        alle_onderwerpen.put("2", onderwerp2);
        alle_onderwerpen.put("3", onderwerp3);
        alle_onderwerpen.put("4", onderwerp4);
        alle_onderwerpen.put("5", onderwerp5);
        alle_onderwerpen.put("6", onderwerp6);
        alle_onderwerpen.put("7", onderwerp7);
        alle_onderwerpen.put("8", onderwerp8);
        alle_onderwerpen.put("9", onderwerp9);

        HashMap<String, String> alle_onderwerpen_nummer = new HashMap<>();
        alle_onderwerpen_nummer.put("1", onderwerp1_nummer);
        alle_onderwerpen_nummer.put("2", onderwerp2_nummer);
        alle_onderwerpen_nummer.put("3", onderwerp3_nummer);
        alle_onderwerpen_nummer.put("4", onderwerp4_nummer);
        alle_onderwerpen_nummer.put("5", onderwerp5_nummer);
        alle_onderwerpen_nummer.put("6", onderwerp6_nummer);
        alle_onderwerpen_nummer.put("7", onderwerp7_nummer);
        alle_onderwerpen_nummer.put("8", onderwerp8_nummer);
        alle_onderwerpen_nummer.put("9", onderwerp9_nummer);

        HashMap<String, String> tijden_uren = new HashMap<>();
        tijden_uren.put("1", ""+tijd1_uren);
        tijden_uren.put("2", ""+tijd2_uren);
        tijden_uren.put("3", ""+tijd3_uren);
        tijden_uren.put("4", ""+tijd4_uren);
        tijden_uren.put("5", ""+tijd5_uren);
        tijden_uren.put("6", ""+tijd6_uren);
        tijden_uren.put("7", ""+tijd7_uren);
        tijden_uren.put("8", ""+tijd8_uren);
        tijden_uren.put("9", ""+tijd9_uren);

        HashMap<String, String> tijden = new HashMap<>();
        tijden.put("1", ""+tijd1_uren+":"+tijd1_minuten+":"+tijd1_seconden);
        tijden.put("2", ""+tijd2_uren+":"+tijd2_minuten+":"+tijd2_seconden);
        tijden.put("3", ""+tijd3_uren+":"+tijd3_minuten+":"+tijd3_seconden);
        tijden.put("4", ""+tijd4_uren+":"+tijd4_minuten+":"+tijd4_seconden);
        tijden.put("5", ""+tijd5_uren+":"+tijd5_minuten+":"+tijd5_seconden);
        tijden.put("6", ""+tijd6_uren+":"+tijd6_minuten+":"+tijd6_seconden);
        tijden.put("7", ""+tijd7_uren+":"+tijd7_minuten+":"+tijd7_seconden);
        tijden.put("8", ""+tijd8_uren+":"+tijd8_minuten+":"+tijd8_seconden);
        tijden.put("9", ""+tijd9_uren+":"+tijd9_minuten+":"+tijd9_seconden);

        String tag = (String) view.getTag();
        String kleur = kleuren.get(tag);
        String onderwerp = alle_onderwerpen.get(tag);
        String onderwerp_nummer = alle_onderwerpen_nummer.get(tag);
        String tijd_uren = tijden_uren.get(tag);
        String tijd = tijden.get(tag);

        nummer = tag;

        switch(kleur){

            case "0":
            case "5":
            case "6":
                if (!gewonnen.equals(naam_speler) && !gewonnen.equals(naam_tegenstander) && status.equals("OK") && animatie_bezig.equals("nee")){
                    if (beurt.equals("3") || beurt.equals("4")){
                        Home.popup_test(this, "popup_1", "leeg");
                    }else if (!beoordelen_speler.equals("0")){
                        Home.popup_test(activity, "popup_6", "popup_6");
                    }else{
                        Intent intent1 = new Intent(Spel.this, Foto_maken.class);
                        intent1.putExtra("naam_speler", naam_speler);
                        intent1.putExtra("naam_tegenstander", naam_tegenstander);
                        intent1.putExtra("punten", punten);
                        intent1.putExtra("kleur_speler", kleur_speler);
                        intent1.putExtra("kleur_tegenstander", kleur_tegenstander);
                        intent1.putExtra("score_speler", score_speler);
                        intent1.putExtra("score_tegenstander", score_tegenstander);
                        intent1.putExtra("beoordelen_speler", beoordelen_speler);
                        intent1.putExtra("beoordelen_tegenstander", beoordelen_tegenstander);
                        intent1.putExtra("chat", chat);
                        intent1.putExtra("id", id);
                        intent1.putExtra("onderwerp", onderwerp);
                        intent1.putExtra("onderwerp_nummer", onderwerp_nummer);
                        intent1.putExtra("tijd", +tijd_beurt_uren + ":" + tijd_beurt_minuten + ":" + tijd_beurt_seconden);
                        if (beurt.equals("1") || beurt.equals("2")) {
                            intent1.putExtra("kleur", kleur_speler);
                        } else {
                            intent1.putExtra("kleur", kleur_tegenstander);
                        }
                        intent1.putExtra("nummer", nummer);
                        intent1.putExtra("profielfoto", profielfoto);
                        intent1.putExtra("beurt", beurt);
                        intent1.putExtra("speltype", speltype);
                        intent1.putExtra("beurt_tellen", beurt_tellen);

                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                    }
                }
                break;
            case "1":
            case "3":
            case "4":
                if (animatie_bezig.equals("nee")) {
                    Intent intent2 = new Intent(Spel.this, Foto_bekijken.class);
                    intent2.putExtra("naam_speler", naam_speler);
                    intent2.putExtra("naam_tegenstander", naam_tegenstander);
                    intent2.putExtra("punten", punten);
                    intent2.putExtra("kleur_speler", kleur_speler);
                    intent2.putExtra("kleur_tegenstander", kleur_tegenstander);
                    intent2.putExtra("score_speler", score_speler);
                    intent2.putExtra("score_tegenstander", score_tegenstander);
                    intent2.putExtra("beoordelen_speler", beoordelen_speler);
                    intent2.putExtra("beoordelen_tegenstander", beoordelen_tegenstander);
                    intent2.putExtra("chat", chat);
                    intent2.putExtra("id", id);
                    intent2.putExtra("onderwerp", onderwerp);
                    intent2.putExtra("onderwerp_nummer", onderwerp_nummer);
                    intent2.putExtra("tijd", tijd);
                    intent2.putExtra("kleur", kleur_speler);
                    intent2.putExtra("nummer", nummer);
                    intent2.putExtra("profielfoto", profielfoto);
                    intent2.putExtra("beurt", beurt);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                }
                break;
            case "2":
                if (!tijd.equals("0:0:0")) {
                    if (!gewonnen.equals(naam_speler) && !gewonnen.equals(naam_tegenstander) && status.equals("OK") && animatie_bezig.equals("nee")) {
                        if (beurt.equals("1") || beurt.equals("2")){
                            Intent intent3 = new Intent(Spel.this, Foto_beoordelen.class);
                            intent3.putExtra("naam_speler", naam_speler);
                            intent3.putExtra("naam_tegenstander", naam_tegenstander);
                            intent3.putExtra("punten", punten);
                            intent3.putExtra("kleur_speler", kleur_speler);
                            intent3.putExtra("kleur_tegenstander", kleur_tegenstander);
                            intent3.putExtra("score_speler", score_speler);
                            intent3.putExtra("score_tegenstander", score_tegenstander);
                            intent3.putExtra("beoordelen_speler", beoordelen_speler);
                            intent3.putExtra("beoordelen_tegenstander", beoordelen_tegenstander);
                            intent3.putExtra("chat", chat);
                            intent3.putExtra("id", id);
                            intent3.putExtra("onderwerp", onderwerp);
                            intent3.putExtra("onderwerp_nummer", onderwerp_nummer);
                            intent3.putExtra("tijd", tijd);
                            intent3.putExtra("kleur", kleur_tegenstander);
                            intent3.putExtra("nummer", nummer);
                            intent3.putExtra("profielfoto", profielfoto);
                            intent3.putExtra("beurt", beurt);
                            startActivity(intent3);
                            overridePendingTransition(0, 0);
                        }
                    }
                }else{
                    if (animatie_bezig.equals("nee")) {
                        Intent intent3 = new Intent(Spel.this, Foto_bekijken.class);
                        intent3.putExtra("naam_speler", naam_speler);
                        intent3.putExtra("naam_tegenstander", naam_tegenstander);
                        intent3.putExtra("punten", punten);
                        intent3.putExtra("kleur_speler", kleur_speler);
                        intent3.putExtra("kleur_tegenstander", kleur_tegenstander);
                        intent3.putExtra("score_speler", score_speler);
                        intent3.putExtra("score_tegenstander", score_tegenstander);
                        intent3.putExtra("beoordelen_speler", beoordelen_speler);
                        intent3.putExtra("beoordelen_tegenstander", beoordelen_tegenstander);
                        intent3.putExtra("chat", chat);
                        intent3.putExtra("id", id);
                        intent3.putExtra("onderwerp", onderwerp);
                        intent3.putExtra("onderwerp_nummer", onderwerp_nummer);
                        intent3.putExtra("tijd", tijd);
                        intent3.putExtra("kleur", kleur_speler);
                        intent3.putExtra("nummer", nummer);
                        intent3.putExtra("profielfoto", profielfoto);
                        intent3.putExtra("beurt", beurt);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                    }
                }
                break;
        }

    }

    public void chat (View view){
        if (animatie_bezig.equals("nee")) {
            Intent intent = new Intent(Spel.this, Chat.class);
            intent.putExtra("nummer", id);
            intent.putExtra("naam_speler", naam_speler);
            intent.putExtra("tegenstander", naam_tegenstander);
            intent.putExtra("kleur_speler", kleur_speler);
            intent.putExtra("kleur_tegenstander", kleur_tegenstander);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void herladen(View view){
        if (animatie_bezig.equals("nee")) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.animatie_rotatie);
            ImageView imageView = (ImageView) findViewById(R.id.laden);
            imageView.startAnimation(animation);
            new spel_laden().execute();
        }
    }

    public void onBackPressed(){
        if (animatie_bezig.equals("nee")){
            super.onBackPressed();
        }
    }

    public void profiel_laden(View view) {
        if (!naam_tegenstander.equals("")){
            new profiel_laden().execute();
        }
    }

    private class profiel_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String tegenstander_nieuw = naam_tegenstander.replace(" ", "%20");

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/profiel_laden.php?naam="+tegenstander_nieuw);
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException");
            }

            if (url != null) {
                try {
                    urlConnection = url.openConnection();
                } catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }
            }

            if (urlConnection != null) {
                try {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                } catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }
            }

            if (inputStream != null) {
                resultaat = inputStream.toString();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try {
                    resultaat = bufferedReader.readLine();
                } catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            }else{
                resultaat = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!resultaat.equals("ERROR")) {
                StringTokenizer stringTokenizer = new StringTokenizer(resultaat, "|");

                String profielfoto = stringTokenizer.nextToken();
                String muntjes = stringTokenizer.nextToken();
                String fotos = stringTokenizer.nextToken();
                String gewonnen = stringTokenizer.nextToken();
                String verloren = stringTokenizer.nextToken();
                String themas = stringTokenizer.nextToken();

                String themas_1a = stringTokenizer.nextToken();
                String themas_1b = stringTokenizer.nextToken();
                String themas_2a = stringTokenizer.nextToken();
                String themas_2b = stringTokenizer.nextToken();
                String themas_3a = stringTokenizer.nextToken();
                String themas_3b = stringTokenizer.nextToken();
                String themas_4a = stringTokenizer.nextToken();
                String themas_4b = stringTokenizer.nextToken();
                String themas_5a = stringTokenizer.nextToken();
                String themas_5b = stringTokenizer.nextToken();

                new download((ImageView) findViewById(R.id.profielfoto)).execute(naam_tegenstander, profielfoto);

                TextView textView1 = (TextView)findViewById(R.id.naam);
                TextView textView2 = (TextView)findViewById(R.id.muntjes);
                TextView textView3 = (TextView)findViewById(R.id.fotos);
                TextView textView4 = (TextView)findViewById(R.id.gewonnen);
                TextView textView5 = (TextView)findViewById(R.id.verloren);
                TextView textView6 = (TextView)findViewById(R.id.TextViewThemas1);
                TextView textView7 = (TextView)findViewById(R.id.TextViewThemas2);

                textView1.setText(naam_tegenstander);
                textView2.setText(getString(R.string.profiel_tegenstander_3)+" "+muntjes);
                textView3.setText(getString(R.string.profiel_tegenstander_4)+" "+fotos);
                textView4.setText(getString(R.string.profiel_tegenstander_5)+" "+gewonnen);
                textView5.setText(getString(R.string.profiel_tegenstander_6)+" "+verloren);

                textView6.setText("");
                textView7.setText("");

                if (themas.contains("1")){
                    textView6.append(getString(R.string.onderwerp_1)+"\n");
                    textView7.append(themas_1a+" / "+themas_1b+" "+getString(R.string.onderwerpen)+"\n");
                }
                if (themas.contains("2")){
                    textView6.append(getString(R.string.onderwerp_2)+"\n");
                    textView7.append(themas_2a+" / "+themas_2b+" "+getString(R.string.onderwerpen)+"\n");
                }
                if (themas.contains("3")){
                    textView6.append(getString(R.string.onderwerp_3)+"\n");
                    textView7.append(themas_3a+" / "+themas_3b+" "+getString(R.string.onderwerpen)+"\n");
                }
                if (themas.contains("4")){
                    textView6.append(getString(R.string.onderwerp_4)+"\n");
                    textView7.append(themas_4a+" / "+themas_4b+" "+getString(R.string.onderwerpen)+"\n");
                }
                if (themas.contains("5")){
                    textView6.append(getString(R.string.onderwerp_5)+"\n");
                    textView7.append(themas_5a+" / "+themas_5b+" "+getString(R.string.onderwerpen)+"\n");
                }

                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.profiel);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public void profiel_uit(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.profiel);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public void vriend_toevoegen(View view){
        String naam_list = naam_tegenstander.replace(" ", "%20");

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", Context.MODE_PRIVATE);
        Set<String> stringSet1 = sharedPreferences.getStringSet("adresboek", new HashSet<String>());

        arrayList_vrienden = new ArrayList<>();
        for (String string : stringSet1) {
            string = string.replace("[enter]", "\n");
            string = string.replace("%20", " ");
            arrayList_vrienden.add(string);
        }
        arrayList_vrienden.add(naam_list);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("adresboek", new HashSet<>(arrayList_vrienden));
        editor.apply();

        Toast toast = Toast.makeText(this.getApplicationContext(), naam_tegenstander + " " + getString(R.string.nieuw_spel_melding_2), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void blokkeren(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Spel.this);
        builder.setTitle(getString(R.string.spellen_melding_5_titel))
                .setMessage(getString(R.string.spellen_melding_5_tekst1)+" "+naam_tegenstander+" "+getString(R.string.spellen_melding_5_tekst2));
        builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spellen_melding_6), getString(R.string.een_ogenblik_geduld), true, false);
                new tegenstander_blokkeren().execute();
            }
        });
        builder.setNegativeButton(getString(R.string.nee), null);
        builder.show();
    }

    private class tegenstander_blokkeren extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_blokkeren = naam_speler.replace(" ", "%20");
            String tegenstander_blokkeren = naam_tegenstander.replace(" ", "%20");

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

        private void tegenstander_blokkeren_klaar(){

            if (resultaat.matches("ERROR")) {
                _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
            }else{
                Toast toast = Toast.makeText(context, getString(R.string.spellen_melding_10), Toast.LENGTH_SHORT);
                toast.show();
            }

        }

    }

    public void beurt_skippen(View view){
        if (beurt.equals("1") || beurt.equals("2")) {
            if (!beoordelen_speler.equals("0")) {
                Home.popup_test(activity, "popup_6", "popup_6");
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(Spel.this);
                builder.setTitle(getString(R.string.spel_melding_3_titel))
                        .setMessage(getString(R.string.spel_melding_3_tekst));
                builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.spel_melding_3_titel), getString(R.string.een_ogenblik_geduld), true, false);
                        new beurt_skippen().execute();
                    }
                });
                builder.setNegativeButton(getString(R.string.nee), null);
                builder.show();
            }
        }
    }

    private class beurt_skippen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_skippen = naam_speler.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/beurt_skippen.php?id="+id+"&naam="+naam_skippen);
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
            if (resultaat.equals("OK")){
                spel_herladen();
            }
        }

    }

}