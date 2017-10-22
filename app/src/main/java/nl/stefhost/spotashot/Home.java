package nl.stefhost.spotashot;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

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

    public String resultaat = "";
    public String resultaat1 = "";
    public String resultaat2 = "";
    public String laatste_spel;

    public String naam;
    public String naam_tegenstander;
    public String kleur_speler;
    public String kleur_tegenstander;
    public String plaatje;
    public String muntjes;
    public String thema;
    public String themas;

    public String thema1a;
    public String thema1b;
    public String thema2a;
    public String thema2b;
    public String thema3a;
    public String thema3b;
    public String thema4a;
    public String thema4b;
    public String thema5a;
    public String thema5b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8094643563031669~8359776034");

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam = sharedPreferences.getString("naam", "");
        naam = naam.replace("%2520", " ");

        if (naam.equals("")) {
            Intent intent = new Intent(this, Welkom.class);
            startActivity(intent);
            finish();
        } else {

            String help_versie = sharedPreferences.getString("help_versie", "");
            if (!help_versie.equals(getString(R.string.help_versie))) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("help_versie", getString(R.string.help_versie));
                editor.apply();
                Intent intent = new Intent(this, Spelregels.class);
                startActivity(intent);
            }
        }

        SQLiteDatabase SQLiteDatabase = this.openOrCreateDatabase("Database", Context.MODE_PRIVATE, null);
        SQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS spellen (id INTEGER PRIMARY KEY, id2 text not null, tegenstander text not null, punten text not null, kleur_speler text not null, kleur_tegenstander text not null, score_speler text not null, score_tegenstander text not null, beoordelen_speler text not null, beoordelen_tegenstander text not null, chat text not null, profielfoto text not null, thema text not null, status text, datum text, speltype text, beurt text, taal text)");
        SQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS chat (id INTEGER PRIMARY KEY, nummer text, afzender text, datum text, bericht text)");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        TextView textView1 = (TextView) findViewById(R.id.textView2);
        TextView textView2 = (TextView) findViewById(R.id.textView3);
        TextView textView3 = (TextView) findViewById(R.id.textView4);
        TextView textView4 = (TextView) findViewById(R.id.textView5);
        TextView textView5 = (TextView) findViewById(R.id.beurt);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        //textView5.setTypeface(typeface);
    }

    protected void onResume() {
        super.onResume();

        //laatste spel weergeven
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        laatste_spel = sharedPreferences.getString("laatste_spel", "");
        String uitloggen = sharedPreferences.getString("uitloggen", "");
        String spel_homescherm = sharedPreferences.getString("spel_homescherm", "");

        if (uitloggen.equals("JA")) {
            finish();
        }

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView textView = (TextView) findViewById(R.id.textView);

        if (laatste_spel.equals("") || spel_homescherm.equals("UIT")) {
            imageView1.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }else{

            if (spel_homescherm.equals("") || spel_homescherm.equals("AAN")) {
                imageView1.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    imageView1.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    Toast.makeText(this.getApplicationContext(), getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
                } else {
                    new laatste_spel().execute();
                }
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("huidig_spel", "");
        editor.apply();

        plaatje = sharedPreferences.getString("plaatje", "");

        if (!plaatje.equals("")) {
            ImageView profiel = (ImageView) findViewById(R.id.profiel);
            profiel.setVisibility(View.INVISIBLE);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            new update().execute();
        }

    }

    private class laatste_spel extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            String naam_laatste = naam.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas) + "/laatste_spel.php?nummer=" + laatste_spel + "&naam=" + naam_laatste + "&versie=1.04");
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
                resultaat1 = inputStream.toString();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try {
                    resultaat1 = bufferedReader.readLine();
                } catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            } else {
                resultaat1 = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!resultaat1.equals("ERROR")) {
                laatste_spel_klaar();
            }
        }

    }


    public void laatste_spel_klaar() {

        if (resultaat1.equals("WEG")) {
            ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
            RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout0);
            TextView textView = (TextView) findViewById(R.id.textView);

            imageView1.setVisibility(View.VISIBLE);
            relativeLayout1.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("laatste_spel", "");
            editor.apply();
        } else {

            StringTokenizer tokens = new StringTokenizer(resultaat1, "|");

            naam_tegenstander = tokens.nextToken();
            String punten = tokens.nextToken();
            kleur_speler = tokens.nextToken();
            kleur_tegenstander = tokens.nextToken();
            String score_speler = tokens.nextToken();
            String score_tegenstander = tokens.nextToken();
            String beoordelen_speler = tokens.nextToken();
            String beoordelen_tegenstander = tokens.nextToken();
            String chat = tokens.nextToken();
            String profielfoto = tokens.nextToken();
            String beurt = tokens.nextToken();

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
            TextView textView_beurt = (TextView) findViewById(R.id.beurt);

            int kleur_Speler = 0;
            int kleur_Speler_uit = 0;
            int kleur_Tegenstander = 0;
            int kleur_Tegenstander_uit = 0;

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

            while (tellen < score) {
                imageViewPuntenSpeler[tellen].setImageResource(kleur_Speler_uit);
                tellen++;
            }
            tellen = 0;
            while (tellen < score_Speler) {
                imageViewPuntenSpeler[tellen].setImageResource(kleur_Speler);
                tellen++;
            }

            tellen = 0;
            while (tellen < score) {
                imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander_uit);
                tellen++;
            }
            tellen = 0;
            while (tellen < score_Tegenstander) {
                imageViewPuntenTegenstander[tellen].setImageResource(kleur_Tegenstander);
                tellen++;
            }

            Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animatie_oneindig);

            if (beoordelen_speler != null && Integer.parseInt(beoordelen_speler) > 0) {
                imageView_beoordelen_speler.setVisibility(View.VISIBLE);
                imageView_beoordelen_speler.setImageResource(R.drawable.beoordelen);
                imageView_beoordelen_speler.startAnimation(myAnimation);
            } else {
                imageView_beoordelen_speler.setVisibility(View.INVISIBLE);
                imageView_beoordelen_speler.clearAnimation();
            }
            if (beoordelen_tegenstander != null && Integer.parseInt(beoordelen_tegenstander) > 0) {
                imageView_beoordelen_tegenstander.setVisibility(View.VISIBLE);
                imageView_beoordelen_tegenstander.setImageResource(R.drawable.beoordelen);
            } else {
                imageView_beoordelen_tegenstander.setVisibility(View.INVISIBLE);
                imageView_beoordelen_tegenstander.clearAnimation();
            }

            textView_naam_speler.setText(naam);
            textView_naam_tegenstander.setText(naam_tegenstander);
            textView_chat.setText(chat);

            if (Integer.parseInt(chat) > 0){
                textView_chat.startAnimation(myAnimation);
            }else{
                textView_chat.clearAnimation();
            }

            TextView textView = (TextView) findViewById(R.id.textView);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
            Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
            textView_naam_speler.setTypeface(typeface);
            textView_naam_tegenstander.setTypeface(typeface);
            textView_chat.setTypeface(typeface_bold);
            textView.setTypeface(typeface);

            File opslag = Environment.getExternalStorageDirectory();
            String tegenstander_foto = naam_tegenstander.replace(" ", "%20");
            File bestand_profielfoto = new File(opslag, "/Spotashot/profielfotos/" + tegenstander_foto + "_" + profielfoto + ".jpg");
            if (bestand_profielfoto.exists()) {
                final Bitmap myBitmap = BitmapFactory.decodeFile(bestand_profielfoto.getAbsolutePath());
                imageView_foto_tegenstander.post(new Runnable() {
                    public void run() {
                        imageView_foto_tegenstander.setImageBitmap(myBitmap);
                    }
                });
            }

            //beurt weergeven
            if (beurt.equals("1") || beurt.equals("2")) {
                textView_beurt.setBackgroundColor(Color.parseColor("#3b0dcf3a"));
                textView_beurt.setText(getString(R.string.spellen_7));
                imageView_beurt_speler.setImageResource(R.drawable.beurt_aan);
                imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_uit);
                textView_naam_speler.setTextColor(Color.parseColor("#ffffff"));
                textView_naam_tegenstander.setTextColor(Color.parseColor("#555555"));
            }else{
                textView_beurt.setBackgroundColor(Color.parseColor("#3bed1c24"));
                textView_beurt.setText(getString(R.string.spellen_8));
                imageView_beurt_speler.setImageResource(R.drawable.beurt_uit);
                imageView_beurt_tegenstander.setImageResource(R.drawable.beurt_aan);
                textView_naam_speler.setTextColor(Color.parseColor("#555555"));
                textView_naam_tegenstander.setTextColor(Color.parseColor("#ffffff"));
            }
        }

    }

    public void start_spel(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.spelbalk);
        Intent intent = new Intent(this, Spel.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", laatste_spel);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, relativeLayout, "Spel");
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void nieuw_spel(View view) {
        TextView textView = (TextView) findViewById(R.id.textView2);
        Intent intent = new Intent(Home.this, Nieuw_spel.class);

        Bundle bundle = new Bundle();
        bundle.putString("muntjes", muntjes);
        bundle.putString("themas", themas);
        bundle.putString("thema1a", thema1a);
        bundle.putString("thema1b", thema1b);
        bundle.putString("thema2a", thema2a);
        bundle.putString("thema2b", thema2b);
        bundle.putString("thema3a", thema3a);
        bundle.putString("thema3b", thema3b);
        bundle.putString("thema4a", thema4a);
        bundle.putString("thema4b", thema4b);
        bundle.putString("thema5a", thema5a);
        bundle.putString("thema5b", thema5b);
        intent.putExtras(bundle);

        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "newGame");
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void spellen(View view) {
        TextView textView = (TextView) findViewById(R.id.textView3);
        Intent intent = new Intent(Home.this, Spellen.class);

        Bundle bundle = new Bundle();
        bundle.putString("muntjes", muntjes);
        bundle.putString("themas", themas);
        bundle.putString("thema1a", thema1a);
        bundle.putString("thema1b", thema1b);
        bundle.putString("thema2a", thema2a);
        bundle.putString("thema2b", thema2b);
        bundle.putString("thema3a", thema3a);
        bundle.putString("thema3b", thema3b);
        bundle.putString("thema4a", thema4a);
        bundle.putString("thema4b", thema4b);
        bundle.putString("thema5a", thema5a);
        bundle.putString("thema5b", thema5b);
        intent.putExtras(bundle);

        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "myGames");
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void profiel(View view) {
        TextView textView = (TextView) findViewById(R.id.textView4);
        ImageView imageView = (ImageView) findViewById(R.id.profiel);
        Intent intent = new Intent(Home.this, Profiel.class);
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions activityOptions;
            if (!plaatje.equals("")) {
                activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "myProfile_1");
            } else {
                activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create((View) textView, "myProfile_1"), Pair.create((View) imageView, "myProfile_2"));
            }
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void spelregels(View view) {
        TextView textView = (TextView) findViewById(R.id.textView5);
        Intent intent = new Intent(Home.this, Spelregels.class);
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, textView, "Spelregels");
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void instellingen(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView6);
        Intent intent = new Intent(Home.this, Instellingen.class);
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this, imageView, "Instellingen");
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private class update extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            resultaat2 = "ERROR";
            String naam_update = naam.replace(" ", "%20");

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/update.php?naam="+naam_update);
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
                resultaat2 = inputStream.toString();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try {
                    resultaat2 = bufferedReader.readLine();
                } catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            } else {
                resultaat2 = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!resultaat2.equals("ERROR")) {
                update_klaar();
            }
        }

    }

    private void update_klaar() {

        StringTokenizer tokens = new StringTokenizer(resultaat2, "|");

        String type = tokens.nextToken();
        String titel = tokens.nextToken();
        String tekst = tokens.nextToken();
        String versie = tokens.nextToken();
        muntjes = tokens.nextToken();
        themas = tokens.nextToken();

        tekst = tekst.replace("[enter]", "\n");

        if (type.equals("SERVER")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setTitle(titel)
                    .setMessage(tekst)
                    .setCancelable(false);
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.show();

        }else{

            String versie_app;

            try {
                PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
                versie_app = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException("Could not get package name: " + e);
            }

            StringTokenizer stringTokenizer = new StringTokenizer(versie_app, "-");
            String nummer = stringTokenizer.nextToken();
            String betaald = stringTokenizer.nextToken();

            if (!versie.equals(nummer)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Update beschikbaar")
                        .setMessage("Er is een update beschikbaar")
                        .setCancelable(false);
                builder.setPositiveButton("DOWNLOAD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        if (getString(R.string.reclame).equals("ja")) {
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=nl.stefhost.spotashot.gratis"));
                        } else {
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=nl.stefhost.spotashot.betaald"));
                        }
                        startActivity(intent);
                    }
                });
                // Tijdelijk uitschakelen tijdens testen
                //builder.show();
            }
        }

        // Themas opslaan
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("themas", themas);
        editor.apply();

        thema1a = tokens.nextToken();
        thema1b = tokens.nextToken();
        thema2a = tokens.nextToken();
        thema2b = tokens.nextToken();
        thema3a = tokens.nextToken();
        thema3b = tokens.nextToken();
        thema4a = tokens.nextToken();
        thema4b = tokens.nextToken();
        thema5a = tokens.nextToken();
        thema5b = tokens.nextToken();

        // Muntjes updaten
        TextView textView1 = (TextView) findViewById(R.id.textViewMuntjes);
        textView1.setText(muntjes);

        // Themas updaten
        TextView textView2 = (TextView) findViewById(R.id.TextView1);
        textView2.setText(muntjes);

        TextView thema2 = (TextView) findViewById(R.id.thema2);
        TextView thema3 = (TextView) findViewById(R.id.thema3);
        TextView thema4 = (TextView) findViewById(R.id.thema4);
        TextView thema5 = (TextView) findViewById(R.id.thema5);

        TextView muntjes1 = (TextView) findViewById(R.id.muntjes1);
        TextView muntjes2 = (TextView) findViewById(R.id.muntjes2);
        TextView muntjes3 = (TextView) findViewById(R.id.muntjes3);
        TextView muntjes4 = (TextView) findViewById(R.id.muntjes4);
        TextView muntjes5 = (TextView) findViewById(R.id.muntjes5);

        TextView prijs1 = (TextView) findViewById(R.id.prijs1);
        TextView prijs2 = (TextView) findViewById(R.id.prijs2);
        TextView prijs3 = (TextView) findViewById(R.id.prijs3);
        TextView prijs4 = (TextView) findViewById(R.id.prijs4);
        TextView prijs5 = (TextView) findViewById(R.id.prijs5);

        Drawable drawable_leeg = this.getResources().getDrawable(R.drawable.leeg);
        Drawable drawable_ster_grijs = this.getResources().getDrawable(R.drawable.ster_grijs);
        Drawable drawable_ster_geel = this.getResources().getDrawable(R.drawable.ster_geel);

        int aantal_muntjes = Integer.parseInt(muntjes);

        muntjes1.setText(thema1a+" / "+thema1b+" "+getString(R.string.onderwerpen));

        if (thema1a.equals(thema1b)){
            prijs1.setBackground(drawable_ster_geel);
        }

        if (themas.contains("2")) {
            thema2.setCompoundDrawables(drawable_leeg, null, null, null);
            thema2.setCompoundDrawablePadding(90);
            thema2.setTextColor(Color.parseColor("#ffffff"));
            muntjes2.setCompoundDrawables(drawable_leeg, null, null, null);
            muntjes2.setTextColor(Color.parseColor("#ffffff"));
            muntjes2.setText(thema2a+" / "+thema2b+" "+getString(R.string.onderwerpen));
            prijs2.setText("");
            if (thema2a.equals(thema2b)){
                prijs2.setBackground(drawable_ster_geel);
            }else{
                prijs2.setBackground(drawable_ster_grijs);
            }
        }else if (aantal_muntjes > 999) {
            thema2.setTextColor(Color.parseColor("#ffffff"));
            muntjes2.setTextColor(Color.parseColor("#ffffff"));
        }else{
            thema2.setTextColor(Color.parseColor("#868386"));
            muntjes2.setTextColor(Color.parseColor("#868386"));
        }

        if (themas.contains("3")) {
            thema3.setCompoundDrawables(drawable_leeg, null, null, null);
            thema3.setCompoundDrawablePadding(90);
            thema3.setTextColor(Color.parseColor("#ffffff"));
            muntjes3.setCompoundDrawables(drawable_leeg, null, null, null);
            muntjes3.setTextColor(Color.parseColor("#ffffff"));
            muntjes3.setText(thema3a+" / "+thema3b+" "+getString(R.string.onderwerpen));
            prijs3.setText("");
            if (thema3a.equals(thema3b)){
                prijs3.setBackground(drawable_ster_geel);
            }else{
                prijs3.setBackground(drawable_ster_grijs);
            }
        }else if (aantal_muntjes > 1999) {
            thema3.setTextColor(Color.parseColor("#ffffff"));
            muntjes3.setTextColor(Color.parseColor("#ffffff"));
        }else{
            thema3.setTextColor(Color.parseColor("#868386"));
            muntjes3.setTextColor(Color.parseColor("#868386"));
        }

        if (themas.contains("4")) {
            thema4.setCompoundDrawables(drawable_leeg, null, null, null);
            thema4.setCompoundDrawablePadding(90);
            thema4.setTextColor(Color.parseColor("#ffffff"));
            muntjes4.setCompoundDrawables(drawable_leeg, null, null, null);
            muntjes4.setTextColor(Color.parseColor("#ffffff"));
            muntjes4.setText(thema4a+" / "+thema4b+" "+getString(R.string.onderwerpen));
            prijs4.setText("");
            if (thema4a.equals(thema4b)){
                prijs4.setBackground(drawable_ster_geel);
            }else{
                prijs4.setBackground(drawable_ster_grijs);
            }
        }else if (aantal_muntjes > 1999) {
            thema4.setTextColor(Color.parseColor("#ffffff"));
            muntjes4.setTextColor(Color.parseColor("#ffffff"));
        }else{
            thema4.setTextColor(Color.parseColor("#868386"));
            muntjes4.setTextColor(Color.parseColor("#868386"));
        }

        if (themas.contains("5")) {
            thema5.setCompoundDrawables(drawable_leeg, null, null, null);
            thema5.setCompoundDrawablePadding(90);
            thema5.setTextColor(Color.parseColor("#ffffff"));
            muntjes5.setCompoundDrawables(drawable_leeg, null, null, null);
            muntjes5.setTextColor(Color.parseColor("#ffffff"));
            muntjes5.setText(thema5a+" / "+thema5b+" "+getString(R.string.onderwerpen));
            prijs5.setText("");
            if (thema5a.equals(thema5b)){
                prijs5.setBackground(drawable_ster_geel);
            }else{
                prijs5.setBackground(drawable_ster_grijs);
            }
        }else if (aantal_muntjes > 2499) {
            thema5.setTextColor(Color.parseColor("#ffffff"));
            muntjes5.setTextColor(Color.parseColor("#ffffff"));
        }else{
            thema5.setTextColor(Color.parseColor("#868386"));
            muntjes5.setTextColor(Color.parseColor("#868386"));
        }

        String tip7 = sharedPreferences.getString("tip_7", "");
        if ((!tip7.equals("JA")) && Integer.parseInt(muntjes) > 0){
            editor.putString("tip_7", "JA");
            editor.apply();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_tips_nieuw, null);
            builder.setView(view);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(getString(R.string.tip_7));
            builder.setPositiveButton(getString(R.string.ok), null);
            builder.setCancelable(false);
            builder.show();
        }

        // Muntjes popup
        String muntjes_dag = tokens.nextToken();
        if (muntjes_dag.equals("ja")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_tips_logo, null);
            builder.setView(view);
            TextView textView = (TextView) view.findViewById(R.id.textView);

            String string = getString(R.string.popup_0)+"\n\n[1] +50";
            SpannableString spannableString = new SpannableString(string);
            Drawable drawable = getResources().getDrawable(R.drawable.munt_klein);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);

            int plaats = string.indexOf("[1]");
            spannableString.setSpan(imageSpan, plaats, plaats+3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);

            builder.setPositiveButton(getString(R.string.ok), null);
            builder.setCancelable(false);
            builder.show();
        }
    }

    public void chat(View view) {
        Intent intent = new Intent(Home.this, Chat.class);
        intent.putExtra("nummer", laatste_spel);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    boolean themas_zichtbaar = false;

    public void themas_aan(View view) {
        themas_zichtbaar = true;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.themas);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    public void themas_uit(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.themas);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(View.GONE);
        }
        themas_zichtbaar = false;
    }

    @Override
    public void onBackPressed() {
        if (themas_zichtbaar) {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.themas);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
            themas_zichtbaar = false;
        } else {
            super.onBackPressed();
        }
    }

    public void kopen_muntjes(View view) {
        thema = view.getTag().toString();
        int kosten = 0;

        if (thema.equals("2")){
            kosten = 1000;
        }else if (thema.equals("3")){
            kosten = 2000;
        }else if(thema.equals("4")){
            kosten = 2000;
        }else if(thema.equals("5")){
            kosten = 2500;
        }
        int aantal_muntjes = Integer.parseInt(muntjes);

        if (kosten > aantal_muntjes){
            Log.d("SAS", "te duur!");
        }else{
            Log.d("SAS", "kopen maar!");
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage(getString(R.string.home_9)+" "+kosten+" "+getString(R.string.home_10));
            builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    new thema_kopen().execute();
                }
            });
            builder.setNegativeButton(getString(R.string.nee), null);
            builder.show();
        }
    }

    private class thema_kopen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String naam_update = naam.replace(" ", "%20");

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/thema_kopen.php?naam="+naam_update+"&thema="+thema);
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
                new update().execute();
            }
        }

    }

    public void kopen_geld(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.home_8), Toast.LENGTH_LONG);
        toast.show();
    }

    public void kies_thema(View view) {

    }

    public void profiel_laden(View view) {

    }

    public static void popup_test(final Activity activity, String keuze1, String keuze2){
        View view;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (keuze1.equals("popup_10")){
            view = activity.getLayoutInflater().inflate(R.layout.dialog_tips_nieuw_checkbox, null);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Log.d("SAS", "checkbox staat aan");
                        SharedPreferences sharedPreferences = activity.getSharedPreferences("opties", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("popup_10", "JA");
                        editor.apply();
                    }else{
                        Log.d("SAS", "checkbox staat uit");
                    }
                }
            });
        }else{
            view = activity.getLayoutInflater().inflate(R.layout.dialog_tips_nieuw, null);
        }
        builder.setView(view);

        String tekst = activity.getResources().getString(activity.getResources().getIdentifier(keuze1, "string", activity.getPackageName()));
        Drawable icoontje = activity.getResources().getDrawable(activity.getResources().getIdentifier(keuze2, "drawable", activity.getPackageName()));

        SpannableString spannableString = new SpannableString("I\n\n"+tekst);
        icoontje.setBounds(0, 0, icoontje.getIntrinsicWidth(), icoontje.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(icoontje, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(spannableString);

        builder.setPositiveButton(activity.getString(R.string.ok), null);
        builder.setCancelable(false);
        builder.show();
    }
}