package telas;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.Color;
import java.awt.Component;
import validacao.Validacoes;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class TelaReservar extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
 
//    PreparedStatement pst2 = null;
//    ResultSet rs2 = null;
    
    public TelaReservar() {            
        initComponents();
        
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
        
        // Remove a barra de título
        this.setBorder(null);
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
                        
        mostrarQuartos();
    }

    
    private void mostrarQuartos() {
        
        String pegarQuartos = "SELECT nome, descricao, preco, disponibilidade FROM tbl_quartos";
        
        try {
            
            pst = conexao.prepareStatement(pegarQuartos); // Prepara a declaração SQL para pegar os quartos
                        
            rs = pst.executeQuery();

            int i = 0;
            while(rs.next()) {
                
                String[] labelTexts = new String[4];
                labelTexts[0] = rs.getString("nome");
                labelTexts[1] = rs.getString("descricao");
                labelTexts[2] = rs.getString("preco");
                labelTexts[3] = rs.getString("disponibilidade");
                
                int y = 124;
                y *= i;
                y -= (4 * (i - 1));
                i++;

                javax.swing.JPanel paineis = criarPainel(labelTexts, y, i);
                
                jPanel2.add(paineis);
            }

        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
    
    private javax.swing.JPanel criarPainel(String[] labelTexts, int y, int id) {
        
        String atualizarInfoQuarto = "UPDATE tbl_quartos SET dataEntrada=?, dataSaida=?, idUsuario=?, disponibilidade=? WHERE id=?";
        
        javax.swing.JPanel painel = new javax.swing.JPanel();

        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.setBounds(0, y, 610, 120);

        painel.add(Box.createRigidArea(new Dimension(0, 10)));

        javax.swing.JLabel nomeQuarto = new javax.swing.JLabel();
        nomeQuarto.setText(labelTexts[0]);
        nomeQuarto.setAlignmentX(CENTER_ALIGNMENT);

        Font font = nomeQuarto.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, 14);
        nomeQuarto.setFont(boldFont);
        painel.add(nomeQuarto);

        javax.swing.JLabel descricao = new javax.swing.JLabel();
        descricao.setText(labelTexts[1]);
        descricao.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(descricao);

        javax.swing.JLabel preco = new javax.swing.JLabel();
        preco.setText("R$ " + labelTexts[2]);
        preco.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(preco);

        javax.swing.JLabel disponibilidade = new javax.swing.JLabel();
        
        System.out.println(labelTexts[3]);

        if (Integer.parseInt(labelTexts[3]) == 0) {

            disponibilidade.setText("Ocupado");
        } else {

            disponibilidade.setText("Disponível");
        }

        disponibilidade.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(disponibilidade);

        painel.add(Box.createRigidArea(new Dimension(0, 10)));

        javax.swing.JButton fazerReserva = new javax.swing.JButton();
        fazerReserva.setText("Reservar");
        fazerReserva.setAlignmentX(CENTER_ALIGNMENT);

        if (disponibilidade.getText() == "Disponível") {
            
            fazerReserva.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    boolean loop = true;
                    while (loop) {
                        String dataEntrada = JOptionPane.showInputDialog(null, "Qual a data de entrada (no formato dd/mm/aaaa) da reserva?", "Tempo de reserva", JOptionPane.QUESTION_MESSAGE);
                        String dataSaida = JOptionPane.showInputDialog(null, "Qual a data de saída (no formato dd/mm/aaaa) da reserva? Você pagará o preço do quarto por cada dia reservado", "Tempo de reserva", JOptionPane.QUESTION_MESSAGE);

                        if (!Validacoes.validarData(dataEntrada) || !Validacoes.validarData(dataSaida)) {

                            JOptionPane.showMessageDialog(null, "Insira uma data válida");
                        } else {

                            try {

                                pst = conexao.prepareStatement(atualizarInfoQuarto);

                                pst.setString(1, Validacoes.converterDataParaFormatoSQL(dataEntrada));
                                pst.setString(2, Validacoes.converterDataParaFormatoSQL(dataSaida));
                                pst.setString(3, String.valueOf(TelaLogin.getUserID()));
                                pst.setString(4, "0");
                                pst.setString(5, String.valueOf(id));

                                int atualizado = pst.executeUpdate();

                                if(atualizado > 0) {

                                    JOptionPane.showMessageDialog(null, "Quarto reservado! Vá para a tela de pagamento para efetuar o pagamento");

                                    loop = false;

                                    JInternalFrame parentFrame = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, (Component) e.getSource());
    //                                JInternalFrame[] frames = desktop.getAllFrames();

    //                                for (JInternalFrame frame : frames) {

                                    parentFrame.dispose();
    //                                }
                                }
                            } catch (Exception error) {

                                JOptionPane.showMessageDialog(null, error); // Se ocorrer uma exceção, mostra uma mensagem de erro
                            }
                        }
                    }
                }
            });
        } else {
            
            fazerReserva.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    JOptionPane.showMessageDialog(null, "Este quarto já está reservado", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            });
        }
        
        painel.add(fazerReserva);

        return painel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new java.awt.Dimension(603, 445));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3601, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(jPanel2);

        getContentPane().add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
