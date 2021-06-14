package Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.wyf.architect_mvc.DiariesFragment;
import com.example.wyf.mvc_active.R;
import Aplication.EnApplication;
import Model.Diary;
import Model.Observer.Observer;
import Model.data.DiariesRepository;
import Source.DataCallback;
import java.util.ArrayList;
import java.util.List;

public class DiariesController implements Observer<Diary> {

    private final DiariesRepository mDiariesRepository;
    private final DiariesFragment mView;
    private DiariesAdapter mListAdapter;

    public DiariesController(@NonNull DiariesFragment diariesFragment) { // 控制日记显示的Controller
        mDiariesRepository = DiariesRepository.getInstance(); // 获取数据仓库的实例
        mView = diariesFragment; // 将传入的界面复制给日记的成员变量保存
        mView.setHasOptionsMenu(true);  // 设置界面有菜单功能
        initAdapter(); // 创建日记列表的适配器
    }

    private void initAdapter() {
        mListAdapter = new DiariesAdapter(new ArrayList<Diary>()); // 创建日记列表的适配器
        // 设置列表的条目长按事件
        mListAdapter.setOnLongClickListener(new DiariesAdapter.OnLongClickListener<Diary>() {
            @Override
            public boolean onLongClick(View v, Diary data) {
                showInputDialog(data); // 长按弹出输入对话框
                return false;
            }
        });
    }

    public void loadDiaries() { // 加载日记数据
        mDiariesRepository.getAll(new DataCallback<List<Diary>>() { // 通过数据仓库获取数据
            @Override
            public void onSuccess(List<Diary> diaryList) {
                processDiaries(diaryList); // 数据获取成功，处理数据
            }
            @Override
            public void onError() {
                showError();  // 数据获取失败，弹出错误提示
            }
        });
    }

    private void processDiaries(List<Diary> diaries) {
        for (Diary diary : diaries) {//会有内存泄漏的风险？？？？？
            diary.registerObserver(this);
        }
        mListAdapter.update(diaries); // 更新列表中的日记数据
    }

    public void gotoWriteDiary(){
        showMessage(mView.getString(R.string.developing)); // 弹出功能未开放提示
    }

    public void showError(){
        showMessage(mView.getString(R.string.error)); // 弹出数据获取失败提示
    }

    private void showMessage(String message) {
        Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show(); // 弹出文字提示信息
    }

    public void setDiariesList(RecyclerView recyclerView) { // 配置日记列表
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext())); // 设置日记列表为线性布局显示
        recyclerView.setAdapter(mListAdapter); // 为列表设置适配器
        recyclerView.addItemDecoration(
                new DividerItemDecoration(mView.getContext(), DividerItemDecoration.VERTICAL) // 为列表条目添加分割线
        );
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // 设置列表默认动画

    }

    private void showInputDialog(final Diary data) { // 弹出输入对话框
        final EditText editText = new EditText(mView.getContext()); // 创建输入框
        editText.setText(data.getDescription()); // 设置输入框默认文字为日志详情信息

        new AlertDialog.Builder(mView.getContext()).setTitle(data.getTitle())
                .setView(editText)
                .setPositiveButton(EnApplication.get().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { // 确认按钮点击监听
                                data.setDescription(editText.getText().toString()); // 修改日记信息为输入框信息
                                mDiariesRepository.update(data); // 更新日记数据
                                loadDiaries(); // 重新加载列表
                            }
                        })
                .setNegativeButton(EnApplication.get().getString(R.string.cancel), null).show(); // 弹出对话框
    }

    @Override
    public void update(Diary data) {
        mDiariesRepository.update(data);
        loadDiaries();
    }
}
