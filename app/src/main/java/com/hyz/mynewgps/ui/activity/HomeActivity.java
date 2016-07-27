package com.hyz.mynewgps.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyz.mynewgps.R;
import com.hyz.mynewgps.ui.base.BaseMoudleActivity;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttplib.HttpInfo;
import okhttplib.OkHttpUtil;

/**
 * Created by ${hyz} on 2016/7/21.
 */
public class HomeActivity extends BaseMoudleActivity {

    @BindView(R.id.qq_init)
    EditText qqNumber;

    @BindView(R.id.send_qq)
    TextView qqSend;

    @BindView(R.id.resultTV)
    TextView resultTV;

    private String qqNum;

    OkHttpClient mOkHttpClient = new OkHttpClient();

    /**
     * 注意：测试时请更换该地址
     */
    private String url = "http://api.k780.com:88/?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";

    @Override
    public View setContent() {
        return inflater.inflate(R.layout.activity_home,null);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        initView();
    }

//    private void initView() {
//        qqNumber = (EditText) findViewById(R.id.qq_init);
//        qqSend = (TextView) findViewById(R.id.send_qq);
//        qqResult = (TextView) findViewById(R.id.result);
//    }

    @OnClick(R.id.send_qq)
    public void setQqSend(){
        Logger.d("点击事件");
//        Toast.makeText(mAc,"点击事件",Toast.LENGTH_SHORT).show();

        doHttpSync();
//        getMessage();
    }

    private void getMessage() {



      final   Request request = new Request.Builder()
              .url("http://japi.juhe.cn/qqevaluate/qq")
              .build();

        Call call =mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mAc, "联网失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //大文件下载
                final String res = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
        });
    }

    private void putMessage(){

//        Request request = buildMultipartFormRequest(
//                url, new File[]{file}, new String[]{fileKey},null);
//
//        FormEncodingBuilder builder = new FormEncodingBuilder();
    }


    /**
     * 同步请求：由于不能在UI线程中进行网络请求操作，所以采用子线程方式
     */
    private void doHttpSync() {
        new Thread(()-> {
            HttpInfo info = HttpInfo.Builder()
                    .setUrl(url)
                    .addParam("qq","632067489")
                    .addParam("key","")
                    .build();
            OkHttpUtil.getDefault(mAc).doGetSync(info);
            if (info.isSuccessful()) {
                final String result = info.getRetDetail();
                runOnUiThread(() -> {
                            resultTV.setText("同步请求：" + result);
                        }
                );
            }
        }
        ).start();
    }

}
