package com.example.burcu1.colordetectortrial.main;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import com.example.burcu1.colordetectortrial.function.*;
import com.example.burcu1.colordetectortrial.utility.*;
import com.example.burcu1.colordetectortrial.db.*;
import com.example.burcu1.colordetectortrial.R;

import java.util.Locale;


public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener{

    //Image capturing & color detecting variables
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Uri imageUri;
    private Bitmap imageBitmap;
    private String densityColor;
    private String luminanceColor;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        callCaptureImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callCaptureImage()
    {
        Intent takePicture = new Intent(this, CaptureImage.class);
        startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
    }

    private void callColorFinder()
    {
        String path = getPath(imageUri);
        imageBitmap = ImageUtility.loadStandardBitmapFromPath(path);
        if (imageBitmap != null) {
            getPixelDensity(imageBitmap);

            getPixelLuminance(imageBitmap);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Error in getting bitmap! ", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        createTTS();
        for(int i = 0; i < 3000; i++){};
        Log.w("activityReult","gelen request code: "+requestCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if(resultCode == RESULT_OK){
                //picture was taken.
                String uri = data.getStringExtra("fileUri");
                displayCapturedImage(uri);
            }
            if (resultCode == RESULT_CANCELED) {
                //picture could not be taken.
                callCaptureImage();
            }
            callColorFinder();
        }
    }

    //image capturing and color detecting functions
    private void displayCapturedImage(String uri) {

        try {
            imageUri = Uri.parse(uri);
            imageView.setImageURI(imageUri);
        }
        catch (NullPointerException e) {

            e.printStackTrace();

            Toast.makeText(this, "There is a problem in image displaying!\n", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {

            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { android.provider.MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    private void getPixelDensity(Bitmap bitmap) {
        new PixelDensity(new PixelDensity.CallbackInterface() {
            @Override
            public void onCompleted(String hexColor) {
                setDensity(hexColor);
            }
        }).findDominantColor(bitmap);
    }

    private void getPixelLuminance(Bitmap bitmap) {
        new PixelLuminance(new PixelLuminance.CallbackInterface() {
            @Override
            public void onCompleted(String hexColor) {
                setLuminance(hexColor);
            }
        }).findDominantColor(bitmap);
    }

    private void setLuminance(String hexColor) {
        luminanceColor = hexColor;
        int color = Color.parseColor(hexColor);
        findViewById(R.id.luminance).setBackgroundColor(color);
        ((TextView)findViewById(R.id.luminanceText)).setTextColor(ColorUtility.getOptimizedTextColor(color, 0f));
        ((TextView)findViewById(R.id.luminanceText)).setText("Brightest Pixel Color\n" + hexColor);
    }

    private void setDensity(String hexColor) {
        densityColor = hexColor;
        int color = Color.parseColor(hexColor);
        findViewById(R.id.density).setBackgroundColor(Color.parseColor(hexColor));
        ((TextView)findViewById(R.id.densityText)).setTextColor(ColorUtility.getOptimizedTextColor(color, 0f));
        ((TextView)findViewById(R.id.densityText)).setText("Densest Pixel Color\n" + hexColor);

        findColorName(densityColor);
    }

    private void findColorName(String color)
    {
        DBHelper helper = new DBHelper(this);
        Toast.makeText(this, "Hex color is:\n" + color, Toast.LENGTH_LONG).show();

        ColorProperties gotColor = helper.getColorByCode(color);
        if(gotColor != null) {
            Toast.makeText(this, "Detected color is:\n" + gotColor.colorName, Toast.LENGTH_LONG).show();
            TextView textView = (TextView) findViewById(R.id.colorName);
            textView.setText(gotColor.colorName);
            vocalize(gotColor.colorName);
        }
        else
        {
            Toast.makeText(this, "Color could not found!\n", Toast.LENGTH_LONG).show();
        }

    }

    //TTS functions
/*
    private void checkTTS(){
        Log.w("CheckTTS", "check'de");

        tts = new TextToSpeech(this, this);
        Log.w("activityReultTTS","yeni speaker yaratıldı");
        if(tts.isLanguageAvailable(Locale.ENGLISH)==TextToSpeech.LANG_COUNTRY_AVAILABLE)
        {
            Log.w("chechTTS","language available");
        }
        else
        {
            Intent install = new Intent();
            install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(install);
        }
    }
*/
    @Override
    public void onInit(int status)
    {
        Log.w("TTS", "onInit'de");
        if (status == TextToSpeech.SUCCESS) {
            if (tts.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                tts.setLanguage(Locale.UK);
        }
        else
        {
            Intent install = new Intent();
            install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(install);
        }

        if(status == TextToSpeech.SUCCESS){
            int result = tts.isLanguageAvailable(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "TTS is missing or not supported");
            }
            else
            {
                Log.w("onInit","language was set");
            }
        }
        else
        {
            Log.w("onInit","TextToSpeech status does not successful");
        }

    }

    private void createTTS()
    {
        if (tts == null) {
            // currently can't change Locale until speech ends
            try {
                // Initialize text-to-speech. This is an asynchronous operation.
                // The OnInitListener (second argument) is called after
                // initialization completes.
                tts = new TextToSpeech(this, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void vocalize(String text)
    {
        Log.w("vocalize'a gelen:", text);
        tts.speak(text,TextToSpeech.QUEUE_ADD,null);
        tts.playSilence(1200,TextToSpeech.QUEUE_ADD, null);

    }

    @Override
    public void onDestroy()
    {
        // Don't forget to shutdown!
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}
