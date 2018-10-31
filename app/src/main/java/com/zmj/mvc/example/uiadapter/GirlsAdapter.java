package com.zmj.mvc.example.uiadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.entery.Girl;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/10/30
 */
public class GirlsAdapter extends BaseAdapter {
    private Context context;
    private List<Girl> girls;

    public GirlsAdapter(Context context, List<Girl> girls) {
        this.context = context;
        this.girls = girls;
    }

    @Override
    public int getCount() {
        return girls.size();
    }

    @Override
    public Object getItem(int position) {
        return girls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GirlViewHilder girlViewHilder = null;
        if (convertView == null){
            girlViewHilder = new GirlViewHilder();
            convertView = LayoutInflater.from(context).inflate(R.layout.girl_item,null);
            girlViewHilder.iv_girlpic = convertView.findViewById(R.id.iv_girlpic);
            girlViewHilder.tv_girlName = convertView.findViewById(R.id.tv_girlName);
            convertView.setTag(girlViewHilder);
        }else {
            girlViewHilder = (GirlViewHilder) convertView.getTag();
        }

        girlViewHilder.iv_girlpic.setImageDrawable(context.getResources().getDrawable(girls.get(position).getPic()));
        girlViewHilder.tv_girlName.setText(girls.get(position).getName());
        return convertView;
    }

    class GirlViewHilder{
        public GirlViewHilder() {

        }

        ImageView iv_girlpic;
        TextView tv_girlName;
    }
}
