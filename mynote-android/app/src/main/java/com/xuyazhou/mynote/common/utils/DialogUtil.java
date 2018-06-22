package com.xuyazhou.mynote.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.model.bean.NoteDeatils;
import com.xuyazhou.mynote.vp.adapter.ListPopupAdater;

/**
 * Created by mayuhan on 14/12/13.
 */
public class DialogUtil {
    public static void showSingleChoiceListDialog(Context context,
                                                  String title, String[] items,
                                                  DialogInterface.OnClickListener onItemClickCallBack) {
        Dialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setItems(items, onItemClickCallBack).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showConfirmDialog(Context context,
                                         String msg, DialogInterface.OnClickListener positive,
                                         DialogInterface.OnClickListener negative) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(msg).setPositiveButton("确定", positive)
                .setNegativeButton("放弃", negative).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showConfirmDeleteDialog(Context context,
                                               String title,
                                               DialogInterface.OnClickListener positive,
                                               CompoundButton.OnCheckedChangeListener CheckedChangeListener) {

        View view = LayoutInflater.from(context).inflate(R.layout.delete_confirm_dialog, null);

        CheckBox delete = (CheckBox) view.findViewById(R.id.delete_confirm_checkbox);
        delete.setOnCheckedChangeListener(CheckedChangeListener);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title).setPositiveButton("删除", positive)
                .setView(view)
                .setNegativeButton("取消", (dialog, which) -> {
                }).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showNoteDialogMsg(Context context, NoteDeatils deatils) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setTitle("详细信息");

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_note_detail, null);
        builder.setView(view);

        TextView wordNumber = (TextView) view.findViewById(R.id.word_number);
        TextView lastMofitime = (TextView) view.findViewById(R.id.modified);
        TextView createTime = (TextView) view.findViewById(R.id.created);

        int checkLength = 0;
        for (int i = 0; i < deatils.getCheckList().size(); i++) {
            checkLength = checkLength + deatils.getCheckList().get(i).getTitle().length();
        }

        wordNumber.setText(deatils.getNote().getContent().length() + checkLength + "");
        lastMofitime.setText(TimeUtils.getFormatTime(deatils.getNote().getModifiedTime()* 1000, "yyyy/MM/dd HH:mm"));
        createTime.setText(TimeUtils.getFormatTime(deatils.getNote().getCreateTime()* 1000, "yyyy/MM/dd HH:mm"));

        builder.setPositiveButton("确定", (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showSendChoice(Context context, NoteDeatils noteDeatils) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_send, null);
        builder.setView(view);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ListPopupAdater());

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, StringUtil.appendCheckText(
                        noteDeatils.getNote().getContent(),
                        ListUtils.getCheckList(noteDeatils.getCheckList(),
                                noteDeatils.getCheckListDone())));
                sendIntent.setType("text/plain");
                // startActivity(sendIntent);
                context.startActivity(Intent.createChooser(sendIntent, "分享到"));

            } else {

            }
            alertDialog.dismiss();
        });
    }

    public static void showConfirmDialog(Context context, String title,
                                         String msg, DialogInterface.OnClickListener positive,
                                         DialogInterface.OnClickListener negative) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg).setPositiveButton("确定", positive)
                .setNegativeButton("放弃", negative).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showConfirmDialog(Context context, String title,
                                         String msg, String positiveString, DialogInterface.OnClickListener positive) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg).setPositiveButton(positiveString, positive)
                .setNegativeButton("取消", (dialog, which) -> {
                }).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showSingleConfirmDialog(Context context, String title,
                                               String msg, DialogInterface.OnClickListener positive
    ) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(msg).setNegativeButton("确定", positive).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    /**
     * 提示弹窗
     *
     * @param activity
     * @param msg
     */
    public static void showDialogHint(final Activity activity, String msg,
                                      Boolean cancelable) {
        // wehax:howe 11-23:检查activity是否被finish
        if (activity.isFinishing()) {
            return;
        }
        new AlertDialog.Builder(activity).setTitle("温馨提示").setMessage(msg)
                .setCancelable(cancelable)
                .setPositiveButton("确定", (arg0, arg1) -> activity.finish()).show();
    }

    public static void showDialogHintId(final Activity activity,
                                        int resource_id, Boolean cancelable) {
        if (activity.isFinishing()) {
            return;
        }
        new AlertDialog.Builder(activity).setTitle("温馨提示")
                .setMessage(activity.getString(resource_id))
                .setCancelable(cancelable)
                .setPositiveButton("确定", (arg0, arg1) -> activity.finish()).show();
    }


    /**
     * 提示弹窗-没有实现确定和取消的事件
     *
     * @param activity
     * @param msg
     * @param cancelable true点击窗口外部可关闭窗口
     * @return
     */
    public static AlertDialog.Builder showDialogHintTow(
            final Activity activity, String msg, Boolean cancelable,
            String title) {

        return new AlertDialog.Builder(activity).setTitle(title)
                .setMessage(msg).setCancelable(cancelable);
    }


}
