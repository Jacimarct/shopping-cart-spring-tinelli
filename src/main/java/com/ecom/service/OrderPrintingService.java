/*
 * package com.ecom.service;
 * 
 * import java.awt.print.*; import java.util.List; import
 * javax.print.attribute.*; import javax.print.attribute.standard.*; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.scheduling.annotation.Scheduled; import
 * org.springframework.stereotype.Service; import com.ecom.model.Order; import
 * com.ecom.model.OrderItem; import com.ecom.repository.OrderRepository;
 * 
 * import jakarta.transaction.Transactional;
 * 
 * @Service public class OrderPrintingService {
 * 
 * @Autowired private OrderRepository orderRepository;
 * 
 * @Scheduled(fixedRate = 5000)
 * 
 * @Transactional public void checkForNewOrders() { List<Order> newOrders =
 * orderRepository.findByPrintedFalse(); for (Order order : newOrders) {
 * printOrder(order); order.setPrinted(true); orderRepository.save(order); } }
 * 
 * private void printOrder(Order order) { PrinterJob job =
 * PrinterJob.getPrinterJob(); job.setPrintable((graphics, pageFormat,
 * pageIndex) -> { if (pageIndex > 0) { return Printable.NO_SUCH_PAGE; }
 * 
 * graphics.drawString(
 * "==========================================================", 100, 100);
 * graphics.
 * drawString("                  Detalhes do Pedido                      ", 100,
 * 120); graphics.drawString(
 * "==========================================================", 100, 140);
 * graphics.drawString("Número do Pedido : " + order.getId(), 100, 160);
 * graphics.drawString("Cliente : " + order.getFirstName() + " " +
 * order.getLastName(), 100, 180); graphics.drawString("E-mail: " +
 * order.getEmail(), 100, 200); graphics.drawString("Endereço: " +
 * order.getAddress() + ", " + order.getCity() + " - " + order.getState(), 100,
 * 220); graphics.drawString(
 * "-------------------------------------------------------------------------------------------------------",
 * 100, 240);
 * 
 * graphics.drawString("Itens do Pedido : ", 100, 260);
 * 
 * for (OrderItem item : order.getItems()) { String productTitle =
 * item.getProduct() != null ? item.getProduct().getTitle() :
 * "Produto não disponivel";
 * 
 * graphics.drawString("- " + productTitle + " | Qtd: " + item.getQuantity(),
 * 100, 300);
 * 
 * }
 * 
 * graphics.drawString(
 * "---------------------------------------------------------------------------------------------------------",
 * 100, 310); graphics.drawString("Valor Total: R$ " + order.getTotalAmount(),
 * 100,330); graphics.drawString("Data do Pedido: " + order.getOrderDate(), 100,
 * 350); graphics.drawString("Forma de Pagamento: " + order.getPaymentType(),
 * 100, 370); graphics.drawString(
 * "===========================================================", 100, 400);
 * 
 * return Printable.PAGE_EXISTS; });
 * 
 * PageFormat pageFormat = job.defaultPage(); Paper paper = new Paper();
 * paper.setSize(595, 842); // A4 em pontos (1 ponto = 1/72 pol.)
 * paper.setImageableArea(50, 50, 495, 742); // Margens
 * pageFormat.setPaper(paper);
 * 
 * try { job.print(); System.out.println("Impressão enviada com sucesso."); }
 * catch (PrinterException e) { System.err.println("Erro ao imprimir: " +
 * e.getMessage()); e.printStackTrace(); } } }
 */




