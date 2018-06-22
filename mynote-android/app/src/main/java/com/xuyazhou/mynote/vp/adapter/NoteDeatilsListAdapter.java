package com.xuyazhou.mynote.vp.adapter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.EditUtlis;
import com.xuyazhou.mynote.common.utils.FileSizeUtils;
import com.xuyazhou.mynote.common.widget.IconTextView;
import com.xuyazhou.mynote.common.widget.IconTextViewNew;
import com.xuyazhou.mynote.common.widget.OnItemDragbackListener;
import com.xuyazhou.mynote.common.widget.WatcherEditText;
import com.xuyazhou.mynote.model.bean.NoteDeatils;
import com.xuyazhou.mynote.model.data.DataManager;
import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.model.db.CheckListItem;
import com.xuyazhou.mynote.model.db.Note;
import com.xuyazhou.mynote.vp.home.detail.file.FileBrowserActivity;
import com.xuyazhou.mynote.vp.home.detail.image.ImageExpandActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016/12/22
 */
public class NoteDeatilsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemDragbackListener {

    private static final int TYPE_TXT = 0;
    private static final int TYPE_CHECKLIST = 1;
    private static final int TYPE_IMAGE = 2;
    private static final int TYPE_ATTACH = 3;

    private boolean isNew;
    private boolean isFous;
    private boolean showType[];
    private boolean checkListType[];
    private boolean imageType[];
    private boolean fileType[];
    private boolean isRemoved;


    private NoteDeatils noteDeatils;
    private WatcherEditText watcherEditText;
    private EditViewCreateListener editViewCreateListener;
    private CheckListItemClickListener checkListItemClickListener;
    @Inject
    DataManager dataManager;

    public void setCheckListItemClickListener(CheckListItemClickListener checkListItemClickListener) {
        this.checkListItemClickListener = checkListItemClickListener;
    }


    public void setEditViewCreateListener(EditViewCreateListener editViewCreateListener) {
        this.editViewCreateListener = editViewCreateListener;
    }


    @Inject
    public NoteDeatilsListAdapter() {
        this.noteDeatils = new NoteDeatils();
        noteDeatils.setAttachmentList(new ArrayList<>());
        noteDeatils.setImageList(new ArrayList<>());
        noteDeatils.setCheckList(new ArrayList<>());
    }

    public void setNoteDeatils(NoteDeatils noteDeatils) {
        this.noteDeatils = noteDeatils;
        restBoolean();
        notifyDataSetChanged();
    }

    public WatcherEditText getWatcherEditText() {
        return watcherEditText;
    }

    public void restBoolean() {
        showType = new boolean[noteDeatils.getCheckList().size()];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;


        if (viewType == TYPE_TXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item_content, parent, false);
            return new ItemTextViewHolder(view);
        }


