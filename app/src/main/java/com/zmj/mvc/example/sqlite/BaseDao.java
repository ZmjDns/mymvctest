package com.zmj.mvc.example.sqlite;

/**
 * @author Zmj
 * @date 2018/11/18
 */
public class BaseDao<T> implements IBaseDao<T> {
    @Override
    public long insert(T entry) {
        return 0;
    }
}
