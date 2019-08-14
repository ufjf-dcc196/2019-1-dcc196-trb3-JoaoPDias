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
import com.ufjf.br.trabalho03.dao.CandidatoDAO;
import com.ufjf.br.trabalho03.model.Candidato;

import java.text.ParseException;
import java.util.Locale;

public class CandidatoInsertActivity extends AppCompatActivity implements
        View.OnFocusChangeListener {
    private EditText txtDate;
    private EditText txtPerfil;
    private EditText txtTelefone;
    private EditText txtNome;
    private EditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_insert);
        txtNome = findViewById(R.id.edt_nome);
        txtDate = findViewById(R.id.edt_data_nasc);
        txtDate.setOnFocusChangeListener(this);
        txtPerfil = findViewById(R.id.edt_perfil);
        txtEmail = findViewById(R.id.edt_email);
        txtTelefone = findViewById(R.id.edt_telefone);
        Button botaoSalvarCandidato = findViewById(R.id.buttonSalvarCandidato);
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
                                .setTelefone(telefone);
                        CandidatoDAO.getInstance().Save(candidato, CandidatoInsertActivity.this);
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
                    Toast.makeText(CandidatoInsertActivity.this,
                            "Informe um Nome para o Candidato",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtTelefone.getText())) {
                    txtTelefone.requestFocus();
                    Toast.makeText(CandidatoInsertActivity.this,
                            "Informe o Telefone para o Candidato",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtEmail.getText())) {
                    txtDate.requestFocus();
                    Toast.makeText(CandidatoInsertActivity.this,
                            "Informe um E-mail para o Candidato",
                            Toast.LENGTH_SHORT).show();
                    apto = false;
                } else if (TextUtils.isEmpty(txtPerfil.getText())) {
                    txtPerfil.requestFocus();
                    Toast.makeText(CandidatoInsertActivity.this,
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
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(String.format(Locale.getDefault(),"%d/%02d/%d", dayOfMonth, monthOfYear + 1, year));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
