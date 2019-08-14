package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.ufjf.br.trabalho03.R;
import com.ufjf.br.trabalho03.dao.CategoriaDAO;
import com.ufjf.br.trabalho03.dao.ProducaoDAO;
import com.ufjf.br.trabalho03.model.Categoria;
import com.ufjf.br.trabalho03.model.Producao;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

public class ProducaoEditActivity extends AppCompatActivity implements
        OnFocusChangeListener, OnClickListener {
    private Spinner spinnerCategoria;
    private TextView txtTituloProducao;
    private TextView txtDateInicio;
    private TextView txtDateFim;
    private TextView txtDescricao;
    private TextView txtHoras;
    private Producao producao;
    int mYearInicio;
    int mMonthInicio;
    int mDayInicio;
    private Calendar calendarInicio;
    private Calendar calendarFim;
    private int mDayFim;
    private int mMonthFim;
    private int mYearFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao_edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.producao = (Producao) bundle.get("producao");
        }
        this.calendarInicio = Objects.requireNonNull(this.producao).getCalendarInicio();
        this.calendarFim = Objects.requireNonNull(this.producao).getCalendarFim();
        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.spinner_layout_item, CategoriaDAO.getInstance().getCategorias(this));
        adapter.setDropDownViewResource(R.layout.spinner_layout_item);

        this.spinnerCategoria = findViewById(R.id.spinnerCategoria_edit);
        spinnerCategoria.setAdapter(adapter);
        txtTituloProducao = findViewById(R.id.edt_tituloProducao_edit);

        txtDateInicio = findViewById(R.id.edt_data_inicio_edit);
        txtDateFim = findViewById(R.id.edt_data_fim_edit);
        txtDateInicio.setOnFocusChangeListener(this);
        txtDateFim.setOnFocusChangeListener(this);
        txtDateInicio.setOnClickListener(this);
        txtDateFim.setOnClickListener(this);

        txtDescricao = findViewById(R.id.edt_descricao_producao_edit);
        txtHoras = findViewById(R.id.edt_horas_edit);
        this.updateDateInicio();
        txtDateInicio.setText(String.format(Locale.getDefault(), "%d/%02d/%d", this.mDayInicio, this.mMonthInicio + 1, this.mYearInicio));
        this.updateDateFim();
        txtDateFim.setText(String.format(Locale.getDefault(), "%d/%02d/%d", this.mDayFim, this.mMonthFim + 1, this.mYearFim));

        txtTituloProducao.setText(this.producao.getTitulo());
        txtDescricao.setText(this.producao.getDescricao());
        txtHoras.setText(Integer.toString(this.producao.getHoras()));
        spinnerCategoria.setSelection(adapter.getPosition(this.producao.getCategoria()));
        Button botaoSalvarProducao = findViewById(R.id.buttonSalvarProducao_edit);
        botaoSalvarProducao.setOnClickListener(new OnClickListener() {
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
                            .setDescricao(descricao).setCandidato(ProducaoEditActivity.this.producao.getCandidato())
                            .setInicio(dataInicio)
                            .setTitulo(titulo).setIdProducao(ProducaoEditActivity.this.producao.getIdProducao());
                    ProducaoDAO.getInstance().Update(producao, ProducaoEditActivity.this);
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
            mYearFim = c.get(Calendar.YEAR);
            mMonthFim = c.get(Calendar.MONTH);
            mDayFim = c.get(Calendar.DAY_OF_MONTH);


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDateInicio() {
        mYearInicio = this.calendarInicio.get(java.util.Calendar.YEAR);
        mMonthInicio = this.calendarInicio.get(java.util.Calendar.MONTH);
        mDayInicio = this.calendarInicio.get(java.util.Calendar.DAY_OF_MONTH);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDateFim() {
        mYearFim = this.calendarFim.get(java.util.Calendar.YEAR);
        mMonthFim = this.calendarFim.get(java.util.Calendar.MONTH);
        mDayFim = this.calendarFim.get(java.util.Calendar.DAY_OF_MONTH);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == txtDateInicio) {

            // Get Current Date
            this.updateDateInicio();


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDateInicio.setText(String.format(Locale.getDefault(), "%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));
                            java.util.Calendar calendar = java.util.Calendar.getInstance();
                            ProducaoEditActivity.this.calendarInicio.set(java.util.Calendar.YEAR, year);
                            ProducaoEditActivity.this.calendarInicio.set(java.util.Calendar.MONTH, monthOfYear);
                            ProducaoEditActivity.this.calendarInicio.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        }
                    }, mYearInicio, mMonthInicio, mDayInicio);
            datePickerDialog.show();
        }
        else if (v == txtDateFim) {

            // Get Current Date
            this.updateDateFim();


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDateFim.setText(String.format(Locale.getDefault(), "%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));
                            java.util.Calendar calendar = java.util.Calendar.getInstance();
                            ProducaoEditActivity.this.calendarFim.set(java.util.Calendar.YEAR, year);
                            ProducaoEditActivity.this.calendarFim.set(java.util.Calendar.MONTH, monthOfYear);
                            ProducaoEditActivity.this.calendarFim.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        }
                    }, mYearFim, mMonthFim, mDayFim);
            datePickerDialog.show();
        }
    }


}
