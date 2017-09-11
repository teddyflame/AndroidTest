package com.xu.myandroidtest.file;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xu.myandroidtest.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileTestActivity extends AppCompatActivity {
    String FILE_NAME = "crazy.bin";
    EditText readET ;
    EditText writeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_test);

        readET = (EditText)findViewById(R.id.readET);
        writeET  = (EditText)findViewById(R.id.writeET);
    }

    public void readClick(View view){
        readET.setText(read());
    }

    public void writeClick(View view){
        boolean result =write(writeET.getText().toString());
        if(result){
            Toast.makeText(this,"write success!",Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(this,"write failed!",Toast.LENGTH_SHORT);
        }
    }

    private String read(){
        try{
            //打开文件输入流
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buff = new byte[1024];
            int hasRead = 0;

            StringBuilder sb = new StringBuilder();

            while((hasRead=fis.read(buff))>0){
                sb.append(new String(buff,0,hasRead));
            }
            fis.close();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    private boolean write(String content){
        try{
            //以追加方式打开文件输出流
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
            //将FileOutStream  包装成 PrintStream
            PrintStream ps = new PrintStream(fos);

            ps.println(content);
            ps.close();

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