/*
 * package com.ecom.service;
 * 
 * import java.awt.print.*; import java.util.List; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.scheduling.annotation.Scheduled; import
 * org.springframework.stereotype.Service; import com.ecom.model.Order; import
 * com.ecom.model.OrderItem; import com.ecom.repository.OrderRepository; import
 * jakarta.transaction.Transactional;
 * 
 * @Service public class OrderPrintingService {
 * 
 * @Autowired private OrderRepository orderRepository;
 * 
 * @Scheduled(fixedRate = 5000)
 * 
 * @Transactional public void checkForNewOrders() { List<Order> newOrders =
 * orderRepository.findByPrintedFalse(); for (Order order : newOrders) {
 * printOrder(order); order.setPrinted(true); orderRepository.save(order); } }
 * 
 * private void printOrder(Order order) { PrinterJob job =
 * PrinterJob.getPrinterJob();
 * 
 * Printable printable = (graphics, pageFormat, pageIndex) -> { if (pageIndex >
 * 0) { return Printable.NO_SUCH_PAGE; }
 * 
 * // Configuração de fonte e margens
 * graphics.setFont(graphics.getFont().deriveFont(12f));
 * 
 * int x = 100; int y = 100; int lineHeight = 20;
 * 
 * graphics.drawString("==================================================", x,
 * y); y += lineHeight;
 * graphics.drawString("             Detalhes do Pedido                   ", x,
 * y); y += lineHeight;
 * graphics.drawString("==================================================", x,
 * y); y += lineHeight;
 * 
 * graphics.drawString("Número do Pedido: " + order.getId(), x, y); y +=
 * lineHeight; graphics.drawString("Cliente: " + order.getFirstName() + " " +
 * order.getLastName(), x, y); y += lineHeight; graphics.drawString("E-mail: " +
 * order.getEmail(), x, y); y += lineHeight; graphics.drawString("Endereço: " +
 * order.getAddress() + ", " + order.getCity() + " - " + order.getState(), x,
 * y); y += lineHeight;
 * 
 * graphics.drawString("--------------------------------------------------", x,
 * y); y += lineHeight; graphics.drawString("Itens do Pedido:", x, y); y +=
 * lineHeight;
 * 
 * for (OrderItem item : order.getItems()) { String productTitle =
 * item.getProduct() != null ? item.getProduct().getTitle() :
 * "Produto não disponível"; graphics.drawString("- " + productTitle +
 * " | Qtd: " + item.getQuantity(), x, y); y += lineHeight; }
 * 
 * graphics.drawString("--------------------------------------------------", x,
 * y); y += lineHeight; graphics.drawString("Valor Total: R$ " +
 * order.getTotalAmount(), x, y); y += lineHeight;
 * graphics.drawString("Data do Pedido: " + order.getOrderDate(), x, y); y +=
 * lineHeight; graphics.drawString("Forma de Pagamento: " +
 * order.getPaymentType(), x, y); y += lineHeight;
 * graphics.drawString("==================================================", x,
 * y);
 * 
 * return Printable.PAGE_EXISTS; };
 * 
 * // Configuração do formato de página PageFormat pageFormat =
 * job.defaultPage(); Paper paper = new Paper();
 * 
 * // Definindo tamanho A4 em pontos (1 polegada = 72 pontos) double width =
 * 8.27 * 72; // Largura A4 em polegadas double height = 11.69 * 72; // Altura
 * A4 em polegadas double margin = 0.5 * 72; // Margem de 0.5 polegadas
 * 
 * paper.setSize(width, height); paper.setImageableArea(margin, margin, width -
 * 2*margin, height - 2*margin); pageFormat.setPaper(paper);
 * pageFormat.setOrientation(PageFormat.PORTRAIT);
 * 
 * // Define o Printable com o formato de página personalizado
 * job.setPrintable(printable, pageFormat);
 * 
 * try { // Mostra o diálogo de impressão para confirmação if
 * (job.printDialog()) { job.print();
 * System.out.println("Impressão enviada com sucesso."); } else {
 * System.out.println("Impressão cancelada pelo usuário."); } } catch
 * (PrinterException e) { System.err.println("Erro ao imprimir: " +
 * e.getMessage()); e.printStackTrace(); } } }
 */



