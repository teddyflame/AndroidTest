package com.xu.myandroidtest.file;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xu.myandroidtest.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SDFileExplorerActivity extends Activity {

    ListView listView;
    TextView textView;

    File currentParent;
    File[] currentFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdfile_explorer);

        listView  = (ListView)findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.pathTv);
        File root  = new File("/mnt/sdcard");

        if(root.exists()){
            currentParent = root;
            currentFiles = root.listFiles();
            inflateListView(currentFiles);
        }

        setListener();
    }

    private void setListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentFiles[i].isFile()){
                    Toast.makeText(SDFileExplorerActivity.this,"点击的是文件",Toast.LENGTH_SHORT).show();
                    return;
                }

                File[] tmp = currentFiles[i].listFiles();
                if(tmp==null||tmp.length==0){
                    Toast.makeText(SDFileExplorerActivity.this,"当前路径不可访问或没有文件",Toast.LENGTH_SHORT).show();
                }else{
                    currentParent = currentFiles[i];
                    currentFiles = tmp;
                    inflateListView(currentFiles);
                }
            }
        });
    }


    public void backonClick(View view){
        try{
            if(!currentParent.getCanonicalPath().equals("/mnt/sdcard")){
                //获取上一级目录
                currentParent = currentParent.getParentFile();
                //列出当前目录下所有文件
                currentFiles = currentParent.listFiles();
                //再一次更新ListView
                inflateListView(currentFiles);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void inflateListView(File[] files){
        List<Map<String,Object>> listItems = new ArrayList<>();
        for(int i=0;i<files.length;i++){
            Map<String,Object> listItem = new HashMap<>();

            //设置图标：文件夹或者文件
            if(files[i].isDirectory()){
                listItem.put("icon",R.drawable.folder);
            }else{
                listItem.put("icon",R.drawable.file);
            }

            listItem.put("fileName",files[i].getName());

            listItems.add(listItem);
        }

        //创建一个ADAPTER
        SimpleAdapter adapter = new SimpleAdapter(this,
                listItems,R.layout.sdfile_explorer_list_cell,
                new String[]{"icon","fileName"},
                new int[] {R.id.icon,R.id.file_name});

        listView.setAdapter(adapter);

        try{
            textView.setText("当前路径为："+currentParent.getCanonicalPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
