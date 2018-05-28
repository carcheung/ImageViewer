package com.example.carolyncheung.hisimageviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.carolyncheung.hisimageviewer.utils.DrawRectangle;
import com.example.carolyncheung.hisimageviewer.utils.HISDecoder;
import com.example.carolyncheung.hisimageviewer.utils.ImageUtils;
import com.example.carolyncheung.hisimageviewer.utils.TouchImageView;

public class ImageViewerActivity extends AppCompatActivity {
    Bitmap mBitmap;
    TouchImageView mImageView;
    DrawRectangle rectangleView;
    // store raw pixel values into array for continuous manipulation
    int[] rawPixelValues;
    int[] pixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        final ViewGroup rootView = findViewById(android.R.id.content);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.image_bottom_navigiation_bar);
        mImageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        String filePath = intent.getExtras().getString(HISDecoder.FILEPATH_EXTRA);
        HISDecoder.HISOpen(filePath);

        // clone raw pixels for manipulation
        rawPixelValues = HISDecoder.getBytes();
        pixels = new int[rawPixelValues.length];
        System.arraycopy(rawPixelValues, 0, pixels, 0, rawPixelValues.length);

        renderImage(0, 65535);

        HISDecoder.HISClose();

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()) {
                        // lock and allow for selection
                        case R.id.action_LUT_area_highlight:
                            mImageView.setLock();
                            if (mImageView.getLock()) {
                                int navBarLocation = rootView.getHeight() - bottomNavigationView.getHeight();
                                rectangleView = new DrawRectangle(ImageViewerActivity.this);
                                rectangleView.setNavBarLocation(navBarLocation);
                                rectangleView.setStrokeWidth(mImageView.getCurrentZoom());
                                rootView.addView(rectangleView);
                            } else {
                                rootView.removeView(rectangleView);
                                rectangleView = null;
                            }
                            break;
                        // TODO: allow adjustment of brightness
                        case R.id.action_brightness:

                            break;
                        // TODO: allow contrast adjustment
                        case R.id.action_contrast:
                            pixels = ImageUtils.BrightnessAdjustment(pixels, 20);
                            break;
                        // adjust image based on selected area
                        case R.id.action_adjust_image:
                            if (rectangleView != null) {
                                // get the numbers of the selected area pls
                                Rect r = mImageView.translateSelectionCoordinates(rectangleView.getRectCoords());
                                int minVal = 65535;
                                int maxVal = 0;
                                int top, bottom, left, right;
                                if (r.top < r.bottom) {
                                    top = r.top;
                                    bottom = r.bottom;
                                } else {
                                    top = r.bottom;
                                    bottom = r.top;
                                }
                                if (r.left < r.right) {
                                    left = r.left;
                                    right = r.right;
                                } else {
                                    left = r.right;
                                    right = r.left;
                                }
                                // scan only pixels within ROI
                                for (int y = top; y < bottom; y++) {
                                    for (int x = left; x < right; x++) {
                                        int idx = mBitmap.getWidth() * y + x;
                                        if (rawPixelValues[idx] > maxVal) {
                                            maxVal = rawPixelValues[idx];
                                        }
                                        if (rawPixelValues[idx] < minVal) {
                                            minVal = rawPixelValues[idx];
                                        }
                                    }
                                }

                                System.arraycopy(rawPixelValues, 0, pixels, 0, rawPixelValues.length);
                                renderImage(minVal, maxVal);
                            }
                            break;
                    }
                    return true;
                }
            }
        );
    }

    // renders HIS image from raw pixel values
    private void renderImage(int dark, int bright) {
        pixels = ImageUtils.LUTCalculation(pixels, bright, dark);

        mBitmap = Bitmap.createBitmap(pixels, HISDecoder.getWidth(), HISDecoder.getHeight(),
                Bitmap.Config.ARGB_8888);
        mImageView.setImageBitmap(mBitmap);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setAdjustViewBounds(true);
        mImageView.setBackgroundColor(Color.parseColor("#000000"));
    }
}
