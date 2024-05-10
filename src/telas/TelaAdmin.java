package telas;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.Color;
import java.awt.Component;
import validacao.Validacoes;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TelaAdmin extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
 
    public TelaAdmin() {            
        initComponents();
        
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
        
        // Tira a borda do JInternalFrame
        this.setBorder(null);
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
                        
        mostrarQuartos(); // Chama o método para mostrar os quartos que o usuário reservou
    }

    // Método para mostrar todos os quartos que o usuário atualmente logado reservou
    private void mostrarQuartos() {
        
        // Pega no banco nome, descricao e preco da tabela quartos
        String pegarQuartos = "SELECT nome, descricao, preco FROM tbl_quartos";
        
        try {
            
            pst = conexao.prepareStatement(pegarQuartos); // Prepara a declaração SQL para pegar os quartos
                        
            rs = pst.executeQuery(); // Executa a SQL query

            int i = 0; // Variável para calcular o valor do eixo y dos painéis dos quartos e para ser usado como seu ID
            
            // Enquanto houver um resultado...
            while(rs.next()) {
                
                // Array com todos os valores consultados na query
                String[] labelTexts = new String[3];
                labelTexts[0] = rs.getString("nome");
                labelTexts[1] = rs.getString("descricao");
                labelTexts[2] = rs.getString("preco");
                
                // Calcula o eixo y do painel que conterá as informações dos quartos
                int y = 124;
                y *= i;
                y -= (4 * (i - 1));
                i++;

                javax.swing.JPanel paineis = criarPainel(labelTexts, y, i); // Chama o método que monta os painéis dos quartos na inteface
                
                jPanel2.add(paineis); // adiciona os paineis dos quartos dentro da ScrollPane
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
    
    // Método para montar os painéis com as informações dos quartos do usuário
    private javax.swing.JPanel criarPainel(String[] labelTexts, int y, int id) {
        
        javax.swing.JPanel painel = new javax.swing.JPanel(); // Cria o componente painel

        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS)); // Muda o layout do painel para BoxLayout no estilo vertical

        painel.setBounds(0, y, 610, 130); // Define a posição e as dimensões do painel

        painel.add(Box.createRigidArea(new Dimension(0, 10))); // Aplica quebra de linha no inicío do componente para separar melhor um painel do outro

        // Cria os labels contendo as informações do quarto, centraliza-os e os adiciona ao painel
        javax.swing.JLabel nomeQuarto = new javax.swing.JLabel();
        nomeQuarto.setText(labelTexts[0]);
        nomeQuarto.setAlignmentX(CENTER_ALIGNMENT);

        // Aumenta a fonte do nome do quarto e a deixa em negrito
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

        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Atualiza as informações do quarto
        String atualizarInfoQuarto = "UPDATE tbl_quartos SET nome=?, descricao=?, preco=? WHERE id=?";

        // Cria o botão com a funcionalidade de editar o quarto
        javax.swing.JButton editar = new javax.swing.JButton();
        editar.setText("Editar");
        editar.setAlignmentX(CENTER_ALIGNMENT);
            
        // Atribuí uma ação ao botão
        editar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String novoNome  = JOptionPane.showInputDialog(null, "Digite o novo nome", "Editar quarto", JOptionPane.PLAIN_MESSAGE);
                String novaDescricao = JOptionPane.showInputDialog(null, "Digite a nova descrição", "Editar quarto", JOptionPane.PLAIN_MESSAGE);
                String novoPreco = JOptionPane.showInputDialog(null, "Digite o novo preço", "Editar quarto", JOptionPane.PLAIN_MESSAGE);
                
                try {
                    
                    // Se alguma das informações não for colocada, mantém o valor original
                    if (novoNome == null) {
                        
                        novoNome = labelTexts[0];
                    }
                    
                    if (novaDescricao == null) {
                        
                        novaDescricao = labelTexts[1];
                    }
                    
                    if (novoPreco == null) {
                        
                        novoPreco = labelTexts[2];
                    }

                    pst = conexao.prepareStatement(atualizarInfoQuarto); // Prepara a declaração SQL

                    // Define os parâmetros para a declaração, sendo estes para atualizar o quarto com as novas informações
                    pst.setString(1, novoNome);
                    pst.setString(2, novaDescricao);
                    pst.setString(3, novoPreco);
                    pst.setString(4, String.valueOf(id));

                    int atualizado = pst.executeUpdate(); // Executa a declaração SQL

                    // Se der certo, mostra uma mensagem de sucesso e fecha a tela de admin
                    if(atualizado > 0) {

                        JOptionPane.showMessageDialog(null, "Quarto atualizado com sucesso");

                        JInternalFrame parentFrame = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, (Component) e.getSource());
                        parentFrame.dispose();
//                                }
                    }
                } catch (Exception error) {

                    JOptionPane.showMessageDialog(null, error); // Se ocorrer uma exceção, mostra uma mensagem de erro
                }
            }
        });
        
        painel.add(editar);
        
        // Remove o quarto do banco
        String removerQuarto = "DELETE FROM tbl_quartos WHERE id=?";
        
        // Cria o botão com a funcionalidade de remover o quarto
        javax.swing.JButton deletar = new javax.swing.JButton();
        deletar.setText("Remover");
        deletar.setAlignmentX(CENTER_ALIGNMENT);
            
        // Atribuí uma ação ao botão
        deletar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int confirmacao = JOptionPane.showConfirmDialog(null, "Remover o quarto da Pousada?", "Atenção", JOptionPane.YES_NO_OPTION);

                // Verificando se o usuário clicou em "Yes"
                if (confirmacao == JOptionPane.YES_OPTION) {
                
                    try {

                        pst = conexao.prepareStatement(removerQuarto); // Prepara a declaração SQL

                        pst.setString(1, String.valueOf(id)); // Define o primeiro parâmetro para a declaração SQL baseado no ID selecionado
                        
                        // Executa a declaração SQL
                        int deletado = pst.executeUpdate();

                        // Se a remoção do quarto for bem-sucedida, mostra uma mensagem de sucesso e fecha a tela de admin
                        if(deletado > 0) {

                            JOptionPane.showMessageDialog(null, "Quarto removido com sucesso");

                            JInternalFrame parentFrame = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, (Component) e.getSource());
                            parentFrame.dispose();
                        }
                    } catch (Exception error) {

                        JOptionPane.showMessageDialog(null, error); // Se ocorrer uma exceção, mostra uma mensagem de erro
                    }
                }
            }
        });
        
        painel.add(deletar);

        return painel;
    }
    
    private void adicionarQuarto() {
        
        // Declarações SQL para inserir as informações no banco de dados
        String sql = "INSERT INTO tbl_quartos (nome,descricao,preco,dataEntrada,dataSaida,idUsuario,disponibilidade,pago) VALUES (?,?,?,?,?,?,?,?)";
        
        // Loop para quando o usuário digitar algo errado
        boolean loop = true;
        while (loop) {
                        
            String nome  = JOptionPane.showInputDialog(null, "Digite o nome do quarto", "Editar quarto", JOptionPane.PLAIN_MESSAGE);
            String descricao = JOptionPane.showInputDialog(null, "Digite a descrição do quarto", "Editar quarto", JOptionPane.PLAIN_MESSAGE);
            String preco = JOptionPane.showInputDialog(null, "Digite o preço do quarto", "Editar quarto", JOptionPane.PLAIN_MESSAGE);
            LocalDate diaAtual = LocalDate.now();

            // Se o usuário não entrar nada, encerra o loop. Se fizer tudo corretamente, o processo de adicionar o quarto ocorrerá
            if(nome == null && descricao == null && preco == null){
                        
                loop = false;
            } else {
                
                try {

                    pst = conexao.prepareStatement(sql); // Prepara a primeira declaração SQL

                    // Define os parâmetros para a declaração
                    pst.setString(1, nome);
                    pst.setString(2, descricao);
                    pst.setString(3, preco);
                    pst.setString(4, String.valueOf(diaAtual));
                    pst.setString(5, String.valueOf(diaAtual));
                    pst.setString(6, null);
                    pst.setString(7, "1");
                    pst.setString(8, "0");

                    // Executa a declaração SQL
                    int adicionado = pst.executeUpdate();

                    // Se a inserção for bem-sucedida, mostra uma mensagem de sucesso, encerra o loop e fecha a tela de admin
                    if(adicionado > 0) {

                        JOptionPane.showMessageDialog(null, "Quarto adicionado com sucesso");
                        
                        loop = false;

                        this.dispose();
                    }
                } catch(Exception e) {

                    JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();

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
            .addGap(0, 3754, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(jPanel2);

        getContentPane().add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 490));

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });
        getContentPane().add(btnVoltar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 510, -1, -1));

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 510, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed

        this.dispose(); // Fecha a tela de admin
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        adicionarQuarto(); // Atribuí o método de adicionarQuarto ao botão Adicionar
    }//GEN-LAST:event_btnAdicionarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
