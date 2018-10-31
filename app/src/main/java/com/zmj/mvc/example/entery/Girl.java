package com.zmj.mvc.example.entery;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author Zmj
 * @date 2018/10/30
 */
public class Girl {
    private String name;
    private int pic;
    private String commit;

    public Girl(String name, int pic, String commit) {
        this.name = name;
        this.pic = pic;
        this.commit = commit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
