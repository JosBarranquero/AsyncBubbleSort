package com.barranquero.asyncbubblesort;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    private TextView mTxvProgress;
    private Button mBtnStart, mBtnCancel;
    private ProcessFragment mFragment;
    private ProgressBar mProgressBar;

    public static final int LENGTH = 2000;
    public static int numbers[] = new int[LENGTH];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxvProgress = (TextView)findViewById(R.id.txvProgress);
        mBtnStart = (Button)findViewById(R.id.btnStart);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment = new ProcessFragment();
            }
        });
        mBtnCancel = (Button)findViewById(R.id.btnCancel);

        if (mFragment == null) {
            generateNumbers();
        }
    }

    /**
     * Method which initialises numbers[] with random numbers
     */
    private void generateNumbers() {
        Random rnd = new Random();
        for (int i = 0; i < LENGTH; i++) {
            numbers[i] = rnd.nextInt(100);
        }
    }
}
