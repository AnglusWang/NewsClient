package com.angluswang.newsclient.base.menudetail;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.base.BaseMenuDetailPager;
import com.angluswang.newsclient.bean.PhotosData;
import com.lidroid.xutils.BitmapUtils;

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

    public PhotoMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);

        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

        return view;
    }

    private class PhotoAdapter extends BaseAdapter {

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

    private static class ViewHolder {
        public TextView tvTitle;
        public ImageView ivPic;
    }
}
