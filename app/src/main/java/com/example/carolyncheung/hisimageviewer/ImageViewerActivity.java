package com.example.carolyncheung.hisimageviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.carolyncheung.hisimageviewer.utils.HISDecoder;
import com.example.carolyncheung.hisimageviewer.utils.TouchImageView;

// TODO: Fragment ? to control contrast/brightness

public class ImageViewerActivity extends AppCompatActivity {
    Bitmap mBitmap;
    LinearLayout mLinearLayout;
    TouchImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Intent intent = getIntent();
        String filePath = intent.getExtras().getString(HISDecoder.FILEPATH_EXTRA);
        HISDecoder.HISOpen(filePath);
        mImageView = findViewById(R.id.imageView);
        int[] pixels = HISDecoder.getBytes();

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] * 500000;
        }

        mBitmap = Bitmap.createBitmap(pixels, HISDecoder.getWidth(), HISDecoder.getHeight(),
                Bitmap.Config.ARGB_8888);

        mImageView.setImageBitmap(mBitmap);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setAdjustViewBounds(true);
        mImageView.setBackgroundColor(Color.parseColor("#000000"));

        HISDecoder.HISClose();
    }
}
