package com.barranquero.asyncbubblesort;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity implements ProcessFragment.TaskCallback {
    private TextView mTxvProgress;
    private Button mBtnStart, mBtnCancel;
    private ProcessFragment mFragment;
    private ProgressBar mProgressBar;

    public static final int LENGTH = 35000;
    public static int numbers[] = new int[LENGTH];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxvProgress = (TextView) findViewById(R.id.txvProgress);

        mProgressBar = (ProgressBar) findViewById(R.id.pbProgress);

        mBtnStart = (Button) findViewById(R.id.btnStart);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment = new ProcessFragment();
                getFragmentManager().beginTransaction().add(mFragment, "FRAGMENT_HIDDEN").commit();
            }
        });
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.bubbleSortTask.cancel(true);
            }
        });

        mFragment = (ProcessFragment)getFragmentManager().findFragmentByTag("FRAGMENT_HIDDEN");
        if (mFragment == null) {
            generateNumbers();
        }

        if (mFragment != null) {
            if (mFragment.bubbleSortTask.getStatus() == AsyncTask.Status.RUNNING) {
                mProgressBar.setVisibility(View.VISIBLE);
                mBtnStart.setVisibility(View.GONE);
                mBtnCancel.setVisibility(View.VISIBLE);
            }
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

    @Override
    public void onPreExecute() {
        mTxvProgress.setText("Yo me iría a por un cafelito...");
        mBtnCancel.setVisibility(View.VISIBLE);
        mBtnStart.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressUpdate(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    public void onCancelled() {
        mTxvProgress.setText("Que impaciente eres");
        mBtnCancel.setVisibility(View.GONE);
        mBtnStart.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPostExecute(Long time) {
        mBtnCancel.setVisibility(View.GONE);
        mBtnStart.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTxvProgress.setText("Ha tardado pechá, " + time + " milisegundos");
    }
}