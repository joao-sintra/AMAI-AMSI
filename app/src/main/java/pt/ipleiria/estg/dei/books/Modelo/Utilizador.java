package pt.ipleiria.estg.dei.books.Modelo;

public class Utilizador {
    private int id;
    private String username, primeiroNome, apelido, email, password, morada, cod_postal, rua, localidade, telefone, nif;

    public Utilizador(int id, String username, String primeiroNome, String apelido, String email, String password, String morada, String cod_postal, String rua, String localidade, String telefone, String nif) {
        this.id = id;
        this.username = username;
        this.primeiroNome = primeiroNome;
        this.apelido = apelido;
        this.email = email;
        this.password = password;
        this.morada = morada;
        this.cod_postal = cod_postal;
        this.rua = rua;
        this.localidade = localidade;
        this.telefone = telefone;
        this.nif = nif;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public String getApelido() {
        return apelido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMorada() {
        return morada;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public String getRua() {
        return rua;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNif() {
        return nif;
    }


}
