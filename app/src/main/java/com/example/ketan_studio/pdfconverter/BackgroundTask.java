package com.example.ketan_studio.pdfconverter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class BackgroundTask extends AsyncTask<Void, Integer,String> {
    Context c;
    ProgressDialog pd;

    public BackgroundTask(Context c) {
        this.c = c;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(c);
        pd.setTitle("Downloading");
        pd.setMessage("Please wait...");
        pd.setMax(10);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(3000);
                publishProgress(i);
                Log.i("Thread","Exception"+i);
                
            }
            return "Sucess";
        }catch (Exception e){
            Log.i("Exception e",e.getMessage());
            return "Failer";
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int myValue = values[0];
        pd.setProgress(myValue);
    }

    @Override
    protected void onPostExecute(String s) {
        pd.dismiss();
        Toast.makeText(c, "Sucessfull", Toast.LENGTH_SHORT).show();
    }
}
