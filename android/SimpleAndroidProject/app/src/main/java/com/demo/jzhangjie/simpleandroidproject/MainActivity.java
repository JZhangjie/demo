package com.demo.jzhangjie.simpleandroidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.jzhangjie.simpleandroidproject.fragment.AdjustableHeightContainer;

public class MainActivity extends AppCompatActivity implements AdjustableHeightContainer.AdjustableHeightContainerCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void TopButtonClick(String key) {
        //此次方法将在窗口顶部按钮点击，切换窗口时调用
    }
}
