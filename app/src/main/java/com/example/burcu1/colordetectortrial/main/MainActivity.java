package com.example.burcu1.colordetectortrial.main;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.TextView;

import com.example.burcu1.colordetectortrial.function.*;
import com.example.burcu1.colordetectortrial.utility.*;
import com.example.burcu1.colordetectortrial.db.*;
import com.example.burcu1.colordetectortrial.R;

public class MainActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView imageView;
    private Uri imageUri;
    private Bitmap imageBitmap;
    private String densityColor;
    private String luminanceColor;

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
        }
        else
        {
            Toast.makeText(this, "Color could not found!\n", Toast.LENGTH_LONG).show();
        }

    }

}
