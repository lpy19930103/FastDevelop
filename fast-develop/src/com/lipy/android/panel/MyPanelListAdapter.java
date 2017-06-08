package com.lipy.android.panel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lipy.fastdevelop.R;
import com.lipy.viewlibrary.panel.PanelListAdapter;
import com.lipy.viewlibrary.panel.PanelListLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyPanelListAdapter extends PanelListAdapter {

    private Context context;

    private ListView lv_content;
    private int contentResourceId;
    private List<Map<String, String>> contentList = new ArrayList<>();

    /**
     * constructor
     *
     */
    public MyPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                              int contentResourceId, List<Map<String, String>> contentList) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.lv_content = lv_content;
        this.contentResourceId = contentResourceId;
        this.contentList = contentList;
    }

    /**
     * 调用父类方法进行同步控制
     *
     * 自行编写Adapter并进行setAdapter
     */
    @Override
    public void initAdapter() {
        setTitleHeight(100);//设置表标题的高
        setTitleWidth(150);//设置表标题的宽
        setRowDataList(getRowDataList());//设置横向表头的内容

        // set自己写的contentAdapter
        ContentAdapter contentAdapter = new ContentAdapter(context,contentResourceId,contentList);
        lv_content.setAdapter(contentAdapter);

        super.initAdapter();//一定要在设置完后调用父类的方法
    }

    /**
     * 重写父类的该方法，返回数据的个数即可
     *
     * @return size of content
     */
    @Override
    protected int getCount() {
        return contentList.size();
    }

    /** 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private List<String> getRowDataList(){
        List<String> rowDataList = new ArrayList<>();
        rowDataList.add("时间");
        rowDataList.add("城市");
        rowDataList.add("施工面积");
        rowDataList.add("竣工面积");
        rowDataList.add("销售面积");
        rowDataList.add("现房");
        rowDataList.add("期房");
        rowDataList.add("待销面积");
        return rowDataList;
    }

    /**
     * content部分的adapter
     */
    private class ContentAdapter extends ArrayAdapter {

        private List<Map<String, String>> contentList;
        private int resourceId;

        ContentAdapter(Context context, int resourceId, List<Map<String, String>> contentList) {
            super(context, resourceId);
            this.contentList = contentList;
            this.resourceId = resourceId;
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Map<String, String> data = contentList.get(position);
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }


            viewHolder.tv_01.setText(data.get("时间"));
            viewHolder.tv_02.setText(data.get("城市"));
            viewHolder.tv_03.setText(data.get("施工面积"));
            viewHolder.tv_04.setText(data.get("竣工面积"));
            viewHolder.tv_05.setText(data.get("销售面积"));
            viewHolder.tv_06.setText(data.get("现房"));
            viewHolder.tv_07.setText(data.get("期房"));
            viewHolder.tv_08.setText(data.get("待销面积"));

//            if (lv_content.isItemChecked(position)){
//                view.setBackgroundColor(context.getResources().getColor(R.color.colorSelected));
//            } else {
//                view.setBackgroundColor(context.getResources().getColor(R.color.colorDeselected));
//            }

            return view;
        }

        private class ViewHolder {
            TextView tv_01;
            TextView tv_02;
            TextView tv_03;
            TextView tv_04;
            TextView tv_05;
            TextView tv_06;
            TextView tv_07;
            TextView tv_08;

            ViewHolder(View itemView) {
                tv_01 = (TextView) itemView.findViewById(R.id.id_tv_01);
                tv_02 = (TextView) itemView.findViewById(R.id.id_tv_02);
                tv_03 = (TextView) itemView.findViewById(R.id.id_tv_03);
                tv_04 = (TextView) itemView.findViewById(R.id.id_tv_04);
                tv_05 = (TextView) itemView.findViewById(R.id.id_tv_05);
                tv_06 = (TextView) itemView.findViewById(R.id.id_tv_06);
                tv_07 = (TextView) itemView.findViewById(R.id.id_tv_07);
                tv_08 = (TextView) itemView.findViewById(R.id.id_tv_08);
            }
        }
    }
}
