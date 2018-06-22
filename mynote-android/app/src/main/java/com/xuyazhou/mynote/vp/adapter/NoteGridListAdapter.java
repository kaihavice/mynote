package com.xuyazhou.mynote.vp.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.DensityUtil;
import com.xuyazhou.mynote.common.utils.ListUtils;
import com.xuyazhou.mynote.common.utils.StringUtil;
import com.xuyazhou.mynote.common.utils.SystemUtil;
import com.xuyazhou.mynote.common.utils.TimeUtils;
import com.xuyazhou.mynote.common.widget.IconTextView;
import com.xuyazhou.mynote.model.data.DataManager;
import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.model.db.CheckListItem;
import com.xuyazhou.mynote.model.db.Note;
import com.xuyazhou.mynote.vp.home.detail.NoteDetailActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xuyazhou.mynote.R.id.list_picture_layout;
import static com.xuyazhou.mynote.R.id.select_layout;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016/12/22
 */
public class NoteGridListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Note> noteList;
    private static final int GRID = 0;
    private static final int LIST = 1;
    private static final int GRIDIMAGE = 2;
    private static final int LISTONLYTITLE = 3;
    private String listType;
    private boolean selectType[];
    @Inject
    DataManager dataManager;

    @Inject
    public NoteGridListAdapter() {
        noteList = new ArrayList<>();
    }

    public void setData(ArrayList<Note> noteList, String listType) {
        this.listType = listType;
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public void setList(String list) {
        listType = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;


        if (viewType == GRID) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_note_text, parent, false);
            return new ItemGridViewHolder(view);
        }


        if (viewType == LIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note, parent, false);
            return new ItemListViewHolder(view);
        }

        if (viewType == LISTONLYTITLE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note_title, parent, false);
            return new ItemListTitleViewHolder(view);
        }

        if (viewType == GRIDIMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_note_picture, parent, false);
            return new ItemGirdImageViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == GRID) {
            ItemGridViewHolder itemGridViewHolder = (ItemGridViewHolder) holder;
            itemGridViewHolder.BindTo(noteList.get(position), dataManager.selectCheckList(
                    noteList.get(position).getNoteId()), dataManager.selectCheckListDone(noteList.get(position).getNoteId()));
        }

        if (getItemViewType(position) == LIST) {
            ItemListViewHolder itemListViewHolder = (ItemListViewHolder) holder;
            itemListViewHolder.BindTo(noteList.get(position),
                    dataManager.getFirstPhotoByNoteid(noteList.get(position).getNoteId()), dataManager.selectCheckList(
                            noteList.get(position).getNoteId()), dataManager.selectCheckListDone(noteList.get(position).getNoteId()));
        }

        if (getItemViewType(position) == LISTONLYTITLE) {
            ItemListTitleViewHolder itemListTitleViewHolder = (ItemListTitleViewHolder) holder;
            itemListTitleViewHolder.BindTo(noteList.get(position),
                    dataManager.getFirstPhotoByNoteid(noteList.get(position).getNoteId()));
        }

        if (getItemViewType(position) == GRIDIMAGE) {

            selectType = new boolean[noteList.size()];
            ItemGirdImageViewHolder itemGirdImageViewHolder = (ItemGirdImageViewHolder) holder;
            itemGirdImageViewHolder.BindTo(noteList.get(position),
                    dataManager.getFirstPhotoByNoteid(noteList.get(position).getNoteId()), selectType, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (listType) {
            case Constant.LIST:
                return LIST;
            case Constant.LISTONLYTITLE:
                return LISTONLYTITLE;
            default:
                if (noteList.get(position).getKind().equals(Constant.PHOTO) ||
                        noteList.get(position).getKind().equals(Constant.CHECKLISTIMAGE)
                        || noteList.get(position).getKind().equals(Constant.ATTACHIMAGE)) {
                    return GRIDIMAGE;
                } else {
                    return GRID;
                }
        }

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    static class ItemGridViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_title)
        TextView gridTitle;
        @BindView(R.id.grid_notes)
        TextView gridNotes;
        @BindView(R.id.grid_time)
        TextView gridTime;

        @BindView(R.id.grid_file)
        IconTextView gridFile;
        @BindView(R.id.bottom_layout)
        LinearLayout bottomLayout;
        @BindView(R.id.select_bg_view)
        IconTextView selectBgView;
        @BindView(R.id.grid_selected)
        IconTextView gridSelected;
        @BindView(R.id.grid_not_select)
        IconTextView gridNotSelect;
        @BindView(select_layout)
        RelativeLayout selectLayout;
        @BindView(R.id.grid_image_layout)
        RelativeLayout gridImageLayout;

        ItemGridViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void BindTo(Note note, ArrayList<CheckListItem> checkList, ArrayList<CheckListItem> checkListdone) {
            if (note.getContent().equals("")) {
                gridTitle.setText("无标题");
            } else {
                gridTitle.setText(StringUtil.getNoteTilte(note.getContent()));
            }

            if (checkList.size() > 0) {
                gridNotes.setText(StringUtil.appendCheckString(ListUtils.getCheckList(checkList, checkListdone)));
            } else {
                gridNotes.setText(note.getContent());
            }

            if (TimeUtils.isToday(note.getModifiedTime())) {
                gridTime.setText(TimeUtils.getFormatTime
                        (note.getModifiedTime() * 1000, "HH:mm"));
            } else {
                if (TimeUtils.isThisYear(note.getModifiedTime())) {
                    gridTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime() * 1000, "MM月dd日"));
                } else {
                    gridTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime() * 1000, "yyyy年MM月dd日"));
                }

            }

            if (note.isAttach()) {
                gridFile.setVisibility(View.VISIBLE);
            } else {
                gridFile.setVisibility(View.GONE);
            }


            RxView.clicks(gridImageLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        Intent intent = new Intent(gridFile.getContext(), NoteDetailActivity.class);
                        intent.putExtra("noteId", note.getNoteId());
                        gridImageLayout.getContext().startActivity(intent);
                    });
        }
    }

    static class ItemListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_not_selected)
        IconTextView listNotSelected;
        @BindView(R.id.list_selected)
        IconTextView listSelected;
        @BindView(R.id.left_layout)
        LinearLayout leftLayout;
        @BindView(R.id.list_title)
        TextView listTitle;
        @BindView(R.id.list_content)
        TextView listContent;
        @BindView(R.id.list_time)
        TextView listTime;

        @BindView(R.id.list_file)
        IconTextView listFile;
        @BindView(R.id.bottom_layout)
        LinearLayout bottomLayout;
        @BindView(R.id.list_picture)
        ImageView listPicture;
        @BindView(R.id.default_picture)
        IconTextView defaultPicture;
        @BindView(list_picture_layout)
        CardView listPictureLayout;
        @BindView(R.id.text_layout)
        RelativeLayout textLayout;
        @BindView(R.id.divider_bottom)
        ImageView dividerBottom;
        @BindView(R.id.list_item)
        RelativeLayout listItem;

        ItemListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void BindTo(Note note, AttachMent attachMent, ArrayList<CheckListItem> checkList, ArrayList<CheckListItem> checkListdone) {

            if (note.getContent().equals("")) {
                listTitle.setText("无标题");
            } else {
                listTitle.setText(StringUtil.getNoteTilte(note.getContent()));
            }

            if (checkList.size() > 0) {
                listContent.setText(StringUtil.appendCheckString(ListUtils.getCheckList(checkList, checkListdone)));
            } else {
                listContent.setText("");
            }
            if (TimeUtils.isToday(note.getModifiedTime())) {
                listTime.setText(TimeUtils.getFormatTime
                        (note.getModifiedTime()* 1000, "HH:mm"));
            } else {
                if (TimeUtils.isThisYear(note.getModifiedTime())) {
                    listTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime() * 1000, "MM月dd日"));
                } else {
                    listTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime() * 1000, "yyyy年MM月dd日"));
                }

            }

            if (note.isAttach()) {
                listFile.setVisibility(View.VISIBLE);
            } else {
                listFile.setVisibility(View.GONE);
            }


            int realWith = SystemUtil.getScreenWidth(listPicture.getContext());

            if (attachMent != null) {
                Glide.with(listPicture.getContext()).load(attachMent.getLocalPath() != null ?
                        attachMent.getLocalPath() : attachMent.getSpath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(DensityUtil.dp2px(listPicture.getContext(), 100),
                                DensityUtil.dp2px(listPicture.getContext(), 75))
                        .centerCrop()
                        .into(listPicture);
                listPictureLayout.setVisibility(View.VISIBLE);
            } else {
                listPictureLayout.setVisibility(View.GONE);
            }


            RxView.clicks(listItem).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        Intent intent = new Intent(listItem.getContext(), NoteDetailActivity.class);
                        intent.putExtra("noteId", note.getNoteId());
                        listItem.getContext().startActivity(intent);
                    });

            RxView.longClicks(listItem).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {

                    });
        }
    }


    static class ItemGirdImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_image)
        ImageView gridImage;
        @BindView(R.id.default_picture)
        IconTextView defaultPicture;
        @BindView(R.id.grid_title)
        TextView gridTitle;
        @BindView(R.id.grid_time)
        TextView gridTime;

        @BindView(R.id.grid_file)
        IconTextView gridFile;
        @BindView(R.id.select_bg_view)
        IconTextView selectBgView;
        @BindView(R.id.grid_selected)
        IconTextView gridSelected;
        @BindView(R.id.grid_not_select)
        IconTextView gridNotSelect;
        @BindView(select_layout)
        RelativeLayout selectLayout;
        @BindView(R.id.grid_image_layout)
        RelativeLayout gridImageLayout;


        ItemGirdImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void BindTo(Note note, AttachMent attachMent, boolean[] selectType, int position) {
            if (note.getContent().equals("")) {
                gridTitle.setText("无标题");
            } else {
                gridTitle.setText(StringUtil.getNoteTilte(note.getContent()));
                gridTitle.setTextColor(gridTitle.getResources().getColor(R.color.white));
            }


            if (TimeUtils.isToday(note.getModifiedTime())) {
                gridTime.setText(TimeUtils.getFormatTime
                        (note.getModifiedTime() * 1000, "HH:mm"));
            } else {
                if (TimeUtils.isThisYear(note.getModifiedTime())) {
                    gridTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime() * 1000, "MM月dd日"));
                } else {
                    gridTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime() * 1000, "yyyy年MM月dd日"));
                }

            }

            if (attachMent != null) {
                int realWith = SystemUtil.getScreenWidth(gridImage.getContext());

                Glide.with(gridImage.getContext()).load(attachMent.getLocalPath() != null ?
                        attachMent.getLocalPath() : attachMent.getSpath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(realWith / 2 - DensityUtil.dp2px(gridImage.getContext(), 30),
                                DensityUtil.dp2px(gridImage.getContext(), 200))
                        .centerCrop()
                        .into(gridImage);


            }

            if (note.isAttach()) {
                gridFile.setVisibility(View.VISIBLE);
            } else {
                gridFile.setVisibility(View.GONE);
            }


            RxView.clicks(gridImageLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        Intent intent = new Intent(gridFile.getContext(), NoteDetailActivity.class);
                        intent.putExtra("noteId", note.getNoteId());
                        gridImageLayout.getContext().startActivity(intent);
                    });

            RxView.longClicks(gridImageLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        if (selectType[position]) {
                            selectLayout.setVisibility(View.GONE);
                            gridNotSelect.setVisibility(View.VISIBLE);

                            gridSelected.setVisibility(View.GONE);
                            selectType[position] = false;
                        } else {
                            selectLayout.setVisibility(View.VISIBLE);
                            gridNotSelect.setVisibility(View.VISIBLE);
                            gridSelected.setVisibility(View.GONE);

                            selectType[position] = true;
                        }
                    });


            RxView.clicks(gridNotSelect).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        gridNotSelect.setVisibility(View.GONE);
                        gridSelected.setVisibility(View.VISIBLE);
                    });
            RxView.clicks(gridSelected).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        gridNotSelect.setVisibility(View.VISIBLE);
                        gridSelected.setVisibility(View.GONE);
                    });

        }
    }

    static class ItemListTitleViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.list_not_selected)
        IconTextView listNotSelected;
        @BindView(R.id.list_selected)
        IconTextView listSelected;
        @BindView(R.id.left_layout)
        LinearLayout leftLayout;
        @BindView(R.id.list_title)
        TextView listTitle;
        @BindView(R.id.list_time)
        TextView listTime;
        @BindView(R.id.list_reminder)
        IconTextView listReminder;
        @BindView(R.id.list_radio)
        IconTextView listRadio;
        @BindView(R.id.list_video)
        IconTextView listVideo;
        @BindView(R.id.list_file)
        IconTextView listFile;
        @BindView(R.id.bottom_layout)
        LinearLayout bottomLayout;
        @BindView(R.id.list_picture)
        ImageView listPicture;
        @BindView(R.id.default_picture)
        IconTextView defaultPicture;
        @BindView(list_picture_layout)
        CardView listPictureLayout;
        @BindView(R.id.text_layout)
        RelativeLayout textLayout;
        @BindView(R.id.divider_bottom)
        ImageView dividerBottom;
        @BindView(R.id.list_item)
        RelativeLayout listItem;

        ItemListTitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void BindTo(Note note, AttachMent attachMent) {
            if (note.getContent().equals("")) {
                listTitle.setText("无标题");
            } else {
                listTitle.setText(StringUtil.getNoteTilte(note.getContent()));
            }

            if (TimeUtils.isToday(note.getModifiedTime())) {
                listTime.setText(TimeUtils.getFormatTime
                        (note.getModifiedTime()* 1000, "HH:mm"));
            } else {
                if (TimeUtils.isThisYear(note.getModifiedTime())) {
                    listTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime()* 1000, "MM月dd日"));
                } else {
                    listTime.setText(TimeUtils.getFormatTime
                            (note.getModifiedTime()* 1000, "yyyy年MM月dd日"));
                }

            }

            if (note.isAttach()) {
                listFile.setVisibility(View.VISIBLE);
            } else {
                listFile.setVisibility(View.GONE);
            }

            if (attachMent != null) {

                Glide.with(listPicture.getContext()).load(attachMent.getLocalPath() != null ?
                        attachMent.getLocalPath() : attachMent.getSpath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .override(realWith - DensityUtil.dp2px(listPicture.getContext(), 32),
//                            DensityUtil.dp2px(listPicture.getContext(), 196))
                        .centerCrop()
                        .into(listPicture);

                listPictureLayout.setVisibility(View.VISIBLE);
            } else {
                listPictureLayout.setVisibility(View.GONE);
            }

            RxView.clicks(listItem).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(oid -> {
                        Intent intent = new Intent(listItem.getContext(), NoteDetailActivity.class);
                        intent.putExtra("noteId", note.getNoteId());
                        listItem.getContext().startActivity(intent);
                    });
        }
    }

}
