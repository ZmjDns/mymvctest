package com.zmj.mvc.example.view.getwordsmvp;

import java.lang.ref.WeakReference;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public class ParaseWordsPersenter <T extends IGetWords> {

//    private IGetWords iGetWords;

    private WeakReference<T> iGetWords;

//    private String word;

    private ParaseWordModel paraseWordModel;

    public ParaseWordsPersenter(/*T view*/) {
//        this.iGetWords = view;
//        this.word = word;
//        this.paraseWordModel = new ParaseWordModel(iGetWords.getWord());
    }

    public void onAttach(T view){
        iGetWords = new WeakReference<>(view);
        paraseWordModel = new ParaseWordModel(iGetWords.get().getWord());
    }

    public void onDeatch(){
        iGetWords.clear();
    }



    public void featch(){
        if (iGetWords.get() != null && paraseWordModel != null){
            paraseWordModel.parasrWord(new IWordsModel.parasrCompleteListner() {
                @Override
                public void complete(String s) {
                    iGetWords.get().showChangedWords(s);
                }
            });
        }
    }

}
