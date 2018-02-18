package com.example.carolyncheung.hisimageviewer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.carolyncheung.hisimageviewer.utils.FilePath;
import com.example.carolyncheung.hisimageviewer.utils.HISDecoder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("HISDecoder");
    }
    private Button filePickTest_btn;

    private static final int READ_REQUEST_CODE = 42;
    private Uri resultData;
    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filePickTest_btn = (Button) findViewById(R.id.open_file_picker_btn);

        filePickTest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //performFileSearch();
                fileSelect();
            }
        });
    }

    public void fileSelect() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // all types for now
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), READ_REQUEST_CODE);
    }

    // pls fire an intent to spin up the "file chooser" UI and select an image
    public void performFileSearch() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // filter to only show results that can be "opened", such as a file
        // as opposed to a list of contacts or timezones
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // filter to show only images, using the image MIME data type.
        // if one wanted to search for ogg vorbis files, the type would be "audio/ogg"
        // to search for all documents available via installed storaged providers,
        // it would be "*/*"
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // the document selected by the user wont be returned in the intent
            // a URI to that document will be contained in the return intent
            // provided to this as a parameter
            // pull URI using resultData.getData()
            if (data != null) {
                //resultData = data.getData();
                //Log.i("SAFHelper", "URri: + " + resultData.toString());
                //int a = HISDecoder.getWidth();
                //HISDecoder.HISOpen(resultData.toString());

                String selectedFilePath = "";
                resultData = data.getData();
                selectedFilePath = FilePath.getPath(getApplicationContext(), resultData);
                Log.d("File path", selectedFilePath);
                HISDecoder.HISOpen(selectedFilePath);
                Log.d("Height", Integer.toString(HISDecoder.getHeight()));

            }
        }
    }

    // get bytes from URI
    private byte[] getBytesFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        byte[] inputData = byteBuffer.toByteArray();
        return inputData;
    }
}
