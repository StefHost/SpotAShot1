package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import nl.stefhost.viewfinder.functies.download;
import nl.stefhost.viewfinder.functies.laad_plaatje;

public class Nieuw_spel extends AppCompatActivity {

    public Context context;
    public android.app.ProgressDialog ProgressDialog;

    int punten = 0;
    String speltype = "";
    String kleur = "";

    public String resultaat;
    public String naam;
    public String tegenstander;

    public ArrayList<String> arrayList;
    public ArrayList<String> arrayList_vrienden;
    public String[] stringArray;

    public boolean start = false;
    public boolean vriend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        naam = sharedPreferences.getString("naam", null);

        setContentView(R.layout.activity_nieuw_spel);
        context = Nieuw_spel.this;
        arrayList = new ArrayList<>();
        arrayList_vrienden = new ArrayList<>();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        TextView textViewBoven = (TextView) findViewById(R.id.textview_boven);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView textViewOnder = (TextView) findViewById(R.id.textview_onder);
        EditText editText1 = (EditText) findViewById(R.id.editText1);

        textViewBoven.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textView5.setTypeface(typeface);
        textViewOnder.setTypeface(typeface);
        editText1.setTypeface(typeface);
    }

    public void punten(View view) {

        arrayList = new ArrayList<>();
        arrayList.add("3");
        arrayList.add("5");
        arrayList.add("7");

        stringArray = arrayList.toArray(new String[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Points for win")
                .setItems(stringArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String gekozen = stringArray[which];
                        punten = Integer.parseInt(gekozen);

                        ImageView imageViewPunten1 = (ImageView) findViewById(R.id.imageViewPunten1);
                        ImageView imageViewPunten2 = (ImageView) findViewById(R.id.imageViewPunten2);
                        ImageView imageViewPunten3 = (ImageView) findViewById(R.id.imageViewPunten3);
                        ImageView imageViewPunten4 = (ImageView) findViewById(R.id.imageViewPunten4);
                        ImageView imageViewPunten5 = (ImageView) findViewById(R.id.imageViewPunten5);
                        ImageView imageViewPunten6 = (ImageView) findViewById(R.id.imageViewPunten6);
                        ImageView imageViewPunten7 = (ImageView) findViewById(R.id.imageViewPunten7);
                        imageViewPunten1.setImageResource(R.drawable.kleur_uit);

                        if (gekozen.equals("3")){
                            imageViewPunten1.setVisibility(View.VISIBLE);
                            imageViewPunten2.setVisibility(View.VISIBLE);
                            imageViewPunten3.setVisibility(View.VISIBLE);
                            imageViewPunten4.setVisibility(View.GONE);
                            imageViewPunten5.setVisibility(View.GONE);
                            imageViewPunten6.setVisibility(View.GONE);
                            imageViewPunten7.setVisibility(View.GONE);
                        }else if(gekozen.equals("5")){
                            imageViewPunten1.setVisibility(View.VISIBLE);
                            imageViewPunten2.setVisibility(View.VISIBLE);
                            imageViewPunten3.setVisibility(View.VISIBLE);
                            imageViewPunten4.setVisibility(View.VISIBLE);
                            imageViewPunten5.setVisibility(View.VISIBLE);
                            imageViewPunten6.setVisibility(View.GONE);
                            imageViewPunten7.setVisibility(View.GONE);
                        }else{
                            imageViewPunten1.setVisibility(View.VISIBLE);
                            imageViewPunten2.setVisibility(View.VISIBLE);
                            imageViewPunten3.setVisibility(View.VISIBLE);
                            imageViewPunten4.setVisibility(View.VISIBLE);
                            imageViewPunten5.setVisibility(View.VISIBLE);
                            imageViewPunten6.setVisibility(View.VISIBLE);
                            imageViewPunten7.setVisibility(View.VISIBLE);
                        }

                    }
                });
        builder.show();

    }

    public void kleur(View view) {

        arrayList = new ArrayList<>();
        arrayList.add("Blue");
        arrayList.add("Green");
        arrayList.add("Orange");
        arrayList.add("Purple");
        arrayList.add("Red");

        stringArray = arrayList.toArray(new String[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Play Color")
                .setItems(stringArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String gekozen = stringArray[which];
                        TextView textView = (TextView) findViewById(R.id.textView2);
                        if (gekozen.equals("Green")){
                            kleur = "groen";
                            textView.setText("green");
                            textView.setTextColor(Color.parseColor("#197b30"));
                        }else if (gekozen.equals("Blue")){
                            kleur = "blauw";
                            textView.setText("blue");
                            textView.setTextColor(Color.parseColor("#0000ff"));
                        }else if (gekozen.equals("Purple")){
                            kleur = "paars";
                            textView.setText("purple");
                            textView.setTextColor(Color.parseColor("#92278f"));
                        }else if (gekozen.equals("Orange")){
                            kleur = "oranje";
                            textView.setText("orange");
                            textView.setTextColor(Color.parseColor("#f26522"));
                        }else{
                            kleur = "rood";
                            textView.setText("red");
                            textView.setTextColor(Color.parseColor("#ED1C24"));
                        }
                    }
                });
        builder.show();

    }

    public void speltype(View view) {

        arrayList = new ArrayList<>();
        arrayList.add("Binnen");
        arrayList.add("Buiten");
        arrayList.add("Natuur");
        arrayList.add("Random");
        arrayList.add("Stedelijk");

        stringArray = arrayList.toArray(new String[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Topic")
                .setItems(stringArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String gekozen = stringArray[which];
                        TextView textView = (TextView) findViewById(R.id.textView3);
                        if (gekozen.equals("Binnen")) {
                            speltype = "binnen";
                            textView.setText("binnen");
                        }else if(gekozen.equals("Buiten")) {
                            speltype = "buiten";
                            textView.setText("buiten");
                        }else if(gekozen.equals("Natuur")) {
                            speltype = "natuur";
                            textView.setText("natuur");
                        }else if(gekozen.equals("Random")) {
                            speltype = "random";
                            textView.setText("random");
                        }else{
                            speltype = "stedelijk";
                            textView.setText("stedelijk");
                        }
                    }
                });
        builder.show();

    }

    public void vriend(View view) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", Context.MODE_PRIVATE);
        Set<String> stringSet1 = sharedPreferences.getStringSet("adresboek", new HashSet<String>());

        arrayList_vrienden = new ArrayList<>();
        for (String string : stringSet1) {
            string = string.replace("[enter]", "\n");
            arrayList_vrienden.add(string);
        }

        if (!vriend) {

            Collections.sort(arrayList_vrienden, String.CASE_INSENSITIVE_ORDER);
            stringArray = arrayList_vrienden.toArray(new String[arrayList_vrienden.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Friends")
                    .setItems(stringArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            tegenstander = stringArray[which];

                            vriend = true;
                            ProgressDialog = android.app.ProgressDialog.show(context, "Searching for opponent", "One moment please..", true, false);
                            new tegenstander_zoeken().execute();

                        }
                    });
            builder.show();

        }else{

            TextView textView = (TextView) findViewById(R.id.textView4);
            String naam = textView.getText().toString();
            arrayList_vrienden.add(naam);
            ImageView imageView =  (ImageView) findViewById(R.id.imageView8);
            imageView.setImageResource(R.drawable.pijltje_omlaag);

            vriend = false;

            Toast toast = Toast.makeText(this.getApplicationContext(), naam + " is added to your friend list!", Toast.LENGTH_SHORT);
            toast.show();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("adresboek", new HashSet<>(arrayList_vrienden));
            editor.apply();

        }

    }

    public void zoeken(View view){

        EditText editText = (EditText) findViewById(R.id.editText1);

        tegenstander = editText.getText().toString();
        tegenstander = tegenstander.replace(" ", "%20");

        try {
            tegenstander = URLEncoder.encode(tegenstander, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (tegenstander.equals(naam)){
            _functions.melding("Wrong opponent", "You can't play against yourself.\nPlease try again.", context);
        }else if (tegenstander.matches("")) {
            _functions.melding("No username or email", "You didn't provide a username or email.\nPlease try again.", context);
        }else{
            InputMethodManager InputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            ProgressDialog = android.app.ProgressDialog.show(this, "Searching for opponent", "One moment please..", true, false);
            new tegenstander_zoeken().execute();
        }
    }

    private class tegenstander_zoeken extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/tegenstander_zoeken.php?naam="+naam+"&tegenstander="+tegenstander);
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
            _functions.melding("SERVER ERROR", "Probeer het later nog een keer.", context);
        }else if (resultaat.matches("USERNAME")){
            _functions.melding("Wrong username", "You provided a wrong username.\nPlease try again.", context);
        }else if (resultaat.matches("EMAIL")){
            _functions.melding("Wrong email", "You provided a wrong email adress.\nPlease try again.", context);
        }else{
            StringTokenizer tokens = new StringTokenizer(resultaat, "|");
            String naam = tokens.nextToken();
            String plaatje = tokens.nextToken();
            String profielfoto = tokens.nextToken();

            ImageView imageView1 = (ImageView) findViewById(R.id.imageView7);
            if (plaatje.equals("LEEG")){
                imageView1.setImageResource(R.drawable.profiel);
            }else{
                new download((ImageView)findViewById(R.id.imageView7)).execute(naam,profielfoto);
            }

            TextView textView1 = (TextView) findViewById(R.id.textView4);
            textView1.setText(naam);

            TextView textView2 = (TextView) findViewById(R.id.textview_onder);
            textView2.setTextColor(Color.parseColor("#ffffff"));

            if (!vriend) {
                ImageView imageView2 = (ImageView) findViewById(R.id.imageView8);
                imageView2.setImageResource(R.drawable.plusje);
                vriend = true;
            }else{
                vriend = false;
            }

            start = true;
        }

    }

    public void random_speler(View view){

        tegenstander = "RANDOM";
        ProgressDialog = android.app.ProgressDialog.show(this, "Searching for random opponent", "One moment please..", true, false);
        new tegenstander_zoeken().execute();

    }

    public void start(View view){

        if (start){

            if (punten == 0){
                _functions.melding("Geen puntenaantal", "Je hebt geen puntenaantal gekozen.\nProbeer het nog een keer.", context);
            }else if (kleur.equals("")){
                _functions.melding("Geen kleur", "Je hebt geen kleur gekozen.\nProbeer het nog een keer.", context);
            }else if (speltype.equals("")){
                _functions.melding("Geen speltype", "Je hebt geen speltype gekozen.\nProbeer het nog een keer.", context);
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, "Creating game", "One moment please..", true, false);
                new nieuw_spel().execute();
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

            try {
                url = new URL(getString(R.string.website_paginas)+"/nieuw_spel.php?naam="+naam+"&tegenstander="+tegenstander+"&punten="+punten+"&speltype="+speltype+"&kleur="+kleur);
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

        if (resultaat.matches("ERROR")) {
            _functions.melding("SERVER ERROR", "Probeer het later nog een keer.", context);
        }else{
            Toast toast = Toast.makeText(this.getApplicationContext(), "Game created!", Toast.LENGTH_SHORT);
            toast.show();
            super.onBackPressed();
        }

    }

}
