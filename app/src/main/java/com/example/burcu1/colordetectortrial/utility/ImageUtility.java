package com.example.burcu1.colordetectortrial.utility;

/**
 * Created by Burcu1 on 25.2.2015.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class ImageUtility {

    private static final String TAG = ImageUtility.class.getSimpleName();

    public static Bitmap loadStandardBitmapFromPath(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        return decodeSampledBitmapFromPath(path, imageWidth, imageHeight, false);
    }

    /**
     * Decodes the bitmap from path
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static Bitmap decodeSampledBitmapFromPath(String path,
                                                      int reqWidth, int reqHeight, boolean sampleSize) {
        Bitmap returnBitmap = null;

        try {
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();

            if (sampleSize)
                if ((reqWidth > 500 || reqHeight > 500) && (reqWidth < 1000 || reqHeight < 1000)) {
                    options.inSampleSize = 5;
                } else if ((reqWidth >= 1000 || reqHeight >= 1000) && (reqWidth < 2800 || reqHeight < 2800)) {
                    options.inSampleSize = 7;
                } else if (reqWidth > 2000 || reqHeight > 2000) {
                    options.inSampleSize = 14;
                } else {
                    options.inSampleSize = 4;
                }
            else
                options.inSampleSize = 1;

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;

            returnBitmap = BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            Log.e(TAG, "ERROR", e);
        }

        return returnBitmap;
    }
}

