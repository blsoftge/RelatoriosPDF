package br.com.blsoft.relatoriospdf.relatorios;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.GroupLayout.Alignment;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import br.com.blsoft.relatoriospdf.vendas.Produto;
import br.com.blsoft.relatoriospdf.vendas.Venda;

public class RelatorioPDFSimples implements Relatorio {

    private Venda venda;
    private Document documentoPDF;
    private String caminhoRelatorio = "RelatorioSimplesPDF.pdf";

    public RelatorioPDFSimples(Venda venda) {
        this.venda = venda;
        documentoPDF = new Document();
        try {
            PdfWriter.getInstance(documentoPDF, new FileOutputStream(caminhoRelatorio));
            documentoPDF.open();
        } catch (DocumentException e) {

            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gerarCabecalho() {
        Paragraph paragrafoTitulo = new Paragraph();
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        paragrafoTitulo.add(new Chunk("RELATÓRIO DE VENDAS SIMPLES", new Font(Font.HELVETICA, 24)));
        documentoPDF.add(paragrafoTitulo);

        this.documentoPDF.add(new Paragraph(" "));

        Paragraph paragrafoData = new Paragraph();
        paragrafoData.setAlignment(Element.ALIGN_CENTER);
        paragrafoData.add(new Chunk(this.venda.getDataVenda().toString()));
        this.documentoPDF.add(paragrafoData);
        this.documentoPDF.add(new Paragraph(" "));
        this.documentoPDF.add(new Paragraph(" "));

        Paragraph paragrafoCliente = new Paragraph();
        paragrafoCliente.setAlignment(Element.ALIGN_CENTER);
        paragrafoCliente.add(new Chunk("Cliente: " + this.venda.getNomeCliente(), new Font(Font.BOLD, 16)));
        this.documentoPDF.add(paragrafoCliente);

        Paragraph paragrafoSessao = new Paragraph("----------------------------------------------------------");
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        this.documentoPDF.add(paragrafoSessao);
        this.documentoPDF.add(new Paragraph(" "));
    }

    @Override
    public void gerarCorpo() {
        Paragraph pItensVendidos = new Paragraph();
        pItensVendidos.setAlignment(Element.ALIGN_CENTER);
        pItensVendidos.add(new Chunk("ITENS VENDIDOS ", new Font(Font.TIMES_ROMAN, 16)));
        documentoPDF.add(new Paragraph(pItensVendidos));

        for (Produto produto : this.venda.getProdutosVendidos()) {
            Paragraph pNomeProduto = new Paragraph();
            pNomeProduto.add(new Chunk(produto.getNome(), new Font(Font.COURIER, 14)));
            Paragraph pDadosProduto = new Paragraph();
            pDadosProduto.add("Quantidade:  " + produto.getQuantidade() + " - Preço unit.: R$ " + produto.getValor()
                    + " - Total: R$ " + produto.calcularPreco());

            this.documentoPDF.add(pNomeProduto);
            this.documentoPDF.add(pDadosProduto);
            this.documentoPDF.add(new Paragraph("---------------"));

        }
        Paragraph pTotal = new Paragraph();
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        pTotal.add(new Chunk("Total da venda: R$ " + this.venda.calcularValorTotalCarrinho(),
                new Font(Font.TIMES_ROMAN, 20)));
        this.documentoPDF.add(pTotal);
    }

    @Override
    public void gerarRodape() {
        Paragraph paragrafoSessao = new Paragraph("----------------------------------------------------------");
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        this.documentoPDF.add(paragrafoSessao);
        this.documentoPDF.add(new Paragraph(" "));
        Paragraph pRodape = new Paragraph();
        pRodape.setAlignment(Element.ALIGN_CENTER);
        pRodape.add(new Chunk("www.blsoft.com.br/like", new Font(Font.TIMES_ROMAN, 14)));
        this.documentoPDF.add(pRodape);
    }

    @Override
    public void imprimir() {
        if(this.documentoPDF != null && this.documentoPDF.isOpen()){
            documentoPDF.close();
        }
    }

}
