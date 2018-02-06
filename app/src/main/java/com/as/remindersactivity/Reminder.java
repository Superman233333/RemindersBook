package com.as.remindersactivity;

/**
 * Created by pc on 2018/2/6.
 */

public class Reminder {
    private int mId;
    private String mContent;
    private int mImportant;

    public Reminder(int id,String content,int important){
        mId = id;
        mImportant = important;
        mContent = content;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
         mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getmImportant() {
        return mImportant;
    }

    public void setmImportant(int important) {
        mImportant = important;
    }
}
