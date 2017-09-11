package com.xu.myandroidtest.dict;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xu.myandroidtest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DictActivity extends Activity {

    MyDataBaseHelper dbHelpler;
    EditText wordEt;
    EditText detailEt;
    EditText keyEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        //创建MyDataBaseHelper对象，指定数据库版本为1，使用相对路径
        //数据库文件自动保存在数据文件夹的database目录下
        dbHelpler = new MyDataBaseHelper(this,"myDict.db3",1);

        initView();

    }

    private void initView(){
        wordEt = (EditText)findViewById(R.id.wordEt);
        detailEt = (EditText)findViewById(R.id.detailEt);
        keyEt = (EditText)findViewById(R.id.keyEt);
    }

    public void insertOnClick(View view){
        String word = wordEt.getText().toString();
        String detail = detailEt.getText().toString();

        //插入生词
         insertData(dbHelpler.getWritableDatabase(),word,detail);

        Toast.makeText(this,"添加生词成功！",Toast.LENGTH_SHORT).show();
    }

    private void insertData(SQLiteDatabase db,String word,String detail){
        db.execSQL("INSERT INTO dict values(null,?,?)",new String[]{word,detail});
    }

    public void searchOnClick(View view){

        String key = keyEt.getText().toString();

        Cursor cursor = dbHelpler.getReadableDatabase().rawQuery(
                "select * from dict where word like ? or detail like ?",
                new String[] {"%" + key +"%","%" + key +"%"});

        Bundle bundle = new Bundle();

        bundle.putSerializable("data",convertCursorToList(cursor));

        Intent intent = new Intent(DictActivity.this,DictResultActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private ArrayList<Map<String,String>> convertCursorToList(Cursor cursor){
        ArrayList<Map<String,String>> result = new ArrayList<>();

        while(cursor.moveToNext()){
            Map<String,String> map = new HashMap<>();

            map.put("word",cursor.getString(1));
            map.put("detail",cursor.getString(2));
            result.add(map);
        }

        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //退出时关闭MyDatabaseHelper里的SQLiteDataBase
        if(dbHelpler!=null){
            dbHelpler.close();
        }
    }
}
