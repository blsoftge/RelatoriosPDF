package br.com.blsoft.relatoriospdf.relatorios;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import br.com.blsoft.relatoriospdf.vendas.Produto;
import br.com.blsoft.relatoriospdf.vendas.Venda;

public class RelatorioPDFBonito implements Relatorio {

    private Venda venda;
    private Document documentoPDF;
    private String caminhoRelatorio = "RelatorioBonitoPDF.pdf";

    public RelatorioPDFBonito(Venda venda) {
        this.venda = venda;
        documentoPDF = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter.getInstance(documentoPDF, new FileOutputStream(caminhoRelatorio));
            HeaderFooter paginacao = new HeaderFooter(new Phrase("Pág.", new Font(Font.BOLD)), true);
            paginacao.setAlignment(Element.ALIGN_RIGHT);
            paginacao.setBorder(Rectangle.NO_BORDER);
            documentoPDF.setHeader(paginacao);
            documentoPDF.open();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gerarCabecalho() {
        Image imgTitulo = null;
        try {
            imgTitulo = Image.getInstance("LOGO_FLAT.jpg");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IMG Não encontrada", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        if (imgTitulo != null) {
            imgTitulo.setAlignment(Element.ALIGN_CENTER);
            this.documentoPDF.add(imgTitulo);
        }

        Paragraph paragrafoTitulo = new Paragraph();
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        Chunk cTitulo = new Chunk("RELATÓRIO DE VENDAS BONITO");
        cTitulo.setFont(new Font(Font.COURIER, 24));
        cTitulo.setBackground(Color.lightGray, 2, 2, 2, 2);
        paragrafoTitulo.add(cTitulo);
        documentoPDF.add(paragrafoTitulo);

        this.documentoPDF.add(new Paragraph(" "));

        Paragraph paragrafoData = new Paragraph();

        StringBuilder dataVenda = new StringBuilder();
        dataVenda.append(" - Data da venda: ");
        dataVenda.append(this.venda.getDataVenda().getDayOfMonth());
        dataVenda.append("/");
        dataVenda.append(this.venda.getDataVenda().getMonthValue());
        dataVenda.append("/");
        dataVenda.append(this.venda.getDataVenda().getYear());

        Chunk dataCliente = new Chunk();
        dataCliente.append("Cliente: " + this.venda.getNomeCliente());
        dataCliente.append(dataVenda.toString());
        paragrafoData.add(dataCliente);
        this.documentoPDF.add(paragrafoData);
        Paragraph paragrafoSessao = new Paragraph("__________________________________________________________");
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
        documentoPDF.add(new Paragraph(" "));

        // tabela com 4 colunas
        PdfPTable tableProdutos = new PdfPTable(4);
        tableProdutos.setWidthPercentage(98);
        tableProdutos.setWidths(new float[] { 2f, 1f, 1f, 1f });

        PdfPCell celulaTitulo = new PdfPCell(new Phrase("PRODUTO"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("QUANTIDADE"));
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("VALOR UNI."));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        celulaTitulo = new PdfPCell(new Phrase("VALOR TOTAL"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tableProdutos.addCell(celulaTitulo);

        int contador = 1;

        for (Produto produto : this.venda.getProdutosVendidos()) {

            PdfPCell celulaNome = new PdfPCell(new Phrase(produto.getNome()));
            PdfPCell celulaQuantidade = new PdfPCell(new Phrase(String.valueOf(produto.getQuantidade())));
            celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaValor = new PdfPCell(new Phrase("R$ " + String.valueOf(produto.getValor())));
            celulaValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaTotalUnit = new PdfPCell(new Phrase("R$ " + String.valueOf(produto.calcularPreco())));
            celulaTotalUnit.setHorizontalAlignment(Element.ALIGN_CENTER);

            if (contador % 2 == 0) {
                celulaNome.setBackgroundColor(Color.LIGHT_GRAY);
                celulaQuantidade.setBackgroundColor(Color.LIGHT_GRAY);
                celulaValor.setBackgroundColor(Color.LIGHT_GRAY);
                celulaTotalUnit.setBackgroundColor(Color.LIGHT_GRAY);
            }

            tableProdutos.addCell(celulaNome);
            tableProdutos.addCell(celulaQuantidade);
            tableProdutos.addCell(celulaValor);
            tableProdutos.addCell(celulaTotalUnit);

            contador++;
        }
        this.documentoPDF.add(tableProdutos);

        this.documentoPDF.add(new Paragraph(" "));

        Paragraph pTotal = new Paragraph();
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        pTotal.add(new Chunk("Total da venda: R$ " + this.venda.calcularValorTotalCarrinho(),
                new Font(Font.TIMES_ROMAN, 20)));
        this.documentoPDF.add(pTotal);
    }

    @Override
    public void gerarRodape() {
        Paragraph paragrafoSessao = new Paragraph("__________________________________________________________");
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
        if (this.documentoPDF != null && this.documentoPDF.isOpen()) {
            documentoPDF.close();
        }
    }

}
