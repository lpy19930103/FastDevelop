package com.lipy.android.fragment.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lipy.android.data.Business;
import com.lipy.fastdevelop.R;

/**
 *
 * @author aliang
 *         2014-06-06
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     *隐藏当前业务的Fragment
     * @param transaction FragmentTransaction
     */
    private void hideCurrentBusiness(FragmentTransaction transaction) {
        if (Business.getCurrentBiz() != null) {
            Fragment fragment = getFragmentManager().findFragmentByTag(
                    Business.getCurrentBiz().getName());
            if (null != fragment) {
                transaction.hide(fragment);
            }
        }
    }

    /**
     * 根据业务模块响应不同的界面
     *
     * @param biz 业务模块
     */
    public void changeFragment(Business biz) {
        try {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment f = getFragmentManager().findFragmentByTag(biz.getName());
            if (f != null) {
                hideCurrentBusiness(transaction);
                transaction.show(f);
            } else {
                Class<?> claz;
                try {
                    claz = Class.forName(biz.getClasspath());
                } catch (ClassNotFoundException e) {
                    claz = Class.forName(biz.getClasspath() + "Fragment");
                }
                f = (Fragment) claz.newInstance();

                transaction.add(R.id.fl_content, f, biz.getName()).show(f);
                hideCurrentBusiness(transaction);
            }

            Business.setCurrentBiz(biz);
            transaction.commitAllowingStateLoss();//commit();防止Can not perform this action after onSaveInstanceState异常

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
