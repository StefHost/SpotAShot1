package nl.stefhost.viewfinder.functies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import nl.stefhost.viewfinder.R;

public class laad_plaatje_textview extends AsyncTask<String, Void, Bitmap> {

	  TextView textView;

	  public laad_plaatje_textview(TextView textView) {
		  this.textView = textView;
	  }

	  protected Bitmap doInBackground(String... urls) {

	      String link = urls[0];
	      Bitmap plaatje = null;
	      try {
	        InputStream in = new java.net.URL(link).openStream();
	        plaatje = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return plaatje;
	  }

	  protected void onPostExecute(Bitmap result) {

		  textView.setBackgroundResource(android.R.color.transparent);
		  Drawable d = new BitmapDrawable(result);
		  textView.setBackground(d);

	  }
	}
