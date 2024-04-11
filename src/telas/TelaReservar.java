package telas;

import java.sql.*;
import dal.ModuloConexao;
import validacao.Validacoes;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.Dimension;
import javax.swing.JOptionPane;

public class TelaReservar extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
 
    
    public TelaReservar() {
        initComponents();
        
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
        
        // Remove a barra de título
        this.setBorder(null);
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
    }
    
    private void mostrarQuartos() {
        
        String pegarQuartos = "SELECT * FROM tbl_quartos WHERE id=?";
        String pegarNumeroDeQuartos = "SELECT MAX(id) as maxID FROM tbl_quartos";
        
        try {
            
            pst = conexao.prepareStatement(pegarQuartos); // Prepara a declaração SQL

            rs = pst.executeQuery(); // Executa a SQL query
            
            while(rs.next()) {
                
//                javax.swing.JPanel paineis = criarPainel("Panel " + (i + 1));
                javax.swing.JPanel paineis = criarPainel();
                add(paineis);
            }

        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
        
        int maxID = rs.getInt("maxID");
        
//        for (int i = 0; i < maxID; i++) {
//            javax.swing.JPanel paineis = criarPainel("Panel " + (i + 1));
//            add(paineis);
//        }
    }
    
    private javax.swing.JPanel criarPainel() {
        
        javax.swing.JPanel painel = new javax.swing.JPanel();
//        panel.setBorder(BorderFactory.createTitledBorder(nomePainel));
        painel.setPreferredSize(new Dimension(592, 119)); // Adjust size as needed;
//        return painel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nomeQuarto = new javax.swing.JLabel();
        descricaoQuarto = new javax.swing.JLabel();
        precoQuarto = new javax.swing.JLabel();
        disponibilidadeQuarto = new javax.swing.JLabel();
        reservarBtn = new javax.swing.JButton();

        nomeQuarto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomeQuarto.setText("Nome Provisório");

        descricaoQuarto.setText("Descrição Provisória");

        precoQuarto.setText("Preço");

        disponibilidadeQuarto.setText("Disponibilidade");

        reservarBtn.setText("Reservar");
        reservarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservarBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nomeQuarto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(precoQuarto)
                        .addGap(87, 87, 87))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(descricaoQuarto)
                        .addGap(125, 125, 125)
                        .addComponent(disponibilidadeQuarto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                        .addComponent(reservarBtn)
                        .addGap(63, 63, 63))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeQuarto)
                    .addComponent(precoQuarto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descricaoQuarto)
                    .addComponent(disponibilidadeQuarto)
                    .addComponent(reservarBtn))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 412, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reservarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reservarBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descricaoQuarto;
    private javax.swing.JLabel disponibilidadeQuarto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nomeQuarto;
    private javax.swing.JLabel precoQuarto;
    private javax.swing.JButton reservarBtn;
    // End of variables declaration//GEN-END:variables
}
