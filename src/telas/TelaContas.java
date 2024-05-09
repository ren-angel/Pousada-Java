package telas;

import java.sql.*;
import dal.ModuloConexao;
import validacao.Validacoes;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class TelaContas extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
    
    String ID = String.valueOf(TelaLogin.getUserID()); // Pega o ID do usuário atual
    
    public TelaContas() {
        initComponents();
        
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
        
        // Remove a barra de título
        this.setBorder(null);
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        
        // Chama o método mostra os dados do usuário
        mostrarDados();
    }
    
    // Método para visualizar os dados de um usuário cadastrado
    private void mostrarDados() {
                
        String sql = "SELECT * FROM tbl_login WHERE id=?"; // Declaração SQL para buscar dados do banco de dados

        try {

            pst = conexao.prepareStatement(sql); // Prepara a declaração SQL

            pst.setString(1, ID); // Define o parâmetro para a declaração SQL baseado no ID selecionado

            rs = pst.executeQuery(); // Executa a SQL query

            // Se um resultado for encontrado, preenche os text fields com os dados recuperados
            if(rs.next()) {

                // Pega os dados do banco e os inseri nos text fields
                txtNome.setText(rs.getString(2));
                txtSenha.setText(rs.getString(3));
                txtEmail.setText(rs.getString(4));
                txtCPF.setText(rs.getString(5));
                txtDDD.setText(rs.getString(6));
                txtCelular.setText(rs.getString(7));
                txtEndereco.setText(rs.getString(8));
                txtEstado.setText(rs.getString(9));
                txtCidade.setText(rs.getString(10));
            }
        } catch(Exception e) {

            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
    
    // Método para editar os dados de um usuário cadastrado 
    private void alterar() {
        
        // Declarações SQL para atualizar as informações do banco de dados
        String sql = "UPDATE tbl_login SET nome=?, senha=?, email=?, cpf=?, ddd=?, celular=?, endereco=?, estado=?, cidade=? WHERE id=?";
        
        // Validações de cada entrada
        if(txtNome.getText().length() < 1 || txtNome.getText().length() > 100) {
            
            JOptionPane.showMessageDialog(null, "Nome deve ter dentre 1 a 100 caracteres");
        } else if(!txtDDD.getText().matches("[0-9]+")) {
        
            JOptionPane.showMessageDialog(null, "DDD precisa ser um número");
        } else if(txtDDD.getText().length() != 2) {

            JOptionPane.showMessageDialog(null, "DDD precisa ter 2 dígitos");
        } else if(!txtCelular.getText().matches("[0-9]+")) {

            JOptionPane.showMessageDialog(null, "Celular precisa ser um número");
        } else if(txtCelular.getText().length() != 9) {

            JOptionPane.showMessageDialog(null, "Celular precisa ter 9 dígitos");
        } else if(txtEmail.getText().length() < 1 || txtEmail.getText().length() > 50) {
            
            JOptionPane.showMessageDialog(null, "E-mail deve ter dentre 1 a 50 caracteres");
        } else if(txtSenha.getText().length() < 1 || txtSenha.getText().length() > 100) {
            
            JOptionPane.showMessageDialog(null, "Senha deve ter dentre 1 a 100 caracteres");
        } else if(!Validacoes.validarCPF(txtCPF.getText())) {

            JOptionPane.showMessageDialog(null, "CPF inválido");
        } else if(txtEndereco.getText().length() < 1 || txtEndereco.getText().length() > 150) {
            
            JOptionPane.showMessageDialog(null, "Endereço deve ter dentre 1 a 150 caracteres");
        } else if(txtCidade.getText().length() < 1 || txtCidade.getText().length() > 70) {
            
            JOptionPane.showMessageDialog(null, "Cidade deve ter dentre 1 a 70 caracteres");
        } else if(txtEstado.getText().length() < 1 || txtEstado.getText().length() > 70) {
            
            JOptionPane.showMessageDialog(null, "Estado deve ter dentre 1 a 70 caracteres");
        } else {
        
            try {

                pst = conexao.prepareStatement(sql); // Prepara a primeira declaração SQL

                // Define os parâmetros para a primeira declaração
                pst.setString(1, txtNome.getText());
                pst.setString(2, txtSenha.getText());
                pst.setString(3, txtEmail.getText());
                pst.setString(4, txtCPF.getText());
                pst.setString(5, txtDDD.getText());
                pst.setString(6, txtCelular.getText());
                pst.setString(7, txtEndereco.getText());
                pst.setString(8, txtEstado.getText());
                pst.setString(9, txtCidade.getText());
                pst.setString(10, ID);

                // Executa ambas as declarações SQL
                int alterado = pst.executeUpdate();

                // Se a atualização dos dados do usuário em ambas as tabelas for bem-sucedida, mostra uma mensagem de sucesso e limpa os text fields
                if(alterado > 0) {

                    JOptionPane.showMessageDialog(null, "Usuário atualizado");
                }
            } catch(Exception e) {

                JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
            }
        }
    }
    
    // Método para apagar para os dados de um usuário do banco de dados
    private void apagar() {
        
        // Exibe uma caixa de diálogo de confirmação para garantir que o usuário realmente deseja excluir o usuário
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuário?", "ATENÇÂO", JOptionPane.YES_NO_OPTION);
        
        // Se o usuário confirmar a exclusão
        if(confirmar == JOptionPane.YES_OPTION) {
            
            // Declarações SQL para excluir os dados do banco de dados
            String sql = "DELETE FROM tbl_login WHERE id=?";
                
            try {

                pst = conexao.prepareStatement(sql); // Prepara a primeira declaração SQL

                pst.setString(1, ID); // Define o primeiro parâmetro para a declaração SQL baseado no ID selecionado

                // Executa ambas as declarações SQL
                int adicionado = pst.executeUpdate();

                // Se o deletar do usuário em ambas as tabelas for bem-sucedida, mostra uma mensagem de sucesso e limpa os text fields
                if(adicionado > 0) {

                    JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso");

                    // Pega o componente pai desta tela, no caso, o TelaPrincipal
                    javax.swing.JFrame principal = (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this);
                    
                    // Volta para a tela de login
                    principal.dispose();
                    TelaLogin login = new TelaLogin();
                    login.setVisible(true);
                }
            } catch(Exception e) {

                JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDDD = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JTextField();
        voltar = new javax.swing.JButton();

        jLabel1.setText("EDITAR CONTA");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Nome");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Email");

        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("DDD");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Celular");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("CPF");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Endereço");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Cidade");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Estado");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Senha");

        voltar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        voltar.setText("Voltar");
        voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(255, 255, 255))
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEditar)
                        .addGap(141, 425, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDDD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(voltar)
                                    .addGap(66, 66, 66)
                                    .addComponent(btnExcluir))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCPF, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                    .addComponent(txtCidade)
                                    .addComponent(txtEstado)
                                    .addComponent(txtEndereco)
                                    .addComponent(txtEmail)
                                    .addComponent(txtSenha)
                                    .addComponent(txtNome))))
                        .addGap(0, 128, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir)
                    .addComponent(voltar))
                .addGap(59, 59, 59))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        alterar(); // Atribuí o método de alterar ao botão Editar
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        apagar(); // Atribuí o método de apagar ao botão Excluir
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voltarActionPerformed
        this.dispose();  // Fecha a tela de contas
    }//GEN-LAST:event_voltarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtDDD;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSenha;
    private javax.swing.JButton voltar;
    // End of variables declaration//GEN-END:variables
}
