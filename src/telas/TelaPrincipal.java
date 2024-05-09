package telas;
import javax.swing.JOptionPane;
import dal.ModuloConexao;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JInternalFrame;

public class TelaPrincipal extends javax.swing.JFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
    

    public TelaPrincipal() {
        initComponents();
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
    
        atualizarBanco(); // Chama o método atualizarBanco quando entrar no programa
    }
    
    // Método para atualizar o banco quando necessário, como quando um quarto passa da data de saída
    private void atualizarBanco() {
        
        // Consulta as datas de saída para verificar quais datas já passaram
        String sql = "SELECT dataSaida FROM tbl_quartos";
        
        try {
            
            pst = conexao.prepareStatement(sql); // Prepara a declaração SQL
            
            rs = pst.executeQuery(); // Executa a SQL query
            
            // Enquanto houver um resultado...
            while(rs.next()) {
                
                // Atualiza os dados do quarto de volta ao padrão
                sql = "UPDATE tbl_quartos SET idUsuario=?, disponibilidade=?, pago=?";
                
                // Pega a data de saída obtida e a converte de String para data
                String dataSaida = rs.getString("dataSaida");
                LocalDate dataSaidaConvertida = LocalDate.parse(dataSaida);
                
                LocalDate diaAtual = LocalDate.now(); // Pega a data atual
                
                // Se a data de saída for anterior ao dia de hoje
                if (dataSaidaConvertida.isBefore(diaAtual)) {
                    
                    pst = conexao.prepareStatement(sql); // Prepara a declaração SQL
                    
                    // Define os parâmetros para a declaração, sendo estes para retornar aos valores padrões
                    pst.setString(1, null);
                    pst.setString(2, "1");
                    pst.setString(3, "0");
                    
                    pst.executeUpdate(); // Executa a declaração SQL
                }
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
    
    
    // Método para fechar o JInternalFrame atualmente aberto ao abrir outro
    private void fecharFrame() {
        
        JInternalFrame[] frames = desktop.getAllFrames(); // Pega todos os frames abertos
        
        // Fecha cada um dos frames abertos
        for (JInternalFrame frame : frames) {
            
            frame.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        desktop = new javax.swing.JDesktopPane();
        menu = new javax.swing.JMenuBar();
        menuConta = new javax.swing.JMenu();
        usuarios = new javax.swing.JMenuItem();
        menuReserva = new javax.swing.JMenu();
        pagamentoReserva = new javax.swing.JMenuItem();
        fazerReserva = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        ajudaSobre = new javax.swing.JMenuItem();
        menuOpcoes = new javax.swing.JMenu();
        opcoesSair = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de CRUD");
        setResizable(false);

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );

        menuConta.setText("Conta");

        usuarios.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        usuarios.setText("Gerenciar");
        usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuariosActionPerformed(evt);
            }
        });
        menuConta.add(usuarios);

        menu.add(menuConta);

        menuReserva.setText("Reserva");

        pagamentoReserva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        pagamentoReserva.setText("Pagamento");
        pagamentoReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagamentoReservaActionPerformed(evt);
            }
        });
        menuReserva.add(pagamentoReserva);

        fazerReserva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        fazerReserva.setText("Reservar");
        fazerReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fazerReservaActionPerformed(evt);
            }
        });
        menuReserva.add(fazerReserva);

        menu.add(menuReserva);

        menuAjuda.setText("Ajuda");

        ajudaSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        ajudaSobre.setText("Sobre");
        ajudaSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajudaSobreActionPerformed(evt);
            }
        });
        menuAjuda.add(ajudaSobre);

        menu.add(menuAjuda);

        menuOpcoes.setText("Opções");

        opcoesSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        opcoesSair.setText("Sair");
        opcoesSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcoesSairActionPerformed(evt);
            }
        });
        menuOpcoes.add(opcoesSair);

        menu.add(menuOpcoes);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ajudaSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajudaSobreActionPerformed

        TelaSobre sobre = new TelaSobre(); // Criando uma instância da classe TelaSobre
        sobre.setVisible(true); // Torna visível o frame TelaSobre
    }//GEN-LAST:event_ajudaSobreActionPerformed

    private void opcoesSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcoesSairActionPerformed

        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
        
        // Verificando se o usuário clicou em "Yes"
        if (sair == JOptionPane.YES_OPTION) {
            
            System.exit(0); // Sai do aplicativo
        }
    }//GEN-LAST:event_opcoesSairActionPerformed

    private void usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuariosActionPerformed

        fecharFrame(); // Chama o método fecharFrame ao abrir o frame
        
        TelaContas conta = new TelaContas(); // Criando uma instância da classe TelaContas
        
        conta.setVisible(true); // Torna visível o frame TelaContas
        
        desktop.add(conta); // Adiciona o frame TelaContas ao painel da área de trabalho
    }//GEN-LAST:event_usuariosActionPerformed

    private void fazerReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fazerReservaActionPerformed

        fecharFrame(); // Chama o método fecharFrame ao abrir o frame
        
        TelaReservar reservar = new TelaReservar(); // Criando uma instância da classe TelaReservar
        
        reservar.setVisible(true); // Torna visível o frame TelaReservar
        
        desktop.add(reservar); // Adiciona o frame TelaReservar ao painel da área de trabalho
    }//GEN-LAST:event_fazerReservaActionPerformed

    private void pagamentoReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagamentoReservaActionPerformed
        
        fecharFrame(); // Chama o método fecharFrame ao abrir o frame
                
        TelaPagamento visualizar = new TelaPagamento(); // Criando uma instância da classe TelaVisualizarReserva
        
        visualizar.setVisible(true); // Torna visível o frame TelaVisualizarReserva
        
        desktop.add(visualizar); // Adiciona o frame TelaVisualizarReserva ao painel da área de trabalho
    }//GEN-LAST:event_pagamentoReservaActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ajudaSobre;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JMenuItem fazerReserva;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenu menuConta;
    private javax.swing.JMenu menuOpcoes;
    private javax.swing.JMenu menuReserva;
    private javax.swing.JMenuItem opcoesSair;
    private javax.swing.JMenuItem pagamentoReserva;
    private javax.swing.JMenuItem usuarios;
    // End of variables declaration//GEN-END:variables
}
