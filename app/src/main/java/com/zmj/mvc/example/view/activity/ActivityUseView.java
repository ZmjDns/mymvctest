package com.zmj.mvc.example.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.utils.CharacterParser;
import com.zmj.mvc.example.view.getwordsmvp.IGetWords;
import com.zmj.mvc.example.view.getwordsmvp.ParaseWordsPersenter;

public class ActivityUseView extends AppCompatActivity{

    private static String TAG = "ActivityUseView";
    private EditText et_words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_view);

        et_words = findViewById(R.id.et_);

    }

}
