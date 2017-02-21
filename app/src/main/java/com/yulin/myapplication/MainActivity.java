package com.yulin.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    android.support.v7.widget.RecyclerView mRecyclerView;

    public RankDBhelper mDBhelper;
    private String name = "全部";
    private NormalMaterialPriceListAdapter mAdapter;
    public List<NormalMaterialPrice> mListCache = new ArrayList<>();
    private List<NormalMaterialPrice> mList = new ArrayList<>();

    private Context mContext;
    public static final int ADD_ITEM = 0, DELETE_ALL_DATA = 1, FILTRATE_BE_MODIFIED_DATA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        initData();
    }

    /**每次activity，onDestroy的时候，务必关闭数据库，否则容易报异常*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDBhelper.closeSQLiteDatabase();
    }

    /**初始化数据库*/
    private void initData() {


        mDBhelper = new RankDBhelper(mContext, mListCache);
        mDBhelper.onlyQueryAllData();
        if (mListCache.size() < 1) {
            addRecyclerViewData();
            mDBhelper.addAllData();
        }

        initFirstSpinner();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_normal_material_price, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setOnOptionsItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setOnOptionsItemSelected(int selecte) {
        if (mAdapter != null) {
            switch (selecte) {
                case R.id.addItem:
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.inflate_addsqlitedata_normal_material_price, null);
                    final EditText mEtName = (EditText) view.findViewById(R.id.et_name);
                    final EditText mEtNum = (EditText) view.findViewById(R.id.et_num);
                    final EditText mEtMinPrice = (EditText) view.findViewById(R.id.et_minPrice);
                    final EditText mEtMaxPrice = (EditText) view.findViewById(R.id.et_maxPrice);
                    new AlertDialog.Builder(mContext)
                            .setView(view)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDBhelper.addData(mEtName.getText().toString(), mEtNum.getText().toString(), mEtMinPrice.getText().toString() + "--" + mEtMaxPrice.getText().toString());
                                    mListCache.add(new NormalMaterialPrice(mEtName.getText().toString(),
                                            mEtNum.getText().toString(), mEtMinPrice.getText().toString() + "--" + mEtMaxPrice.getText().toString(),
                                            1
                                    ));
                                    mList.clear();
                                    mList.addAll(mListCache);
                                    mAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                case R.id.deleteAllData:
                    new AlertDialog.Builder(mContext)
                            .setTitle("温馨提示")
                            .setMessage("是否删除所有数据库信息")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDBhelper.resetData();
                                    mList.clear();
                                    mListCache.clear();
                                    addRecyclerViewData();
                                    mList.addAll(mListCache);
                                    mAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                case R.id.filtrateBeModifiedData:
                    mList.clear();
                    for (int i = 0; i < mListCache.size(); i++) {
                        if (mListCache.get(i).getIsRefresh() == 1) {
                            mList.add(mListCache.get(i));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        }
    }

    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new NormalMaterialPriceListAdapter(mContext, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener(new MyRecyclerViewAdapter.OnItemLongClickListener() {

            @Override
            public void onItemLongClick(View view2, int position) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.inflate_addsqlitedata_normal_material_price, null);
                final EditText mEtName = (EditText) view.findViewById(R.id.et_name);
                final EditText mEtNum = (EditText) view.findViewById(R.id.et_num);
                final EditText mEtMinPrice = (EditText) view.findViewById(R.id.et_minPrice);
                final EditText mEtMaxPrice = (EditText) view.findViewById(R.id.et_maxPrice);

                final NormalMaterialPrice mNormalMaterialPrice = mList.get(position);
                mEtName.setText(mNormalMaterialPrice.getName());
                mEtNum.setText(mNormalMaterialPrice.getNum());

                String price[] = mNormalMaterialPrice.getPrice().split("--");
                if (price.length > 0) {
                    mEtMinPrice.setText(price[0]);
                }
                if (price.length > 1) {
                    mEtMaxPrice.setText(price[1]);
                }

                new AlertDialog.Builder(mContext)
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mDBhelper.updateData(mNormalMaterialPrice.getName(), mEtName.getText().toString(), mEtNum.getText().toString(), mEtMinPrice.getText().toString() + "--" + mEtMaxPrice.getText().toString());
                                mList.clear();
                                mListCache.clear();
                                mDBhelper.onlyQueryAllData();

                                if (!name.equals("全部")) {
                                    reInitRecyclerViewNameData(name);
                                } else {
                                    mList.addAll(mListCache);
                                    mAdapter.notifyDataSetChanged();
                                }
//                                mAdapter.notifyItemChanged(position);
                            }
                        })
                        .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDBhelper.deleteData(mNormalMaterialPrice.getName());
                                mList.clear();
                                mListCache.clear();
                                mDBhelper.onlyQueryAllData();
                                mList.addAll(mListCache);
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

    }


    private void initFirstSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_first);

        final List<String> list = new ArrayList<>();

        for (int i = 0; i < mListCache.size(); i++) {
            list.add(mListCache.get(i).getName());
        }

        /**java中的中文排序逻辑*/
        Collator coll = Collator.getInstance(Locale.CHINESE);
        Collections.sort(list, coll);

        list.add(0, "全部");

        //android.R.layout.simple_spinner_item为系统默认样式
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                name = list.get(position).substring(2);
                if (position == 0) {
                    mList.clear();
                    mList.addAll(mListCache);
                    mAdapter.notifyDataSetChanged();
                } else {
                    reInitRecyclerViewNameData(name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void reInitRecyclerViewNameData(String name) {
        mList.clear();
        for (int i = 0; i < mListCache.size(); i++) {
            if (mListCache.get(i).getName().contains(name)) {
                mList.add(mListCache.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    private void addRecyclerViewData() {
        mListCache.add(new NormalMaterialPrice("土蜂幼虫",
                "1", "98--198", 0
        ));
        mListCache.add(new NormalMaterialPrice("杀人蜂幼虫",
                "1", "98--222", 0
        ));
        mListCache.add(new NormalMaterialPrice("山青虫",
                "1", "99--133", 0
        ));
        mListCache.add(new NormalMaterialPrice("蓝闪蝶",
                "1", "520--655", 0
        ));
    }

}
