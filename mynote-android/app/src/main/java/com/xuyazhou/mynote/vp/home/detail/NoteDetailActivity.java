package com.xuyazhou.mynote.vp.home.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.ActivityUtil;
import com.xuyazhou.mynote.common.utils.DialogUtil;
import com.xuyazhou.mynote.common.utils.EditUtlis;
import com.xuyazhou.mynote.common.utils.FileUtil;
import com.xuyazhou.mynote.common.utils.ImageUtils;
import com.xuyazhou.mynote.common.utils.KeyBoardUtils;
import com.xuyazhou.mynote.common.utils.RecyclerViewUtils;
import com.xuyazhou.mynote.common.widget.IconTextView;
import com.xuyazhou.mynote.common.widget.WatcherEditText.BottomToolListener;
import com.xuyazhou.mynote.common.widget.WatcherEditText.NoteWatcherListener;
import com.xuyazhou.mynote.model.bean.FileType;
import com.xuyazhou.mynote.model.bean.NoteDeatils;
import com.xuyazhou.mynote.vp.adapter.NoteDeatilsListAdapter;
import com.xuyazhou.mynote.vp.adapter.NoteDeatilsListAdapter.EditViewCreateListener;
import com.xuyazhou.mynote.vp.base.BaseActivity;
import com.xuyazhou.mynote.vp.home.detail.file.FileBrowserActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NoteDetailActivity extends BaseActivity<NoteDetailPresenter> implements NoteDetailContract.View,
        NoteWatcherListener, BottomToolListener, EditViewCreateListener, EditUtlis.TextUndoListener, NoteDeatilsListAdapter.CheckListItemClickListener {

    @BindView(R.id.collection_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_layout)
    LinearLayout btnLayout;
    @BindView(R.id.detail_toolbar_layout)
    LinearLayout detailToolbarLayout;
    @Inject
    NoteDeatilsListAdapter adapter;
    @BindView(R.id.btn_undo)
    IconTextView btnUndo;
    @BindView(R.id.btn_redo)
    IconTextView btnRedo;
    @BindView(R.id.btn_time)
    IconTextView btnTime;
    @BindView(R.id.btn_paragraph)
    IconTextView btnParagraph;
    private ViewHolder viewHolder;

    private EditUtlis editUtlis;
    private EditUtlis noteeditUtlis;

    private int fristTime;


    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_note_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();


    }

    private void handleSendMsg() {

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            } else if (type.startsWith("image/")) {
                handleSendImage(intent);
            } else {
                handleOther(intent);
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("message/")) {
                handleSendText(intent);
            } else if (type.startsWith("*/")) {
                ArrayList<Uri> fileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                for (int i = 0; i < fileUris.size(); i++) {
                    FileType fileType = ImageUtils.getPath(this, fileUris.get(i));
                    if (fileType.isImage()) {
                        presenter.setPhotoData(fileType.getFilePath(), FileUtil.getFileName(fileType.getFilePath()));
                    } else {
                        presenter.addAttachment(fileType.getFilePath(), FileUtil.getFileName(fileType.getFilePath()));
                    }
                }

            }
        }
    }

    private void handleOther(Intent intent) {

        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String filePath = ImageUtils.getPath(this, uri).getFilePath();
        presenter.addAttachment(filePath, FileUtil.getFileName(filePath));
    }


    private void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String path = ImageUtils.getPath(this, imageUri).getFilePath();
        presenter.setPhotoData(path, FileUtil.getFileName(path));
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            NoteDeatils noteDeatils = presenter.getDeatils();
            noteDeatils.getNote().setContent(sharedText);
            adapter.setNoteCotent(noteDeatils);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.FILEBROWERBACK && resultCode == RESULT_OK) {
            presenter.addAttachment(data.getStringExtra("fileurl"), data.getStringExtra("fileName"));
        } else {
            //   getTakePhoto().onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initUI() {


        setSupportActionBar(toolbar);
        View titleView = LayoutInflater.from(this).inflate(R.layout.action_bar_view_note_detail, null);

        viewHolder = new ViewHolder(titleView);

        toolbar.addView(titleView);

        toolbar.setNavigationOnClickListener(v -> finish());
        viewHolder.title.setText("所有");

        viewHolder.icAttachment.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(NoteDetailActivity.this, viewHolder.icAttachment, 0, 0, R.style.OverflowMenu);
            popup.getMenuInflater()
                    .inflate(R.menu.menu_mynotes_attach, popup.getMenu());


            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.photo:
                        RxPaparazzo.single(NoteDetailActivity.this)
                                .usingGallery()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(response -> {
                                    // See response.resultCode() doc
                                    if (response.resultCode() != RESULT_OK) {

                                        return;
                                    }

                                    FileData fileData = response.data();
                                    presenter.setPhotoData(fileData.getFile().getAbsolutePath(), fileData.getFilename());

                                });
                        break;
                    case R.id.recorder:
                        break;
                    case R.id.handwrite:
                        break;
                    case R.id.graffiti:
                        break;
                    case R.id.attach:
                        ActivityUtil.moveToActivityForResult(NoteDetailActivity.this,
                                FileBrowserActivity.class, Constant.FILEBROWERBACK);
                        break;
                }
                return true;
            });

            popup.show();
        });

        viewHolder.icCamera.setOnClickListener(v -> {
            RxPaparazzo.single(NoteDetailActivity.this)
                    .usingCamera()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.resultCode() != RESULT_OK) {
                            return;
                        }
                        FileData fileData = response.data();
                        presenter.setPhotoData(fileData.getFile().getAbsolutePath(), fileData.getFilename());
                    });
        });

        adapter.setEditViewCreateListener(this);
        adapter.setHasStableIds(true);
        //  recyclerView.setItemAnimator(null);
        RecyclerViewUtils.setRecyclerView(this, recyclerView, adapter, 0, () -> {
        });

