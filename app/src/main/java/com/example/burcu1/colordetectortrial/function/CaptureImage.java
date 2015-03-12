package com.example.burcu1.colordetectortrial.function;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.burcu1.colordetectortrial.R;


public class CaptureImage extends ActionBarActivity{

    // private static final int CAMERA_REQUEST_CODE = 1888;
    private Intent comingIntent;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        comingIntent = getIntent();
        openCamera();
    }

    private void openCamera()
    {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" + fileUri, Toast.LENGTH_LONG).show();
                //return main activity
                comingIntent.putExtra("fileUri", fileUri.toString());
                setResult(RESULT_OK,comingIntent);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                Toast.makeText(this, "Taking picture was canceled", Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED,comingIntent);
                finish();
            } else {
                // Image capture failed, advise user
                Toast.makeText(this, "Error in Taking picture!", Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED,comingIntent);
                finish();
            }
        }
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyColorDetectorApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyColorDetectorApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }else {
            return null;
        }

        return mediaFile;
    }
}
