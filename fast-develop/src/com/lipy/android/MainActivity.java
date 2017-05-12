package com.lipy.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.lipy.android.data.UserData;
import com.lipy.android.fragment.squared.MainScreenFragment;
import com.lipy.fastdevelop.R;

public class MainActivity extends FragmentActivity {


    private long mExitTime;
    private MainScreenFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.root);

    }


    @Override
    protected void onResume() {
        super.onResume();
        changeView(UserData.getInstance().getUseInfo().getMainIndex());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //选择页面
    private void changeView(int index) {
        getFragmentManager().findFragmentById(R.id.fragment_root);
        fragment = (MainScreenFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_root);
        UserData.getInstance().getUseInfo().setMainIndex(index);
        fragment.setNavByIndex(index);
    }

}
