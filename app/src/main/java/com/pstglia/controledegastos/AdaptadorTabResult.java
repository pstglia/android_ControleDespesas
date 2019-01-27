package com.pstglia.controledegastos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
/**
 *  Referências / references:
 *  https://medium.com/pen-bold-kiln-press/tablelayout-vs-gridlayout-ae28be87b4b6
 *  https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 *
 */
public class AdaptadorTabResult extends RecyclerView.Adapter<AdaptadorTabResult.ViewHolder> {

    private ArrayList<String> list = new ArrayList<>();
    private Context context;

    public AdaptadorTabResult(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_layout_lista_despesas, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtColuna);
        }
    }
}
