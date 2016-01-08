package com.edu.android_day1229_01_imagehttp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.android_day1229_01_imagehttp.activities.GsonActivity;
import com.edu.android_day1229_01_imagehttp.adapters.ItemAdapter;
import com.edu.android_day1229_01_imagehttp.dao.ShitService;
import com.edu.android_day1229_01_imagehttp.entity.Item;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<List<Item>> {

    private static final String TAG = "MainActivity";
    private Call<List<Item>> call;
    private ListView listView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ItemAdapter(this);
        listView.setAdapter(adapter);
        // 得到对象
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
                        return new Converter<ResponseBody, List<Item>>() {
                            @Override
                            public List<Item> convert(ResponseBody value) throws IOException {
                                String s = value.string();
                                JSONObject object;
                                List<Item> list = new ArrayList<>();
                                try {
                                    object = new JSONObject(s);
                                    JSONArray items = object.getJSONArray("items");
                                    for (int i = 0; i < items.length(); i++) {
                                        list.add(new Item(items.getJSONObject(i)));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return list;
                            }
                        };
                    }
                })
                .build();

        ShitService service = build.create(ShitService.class);
        call = service.getList("image", 1);
        call.enqueue(this);

    }

    // 主线程

    @Override
    public void onResponse(Response<List<Item>> response, Retrofit retrofit) {
        adapter.addAll(response.body());
    }

    /**
     * 必须写
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(MainActivity.this, "显示失败", Toast.LENGTH_SHORT).show();
    }

    public void btnJump(View view) {

        Intent intent = new Intent(this, GsonActivity.class);
        startActivity(intent);
    }
}
