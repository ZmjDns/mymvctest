package com.zmj.mvc.example.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.zmj.mvc.example.MyApplication;
import com.zmj.mvc.example.R;
import com.zmj.mvc.example.view.TcpSocketAct;
import com.zmj.mvc.example.view.getwordsmvp.IGetWords;
import com.zmj.mvc.example.view.getwordsmvp.ParaseWordModel;
import com.zmj.mvc.example.view.getwordsmvp.ParaseWordsPersenter;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public class RecongiseFragment extends Fragment implements IGetWords ,View.OnClickListener{

    private ParaseWordsPersenter paraseWordsPersenter;

    private EditText et_words;
    private Button btn_change,btn_jumpNext;

    public RecongiseFragment() {
    }

    public static RecongiseFragment newInstence(){
        return new RecongiseFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recongise_fragment,container,false);
        et_words = view.findViewById(R.id.et_Words);
        btn_change = view.findViewById(R.id.btn_change);
        btn_jumpNext = view.findViewById(R.id.btn_jumpNext);

        btn_change.setOnClickListener(this);
        btn_jumpNext.setOnClickListener(this);

//        String word = et_words.getText().toString();
//        new ParaseWordsPersenter<>(word,this).featch();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
//        String word = et_words.getText().toString();
//        new ParaseWordsPersenter<>(word,this).featch();
    }

    @Override
    public String getWord() {
        return et_words.getText().toString().trim();
    }

    @Override
    public void showChangedWords(String s) {

        Toast.makeText(getContext(),"转换的结果：" + s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change:
                String getWord = et_words.getText().toString();
                //实例化Persenter的时候传RecongiseFragment.this或Activity.this,
                // 只是为了让persenter获得IGetWords接口的引用，在Perssenter中调用接口的方法
//                new ParaseWordsPersenter<>(/*RecongiseFragment.this*/this).featch();
                paraseWordsPersenter = new ParaseWordsPersenter();
                paraseWordsPersenter.onAttach(this);
                /*paraseWordsPersenter.featch();*/
                loginYWIM();
                break;
            case R.id.btn_jumpNext:
                startActivity(new Intent(getActivity(), TcpSocketAct.class));
            default:
                break;
        }
    }

    private void loginYWIM(){

        String userId = "testpro1";
        String password = "taobao1234";

        final YWIMKit ywimKit = getUserInfo();
        if (ywimKit != null){
            IYWLoginService loginService = ywimKit.getLoginService();
            YWLoginParam loginParam = YWLoginParam.createLoginParam(userId,password);

            loginService.login(loginParam, new IWxCallback() {
                @Override
                public void onSuccess(Object... objects) {
                    Log.d("LOGININFO", "onSuccess: ..........." );

                    Intent intent = ywimKit.getConversationActivityIntent();
//                    Intent intent = ywimKit.getTribeChattingActivityIntent(564815123);
                    getActivity().startActivity(intent);
                }

                @Override
                public void onError(int i, String s) {
                    Log.d("LOGININFO", "onProgress: 登陆失败"  + i );
                }

                @Override
                public void onProgress(int i) {

                }
            });
        }
    }

    //获取SDK对象实现
    public YWIMKit getUserInfo(){
        YWIMKit ywimKit ;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("usinfo",0);
        final String userId = sharedPreferences.getString("BCuserID","");
        if (userId != null && !"".equals(userId)){
            ywimKit = YWAPI.getIMKitInstance(userId,MyApplication.APPKEY);
            return ywimKit;
        }else {
            return null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        paraseWordsPersenter.onDeatch();
    }
}
