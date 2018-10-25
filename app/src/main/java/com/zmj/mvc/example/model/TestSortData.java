package com.zmj.mvc.example.model;

import android.util.Log;

import com.zmj.mvc.example.entery.Person;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author Zmj
 * @date 2018/10/25
 */
public class TestSortData {

    public static List<Person> addData() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person("张三", "15845669428"));
        personList.add(new Person("李四", "15845669428"));
        personList.add(new Person("王五", "15845669428"));
        personList.add(new Person("马六", "15845669428"));
        personList.add(new Person("鬼泣", "15845669428"));
        personList.add(new Person("方八", "15845669428"));
        personList.add(new Person("刘九", "15845669428"));

        return personList;
    }

    public static List<Person> sortPersons(List<Person> personList) {
//        List<Person> newPersonList = new ArrayList<>();

        Collections.sort(personList, new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                String s1 = o1.getName();
                String s2 = o2.getName();
                return Collator.getInstance(Locale.CHINESE).compare(s1, s2);
            }
        });


//        Comparator cmp = Collator.getInstance(Locale.CHINA);
//        Collections.sort(personList,cmp);

        for (int i = 0; i < personList.size(); i++){
            Log.d("TEST", "姓名：" + personList.get(i).getName() + "电话：" + personList.get(i).getPhone());
        }

        return null;
    }
}
