package com.example.ketan_studio.pdfconverter;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;

public class CliptoBitmapTask extends AsyncTask<ClipData,Void, Uri> {

    Context c;
    ContentResolver cr;

    public CliptoBitmapTask(Context c) {
        this.c = c;
    }

    public CliptoBitmapTask() {
    }

    @Override
    protected Uri doInBackground(ClipData... clipData) {
        try {
            ClipData c = clipData[0];
            for (int i = 0; i < c.getItemCount(); i++){
                Log.i("Level 1","ThreadNo:"+i);
                ClipData.Item CD = c.getItemAt(i);
                Log.i("Level 2","ThreadNo:"+i);
                Uri u = CD.getUri();
                Log.i("Level 3","ThreadNo:"+i);
                return u;
            }
        }catch (Exception e){
            Log.i("Exception",""+e.getMessage());
        }
        return null;
    }
}
