package com.example.ketan_studio.pdfconverter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button image,create;
    Uri u;
    int code = 1;
    int fileno = 1;
    static ClipData clip;

    Bitmap scale;
    Paint myPaint;
    PdfDocument myPdfDocument;
    int h;
    int w;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);

        image = (Button)findViewById(R.id.button);


        create = (Button)findViewById(R.id.create);

        try {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(i,code);
                }
            });
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myPdfDocument = new PdfDocument();
                    myPaint = new Paint();
                    for (i = 0;i<clip.getItemCount();i++){
                        try {
                            ClipData.Item c = clip.getItemAt(i);
                            u = c.getUri();
                            InputStream inputStream = getContentResolver().openInputStream(u);
                            Bitmap b = BitmapFactory.decodeStream(inputStream);
                            h = b.getHeight();
                            w = b.getWidth();
                            scale = Bitmap.createScaledBitmap(b,w,h,false);
                            Log.i("Execp","Level"+i);

                            ////////////////////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////////////////
                            ///////////////////////////////////////////////////////////////////////////////
                            Log.i("Execp Inside","Level"+i);
                            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(w,h,i).create();
                            PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                            Canvas canvas = myPage1.getCanvas();
                            Log.i("Execp Inside","Running Smooth"+i);
                            canvas.drawBitmap(scale,2,2,myPaint);
                            myPdfDocument.finishPage(myPage1);

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error IO For loop below", Toast.LENGTH_SHORT).show();
                            Log.i("Exception","Level"+i);
                        }


                    }

                    File file = new File(Environment.getExternalStorageDirectory(),"/Hello"+fileno+".pdf");
                    fileno = fileno ++;

                    try {
                        myPdfDocument.writeTo(new FileOutputStream(file));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.close();
                    Toast.makeText(MainActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Main Error", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==code && resultCode==RESULT_OK && data!=null){

            clip = data.getClipData();
            if (clip != null){
                create.setVisibility(View.VISIBLE);
            }
        }
    }
}
