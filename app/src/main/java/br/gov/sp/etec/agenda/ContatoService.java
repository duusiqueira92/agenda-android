package br.gov.sp.etec.agenda;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by etec on 25/09/17.
 */

public interface ContatoService {
    //Carrega Todos contatos
    @GET("contatos/")
    Call<List<Contato>> carregarTodos();


    // Salvar um novo contato ADD
    @POST("contato")
    Call<Contato> salvarContato(@Body Contato contato);

    //Carragar Contato
    @GET ("contato/{id}")
    Call<Contato> carregarContato(@Path("id") int contatoID);

    //Alterar Contato
    @PUT("contato/{id}")
    Call<Contato> alterarContato(@Path("id") int id,@Body Contato contato);

    //Excluir contato
    @DELETE("contato/[id]")
    Call<Void> excluirContato(@Path("id") int id);


}
