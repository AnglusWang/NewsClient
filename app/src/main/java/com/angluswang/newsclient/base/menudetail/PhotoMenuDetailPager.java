package com.angluswang.newsclient.base.menudetail;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.base.BaseMenuDetailPager;
import com.angluswang.newsclient.bean.PhotosData;
import com.angluswang.newsclient.global.GlobalContants;
import com.angluswang.newsclient.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/19.
 * 菜单详情页-组图
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    private ListView lvPhoto;
    private GridView gvPhoto;

    private ArrayList<PhotosData.PhotoInfo> mPhotoList;
    private PhotoAdapter mAdapter;

    private ImageButton imgPhoto;

    public PhotoMenuDetailPager(Activity activity, ImageButton imgPhoto) {
        super(activity);

        this.imgPhoto = imgPhoto;
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDisplay();
            }
        });
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);

        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(GlobalContants.PHOTOS_URL, mActivity);

        if (!TextUtils.isEmpty(cache)) {
            // TODO: 2016/8/28
        }

        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils =  new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET,
                GlobalContants.PHOTOS_URL, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        parseData(result);
                        // 设置缓存
                        CacheUtils.setCache(GlobalContants.PHOTOS_URL, result,
                                mActivity);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, msg,
                                Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        PhotosData photosData = gson.fromJson(result, PhotosData.class);

        mPhotoList = photosData.data.news;
        if (mPhotoList != null) {
            mAdapter = new PhotoAdapter();
            lvPhoto.setAdapter(mAdapter);
            gvPhoto.setAdapter(mAdapter);
        }
    }

    class PhotoAdapter extends BaseAdapter {

        private BitmapUtils utils;

        public PhotoAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.drawable.news_pic_default);
        }

        @Override
        public int getCount() {
            return mPhotoList.size();
        }

        @Override
        public PhotosData.PhotoInfo getItem(int position) {
            return mPhotoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_photo_item, null);

                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PhotosData.PhotoInfo item = getItem(position);
            holder.tvTitle.setText(item.title);
            utils.display(holder.ivPic, item.listimage);

            return convertView;
        }

    }

    static class ViewHolder {
        public TextView tvTitle;
        public ImageView ivPic;
    }

    private boolean isListDisplay = true;// 是否是列表展示
    /**
     * 切换展现方式
     */
    private void changeDisplay() {
        if (isListDisplay) {
            isListDisplay = false;
            lvPhoto.setVisibility(View.GONE);
            gvPhoto.setVisibility(View.VISIBLE);

            imgPhoto.setImageResource(R.drawable.icon_pic_list_type);

        } else {
            isListDisplay = true;
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);

            imgPhoto.setImageResource(R.drawable.icon_pic_grid_type);
        }
    }
}
