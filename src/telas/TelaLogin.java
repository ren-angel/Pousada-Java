package telas;

import dal.ModuloConexao;
import java.sql.*;
import javax.swing.JOptionPane;

public class TelaLogin extends javax.swing.JFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
    static int ID = 0;
    
    public TelaLogin() {
        initComponents();
        
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
    }
    
    // Método para fazer login
    public void Logar() {
        
        String sql = "SELECT id FROM tbl_login WHERE nome=? AND senha=?"; // SQL query para recuperar o usuário com base no e-mail e na senha
        
        try {
            
            pst = conexao.prepareStatement (sql); //Prepara a declaração SQL
            
            // Definindo os parâmetros (email e senha) para a declaração preparada
            pst.setString(1, inputEmail.getText());
            pst.setString(2, inputSenha.getText());
            
            rs = pst.executeQuery(); // Executando a query e armazenando o resultado no ResultSet
            
            // Verifica se há um usuário correspondente
            if (rs.next()) {
                
                ID = rs.getInt("id"); // Pega o ID do usuário ao logar
                
                TelaPrincipal principal = new TelaPrincipal(); // Criando uma instância da classe TelaPrincipal
                
                principal.setVisible(true); // Torna visível o frame TelaPrincipal
                                
                this.dispose(); // Descarta o frame atual
                conexao.close(); // Fechan a conexão com o banco de dados
            } else {
                
                JOptionPane.showMessageDialog(null, "Usuário/Senha Inválido");
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para limpar campos de entrada
    private void Limpar() {
        
        try {
            
            inputEmail.setText(null);
            inputSenha.setText(null);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para abrir a janela de cadastro de um novo usuário
    private void Cadastrar() {
        
        TelaCadastro cadastrar = new TelaCadastro(); // Criando uma instância da classe TelaCadastro
        cadastrar.setVisible(true); // Torna visível o frame TelaCadastro        
    }
    
    // Exporta o ID do usuário para ser usado como token para mostrar apenas as informações do usuário atual
    public static int getUserID() {
        
        return ID;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        txtNome = new javax.swing.JLabel();
        txtSenha = new javax.swing.JLabel();
        inputEmail = new javax.swing.JTextField();
        botaoLogin = new javax.swing.JButton();
        botaoLimpar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        inputSenha = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela de Login");
        setResizable(false);

        txtNome.setText("Nome");

        txtSenha.setText("Senha");

        botaoLogin.setText("LOGIN");
        botaoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLoginActionPerformed(evt);
            }
        });

        botaoLimpar.setText("LIMPAR");
        botaoLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLimparActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Informe suas credenciais");

        jLabel2.setText("Não possuí uma conta?");

        jButton1.setText("Clique aqui para criar conta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel2)
                .addGap(44, 44, 44)
                .addComponent(jButton1)
                .addContainerGap(58, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(botaoLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoLimpar)
                .addGap(79, 79, 79))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome)
                    .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenha)
                    .addComponent(inputSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoLogin)
                    .addComponent(botaoLimpar))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Invocando o método Logar() quando o botão de login é clicado
    private void botaoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLoginActionPerformed
        
        Logar(); // Atribuí o método de logar ao botão Login
    }//GEN-LAST:event_botaoLoginActionPerformed

    // Invocando o método Limpar() quando o botão de limpar é clicado
    private void botaoLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLimparActionPerformed
        
        Limpar(); // Atribuí o método de limpar ao botão Limpar
    }//GEN-LAST:event_botaoLimparActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Cadastrar(); // Atribuí o método de cadastrar um novo usuário ao botão de cadastrar
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoLimpar;
    private javax.swing.JButton botaoLogin;
    private javax.swing.JTextField inputEmail;
    private javax.swing.JPasswordField inputSenha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel txtNome;
    private javax.swing.JLabel txtSenha;
    // End of variables declaration//GEN-END:variables
}
