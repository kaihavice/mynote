package com.xuyazhou.mynote.vp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.xuyazhou.mynote.R;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FileBrowerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private File[] currentFiles;
    private File currentParent;
    private FileItemClickLisenter fileItemClickLisenter;

    public void setDataList(File[] currentFiles) {

        this.currentFiles = currentFiles;
        notifyDataSetChanged();

    }

    public void setFileItemClickLisenter(FileItemClickLisenter fileItemClickLisenter) {
        this.fileItemClickLisenter = fileItemClickLisenter;
    }

    @Inject
    public FileBrowerAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;


        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);

        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        itemViewHolder.bindTo(currentFiles[position], currentParent, fileItemClickLisenter);


    }


    @Override
    public int getItemCount() {

        return currentFiles != null ? currentFiles.length : 0;


    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.file_icon)
        ImageView fileIcon;
        @BindView(R.id.file_name)
        TextView fileName;
        @BindView(R.id.file_checkbox)
        CheckBox fileCheckbox;
        @BindView(R.id.right_layout)
        LinearLayout rightLayout;
        @BindView(R.id.list_item)
        RelativeLayout listItem;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(File currentFile, File currentParent, FileItemClickLisenter fileItemClickLisenter) {

            if (currentFile.isDirectory()) {
                fileIcon.setImageResource(R.mipmap.ic_browse_folder);
            } else {
                fileIcon.setImageResource(R.mipmap.ic_browse_file);
            }
            fileName.setText(currentFile.getName());

            RxView.clicks(listItem).subscribe(aVoid -> {

                if (currentFile.isFile()) {
                    fileItemClickLisenter.fileClick(currentFile);

                } else {
                    fileItemClickLisenter.directoryClick(currentFile);
                }


            });


        }
    }

    public interface FileItemClickLisenter {
        void fileClick(File file);

        void directoryClick(File file);
    }

}
