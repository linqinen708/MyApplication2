package com.yulin.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
public class SQlite extends SQLiteOpenHelper {

    private final String TAG = "SQlite ";

    public static final String TABLE_NAME = "rank";
    public static int version = 1;

    List<NormalMaterialPrice> mListCache;

    public SQlite(Context context, String name, CursorFactory factory,
                  int version) {
        super(context, TABLE_NAME+".db"/*数据库名*/, null/*factory 一般不用*/, version);
    }
    public SQlite(Context context,List<NormalMaterialPrice> mListCache){
        super(context, TABLE_NAME+".db", null/*factory 一般不用*/, version);
        this.mListCache = mListCache;


    }

    //onCreate用来创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {

        //当调用了getReadableDataBase或者getWritealbeDatabase函数就会创建数据库
        db.execSQL("create table " + TABLE_NAME + "(_id integer primary key autoincrement , name text , num integer , price integer , isRefresh integer)");
		/**/
        //参数1.表名
        //参数2.指定是否可以为空（尽量不要为空）
        //参数3.要保存的数据
        //key是字段的名字，必须和建表时字段相同

        for (int i = 0; i < mListCache.size(); i++) {
            ContentValues contentValuses = new ContentValues();
            contentValuses.put("name", mListCache.get(i).getName());
            contentValuses.put("num", mListCache.get(i).getNum());
            contentValuses.put("price", mListCache.get(i).getPrice());
            contentValuses.put("isRefresh", mListCache.get(i).getIsRefresh());

            db.insert(SQlite.TABLE_NAME, "name", contentValuses);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}