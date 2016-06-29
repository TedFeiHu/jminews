package com.wuxianedu.jminews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wuxianedu.jminews.DetailActivity;
import com.wuxianedu.jminews.MyApplication;
import com.wuxianedu.jminews.R;
import com.wuxianedu.jminews.bean.DataBean;
import com.wuxianedu.jminews.bean.RequestBean;
import com.wuxianedu.jminews.bean.ResultBean;
import com.wuxianedu.jminews.utils.Contest;
import com.wuxianedu.jminews.utils.HttpUtils;

import org.xutils.x;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Hu131 on 2016/6/23.
 */
public class TopFragment extends Fragment implements AdapterView.OnItemClickListener{

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    getfresh((String) msg.obj);
                    break;
            }
        }
    };

    LayoutInflater inflater;
    private ListView topListView;
    private ArrayList<DataBean> dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_top, null);
        topListView = (ListView) view.findViewById(R.id.list_view_top);
        topListView.setOnItemClickListener(this);

        //开启网络请求线程
        ThreadHttp threadHttp = new ThreadHttp();
        Thread thread = new Thread(threadHttp);
        thread.start();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("uri",dataList.get(position).getUrl());
        startActivity(intent);
    }


    class ThreadHttp implements Runnable {
        @Override
        public void run() {
            try {
                String json = HttpUtils.getHttp(Contest.URI_TOP);
                Message msg = handler.obtainMessage();
                msg.obj = json;
                msg.what = 0;
                handler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 视图
     */
    public void getfresh(String json) {
        //访问ASSETS文件夹下的资源文件（网络请求数据）
        try {
//            InputStream is = getActivity().getAssets().open("topnews.json");
//            String json = IOUtils.toString(is, "utf-8"); //将inputStream转换成String
            //json解析（GSON）
            Gson gson = new Gson();
            RequestBean requestBean = gson.fromJson(json, RequestBean.class);
            ResultBean resultBean = requestBean.getResult();
            dataList = resultBean.getData();
            Log.i("---海事新闻----", dataList.get(0).getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
        topListView.setAdapter(new TopAdapter());
    }

    class TopAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.fragment_top_item, null);
                holder.head = (ImageView) convertView.findViewById(R.id.image_view_top_head);
                holder.title = (TextView) convertView.findViewById(R.id.text_view_top_title);
                holder.author = (TextView) convertView.findViewById(R.id.text_view_top_author);
                holder.url = (TextView) convertView.findViewById(R.id.text_view_top_url);
                holder.date = (TextView) convertView.findViewById(R.id.text_view_top_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title.setText(dataList.get(position).getTitle());
            holder.author.setText(dataList.get(position).getAuthor_name());
            holder.url.setText(dataList.get(position).getUrl());
            holder.date.setText(dataList.get(position).getDate());

            x.image().bind(holder.head, dataList.get(position).getThumbnail_pic_s02(), MyApplication.imageOptions);

            return convertView;
        }
    }

    class ViewHolder {
        ImageView head;
        TextView title;
        TextView author;
        TextView url;
        TextView date;
    }
}