/*
 * package com.ecom.service;
 * 
 * import java.awt.*; import java.awt.print.*; import java.util.List; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.scheduling.annotation.Scheduled; import
 * org.springframework.stereotype.Service; import com.ecom.model.Order; import
 * com.ecom.model.OrderItem; import com.ecom.repository.OrderRepository; import
 * jakarta.transaction.Transactional;
 * 
 * @Service public class OrderPrintingService {
 * 
 * @Autowired private OrderRepository orderRepository;
 * 
 * @Scheduled(fixedRate = 5000)
 * 
 * @Transactional public void checkForNewOrders() { List<Order> newOrders =
 * orderRepository.findByPrintedFalse(); for (Order order : newOrders) { try {
 * printOrder(order); order.setPrinted(true); orderRepository.save(order); }
 * catch (Exception e) { System.err.println("Erro ao processar pedido #" +
 * order.getId() + ": " + e.getMessage()); e.printStackTrace(); } } }
 * 
 * private void printOrder(Order order) throws PrinterException { //
 * Configuração do trabalho de impressão PrinterJob job =
 * PrinterJob.getPrinterJob();
 * 
 * // Configurar o formato da página PageFormat pageFormat = job.defaultPage();
 * Paper paper = new Paper();
 * 
 * // Tamanho A4 em pontos (1 polegada = 72 pontos) double width = 8.27 * 72;
 * double height = 11.69 * 72; double margin = 36; // 0.5 polegadas = 36 pontos
 * 
 * paper.setSize(width, height); paper.setImageableArea(margin, margin, width -
 * 2 * margin, height - 2 * margin); pageFormat.setPaper(paper);
 * pageFormat.setOrientation(PageFormat.PORTRAIT);
 * 
 * // Definir o conteúdo imprimível job.setPrintable(new OrderPrintable(order),
 * pageFormat);
 * 
 * // Remover o diálogo de impressão para ambiente headless try { job.print();
 * System.out.println("Pedido #" + order.getId() + " impresso com sucesso."); }
 * catch (PrinterException e) { System.err.println("Falha ao imprimir pedido #"
 * + order.getId()); throw e; } }
 * 
 * // Classe interna para o conteúdo imprimível private static class
 * OrderPrintable implements Printable { private final Order order;
 * 
 * public OrderPrintable(Order order) { this.order = order; }
 * 
 * @Override public int print(Graphics graphics, PageFormat pageFormat, int
 * pageIndex) { if (pageIndex > 0) { return NO_SUCH_PAGE; }
 * 
 * Graphics2D g2d = (Graphics2D) graphics;
 * 
 * // Configurações de renderização
 * g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
 * RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 * 
 * // Configurações de fonte Font font = new Font("Arial", Font.PLAIN, 10);
 * g2d.setFont(font); FontMetrics fm = g2d.getFontMetrics(); int lineHeight =
 * fm.getHeight();
 * 
 * // Margens int x = (int) pageFormat.getImageableX() + 10; int y = (int)
 * pageFormat.getImageableY() + 20;
 * 
 * // Conteúdo do pedido drawString(g2d,
 * "============================================", x, y); y += lineHeight;
 * drawString(g2d, "          DETALHES DO PEDIDO               ", x, y); y +=
 * lineHeight; drawString(g2d, "============================================",
 * x, y); y += lineHeight * 2;
 * 
 * drawString(g2d, "Número do Pedido: " + order.getId(), x, y); y += lineHeight;
 * drawString(g2d, "Cliente: " + order.getFirstName() + " " +
 * order.getLastName(), x, y); y += lineHeight; drawString(g2d, "E-mail: " +
 * order.getEmail(), x, y); y += lineHeight; drawString(g2d, "Endereço: " +
 * order.getAddress() + ", " + order.getCity(), x, y); y += lineHeight;
 * drawString(g2d, order.getState() + " - CEP: " + order.getPincode(), x, y); y
 * += lineHeight * 2;
 * 
 * drawString(g2d, "--------------------------------------------", x, y); y +=
 * lineHeight; drawString(g2d, "ITENS DO PEDIDO:", x, y); y += lineHeight;
 * drawString(g2d, "--------------------------------------------", x, y); y +=
 * lineHeight;
 * 
 * for (OrderItem item : order.getItems()) { String productTitle =
 * item.getProduct() != null ? item.getProduct().getTitle() :
 * "Produto não disponível"; drawString(g2d, "- " + productTitle + " | Qtd: " +
 * item.getQuantity(), x, y); y += lineHeight; }
 * 
 * drawString(g2d, "--------------------------------------------", x, y); y +=
 * lineHeight; drawString(g2d, "Valor Total: R$ " + order.getTotalAmount(), x,
 * y); y += lineHeight; drawString(g2d, "Data do Pedido: " +
 * order.getOrderDate(), x, y); y += lineHeight; drawString(g2d,
 * "Forma de Pagamento: " + order.getPaymentType(), x, y); y += lineHeight;
 * drawString(g2d, "============================================", x, y);
 * 
 * return PAGE_EXISTS; }
 * 
 * private void drawString(Graphics2D g2d, String text, int x, int y) {
 * g2d.drawString(text, x, y); } } }
 */
