package com.example.admin.personallibrarycatalogue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Mikhail Valuyskiy on 28.05.2015.
 */
public class Util {

    /**
     * Converts byte[] to Bitmap
     */
    static public Bitmap getBitmapFromBytes(byte[] image) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
        Bitmap decodedImage = BitmapFactory.decodeStream(imageStream);
        return decodedImage;
    }

    static public byte[] getBytesFromBitmap(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }

    static public byte[] getBytesFromDrawable(Drawable image){
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        byte imageInByte[] = getBytesFromBitmap(bitmap);
        return  imageInByte;
    }
}
