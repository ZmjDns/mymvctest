package com.zmj.mvc.example.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.entery.GroupMemberBean;
import com.zmj.mvc.example.uiadapter.SortItemAdapter;
import com.zmj.mvc.example.utils.CharacterParser;
import com.zmj.mvc.example.utils.PinyinComparator;
import com.zmj.mvc.example.view.widget.ClearEditText;
import com.zmj.mvc.example.view.widget.SilderBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortDataAct extends AppCompatActivity implements SectionIndexer {

    private ListView sortListView;
    private SilderBar silderBar;
    private TextView dialog;
    private SortItemAdapter adapter;
    private ClearEditText clearEditText;

    private LinearLayout titleLayout;
    private TextView title;
    private TextView tvNoFriends;

    //上次第一个可见元素，用于滚动时记录标识
    private int lastFirstVisiableItem = -1;

    //汉字转拼音的类
    private CharacterParser characterParser;

    //数据
    private List<GroupMemberBean> dataList;

    //根据拼音排列ListView内的数据
    private PinyinComparator pinyinComparator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_data);

        initViews();
    }

    private void initViews() {
//        clearEditText = findViewById(R.id.filter_editor);
        dataList = filledData();
//        clearEditText.setText("");

        titleLayout = findViewById(R.id.title_layout);
        title = findViewById(R.id.title_layout_catalog);
        tvNoFriends = findViewById(R.id.title_layout_no_friends);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        silderBar = findViewById(R.id.sliderBar);
        dialog = findViewById(R.id.tv_dialog);
        silderBar.setTextView(dialog);

        //设置sliderBar的触摸监听
        silderBar.setOnTachingLetterChangedListener(new SilderBar.OnTachingLetterChangedListener() {
            @Override
            public void onTachingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1){
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView = findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupMemberBean bean = (GroupMemberBean) adapter.getItem(position);
                Log.d("LISTVIEW", "onItemClick: 点击ListView获取对象：  " + bean.getName() + bean.getPhoneNumber());
            }
        });

        //根据A-Z进行源数据排序
        Collections.sort(dataList,pinyinComparator);
        adapter = new SortItemAdapter(dataList,this);
        sortListView.setAdapter(adapter);

        //sortListView滚动监听
        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int section = getSectionForPosition(firstVisibleItem);
                int nexSection = getSectionForPosition(firstVisibleItem + 1);
                int nextPosition = getPositionForSection( + nexSection);
                if (firstVisibleItem != lastFirstVisiableItem){
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(dataList.get(getPositionForSection(section)).getSortLetters());   //设置首字母
                }
                if (nextPosition == firstVisibleItem + 1){
                    View childView = view.getChildAt(0);
                    if (childView != null){
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
                        if (bottom < titleHeight){
                            float pushedDiatence = bottom - titleHeight;
                            params.topMargin = (int)pushedDiatence;
                            titleLayout.setLayoutParams(params);
                        }else {
                            if (params.topMargin != 0){
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisiableItem = firstVisibleItem;
            }
        });

        adapter.setOnTelePhoneClickListener(new SortItemAdapter.OnTelePhoneClickListener() {
            @Override
            public void onTelephoneClick(GroupMemberBean bean) {
                Log.d("LISTENER", "onTelephoneClick: LISTENER" + bean.getName() + bean.getPhoneNumber());
                startActivity( new Intent(SortDataAct.this,LoginAct.class));
            }
        });

        adapter.setOnLineClickListener(new SortItemAdapter.OnLineClickListener() {
            @Override
            public void onLineCLickListener(GroupMemberBean bean) {
                Log.d("ONLINELISTENER", "onLineCLickListener: " + bean.getName() + bean.getPhoneNumber());
                startActivity( new Intent(SortDataAct.this,LoginAct.class));
            }
        });



        //clearEditText 对象获取和       过滤功能实现
        clearEditText = findViewById(R.id.filter_editor);
        //根据输入框值的改变来过滤搜索
        clearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //隐藏titleLayout
                titleLayout.setVisibility(View.GONE);
                //当搜索框里值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }


    //根据clearText内的数据过滤数据
    private void filterData(String filterStr){
        List<GroupMemberBean> filterDataList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)){
            filterDataList = dataList;
            tvNoFriends.setVisibility(View.GONE);
        }else {
            filterDataList.clear();
            for (GroupMemberBean sortModle:dataList){
                String name = sortModle.getName();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDataList.add(sortModle);
                }
            }
        }
        Collections.sort(filterDataList,pinyinComparator);
        //拿到过滤的数据之后更新adapter
        adapter.updateListView(filterDataList);
        if (filterDataList.size() == 0){
            tvNoFriends.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public Object[] getSections() {
        return null;
    }

    //根据ListView当前位置获取分类的首字母的Char ascii值
    @Override
    public int getSectionForPosition(int position) {
        return dataList.get(position).getSortLetters().charAt(0);
    }

    //根据分类的首字母的Char ascii值获取第一次出现该首字母的位置
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0;i < dataList.size(); i++){
            String sorStr = dataList.get(i).getSortLetters();
            char firstChar = sorStr.toUpperCase().charAt(0);
            if (firstChar == section){
                return i;
            }
        }
        return -1;
    }




    private List<GroupMemberBean> filledData(){
        List<GroupMemberBean> tmpList = new ArrayList<>();

        tmpList.add(new GroupMemberBean("大新华航空有限公司", "950718", "D","CN"));
        tmpList.add(new GroupMemberBean("天津航空有限责任公司", "95350", "T","GS"));
        tmpList.add(new GroupMemberBean("河北航空有限公司", "031196699", "H","NS"));
        tmpList.add(new GroupMemberBean("海南航空股份有限公司", "95339", "H","HU"));
        tmpList.add(new GroupMemberBean("华夏航空有限公司", "4006006633", "H","G5"));
        tmpList.add(new GroupMemberBean("上海吉祥航空有限公司", "95520", "S","HO"));
        tmpList.add(new GroupMemberBean("北京首都航空有限公司", "69615308", "B","JD"));
        tmpList.add(new GroupMemberBean("河南航空有限公司", "无", "H","VD"));
        tmpList.add(new GroupMemberBean("山东航空股份有限公司", "95369", "S","SC"));
        tmpList.add(new GroupMemberBean("上海航空股份有限公司", "95530", "S","SC"));
        tmpList.add(new GroupMemberBean("深圳航空有限责任公司", "95080", "S","ZH"));
        tmpList.add(new GroupMemberBean("四川航空股份有限公司", "4008300999", "S","3U"));
        tmpList.add(new GroupMemberBean("西部航空有限责任公司", "02386859618", "X","PN"));
        tmpList.add(new GroupMemberBean("厦门航空有限公司", "95557", "X","MF"));
        tmpList.add(new GroupMemberBean("云南祥鹏航空有限责任公司", "087167095315", "Y","8L"));
        tmpList.add(new GroupMemberBean("成都航空有限公司", "02866668888", "C","EU"));
        tmpList.add(new GroupMemberBean("中国东方航空股份有限公司", "95530", "Z","MU"));

        tmpList.add(new GroupMemberBean("中国国际航空股份有限公司","95583","Z","CA"));
        tmpList.add(new GroupMemberBean("中国联合航空有限公司","4001026666","Z","KN"));
        tmpList.add(new GroupMemberBean("中国南方航空股份有限公司","95539","Z","CZ"));
        tmpList.add(new GroupMemberBean("澳达航空公司", "95530", "A","AD"));//暂时代替电话
        tmpList.add(new GroupMemberBean("奥凯航空有限公司","4000668866","A","BK"));
        tmpList.add(new GroupMemberBean("春秋航空有限公司","4008206222","C","9C"));
        tmpList.add(new GroupMemberBean("重庆航空有限责任公司","95539","C","OQ"));
        tmpList.add(new GroupMemberBean("幸福航空有限责任公司","4008680000","X","JR"));
        tmpList.add(new GroupMemberBean("昆明航空有限公司","4008876737","K","KY"));
        tmpList.add(new GroupMemberBean("西藏航空有限公司","4008089188","X","TV"));
        tmpList.add(new GroupMemberBean("大连航空有限公司","95583","D","CD"));
        tmpList.add(new GroupMemberBean("浙江长龙航空公司","057189999999","Z","GJ"));
        tmpList.add(new GroupMemberBean("青岛航空股份有限公司","053296630","Q","QW"));
        tmpList.add(new GroupMemberBean("东海航空有限公司","4000888666","D","DZ"));
        tmpList.add(new GroupMemberBean("瑞丽航空有限公司","40000599999","R","DR"));
        tmpList.add(new GroupMemberBean("乌鲁木齐航空有限责任公司","95334","W","UQ"));
        tmpList.add(new GroupMemberBean("九元航空有限公司","4001051999","J","AQ"));
        tmpList.add(new GroupMemberBean("福州航空有限责任公司","95071666","F","FU"));
        tmpList.add(new GroupMemberBean("广西北部湾航空有限责任公司","95071111","G","GX"));
        tmpList.add(new GroupMemberBean("多彩贵州航空有限公司","08519610077","D","GY "));
        tmpList.add(new GroupMemberBean("长安航空有限责任公司","95071199","C","9H"));
        tmpList.add(new GroupMemberBean("桂林航空有限公司","9507101","G","GT "));
        tmpList.add(new GroupMemberBean("江西航空有限公司","079196300","J","RY"));
        tmpList.add(new GroupMemberBean("云南红土航空股份有限公司","4008337777","Y","A6"));
//            tmpList.add(new GroupMemberBean("扬子江快运航空有限公司","","Y","Y8"));
        tmpList.add(new GroupMemberBean("龙江航空有限公司","045181181111","L","LT "));

        /*tmpList.add(new GroupMemberBean("大新华航空有限公司", "950718", "CN"));
        tmpList.add(new GroupMemberBean("天津航空有限责任公司", "95350", "GS"));
        tmpList.add(new GroupMemberBean("河北航空有限公司", "031196699", "NS"));
        tmpList.add(new GroupMemberBean("海南航空股份有限公司", "95339", "HU"));
        tmpList.add(new GroupMemberBean("华夏航空有限公司", "4006006633", "G5"));
        tmpList.add(new GroupMemberBean("上海吉祥航空有限公司", "95520", "HO"));
        tmpList.add(new GroupMemberBean("北京首都航空有限公司", "69615308", "JD"));
        tmpList.add(new GroupMemberBean("河南航空有限公司", "无", "VD"));
        tmpList.add(new GroupMemberBean("山东航空股份有限公司", "95369", "SC"));
        tmpList.add(new GroupMemberBean("上海航空股份有限公司", "95530", "SC"));
        tmpList.add(new GroupMemberBean("深圳航空有限责任公司", "95080", "ZH"));
        tmpList.add(new GroupMemberBean("四川航空股份有限公司", "4008300999", "3U"));
        tmpList.add(new GroupMemberBean("西部航空有限责任公司", "02386859618", "PN"));
        tmpList.add(new GroupMemberBean("厦门航空有限公司", "95557", "MF"));
        tmpList.add(new GroupMemberBean("云南祥鹏航空有限责任公司", "087167095315", "8L"));
        tmpList.add(new GroupMemberBean("成都航空有限公司", "02866668888", "EU"));
        tmpList.add(new GroupMemberBean("中国东方航空股份有限公司", "95530", "MU"));
        tmpList.add(new GroupMemberBean("中国国际航空股份有限公司","95583","CA"));
        tmpList.add(new GroupMemberBean("中国联合航空有限公司","4001026666","KN"));
        tmpList.add(new GroupMemberBean("中国南方航空股份有限公司","95539","CZ"));
        tmpList.add(new GroupMemberBean("澳达航空公司", "95530", "AD"));//暂时代替电话
        tmpList.add(new GroupMemberBean("奥凯航空有限公司","4000668866","BK"));
        tmpList.add(new GroupMemberBean("春秋航空有限公司","4008206222","9C"));
        tmpList.add(new GroupMemberBean("重庆航空有限责任公司","95539","OQ"));
        tmpList.add(new GroupMemberBean("幸福航空有限责任公司","4008680000","JR"));
        tmpList.add(new GroupMemberBean("昆明航空有限公司","4008876737","KY"));
        tmpList.add(new GroupMemberBean("西藏航空有限公司","4008089188","TV"));
        tmpList.add(new GroupMemberBean("大连航空有限公司","95583","CD"));
        tmpList.add(new GroupMemberBean("浙江长龙航空公司","057189999999","GJ"));
        tmpList.add(new GroupMemberBean("青岛航空股份有限公司","053296630","QW"));
        tmpList.add(new GroupMemberBean("东海航空有限公司","4000888666","DZ"));
        tmpList.add(new GroupMemberBean("瑞丽航空有限公司","40000599999","DR"));
        tmpList.add(new GroupMemberBean("乌鲁木齐航空有限责任公司","95334","UQ"));
        tmpList.add(new GroupMemberBean("九元航空有限公司","4001051999","AQ"));
        tmpList.add(new GroupMemberBean("福州航空有限责任公司","95071666","FU"));
        tmpList.add(new GroupMemberBean("广西北部湾航空有限责任公司","95071111","GX"));
        tmpList.add(new GroupMemberBean("多彩贵州航空有限公司","08519610077","GY "));
        tmpList.add(new GroupMemberBean("长安航空有限责任公司","95071199","9H"));
        tmpList.add(new GroupMemberBean("桂林航空有限公司","9507101","GT "));
        tmpList.add(new GroupMemberBean("江西航空有限公司","079196300","RY"));
        tmpList.add(new GroupMemberBean("云南红土航空股份有限公司","4008337777","A6"));
        tmpList.add(new GroupMemberBean("扬子江快运航空有限公司","","Y8"));
        tmpList.add(new GroupMemberBean("龙江航空有限公司","045181181111","LT "));*/

        return tmpList;
    }


}
