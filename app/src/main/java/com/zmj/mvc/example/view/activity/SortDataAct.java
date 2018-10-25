package com.zmj.mvc.example.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.entery.Person;
import com.zmj.mvc.example.model.TestSortData;

import java.util.List;

public class SortDataAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_data);

        test();
    }

    private void test(){
        List<Person> myPersonList = TestSortData.addData();
        TestSortData.sortPersons(myPersonList);
    }
}
