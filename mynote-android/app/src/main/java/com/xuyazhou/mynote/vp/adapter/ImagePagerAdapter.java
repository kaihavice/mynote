package com.xuyazhou.mynote.vp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.xuyazhou.mynote.model.db.AttachMent;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/1/16
 */
public class ImagePagerAdapter extends PagerAdapter {

    private ArrayList<AttachMent> imageList;

    @Inject
    public ImagePagerAdapter() {
        imageList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        PhotoView image = new PhotoView(container.getContext());


        if (imageList.get(position).getLocalPath() != null) {

            Glide.with(image.getContext()).load(new File(imageList.get(position).getLocalPath()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .override(realWith - DensityUtil.dp2px(image.getContext(), 32),
//                            DensityUtil.dp2px(image.getContext(), 196))
                    .fitCenter()
                    .into(image);

        }else {
            Glide.with(image.getContext()).load(imageList.get(position).getSpath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .override(realWith - DensityUtil.dp2px(image.getContext(), 32),
//                            DensityUtil.dp2px(image.getContext(), 196))
                    .fitCenter()
                    .into(image);
        }


        container.addView(image);


        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public void setData(ArrayList<AttachMent> imagelist) {
        this.imageList = imagelist;

    }
}
