package com.example.hxx.todaynews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hxx.todaynews.adapter.NewsAdapter;
import com.example.hxx.todaynews.adapter.OnItemClickListener;
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

public class MainActivity extends AppCompatActivity {
        public List<News.Newslist> list=new ArrayList<>();
        RecyclerView recyclerView;


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
        setContentView(R.layout.activity_main);

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
                            Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();

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
        recyclerView=(RecyclerView) findViewById(R.id.recycleview);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        NewsAdapter adapter=new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(MainActivity.this,WebActivity.class);
                startActivity(intent);
            }
        });
    }

    public void parseJson(String responseData){
        try {
            JSONObject firstjson=new JSONObject(responseData);
            JSONArray jsonArray=firstjson.getJSONArray("newslist");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject secondjson=jsonArray.getJSONObject(i);
                String title=secondjson.getString("title");
                String description=secondjson.getString("description");
                String picurl=secondjson.getString("picUrl");
                String url=secondjson.getString("url");
                Log.e("MainActivity","图片"+picurl);
                News news=new News();
                News.Newslist newslist1=news.new Newslist();
                newslist1.setDescription("消息来源:"+description);
                newslist1.setTitle(title);
                newslist1.setUrl(url);
                newslist1.setPicUrl(picurl);
                list.add(newslist1);

        }
    }catch (Exception e){
        e.printStackTrace();}
    }
}
