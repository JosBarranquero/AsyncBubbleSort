package com.barranquero.asyncbubblesort;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by usuario on 13/01/17
 * AsyncBubbleSort
 */

public class ProcessFragment extends Fragment {
    private TaskCallback mCallback;

    interface TaskCallback {
        void onPreExecute();    //Sets the button visibility
        void onProgressUpdate(int progress);    //Updates the operation progress
        void onCancelled();
        void onPostExecute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (TaskCallback)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() + " must implement TaskCallback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        BubbleSortTask bubbleSortTask = new BubbleSortTask();
        bubbleSortTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    class BubbleSortTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCallback.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mCallback.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCallback.onPostExecute();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mCallback.onCancelled();
        }
    }
}
