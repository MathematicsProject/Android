package com.example.user.cameracapture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by user on 2016-05-24.
 */
public class ResultActivity extends Activity{
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent = getIntent();
        iv=(ImageView)findViewById( R.id.iv);
        iv.setImageBitmap((Bitmap) intent.getParcelableExtra("image"));

    }
}
