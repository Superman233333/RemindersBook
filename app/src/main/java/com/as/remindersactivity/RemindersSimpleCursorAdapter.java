package com.as.remindersactivity;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pc on 2018/2/6.
 */

public class RemindersSimpleCursorAdapter extends SimpleCursorAdapter{

    public RemindersSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    public RemindersSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    //to use a viewholder , you must override the following two methods and使用viewholder，必须重写以下两种方法
    //define a ViewHolder class定义一个viewholder类

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null){
            holder = new ViewHolder();
            holder.colImp = cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_IMPORTANT);
            holder.listTab = view.findViewById(R.id.row_tab);
            view.setTag(holder);
        }
        if (cursor.getInt(holder.colImp)>0){
            holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.orange));
        }else {
            holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }
    static class ViewHolder{
        //store the column index
        int colImp;
        //store the view
        View listTab;
    }
}
