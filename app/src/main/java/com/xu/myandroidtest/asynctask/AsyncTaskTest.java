package com.xu.myandroidtest.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xu.myandroidtest.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncTaskTest extends AppCompatActivity {

    private TextView show;
    private Button bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);


        show = (TextView)findViewById(R.id.show);
        bn = (Button)findViewById(R.id.bn);

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    download(bn);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void download(View source) throws MalformedURLException{

        DownTask task = new DownTask(this);

        task.execute(new URL("http://www.crazyit.org/ethow.php"));
    }


    class DownTask extends AsyncTask<URL,Integer,String>{
        //可变长的输入参数
        ProgressDialog pDialog;

        //定义已经读取的行数
        int hasRead = 0;

        Context mContext;

        public DownTask(Context ctx){
            mContext = ctx;
        }

        @Override
        protected String doInBackground(URL... urls) {
            StringBuilder sb = new StringBuilder();

            try{
                URLConnection conn = urls[0].openConnection();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(),
                                "utf-8"));
                String line = null;
                while((line=br.readLine())!=null) {
                    sb.append(line + "\n");
                    hasRead++;
                    publishProgress(hasRead);
                }
                return sb.toString();
            }catch (Exception e){
                e.printStackTrace();
            }

            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            show.setText(s);
            pDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(mContext);
            pDialog.setTitle("任务执行中");
            pDialog.setMessage("任务执行中，请等待。。");
            pDialog.setCancelable(false);
            pDialog.setMax(202);
            pDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            show.setText("已经读取了"+values[0]+"行");
            pDialog.setProgress(values[0]);
        }
    }



}
