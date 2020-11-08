package br.com.blsoft.relatoriospdf.vendas;

public class Produto {
    private String nome;
    private int quantidade;
    private double valor;

    public Produto(String nome, int quantidade, double valor) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public double calcularPreco(){
        return this.quantidade * this.valor;
    }

    public String getNome() {
        return this.nome;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public double getValor() {
        return this.valor;
    }
    
}
