package com.lipy.android.fragment.squared;


import com.lipy.android.fragment.BaseFragment;
import com.lipy.android.utils.ImageUtil;
import com.lipy.android.data.Business;
import com.lipy.fastdevelop.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Tab导航栏
 * Created by dragon on 2014/7/4.
 */
public class NavBottomBarFragment extends Fragment {

    private BaseFragment mBaseFragment;
    private List<Business> bizs;
    private LayoutInflater inflater;
    private LinearLayout view;
    private View[] navViews;
    private ShowMyHintView showMyHintView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        view = new LinearLayout(getActivity());
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOrientation(LinearLayout.HORIZONTAL);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initNavBar();
    }

    private void initNavBar() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        //控制最多只能出现四个
        int navCount = bizs.size() > 4 ? 4 : bizs.size();
        int itemWidth = dm.widthPixels / navCount;
        navViews = new View[navCount];
        for (int i = 0; i < navCount; i++) {
            View currentView = inflater.inflate(R.layout.item_nav_bottom_bar, null);
            currentView.setTag(i);
            currentView.setLayoutParams(
                    new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT));
            setCurrentViewStyle(currentView, bizs.get(i), i == 0);
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setViewById((Integer) (view.getTag()));
                }
            });
            navViews[i] = currentView;
            view.addView(currentView);
        }

    }

    private void setCurrentViewStyle(View currentView, Business biz, boolean pressed) {
        String backColor = pressed ? biz.getPressedColor() : biz.getColor();
        String backImage = pressed ? biz.getPressedIcon() : biz.getIcon();
        String textColor = pressed ? biz.getTextPressedColor() : biz.getTextColor();
        currentView.setBackgroundColor(Color.parseColor(backColor));

        ((ImageView) currentView.findViewById(R.id.nav_image)).setImageBitmap(ImageUtil.readBitMap(
                currentView.getContext(), R.drawable.class, backImage));
        ((TextView) currentView.findViewById(R.id.nav_name)).setTextColor(
                Color.parseColor(textColor));
        ((TextView) currentView.findViewById(R.id.nav_name)).setText(biz.getName());
    }

    public void setBizs(BaseFragment baseFragment, List<Business> bizs) {
        this.mBaseFragment = baseFragment;
        this.bizs = bizs;
    }

    public void setViewById(int index) {
//        UserData.getInstance().getUseInfo().setMainIndex(index);
//        // 切换到指定fragment，判断是否需要登录
//        try {
//            if (bizs.get(index).getNeedLogin() && !UserData.getInstance().isLogin()){
//                UserData.getInstance().getUseInfo().go2Login(getActivity());
//            } else {
                mBaseFragment.changeFragment(bizs.get(index));
                //mainIndex为1，我的Fragment，

//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        for (int i = 0; i < navViews.length; i++) {
            setCurrentViewStyle(navViews[i], bizs.get(i), index == i);
        }
    }

    public void setShowMyHintView(ShowMyHintView showMyHintView) {
        this.showMyHintView = showMyHintView;
    }

    public interface ShowMyHintView {
        void onShowMyHintView(boolean isShowMyHint);
    }

}
