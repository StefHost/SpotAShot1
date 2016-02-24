package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

import nl.stefhost.viewfinder.functies.laad_plaatje;

public class Foto_beoordelen extends AppCompatActivity {

    public String id;
    public String naam_speler;
    public String naam_tegenstander;
    public String kleur_speler;
    public String kleur_tegenstander;

    public String nummer;
    public String keuze;
    public String resultaat;
    public Context context;
    public android.app.ProgressDialog ProgressDialog;

    int tijd_uren;
    int tijd_minuten;
    int tijd_seconden;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_foto_beoordelen);
        context = Foto_beoordelen.this;

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animatie_rotatie);
        ImageView logo = (ImageView) findViewById(R.id.laden);
        logo.startAnimation(animation);

        spel_laden();
    }

    public void spel_laden(){

        naam_speler = getIntent().getExtras().getString("naam_speler");
        naam_tegenstander = getIntent().getExtras().getString("naam_tegenstander");
        String punten = getIntent().getExtras().getString("punten");
        kleur_speler = getIntent().getExtras().getString("kleur_speler");
        kleur_tegenstander = getIntent().getExtras().getString("kleur_tegenstander");
        String score_speler = getIntent().getExtras().getString("score_speler");
        String score_tegenstander = getIntent().getExtras().getString("score_tegenstander");
        String beoordelen_speler = getIntent().getExtras().getString("beoordelen_speler");
        String beoordelen_tegenstander = getIntent().getExtras().getString("beoordelen_tegenstander");
        String chat = getIntent().getExtras().getString("chat");
        id = getIntent().getExtras().getString("id");
        String onderwerp = getIntent().getExtras().getString("onderwerp");
        String tijd = getIntent().getExtras().getString("tijd");
        String kleur = getIntent().getExtras().getString("kleur");
        nummer = getIntent().getExtras().getString("nummer");
        String profielfoto = getIntent().getExtras().getString("profielfoto");

        final ImageView imageView_foto_tegenstander = (ImageView) findViewById(R.id.foto_tegenstander);
        ImageView imageView_punten_speler_1 = (ImageView) findViewById(R.id.punten_speler_1);
        ImageView imageView_punten_speler_2 = (ImageView) findViewById(R.id.punten_speler_2);
        ImageView imageView_punten_speler_3 = (ImageView) findViewById(R.id.punten_speler_3);
        ImageView imageView_punten_speler_4 = (ImageView) findViewById(R.id.punten_speler_4);
        ImageView imageView_punten_speler_5 = (ImageView) findViewById(R.id.punten_speler_5);
        ImageView imageView_punten_speler_6 = (ImageView) findViewById(R.id.punten_speler_6);
        ImageView imageView_punten_speler_7 = (ImageView) findViewById(R.id.punten_speler_7);
        ImageView imageView_punten_speler_8 = (ImageView) findViewById(R.id.punten_speler_8);
        ImageView imageView_punten_speler_9 = (ImageView) findViewById(R.id.punten_speler_9);
        ImageView imageView_punten_tegenstander_1 = (ImageView) findViewById(R.id.punten_tegenstander_1);
        ImageView imageView_punten_tegenstander_2 = (ImageView) findViewById(R.id.punten_tegenstander_2);
        ImageView imageView_punten_tegenstander_3 = (ImageView) findViewById(R.id.punten_tegenstander_3);
        ImageView imageView_punten_tegenstander_4 = (ImageView) findViewById(R.id.punten_tegenstander_4);
        ImageView imageView_punten_tegenstander_5 = (ImageView) findViewById(R.id.punten_tegenstander_5);
        ImageView imageView_punten_tegenstander_6 = (ImageView) findViewById(R.id.punten_tegenstander_6);
        ImageView imageView_punten_tegenstander_7 = (ImageView) findViewById(R.id.punten_tegenstander_7);
        ImageView imageView_punten_tegenstander_8 = (ImageView) findViewById(R.id.punten_tegenstander_8);
        ImageView imageView_punten_tegenstander_9 = (ImageView) findViewById(R.id.punten_tegenstander_9);
        ImageView imageView_beoordelen_speler = (ImageView) findViewById(R.id.beoordelen_speler);
        ImageView imageView_beoordelen_tegenstander = (ImageView) findViewById(R.id.beoordelen_tegenstander);

        TextView textView_naam_speler = (TextView) findViewById(R.id.naam_speler);
        TextView textView_naam_tegenstander = (TextView) findViewById(R.id.naam_tegenstander);
        TextView textView_chat = (TextView) findViewById(R.id.chat);

        if (kleur_speler == null){
            kleur_speler = "";
        }
        if (kleur_tegenstander == null){
            kleur_tegenstander = "";
        }

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
        imageViewPuntenSpeler[7] = imageView_punten_speler_8;
        imageViewPuntenSpeler[8] = imageView_punten_speler_9;

        ImageView[] imageViewPuntenTegenstander = new ImageView[10];
        imageViewPuntenTegenstander[0] = imageView_punten_tegenstander_1;
        imageViewPuntenTegenstander[1] = imageView_punten_tegenstander_2;
        imageViewPuntenTegenstander[2] = imageView_punten_tegenstander_3;
        imageViewPuntenTegenstander[3] = imageView_punten_tegenstander_4;
        imageViewPuntenTegenstander[4] = imageView_punten_tegenstander_5;
        imageViewPuntenTegenstander[5] = imageView_punten_tegenstander_6;
        imageViewPuntenTegenstander[6] = imageView_punten_tegenstander_7;
        imageViewPuntenTegenstander[7] = imageView_punten_tegenstander_8;
        imageViewPuntenTegenstander[8] = imageView_punten_tegenstander_9;

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

        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animatie_oneindig);

        if (beoordelen_speler != null && Integer.parseInt(beoordelen_speler) > 0){
            imageView_beoordelen_speler.setImageResource(R.drawable.beoordelen);
            imageView_beoordelen_speler.startAnimation(myAnimation);
        }
        if (beoordelen_tegenstander != null && Integer.parseInt(beoordelen_tegenstander) > 0){
            imageView_beoordelen_tegenstander.setImageResource(R.drawable.beoordelen);
            //imageView_beoordelen_tegenstander.startAnimation(myAnimation);
        }

        textView_naam_speler.setText(naam_speler);
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

        TextView textView_onderwerp = (TextView) findViewById(R.id.onderwerp);
        textView_onderwerp.setTypeface(typeface);
        textView_onderwerp.setText(onderwerp);
        ImageView foto = (ImageView) findViewById(R.id.foto);
        String onderwerp_plaatje = onderwerp.replace(" ", "%20");
        onderwerp_plaatje = onderwerp_plaatje.replace("/", "");

        new laad_plaatje(foto).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp_plaatje + ".jpg");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int breedte = metrics.widthPixels;
        float dpi  = getResources().getDisplayMetrics().density;
        float breedte_totaal = breedte - (30 * dpi);
        float breedte_vakje = breedte_totaal / 2;

        if (tijd == null){
            tijd = "0:0:0";
        }

        StringTokenizer tijd_token = new StringTokenizer(tijd, ":");
        tijd_uren = Integer.parseInt(tijd_token.nextToken());
        tijd_minuten = Integer.parseInt(tijd_token.nextToken());
        tijd_seconden = Integer.parseInt(tijd_token.nextToken());

        float vakje1_aan = breedte_vakje / 24 * tijd_uren;
        float vakje1_uit = breedte_vakje / 24 * (24-tijd_uren);

        ImageView imageview_tijd_aan = (ImageView) findViewById(R.id.tijd_aan);
        ImageView imageview_tijd_uit = (ImageView) findViewById(R.id.tijd_uit);
        imageview_tijd_aan.getLayoutParams().width = (int)vakje1_aan;
        imageview_tijd_uit.getLayoutParams().width = (int)vakje1_uit;
        imageview_tijd_aan.requestLayout();
        imageview_tijd_uit.requestLayout();

        if (kleur == null){
            kleur = "";
        }

        switch (kleur) {
            case "groen":
                imageview_tijd_aan.setImageResource(R.drawable.groen);
                break;
            case "blauw":
                imageview_tijd_aan.setImageResource(R.drawable.blauw);
                break;
            case "paars":
                imageview_tijd_aan.setImageResource(R.drawable.paars);
                break;
            case "oranje":
                imageview_tijd_aan.setImageResource(R.drawable.oranje);
                break;
            case "rood":
                imageview_tijd_aan.setImageResource(R.drawable.rood);
                break;
        }

        TextView textView_tijd = (TextView) findViewById(R.id.tijd);
        textView_tijd.setTypeface(typeface);
        textView_tijd.setText(tijd);
        handler = new Handler();
        handler.postDelayed(timer, 0);

    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            tijd_seconden = tijd_seconden -1;

            if (tijd_seconden < 0){
                tijd_minuten = tijd_minuten -1;
                tijd_seconden = 59;
            }
            if (tijd_minuten < 0){
                tijd_uren = tijd_uren -1;
                tijd_minuten = 59;
            }
            if (tijd_uren < 1){
                tijd_uren = 0;
            }

            String uren;
            String minuten;
            String seconden;

            if (tijd_uren < 10){
                uren = "0"+tijd_uren;
            }else{
                uren = ""+tijd_uren;
            }
            if (tijd_minuten < 10){
                minuten = "0"+tijd_minuten;
            }else{
                minuten = ""+tijd_minuten;
            }
            if (tijd_seconden < 10){
                seconden = "0"+tijd_seconden;
            }else{
                seconden = ""+tijd_seconden;
            }

            String tijd = uren+":"+minuten+":"+seconden;
            TextView textView_tijd = (TextView) findViewById(R.id.tijd);
            textView_tijd.setText(tijd);
            handler.postDelayed(timer, 1000);
        }
    };

    public void fout (View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Foto_beoordelen.this);
        builder.setTitle("Reject photo")
                .setMessage("Do you want to reject this photo?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                beoordelen();
            }
        });
        builder.setNegativeButton("NO", null);
        builder.show();
    }

    public void beoordelen(){
        keuze = "fout";
        ProgressDialog = android.app.ProgressDialog.show(this, "Foto beoordelen", "One moment please..", true, false);
        new foto_beoordelen().execute();
    }

    public void goed (View view){
        keuze = "goed";
        ProgressDialog = android.app.ProgressDialog.show(this, "Foto beoordelen", "One moment please..", true, false);
        new foto_beoordelen().execute();
    }

    private class foto_beoordelen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/foto_beoordelen.php?naam="+naam_speler+"&id="+id+"&nummer="+nummer+"&keuze="+keuze);
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
            foto_beoordelen_klaar();
        }

    }

    public void foto_beoordelen_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding("Beoordelen niet mogelijk", "Beoordelen is op dit moment niet mogelijk..\nProbeer het later nog een keer.", context);
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("herladen", "ja");
            editor.apply();

            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    public void chat (View view){
        Intent intent = new Intent(Foto_beoordelen.this, Chat.class);
        intent.putExtra("nummer", id);
        intent.putExtra("naam_speler", naam_speler);
        intent.putExtra("tegenstander", naam_tegenstander);
        intent.putExtra("kleur_speler", kleur_speler);
        intent.putExtra("kleur_tegenstander", kleur_tegenstander);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}