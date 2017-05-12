package com.lipy.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lipy.fastdevelop.R;
import com.lipy.viewlibrary.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品列表
 */
public class ProductFragment extends Fragment {


    public static String[] titles = new String[]{
            "banner1",
            "banner2",
            "banner3",
            "banner4",
    };
    public static String[] urls = new String[]{
            "http://ongeesmn7.bkt.clouddn.com/banner/banner1.png",
            "http://ongeesmn7.bkt.clouddn.com/banner/banner2.png",
            "http://ongeesmn7.bkt.clouddn.com/banner/banner3.png",
            "http://ongeesmn7.bkt.clouddn.com/banner/banner4.png",
    };

    public static class BannerItem {
        public String image;
        public String title;

        @Override
        public String toString() {
            return title;
        }
    }

    public static class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {
        @Override
        public View create(BannerItem item, int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
            Glide.with(container.getContext().getApplicationContext())
                    .load(item.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(750, 700)
                    .into(iv);
            return iv;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout1, null);


        List<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.image = urls[i];
            item.title = titles[i];

            list.add(item);
        }

        final BannerView banner1 = (BannerView) view.findViewById(R.id.banner1);
        banner1.setViewFactory(new BannerViewFactory());
        banner1.setDataList(list);
        banner1.start();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