        if (viewType == TYPE_CHECKLIST) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list_checklist, parent, false);
            return new ItemCheckListViewHolder(view);
        }

        if (viewType == TYPE_IMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list_image, parent, false);

            return new ItemImageViewHolder(view);
        }

        if (viewType == TYPE_ATTACH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list_attach, parent, false);

            return new ItemAttachViewHolder(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_TXT:
                ItemTextViewHolder textViewHolder = (ItemTextViewHolder) holder;
                textViewHolder.bindTo(noteDeatils.getNote(), noteDeatils.getCheckList().size() > 0);

                this.watcherEditText = textViewHolder.noteEditor;
                if (editViewCreateListener != null) {
                    editViewCreateListener.created();
                }
                break;
            case TYPE_CHECKLIST:

                ItemCheckListViewHolder checkListViewHolder = (ItemCheckListViewHolder) holder;

                if (noteDeatils.getCheckList().size() > 0) {
                    checkListType = new boolean[noteDeatils.getCheckList().size()];

                }


                checkListViewHolder.bindTo(noteDeatils.getCheckList().get(position - 1),
                        checkListItemClickListener, position - 1, showType, isFous, checkListType);

//                checkListViewHolder.editText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public.xml void onClick(View v) {
//
//                        if (isFous) {
//
//                            checkListViewHolder.editText.setFocusable(true);
//                            checkListItemClickListener.checkShow();
//                            checkListViewHolder.editText.getFocus();
////                            restBoolean();
////                            showType[position - 1] = true;
////                            notifyDataSetChanged();
//                        } else {
//                            isFous = true;
//                            checkListItemClickListener.checkNoShow();
//                            checkListViewHolder.editText.setFocusable(false);
//                            checkListViewHolder.editText.clearFocus();
//                        }
//                    }
//                });


//                if (isFous) {
//
//                            checkListViewHolder.editText.setFocusable(true);
//                            checkListItemClickListener.checkShow();
//                            checkListViewHolder.editText.getFocus();
////                            restBoolean();
////                            showType[position - 1] = true;
////                            notifyDataSetChanged();
////                        } else {
//                            isFous = true;
//                            checkListItemClickListener.checkNoShow();
//                            checkListViewHolder.editText.setFocusable(false);
//                            checkListViewHolder.editText.clearFocus();
////                        }

                checkListViewHolder.editText.setCheckBottomToolListener(new WatcherEditText.BottomToolListener() {
                    @Override
                    public void onshow() {
                        checkListItemClickListener.checkShow();
                        restBoolean();
                        if (showType.length > 0 && position - 1 >= 0 && !isRemoved) {
                            showType[position - 1] = true;
                        }

                        specialUpdate(position);

                        isRemoved = false;
                    }

                    @Override
                    public void onNoShow() {
                        checkListItemClickListener.checkNoShow();

                    }
                }, isFous);


                if (isNew) {
                    if (watcherEditText != null) {
                        watcherEditText.clearFocus();
                    }
                    isNew = false;
                    checkListViewHolder.editText.getFocus();
                }


                break;
            case TYPE_IMAGE:

                if (noteDeatils.getImageList().size() > 0) {
                    imageType = new boolean[noteDeatils.getImageList().size()];

                }
                ItemImageViewHolder itemImageViewHolder = (ItemImageViewHolder) holder;
                itemImageViewHolder.bindTo(noteDeatils.getImageList().get(position - noteDeatils.
                        getCheckList().size() - 1), noteDeatils.getImageList(), imageType, position -
                        noteDeatils.getCheckList().size() - 1, checkListItemClickListener);
                break;
            case TYPE_ATTACH:

                if (noteDeatils.getAttachmentList().size() > 0) {
                    fileType = new boolean[noteDeatils.getAttachmentList().size()];

                }

                ItemAttachViewHolder itemAttachViewHoler = (ItemAttachViewHolder) holder;
                itemAttachViewHoler.bindTo(noteDeatils.getAttachmentList().get(position -
                        noteDeatils.getCheckList().size() - noteDeatils.getImageList().size() - 1), fileType, position -
                        noteDeatils.getCheckList().size() - noteDeatils.getImageList().size() - 1, checkListItemClickListener);
                break;
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void specialUpdate(int postion) {
        Handler handler = new Handler();
        final Runnable r = this::notifyDataSetChanged;
        handler.post(r);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TXT;
        } else if (0 < position && position <= noteDeatils.getCheckList().size()) {
            return TYPE_CHECKLIST;
        } else if (noteDeatils.getCheckList().size() <= position && position
                <= noteDeatils.getImageList().size() + noteDeatils.getCheckList().size()) {
            return TYPE_IMAGE;
        } else {
            return TYPE_ATTACH;
        }
    }


    @Override
    public int getItemCount() {
        return noteDeatils.getAttachmentList().size()
                + noteDeatils.getCheckList().size()
                + noteDeatils.getImageList().size() + 1;
    }


    public void checkInserted(NoteDeatils deatils) {
        this.noteDeatils = deatils;
        this.isNew = true;
        watcherEditText.clearFocus();
        restBoolean();
        notifyItemInserted(deatils.getCheckList().size());

    }

    public void photoInserted(NoteDeatils deatils) {
        this.noteDeatils = deatils;
        notifyItemInserted(deatils.getCheckList().size() + 1);
        notifyItemRangeChanged(deatils.getCheckList().size() + 1, noteDeatils.getCheckList().size() + 1 + noteDeatils.getImageList().size());
    }

    public void delteItem(NoteDeatils deatils, int position) {
        this.noteDeatils = deatils;
        isRemoved = true;
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, noteDeatils.getCheckList().size() + 1);
    }


    public void attchInserted(NoteDeatils deatils) {
        this.noteDeatils = deatils;
        notifyItemInserted(deatils.getCheckList().size() + deatils.getImageList().size() +
                deatils.getAttachmentList().size());
    }

    public void changeCheckToBottom(int position, NoteDeatils deatils) {
        this.noteDeatils = deatils;
        isFous = false;
        notifyItemMoved(position + 1, noteDeatils.getCheckList().size());
        notifyItemRangeChanged(position + 1, noteDeatils.getCheckList().size() - position + 1);

    }

    public void changeCheckToUp(int position, NoteDeatils deatils) {
        this.noteDeatils = deatils;
        isFous = false;
        notifyItemMoved(position + 1, noteDeatils.getCheckList().size() - noteDeatils.getCheckListDone().size());
        notifyItemRangeChanged(1, noteDeatils.getCheckList().size() + 1);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        long formSortder = noteDeatils.getCheckList().get(fromPosition - 1).getSortOder();
//        noteDeatils.getCheckList().get(fromPosition - 1).setSortOder(noteDeatils.getCheckList().get(toPosition - 1).getSortOder());
//        noteDeatils.getCheckList().get(fromPosition - 1).update();
//        noteDeatils.getCheckList().get(toPosition - 1).setSortOder(formSortder);
//        noteDeatils.getCheckList().get(toPosition - 1).update();
//        noteDeatils.setCheckList(ListUtils.getCheckList(dataManager.selectCheckList(noteDeatils.getNote().get_id()),
//                dataManager.selectCheckListDone(noteDeatils.getNote().get_id())));

//        if(fromPosition < toPosition) {
//            Collections.swap(noteDeatils.getCheckList(), fromPosition - 1, toPosition );
//        }else {
//            Collections.swap(noteDeatils.getCheckList(), fromPosition - 1, toPosition - 2);
//        }

        Collections.swap(noteDeatils.getCheckList(), fromPosition - 1, toPosition - 1);
        notifyItemMoved(fromPosition, toPosition);
//        notifyDataSetChanged();
    }

    @Override
    public void onSwipe(int position) {
        noteDeatils.getCheckList().remove(position);
        notifyItemRemoved(position);
    }

    public void setNoteCotent(NoteDeatils noteDeatils) {
        this.noteDeatils = noteDeatils;
        notifyItemChanged(0);
    }


    static class ItemTextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.note_editor)
        WatcherEditText noteEditor;
        @BindView(R.id.list_item_content)
        LinearLayout listItemContent;
        @BindView(R.id.divider)
        ImageView divider;

        ItemTextViewHolder(View view) {

            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(Note note, boolean ishasCheck) {
            if (note != null && !note.getContent().equals(""))
                noteEditor.setText(note.getContent());

            if (ishasCheck) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.GONE);
            }

        }
    }

    public static class ItemCheckListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check_icon)
        IconTextViewNew checkIcon;
        @BindView(R.id.left_layout)
        LinearLayout leftLayout;
        @BindView(R.id.edit_text)
        WatcherEditText editText;
        @BindView(R.id.drag_view)
        IconTextView dragView;
        @BindView(R.id.delete_btn)
        IconTextView deleteBtn;
        @BindView(R.id.right_layout)
        LinearLayout rightLayout;
        @BindView(R.id.divider)
        ImageView divider;
        @BindView(R.id.detail_list_checklist_item)
        RelativeLayout detailListChecklistItem;

        ItemCheckListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(CheckListItem checkListItem, CheckListItemClickListener checkListItemClickListener,
                           int position, boolean[] showType, boolean isFous, boolean[] checkListType) {


            editText.clearTextChangedListeners();
            editText.setText(checkListItem.getTitle());
            editText.setSelection(checkListItem.getTitle().length());

            if (position == 0 && checkListItem.getTitle().equals("")) {
                editText.setHint("按换行键添加更多");
            } else {
                editText.setHint("");
            }

            editText.setNoteWatcherListener(content -> {

                checkListItemClickListener.onclick(checkListItem.getSid(), content,position);
            });


            EditUtlis checkeditUtlis;
            checkeditUtlis = new EditUtlis(editText);

            checkeditUtlis.setListener(new EditUtlis.TextUndoListener() {
                @Override
                public void undo(boolean enable) {
                    checkListItemClickListener.checkUndo(enable, checkeditUtlis);
                }

                @Override
                public void redo(boolean enable) {
                    checkListItemClickListener.checkRedo(enable, checkeditUtlis);
                }
            });


            editText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() &&
                        KeyEvent.ACTION_DOWN == event.getAction())) {
                    checkListItemClickListener.addCheckItem();

                    return true;
                }
                return false;
            });

