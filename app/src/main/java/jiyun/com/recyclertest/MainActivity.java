package jiyun.com.recyclertest;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidkun.PullToRefreshRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jiyun.com.recyclertest.Adapter.FilmItemAdapter;
import jiyun.com.recyclertest.Entity.FilmBean;

public class MainActivity extends Activity {
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.Recycler)
    PullToRefreshRecyclerView Recycler;
    private String url = "https://api-m.mtime.cn/PageSubArea/HotPlayMovies.api?locationId=290";
    private FilmItemAdapter filmItemAdapter;
    private List<FilmBean.MoviesBean> mList = new ArrayList<>();
    private AlertDialog dialog;
    private View view;
    private TextView mTitle, mAuthor;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    filmItemAdapter = new FilmItemAdapter(MainActivity.this, mList);
                    Recycler.setAdapter(filmItemAdapter);
                    if (filmItemAdapter != null) {
                        filmItemAdapter.setItemListner(new SetItemRecyclerViewListener() {
                            @Override
                            public void setItemRecyclerViewListner(View view, int position) {
                                if (view.getId() == R.id.Film_Btn_Detail) {
                                    FilmBean.MoviesBean moviesBean = mList.get(position);
                                    String actorName1 = moviesBean.getActorName1();
                                    String actorName2 = moviesBean.getActorName2();
                                    mTitle.setText(actorName1);
                                    mAuthor.setText(actorName2);
                                    dialog.setView(MainActivity.this.view);
                                    dialog.show();
                                } else {
                                    Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        view = LayoutInflater.from(this).inflate(R.layout.make_view, null);
        mTitle = (TextView) view.findViewById(R.id.Text_Title);
        mAuthor = (TextView) view.findViewById(R.id.Text_author);
        dialog = new AlertDialog.Builder(this).create();
        Network_request();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(linearLayoutManager);
    }

    private void Network_request() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("===================", s);
                        Gson gson = new Gson();
                        FilmBean bean = gson.fromJson(s, FilmBean.class);
                        List<FilmBean.MoviesBean> moviesBeanArrayList = bean.getMovies();
                        Log.i("===========解析数据", moviesBeanArrayList.toString() + "");
                        mList.addAll(moviesBeanArrayList);
                        handler.sendEmptyMessage(200);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                requestQueue.add(stringRequest);
            }
        }).start();
    }
}
