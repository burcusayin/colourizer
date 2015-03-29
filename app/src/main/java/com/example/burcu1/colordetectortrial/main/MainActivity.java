package com.example.burcu1.colordetectortrial.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Color;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import com.example.burcu1.colordetectortrial.function.*;
import com.example.burcu1.colordetectortrial.utility.*;
import com.example.burcu1.colordetectortrial.db.*;
import com.example.burcu1.colordetectortrial.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener{

    //Image capturing & color detecting variables
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView imageView;
    private Uri imageUri;
    private Bitmap imageBitmap;
    private String densityColor;
    private String luminanceColor;

    //TextToSpeech variables
    private TextToSpeech tts;
    private boolean isFinished;
    private boolean speakOk;

    //SpeechToText variables
    static final int REQUEST_VOICE_RECOGNITION = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        isFinished = true;
        createTTS();
        /*
        do{}while(isFinished);
        vocalize("Welcome to Colourizer");
        vocalize("You can say take picture to hear which color your dress has");
        vocalize("Please touch to screen before giving your voice command and wait until you hear a beep");
        */
        startVoiceRecognition();

        /*
        try {
            writeToSD();
        }
        catch (IOException e) {
            e.printStackTrace();
        };
        */
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
        /*
        Intent takePicture = new Intent(this, CaptureImage.class);
        startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
        */

        Intent takePicture = new Intent(this,TakePicture.class);
        takePicture.putExtra("FLASH", "off");
        startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
    }

    private void callColorFinder(String path)
    {
        Bitmap bitmap = ImageUtility.loadStandardBitmapFromPath(path);
        if (bitmap != null) {
            getPixelDensity(bitmap);

            getPixelLuminance(bitmap);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Error in getting bitmap! ", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        createTTS();
        for(int i = 0; i < 3000; i++){};
        Log.w("activityResult","gelen request code: "+requestCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            imageView = (ImageView) findViewById(R.id.imageView);
            String path = null;
            if(resultCode == RESULT_OK){
                //picture was taken.
                String uri = data.getStringExtra("fileUri");
                path = FileUtility.getRealPathFromURI(this,Uri.parse(uri));
                displayCapturedImage(path);
                callColorFinder(path);
            }
            if (resultCode == RESULT_CANCELED) {
                //picture could not be taken.
                callCaptureImage();
            }
        }
        else if(requestCode == REQUEST_VOICE_RECOGNITION)
        {
            imageView = (ImageView) findViewById(R.id.imageView);
            setContentView(R.layout.activity_main);
            if(resultCode == RESULT_OK){
                //picture was taken.
                String voiceInput = data.getStringExtra("voiceInput");
                evaluateVoiceInput(voiceInput);
            }
            if (resultCode == RESULT_CANCELED) {
                //picture could not be taken.
                Log.w("voice","Something went wrong!");
                startVoiceRecognition();
            }
        }
    }

    //image capturing and color detecting functions
    private void displayCapturedImage(String path) {

        try {
            Log.w("displayde","try a girdi");
            Log.w("displayde","image file: " + path);

            Bitmap myImg = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(myImg);
            imageView.invalidate();

        }
        catch (NullPointerException e) {

            e.printStackTrace();

            Toast.makeText(this, "There is a problem in image displaying!\n", Toast.LENGTH_LONG).show();
        }

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
                isFinished = false;
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
        do{  }while(isFinished);
        tts.speak(text,TextToSpeech.QUEUE_ADD,null);
        tts.playSilence(1200,TextToSpeech.QUEUE_ADD, null);
        speakOk = true;
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

    // voice recognition functions

    private void startVoiceRecognition()
    {
        Intent startVoice = new Intent(this, VoiceRecognitionActivity.class);
        startActivityForResult(startVoice, REQUEST_VOICE_RECOGNITION);
    }

    private void evaluateVoiceInput(String input)
    {
        Toast.makeText(this, "Detected voice is:" + input, Toast.LENGTH_LONG).show();

        if(input.toLowerCase().contains("picture".toLowerCase()))
        {
            callCaptureImage();
        }
        else
        {
            speakOk = false;
            vocalize("Please, try again!");
            do{}while(!speakOk);
            startVoiceRecognition();
        }
    }

}
