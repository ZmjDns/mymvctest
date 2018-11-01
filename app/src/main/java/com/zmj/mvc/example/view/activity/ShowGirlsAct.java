package com.zmj.mvc.example.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.entery.Girl;
import com.zmj.mvc.example.presenter.BasePersenter;
import com.zmj.mvc.example.presenter.GirlPersenter;
import com.zmj.mvc.example.uiadapter.GirlsAdapter;
import com.zmj.mvc.example.view.IGirlView;

import java.util.List;

public class ShowGirlsAct extends BaseActivity<IGirlView,GirlPersenter<IGirlView>> implements IGirlView{

    private ListView ListViewGirls;
    private GirlsAdapter adapter;
//    private  GirlPersenter girlPersenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_girls);

        ListViewGirls = findViewById(R.id.lv_girls);

//        new GirlPersenter<>(this).fetch();
//        girlPersenter = new GirlPersenter();
        //绑定
//        girlPersenter.onAttachView(this);

        girPersenter.fetch();

    }

    @Override
    protected GirlPersenter<IGirlView> createPersenter() {
        return new GirlPersenter<>();
    }

//    @Override  解绑
//    protected void onDestroy() {
//        super.onDestroy();
//        girlPersenter.deachView();
//    }

    @Override
    public void showLoading() {
        Toast.makeText(this,"开始加载Girls",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGirls(List<Girl> girls) {
        adapter = new GirlsAdapter(ShowGirlsAct.this,girls);
        ListViewGirls.setAdapter(adapter);
    }
}
