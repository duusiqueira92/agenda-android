package br.gov.sp.etec.agenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContatoActivity extends AppCompatActivity {
    private ContatoService service;

    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtCelular;
    private EditText txtTelefone;

    private Button btnExcluir;

    private int contatoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        service = ServiceGenerator.createService(ContatoService.class);

        //Recebe os parametros
        Intent intent = getIntent();
        contatoID = intent.getIntExtra("ID",0);


        txtNome = (EditText)findViewById(R.id.editTextNome);
        txtEmail = (EditText)findViewById(R.id.editTextEmail);
        txtTelefone = (EditText)findViewById(R.id.editTextTelefone);
        txtCelular = (EditText)findViewById(R.id.editTextCelular);

        btnExcluir = (Button)findViewById(R.id.btnExcluir);

        if( contatoID!=0) {
            carregarContato(contatoID);
            btnExcluir.setVisibility(View.VISIBLE);
        }



    } // Fim OnCreate

    public void salvar(View view){
        //1 Criar objeto Contato
        Contato contato = new Contato();
        contato.setNome(txtNome.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setTelefone(Long.parseLong(txtTelefone.getText().toString()));
        contato.setCelular(Long.parseLong(txtCelular.getText().toString()));

        //SALVAR
        if(contatoID==0){
            //2 Executar a requisição
            Call<Contato> call = service.salvarContato(contato);

            call.enqueue(new Callback<Contato>() {
                @Override
                public void onResponse(Call<Contato> call, Response<Contato> response) {
                    Toast toash = Toast.makeText(getApplicationContext(),
                            "Cadastrado com sucesso",Toast.LENGTH_LONG);
                    toash.show();
                }

                @Override
                public void onFailure(Call<Contato> call, Throwable t) {
                    Toast toash = Toast.makeText(getApplicationContext(),
                            "Erro ao Cadastrar!",Toast.LENGTH_LONG);
                    toash.show();
                }

            });

        }

        // ALTERAR
        else {
            Call<Contato> call = service.alterarContato(contatoID, contato);
            call.enqueue(new Callback<Contato>() {
                @Override
                public void onResponse(Call<Contato> call, Response<Contato> response) {
                    Toast toash = Toast.makeText(getApplicationContext(),
                            "Contato alterado com sucesso!",Toast.LENGTH_LONG);
                    toash.show();
                    finish();
                }

                @Override
                public void onFailure(Call<Contato> call, Throwable t) {
                    Toast toash = Toast.makeText(getApplicationContext(),
                            "Erro ao altarar!",Toast.LENGTH_LONG);
                    toash.show();
                }
            });

        }




    }

    public void carregarContato(int contatoID){

        //Declara
        Call<Contato> call = service.carregarContato(contatoID);

        //Executa
        call.enqueue(new Callback<Contato>() {
            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {

                txtNome.setText(response.body().getNome());
                txtEmail.setText(response.body().getEmail());
                txtTelefone.setText(String.valueOf(response.body().getTelefone()));
                txtCelular.setText(String.valueOf(response.body().getCelular()));
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Erro ao carregar o contato", Toast.LENGTH_LONG);
                toast.show();
            }
        });


    }

    public void excluir(View view) {
        //AlertDialog - pedido de confirmação

        new AlertDialog.Builder(this)
                .setTitle("Confirmação")
                .setMessage("Deseja realmente excluir esse contato")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getApplicationContext(), "Vc clicou em sim", Toast.LENGTH_SHORT).show();

                        Call<Void> call = service.excluirContato(contatoID);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                Toast.makeText(getApplicationContext(), "Contato excluido com sucesso", Toast.LENGTH_SHORT).show();

                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Erro ao excluir", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("Não", null).show();

    }

}
