package com.lipy.android.fragment.squared;


import com.lipy.android.data.Business;
import com.lipy.android.fragment.BaseFragment;
import com.lipy.fastdevelop.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 主界面fragment，包含：底部tab列表、对应tab的各个fragment
 *
 * @author liang.chunjian
 *         2014-05-22
 */
public class MainScreenFragment extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tabcontainer, container);

        List<Business> navs = getBusiness(getActivity());
        // getActionBar().setDisplayHomeAsUpEnabled(true);

        NavBottomBarFragment fragment = (NavBottomBarFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation);
        fragment.setBizs(this, navs);

        return view;
    }

    public void setNavByIndex(int index) {
        NavBottomBarFragment fragment = (NavBottomBarFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation);
        fragment.setViewById(index);

    }





    /**
     * 返回所有的业务模块定义
     *
     * @param context Context
     * @return List<Business>
     */
    public List<Business> getBusiness(Context context) {

        List<Business> bizs = new ArrayList<>();

        //读取assets文件夹的biz.xml文件
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            InputStream inStream = context.getAssets().open("conf/biz.xml");
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(inStream);
            Element root = dom.getDocumentElement();

            NodeList items = root.getElementsByTagName("biz");//查找所有person节点
            for (int i = 0; i < items.getLength(); i++) {
                Business biz = new Business();
                //得到第一个person节点
                Element personNode = (Element) items.item(i);
                //获取person节点的id属性值
                biz.setName(personNode.getAttribute("name"));
                biz.setClasspath(personNode.getAttribute("class"));
                biz.setUrl(personNode.getAttribute("url"));
                biz.setNeedLogin(personNode.getAttribute("needLogin"));
                biz.setIcon(personNode.getAttribute("icon"));
                biz.setPressedIcon(personNode.getAttribute("pressedIcon"));
                biz.setColor(personNode.getAttribute("color"));
                biz.setPressedColor(personNode.getAttribute("pressedColor"));
                biz.setTextColor(personNode.getAttribute("textColor"));
                biz.setTextPressedColor(personNode.getAttribute("textPressedColor"));

                bizs.add(biz);
            }

            inStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bizs;
    }
}
