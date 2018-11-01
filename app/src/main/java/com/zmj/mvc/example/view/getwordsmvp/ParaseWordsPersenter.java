package com.zmj.mvc.example.view.getwordsmvp;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public class ParaseWordsPersenter <T extends IGetWords> {

    private IGetWords iGetWords;

//    private String word;

    private ParaseWordModel paraseWordModel;

    public ParaseWordsPersenter(T view) {
        this.iGetWords = view;
//        this.word = word;
        this.paraseWordModel = new ParaseWordModel(iGetWords.getWord());
    }

    public void featch(){
        if (iGetWords != null && paraseWordModel != null){
            paraseWordModel.parasrWord(new IWordsModel.parasrCompleteListner() {
                @Override
                public void complete(String s) {
                    iGetWords.showChangedWords(s);
                }
            });
        }
    }

}
