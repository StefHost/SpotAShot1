package nl.stefhost.spotashot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import nl.stefhost.spotashot.functies.laad_plaatje;

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

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_spel", "0");
        editor.apply();

        spel_laden();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidige_spel", "");
        editor.apply();
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
        String onderwerp_nummer = getIntent().getExtras().getString("onderwerp_nummer");
        String tijd = getIntent().getExtras().getString("tijd");
        String kleur = getIntent().getExtras().getString("kleur");
        nummer = getIntent().getExtras().getString("nummer");
        String profielfoto = getIntent().getExtras().getString("profielfoto");
        String beurt = getIntent().getExtras().getString("beurt");

        final ImageView imageView_foto_tegenstander = (ImageView) findViewById(R.id.foto_tegenstander);
        ImageView imageView_punten_speler_1 = (ImageView) findViewById(R.id.punten_speler_1);
        ImageView imageView_punten_speler_2 = (ImageView) findViewById(R.id.punten_speler_2);
        ImageView imageView_punten_speler_3 = (ImageView) findViewById(R.id.punten_speler_3);
        ImageView imageView_punten_tegenstander_1 = (ImageView) findViewById(R.id.punten_tegenstander_1);
        ImageView imageView_punten_tegenstander_2 = (ImageView) findViewById(R.id.punten_tegenstander_2);
        ImageView imageView_punten_tegenstander_3 = (ImageView) findViewById(R.id.punten_tegenstander_3);
        ImageView imageView_beoordelen_speler = (ImageView) findViewById(R.id.beoordelen_speler);
        ImageView imageView_beoordelen_tegenstander = (ImageView) findViewById(R.id.beoordelen_tegenstander);
        ImageView imageView_beurt_speler = (ImageView) findViewById(R.id.beurt_speler);
        ImageView imageView_beurt_tegenstander = (ImageView) findViewById(R.id.beurt_tegenstander);

        TextView textView_naam_speler = (TextView) findViewById(R.id.naam_speler);
        TextView textView_naam_tegenstander = (TextView) findViewById(R.id.naam_tegenstander);
        TextView textView_chat = (TextView) findViewById(R.id.chat);

        RelativeLayout BeoordelenSpeler = (RelativeLayout) findViewById(R.id.BeoordelenSpeler);
        RelativeLayout BeoordelenTegenstander = (RelativeLayout) findViewById(R.id.BeoordelenTegenstander);

        if (kleur_speler == null){
            kleur_speler = "";
        }
        if (kleur_tegenstander == null){
            kleur_tegenstander = "";
        }

        int kleur_Speler;
        int kleur_Speler_uit;
        int kleur_Tegenstander;
        int kleur_Tegenstander_uit;

        if (kleur_speler.equals("groen")){
            kleur_Speler = R.drawable.oog_1;
            kleur_Speler_uit = R.drawable.oog_1_uit;
        }else if (kleur_speler.equals("blauw")){
            kleur_Speler = R.drawable.oog_2;
            kleur_Speler_uit = R.drawable.oog_2_uit;
        }else if (kleur_speler.equals("paars")){
            kleur_Speler = R.drawable.oog_3;
            kleur_Speler_uit = R.drawable.oog_3_uit;
        }else if (kleur_speler.equals("oranje")){
            kleur_Speler = R.drawable.oog_4;
            kleur_Speler_uit = R.drawable.oog_4_uit;
        }else{
            kleur_Speler = R.drawable.oog_5;
            kleur_Speler_uit = R.drawable.oog_5_uit;
        }

        if (kleur_tegenstander.equals("groen")){
            kleur_Tegenstander = R.drawable.oog_1;
            kleur_Tegenstander_uit = R.drawable.oog_1_uit;
        }else if (kleur_tegenstander.equals("blauw")){
            kleur_Tegenstander = R.drawable.oog_2;
            kleur_Tegenstander_uit = R.drawable.oog_2_uit;
        }else if (kleur_tegenstander.equals("paars")){
            kleur_Tegenstander = R.drawable.oog_3;
            kleur_Tegenstander_uit = R.drawable.oog_3_uit;
        }else if (kleur_tegenstander.equals("oranje")){
            kleur_Tegenstander = R.drawable.oog_4;
            kleur_Tegenstander_uit = R.drawable.oog_4_uit;
        }else{
            kleur_Tegenstander = R.drawable.oog_5;
            kleur_Tegenstander_uit = R.drawable.oog_5_uit;
        }

        ImageView[] imageViewPuntenSpeler = new ImageView[3];
        imageViewPuntenSpeler[0] = imageView_punten_speler_1;
        imageViewPuntenSpeler[1] = imageView_punten_speler_2;
        imageViewPuntenSpeler[2] = imageView_punten_speler_3;

        ImageView[] imageViewPuntenTegenstander = new ImageView[3];
        imageViewPuntenTegenstander[0] = imageView_punten_tegenstander_1;
        imageViewPuntenTegenstander[1] = imageView_punten_tegenstander_2;
        imageViewPuntenTegenstander[2] = imageView_punten_tegenstander_3;

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
        while (tellen < score){
            imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander_uit);
            tellen++;
        }
        tellen = 0;
        while (tellen < score_Tegenstander){
            imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander);
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

        if (beurt.equals("1") || beurt.equals("2")){
            imageView_beurt_speler.setImageResource(R.drawable.beurt_aan);
            imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_uit);
            textView_naam_speler.setTextColor(Color.parseColor("#ffffff"));
            textView_naam_tegenstander.setTextColor(Color.parseColor("#555555"));
        }else{
            imageView_beurt_speler.setImageResource(R.drawable.beurt_uit);
            imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_aan);
            textView_naam_speler.setTextColor(Color.parseColor("#555555"));
            textView_naam_tegenstander.setTextColor(Color.parseColor("#ffffff"));
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        textView_naam_speler.setTypeface(typeface);
        textView_naam_tegenstander.setTypeface(typeface);
        textView_chat.setTypeface(typeface_bold);

        File opslag = Environment.getExternalStorageDirectory();
        String tegenstander_foto = naam_tegenstander.replace(" ", "%20");
        File bestand_profielfoto = new File(opslag,"/Spotashot/profielfotos/"+tegenstander_foto+"_"+profielfoto+".jpg");
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

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            Toast.makeText(this, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else{
            new laad_plaatje(foto).execute(getString(R.string.website_url) + "/uploads/fotos/" + id + "/" + onderwerp_nummer + ".jpg");
        }

        float dpi  = getResources().getDisplayMetrics().density;
        float breedte_vakje = dpi * 120;

        if (tijd == null){
            tijd = "0:0:0";
        }

        StringTokenizer tijd_token = new StringTokenizer(tijd, ":");
        tijd_uren = Integer.parseInt(tijd_token.nextToken());
        tijd_minuten = Integer.parseInt(tijd_token.nextToken());
        tijd_seconden = Integer.parseInt(tijd_token.nextToken());

        float vakje1_aan = breedte_vakje / 24 * (tijd_uren +1);
        float vakje1_uit = breedte_vakje / 24 * (25-tijd_uren);

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
        builder.setTitle(getString(R.string.foto_beoordelen_melding_1_titel))
                .setMessage(getString(R.string.foto_beoordelen_melding_1_tekst));
        builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                beoordelen();
            }
        });
        builder.setNegativeButton(getString(R.string.nee), null);
        builder.show();
    }

    public void beoordelen(){
        keuze = "fout";
        ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.foto_beoordelen_melding_2), getString(R.string.een_ogenblik_geduld), true, false);
        new foto_beoordelen().execute();
    }

    public void goed (View view){
        keuze = "goed";
        ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.foto_beoordelen_melding_3), getString(R.string.een_ogenblik_geduld), true, false);
        new foto_beoordelen().execute();
    }

    private class foto_beoordelen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_speler_beoordelen = naam_speler.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/foto_beoordelen.php?naam="+naam_speler_beoordelen+"&id="+id+"&nummer="+nummer+"&keuze="+keuze);
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
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else{
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

    public void profiel_laden(View view) {

    }

}