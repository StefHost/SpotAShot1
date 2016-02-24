package nl.stefhost.viewfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Profiel extends AppCompatActivity {

    public int RESULT_LOAD_IMAGE;
    public Context context;
    public android.app.ProgressDialog ProgressDialog;
    public String resultaat;

    public String plaatje;
    public String naam;
    public String email;
    public String wachtwoord;

    public String plaatje_nieuw = "";
    public String naam_nieuw = "";
    public String email_nieuw = "";
    public String wachtwoord_nieuw;
    public String wachtwoord_herhalen;

    int serverResponseCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);
        context = Profiel.this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        plaatje = sharedPreferences.getString("plaatje", "");
        naam = sharedPreferences.getString("naam", "");
        email = sharedPreferences.getString("email", "");
        wachtwoord = sharedPreferences.getString("wachtwoord", "");

        try {
            wachtwoord = URLDecoder.decode(wachtwoord, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        int lengte = wachtwoord.length();
        wachtwoord = new String(new char[lengte]).replace("\0", "*");

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        editText1.setHint(naam);
        editText2.setHint(email);
        editText3.setHint(wachtwoord);
        editText4.setHint(wachtwoord);

        if (!plaatje.equals("")) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setImageBitmap(BitmapFactory.decodeFile(plaatje));
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        TextView textViewBoven = (TextView) findViewById(R.id.textview_boven);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textViewOnder = (TextView) findViewById(R.id.textview_onder);

        textViewBoven.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textViewOnder.setTypeface(typeface);
        editText1.setTypeface(typeface);
        editText2.setTypeface(typeface);
        editText3.setTypeface(typeface);
        editText4.setTypeface(typeface);

    }

    public void plaatje(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            plaatje_nieuw = cursor.getString(columnIndex);
            cursor.close();

            ProgressDialog = android.app.ProgressDialog.show(this, "Uploading", "One moment please..", true, false);

            new Thread(new Runnable() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {

                        }
                    });
                    uploadFile(plaatje_nieuw);
                }
            }).start();

            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setImageBitmap(BitmapFactory.decodeFile(plaatje_nieuw));
        }

    }

    public void opslaan(View view){

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        EditText editText4 = (EditText) findViewById(R.id.editText4);

        naam_nieuw = editText1.getText().toString();
        email_nieuw = editText2.getText().toString();
        wachtwoord_nieuw = editText3.getText().toString();
        wachtwoord_herhalen = editText4.getText().toString();

        naam_nieuw = naam_nieuw.replace(" ", "%20");
        email_nieuw = email_nieuw.replace(" ", "%20");
        wachtwoord_nieuw = wachtwoord_nieuw.replace(" ", "%20");
        wachtwoord_herhalen = wachtwoord_herhalen.replace(" ", "%20");
        plaatje_nieuw = plaatje_nieuw.replace(" ", "%20");

        try {
            naam_nieuw = URLEncoder.encode(naam_nieuw, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            wachtwoord_nieuw = URLEncoder.encode(wachtwoord_nieuw, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            wachtwoord_herhalen = URLEncoder.encode(wachtwoord_herhalen, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!wachtwoord_herhalen.equals("") && wachtwoord_nieuw.equals("")) {
            _functions.melding("No password", "You didn't provide a password.\nPlease try again.", context);
        }else if (!wachtwoord_nieuw.equals("") && wachtwoord_herhalen.equals("")) {
            _functions.melding("Repeat password", "You didn't repeat your password.\nPlease try again.", context);
        }else if (!wachtwoord_nieuw.equals("") && !wachtwoord_herhalen.equals("") && !wachtwoord_nieuw.equals(wachtwoord_herhalen)) {
            _functions.melding("Wrong passwords", "Your passwords do not match.\nPlease try again.", context);
        }else{
            InputMethodManager InputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager.hideSoftInputFromWindow(editText1.getWindowToken(), 0);

            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                Toast.makeText(this.getApplicationContext(), "No active network connection", Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog = android.app.ProgressDialog.show(this, "Saving", "One moment please..", true, false);
                new opslaan().execute();
            }
        }

    }

    private class opslaan extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL(getString(R.string.website_paginas)+"/profiel.php?naam="+naam+"&naam_nieuw="+naam_nieuw+"&email="+email_nieuw+"&wachtwoord="+wachtwoord_nieuw+"&plaatje="+plaatje_nieuw);
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
            opslaan_klaar();
        }

    }

    public void opslaan_klaar(){

        if (resultaat.matches("ERROR")) {
            _functions.melding("Opslaan niet mogelijk", "Opslaan is op dit moment niet mogelijk..\nProbeer het later nog een keer.", context);
        }else if (resultaat.matches("NAAM")){
            _functions.melding("Username exists", "This username already exists.\nPlease try again.", context);
        }else if (resultaat.matches("EMAIL")){
            _functions.melding("E-mail exists", "This e-mail address already exists.\nPlease try again.", context);
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (plaatje_nieuw.equals("")){
                plaatje_nieuw = plaatje;
            }

            if (naam_nieuw.equals("")){
                naam_nieuw = naam;
            }

            if (email_nieuw.equals("")){
                email_nieuw = email;
            }

            editor.putString("plaatje", plaatje_nieuw);
            editor.putString("naam", naam_nieuw);
            editor.putString("email", email_nieuw);
            editor.apply();

            Toast toast = Toast.makeText(this.getApplicationContext(), "Profile saved!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public int uploadFile(String fileName) {

        HttpURLConnection conn;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(fileName);

        if (!sourceFile.isFile()) {
            ProgressDialog.dismiss();
            //Source File not exist
            return 0;
        }else{
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(getString(R.string.website_paginas)+"/upload.php?naam="+naam);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename="+ fileName + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            //Toast.makeText(Profiel.this, "File Upload Complete.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                ProgressDialog.dismiss();
                Log.e("Viewfinder", "MalformedURLException");
            } catch (Exception e) {
                ProgressDialog.dismiss();
                Log.e("Viewfinder", ""+e);
            }
            ProgressDialog.dismiss();
            return serverResponseCode;

        }
    }

    public void uitloggen(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("plaatje", null);
        editor.putString("naam", null);
        editor.putString("email", null);
        editor.putString("wachtwoord", null);
        editor.apply();
        Intent intent = new Intent(this, Welkom.class);
        startActivity(intent);
        finish();
    }

}