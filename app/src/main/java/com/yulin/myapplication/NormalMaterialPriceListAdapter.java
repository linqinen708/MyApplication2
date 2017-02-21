package com.yulin.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 * Created by lin on 2016/12/22.
 */

public class NormalMaterialPriceListAdapter extends MyRecyclerViewAdapter {

    private Context mContext;
    private List<NormalMaterialPrice> mList;

    public NormalMaterialPriceListAdapter(Context mContext) {
        super(mContext);
    }
    public NormalMaterialPriceListAdapter(Context mContext, List<NormalMaterialPrice> mList) {
        super(mContext);
        this.mContext = mContext;
        this.mList = mList;
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder{
        TextView title,name,tv_materialName_1,tv_materialNum_1,tv_materialPrice_1;

        private final String TAG = "NormalMaterialPriceListAdapter";


        SimpleViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.tv_sequenceNumber);
            name = (TextView) view.findViewById(R.id.tv_Name);
            tv_materialName_1 = (TextView) view.findViewById(R.id.tv_materialName_1);
            tv_materialNum_1 = (TextView) view.findViewById(R.id.tv_materialNum_1);
            tv_materialPrice_1 = (TextView) view.findViewById(R.id.tv_materialPrice_1);

        }

    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_normal_material_price, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final SimpleViewHolder mSimpleViewHolder = ((SimpleViewHolder) holder);


        mSimpleViewHolder.title.setText(position + 1 + "");
        mSimpleViewHolder.name.setText(mList.get(position).getName());

        mSimpleViewHolder.tv_materialName_1.setText(mList.get(position).getName());
        mSimpleViewHolder.tv_materialNum_1.setText(mList.get(position).getNum());

        mSimpleViewHolder.tv_materialPrice_1.setText(mList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
