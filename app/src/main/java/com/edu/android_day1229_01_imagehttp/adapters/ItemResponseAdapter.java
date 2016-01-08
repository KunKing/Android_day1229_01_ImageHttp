package com.edu.android_day1229_01_imagehttp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.android_day1229_01_imagehttp.R;
import com.edu.android_day1229_01_imagehttp.db.DBHelper;
import com.edu.android_day1229_01_imagehttp.entity.ItemShit;
import com.edu.android_day1229_01_imagehttp.tools.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ming on 2015/12/29.
 */
public class ItemResponseAdapter extends BaseAdapter {

    private static final String TAG = "ItemAdapter";
    private Context context;
    private List<ItemShit.ItemsEntity> items;

    public ItemResponseAdapter(Context context) {
        this.context = context;
        items = DBHelper.getDatabase().findAll(null,null,null,20);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (items != null) {
            ret = items.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ItemShit.ItemsEntity item = items.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (item.getUser() != null) {
            holder.tvName.setText(item.getUser().getLogin());
            Picasso.with(context)
                    .load(getIconURL(item.getUser().getId(), item.getUser().getIcon()))
                    .transform(new CircleTransformation())
                    .into(holder.tvIcon);
        } else {
            holder.tvName.setText("匿名用户");
            holder.tvIcon.setImageResource(R.mipmap.ic_launcher);
        }

        if (item.getImage() == null) {
            holder.image.setVisibility(View.GONE);
        } else {
            holder.image.setVisibility(View.VISIBLE);
            // resize(宽,高) 不可以为负数，不可以全为0 一个为0 另一个不为0  为0 的失效
            // fit() 可以匹配 imageView 在 listView 中不好用
            // centerInside() 居中适应大小
            // centerCrop() 剪切
            Log.d(TAG, "getView: "+parent.getWidth());
            Picasso.with(context)
                    .load(getImageURL(item.getImage()))
                    .resize(parent.getWidth(), 0)
                    .placeholder(R.mipmap.ic_launcher) // 下载中的图片 占位图片
                    .error(R.mipmap.ic_launcher) // 下载失败的图片
                    .into(holder.image);
        }
        holder.content.setText(item.getContent());
        return convertView;
    }

    // 获取头像
    public static String getIconURL(long id, String icon) {
        String url = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
        return String.format(url, id / 10000, id, icon);
    }

    // 获取 内容图片
    public static String getImageURL(String image) {
        String url = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern = Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher = pattern.matcher(image);
        matcher.find();
        Log.d(TAG, "getImageURL: " + matcher.group());
        // TODO: 2015/12/29 检测网络  工具类 返回师傅是 wifi  medium 或者 3g small
        return String.format(url, matcher.group(1), matcher.group(), "small", image);
    }

    public void addAll(Collection<? extends ItemShit.ItemsEntity> collection){
        items.addAll(collection);
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        private final TextView tvName;
        private final ImageView tvIcon;
        private final TextView content;
        private final ImageView image;

        public ViewHolder(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.user_name);
            tvIcon = (ImageView) itemView.findViewById(R.id.user_icon);
            content = (TextView) itemView.findViewById(R.id.content);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
