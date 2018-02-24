package com.example.carolyncheung.hisimageviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.carolyncheung.hisimageviewer.utils.HISDecoder;

public class ImageViewerActivity extends AppCompatActivity {
    Bitmap mBitmap;
    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create ya own linearlayout
        mLinearLayout = new LinearLayout(this);
        ImageView image = new ImageView(this);

        Intent intent = getIntent();
        String filePath = intent.getExtras().getString(HISDecoder.FILEPATH_EXTRA);
        HISDecoder.HISOpen(filePath);
        int[] pixels = HISDecoder.getBytes();
        mBitmap = Bitmap.createBitmap(pixels, HISDecoder.getWidth(), HISDecoder.getHeight(),
                Bitmap.Config.ARGB_8888);
        image.setImageBitmap(mBitmap);
        image.setAdjustViewBounds(true);
        image.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mLinearLayout.addView(image);
        setContentView(mLinearLayout);
        HISDecoder.HISClose();


        for (int i = 0; i < 10; i++) {
            Log.d(Integer.toString(i), Integer.toString(pixels[i]));
        }
    }
}