//            if (editText.getText().length() == 0) {
//                checkeditUtlis.setDefaultText("");
//            }


            RxView.clicks(checkIcon).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        long currentTime = System.currentTimeMillis() / 1000;
                        if (checkListItem.isChecked()) {

                            checkIcon.setText(checkIcon.getResources().getString(R.string.ic_svg_complete_check_off));
                            editText.setAlpha(1.0F);
                            checkIcon.setAlpha(1.0F);
                            checkListItem.setChecked(false);
                            checkListItem.setModifiedTime(currentTime);
                            checkListItem.update();
                            checkListItemClickListener.changeCheckToUp(position);

                        } else {
                            checkIcon.setText(checkIcon.getResources().getString(R.string.ic_svg_complete_check_on));
                            editText.setAlpha(0.5F);
                            checkIcon.setAlpha(0.5F);
                            checkListItem.setChecked(true);
                            checkListItem.setModifiedTime(currentTime);
                            checkListItem.update();
                            checkListItemClickListener.changeCheckToBottom(position);


                        }
                    });


            RxView.clicks(deleteBtn).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        checkListItemClickListener.deleteCheck(position);
                    });

            if (showType[position]) {
                dragView.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                dragView.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.GONE);
            }


            if (checkListItem.isChecked()) {
                checkIcon.setText(checkIcon.getResources().getString(R.string.ic_svg_complete_check_on));
                editText.setAlpha(0.5F);
                checkIcon.setAlpha(0.5F);

//                dragView.setVisibility(View.GONE);
//                deleteBtn.setVisibility(View.VISIBLE);
            } else {

                checkIcon.setText(checkIcon.getResources().getString(R.string.ic_svg_complete_check_off));
                editText.setAlpha(1.0F);
                checkIcon.setAlpha(1.0F);
//                dragView.setVisibility(View.GONE);
//                deleteBtn.setVisibility(View.GONE);
            }

        }
    }


    static class ItemImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.default_picture)
        IconTextView defaultPicture;
        @BindView(R.id.icon_error)
        IconTextView iconError;
        @BindView(R.id.btn_save)
        IconTextView btnSave;
        @BindView(R.id.btn_delete)
        IconTextView btnDelete;
        @BindView(R.id.file_name)
        TextView fileName;
        @BindView(R.id.file_size)
        TextView fileSize;
        @BindView(R.id.edit_layout)
        RelativeLayout editLayout;

        ItemImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(AttachMent attachMent, ArrayList<AttachMent> imageList, boolean[] imageType, int position, CheckListItemClickListener checkListItemClickListener) {

            if (attachMent != null) {

                Glide.with(image.getContext()).load(attachMent.getLocalPath() != null
                        ? attachMent.getLocalPath() : attachMent.getSpath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .override(realWith - DensityUtil.dp2px(image.getContext(), 32),
//                            DensityUtil.dp2px(image.getContext(), 196))
                        .centerCrop()
                        .into(image);

                fileName.setText(attachMent.getFileName());
                fileSize.setText("大小：" + FileSizeUtils.getAutoFileOrFilesSize(attachMent.getLocalPath()));
            }

            RxView.longClicks(image).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
                if (imageType[position]) {
                    editLayout.setVisibility(View.GONE);
                    imageType[position] = false;
                } else {
                    editLayout.setVisibility(View.VISIBLE);

                    imageType[position] = true;
                }

            });

            RxView.clicks(image).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        Intent intent = new Intent(image.getContext(), ImageExpandActivity.class);
                        intent.putExtra("noteId", attachMent.getNoteId());
                        intent.putExtra("position", imageList.indexOf(attachMent));
                        image.getContext().startActivity(intent);
                    });

            RxView.clicks(btnSave).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        Intent intent = new Intent(btnSave.getContext(), FileBrowserActivity.class);
                        intent.putExtra("isLocalFile", attachMent.getLocalPath() != null);

                        if (attachMent.getLocalPath() != null) {
                            intent.putExtra("path", attachMent.getLocalPath());
                        } else {
                            intent.putExtra("path", attachMent.getSpath());
                        }

                        image.getContext().startActivity(intent);
                    });

            RxView.clicks(btnDelete).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        checkListItemClickListener.deleteImage(position);
                    });

        }
    }


    static class ItemAttachViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.file_image)
        ImageView fileImage;
        @BindView(R.id.file_name)
        TextView fileName;
        @BindView(R.id.file_size)
        TextView fileSize;
        @BindView(R.id.icon_error)
        IconTextView iconError;
        @BindView(R.id.btn_save)
        IconTextView btnSave;
        @BindView(R.id.btn_delete)
        IconTextView btnDelete;
        @BindView(R.id.edit_btn_layout)
        LinearLayout editBtnLayout;
        @BindView(R.id.info_layout)
        LinearLayout infoLayout;
        @BindView(R.id.attachment_file_name_layout)
        RelativeLayout attachmentFileNameLayout;
        @BindView(R.id.attach_item)
        RelativeLayout attachItem;
        @BindView(R.id.play_btn)
        IconTextView playBtn;
        @BindView(R.id.play_seekbar)
        SeekBar playSeekbar;
        @BindView(R.id.play_time)
        TextView playTime;
        @BindView(R.id.background)
        CardView background;

        ItemAttachViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(AttachMent attachMent, boolean[] fileType, int position, CheckListItemClickListener checkListItemClickListener) {

            fileName.setText(attachMent.getFileName());
            fileSize.setText("大小：" + FileSizeUtils.getAutoFileOrFilesSize(attachMent.getLocalPath()));

            RxView.longClicks(attachItem).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
                if (fileType[position]) {
                    editBtnLayout.setVisibility(View.GONE);
                    background.setBackgroundResource(R.color.bg_color_attachment_other_light);
                    fileType[position] = false;
                } else {
                    editBtnLayout.setVisibility(View.VISIBLE);
                    background.setBackgroundResource(R.color.bg_color_attach_edit_light);
                    fileType[position] = true;
                }

            });

            RxView.clicks(btnSave).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        Intent intent = new Intent(btnSave.getContext(), FileBrowserActivity.class);
                        intent.putExtra("isLocalFile", attachMent.getLocalPath() != null);
                        if (attachMent.getLocalPath() != null) {
                            intent.putExtra("path", attachMent.getLocalPath());
                        } else {
                            intent.putExtra("path", attachMent.getSpath());
                        }

                        btnSave.getContext().startActivity(intent);
                    });

            RxView.clicks(btnDelete).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        checkListItemClickListener.deleteAttach(position);
                    });
        }
    }


    public interface EditViewCreateListener {
        void created();
    }

    public interface CheckListItemClickListener {

        void onclick(String id, String content,int position);

        void checkShow();

        void checkNoShow();

        void checkUndo(boolean enable, EditUtlis editUtlis);

        void checkRedo(boolean enable, EditUtlis editUtlis);

        void addCheckItem();

        void changeCheckToBottom(int position);

        void changeCheckToUp(int position);

        void deleteCheck(int position);

        void deleteImage(int position);

        void deleteAttach(int position);
    }

}
