package nl.stefhost.viewfinder;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class Home extends AppCompatActivity {

    public String resultaat;
    public String naam;
    public String laatste_spel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        String naam = sharedPreferences.getString("naam", null);
        if (naam == null) {
            Intent intent = new Intent(this, Welkom.class);
            startActivity(intent);
            finish();
        }

        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
        SQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS spellen (id INTEGER PRIMARY KEY, id2 text not null, tegenstander text not null, punten text not null, kleur_speler text not null, kleur_tegenstander text not null, score_speler text not null, score_tegenstander text not null, beoordelen_speler text not null, beoordelen_tegenstander text not null, chat text not null, profielfoto text not null, thema text not null, status text, datum text)");
        SQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS chat (id INTEGER PRIMARY KEY, nummer text, afzender text, datum text, bericht text)");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        TextView textView1 = (TextView) findViewById(R.id.textView2);
        TextView textView2 = (TextView) findViewById(R.id.textView3);
        TextView textView3 = (TextView) findViewById(R.id.textView4);
        TextView textView4 = (TextView) findViewById(R.id.textView5);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);

    }

    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam = sharedPreferences.getString("naam", "");
        if (naam.equals("")){
            finish();
        }

        //laatste spel weergeven
        laatste_spel = sharedPreferences.getString("laatste_spel", "");

        if (!laatste_spel.equals("")){

            ImageView imageViewOverlay = (ImageView) findViewById(R.id.imageViewOverlay);
            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
            TextView textView = (TextView) findViewById(R.id.textView);
            imageViewOverlay.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

            new laatste_spel().execute();
        }
    }

    private class laatste_spel extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/laatste_spel.php?nummer="+laatste_spel+"&naam="+naam);
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
            laatste_spel_klaar();
        }

    }


    public void laatste_spel_klaar(){

        StringTokenizer tokens = new StringTokenizer(resultaat, "|");

        String naam_tegenstander = tokens.nextToken();
        String punten = tokens.nextToken();
        String kleur_speler = tokens.nextToken();
        String kleur_tegenstander = tokens.nextToken();
        String score_speler = tokens.nextToken();
        String score_tegenstander = tokens.nextToken();
        String beoordelen_speler = tokens.nextToken();
        String beoordelen_tegenstander = tokens.nextToken();
        String chat = tokens.nextToken();
        String profielfoto = tokens.nextToken();

        final ImageView imageView_foto_tegenstander = (ImageView) findViewById(R.id.foto_tegenstander);
        ImageView imageView_punten_speler_1 = (ImageView) findViewById(R.id.punten_speler_1);
        ImageView imageView_punten_speler_2 = (ImageView) findViewById(R.id.punten_speler_2);
        ImageView imageView_punten_speler_3 = (ImageView) findViewById(R.id.punten_speler_3);
        ImageView imageView_punten_speler_4 = (ImageView) findViewById(R.id.punten_speler_4);
        ImageView imageView_punten_speler_5 = (ImageView) findViewById(R.id.punten_speler_5);
        ImageView imageView_punten_speler_6 = (ImageView) findViewById(R.id.punten_speler_6);
        ImageView imageView_punten_speler_7 = (ImageView) findViewById(R.id.punten_speler_7);
        ImageView imageView_punten_tegenstander_1 = (ImageView) findViewById(R.id.punten_tegenstander_1);
        ImageView imageView_punten_tegenstander_2 = (ImageView) findViewById(R.id.punten_tegenstander_2);
        ImageView imageView_punten_tegenstander_3 = (ImageView) findViewById(R.id.punten_tegenstander_3);
        ImageView imageView_punten_tegenstander_4 = (ImageView) findViewById(R.id.punten_tegenstander_4);
        ImageView imageView_punten_tegenstander_5 = (ImageView) findViewById(R.id.punten_tegenstander_5);
        ImageView imageView_punten_tegenstander_6 = (ImageView) findViewById(R.id.punten_tegenstander_6);
        ImageView imageView_punten_tegenstander_7 = (ImageView) findViewById(R.id.punten_tegenstander_7);
        ImageView imageView_beoordelen_speler = (ImageView) findViewById(R.id.beoordelen_speler);
        ImageView imageView_beoordelen_tegenstander = (ImageView) findViewById(R.id.beoordelen_tegenstander);
        TextView textView_naam_speler = (TextView) findViewById(R.id.naam_speler);
        TextView textView_naam_tegenstander = (TextView) findViewById(R.id.naam_tegenstander);
        TextView textView_chat = (TextView) findViewById(R.id.chat);

        int kleur_Speler = 0;
        int kleur_Speler_uit = 0;
        int kleur_Tegenstander = 0;
        int kleur_Tegenstander_uit = 0;

        if (kleur_speler.equals("groen")){
            kleur_Speler = R.drawable.kleur_1;
            kleur_Speler_uit = R.drawable.kleur_1_uit;
        }else if (kleur_speler.equals("blauw")){
            kleur_Speler = R.drawable.kleur_2;
            kleur_Speler_uit = R.drawable.kleur_2_uit;
        }else if (kleur_speler.equals("paars")){
            kleur_Speler = R.drawable.kleur_3;
            kleur_Speler_uit = R.drawable.kleur_3_uit;
        }else if (kleur_speler.equals("oranje")){
            kleur_Speler = R.drawable.kleur_4;
            kleur_Speler_uit = R.drawable.kleur_4_uit;
        }else{
            kleur_Speler = R.drawable.kleur_5;
            kleur_Speler_uit = R.drawable.kleur_5_uit;
        }

        if (kleur_tegenstander.equals("groen")){
            kleur_Tegenstander = R.drawable.kleur_1;
            kleur_Tegenstander_uit = R.drawable.kleur_1_uit;
        }else if (kleur_tegenstander.equals("blauw")){
            kleur_Tegenstander = R.drawable.kleur_2;
            kleur_Tegenstander_uit = R.drawable.kleur_2_uit;
        }else if (kleur_tegenstander.equals("paars")){
            kleur_Tegenstander = R.drawable.kleur_3;
            kleur_Tegenstander_uit = R.drawable.kleur_3_uit;
        }else if (kleur_tegenstander.equals("oranje")){
            kleur_Tegenstander = R.drawable.kleur_4;
            kleur_Tegenstander_uit = R.drawable.kleur_4_uit;
        }else{
            kleur_Tegenstander = R.drawable.kleur_5;
            kleur_Tegenstander_uit = R.drawable.kleur_5_uit;
        }

        ImageView[] imageViewPuntenSpeler = new ImageView[10];
        imageViewPuntenSpeler[0] = imageView_punten_speler_1;
        imageViewPuntenSpeler[1] = imageView_punten_speler_2;
        imageViewPuntenSpeler[2] = imageView_punten_speler_3;
        imageViewPuntenSpeler[3] = imageView_punten_speler_4;
        imageViewPuntenSpeler[4] = imageView_punten_speler_5;
        imageViewPuntenSpeler[5] = imageView_punten_speler_6;
        imageViewPuntenSpeler[6] = imageView_punten_speler_7;

        ImageView[] imageViewPuntenTegenstander = new ImageView[10];
        imageViewPuntenTegenstander[0] = imageView_punten_tegenstander_1;
        imageViewPuntenTegenstander[1] = imageView_punten_tegenstander_2;
        imageViewPuntenTegenstander[2] = imageView_punten_tegenstander_3;
        imageViewPuntenTegenstander[3] = imageView_punten_tegenstander_4;
        imageViewPuntenTegenstander[4] = imageView_punten_tegenstander_5;
        imageViewPuntenTegenstander[5] = imageView_punten_tegenstander_6;
        imageViewPuntenTegenstander[6] = imageView_punten_tegenstander_7;

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
        while (tellen < 7){
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
        while (tellen < 7){
            if (tellen > (score -1)) {
                imageViewPuntenTegenstander[tellen].setImageResource(R.drawable.zwart);
            }
            tellen++;
        }

        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animatie_oneindig);

        if (beoordelen_speler != null && Integer.parseInt(beoordelen_speler) > 0){
            imageView_beoordelen_speler.setImageResource(R.drawable.beoordelen);
            imageView_beoordelen_speler.startAnimation(myAnimation);
        }else{
            imageView_beoordelen_speler.setVisibility(View.INVISIBLE);
        }
        if (beoordelen_tegenstander != null && Integer.parseInt(beoordelen_tegenstander) > 0){
            imageView_beoordelen_tegenstander.setImageResource(R.drawable.beoordelen);
            //imageView_beoordelen_tegenstander.startAnimation(myAnimation);
        }else{
            imageView_beoordelen_tegenstander.setVisibility(View.INVISIBLE);
        }

        textView_naam_speler.setText(naam);
        textView_naam_tegenstander.setText(naam_tegenstander);
        textView_chat.setText(chat);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        textView_naam_speler.setTypeface(typeface);
        textView_naam_tegenstander.setTypeface(typeface);
        textView_chat.setTypeface(typeface);

        File opslag = Environment.getExternalStorageDirectory();
        File bestand_profielfoto = new File(opslag,"/Viewfinder/profielfotos/"+naam_tegenstander+"_"+profielfoto+".jpg");
        if(bestand_profielfoto.exists()){
            final Bitmap myBitmap = BitmapFactory.decodeFile(bestand_profielfoto.getAbsolutePath());
            imageView_foto_tegenstander.post(new Runnable() {
                public void run() {
                    imageView_foto_tegenstander.setImageBitmap(myBitmap);
                }
            });
        }
    }

    public void start_spel(View view){
        Intent intent = new Intent(this, Spel.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", laatste_spel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void nieuw_spel(View view){
        TextView textView = (TextView) findViewById(R.id.textView2);
        Intent intent = new Intent(Home.this, Nieuw_spel.class);
        if (Build.VERSION.SDK_INT > 21){
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "newGame");
            startActivity(intent, activityOptions.toBundle());
        }else{
            startActivity(intent);
        }
    }

    public void spellen(View view){
        TextView textView = (TextView) findViewById(R.id.textView3);
        Intent intent = new Intent(Home.this, Spellen.class);
        if (Build.VERSION.SDK_INT > 21){
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "myGames");
            startActivity(intent, activityOptions.toBundle());
        }else{
            startActivity(intent);
        }
    }

    public void profiel(View view){
        TextView textView = (TextView) findViewById(R.id.textView4);
        Intent intent = new Intent(Home.this, Profiel.class);
        if (Build.VERSION.SDK_INT > 21){
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "myProfile");
            startActivity(intent, activityOptions.toBundle());
        }else{
            startActivity(intent);
        }
    }

    public void help(View view){
        TextView textView = (TextView) findViewById(R.id.textView5);
        Intent intent = new Intent(Home.this, Help.class);
        if (Build.VERSION.SDK_INT > 21){
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "Help");
            startActivity(intent, activityOptions.toBundle());
        }else{
            startActivity(intent);
        }
    }

}