package com.example.wyf.architect_mvc;
import Utils.ActivityUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;

import com.example.wyf.mvc_active.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);
        initToolar();
        initFragment();
    }

    private void initFragment() {
        Toolbar toolbar = findViewById(R.id.toolbar); // 从布局文件中加载顶部导航Toolbar
        setSupportActionBar(toolbar); // 自定义顶部导航Toolbar为ActionBar
    }

    private void initToolar() {
        DiariesFragment diariesFragment = getDiariesFragment(); // 初始化Fragment
        if (diariesFragment == null) { // 查找是否已经创建过日记Fragment
            diariesFragment = new DiariesFragment(); // 创建日记Fragment
            // 将日记Fragment添加到Activity显示
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), diariesFragment, R.id.content);
        }
    }

    private DiariesFragment getDiariesFragment() {
        // 通过FragmentManager查找日记展示Fragment
        return (DiariesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }
}