package br.com.blsoft.relatoriospdf.vendas;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private LocalDate dataVenda;
    private String nomeCliente;
    private List<Produto> produtosVendidos;

    public Venda(String nomeCliente, List<Produto> arrayList) {
        this.dataVenda = LocalDate.now();
        this.nomeCliente = nomeCliente;
        this.produtosVendidos = arrayList;
    }

    public double calcularValorTotalCarrinho(){
        double total = 0;
        for (Produto produto : produtosVendidos) {
            total += produto.calcularPreco();
        }
        return total;
    }

    public void addProdutoAoCarrinho(Produto produto){
        this.produtosVendidos.add(produto);
    }

    public LocalDate getDataVenda() {
        return this.dataVenda;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public List<Produto> getProdutosVendidos() {
        return this.produtosVendidos;
    }

}
