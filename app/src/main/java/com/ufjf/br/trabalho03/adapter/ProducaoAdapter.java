package com.ufjf.br.trabalho03.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.contract.HeadHunterContract;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.dao.ProducaoDAO;
import com.ufjf.br.trabalho03.model.Candidato;
import com.ufjf.br.trabalho03.model.Categoria;
import com.ufjf.br.trabalho03.model.Producao;
import com.ufjf.br.trabalho03.model.Producao;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.List;

public class ProducaoAdapter extends RecyclerView.Adapter<ProducaoAdapter.ViewHolder> {
    private final List<Categoria> categorias;
    private Cursor cursor;
    private ProducaoAdapter.OnProducaoClickListener listener;
    private Candidato candidato;

    public interface OnProducaoClickListener {
        void onProducaoClick(View producaoView, int position);
    }
    public void setOnProducaoClickListener(OnProducaoClickListener listener) {
        this.listener = listener;
    }
    public ProducaoAdapter(Cursor c,Candidato candidato, List<Categoria> categorias) {
        cursor = c;
        this.candidato = candidato;
        this.categorias = categorias;
        notifyDataSetChanged();
    }

    public void setCursor(Cursor c, Candidato candidato) {
        cursor = c;
        this.candidato = candidato;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProducaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tarefaView = inflater.inflate(R.layout.itemlista_layout, viewGroup, false);
        return new ProducaoAdapter.ViewHolder(tarefaView, context);
    }

    public Producao getProducao(int position) throws ParseException {
        int idxDescricao = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao.COLUMN_NAME_DESCRICAO);
        int idxCandidato = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao.COLUMN_NAME_CANDIDATO);
        int idxCategoria = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao.COLUMN_NAME_CATEGORIA);
        int idxDataFim = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao.COLUMN_NAME_DATA_FIM);
        int idxDataInicio = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao.COLUMN_NAME_DATA_INICIO);
        int idxHoras = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao.COLUMN_NAME_HORAS);
        int idxTitulo = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao.COLUMN_NAME_TITULO);
        int idxId = cursor.getColumnIndexOrThrow(HeadHunterContract.Producao._ID);
        cursor.moveToPosition(position);
        Producao producao;
        Integer categoriaId = cursor.getInt(idxCategoria);
        Categoria categoria = new Categoria().setIdCategoria(categoriaId);
        for(int i =0; i < categorias.size();i++){
            if(categorias.get(i).getIdCategoria() == categoriaId)
            {
                categoria = categorias.get(i);
                break;
            }
        }
        producao = new Producao()
                .setIdProducao(cursor.getInt(idxId))
                .setTitulo(cursor.getString(idxTitulo))
                .setDescricao(cursor.getString(idxDescricao))
                .setInicio(cursor.getString(idxDataInicio))
                .setFim(cursor.getString(idxDataFim))
                .setCategoria(categoria)
                .setCandidato(new Candidato().setIdCandidato(cursor.getInt(idxCandidato)))
                .setHoras(cursor.getInt(idxHoras));
        return producao;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProducaoAdapter.ViewHolder viewHolder, int i) {
        final Producao producao;
        try {
            producao = getProducao(i);

            viewHolder.txtTitulo.setText(producao.makeDescription());
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ProducaoDAO.getInstance().Delete(producao, viewHolder.context);
                    ProducaoAdapter.this.setCursor(ProducaoDAO.getInstance().getAll(viewHolder.context, candidato.getIdCandidato()),candidato);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitulo = itemView.findViewById(R.id.layout_text_item);
        private final ImageButton delete = itemView.findViewById(R.id.delete_button);
        private final Context context;

        private ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onProducaoClick(v, position);
                        }
                    }
                }
            });
        }
    }
}
