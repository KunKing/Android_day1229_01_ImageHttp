package com.edu.android_day1229_01_imagehttp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.android_day1229_01_imagehttp.R;
import com.edu.android_day1229_01_imagehttp.adapters.ItemResponseAdapter;
import com.edu.android_day1229_01_imagehttp.dao.ShitService;
import com.edu.android_day1229_01_imagehttp.db.DBHelper;
import com.edu.android_day1229_01_imagehttp.entity.ItemShit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class GsonActivity extends AppCompatActivity implements Callback<ItemShit> {

    private ListView listView;

    private Call<ItemShit> call;
    private ItemResponseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        listView = (ListView) findViewById(R.id.listViewGson);
        adapter = new ItemResponseAdapter(this);
        listView.setAdapter(adapter);
        // 得到对象
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShitService service = build.create(ShitService.class);
        call = service.getListResponse("image", 1);
        call.enqueue(this);

    }

    // 主线程

    @Override
    public void onResponse(Response<ItemShit> response, Retrofit retrofit) {
        DBHelper.getDatabase().saveAll(response.body().getItems());
        adapter.addAll(response.body().getItems());
    }
    /**
     * 必须写
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(GsonActivity.this, "显示失败", Toast.LENGTH_SHORT).show();
    }
}
