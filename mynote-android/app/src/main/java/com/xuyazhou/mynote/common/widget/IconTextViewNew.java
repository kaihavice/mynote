package com.xuyazhou.mynote.common.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016/12/20
 */
public class IconTextViewNew extends AppCompatTextView {

    static Typeface sTypeface;

    public IconTextViewNew(Context paramContext)
    {
        super(paramContext);
        initView();
    }

    public IconTextViewNew(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        initView();
    }

    public IconTextViewNew(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        initView();
    }

    private void initView()
    {
        if (sTypeface == null) {
            sTypeface = Typeface.createFromAsset(getContext().getAssets(), "icons.ttf");
        }
        setTypeface(sTypeface);
    }

//    protected void onDraw(Canvas paramCanvas)
//    {
//        super.onDraw(paramCanvas);
//        if (isEnabled())
//        {
//            setAlpha(1.0F);
//            return;
//        }
//        setAlpha(0.5F);
//    }
}
