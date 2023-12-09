package com.example.apptest;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed;
import java.util.List;

public class NativeAdAdapter extends BaseAdapter {

    private final Context context;
    private final List<Object> items;

    public NativeAdAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
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
        if (getItem(position) instanceof NativeAd) {
            convertView = LayoutInflater.from(context).inflate(R.layout.native_feed, parent, false);
            NativeAdViewNewsFeed nativeAdView = convertView.findViewById(R.id.native_ad_view_news_feed);
            NativeAd nativeAd = (NativeAd) getItem(position);
            nativeAdView.setNativeAd(nativeAd);
            return convertView;
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_native_ad, parent, false);
            ImageView imageViewIcon = convertView.findViewById(R.id.native_ad_icon);
            Drawable adIcon = ContextCompat.getDrawable(context, R.drawable.post_icon_background);
            imageViewIcon.setImageDrawable(adIcon);
            return convertView;
        }
    }

}
