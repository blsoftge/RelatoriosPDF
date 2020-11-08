package br.com.blsoft.relatoriospdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.PrimeiroPDF;
import br.com.blsoft.relatoriospdf.relatorios.Relatorio;
import br.com.blsoft.relatoriospdf.relatorios.RelatorioPDFSimples;
import br.com.blsoft.relatoriospdf.vendas.Produto;
import br.com.blsoft.relatoriospdf.vendas.Venda;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Venda venda = new Venda("Marquês da Sapucaí", new ArrayList<Produto>());
        venda.addProdutoAoCarrinho(new Produto("Pinga", 2, 0.10));
        venda.addProdutoAoCarrinho(new Produto("Pão com mortadela", 1, 1.0));
        venda.addProdutoAoCarrinho(new Produto("Linguiça", 2, 0.90));

        Relatorio relatorioPdfSimples = new RelatorioPDFSimples(venda);
        relatorioPdfSimples.gerarCabecalho();
        relatorioPdfSimples.gerarCorpo();
        relatorioPdfSimples.gerarRodape();
        relatorioPdfSimples.imprimir();
    }
}
