package com.example.carolyncheung.hisimageviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.carolyncheung.hisimageviewer.utils.HISDecoder;

public class ImageViewerActivity extends AppCompatActivity {
    Bitmap mBitmap;
    LinearLayout mLinearLayout;
    ImageView mImageView;

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

        //mBitmap.setDensity(DisplayMetrics.DENSITY_MEDIUM);
        mImageView.setImageBitmap(mBitmap);
        mImageView.setBackgroundColor(Color.parseColor("#000000"));

        HISDecoder.HISClose();

        for (int i = pixels.length / 2; i < pixels.length / 2 + 10; i++) {
            Log.d(Integer.toString(i), Integer.toString(pixels[i]));
        }
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create ya own linearlayout
        mLinearLayout = new LinearLayout(this);
        ImageView image = new ImageView(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);

        Intent intent = getIntent();
        String filePath = intent.getExtras().getString(HISDecoder.FILEPATH_EXTRA);
        HISDecoder.HISOpen(filePath);
        int[] pixels = HISDecoder.getBytes();
        mBitmap = Bitmap.createBitmap(pixels, HISDecoder.getWidth(), HISDecoder.getHeight(),
                Bitmap.Config.ALPHA_8);
        image.setImageBitmap(mBitmap);
        image.setAdjustViewBounds(false);

        image.setPadding(2 , 2, 0, 0);
        image.setBackgroundColor(Color.parseColor("#ffd3e9"));

    //    image.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        mLinearLayout.setBackgroundColor(Color.parseColor("#000000"));
        mLinearLayout.addView(image);
        mLinearLayout.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)));
        setContentView(mLinearLayout);

        image.setScaleType(ImageView.ScaleType.FIT_XY);
        HISDecoder.HISClose();
    } */
}
