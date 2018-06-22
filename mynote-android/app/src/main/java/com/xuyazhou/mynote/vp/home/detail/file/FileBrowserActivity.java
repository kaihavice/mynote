package com.xuyazhou.mynote.vp.home.detail.file;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.RecyclerViewUtils;
import com.xuyazhou.mynote.vp.adapter.FileBrowerAdapter;
import com.xuyazhou.mynote.vp.base.BaseActivity;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FileBrowserActivity extends BaseActivity<FileBorwserPresenter> implements FileBorwserContract.View, FileBrowerAdapter.FileItemClickLisenter {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Inject
    FileBrowerAdapter adapter;
    @BindView(R.id.title_layout)
    TextView titleLayout;
    @BindView(R.id.btn_save)
    Button btnSave;
    private File currentParent;

    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_file_browser;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();

    }

    @Override
    public void initUI() {

        if (getIntent().hasExtra("isLocalFile")) {
            toolbar.setTitle("文件保存到");
            btnSave.setVisibility(View.VISIBLE);
        } else {
            toolbar.setTitle("选择文件");
        }


        RecyclerViewUtils.setRecyclerView(this, recyclerView, adapter, 8, () -> {
        });

        // 获取系统的SDCard的目录
        File root = new File(Environment.getExternalStorageDirectory().getPath());
        currentParent = root;

        try {
            titleLayout.setText(root.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.setFileItemClickLisenter(this);

        Observable.just(root)
                .map(File::listFiles)
                .compose(((BaseActivity) this).bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> adapter.setDataList(files));






    }


    @Override
    public void fileClick(File file) {
        Intent intent = new Intent();
        intent.putExtra("fileName", file.getName());
        intent.putExtra("fileurl", file.getAbsolutePath());
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void directoryClick(File file) {
        currentParent = file;
        try {
            titleLayout.setText(currentParent.getCanonicalPath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Observable.just(file)
                .map(File::listFiles)
                .compose(((BaseActivity) this).bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> adapter.setDataList(files));
    }

    @OnClick(R.id.btn_save)
    public void saveFileClick() {
        try {
            presenter.saveFile(getIntent().getBooleanExtra("isLocalFile", false), getIntent().getStringExtra("path"),
                    currentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            try {
                if (currentParent.getCanonicalPath().equals(Environment.getExternalStorageDirectory().getPath())) {
                    finish();
                } else {
                    titleLayout.setText(currentParent.getParentFile().getCanonicalPath());

                    adapter.setDataList(currentParent.getParentFile().listFiles());

                    currentParent = currentParent.getParentFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}