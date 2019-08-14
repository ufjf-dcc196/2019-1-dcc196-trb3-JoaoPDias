package com.ufjf.br.trabalho03.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;

import java.util.Calendar;

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
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.model.Candidato;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

public class CandidatoEditActivity extends AppCompatActivity implements
        View.OnFocusChangeListener, View.OnClickListener {
    private Candidato candidato;
    private Calendar calendar;
    private EditText txtDate;
    private EditText txtPerfil;
    private EditText txtTelefone;
    private EditText txtNome;
    private EditText txtEmail;
    private int mYear, mMonth, mDay;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_edit);
        Bundle bundle = getIntent().getExtras();
        this.candidato = (Candidato) Objects.requireNonNull(bundle).get("candidato");
        this.calendar = Objects.requireNonNull(this.candidato).getCalendar();
        txtNome = findViewById(R.id.edt_nome_edit);
        txtDate = findViewById(R.id.edt_data_nasc_edit);
        txtDate.setOnFocusChangeListener(this);
        txtPerfil = findViewById(R.id.edt_perfil_edit);
        txtEmail = findViewById(R.id.edt_email_edit);
        txtTelefone = findViewById(R.id.edt_telefone_edit);
        txtDate.setOnFocusChangeListener(this);
        txtDate.setOnClickListener(this);
        this.updateDate();
        txtDate.setText(String.format(Locale.getDefault(), "%d/%02d/%d", this.mDay, this.mMonth + 1, this.mYear));
        txtEmail.setText(this.candidato.getEmail());
        txtNome.setText(this.candidato.getNomeCandidato());
        txtPerfil.setText(this.candidato.getPerfil());
        txtTelefone.setText(this.candidato.getTelefone());
        Button botaoSalvarCandidato = findViewById(R.id.buttonSalvarCandidato_edit);
        botaoSalvarCandidato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testaTela()) {
                    String dataNasc = String.format("%s", txtDate.getText().toString());
                    String nome = txtNome.getText().toString();
                    String perfil = txtPerfil.getText().toString();
                    String email = txtEmail.getText().toString();
                    String telefone = txtTelefone.getText().toString();
                    try {
                        Candidato candidato = new Candidato().setDataNascimento(dataNasc).setEmail(email).setNomeCandidato(nome).setPerfil(perfil)
                                .setTelefone(telefone).setIdCandidato(CandidatoEditActivity.this.candidato.getIdCandidato());
                        CandidatoDAO.getInstance().Update(candidato, CandidatoEditActivity.this);
                        Intent intent = new Intent();
                        intent.putExtra("candidato", candidato);
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
                    Toast.makeText(CandidatoEditActivity.this,
                            "Informe um Nome para o Candidato",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtTelefone.getText())) {
                    txtTelefone.requestFocus();
                    Toast.makeText(CandidatoEditActivity.this,
                            "Informe o Telefone para o Candidato",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtEmail.getText())) {
                    txtDate.requestFocus();
                    Toast.makeText(CandidatoEditActivity.this,
                            "Informe um E-mail para o Candidato",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtPerfil.getText())) {
                    txtPerfil.requestFocus();
                    Toast.makeText(CandidatoEditActivity.this,
                            "Informe um Perfil para o candidato",
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
            this.updateDate();


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(String.format(Locale.getDefault(), "%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));
                            Calendar calendar = Calendar.getInstance();
                            CandidatoEditActivity.this.calendar.set(Calendar.YEAR, year);
                            CandidatoEditActivity.this.calendar.set(Calendar.MONTH, monthOfYear);
                            CandidatoEditActivity.this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDate() {
        mYear = this.calendar.get(Calendar.YEAR);
        mMonth = this.calendar.get(Calendar.MONTH);
        mDay = this.calendar.get(Calendar.DAY_OF_MONTH);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == txtDate) {

            // Get Current Date
            this.updateDate();


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(String.format(Locale.getDefault(), "%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));
                            Calendar calendar = Calendar.getInstance();
                            CandidatoEditActivity.this.calendar.set(Calendar.YEAR, year);
                            CandidatoEditActivity.this.calendar.set(Calendar.MONTH, monthOfYear);
                            CandidatoEditActivity.this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
