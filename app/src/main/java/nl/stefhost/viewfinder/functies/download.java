package nl.stefhost.viewfinder.functies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nl.stefhost.viewfinder.R;

public class download extends AsyncTask<String, String, String> {

	ImageView imageview;
	String tegenstander;
	String profielfoto;

	public download(ImageView imageview) {
		this.imageview = imageview;
	}

	protected String doInBackground(String... input){

		try {
			tegenstander = input[0];
			profielfoto = input[1];
			String link = "http://viewfinder.stefhost.nl/uploads/profielfotos/"+tegenstander+"_"+profielfoto+".jpg";
			URL url = new URL(link);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			File opslag = Environment.getExternalStorageDirectory();
			final File map = new File(opslag,"/Viewfinder/profielfotos/");

			if(!map.exists()){
				map.mkdirs();
			}

			File file = new File(map, tegenstander+"_"+profielfoto+".jpg");
			FileOutputStream fileOutput = new FileOutputStream(file);
			InputStream inputStream = urlConnection.getInputStream();

			byte[] buffer = new byte[1024];
			int bufferLength;

			while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
				fileOutput.write(buffer, 0, bufferLength);
			}
			fileOutput.close();

		}catch (MalformedURLException e){
			Log.e("Viewfinder", "MalformedURLException");
		}catch (IOException e){
			Log.e("Viewfinder","IOException");
		}

		return null;
	}

	protected void onPostExecute(String file_url) {
		File opslag = Environment.getExternalStorageDirectory();
		File plaatje = new File(opslag,"/Viewfinder/profielfotos/"+tegenstander+"_"+profielfoto+".jpg");
		final Bitmap myBitmap = BitmapFactory.decodeFile(plaatje.getAbsolutePath());
		imageview.setImageBitmap(myBitmap);
	}

}