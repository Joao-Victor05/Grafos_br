class No {
    private String nome;
    private boolean capital;

    public No(String nome, boolean capital) {
        this.nome = nome;
        this.capital = capital;
    }

    public String getNome() {
        return nome;
    }

    public boolean isCapital() {
        return capital;
    }

    @Override
    public String toString() {
        return nome + (capital ? " (Capital)" : "");
    }
}