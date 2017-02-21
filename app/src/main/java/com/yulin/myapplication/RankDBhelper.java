package com.yulin.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;


//数据库的增删改查
public class RankDBhelper{

    private final String TAG = "RankDBhelper";
    private List<NormalMaterialPrice> mListCache;

    private SQlite sqlite;
    private SQLiteDatabase dataBase;
    public RankDBhelper(Context context,List<NormalMaterialPrice> mListCache){
        this.mListCache = mListCache;
        if (sqlite == null) {
            sqlite = new SQlite(context,mListCache);
        }
        dataBase = sqlite.getWritableDatabase();
    }



    public void closeSQLiteDatabase(){
        if (dataBase.isOpen()) {
            dataBase.close();
        }
    }

    public Cursor cursorQuery(){
        String querySql = "select * from " + SQlite.TABLE_NAME;
        return dataBase.rawQuery(querySql, null);
    }

    public void onlyQueryAllData(){

        //cursor游标（迭代器）方法的各个参数意义：1.表名 2.列名 3和4.需要查询的条件                          5和6.其他条件   7排序的方式
        Cursor cursor = dataBase.query(SQlite.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            NormalMaterialPrice mBean = new NormalMaterialPrice();
            mBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            mBean.setNum(cursor.getString(cursor.getColumnIndex("num")));
            mBean.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            mBean.setIsRefresh(cursor.getInt(cursor.getColumnIndex("isRefresh")));
            mListCache.add(mBean);
        }
//每次游标cursor用完后都需要关闭，否则会报异常
        cursor.close();
    }

    //查询并更新数据
    public void updateData(String oldName,String newName , String num , String price){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        contentValues.put("num", num);
        contentValues.put("price", price);
        contentValues.put("isRefresh", 1);
        dataBase.update(SQlite.TABLE_NAME, contentValues, "name=?", new String[]{oldName});
    }

    /**删除数据库条目*/
    public void deleteData(String oldName){
        dataBase.delete(SQlite.TABLE_NAME,  "name=?", new String[]{oldName});
    }

    //清空数据库存档
    public void resetData(){
        dataBase.delete(SQlite.TABLE_NAME, null, null);
    }

    /**增加单条数据库条目*/
    public void addData(String name , String num , String price){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("num", num);
        contentValues.put("price", price);
        contentValues.put("isRefresh", 1);
        dataBase.insert(SQlite.TABLE_NAME, "name", contentValues);
    }

    /**增加所有数据库条目*/
    public void addAllData(){
        for (int i = 0; i < mListCache.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", mListCache.get(i).getName());
            contentValues.put("num", mListCache.get(i).getNum());
            contentValues.put("price", mListCache.get(i).getPrice());
            contentValues.put("isRefresh", mListCache.get(i).getIsRefresh());
            dataBase.insert(SQlite.TABLE_NAME, "name", contentValues);
        }
    }


}
