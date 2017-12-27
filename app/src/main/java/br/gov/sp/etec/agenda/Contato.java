package br.gov.sp.etec.agenda;

import com.google.gson.annotations.SerializedName;

/**
 * Created by etec on 25/09/17.
 */

public class Contato {

    @SerializedName("ID")
    private long ID;

    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("celular")
    private long celular;

    @SerializedName("telefone")
    private long telefone;


    public long getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public long getCelular() {
        return celular;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }
}
