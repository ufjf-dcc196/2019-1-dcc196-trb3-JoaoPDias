package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.dao.AtividadeDAO;
import com.ufjf.br.trabalho03.model.Atividade;
import com.ufjf.br.trabalho03.model.Producao;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

public class AtividadeInsertActivity extends AppCompatActivity implements
        View.OnFocusChangeListener {
    private EditText txtDate;
    private EditText txtNome;
    private EditText txtHorasAtividade;
    private Producao producao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_insert);
        Bundle bundle = getIntent().getExtras();
        this.producao = (Producao) Objects.requireNonNull(bundle).get("producao");
        txtNome = findViewById(R.id.edt_nome_atividade);
        txtDate = findViewById(R.id.edt_data_atividade);
        txtDate.setOnFocusChangeListener(this);
        txtHorasAtividade = findViewById(R.id.edt_total_horas_atividade);
        Button botaoSalvarAtividade = findViewById(R.id.buttonSalvarAtividade);
        botaoSalvarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testaTela()) {
                    String dataAtividade = String.format("%s", txtDate.getText().toString());
                    String nome = txtNome.getText().toString();
                    Integer horas = Integer.valueOf(txtHorasAtividade.getText().toString());
                    try {
                        Atividade atividade = new Atividade().setDataAtividade(dataAtividade)
                                .setDescricao(nome)
                                .setHoras(horas).setProducao(AtividadeInsertActivity.this.producao);
                        AtividadeDAO.getInstance().Save(atividade, AtividadeInsertActivity.this);
                        Intent intent = new Intent();
                        intent.putExtra("atividade", atividade);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

            private boolean testaTela() {
                boolean apto = true;
                if (TextUtils.isEmpty(txtNome.getText())) {
                    txtNome.requestFocus();
                    Toast.makeText(AtividadeInsertActivity.this,
                            "Informe um Nome para a Atividade",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtHorasAtividade.getText())) {
                    txtDate.requestFocus();
                    Toast.makeText(AtividadeInsertActivity.this,
                            "Informe um total de horas para a Atividade",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtDate.getText())) {
                    txtDate.requestFocus();
                    Toast.makeText(AtividadeInsertActivity.this,
                            "Informe uma data para a Atividade",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                }
                return apto;
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == txtDate && hasFocus) {

// Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(String.format(Locale.getDefault(), "%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
