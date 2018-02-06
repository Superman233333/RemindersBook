package com.as.remindersactivity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RemindersActivity extends AppCompatActivity {

    private ListView mListView;
    private RemindersDbAdapter mDbAdapter;
    private RemindersSimpleCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        mListView = (ListView) findViewById(R.id.reminders_list_view);
        mListView.setDivider(null);
        mDbAdapter = new RemindersDbAdapter(this);
        mDbAdapter.open();

        Cursor cursor = mDbAdapter.fetchAllReminders();

        //from columns defined in the db从DB中定义的列中定义
        String[] from = new String[]{
                RemindersDbAdapter.COL_CONTENT
        };

        //to the ids of views in the layout对布局视图中的ID
        int[] to = new int[]{
                R.id.row_text
        };

        mCursorAdapter = new RemindersSimpleCursorAdapter(
                //context
                RemindersActivity.this,
                //the layout.reminders_row
                R.layout.reminders_row,
                //cursor
                cursor,
                //from columns defined in the db
                from,
                //to the ids of views in the layout
                to,
                //flag - not used
                0);
        //the cursorAdapter (controller) is now updating the listView (view)的CursorAdapter（控制器）正在更新视图（View）
        //with data from the db (model)来自DB（模型）的数据
        mListView.setAdapter(mCursorAdapter);
    }
    //Abbreviated for brevity略为简洁
}
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this,
//                R.layout.reminders_row,
//                R.id.row_text,
//                new String[] {"first recond","second record","third record"}
//        );
//        mListView.setAdapter(arrayAdapter);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_new:
//                Log.d(getLocalClassName(),"create new Reminder");
//                return true;
//            case R.id.action_exit:
//                finish();
//                return true;
//            default:
//                return false;
//        }
//    }

