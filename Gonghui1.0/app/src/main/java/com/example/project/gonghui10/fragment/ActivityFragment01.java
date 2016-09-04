package com.example.project.gonghui10.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.gonghui10.R;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ActivityFragment01 extends Fragment {
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private HomeAdapter mAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_activity01, container, false);
		initData();
		mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recycleview);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
		mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
		return view;
	}

	protected void initData(){


		mDatas = new ArrayList<>();
		for(int i='A';i<'Z';i++) {
			mDatas.add("" + (char)i);
		}
	}

	class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

		@Override
		public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_activity_item,
					parent,false));
			return holder;
		}

		@Override
		public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
			holder.tv.setText(mDatas.get(position));
		}

		@Override
		public int getItemCount() {
			return mDatas.size();
		}

		class MyViewHolder extends ViewHolder {
			TextView tv;
			public MyViewHolder(View itemView) {
				super(itemView);
				tv = (TextView) itemView.findViewById(R.id.publisher);
			}
		}
	}
}
