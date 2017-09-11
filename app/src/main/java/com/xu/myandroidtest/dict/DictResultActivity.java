package com.xu.myandroidtest.dict;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xu.myandroidtest.R;

import java.util.List;
import java.util.Map;

public class DictResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict_result);

        ListView listView = (ListView) findViewById(R.id.dictList);

        Bundle bundle = getIntent().getExtras();

        List<Map<String,String>> list = (List<Map<String,String>>) bundle.getSerializable("data");

        SimpleAdapter adapter = new SimpleAdapter(this,list,
                R.layout.dict_list_cell,
                new String[]{"word","detail"},
                new int[]{R.id.word,R.id.detail});
        listView.setAdapter(adapter);
    }
}
