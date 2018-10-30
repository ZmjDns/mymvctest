package com.zmj.mvc.example.uiadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.entery.GroupMemberBean;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/10/26
 */
public class SortItemAdapter extends BaseAdapter implements SectionIndexer{

    private List<GroupMemberBean> list = null;
    private Context context;

    //1.定义接口
    public interface OnTelePhoneClickListener {
        void onTelephoneClick(GroupMemberBean bean);
    }

    //2.声明接口
    public OnTelePhoneClickListener onTelePhoneClickListener;

    //设置接口监听的方法
    public void setOnTelePhoneClickListener(OnTelePhoneClickListener listener){
        this.onTelePhoneClickListener = listener;
    }



    //1.定义接口
    public interface OnLineClickListener {
        void onLineCLickListener(GroupMemberBean bean);
    }
    //2.声明接口
    private OnLineClickListener onLineClickListener;
    //暴露接口方法
    public void setOnLineClickListener(OnLineClickListener listener){
        this.onLineClickListener = listener;
    }


    //构造函数
    public SortItemAdapter(List<GroupMemberBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * 当ListView发生变化时调用此方法更新数据
     * @param list
     */
    public void updateListView(List<GroupMemberBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final GroupMemberBean mContent = list.get(position);
        if (view == null){
            viewHolder = new ViewHolder();
//            view = LayoutInflater.from(context).inflate(R.layout.company_item,null);
            view = LayoutInflater.from(context).inflate(R.layout.company_item,parent,false);
            viewHolder.tv_company = view.findViewById(R.id.tv_company);
            viewHolder.tv_letter = view.findViewById(R.id.tv_letter);
            viewHolder.btn_phone = view.findViewById(R.id.btn_phone);
            viewHolder.btn_online = view.findViewById(R.id.btn_online);

            //给每一个item添加点击监听
            //监听不能写在这里面，这里的代码只有view第一次创建的时候才会走，之后就不再走次代码，
            // 所以点击Item的时候就会拿不到对应的Item的数据
            //因此将点击回调监听写在view创建过程之外
            /*viewHolder.btn_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupMemberBean bean = list.get(position);
                    onTelePhoneClickListener.onTelephoneClick(bean);
                    Log.d("TEST", "onItemClick: 点击电话 " + bean.getPhoneNumber() + bean.getName());
                }
            });

            viewHolder.btn_online.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupMemberBean bean = list.get(position);
                    onTelePhoneClickListener.onTelephoneClick(bean);
                    Log.d("TEST", "onItemClick: 点击电话 " + bean.getPhoneNumber() + bean.getName());
                }
            });*/
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTelePhoneClickListener.onTelephoneClick(list.get(position));
            }
        });

        viewHolder.btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLineClickListener.onLineCLickListener(list.get(position));
            }
        });


        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)){
            viewHolder.tv_letter.setVisibility(View.VISIBLE);
            viewHolder.tv_letter.setText(mContent.getSortLetters());
        }else {
            viewHolder.tv_letter.setVisibility(View.GONE);
        }

        viewHolder.tv_company.setText(this.list.get(position).getName());
        return view;
    }

    final static class ViewHolder{
        TextView tv_letter;
        TextView tv_company;
        Button btn_phone;
        Button btn_online;
    }



    //接口部分
    //根据ListView的当前位置获取分类的首字母的Char ascii值
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    //根据分类首字母的Char  ascii值获取其第一次出现该首字母的位置
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0;i < getCount();i++){
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section){
                return i;
            }
        }
        return -1;
    }


    @Override
    public Object[] getSections() {
        return null;
    }

}