/*
package com.ecom.service;

import java.awt.*;
import java.awt.print.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderPrintingService {

    @Autowired
    private OrderRepository orderRepository;

    // Agendado para rodar a cada 5 segundos
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void verificarPedidosParaImpressao() {
        List<Order> pedidosPendentes = orderRepository.findByPrintedFalse();
        for (Order pedido : pedidosPendentes) {
            try {
                imprimirPedido(pedido);
                pedido.setPrinted(true);
                orderRepository.save(pedido);
            } catch (Exception e) {
                System.err.println("⚠️ Erro ao imprimir o pedido #" + pedido.getId() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void imprimirPedido(Order pedido) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new PedidoPrintable(pedido));

        if (job.printDialog()) {
            job.print();
            System.out.println("✅ Pedido #" + pedido.getId() + " impresso com sucesso!");
        } else {
            System.out.println("🛑 Impressão cancelada pelo operador para o pedido #" + pedido.getId());
        }
    }

    private static class PedidoPrintable implements Printable {
        private final Order pedido;

        public PedidoPrintable(Order pedido) {
            this.pedido = pedido;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Font fonte = new Font("Courier New", Font.PLAIN, 10);
            g2d.setFont(fonte);
            FontMetrics fm = g2d.getFontMetrics();
            int alturaLinha = fm.getHeight();

            int x = 10;
            int y = alturaLinha;

            // Cabeçalho
            g2d.drawString("=========== RESUMO DO PEDIDO ===========", x, y); y += alturaLinha;
            g2d.drawString("           COMPROVANTE DE VENDA          ", x, y); y += alturaLinha;
            g2d.drawString("=========================================", x, y); y += alturaLinha * 2;

            // Dados do pedido
            g2d.drawString("Pedido Nº: " + pedido.getId(), x, y); y += alturaLinha;
            g2d.drawString("Cliente: " + pedido.getFirstName() + " " + pedido.getLastName(), x, y); y += alturaLinha;
            g2d.drawString("Email: " + pedido.getEmail(), x, y); y += alturaLinha;
            g2d.drawString("Endereço: " + pedido.getAddress() + ", " + pedido.getCity(), x, y); y += alturaLinha;
            g2d.drawString(pedido.getState() + " - CEP: " + pedido.getPincode(), x, y); y += alturaLinha * 2;

            // Itens
            g2d.drawString("Itens Adquiridos:", x, y); y += alturaLinha;
            for (OrderItem item : pedido.getItems()) {
                String produto = item.getProduct() != null ? item.getProduct().getTitle() : "Produto indisponível";
                g2d.drawString("• " + produto + " - Qtd: " + item.getQuantity(), x, y); y += alturaLinha;
            }

            y += alturaLinha;
            g2d.drawString("Total a pagar: R$ " + pedido.getTotalAmount(), x, y); y += alturaLinha;
            g2d.drawString("=========================================", x, y);

            return PAGE_EXISTS;
        }
    }
}
*/
/*
  package com.ecom.service;
  
  import java.awt.*;
  import java.awt.print.*;
  import java.util.List;

  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.scheduling.annotation.Scheduled;
  import org.springframework.stereotype.Service;

  import com.ecom.model.Order;
  import com.ecom.model.OrderItem;
  import com.ecom.repository.OrderRepository;

  import jakarta.transaction.Transactional;    
  
  @Service public class OrderPrintingService {
	  
  @Autowired private OrderRepository orderRepository;
  
  @Scheduled(fixedRate = 5000)
  
	@Transactional
	public void checkForNewOrders() {
		List<Order> newOrders = orderRepository.findByPrintedFalse();
		for (Order order : newOrders) {
			try {
				printOrder(order);
				order.setPrinted(true);
				orderRepository.save(order);
			} catch (Exception e) {
				System.err.println("Erro ao processar pedido #" + order.getId() + ": " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
  
	private void printOrder(Order order) throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new OrderPrintable(order));
  
		try {
			job.print();
			System.out.println("Pedido #" + order.getId() + " impresso com sucesso.");
		} catch (PrinterException e) {
			System.err.println("Falha ao imprimir pedido #" + order.getId());
			throw e;
		}
	}
  
	private static class OrderPrintable implements Printable {
		private final Order order;
  
  public OrderPrintable(Order order) { this.order = order; }
  
@Override public int print(Graphics graphics, PageFormat pageFormat, int
  pageIndex) { if (pageIndex > 0) { return NO_SUCH_PAGE; }
  
  Graphics2D g2d = (Graphics2D) graphics;
  g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
  g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
  
  Font font = new Font("Arial", Font.PLAIN, 10); g2d.setFont(font); FontMetrics
  fm = g2d.getFontMetrics(); int lineHeight = fm.getHeight();
  
  int x = 10; int y = lineHeight;
  
  // Cabeçalho 
  g2d.drawString("============================================", x, y); y += lineHeight;
  g2d.drawString("          DETALHES DO PEDIDO               ", x, y); y +=
  lineHeight; g2d.drawString("============================================", x,
  y); y += lineHeight * 2;
  
	g2d.drawString("Número do Pedido: " + order.getId(), x, y);
	y += lineHeight;
	g2d.drawString("Cliente: " + order.getFirstName() + " " + order.getLastName(), x, y);
	y += lineHeight;
	g2d.drawString("E-mail: " + order.getEmail(), x, y);
	y += lineHeight;
	g2d.drawString("Endereço: " + order.getAddress() + ", " + order.getCity(), x, y);
	y += lineHeight;
	g2d.drawString(order.getState() + " - CEP: " + order.getPincode(), x, y);
	y += lineHeight * 2;
  
	// Itens do Pedido g2d.drawString("ITENS DO PEDIDO:", x, y); y += lineHeight;
	for (OrderItem item : order.getItems()) {
		String productTitle = item.getProduct() != null ? item.getProduct().getTitle() : "Produto não disponível";
		g2d.drawString("- " + productTitle + " | Qtd: " + item.getQuantity(), x, y);
		y += lineHeight;
	}
  
	y += lineHeight;
	g2d.drawString("Valor Total: R$ " + order.getTotalAmount(), x, y);
	y += lineHeight;
	g2d.drawString("============================================", x, y);
  
  return PAGE_EXISTS; } } }  */


