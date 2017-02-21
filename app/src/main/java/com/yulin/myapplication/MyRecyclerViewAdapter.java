/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
package com.yulin.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "MyRecyclerViewAdapter";

    private Context mContext;

     MyRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private  OnItemLongClickListener mOnItemLongClickListener;
    private  OnItemClickListener mOnItemClickListener;

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, final int position);
    }
    public interface OnItemClickListener{
        void onItemClick(View view, final int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener = mOnItemLongClickListener;

    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener != null){
                    mOnItemLongClickListener.onItemLongClick(v,holder.getAdapterPosition());
                }
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i(TAG, "onClick: mOnItemClickListener:"+mOnItemClickListener+"\tview:"+view+"\tholder:"+holder);
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view,holder.getAdapterPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return 0;
    }


}
