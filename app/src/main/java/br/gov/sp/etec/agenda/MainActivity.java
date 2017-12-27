package br.gov.sp.etec.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView listViewContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContatoActivity.class);

                startActivity(intent);


            }
        });

        listViewContatos = (ListView)findViewById(R.id.listViewContatos);



    }

    @Override
    protected void onStart(){
        super.onStart();
        carregarTodos();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void carregarTodos(){

        ContatoService service = ServiceGenerator.createService(ContatoService.class);

        Call<List<Contato>> call = service.carregarTodos();

        call.enqueue(new Callback<List<Contato>>() {

            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                //Toast (mendagem rápida
                Toast.makeText(MainActivity.this, "Aeee, deu certo!",
                    Toast.LENGTH_LONG).show();

                ArrayList<HashMap<String, String>> contatoList = new ArrayList<HashMap<String, String>>();

                for (Contato contato : response.body()){
                    HashMap<String, String> infoContato = new HashMap<String, String>();

                    infoContato.put("ID", String.valueOf(contato.getID()));
                    infoContato.put("nome", String.valueOf(contato.getNome()));
                    infoContato.put("celular", String.valueOf(contato.getCelular()));

                    contatoList.add(infoContato);



                }
                ListAdapter adapter = new SimpleAdapter(
                        MainActivity.this,
                        contatoList,
                        R.layout.view_item_lista_contato,
                        new String[]{"ID","nome","celular"},
                        new int[]{R.id.textViewItemID, R.id.textViewItemNome,R.id.textViewItemCelular});

                    listViewContatos.setAdapter(adapter);



                    listViewContatos.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                            TextView textViewID = (TextView)view.findViewById(R.id.textViewItemID);

                            Intent intent = new Intent(MainActivity.this, ContatoActivity.class);

                            //Insere paramentros
                            intent.putExtra("ID",Integer.parseInt(textViewID.getText().toString()));
                            startActivity(intent);
                        }

                    });
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {

                //Toast (mendagem rápida
                Toast.makeText(MainActivity.this, "Erro :'( ",
                        Toast.LENGTH_LONG).show();
            }

        });





    }
}
