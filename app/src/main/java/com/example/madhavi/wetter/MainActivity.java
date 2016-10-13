package com.example.madhavi.wetter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText eturl;Button burl;TextView turl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eturl=(EditText)findViewById(R.id.e1);
        burl=(Button)findViewById(R.id.b1);
        turl=(TextView)findViewById(R.id.t1);

        burl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityname = eturl.getText().toString();
                String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityname + "&appid=264a827dad22eef843c0684374e05d1a";

                Asynckwetter asw=new Asynckwetter();
                asw.execute(url);

            }
        });






    }
    private class Asynckwetter extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {
             String value="";JSONObject  data;
            StringBuffer json = new StringBuffer(1024);
            URL url= null;
            try {


                url = new URL(params[0]);

            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                String tmp="";
                while((tmp=br.readLine())!=null)
                    json.append(tmp).append("\n");
                br.close();

                data = new JSONObject(json.toString());
                // This value will be 404 if the request was not
                // successful
                if(data.getInt("cod") != 200){
                    return null;




                }

                return String.valueOf(data);
            }
            catch(Exception e){
                return null;
                     }



        }

        @Override
        protected void onPostExecute(String s) {
           turl.setText(s);
                   }
    }




}
