package com.vishnusivadas.advancedhttpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;
    EditText texttostring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.dataView);
        progressBar = findViewById(R.id.progress);
        Button buttonFetch = findViewById(R.id.btnFetchData);
        Button buttonPut = findViewById(R.id.btnPutData);
        texttostring = findViewById(R.id.texttostring);

        buttonFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://abenk2.000webhostapp.com/abenk.php")));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://abenk2.000webhostapp.com/abenk.php")));
                }

                //Starting Read data from URL
               /* Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FetchData fetchData = new FetchData("https://abenk2.000webhostapp.com/index.php");
                        if (fetchData.startFetch()) {
                            if (fetchData.onComplete()) {
                                String result = fetchData.getResult();
                                progressBar.setVisibility(View.GONE);
                                textView.setText(result);
                                Log.i("FetchData", result);
                            }
                        }
                    }
                }); */
                //End Read data from URL
            }
        });

        buttonPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rcvtext;
                rcvtext = String.valueOf(texttostring.getText());
                if(!rcvtext.equals("")) {

                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[1];
                            field[0] = "keys";
                            //field[1] = "param-2";
                            //Creating array for data
                            String[] data = new String[1];
                            data[0] = rcvtext;
                            //data[1] = "data-2";
                            PutData putData = new PutData("https://abenk2.000webhostapp.com/verifykeys.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    progressBar.setVisibility(View.GONE);
                                     if(result.equals("")){

                                        textView.setText("key is wrong");



                                     } else{

                                         textView.setText(result);






                                    }
                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void exit(String gameName) throws IOException, IOException {

        Process suProcess = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

        os.writeBytes("adb shell" + "\n");

        os.flush();

        os.writeBytes("am force-stop "+gameName+ "\n");
        os.flush();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "succesfully closed", Toast.LENGTH_LONG).show();
    }







}