/*package com.ecom.service;

import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.repository.OrderRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class OrderPrintingService {

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void checkForNewOrders() {
        List<Order> newOrders = orderRepository.findByPrintedFalse();
        for (Order order : newOrders) {
            try {
                generatePdf(order);
                order.setPrinted(true);
                orderRepository.save(order);
            } catch (Exception e) {
                System.err.println("Erro ao processar pedido #" + order.getId() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void generatePdf(Order order) throws Exception {
        String fileName = "Pedido_" + order.getId() + ".pdf";
        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

        document.add(new Paragraph("============================================", normalFont));
        document.add(new Paragraph("           DETALHES DO PEDIDO", titleFont));
        document.add(new Paragraph("============================================\n", normalFont));

        document.add(new Paragraph("Número do Pedido: " + order.getId(), normalFont));
        document.add(new Paragraph("Cliente: " + order.getFirstName() + " " + order.getLastName(), normalFont));
        document.add(new Paragraph("E-mail: " + order.getEmail(), normalFont));
        document.add(new Paragraph("Endereço: " + order.getAddress() + ", " + order.getCity(), normalFont));
        document.add(new Paragraph(order.getState() + " - CEP: " + order.getPincode(), normalFont));
        document.add(new Paragraph("\nITENS DO PEDIDO:", titleFont));

        for (OrderItem item : order.getItems()) {
            String productTitle = item.getProduct() != null ? item.getProduct().getTitle() : "Produto não disponível";
            document.add(new Paragraph("- " + productTitle + " | Qtd: " + item.getQuantity(), normalFont));
        }

        document.add(new Paragraph("\nValor Total: R$ " + order.getTotalAmount(), normalFont));
        document.add(new Paragraph("============================================", normalFont));

        document.close();

        System.out.println("PDF gerado com sucesso: " + fileName);
    }
}
*/
/* FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU - 

package com.ecom.service;

import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.repository.OrderRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class OrderPrintingService {

    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void checkForNewOrders() {
        List<Order> newOrders = orderRepository.findByPrintedFalse();
        System.out.println("Pedidos não impressos encontrados: " + newOrders.size());
        
        for (Order order : newOrders) {
            try {
                System.out.println("Processando pedido #" + order.getId());
                
                // 1. Verifica se o pedido já foi processado
                if (isOrderAlreadyProcessed(order.getId())) {
                    System.out.println("Pedido #" + order.getId() + " já foi processado anteriormente");
                    continue;
                }
                
                // 2. Gera o PDF
                generatePdf(order);
                
                // 3. Atualiza o status de forma segura
                safelyMarkOrderAsPrinted(order);
                
                System.out.println("Pedido #" + order.getId() + " processado com sucesso");
                
            } catch (DataIntegrityViolationException dive) {
                System.err.println("Erro de integridade no pedido #" + order.getId());
                handleDataIntegrityError(order);
            } catch (Exception e) {
                System.err.println("Erro geral no pedido #" + order.getId());
                e.printStackTrace();
            }
        }
    }

    private boolean isOrderAlreadyProcessed(Integer orderId) {
        // Consulta nativa para verificar o status diretamente no banco
        Long count = (Long) entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM product_order WHERE order_id = :orderId AND printed = true")
                .setParameter("orderId", orderId)
                .getSingleResult();
        return count > 0;
    }

    private void safelyMarkOrderAsPrinted(Order order) {
        // Atualização nativa para evitar problemas de concorrência
        entityManager.createNativeQuery(
                "UPDATE product_order SET printed = true WHERE order_id = :orderId AND printed = false")
                .setParameter("orderId", order.getId())
                .executeUpdate();
        
        // Atualiza o objeto na sessão
        Order managedOrder = orderRepository.findById(order.getId()).orElseThrow();
        managedOrder.setPrinted(true);
        orderRepository.save(managedOrder);
    }

    private void handleDataIntegrityError(Order order) {
        try {
            System.out.println("Tentando recuperação para pedido #" + order.getId());
            
            // 1. Verifica o estado atual no banco
            boolean isPrinted = (boolean) entityManager.createNativeQuery(
                    "SELECT printed FROM product_order WHERE order_id = :orderId LIMIT 1")
                    .setParameter("orderId", order.getId())
                    .getSingleResult();
            
            if (isPrinted) {
                System.out.println("Pedido #" + order.getId() + " já está marcado como impresso no banco");
                // Sincroniza o objeto com o banco
                Order managedOrder = orderRepository.findById(order.getId()).orElseThrow();
                managedOrder.setPrinted(true);
                orderRepository.save(managedOrder);
            } else {
                // Tenta novamente com lock pessimista
                safelyMarkOrderAsPrintedWithLock(order);
            }
        } catch (Exception e) {
            System.err.println("Falha na recuperação do pedido #" + order.getId());
            e.printStackTrace();
        }
    }

    private void safelyMarkOrderAsPrintedWithLock(Order order) {
        // Atualização com lock para evitar concorrência
        entityManager.createNativeQuery(
                "UPDATE product_order SET printed = true WHERE order_id = :orderId AND printed = false FOR UPDATE")
                .setParameter("orderId", order.getId())
                .executeUpdate();
        
        // Atualiza o objeto na sessão
        Order managedOrder = orderRepository.findById(order.getId()).orElseThrow();
        managedOrder.setPrinted(true);
        orderRepository.save(managedOrder);
        
        System.out.println("Pedido #" + order.getId() + " atualizado com lock pessimista");
    }

    private void generatePdf(Order order) throws Exception {
        String fileName = "Pedido_" + order.getId() + ".pdf";
        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            PdfWriter.getInstance(document, fos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            // Cabeçalho
            document.add(new Paragraph("DETALHES DO PEDIDO #" + order.getId(), titleFont));
            document.add(new Paragraph("Data: " + order.getOrderDate(), normalFont));
            document.add(new Paragraph("----------------------------------------", normalFont));

            // Informações do cliente
            document.add(new Paragraph("Cliente: " + order.getFirstName() + " " + order.getLastName(), normalFont));
            document.add(new Paragraph("E-mail: " + order.getEmail(), normalFont));
            document.add(new Paragraph("Endereço: " + order.getAddress(), normalFont));
            document.add(new Paragraph(order.getCity() + "/" + order.getState() + " - CEP: " + order.getPincode(), normalFont));
            document.add(new Paragraph("----------------------------------------", normalFont));
            document.add(new Paragraph(" "));

            // Itens do pedido
            document.add(new Paragraph("ITENS:", titleFont));
            for (OrderItem item : order.getItems()) {
                String productTitle = item.getProduct() != null ? 
                    item.getProduct().getTitle() : "Produto não disponível";
                document.add(new Paragraph("- " + productTitle + " (Qtd: " + item.getQuantity() + ")", normalFont));
            }

            // Total e pagamento
            document.add(new Paragraph("----------------------------------------", normalFont));
            document.add(new Paragraph("TOTAL: R$ " + order.getTotalAmount(), titleFont));
            document.add(new Paragraph("Forma de pagamento: " + order.getPaymentType(), normalFont));

            document.close();
            System.out.println("PDF gerado: " + fileName);
        }
    }
}

FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU -FUNCIONANDO + OU - */


