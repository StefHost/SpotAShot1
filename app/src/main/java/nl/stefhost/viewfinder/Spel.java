package nl.stefhost.viewfinder;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import nl.stefhost.viewfinder.functies.laad_plaatje_textview;

public class Spel extends AppCompatActivity {

    public Context context;
    public android.app.ProgressDialog ProgressDialog;
    public String resultaat;
    public String herladen;

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

    public String onderwerp1;
    public String onderwerp2;
    public String onderwerp3;
    public String onderwerp4;
    public String onderwerp5;
    public String onderwerp6;
    public String onderwerp7;
    public String onderwerp8;
    public String onderwerp9;

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

    public int tijd1_minuten;
    public int tijd2_minuten;
    public int tijd3_minuten;
    public int tijd4_minuten;
    public int tijd5_minuten;
    public int tijd6_minuten;
    public int tijd7_minuten;
    public int tijd8_minuten;
    public int tijd9_minuten;

    public int tijd1_seconden;
    public int tijd2_seconden;
    public int tijd3_seconden;
    public int tijd4_seconden;
    public int tijd5_seconden;
    public int tijd6_seconden;
    public int tijd7_seconden;
    public int tijd8_seconden;
    public int tijd9_seconden;

    public String html_kleur_speler;
    public String html_kleur_tegenstander;
    public String html_achtergrond_speler;
    public String html_achtergrond_tegenstander;
    public String nummer;
    public String animatie;
    public String aantal;
    public String gewonnen;
    public String status;

    public ImageView imageview_foto_tegenstander;

    public ImageView imageview_punten_speler_1;
    public ImageView imageview_punten_speler_2;
    public ImageView imageview_punten_speler_3;
    public ImageView imageview_punten_speler_4;
    public ImageView imageview_punten_speler_5;
    public ImageView imageview_punten_speler_6;
    public ImageView imageview_punten_speler_7;
    public ImageView imageview_punten_speler_8;
    public ImageView imageview_punten_speler_9;
    public ImageView imageview_punten_tegenstander_1;
    public ImageView imageview_punten_tegenstander_2;
    public ImageView imageview_punten_tegenstander_3;
    public ImageView imageview_punten_tegenstander_4;
    public ImageView imageview_punten_tegenstander_5;
    public ImageView imageview_punten_tegenstander_6;
    public ImageView imageview_punten_tegenstander_7;
    public ImageView imageview_punten_tegenstander_8;
    public ImageView imageview_punten_tegenstander_9;

    public ImageView imageview_beoordelen_speler;
    public ImageView imageview_beoordelen_tegenstander;

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

    public Handler handler1;
    public Handler handler2;

    public int speler;
    public int tegenstander;

