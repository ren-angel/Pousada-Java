package telas;
import javax.swing.JOptionPane;

public class TelaPrincipal extends javax.swing.JFrame {

    public TelaPrincipal() {
        initComponents();
        
        // Essa linha de código define o texto de um JLabel chamado labelManual como uma string de várias linhas usando formatação HTML. O texto é envolvido por tags <html> e <body> para permitir a renderização de HTML dentro do componente JLabel. Isto permite formatação como quebra de linhas com maior facilidade
        labelManual.setText("<html><body>Para utilizar o programa e manejar os usuários, vá no canto superior esquerdo e clique em \"Cadastro\", então em \"Usuarios\", ou pressione CTRL+N.<br> Clique em \"Ajuda\" e então em \"Sobre\", ou pressione CTRL+S para ver informações sobre o aplicativo.<br> Para sair do programa, clique em \"Opções\" e então em \"Sair\" ou pressione ESC.</body></html>");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        desktop = new javax.swing.JDesktopPane();
        menu = new javax.swing.JMenuBar();
        menuCadastro = new javax.swing.JMenu();
        usuarios = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        visualizarReservas = new javax.swing.JMenuItem();
        gerenciarReservas = new javax.swing.JMenuItem();
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
            .addGap(0, 605, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );

        menuCadastro.setText("Cadastro");

        usuarios.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        usuarios.setText("Usuários");
        usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuariosActionPerformed(evt);
            }
        });
        menuCadastro.add(usuarios);

        menu.add(menuCadastro);

        jMenu1.setText("Reservas");

        visualizarReservas.setText("Visualizar");
        jMenu1.add(visualizarReservas);

        gerenciarReservas.setText("Gerenciar");
        gerenciarReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerenciarReservasActionPerformed(evt);
            }
        });
        jMenu1.add(gerenciarReservas);

        menu.add(jMenu1);

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

        TelaUsuarios usuario = new TelaUsuarios(); // Criando uma instância da classe TelaUsuarios
        
        usuario.setVisible(true); // Torna visível o frame TelaUsuarios
        
        desktop.add(usuario); // Adiciona o frame TelaUsuarios ao painel da área de trabalho
    }//GEN-LAST:event_usuariosActionPerformed

    private void gerenciarReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerenciarReservasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gerenciarReservasActionPerformed

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
    private javax.swing.JMenuItem gerenciarReservas;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenu menuCadastro;
    private javax.swing.JMenu menuOpcoes;
    private javax.swing.JMenuItem opcoesSair;
    private javax.swing.JMenuItem usuarios;
    private javax.swing.JMenuItem visualizarReservas;
    // End of variables declaration//GEN-END:variables
}
