package nl.stefhost.spotashot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import nl.stefhost.spotashot.functies.download;

public class Nieuw_spel extends AppCompatActivity {

    public Context context;
    public android.app.ProgressDialog ProgressDialog;

    String thema = "";
    String kleur = "";

    public String resultaat;
    public String naam;
    public String tegenstander = "";
    public String themas;
    public String muntjes;

    public ArrayList<String> arrayList;
    public ArrayList<String> arrayList_vrienden;
    public String[] stringArray;

    public boolean start = false;

    public boolean zelfde_land = false;

    public String toestemming_profielfoto;
    private static final int WRITE_EXTERNAL_STORAGE = 0;

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam = sharedPreferences.getString("naam", "");
        naam = naam.replace(" ", "%20");
        themas = sharedPreferences.getString("themas", "");

        setContentView(R.layout.activity_nieuw_spel);
        context = Nieuw_spel.this;
        arrayList = new ArrayList<>();
        arrayList_vrienden = new ArrayList<>();

        Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        TextView textViewBoven = (TextView) findViewById(R.id.textview_boven);
        TextView textView_2 = (TextView) findViewById(R.id.textView_2);
        TextView textView_3 = (TextView) findViewById(R.id.textView_3);

        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView textViewOnder = (TextView) findViewById(R.id.textview_onder);
        EditText editText1 = (EditText) findViewById(R.id.editText1);

