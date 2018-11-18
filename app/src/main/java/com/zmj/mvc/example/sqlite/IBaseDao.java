package com.zmj.mvc.example.sqlite;

/**
 * @author Zmj
 * @date 2018/11/18
 * 规范所有的数据库操作
 */
public interface IBaseDao <T>{

    public long insert(T entry);
}