package com.ecom.service;

import com.ecom.util.FormatUtils;
import com.ecom.model.Order;
import com.ecom.model.OrderItem;
import com.ecom.repository.OrderRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class OrderPrintingService {

    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void checkForNewOrders() {
        List<Order> newOrders = orderRepository.findByPrintedFalse();
        System.out.println("Pedidos não impressos encontrados: " + newOrders.size());
        
        for (Order order : newOrders) {
            try {
                System.out.println("Processando pedido #" + order.getId());
                
                if (isOrderAlreadyProcessed(order.getId())) {
                    System.out.println("Pedido #" + order.getId() + " já foi processado anteriormente");
                    continue;
                }

                // Gera e valida o PDF
                boolean pdfGerado = generatePdf(order);
                
                if (pdfGerado) {
                    safelyMarkOrderAsPrinted(order);
                    System.out.println("Pedido #" + order.getId() + " processado com sucesso (PDF OK)");
                } else {
                    System.err.println("Falha ao gerar PDF para o pedido #" + order.getId());
                }

            } catch (DataIntegrityViolationException dive) {
                System.err.println("Erro de integridade no pedido #" + order.getId());
                handleDataIntegrityError(order);
            } catch (Exception e) {
                System.err.println("Erro geral no pedido #" + order.getId());
                e.printStackTrace();
            }
        }
    }

    private boolean isOrderAlreadyProcessed(Integer orderId) {
        Long count = (Long) entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM product_order WHERE order_id = :orderId AND printed = true")
                .setParameter("orderId", orderId)
                .getSingleResult();
        return count > 0;
    }

    private void safelyMarkOrderAsPrinted(Order order) {
        entityManager.createNativeQuery(
                "UPDATE product_order SET printed = true WHERE order_id = :orderId AND printed = false")
                .setParameter("orderId", order.getId())
                .executeUpdate();
        
        Order managedOrder = orderRepository.findById(order.getId()).orElseThrow();
        managedOrder.setPrinted(true);
        orderRepository.save(managedOrder);
    }

    private void handleDataIntegrityError(Order order) {
        try {
            boolean isPrinted = (boolean) entityManager.createNativeQuery(
                    "SELECT printed FROM product_order WHERE order_id = :orderId LIMIT 1")
                    .setParameter("orderId", order.getId())
                    .getSingleResult();
            
            if (isPrinted) {
                Order managedOrder = orderRepository.findById(order.getId()).orElseThrow();
                managedOrder.setPrinted(true);
                orderRepository.save(managedOrder);
            } else {
                safelyMarkOrderAsPrintedWithLock(order);
            }
        } catch (Exception e) {
            System.err.println("Falha na recuperação do pedido #" + order.getId());
            e.printStackTrace();
        }
    }

    private void safelyMarkOrderAsPrintedWithLock(Order order) {
        entityManager.createNativeQuery(
                "UPDATE product_order SET printed = true WHERE order_id = :orderId AND printed = false FOR UPDATE")
                .setParameter("orderId", order.getId())
                .executeUpdate();
        
        Order managedOrder = orderRepository.findById(order.getId()).orElseThrow();
        managedOrder.setPrinted(true);
        orderRepository.save(managedOrder);
    }

    /**
     * Gera o PDF do pedido e retorna true se o arquivo for gerado com sucesso.
     */
    private boolean generatePdf(Order order) {
        String fileName = "numeroPedido_" + order.getId() + ".pdf";
        Document document = new Document(PageSize.A4, 36, 36, 54, 36);

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            PdfWriter.getInstance(document, fos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            document.add(new Paragraph("DETALHES DO PEDIDO #" + order.getId(), titleFont));
            document.add(new Paragraph("Data: " + order.getOrderDate(), normalFont));
            document.add(new Paragraph("----------------------------------------", normalFont));

            document.add(new Paragraph("Cliente: " + order.getFirstName() + " " + order.getLastName(), normalFont));
            document.add(new Paragraph("E-mail: " + order.getEmail(), normalFont));
            document.add(new Paragraph("Endereço: " + order.getAddress(), normalFont));
            document.add(new Paragraph(order.getCity() + "/" + order.getState() + " - CEP: " + order.getPincode(), normalFont));

            
            // Formata o número de telefone usando FormatUtils
            String telefoneFormatado = FormatUtils.formatarTelefone(order.getMobileNo());
            document.add(new Paragraph("Celular de Contato: " + telefoneFormatado, normalFont));
//            document.add(new Paragraph("Celular de Contato: " + order.getMobileNo(), normalFont));            
            
            document.add(new Paragraph("----------------------------------------", normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("ITENS:", titleFont));
            for (OrderItem item : order.getItems()) {
                String productTitle = item.getProduct() != null ? 
                    item.getProduct().getTitle() : "Produto não disponível";
                document.add(new Paragraph("- " + productTitle + " (Qtd: " + item.getQuantity() + ")", normalFont));
            }

            document.add(new Paragraph("----------------------------------------", normalFont));
            document.add(new Paragraph("TOTAL: R$ " + order.getTotalAmount(), titleFont));
            document.add(new Paragraph("Forma de pagamento: " + order.getPaymentType(), normalFont));

            document.close();
            System.out.println("PDF gerado: " + fileName);

            // Validação do arquivo gerado
            File file = new File(fileName);
            return file.exists() && file.length() > 0;

        } catch (Exception e) {
            System.err.println("Erro ao gerar PDF para pedido #" + order.getId());
            e.printStackTrace();
            return false;
        }
    }
}