    public Drawable kruis_speler;
    public Drawable kruis_tegenstander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spel);

        context = Spel.this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        id = getIntent().getExtras().getString("id");
        status = getIntent().getExtras().getString("status");
        if (status == null){
            status = "OK";
        }
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam_speler = sharedPreferences.getString("naam", null);

        // laatste spel bovenaan
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String datum = sdf.format(new Date());
        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
        SQLiteDatabase.execSQL("UPDATE spellen SET datum='"+datum+"' WHERE id2='"+id+"' ");

        imageview_foto_tegenstander = (ImageView) findViewById(R.id.foto_tegenstander);

        imageview_punten_speler_1 = (ImageView) findViewById(R.id.punten_speler_1);
        imageview_punten_speler_2 = (ImageView) findViewById(R.id.punten_speler_2);
        imageview_punten_speler_3 = (ImageView) findViewById(R.id.punten_speler_3);
        imageview_punten_speler_4 = (ImageView) findViewById(R.id.punten_speler_4);
        imageview_punten_speler_5 = (ImageView) findViewById(R.id.punten_speler_5);
        imageview_punten_speler_6 = (ImageView) findViewById(R.id.punten_speler_6);
        imageview_punten_speler_7 = (ImageView) findViewById(R.id.punten_speler_7);
        imageview_punten_speler_8 = (ImageView) findViewById(R.id.punten_speler_8);
        imageview_punten_speler_9 = (ImageView) findViewById(R.id.punten_speler_9);
        imageview_punten_tegenstander_1 = (ImageView) findViewById(R.id.punten_tegenstander_1);
        imageview_punten_tegenstander_2 = (ImageView) findViewById(R.id.punten_tegenstander_2);
        imageview_punten_tegenstander_3 = (ImageView) findViewById(R.id.punten_tegenstander_3);
        imageview_punten_tegenstander_4 = (ImageView) findViewById(R.id.punten_tegenstander_4);
        imageview_punten_tegenstander_5 = (ImageView) findViewById(R.id.punten_tegenstander_5);
        imageview_punten_tegenstander_6 = (ImageView) findViewById(R.id.punten_tegenstander_6);
        imageview_punten_tegenstander_7 = (ImageView) findViewById(R.id.punten_tegenstander_7);
        imageview_punten_tegenstander_8 = (ImageView) findViewById(R.id.punten_tegenstander_8);
        imageview_punten_tegenstander_9 = (ImageView) findViewById(R.id.punten_tegenstander_9);

        imageview_beoordelen_speler = (ImageView) findViewById(R.id.beoordelen_speler);
        imageview_beoordelen_tegenstander = (ImageView) findViewById(R.id.beoordelen_tegenstander);

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

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animatie_rotatie);
        ImageView imageView = (ImageView) findViewById(R.id.laden);
        imageView.startAnimation(animation);

        new spel_laden().execute();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        herladen = sharedPreferences.getString("herladen", "");
        String upload = sharedPreferences.getString("upload", "");

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (herladen.equals("ja")){
            if (upload.equals("NEE")) {
                _functions.melding("Helaas", "Je tegenstander heeft deze foto al gemaakt..", context);
                editor.putString("upload", "");
            }

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.animatie_rotatie);
            ImageView imageView = (ImageView) findViewById(R.id.laden);
            imageView.startAnimation(animation);
            new spel_laden().execute();

            editor.putString("herladen", "nee");
            editor.apply();
        }

    }

    private class spel_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/spel_laden.php?nummer="+id+"&naam="+naam_speler);
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
            _functions.melding("SERVER ERROR", "Probeer het later nog een keer.", context);
        }else{
            StringTokenizer tokens = new StringTokenizer(resultaat, "|");

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

            File opslag = Environment.getExternalStorageDirectory();
            File bestand_profielfoto = new File(opslag,"/Viewfinder/profielfotos/"+naam_tegenstander+"_"+profielfoto+".jpg");
            if(bestand_profielfoto.exists()){
                final Bitmap myBitmap = BitmapFactory.decodeFile(bestand_profielfoto.getAbsolutePath());
                imageview_foto_tegenstander.post(new Runnable() {
                    public void run() {
                        imageview_foto_tegenstander.setImageBitmap(myBitmap);
                    }
                });
            }

            int kleur_Speler = 0;
            int kleur_Speler_uit = 0;

            switch (kleur_speler){
                case "groen":
                    kleur_Speler = R.drawable.kleur_1;
                    kleur_Speler_uit = R.drawable.kleur_1_uit;
                    html_kleur_speler = "#197b30";
                    html_achtergrond_speler = "#0c3d18";
                    speler = R.drawable.groen;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.kruis_groen);
                    break;
                case "blauw":
                    kleur_Speler = R.drawable.kleur_2;
                    kleur_Speler_uit = R.drawable.kleur_2_uit;
                    html_kleur_speler = "#0000ff";
                    html_achtergrond_speler = "#00007f";
                    speler = R.drawable.blauw;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.kruis_blauw);
                    break;
                case "paars":
                    kleur_Speler = R.drawable.kleur_3;
                    kleur_Speler_uit = R.drawable.kleur_3_uit;
                    html_kleur_speler = "#92278f";
                    html_achtergrond_speler = "#491347";
                    speler = R.drawable.paars;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.kruis_paars);
                    break;
                case "oranje":
                    kleur_Speler = R.drawable.kleur_4;
                    kleur_Speler_uit = R.drawable.kleur_4_uit;
                    html_kleur_speler = "#f26522";
                    html_achtergrond_speler = "#793211";
                    speler = R.drawable.oranje;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.kruis_oranje);
                    break;
                case "rood":
                    kleur_Speler = R.drawable.kleur_5;
                    kleur_Speler_uit = R.drawable.kleur_5_uit;
                    html_kleur_speler = "#ED1C24";
                    html_achtergrond_speler = "#760e12";
                    speler = R.drawable.rood;
                    kruis_speler = ContextCompat.getDrawable(context, R.drawable.kruis_rood);
                    break;
            }

            int kleur_Tegenstander = 0;
            int kleur_Tegenstander_uit = 0;

            switch (kleur_tegenstander){
                case "groen":
                    kleur_Tegenstander = R.drawable.kleur_1;
                    kleur_Tegenstander_uit = R.drawable.kleur_1_uit;
                    html_kleur_tegenstander = "#197b30";
                    html_achtergrond_tegenstander = "#0c3d18";
                    tegenstander = R.drawable.groen;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.kruis_groen);
                    break;
                case "blauw":
                    kleur_Tegenstander = R.drawable.kleur_2;
                    kleur_Tegenstander_uit = R.drawable.kleur_2_uit;
                    html_kleur_tegenstander = "#0000ff";
                    html_achtergrond_tegenstander = "#00007f";
                    tegenstander = R.drawable.blauw;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.kruis_blauw);
                    break;
                case "paars":
                    kleur_Tegenstander = R.drawable.kleur_3;
                    kleur_Tegenstander_uit = R.drawable.kleur_3_uit;
                    html_kleur_tegenstander = "#92278f";
                    html_achtergrond_tegenstander = "#491347";
                    tegenstander = R.drawable.paars;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.kruis_paars);
                    break;
                case "oranje":
                    kleur_Tegenstander = R.drawable.kleur_4;
                    kleur_Tegenstander_uit = R.drawable.kleur_4_uit;
                    html_kleur_tegenstander = "#f26522";
                    html_achtergrond_tegenstander = "#793211";
                    tegenstander = R.drawable.oranje;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.kruis_oranje);
                    break;
                case "rood":
                    kleur_Tegenstander = R.drawable.kleur_5;
                    kleur_Tegenstander_uit = R.drawable.kleur_5_uit;
                    html_kleur_tegenstander = "#ED1C24";
                    html_achtergrond_tegenstander = "#760e12";
                    tegenstander = R.drawable.rood;
                    kruis_tegenstander = ContextCompat.getDrawable(context, R.drawable.kruis_rood);
                    break;
            }

            ImageView[] imageViewPuntenSpeler = new ImageView[10];
            imageViewPuntenSpeler[0] = imageview_punten_speler_1;
            imageViewPuntenSpeler[1] = imageview_punten_speler_2;
            imageViewPuntenSpeler[2] = imageview_punten_speler_3;
            imageViewPuntenSpeler[3] = imageview_punten_speler_4;
            imageViewPuntenSpeler[4] = imageview_punten_speler_5;
            imageViewPuntenSpeler[5] = imageview_punten_speler_6;
            imageViewPuntenSpeler[6] = imageview_punten_speler_7;
            imageViewPuntenSpeler[7] = imageview_punten_speler_8;
            imageViewPuntenSpeler[8] = imageview_punten_speler_9;

            ImageView[] imageViewPuntenTegenstander = new ImageView[10];
            imageViewPuntenTegenstander[0] = imageview_punten_tegenstander_1;
            imageViewPuntenTegenstander[1] = imageview_punten_tegenstander_2;
            imageViewPuntenTegenstander[2] = imageview_punten_tegenstander_3;
            imageViewPuntenTegenstander[3] = imageview_punten_tegenstander_4;
            imageViewPuntenTegenstander[4] = imageview_punten_tegenstander_5;
            imageViewPuntenTegenstander[5] = imageview_punten_tegenstander_6;
            imageViewPuntenTegenstander[6] = imageview_punten_tegenstander_7;
            imageViewPuntenTegenstander[7] = imageview_punten_tegenstander_8;
            imageViewPuntenTegenstander[8] = imageview_punten_tegenstander_9;

            int tellen = 0;
            int score_Speler = Integer.parseInt(score_speler);
            int score_Tegenstander = Integer.parseInt(score_tegenstander);
            int score = Integer.parseInt(punten);

            while (tellen < score){
                imageViewPuntenSpeler[tellen].setImageResource(kleur_Speler_uit);
                tellen++;
            }
            tellen = 0;
            while (tellen < score_Speler){
                imageViewPuntenSpeler[tellen].setImageResource(kleur_Speler);
                tellen++;
            }
            tellen = 0;
            while (tellen < 9){
                if (tellen > (score -1)) {
                    imageViewPuntenSpeler[tellen].setImageResource(R.drawable.zwart);
                }
                tellen++;
            }

            tellen = 0;
            while (tellen < score){
                imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander_uit);
                tellen++;
            }
            tellen = 0;
            while (tellen < score_Tegenstander){
                imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander);
                tellen++;
            }
            tellen = 0;
            while (tellen < 9){
                if (tellen > (score -1)) {
                    imageViewPuntenTegenstander[tellen].setImageResource(R.drawable.zwart);
                }
                tellen++;
            }

            Animation myAnimation = AnimationUtils.loadAnimation(context, R.anim.animatie_oneindig);

            if (Integer.parseInt(beoordelen_speler) > 0){
                imageview_beoordelen_speler.setImageResource(R.drawable.beoordelen);
                imageview_beoordelen_speler.startAnimation(myAnimation);
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
            float breedte_totaal = breedte - (30 * dpi);
            float breedte_vakje = breedte_totaal / 3;

            float vakje1_aan = breedte_vakje / 24 * tijd1_uren;
            float vakje1_uit = breedte_vakje / 24 * (24-tijd1_uren);
            float vakje2_aan = breedte_vakje / 24 * tijd2_uren;
            float vakje2_uit = breedte_vakje / 24 * (24-tijd2_uren);
            float vakje3_aan = breedte_vakje / 24 * tijd3_uren;
            float vakje3_uit = breedte_vakje / 24 * (24-tijd3_uren);
            float vakje4_aan = breedte_vakje / 24 * tijd4_uren;
            float vakje4_uit = breedte_vakje / 24 * (24-tijd4_uren);
            float vakje5_aan = breedte_vakje / 24 * tijd5_uren;
            float vakje5_uit = breedte_vakje / 24 * (24-tijd5_uren);
            float vakje6_aan = breedte_vakje / 24 * tijd6_uren;
            float vakje6_uit = breedte_vakje / 24 * (24-tijd6_uren);
            float vakje7_aan = breedte_vakje / 24 * tijd7_uren;
            float vakje7_uit = breedte_vakje / 24 * (24-tijd7_uren);
            float vakje8_aan = breedte_vakje / 24 * tijd8_uren;
            float vakje8_uit = breedte_vakje / 24 * (24-tijd8_uren);
            float vakje9_aan = breedte_vakje / 24 * tijd9_uren;
            float vakje9_uit = breedte_vakje / 24 * (24-tijd9_uren);

            if (tijd1_uren == 0.0 && tijd1_minuten == 0.0 && tijd1_seconden == 0.0){
                imageview_tijd_1_uit.setVisibility(View.INVISIBLE);
                vakje1_aan = 0;
                vakje1_uit = breedte_vakje;
            }else{
                imageview_tijd_1_uit.setImageResource(R.drawable.border_grijs);
            }
            if (tijd2_uren == 0.0 && tijd2_minuten == 0.0 && tijd2_seconden == 0.0){
                imageview_tijd_2_uit.setVisibility(View.INVISIBLE);
                vakje2_aan = 0;
                vakje2_uit = breedte_vakje;
            }else{
                imageview_tijd_2_uit.setImageResource(R.drawable.border_grijs);
            }
            if (tijd3_uren == 0.0 && tijd3_minuten == 0.0 && tijd3_seconden == 0.0){
                imageview_tijd_3_uit.setVisibility(View.INVISIBLE);
                vakje3_aan = 0;
                vakje3_uit = breedte_vakje;
            }else{
                imageview_tijd_3_uit.setImageResource(R.drawable.border_grijs);
            }

            if (tijd4_uren == 0.0 && tijd4_minuten == 0.0 && tijd4_seconden == 0.0){
                imageview_tijd_4_uit.setVisibility(View.INVISIBLE);
                vakje4_aan = 0;
                vakje4_uit = breedte_vakje;
            }else{
                imageview_tijd_4_uit.setImageResource(R.drawable.border_grijs);
            }
            if (tijd5_uren == 0.0 && tijd5_minuten == 0.0 && tijd5_seconden == 0.0){
                imageview_tijd_5_uit.setVisibility(View.INVISIBLE);
                vakje5_aan = 0;
                vakje5_uit = breedte_vakje;
            }else{
                imageview_tijd_5_uit.setImageResource(R.drawable.border_grijs);
            }
            if (tijd6_uren == 0.0 && tijd6_minuten == 0.0 && tijd6_seconden == 0.0){
                imageview_tijd_6_uit.setVisibility(View.INVISIBLE);
                vakje6_aan = 0;
                vakje6_uit = breedte_vakje;
            }else{
                imageview_tijd_6_uit.setImageResource(R.drawable.border_grijs);
            }
            if (tijd7_uren == 0.0 && tijd7_minuten == 0.0 && tijd7_seconden == 0.0){
                imageview_tijd_7_uit.setVisibility(View.INVISIBLE);
                vakje7_aan = 0;
                vakje7_uit = breedte_vakje;
            }else{
                imageview_tijd_7_uit.setImageResource(R.drawable.border_grijs);
            }
            if (tijd8_uren == 0.0 && tijd8_minuten == 0.0 && tijd8_seconden == 0.0){
                imageview_tijd_8_uit.setVisibility(View.INVISIBLE);
                vakje8_aan = 0;
                vakje8_uit = breedte_vakje;
            }else{
                imageview_tijd_8_uit.setImageResource(R.drawable.border_grijs);
            }
            if (tijd9_uren == 0.0 && tijd9_minuten == 0.0 && tijd9_seconden == 0.0){
                imageview_tijd_9_uit.setVisibility(View.INVISIBLE);
                vakje9_aan = 0;
                vakje9_uit = breedte_vakje;
            }else{
                imageview_tijd_9_uit.setImageResource(R.drawable.border_grijs);
            }

            imageview_tijd_1_aan.getLayoutParams().width = (int)vakje1_aan;
            imageview_tijd_1_uit.getLayoutParams().width = (int)vakje1_uit;
            imageview_tijd_2_aan.getLayoutParams().width = (int)vakje2_aan;
            imageview_tijd_2_uit.getLayoutParams().width = (int)vakje2_uit;
            imageview_tijd_3_aan.getLayoutParams().width = (int)vakje3_aan;
            imageview_tijd_3_uit.getLayoutParams().width = (int)vakje3_uit;
            imageview_tijd_4_aan.getLayoutParams().width = (int)vakje4_aan;
            imageview_tijd_4_uit.getLayoutParams().width = (int)vakje4_uit;
            imageview_tijd_5_aan.getLayoutParams().width = (int)vakje5_aan;
            imageview_tijd_5_uit.getLayoutParams().width = (int)vakje5_uit;
            imageview_tijd_6_aan.getLayoutParams().width = (int)vakje6_aan;
            imageview_tijd_6_uit.getLayoutParams().width = (int)vakje6_uit;
            imageview_tijd_7_aan.getLayoutParams().width = (int)vakje7_aan;
            imageview_tijd_7_uit.getLayoutParams().width = (int)vakje7_uit;
            imageview_tijd_8_aan.getLayoutParams().width = (int)vakje8_aan;
            imageview_tijd_8_uit.getLayoutParams().width = (int)vakje8_uit;
            imageview_tijd_9_aan.getLayoutParams().width = (int)vakje9_aan;
            imageview_tijd_9_uit.getLayoutParams().width = (int)vakje9_uit;

            imageview_tijd_1_aan.requestLayout();
            imageview_tijd_1_uit.requestLayout();
            imageview_tijd_2_aan.requestLayout();
            imageview_tijd_2_uit.requestLayout();
            imageview_tijd_3_aan.requestLayout();
            imageview_tijd_3_uit.requestLayout();
            imageview_tijd_4_aan.requestLayout();
            imageview_tijd_4_uit.requestLayout();
            imageview_tijd_5_aan.requestLayout();
            imageview_tijd_5_uit.requestLayout();
            imageview_tijd_6_aan.requestLayout();
            imageview_tijd_6_uit.requestLayout();
            imageview_tijd_7_aan.requestLayout();
            imageview_tijd_7_uit.requestLayout();
            imageview_tijd_8_aan.requestLayout();
            imageview_tijd_8_uit.requestLayout();
            imageview_tijd_9_aan.requestLayout();
            imageview_tijd_9_uit.requestLayout();

            if (gewonnen.equals(naam_speler)){
                textview_gewonnen.setText(getString(R.string.spel_1));
            }
            if (gewonnen.equals(naam_tegenstander)){
                textview_gewonnen.setText(getString(R.string.spel_2));
            }

            //SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
            //SQLiteDatabase.execSQL("delete from laatste_spel");
            //SQLiteDatabase.execSQL("INSERT INTO laatste_spel (id, naam_speler, naam_tegenstander, kleur_speler, kleur_tegenstander, score_speler, score_tegenstander, punten, beoordelen_speler, beoordelen_tegenstander, chat, profielfoto) VALUES ('"+id+"', '"+naam_speler+"', '"+naam_tegenstander+"', '"+kleur_speler+"', '"+kleur_tegenstander+"', '"+score_speler+"', '"+score_tegenstander+"', '"+punten+"', '"+beoordelen_speler+"', '"+beoordelen_tegenstander+"', '"+chat+"', '"+profielfoto+"')");

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("laatste_spel", id);
            editor.apply();

            kleuren_kruizen();
        }

    }

    public void kleuren_kruizen(){

        String[] kleuren = {kleur1, kleur2, kleur3, kleur4, kleur5, kleur6, kleur7, kleur8, kleur9};
        TextView[] spelvakken = {textview_spelvak_1, textview_spelvak_2, textview_spelvak_3, textview_spelvak_4, textview_spelvak_5, textview_spelvak_6, textview_spelvak_7, textview_spelvak_8, textview_spelvak_9};
        TextView[] achtergronden = {textview_achtergrond_1, textview_achtergrond_2, textview_achtergrond_3, textview_achtergrond_4, textview_achtergrond_5, textview_achtergrond_6, textview_achtergrond_7, textview_achtergrond_8, textview_achtergrond_9};
        ImageView[] tijden = {imageview_tijd_1_aan, imageview_tijd_2_aan, imageview_tijd_3_aan, imageview_tijd_4_aan, imageview_tijd_5_aan, imageview_tijd_6_aan, imageview_tijd_7_aan, imageview_tijd_8_aan, imageview_tijd_9_aan};
        TextView[] kruizen = {textview_kruis_1, textview_kruis_2, textview_kruis_3, textview_kruis_4, textview_kruis_5, textview_kruis_6, textview_kruis_7, textview_kruis_8, textview_kruis_9};

        int tellen = 0;
        while (tellen < 9){

            String kleur = kleuren[tellen];
            TextView spelvak = spelvakken[tellen];
            TextView achtergrond = achtergronden[tellen];
            ImageView tijd = tijden[tellen];
            TextView kruis = kruizen[tellen];

            switch (kleur){

                case "0":
                    spelvak.setBackgroundColor(Color.parseColor("#464646"));
                    achtergrond.setBackgroundColor(0);
                    tijd.setImageResource(R.drawable.grijs);
                    kruis.setBackground(null);
                    break;
                case "1":
                    spelvak.setBackgroundColor(Color.parseColor(html_kleur_speler));
                    achtergrond.setBackgroundColor(0);
                    tijd.setImageResource(speler);
                    kruis.setBackground(null);
                    break;
                case "2":
                    spelvak.setBackgroundColor(Color.parseColor(html_kleur_tegenstander));
                    achtergrond.setBackgroundColor(0);
                    tijd.setImageResource(tegenstander);
                    kruis.setBackground(null);
                    break;
                case "3":
                    spelvak.setBackgroundColor(Color.parseColor(html_kleur_speler));
                    achtergrond.setBackgroundColor(Color.parseColor(html_achtergrond_speler));
                    tijd.setImageResource(speler);
                    kruis.setBackground(null);
                    break;
                case "4":
                    spelvak.setBackgroundColor(Color.parseColor(html_kleur_tegenstander));
                    achtergrond.setBackgroundColor(Color.parseColor(html_achtergrond_tegenstander));
                    tijd.setImageResource(tegenstander);
                    kruis.setBackground(null);
                    break;
                case "5":
                    spelvak.setBackgroundColor(Color.parseColor("#464646"));
                    achtergrond.setBackgroundColor(0);
                    tijd.setImageResource(R.drawable.grijs);
                    kruis.setBackground(kruis_speler);
                    break;
                case "6":
                    spelvak.setBackgroundColor(Color.parseColor("#464646"));
                    achtergrond.setBackgroundColor(0);
                    tijd.setImageResource(R.drawable.grijs);
                    kruis.setBackground(kruis_tegenstander);
                    break;
            }

            tellen++;
        }

        ImageView imageView = (ImageView) findViewById(R.id.laden);
        imageView.clearAnimation();
        imageView.setVisibility(View.INVISIBLE);

        Log.d("Viewfinder", animatie);

        if (animatie.equals("N")){
            handler1 = new Handler();
            handler1.post(animatie_fadein);
        }else{
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.VISIBLE);
        }

        /*if (animatie.equals("0")){
            handler2 = new Handler();
            handler2.postDelayed(herlaad_spel, 0);
        }else{
            handler2 = new Handler();
            handler2.postDelayed(herlaad_spel, 3000);
        }*/

    }

    private Runnable animatie_fadein = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_fadein);
            animation.setFillAfter(true);
            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.VISIBLE);
            textView.startAnimation(animation);
            handler1.postDelayed(animatie_fadeout, 2000);
        }
    };

    private Runnable animatie_fadeout = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animatie_fadeout);
            animation.setFillAfter(true);
            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.startAnimation(animation);
            handler1.postDelayed(start_animaties, 1000);
        }
    };

    private Runnable start_animaties = new Runnable() {
        @Override
        public void run() {

            TextView textView = (TextView) findViewById(R.id.tekst);
            textView.setVisibility(View.INVISIBLE);

            Animation myAnimation = AnimationUtils.loadAnimation(context, R.anim.animatie);

                if (animatie.contains("1")){
                    textview_spelvak_1.startAnimation(myAnimation);
                }
                if (animatie.contains("2")){
                    textview_spelvak_2.startAnimation(myAnimation);
                }
                if (animatie.contains("3")){
                    textview_spelvak_3.startAnimation(myAnimation);
                }
                if (animatie.contains("4")){
                    textview_spelvak_4.startAnimation(myAnimation);
                }
                if (animatie.contains("5")){
                    textview_spelvak_5.startAnimation(myAnimation);
                }
                if (animatie.contains("6")){
                    textview_spelvak_6.startAnimation(myAnimation);
                }
                if (animatie.contains("7")){
                    textview_spelvak_7.startAnimation(myAnimation);
                }
                if (animatie.contains("8")){
                    textview_spelvak_8.startAnimation(myAnimation);
                }
                if (animatie.contains("9")){
                    textview_spelvak_9.startAnimation(myAnimation);
                }
                if (animatie.contains("a")){
                    textview_spelvak_1.startAnimation(myAnimation);
                    textview_spelvak_2.startAnimation(myAnimation);
                    textview_spelvak_3.startAnimation(myAnimation);
                }
                if (animatie.contains("b")){
                    textview_spelvak_4.startAnimation(myAnimation);
                    textview_spelvak_5.startAnimation(myAnimation);
                    textview_spelvak_6.startAnimation(myAnimation);
                }
                if (animatie.contains("c")){
                    textview_spelvak_7.startAnimation(myAnimation);
                    textview_spelvak_8.startAnimation(myAnimation);
                    textview_spelvak_9.startAnimation(myAnimation);
                }
                if (animatie.contains("d")){
                    textview_spelvak_1.startAnimation(myAnimation);
                    textview_spelvak_4.startAnimation(myAnimation);
                    textview_spelvak_7.startAnimation(myAnimation);
                }
                if (animatie.contains("e")){
                    textview_spelvak_2.startAnimation(myAnimation);
                    textview_spelvak_5.startAnimation(myAnimation);
                    textview_spelvak_8.startAnimation(myAnimation);
                }
                if (animatie.contains("f")){
                    textview_spelvak_3.startAnimation(myAnimation);
                    textview_spelvak_6.startAnimation(myAnimation);
                    textview_spelvak_9.startAnimation(myAnimation);
                }

            if (animatie.contains("N")){

                Animation test = AnimationUtils.loadAnimation(context, R.anim.animatie_test);
                test.setFillAfter(true);
                test.setFillBefore(true);

                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.startAnimation(test);
            }

        }
    };

    private Runnable herlaad_spel = new Runnable() {
        @Override
        public void run() {
            if (!aantal.equals("1")){
                ProgressDialog = android.app.ProgressDialog.show(context, "Reloading game", "One moment please..", true, false);
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
                    if (x2 - x1 > 300){
                        kleuren_of_plaatjes();
                    }
                }
                if (x2 < x1){
                    if (x1 - x2 > 300){
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

            final String onderwerp1_plaatje = onderwerp1.replace(" ", "%20").replace("/", "");
            final String onderwerp2_plaatje = onderwerp2.replace(" ", "%20").replace("/", "");
            final String onderwerp3_plaatje = onderwerp3.replace(" ", "%20").replace("/", "");
            final String onderwerp4_plaatje = onderwerp4.replace(" ", "%20").replace("/", "");
            final String onderwerp5_plaatje = onderwerp5.replace(" ", "%20").replace("/", "");
            final String onderwerp6_plaatje = onderwerp6.replace(" ", "%20").replace("/", "");
            final String onderwerp7_plaatje = onderwerp7.replace(" ", "%20").replace("/", "");
            final String onderwerp8_plaatje = onderwerp8.replace(" ", "%20").replace("/", "");
            final String onderwerp9_plaatje = onderwerp9.replace(" ", "%20").replace("/", "");

            if (kleur1.equals("1") || kleur1.equals("2") || kleur1.equals("3") || kleur1.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_1).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp1_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur2.equals("1") || kleur2.equals("2") || kleur2.equals("3") || kleur2.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_2).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp2_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur3.equals("1") || kleur3.equals("2") || kleur3.equals("3") || kleur3.equals("4")) {
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
                        new laad_plaatje_textview(textview_spelvak_3).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp3_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur4.equals("1") || kleur4.equals("2") || kleur4.equals("3") || kleur4.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_4).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp4_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur5.equals("1") || kleur5.equals("2") || kleur5.equals("3") || kleur5.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_5).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp5_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur6.equals("1") || kleur6.equals("2") || kleur6.equals("3") || kleur6.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_6).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp6_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur7.equals("1") || kleur7.equals("2") || kleur7.equals("3") || kleur7.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_7).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp7_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur8.equals("1") || kleur8.equals("2") || kleur8.equals("3") || kleur8.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_8).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp8_plaatje + ".jpg");
                    }
                }, 500);
            }
            if (kleur9.equals("1") || kleur9.equals("2") || kleur9.equals("3") || kleur9.equals("4")){
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
                        new laad_plaatje_textview(textview_spelvak_9).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp9_plaatje + ".jpg");
                    }
                }, 500);
            }

            plaatjes = true;
        }else{

            if (kleur1.equals("1") || kleur1.equals("2") || kleur1.equals("3") || kleur1.equals("4")) {
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
            if (kleur2.equals("1") || kleur2.equals("2") || kleur2.equals("3") || kleur2.equals("4")) {
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
            if (kleur3.equals("1") || kleur3.equals("2") || kleur3.equals("3") || kleur3.equals("4")) {
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
            if (kleur4.equals("1") || kleur4.equals("2") || kleur4.equals("3") || kleur4.equals("4")) {
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
            if (kleur5.equals("1") || kleur5.equals("2") || kleur5.equals("3") || kleur5.equals("4")) {
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
            if (kleur6.equals("1") || kleur6.equals("2") || kleur6.equals("3") || kleur6.equals("4")) {
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
            if (kleur7.equals("1") || kleur7.equals("2") || kleur7.equals("3") || kleur7.equals("4")) {
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
            if (kleur8.equals("1") || kleur8.equals("2") || kleur8.equals("3") || kleur8.equals("4")) {
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
            if (kleur9.equals("1") || kleur9.equals("2") || kleur9.equals("3") || kleur9.equals("4")) {
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
        String tijd_uren = tijden_uren.get(tag);
        String tijd = tijden.get(tag);

        nummer = tag;

        switch(kleur){

            case "0":
            case "5":
            case "6":
                if (!gewonnen.equals(naam_speler) && !gewonnen.equals(naam_tegenstander) && status.equals("OK")){
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
                    intent1.putExtra("tijd", tijd);
                    intent1.putExtra("kleur", kleur_speler);
                    intent1.putExtra("nummer", nummer);
                    intent1.putExtra("profielfoto", profielfoto);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                }
                break;
            case "1":
            case "3":
            case "4":
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
                intent2.putExtra("tijd", tijd);
                intent2.putExtra("kleur", kleur_speler);
                intent2.putExtra("nummer", nummer);
                intent2.putExtra("profielfoto", profielfoto);
                startActivity(intent2);
                overridePendingTransition(0, 0);
                break;
            case "2":
                if (!tijd_uren.equals("0")) {
                    if (!gewonnen.equals(naam_speler) && !gewonnen.equals(naam_tegenstander) && status.equals("OK")) {
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
                        intent3.putExtra("tijd", tijd);
                        intent3.putExtra("kleur", kleur_tegenstander);
                        intent3.putExtra("nummer", nummer);
                        intent3.putExtra("profielfoto", profielfoto);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                    }
                }else{
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
                    intent3.putExtra("tijd", tijd);
                    intent3.putExtra("kleur", kleur_speler);
                    intent3.putExtra("nummer", nummer);
                    intent3.putExtra("profielfoto", profielfoto);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                }
                break;
        }

    }

    public void chat (View view){
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