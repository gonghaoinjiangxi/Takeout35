package com.heima.baidumap35;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by lidongzhi on 2017/4/18.
 */

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    String[] mTitles = new String[]{"地图控制","标注覆盖物","圆形覆盖物","矩形覆盖物"};
    Class[] mClasses = new Class[]{BaseMapActivity.class,OverLayerActivity.class,CircleActivity.class,RectActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, mTitles);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, mClasses[position]);
        startActivity(intent);
    }
}
