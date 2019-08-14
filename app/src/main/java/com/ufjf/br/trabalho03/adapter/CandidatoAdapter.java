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
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.model.Categoria;
import com.ufjf.br.trabalho03.model.Candidato;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;

public class CandidatoAdapter extends RecyclerView.Adapter<CandidatoAdapter.ViewHolder> {
    private Cursor cursor;
    private OnCandidatoClickListener listener;
    private final Categoria categoria;

    public interface OnCandidatoClickListener {
        void onCandidatoClick(View candidatoView, int position);
    }

    public void setOnCandidatoClickListener(OnCandidatoClickListener listener) {
        this.listener = listener;
    }

    public CandidatoAdapter(Cursor c, Categoria categoria) {
        cursor = c;
        this.categoria = categoria;
        notifyDataSetChanged();
    }

    public void setCursor(Cursor c) {
        cursor = c;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CandidatoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View candidatoView = inflater.inflate(R.layout.itemlista_layout, viewGroup, false);
        return new ViewHolder(candidatoView, context);
    }

    public Candidato getCandidato(int position) {
        int idxNome = cursor.getColumnIndexOrThrow(HeadHunterContract.Candidato.COLUMN_NAME_NOME);
        int idxEmail = cursor.getColumnIndexOrThrow(HeadHunterContract.Candidato.COLUMN_NAME_EMAIL);
        int idxDataNasc = cursor.getColumnIndexOrThrow(HeadHunterContract.Candidato.COLUMN_NAME_NASCIMENTO);
        int idxPerfil = cursor.getColumnIndexOrThrow(HeadHunterContract.Candidato.COLUMN_NAME_PERFIL);
        int idxTelefone = cursor.getColumnIndexOrThrow(HeadHunterContract.Candidato.COLUMN_NAME_TELEFONE);
        int idxId = cursor.getColumnIndexOrThrow(HeadHunterContract.Candidato._ID);
        int idxSoma = cursor.getColumnIndexOrThrow(HeadHunterContract.Candidato.COLUMN_NAME_SOMA);
        cursor.moveToPosition(position);
        Candidato candidato = null;
        try {
            candidato = new Candidato().setIdCandidato(cursor.getInt(idxId))
                    .setNomeCandidato(cursor.getString(idxNome))
                    .setEmail(cursor.getString(idxEmail))
                    .setDataNascimento(
                            cursor.getString(idxDataNasc))
                    .setPerfil(cursor.getString(idxPerfil))
                    .setTelefone(cursor.getString(idxTelefone))
                    .setTotalHoras(cursor.getInt(idxSoma));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return candidato;
    }

    @Override
    public void onBindViewHolder(@NonNull final CandidatoAdapter.ViewHolder viewHolder, final int i) {
        final Candidato candidato = getCandidato(i);
        if (candidato != null) {
            viewHolder.txtTitulo.setText(candidato.makeDescription());
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CandidatoDAO.getInstance().Delete(candidato, viewHolder.context);
                    setCursor(CandidatoDAO.getInstance().getCandidatosSomaHoras(viewHolder.context, categoria));
                    notifyItemRemoved(i); // bug ao final da lista
                }
            });
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
                            listener.onCandidatoClick(v, position);
                        }
                    }
                }
            });
        }
    }
}

