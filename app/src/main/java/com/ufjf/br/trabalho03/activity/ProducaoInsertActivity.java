package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.dao.ProducaoDAO;
import com.ufjf.br.trabalho03.model.Candidato;
import com.ufjf.br.trabalho03.model.Categoria;
import com.ufjf.br.trabalho03.model.Producao;

import java.text.ParseException;
import java.util.Locale;

public class ProducaoInsertActivity extends AppCompatActivity implements
        OnFocusChangeListener {
    private Spinner spinnerCategoria;
    private TextView txtTituloProducao;
    private TextView txtDateInicio;
    private TextView txtDateFim;
    private TextView txtDescricao;
    private TextView txtHoras;
    private Candidato candidato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao_insert);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.candidato = (Candidato) bundle.get("candidato");
        }

        this.spinnerCategoria = findViewById(R.id.spinnerCategoria);
        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.spinner_layout_item, CategoriaDAO.getInstance().getCategorias(this));
        adapter.setDropDownViewResource(R.layout.spinner_layout_item);
        spinnerCategoria.setAdapter(adapter);
        txtTituloProducao = findViewById(R.id.edt_tituloProducao);
        txtDateInicio = findViewById(R.id.edt_data_inicio);
        txtDateFim = findViewById(R.id.edt_data_fim);
        txtDateInicio.setOnFocusChangeListener(this);
        txtDateFim.setOnFocusChangeListener(this);
        txtDescricao = findViewById(R.id.edt_descricao_producao);
        txtHoras = findViewById(R.id.edt_horas);
        Button botaoSalvarProducao = findViewById(R.id.buttonSalvarProducao);
        botaoSalvarProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String dataInicio = String.format("%s", txtDateInicio.getText().toString());
                    String dataFim = String.format("%s", txtDateFim.getText().toString());
                    String titulo = txtTituloProducao.getText().toString();
                    Categoria categoria = (Categoria) spinnerCategoria.getSelectedItem();
                    String descricao = txtDescricao.getText().toString();
                    Integer horas = Integer.parseInt(txtHoras.getText().toString());
                    try {
                        Producao producao = new Producao().setHoras(horas)
                                .setCategoria(categoria)
                                .setFim(dataFim)
                                .setDescricao(descricao).setCandidato(ProducaoInsertActivity.this.candidato)
                                .setInicio(dataInicio)
                                .setTitulo(titulo);
                        ProducaoDAO.getInstance().Save(producao, ProducaoInsertActivity.this);
                        Intent intent = new Intent();
                        intent.putExtra("producao", producao);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int mYearInicio;
        int mMonthInicio;
        int mDayInicio;

        if (v == txtDateInicio && hasFocus) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYearInicio = c.get(Calendar.YEAR);
            mMonthInicio = c.get(Calendar.MONTH);
            mDayInicio = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDateInicio.setText(String.format(Locale.getDefault(),"%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));

                        }
                    }, mYearInicio, mMonthInicio, mDayInicio);
            datePickerDialog.show();
        }

        else if (v == txtDateFim && hasFocus) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYearInicio = c.get(Calendar.YEAR);
            mMonthInicio = c.get(Calendar.MONTH);
            mDayInicio = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDateFim.setText(String.format(Locale.getDefault(),"%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));

                        }
                    }, mYearInicio, mMonthInicio, mDayInicio);
            datePickerDialog.show();
        }
    }


}
