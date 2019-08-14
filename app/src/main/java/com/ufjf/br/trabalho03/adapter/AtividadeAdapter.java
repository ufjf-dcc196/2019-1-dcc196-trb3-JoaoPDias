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
import com.ufjf.br.trabalho03.dao.AtividadeDAO;
import com.ufjf.br.trabalho03.model.Candidato;
import com.ufjf.br.trabalho03.model.Categoria;
import com.ufjf.br.trabalho03.model.Atividade;
import com.ufjf.br.trabalho03.model.Producao;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder> {
    private Cursor cursor;
    private AtividadeAdapter.OnAtividadeClickListener listener;
    private Producao producao;

    public interface OnAtividadeClickListener {
        void onAtividadeClick(View atividadeView, int position);
    }
    public void setOnAtividadeClickListener(OnAtividadeClickListener listener) {
        this.listener = listener;
    }
    public AtividadeAdapter(Cursor c, Producao producao) {
        cursor = c;
        this.producao = producao;
        notifyDataSetChanged();
    }

    public void setCursor(Cursor c, Producao producao) {
        cursor = c;
        this.producao = producao;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AtividadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tarefaView = inflater.inflate(R.layout.itemlista_layout, viewGroup, false);
        return new AtividadeAdapter.ViewHolder(tarefaView, context);
    }

    public Atividade getAtividade(int position) throws ParseException {
        int idxDescricao = cursor.getColumnIndexOrThrow(HeadHunterContract.Atividade.COLUMN_NAME_DESCRICAO);
        int idxDataAtividade = cursor.getColumnIndexOrThrow(HeadHunterContract.Atividade.COLUMN_NAME_DATA_ATIVIDADE);
        int idxProducao = cursor.getColumnIndexOrThrow(HeadHunterContract.Atividade.COLUMN_NAME_PRODUCAO);
        int idxHoras = cursor.getColumnIndexOrThrow(HeadHunterContract.Atividade.COLUMN_NAME_HORAS);
        int idxId = cursor.getColumnIndexOrThrow(HeadHunterContract.Atividade._ID);
        cursor.moveToPosition(position);
        Atividade atividade;
        atividade = new Atividade()
                .setIdAtividade(cursor.getInt(idxId))
                .setDescricao(cursor.getString(idxDescricao))
                .setProducao(producao)
                .setHoras(cursor.getInt(idxHoras))
                .setDataAtividade(cursor.getString(idxDataAtividade));
        return atividade;
    }

    @Override
    public void onBindViewHolder(@NonNull final AtividadeAdapter.ViewHolder viewHolder, int i) {
        final Atividade atividade;
        try {
            atividade = getAtividade(i);

            viewHolder.txtTitulo.setText(atividade.makeDescription());
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AtividadeDAO.getInstance().Delete(atividade, viewHolder.context);
                    setCursor(AtividadeDAO.getInstance().getAll(viewHolder.context, atividade.getIdAtividade()),producao);
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
                            listener.onAtividadeClick(v, position);
                        }
                    }
                }
            });
        }
    }
}
