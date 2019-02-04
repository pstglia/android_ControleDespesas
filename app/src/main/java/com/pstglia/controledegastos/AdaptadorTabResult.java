package com.pstglia.controledegastos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private ArrayList<String[]> list = new ArrayList<String[]>();
    private Context context;

    public AdaptadorTabResult(ArrayList<String[]> list, Context context) {
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

        Log.i("CTRLGASTOSDBG","Qtde de itens no onBindViewHolder: " + String.valueOf(list.size())  );

        if (list.size() > 0) {
            holder.textView1.setText(list.get(position)[0]);
            holder.textView2.setText(list.get(position)[1]);
            holder.textView3.setText(list.get(position)[2]);
            holder.textView4.setText(list.get(position)[3]);
            holder.textView5.setText(list.get(position)[4]);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.txtColuna1);
            textView2 = itemView.findViewById(R.id.txtColuna2);
            textView3 = itemView.findViewById(R.id.txtColuna3);
            textView4 = itemView.findViewById(R.id.txtColuna4);
            textView5 = itemView.findViewById(R.id.txtColuna5);
        }
    }
}
