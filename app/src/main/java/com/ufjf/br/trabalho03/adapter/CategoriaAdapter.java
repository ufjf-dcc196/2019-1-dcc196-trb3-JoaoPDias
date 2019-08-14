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
import android.widget.Toast;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.contract.HeadHunterContract;
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.model.Categoria;

import org.jetbrains.annotations.NotNull;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private Cursor cursor;
    private OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener {
        void onCategoriaClick(View producaoView, int position);
    }

    public void setOnCategoriaClickListener(OnCategoriaClickListener listener) {
        this.listener = listener;
    }

    public CategoriaAdapter(Cursor c) {
        cursor = c;
        notifyDataSetChanged();
    }

    public void setCursor(Cursor c) {
        cursor = c;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tarefaView = inflater.inflate(R.layout.itemlista_layout, viewGroup, false);
        return new ViewHolder(tarefaView, context);
    }

    public Categoria getCategoria(int position) {
        int idxTitulo = cursor.getColumnIndexOrThrow(HeadHunterContract.Categoria.COLUMN_NAME_DESCRICAO);
        int idxId = cursor.getColumnIndexOrThrow(HeadHunterContract.Categoria._ID);
        cursor.moveToPosition(position);
        Categoria categoria;
        categoria = new Categoria(cursor.getInt(idxId), cursor.getString(idxTitulo));
        return categoria;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Categoria categoria = getCategoria(i);
        viewHolder.txtTitulo.setText(categoria.makeDescription());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Categoria categoria = CategoriaAdapter.this.getCategoria(i);
                Cursor cursor = CandidatoDAO.getInstance().getCandidatosSomaHoras(viewHolder.context, categoria);
                if (cursor.getCount() > 0) {
                    Toast.makeText(viewHolder.context,
                            "Há Candidatos vinculados a essa Categoria. Não é possível excluí-la",
                            Toast.LENGTH_SHORT).show();
                } else {
                    CategoriaDAO.getInstance().Delete(categoria, viewHolder.context);
                    setCursor(CategoriaDAO.getInstance().getAll(viewHolder.context));
                }
            }
        });
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
                            listener.onCategoriaClick(v, position);
                        }
                    }
                }
            });
        }
    }
}
