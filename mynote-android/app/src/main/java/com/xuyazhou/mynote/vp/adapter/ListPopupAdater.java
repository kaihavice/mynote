package com.xuyazhou.mynote.vp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.widget.IconTextView;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/2/23
 */
public class ListPopupAdater extends BaseAdapter {

    private final String textList[] = {"以文本形式发送", "以图片形式发送"};



    @Override
    public int getCount() {
        return textList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_send_type_item, null, false);

        TextView textView = (TextView) convertView.findViewById(R.id.text);
        IconTextView iconTextView = (IconTextView) convertView.findViewById(R.id.icon);

        if(position == 0){
            textView.setText(textList[0]);
            iconTextView.setText(R.string.ic_svg_share_text);
        }else {
            textView.setText(textList[1]);
            iconTextView.setText(R.string.ic_svg_share_image);
        }

        return convertView;
    }
}