//        MyItemTouchHelperCallback callback = new MyItemTouchHelperCallback(adapter);
//
//        ItemTouchHelper helper = new ItemTouchHelper(callback);
//
//        helper.attachToRecyclerView(recyclerView);

        adapter.setCheckListItemClickListener(this);


    }


    @Override
    public void ShowData(NoteDeatils deatils) {
        adapter.setNoteDeatils(deatils);
    }

    @Override
    public void insertCheckData(NoteDeatils deatils) {
        adapter.checkInserted(deatils);
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void insertPhotoData(NoteDeatils deatils) {
        adapter.photoInserted(deatils);
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void insertAttachData(NoteDeatils deatils) {
        adapter.attchInserted(deatils);
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void delteItemTolist(NoteDeatils deatils, int position) {

        adapter.delteItem(deatils, position);
    }

    @Override
    public void showNoteDialog(NoteDeatils deatils) {


        DialogUtil.showNoteDialogMsg(this, deatils);
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.initNote();
        if (fristTime == 0) {
            handleSendMsg();
            fristTime++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mynotes_detail, menu);


        return true;
    }


    @Override
    public void onshow() {
        detailToolbarLayout.setVisibility(View.VISIBLE);

        btnParagraph.setEnabled(true);
        this.editUtlis = noteeditUtlis;
    }

    @Override
    public void onNoShow() {
        detailToolbarLayout.setVisibility(View.GONE);
    }

    @Override
    public void saveNoteContent(String content) {
        if (!content.equals("")) {
            presenter.setCurrentNoteContent(content);
            presenter.saveNote(content);
        }
    }


    @Override
    public void created() {
        adapter.getWatcherEditText().setNoteWatcherListener(this);
        if (getIntent().hasExtra("noteId")) {
            adapter.getWatcherEditText().setBottomToolListener(this, false);

        } else {
            adapter.getWatcherEditText().setBottomToolListener(this, false);


        }

        noteeditUtlis = new EditUtlis(adapter.getWatcherEditText());
        noteeditUtlis.setListener(this);
        if (adapter.getWatcherEditText().getText().length() == 0) {
            noteeditUtlis.setDefaultText("");
        }

        this.editUtlis = noteeditUtlis;
    }


    @OnClick(R.id.btn_keyboard_close)
    public void closeKeyboard() {
        KeyBoardUtils.closeKeyBoard(adapter.getWatcherEditText());
        detailToolbarLayout.setVisibility(View.GONE);
        adapter.getWatcherEditText().clearFocus();
    }

    @OnClick(R.id.btn_undo)
    public void undoClick() {
        editUtlis.undo();
    }

    @OnClick(R.id.btn_redo)
    public void redoClick() {
        editUtlis.redo();
    }

    @OnClick(R.id.btn_paragraph)
    public void paragraphClick() {
        if (adapter.getWatcherEditText().getParagraphState()) {
            adapter.getWatcherEditText().clearParagraph();
        } else {
            adapter.getWatcherEditText().addParagraph();
        }
    }

    @OnClick(R.id.btn_time)
    public void timeClick() {
        adapter.getWatcherEditText().addTimeText();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        presenter.cearEmpty();
        editUtlis.clearHistory();

    }

    @Override
    public void undo(boolean enable) {
        btnUndo.setEnabled(enable);
    }

    @Override
    public void redo(boolean enable) {
        btnRedo.setEnabled(enable);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.checklist:
                presenter.addCheckItem();
                break;
            case R.id.delete:
                if (presenter.getUserseting().is_deleted()) {
                    presenter.deleteNote();
                    this.finish();
                } else {
                    DialogUtil.showConfirmDeleteDialog(this, "删除确认", (dialog, which) -> {
                        presenter.deleteNote();
                        this.finish();
                    }, (buttonView, isChecked) -> {
                        presenter.setdeleteDialog(isChecked);
                    });
                }


                break;
            case R.id.detail:

                presenter.showDialogData();


                break;
            case R.id.send:

                DialogUtil.showSendChoice(NoteDetailActivity.this, presenter.getDeatils());


                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onclick(String id, String content,int position) {

        presenter.updateCheck(id, content,position);


    }

    @Override
    public void checkShow() {
        detailToolbarLayout.setVisibility(View.VISIBLE);
        btnParagraph.setEnabled(false);
    }

    @Override
    public void checkNoShow() {
        detailToolbarLayout.setVisibility(View.GONE);
        btnParagraph.setEnabled(true);
    }

    @Override
    public void checkUndo(boolean enable, EditUtlis editUtlis) {
        btnUndo.setEnabled(enable);
        this.editUtlis = editUtlis;
    }

    @Override
    public void checkRedo(boolean enable, EditUtlis editUtlis) {
        btnRedo.setEnabled(enable);
        this.editUtlis = editUtlis;
    }

    @Override
    public void addCheckItem() {
        presenter.addCheckItem();
    }

    @Override
    public void changeCheckToBottom(int position) {

        adapter.changeCheckToBottom(position, presenter.moveCheck());
    }

    @Override
    public void changeCheckToUp(int position) {
        adapter.changeCheckToUp(position, presenter.moveCheck());
    }


    @Override
    public void deleteCheck(int position) {
        presenter.delteCheck(position);
    }

    @Override
    public void deleteImage(int position) {
        presenter.delteImage(position);
    }

    @Override
    public void deleteAttach(int position) {
        presenter.delteAttach(position);
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.title_layout)
        LinearLayout titleLayout;
        @BindView(R.id.ic_attachment)
        IconTextView icAttachment;
        @BindView(R.id.ic_camera)
        IconTextView icCamera;
        @BindView(R.id.option_btns)
        LinearLayout optionBtns;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}