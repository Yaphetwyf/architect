package com.example.wyf.mvp.edit;

import android.support.annotation.NonNull;

import com.example.wyf.mvp.base.BasePresenter;
import com.example.wyf.mvp.base.BaseView;
import com.example.wyf.mvp.model.data.Diary;
import com.example.wyf.mvp.presenter.DiariesAdapter;


public interface DiariesContract {

    interface View extends BaseView<Presenter> { // 日记列表视图

        void gotoWriteDiary(); // 跳转添加日记

        void gotoUpdateDiary(String diaryId); // 跳转更新日记

        void showSuccess(); // 弹出成功提示信息

        void showError(); // 弹出失败提示信息

        boolean isActive();  // 判断Fragment是否已经添加到了Activity中

        void setListAdapter(DiariesAdapter mListAdapter); // 设置适配器
    }

    interface Presenter extends BasePresenter { // 日记列表主持人

        void loadDiaries(); // 加载日记数据

        void addDiary(); // 跳转添加日记

        void updateDiary(@NonNull Diary diary); // 跳转更新日记

        void onResult(int requestCode, int resultCode); // 返回界面获取结果信息
    }
}
