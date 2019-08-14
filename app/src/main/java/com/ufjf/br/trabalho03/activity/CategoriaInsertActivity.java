package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.model.Categoria;

public class CategoriaInsertActivity extends AppCompatActivity {
    private EditText txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_insert);
        txtTitulo = findViewById(R.id.edt_tituloCategoria);
        Button botaoSalvarEtiquta = findViewById(R.id.buttonSalvarCategoria);
        botaoSalvarEtiquta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testaTela()) {
                    String titulo = txtTitulo.getText().toString();
                    Categoria categoria = new Categoria().setDescricao(titulo);
                    CategoriaDAO.getInstance().Save(categoria, CategoriaInsertActivity.this);
                    Intent intent = new Intent();
                    intent.putExtra("categoria", categoria);
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                }
            }

            private boolean testaTela() {
                boolean apto = true;
                if (TextUtils.isEmpty(txtTitulo.getText())) {
                    txtTitulo.requestFocus();
                    Toast.makeText(CategoriaInsertActivity.this,
                            "Informe um Título para a Etiqueta",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                }
                return apto;
            }
        });
    }
}
