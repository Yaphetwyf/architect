package com.example.wyf.mvp.util;

import android.support.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction(); // Fragment事务开始
        transaction.add(frameId, fragment); // 添加Fragment，frameId为fragment的id
        transaction.commit(); // 提交事务
    }

}
