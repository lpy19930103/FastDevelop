package com.lipy.android.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lipy.android.common.NoInterfaceActivity;
import com.lipy.fastdevelop.R;


/**
 * 我的
 */
public class MineFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout2, null);
        view.findViewById(R.id.test_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoInterfaceActivity.class);
                intent.putExtra(NoInterfaceActivity.LISTENER_KEY,
                        "com.lipy.android.common.nointerface.NoInterfaceListenerImp");
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
