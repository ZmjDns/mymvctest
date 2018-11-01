package com.zmj.mvc.example.view.getwordsmvp;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public interface IWordsModel {
    void parasrWord(parasrCompleteListner completeListner);

    interface parasrCompleteListner{
        void complete(String s);
    }
}
