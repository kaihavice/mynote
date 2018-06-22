package com.xuyazhou.mynote.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.xuyazhou.mynote.R;


/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/2/10
 */
public class IconFontPreference extends Preference {

    private String valve = null;
    private IconTextView iconTextView;


    public IconFontPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView(context, attrs);
    }

    public IconFontPreference(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }


    private void setView(Context paramContext, AttributeSet paramAttributeSets) {

        TypedArray paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSets, R.styleable.mypreference);

//        int i = paramAttributeSet.getResourceId(0, 0);
//        if (i != 0) {
//            this.valve = paramContext.getResources().getString(i);
//        }
        valve = paramAttributeSet.getString(R.styleable.mypreference_icon_text);

        paramAttributeSet.recycle();
    }

    @Override
    protected void onBindView(View paramView) {
        super.onBindView(paramView);

        iconTextView = (IconTextView) paramView.findViewById(R.id.icon_text);

        if (this.iconTextView != null) {
            if (TextUtils.isEmpty(this.valve)) {
                this.iconTextView.setVisibility(View.GONE);
            }
        } else {
            return;
        }
        this.iconTextView.setVisibility(View.VISIBLE);
        this.iconTextView.setText(this.valve);
    }


//    @Override
//    protected View onCreateView(ViewGroup paramViewGroup) {
//        super.onCreateView(paramViewGroup);
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.preference_screen_icon_font, paramViewGroup, false);
//        this.iconTextView = ((IconTextView) view.findViewById(R.id.icon_text));
//        return view;
//    }
}
