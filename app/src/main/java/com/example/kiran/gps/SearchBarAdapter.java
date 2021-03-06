package com.example.kiran.gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.kiran.gps.databinding.RowItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class SearchBarAdapter extends BaseAdapter implements Filterable {

    List<String> filteredData;
    private final List<String> mStringFilterList;
    private ValueFilter valueFilter;
    private LayoutInflater inflater;

    SearchBarAdapter(List<String> canceltype) {
        filteredData = canceltype;
        mStringFilterList = canceltype;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public String getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @SuppressLint
                ("ViewHolder") RowItemBinding rowItemBinding = DataBindingUtil.inflate(Objects.requireNonNull(inflater), R.layout.row_item, parent, false);
        rowItemBinding.stringName.setText(filteredData.get(position));
        return rowItemBinding.getRoot();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private void searchBarFilterIteration(CharSequence constraint, List<String> filterList) {
        for (int i = 0; i < mStringFilterList.size(); i++) {
            if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                filterList.add(mStringFilterList.get(i));
            }
        }
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                searchBarFilterIteration(constraint, filterList);
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            filteredData = (List<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
