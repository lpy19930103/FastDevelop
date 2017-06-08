package com.lipy.android.panel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.lipy.fastdevelop.R;
import com.lipy.viewlibrary.panel.PanelListLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PanelActivity extends Activity implements PanelView {

    private PanelListLayout pl_root;
    private ListView lv_content;
    private List<Map<String, String>> contentList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private PanelPresenterImpl panelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        panelPresenter = new PanelPresenterImpl(this);
        panelPresenter.loadData();


    }

    private void initView() {
        mProgressDialog = new ProgressDialog(this);
        pl_root = (PanelListLayout) findViewById(R.id.id_pl_root);
        lv_content = (ListView) findViewById(R.id.id_lv_content);
    }


    @Override
    public void showSuccess(JsonData bigData) {
        List<JsonData.ResultsBean> results = bigData.getResults();
        contentList.clear();
        for (JsonData.ResultsBean bean : results) {
            Map<String, String> data = new HashMap<>();
            data.put("时间", bean.getDate());
            data.put("城市", bean.getCity());
            data.put("施工面积", bean.getConstruct());
            data.put("竣工面积", bean.getBecompleted());
            data.put("销售面积", bean.getMarket());
            data.put("现房", bean.getReadyhouse());
            data.put("期房", bean.getPropertyhouse());
            data.put("待销面积", bean.getForsale());
            contentList.add(data);
        }
        MyPanelListAdapter adapter = new MyPanelListAdapter(this, pl_root, lv_content, R.layout.item_content, contentList);
        adapter.initAdapter();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage("正在加载...");
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.cancel();
    }

    @Override
    protected void onDestroy() {
        panelPresenter.onDestroy();
        super.onDestroy();
    }
}
