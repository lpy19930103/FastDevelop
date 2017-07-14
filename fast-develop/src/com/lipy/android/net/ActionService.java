package com.lipy.android.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lipy.android.http.response.DataObject;
import com.lipy.android.net.callback.JsonCallback;
import com.lipy.android.net.callback.OnResponeListener;
import com.lipy.android.net.dto.ServerModel;
import com.lipy.android.util.FileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.OkUpload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.lzy.okserver.upload.UploadListener;
import com.lzy.okserver.upload.UploadTask;

import java.io.File;
import java.util.Random;


/**
 * 网络请求类
 * Created by lipy on 17/7/10.
 */

public class ActionService<T extends DataObject> {

    private static final String TAG = "Action";

    public ActionService() {

    }

    public static final String url = "http://ongeesmn7.bkt.clouddn.com/";

    public static final String SERVER = "http://192.168.3.51:8080/";
    public static final String URL_FORM_UPLOAD = SERVER + "upload";
    public static final String DOWN_LOAD_URL = "http://121.29.10.1/f5.market.mi-img.com/download/AppStore/0b8b552a1df0a8bc417a5afae3a26b2fb1342a909/com.qiyi.video.apk";
//    public static final String DOWN_LOAD_URL = "http://ongeesmn7.bkt.clouddn.com/miniapps/test.mp4";

    public void request(HttpParams httpParams, final OnResponeListener onResponeListener) {
        Log.e(TAG, url + onResponeListener.getTag());
        OkGo.<ServerModel>post(url + onResponeListener.getTag())
                .tag(onResponeListener.getTag())
                .headers("token", "token")
                .params(httpParams)
                .execute(new JsonCallback<ServerModel>(onResponeListener.getType()) {
                    @Override
                    public void onSuccess(Response<ServerModel> response) {
                        try {
                            ServerModel<T> body = response.body();
                            Log.e("TAG", response.getRawResponse() + " =  onSuccess");
                            if (body == null) {
                                onResponeListener.error("服务器无响应");
                                return;
                            }
                            switch (body.getCode()) {
                                case 0:
                                    onResponeListener.success(body);
                                    break;
                                default:
                                    onResponeListener.error(body.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ServerModel> response) {
                        super.onError(response);
                        try {
                            Log.e("TAG", response.getRawResponse() + " =  onError");
                            onResponeListener.error(response.message());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public UploadTask upLoad(Context context, UploadListener listener, String filepath) {
        if (!FileUtils.fileIsExists(filepath)) {
            Toast.makeText(context, "请先选择文件", Toast.LENGTH_SHORT).show();
            return null;
        }
        PostRequest<String> postRequest = OkGo.<String>post(URL_FORM_UPLOAD)//
                .headers("aaa", "111")//
                .params("bbb", "222")//
                .params("filepath", new File(filepath))//
                .converter(new StringConvert());

        return OkUpload.request(URL_FORM_UPLOAD, postRequest)//
                .priority(new Random().nextInt(100))//
                .register(listener)
                .start();
    }

    public DownloadTask download(String url, DownloadListener logDownloadListener) {
        if (OkDownload.getInstance().hasTask(url)) {
            return OkDownload.getInstance()
                    .getTask(url)
                    .register(logDownloadListener);
        }
        Progress progress = DownloadManager.getInstance().get(url);
        if (progress != null) {
            return OkDownload.restore(progress)//
                    .register(logDownloadListener);
        }
        //这里只是演示，表示请求可以传参，怎么传都行，和okgo使用方法一样
        GetRequest<File> request = OkGo.<File>get(url)//
                .headers("aaa", "111")//
                .params("bbb", "222");

        //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
        return OkDownload.request(url, request)//
                .priority(new Random().nextInt(100))//
                .register(logDownloadListener);
//                .folder("/storage/emulated/0/")
//                .fileName("aaa.mp4");
    }

    public static void cancle(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }
}
