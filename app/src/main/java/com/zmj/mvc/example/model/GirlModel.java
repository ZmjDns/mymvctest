package com.zmj.mvc.example.model;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.zmj.mvc.example.MyApplication;
import com.zmj.mvc.example.R;
import com.zmj.mvc.example.entery.Girl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zmj
 * @date 2018/10/30
 */
public class GirlModel implements IGirlModel {
    @Override
    public void loadGirl(GirlOnLoadListener listener) {
        List<Girl> data = new ArrayList<>();

        data.add(new Girl("aa", R.drawable.bg_header,"五星好评"));
        data.add(new Girl("aa", R.drawable.avatar_round,"五星好评"));
        data.add(new Girl("aa", R.drawable.avatar_round,"五星好评"));
        data.add(new Girl("aa", R.drawable.avatar_round,"五星好评"));
        data.add(new Girl("aa", R.drawable.avatar_round,"五星好评"));

        listener.onComplete(data);
    }
}
