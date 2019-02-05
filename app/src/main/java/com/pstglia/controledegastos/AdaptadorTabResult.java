package com.pstglia.controledegastos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pstglia.controledegastos.interfaces.CliqueBotaoRecycler;

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
    private static CliqueBotaoRecycler mListener;

    public AdaptadorTabResult(ArrayList<String[]> list, Context context, CliqueBotaoRecycler pBotaoClicked) {
        this.list = list;
        this.context = context;
        this.mListener = pBotaoClicked;
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

    public void remove(int posicao) {
        list.remove(posicao);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        private ImageView imgDelLinha;
        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.txtColuna1);
            textView2 = itemView.findViewById(R.id.txtColuna2);
            textView3 = itemView.findViewById(R.id.txtColuna3);
            textView4 = itemView.findViewById(R.id.txtColuna4);
            textView5 = itemView.findViewById(R.id.txtColuna5);
            imgDelLinha = itemView.findViewById(R.id.imgDelLinha);
            imgDelLinha.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String vId = textView1.getText().toString();
            mListener.botaoClicado(v,vId,getAdapterPosition());
        }
    }
}
