package com.example.tt.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tt.PdfViewerActivity;
import com.example.tt.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.PdfListViewHolder> implements Filterable {
    Context context;
    public ArrayList<File> list=new ArrayList<>();
    ArrayList<File> allList;
    public PdfListAdapter(Context context){
        this.context=context;
       // allList=new ArrayList<File>(list);
    }
    @NotNull
    @Override
    public PdfListViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.pdf_list_item,parent,false);
        return new PdfListViewHolder(view);
    }

    @Override
    public void onBindViewHolder( PdfListViewHolder holder, int position) {
        holder.pdfName.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, PdfViewerActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
                Toast.makeText(context,list.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public void updateList(ArrayList newlist){
        list.clear();
        list.addAll(newlist);
        allList=new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Log.d("list size", String.valueOf(allList.size()));
        return exampleFilter;
    }
    private final Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<File> sample = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                sample.addAll(allList);
            } else {
                String pattern = constraint.toString().toLowerCase().trim();
                for (File data : allList) {
                    String item = data.getName();
                    if (item.toLowerCase().contains(pattern)) {
                        sample.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = sample;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList<File>)results.values);
            notifyDataSetChanged();
        }
    };

    public class PdfListViewHolder extends RecyclerView.ViewHolder {
        TextView pdfName;
        public PdfListViewHolder( View itemView) {
            super(itemView);
            pdfName=itemView.findViewById(R.id.txtPdfName);
        }
    }
}