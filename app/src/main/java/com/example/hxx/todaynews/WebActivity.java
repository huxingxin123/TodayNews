package com.example.hxx.todaynews;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.hxx.todaynews.news.News;
import com.example.hxx.todaynews.utils.OkHttp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WebActivity extends AppCompatActivity {
    List<News.Newslist> newslists=new ArrayList<>();
    String url;
    Handler handler=new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    initview();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        sendRequestWithOkHttp();
    }
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttp.OkHttpConnection("https://api.tianapi.com/guonei/?key=31951f7977bf01c27395f41c1a51f211", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(WebActivity.this,"error",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("MainActivity","连接成功"+response);
                            String responseData=response.body().string();
                            parseJson(responseData);
                            Message message=new Message();
                            message.what=1;
                            handler.sendMessage(message);

                        }



                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initview(){
        WebView webView=(WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    public void parseJson(String responseData){
        try {
            JSONObject firstjson=new JSONObject(responseData);
            JSONArray jsonArray=firstjson.getJSONArray("newslist");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject secondjson=jsonArray.getJSONObject(i);
                url=secondjson.getString("url");
                News news=new News();
                News.Newslist llist=news.new Newslist();
                llist.setUrl(url);
                newslists.add(llist);

                Log.e("MainActivity","网络"+url);
        }
    }catch (Exception e){
        e.printStackTrace();}
    }
}
