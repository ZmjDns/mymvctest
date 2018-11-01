package com.zmj.mvc.example.view.getwordsmvp;

import com.zmj.mvc.example.utils.CharacterParser;
import com.zmj.mvc.example.utils.MyCharacterParaser;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public class ParaseWordModel implements IWordsModel {

    private String words;

    public ParaseWordModel(String words) {
        this.words = words;
    }

    @Override
    public void parasrWord(parasrCompleteListner completeListner) {

        completeListner.complete(CharacterParser.getInstance().getSelling(words));

//        completeListner.complete(MyCharacterParaser.getInstance().getPinyin(words));
    }
}