        textViewBoven.setTypeface(typeface_bold);
        textView_2.setTypeface(typeface_bold);
        textView_3.setTypeface(typeface_bold);
        textView4.setTypeface(typeface_bold);
        editText1.setTypeface(typeface_bold);
        textView5.setTypeface(typeface_bold);
        textViewOnder.setTypeface(typeface_bold);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            toestemming_profielfoto = "nee";
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
        }else{
            toestemming_profielfoto = "ja";
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && getIntent().getExtras().getString("kleur") != null){
            String kleur_gekozen = getIntent().getExtras().getString("kleur");
            String thema_gekozen = getIntent().getExtras().getString("thema");
            String tegenstander_gekozen = getIntent().getExtras().getString("tegenstander");

            // kleur
            if (kleur_gekozen.equals("blauw")){
                kleur = "blauw";
                textView_2.setText(getString(R.string.kleur_1));
                textView_2.setTextColor(Color.parseColor("#0000ff"));
            }else if (kleur_gekozen.equals("groen")){
                kleur = "groen";
                textView_2.setText(getString(R.string.kleur_2));
                textView_2.setTextColor(Color.parseColor("#197b30"));
            }else if (kleur_gekozen.equals("oranje")){
                kleur = "oranje";
                textView_2.setText(getString(R.string.kleur_3));
                textView_2.setTextColor(Color.parseColor("#f26522"));
            }else if (kleur_gekozen.equals("paars")){
                kleur = "paars";
                textView_2.setText(getString(R.string.kleur_4));
                textView_2.setTextColor(Color.parseColor("#92278f"));
            }else{
                kleur = "rood";
                textView_2.setText(getString(R.string.kleur_5));
                textView_2.setTextColor(Color.parseColor("#ED1C24"));
            }

            //thema
            if (thema_gekozen.equals("binnen")) {
                thema = "binnen";
                textView_3.setText(getString(R.string.onderwerp_1));
            }else if(thema_gekozen.equals("buiten")) {
                thema = "buiten";
                textView_3.setText(getString(R.string.onderwerp_2));
            }else if(thema_gekozen.equals("natuur")) {
                thema = "natuur";
                textView_3.setText(getString(R.string.onderwerp_3));
            }else if(thema_gekozen.equals("stedelijk")) {
                thema = "stedelijk";
                textView_3.setText(getString(R.string.onderwerp_4));
            }else{
                thema = "willekeurig";
                textView_3.setText(getString(R.string.onderwerp_5));
            }

            //tegenstander
            tegenstander = tegenstander_gekozen.replace(" ", "%20");
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.nieuw_spel_melding_1), getString(R.string.een_ogenblik_geduld), true, false);
                new tegenstander_zoeken().execute();
            }

        }

        muntjes = getIntent().getExtras().getString("muntjes");
        themas = getIntent().getExtras().getString("themas");
        themas_updaten();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        String gekocht = sharedPreferences.getString("gekocht", "");

        if (!gekocht.equals("ja")) {
            requestNewInterstitial();
        }

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    public void themas_updaten(){

        // Themas updaten

        String thema1a = getIntent().getExtras().getString("thema1a");
        String thema1b = getIntent().getExtras().getString("thema1b");
        String thema2a = getIntent().getExtras().getString("thema2a");
        String thema2b = getIntent().getExtras().getString("thema2b");
        String thema3a = getIntent().getExtras().getString("thema3a");
        String thema3b = getIntent().getExtras().getString("thema3b");
        String thema4a = getIntent().getExtras().getString("thema4a");
        String thema4b = getIntent().getExtras().getString("thema4b");
        String thema5a = getIntent().getExtras().getString("thema5a");
        String thema5b = getIntent().getExtras().getString("thema5b");

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
        }else if (aantal_muntjes > 499) {
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
        }else if (aantal_muntjes > 1499) {
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
        }else if (aantal_muntjes > 1499) {
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
        }else if (aantal_muntjes > 1999) {
            thema5.setTextColor(Color.parseColor("#ffffff"));
            muntjes5.setTextColor(Color.parseColor("#ffffff"));
        }else{
            thema5.setTextColor(Color.parseColor("#868386"));
            muntjes5.setTextColor(Color.parseColor("#868386"));
        }

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

    public void kleur(View view) {

        arrayList = new ArrayList<>();
        arrayList.add(getString(R.string.kleur_1));
        arrayList.add(getString(R.string.kleur_2));
        arrayList.add(getString(R.string.kleur_3));
        arrayList.add(getString(R.string.kleur_4));
        arrayList.add(getString(R.string.kleur_5));

        stringArray = arrayList.toArray(new String[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.nieuw_spel_opties_2))
                .setItems(stringArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String gekozen = stringArray[which];
                        TextView textView = (TextView) findViewById(R.id.textView_2);
                        if (gekozen.equals(getString(R.string.kleur_1))){
                            kleur = "blauw";
                            textView.setText(getString(R.string.kleur_1));
                            textView.setTextColor(Color.parseColor("#0000ff"));
                        }else if (gekozen.equals(getString(R.string.kleur_2))){
                            kleur = "groen";
                            textView.setText(getString(R.string.kleur_2));
                            textView.setTextColor(Color.parseColor("#197b30"));
                        }else if (gekozen.equals(getString(R.string.kleur_3))){
                            kleur = "oranje";
                            textView.setText(getString(R.string.kleur_3));
                            textView.setTextColor(Color.parseColor("#f26522"));
                        }else if (gekozen.equals(getString(R.string.kleur_4))){
                            kleur = "paars";
                            textView.setText(getString(R.string.kleur_4));
                            textView.setTextColor(Color.parseColor("#92278f"));
                        }else{
                            kleur = "rood";
                            textView.setText(getString(R.string.kleur_5));
                            textView.setTextColor(Color.parseColor("#ED1C24"));
                        }
                    }
                });
        builder.show();

    }

    public void thema(View view) {

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.themas);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(View.VISIBLE);
        }

    }

    public void kies_thema(View view){

        thema = view.getTag().toString();
        TextView textView = (TextView) findViewById(R.id.textView_3);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.themas);

        if (thema.equals("1") && themas.contains("1")) {
            thema = "binnen";
            textView.setText(getString(R.string.onderwerp_1));
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
        }else if(thema.equals("2") && themas.contains("2")) {
            thema = "buiten";
            textView.setText(getString(R.string.onderwerp_2));
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
        }else if(thema.equals("3") && themas.contains("3")) {
            thema = "natuur";
            textView.setText(getString(R.string.onderwerp_3));
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
        }else if(thema.equals("4") && themas.contains("4")) {
            thema = "stedelijk";
            textView.setText(getString(R.string.onderwerp_4));
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
        }else if(thema.equals("5") && themas.contains("5")) {
            thema = "willekeurig";
            textView.setText(getString(R.string.onderwerp_5));
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
        }

    }

    public void vriend(View view) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", Context.MODE_PRIVATE);
        Set<String> stringSet1 = sharedPreferences.getStringSet("adresboek", new HashSet<String>());

        arrayList_vrienden = new ArrayList<>();
        for (String string : stringSet1) {
            string = string.replace("[enter]", "\n");
            string = string.replace("%20", " ");
            arrayList_vrienden.add(string);
        }

        Collections.sort(arrayList_vrienden, String.CASE_INSENSITIVE_ORDER);
        stringArray = arrayList_vrienden.toArray(new String[arrayList_vrienden.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.nieuw_spel_opties_4))
            .setItems(stringArray, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    tegenstander = stringArray[which];
                    tegenstander = tegenstander.replace(" ", "%20");

                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo == null){
                        Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
                    }else{
                        ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.nieuw_spel_melding_1), getString(R.string.een_ogenblik_geduld), true, false);
                        new tegenstander_zoeken().execute();
                    }

                }
            });
        builder.show();

    }

    public void zoeken(View view){

        EditText editText = (EditText) findViewById(R.id.editText1);

        tegenstander = editText.getText().toString();
        tegenstander = tegenstander.replace(" ", "%20");

        if (tegenstander.equals(naam)){
            _functions.melding(getString(R.string.nieuw_spel_foutmelding_1_titel), getString(R.string.nieuw_spel_foutmelding_1_tekst), context);
        }else if (tegenstander.matches("")) {
            _functions.melding(getString(R.string.nieuw_spel_foutmelding_2_titel), getString(R.string.nieuw_spel_foutmelding_2_tekst), context);
        }else{

            InputMethodManager InputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.nieuw_spel_melding_1), getString(R.string.een_ogenblik_geduld), true, false);
                new tegenstander_zoeken().execute();
            }

        }
    }

    private class tegenstander_zoeken extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/tegenstander_zoeken.php?naam="+naam+"&tegenstander="+tegenstander+"&thema="+thema+"&zelfde_land="+zelfde_land);
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
            tegenstander_zoeken_klaar();
        }

    }

    public void tegenstander_zoeken_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else if (resultaat.matches("USERNAME")){
            _functions.melding(getString(R.string.nieuw_spel_foutmelding_3_titel), getString(R.string.nieuw_spel_foutmelding_3_tekst), context);
        }else if (resultaat.matches("EMAIL")){
            //_functions.melding(getString(R.string.nieuw_spel_foutmelding_4_titel), getString(R.string.nieuw_spel_foutmelding_4_tekst), context);

            AlertDialog.Builder builder = new AlertDialog.Builder(Nieuw_spel.this);
            builder.setTitle(getString(R.string.nieuw_spel_melding_7_titel))
                    .setMessage(getString(R.string.nieuw_spel_melding_7_tekst));
            builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.nieuw_spel_melding_8), getString(R.string.een_ogenblik_geduld), true, false);
                    new email_versturen().execute();
                }
            });
            builder.setNegativeButton(getString(R.string.nee), null);
            builder.show();

        }else{
            StringTokenizer tokens = new StringTokenizer(resultaat, "|");
            String naam = tokens.nextToken();
            String plaatje = tokens.nextToken();
            String profielfoto = tokens.nextToken();
            String taal = tokens.nextToken();

            int vlag = 0;
            switch (taal){
                case "nl":
                    vlag = R.drawable.vlag_nl;
                    break;
                case "de":
                    vlag = R.drawable.vlag_de;
                    break;
                case "en":
                    vlag = R.drawable.vlag_en;
                    break;
            }

            if (!taal.equals("nl")){
                SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                String tip6 = sharedPreferences.getString("tip_6", "");
                if (!tip6.equals("JA")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tip_6", "JA");
                    editor.apply();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View view = getLayoutInflater().inflate(R.layout.dialog_tips_nieuw, null);
                    builder.setView(view);
                    TextView textView = (TextView) view.findViewById(R.id.textView);
                    textView.setText(getString(R.string.tip_6));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }

            tegenstander = naam;

            ImageView imageView1 = (ImageView) findViewById(R.id.imageView7);
            if (plaatje.equals("LEEG")){
                imageView1.setImageResource(R.drawable.profiel);
            }else{
                if (toestemming_profielfoto.equals("ja")) {
                    new download((ImageView) findViewById(R.id.imageView7)).execute(naam, profielfoto);
                }
            }

            TextView textView1 = (TextView) findViewById(R.id.textView4);
            textView1.setText(naam);
            textView1.setCompoundDrawablesWithIntrinsicBounds(vlag, 0, 0, 0);

            TextView textView2 = (TextView) findViewById(R.id.textview_onder);
            textView2.setTextColor(Color.parseColor("#ffffff"));

            start = true;
        }

    }


    private class email_versturen extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/email_versturen.php?naam="+naam+"&email="+tegenstander);
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
            email_versturen_klaar();
        }

    }

    public void email_versturen_klaar(){
        Toast toast = Toast.makeText(this.getApplicationContext(), getString(R.string.nieuw_spel_melding_9), Toast.LENGTH_SHORT);
        toast.show();
    }

    int aantal_random = 0;

    public void random_speler(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(Nieuw_spel.this);
        builder.setTitle(getString(R.string.nieuw_spel_melding_3_titel))
                .setMessage(getString(R.string.nieuw_spel_melding_3_tekst));
        builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tegenstander = "RANDOM";

                aantal_random++;

                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null){
                    Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
                }else{
                    if (aantal_random == 8){
                        Log.d("SAS", "FILMPJE");
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        aantal_random = 0;
                    }else{
                        ProgressDialog = android.app.ProgressDialog.show(context, getString(R.string.nieuw_spel_melding_4), getString(R.string.een_ogenblik_geduld), true, false);
                        new tegenstander_zoeken().execute();
                    }
                }

            }
        });
        builder.setNegativeButton(getString(R.string.nee), null);
        builder.show();

    }

    public void start(View view){

        if (start){

            if (kleur.equals("")){
                _functions.melding(getString(R.string.nieuw_spel_foutmelding_6_titel), getString(R.string.nieuw_spel_foutmelding_6_tekst), context);
            }else if (thema.equals("")){
                _functions.melding(getString(R.string.nieuw_spel_foutmelding_7_titel), getString(R.string.nieuw_spel_foutmelding_7_tekst), context);
            }else{
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null){
                    Toast.makeText(context, getString(R.string.foutmelding_internet), Toast.LENGTH_SHORT).show();
                }else{
                    ProgressDialog = android.app.ProgressDialog.show(this, getString(R.string.nieuw_spel_melding_5), getString(R.string.een_ogenblik_geduld), true, false);
                    new nieuw_spel().execute();
                }

            }
        }

    }

    private class nieuw_spel extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            resultaat = "";

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            tegenstander = tegenstander.replace(" ", "%20");

            try {
                url = new URL(getString(R.string.website_paginas)+"/nieuw_spel.php?naam="+naam+"&tegenstander="+tegenstander+"&thema="+thema+"&kleur="+kleur);
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
            nieuw_spel_klaar();
        }

    }

    public void nieuw_spel_klaar(){

        if (resultaat.equals("ERROR")){
            _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
        }else if (resultaat.equals("THEMA")){
            _functions.melding("", getString(R.string.nieuw_spel_foutmelding_9), context);
        }else{
            Toast toast = Toast.makeText(this.getApplicationContext(), getString(R.string.nieuw_spel_melding_6), Toast.LENGTH_SHORT);
            toast.show();
            super.onBackPressed();
        }

    }

    public void themas_uit(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.themas);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public void kopen_geld(View view) {
        if (themas.contains(thema)) {
            Log.d("SAS", "al gekocht");
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.home_8), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void kopen_muntjes(View view) {
        thema = view.getTag().toString();
        int kosten = 0;

        if (thema.equals("2")){
            kosten = 500;
        }else if (thema.equals("3")){
            kosten = 1500;
        }else if(thema.equals("4")){
            kosten = 1500;
        }else if(thema.equals("5")){
            kosten = 2000;
        }
        int aantal_muntjes = Integer.parseInt(muntjes);

        final int kosten_nieuw = kosten;

        if (themas.contains(thema)){
            Log.d("SAS", "al gekocht");
        }else if (kosten > aantal_muntjes){
            Log.d("SAS", "te duur!");
        }else{
            Log.d("SAS", "kopen maar!");
            AlertDialog.Builder builder = new AlertDialog.Builder(Nieuw_spel.this);
            builder.setMessage(getString(R.string.home_9)+" "+kosten+" "+getString(R.string.home_10));
            builder.setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    new thema_kopen().execute();
                    muntjes = String.valueOf(Integer.parseInt(muntjes) - kosten_nieuw);
                    themas = themas+""+thema;
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
                themas_updaten();
            }
        }

    }


    public void profiel_laden(View view) {
        if (!tegenstander.equals("")){
            new profiel_laden().execute();
        }
    }

    private class profiel_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String tegenstander_nieuw = tegenstander.replace(" ", "%20");

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

                new download((ImageView) findViewById(R.id.profielfoto)).execute(tegenstander, profielfoto);

                TextView textView1 = (TextView)findViewById(R.id.naam);
                TextView textView2 = (TextView)findViewById(R.id.muntjes);
                TextView textView3 = (TextView)findViewById(R.id.fotos);
                TextView textView4 = (TextView)findViewById(R.id.gewonnen);
                TextView textView5 = (TextView)findViewById(R.id.verloren);
                TextView textView6 = (TextView)findViewById(R.id.TextViewThemas1);
                TextView textView7 = (TextView)findViewById(R.id.TextViewThemas2);

                textView1.setText(tegenstander);
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
        TextView textView = (TextView) findViewById(R.id.textView4);
        String naam = textView.getText().toString();
        String naam_list = naam.replace(" ", "%20");

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

        Toast toast = Toast.makeText(this.getApplicationContext(), naam + " " + getString(R.string.nieuw_spel_melding_2), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void blokkeren(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Nieuw_spel.this);
        builder.setTitle(getString(R.string.spellen_melding_5_titel))
                .setMessage(getString(R.string.spellen_melding_5_tekst1)+" "+tegenstander+" "+getString(R.string.spellen_melding_5_tekst2));
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

        private void tegenstander_blokkeren_klaar(){

            if (resultaat.matches("ERROR")) {
                _functions.melding(getString(R.string.foutmelding_server_titel), getString(R.string.foutmelding_server_tekst), context);
            }else{
                Toast toast = Toast.makeText(context, getString(R.string.spellen_melding_10), Toast.LENGTH_SHORT);
                toast.show();
            }

        }

    }

    public void zelfde_land(View view){
        if (zelfde_land){
            zelfde_land = false;
        }else{
            zelfde_land = true;
        }
        Log.d("SAS", ""+zelfde_land);
    }

}
