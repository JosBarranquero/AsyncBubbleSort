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
    BubbleSortTask bubbleSortTask;

    interface TaskCallback {
        void onPreExecute();    //Sets the button visibility
        void onProgressUpdate(int progress);    //Updates the operation progress
        void onCancelled();
        void onPostExecute(Long time);
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
        bubbleSortTask = new BubbleSortTask();
        bubbleSortTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    class BubbleSortTask extends AsyncTask<Void, Integer, Long> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCallback.onPreExecute();
        }

        @Override
        protected Long doInBackground(Void... params) {
            long t0 = System.currentTimeMillis();

            int aux;
            int numbers[] = MainActivity.numbers;

            for (int i = 0; i < numbers.length - 1; i++) {
                for (int j = 0; j < numbers.length -1; j++) {
                    if (numbers[j] > numbers[j+1])
                    {
                        aux          = numbers[j];
                        numbers[j]   = numbers[j+1];
                        numbers[j+1] = aux;
                    }
                }
                if(!isCancelled())
                    publishProgress((int)(((i+1)/(float)(numbers.length-1))*100));
                else break;

            }

            long t1 = System.currentTimeMillis();

            return t1 - t0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mCallback.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(Long time) {
            super.onPostExecute(time);
            mCallback.onPostExecute(time);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mCallback.onCancelled();
        }
    }
}
