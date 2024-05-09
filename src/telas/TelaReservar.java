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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TelaReservar extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
 
    public TelaReservar() {            
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
        
        // Pega no banco nome, descricao, preco e disponibilidade da tabela quartos
        String pegarQuartos = "SELECT nome, descricao, preco, disponibilidade FROM tbl_quartos";
        
        try {
            
            pst = conexao.prepareStatement(pegarQuartos); // Prepara a declaração SQL para pegar os quartos
                        
            rs = pst.executeQuery(); // Executa a SQL query

            int i = 0; // Variável para calcular o valor do eixo y dos painéis dos quartos e para ser usado como seu ID
            
            // Enquanto houver um resultado...
            while(rs.next()) {
                
                // Array com todos os valores consultados na query
                String[] labelTexts = new String[4];
                labelTexts[0] = rs.getString("nome");
                labelTexts[1] = rs.getString("descricao");
                labelTexts[2] = rs.getString("preco");
                labelTexts[3] = rs.getString("disponibilidade");
                
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

        painel.setBounds(0, y, 610, 120); // Define a posição e as dimensões do painel

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

        javax.swing.JLabel disponibilidade = new javax.swing.JLabel();
        
        // Condição para ajustar o texto da disponibilidade baseado no seu valor
        if (Integer.parseInt(labelTexts[3]) == 0) {

            disponibilidade.setText("Ocupado");
            disponibilidade.setForeground(Color.red);
        } else {

            disponibilidade.setText("Disponível");
            disponibilidade.setForeground(Color.green);
        }

        disponibilidade.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(disponibilidade);

        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Atualiza as informações relacionadas ao usuário no quarto que for reservado
        String atualizarInfoQuarto = "UPDATE tbl_quartos SET dataEntrada=?, dataSaida=?, idUsuario=? WHERE id=?";

        // Cria o botão com a funcionalidade de enviar o quarto ao carrinho
        javax.swing.JButton fazerReserva = new javax.swing.JButton();
        fazerReserva.setText("Colocar no carrinho");
        fazerReserva.setAlignmentX(CENTER_ALIGNMENT);

        // Se o quarto estiver disponível, efetua o processo de encaminhar ao carrinho. Senão, exibe uma mensagem dizendo que ele já está ocupado
        if (disponibilidade.getText() == "Disponível") {
            
            // Atribuí uma ação ao botão
            fazerReserva.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    // Loop para quando o usuário digitar algo errado
                    boolean loop = true;
                    while (loop) {
                        
                        String dataEntrada = JOptionPane.showInputDialog(null, "Qual a data de entrada (no formato dd/mm/aaaa) da reserva?", "Tempo de reserva", JOptionPane.QUESTION_MESSAGE);
                        String dataSaida = JOptionPane.showInputDialog(null, "Qual a data de saída (no formato dd/mm/aaaa) da reserva? Você pagará o preço do quarto por cada dia reservado", "Tempo de reserva", JOptionPane.QUESTION_MESSAGE);

                        // Se o usuário digitar uma data inválida, exibe a mensagem e reinicia o loop. Se não entrar nada, encerra o loop. Se fizer tudo corretamente, o processo de encaminhar ao carrinho ocorrerá
                        if (Validacoes.validarData(dataEntrada) || Validacoes.validarData(dataSaida)) {

                            JOptionPane.showMessageDialog(null, "Insira uma data válida");
                        } else if(dataEntrada == null || dataSaida == null){
                        
                            loop = false;
                        } else {

                            try {

                                pst = conexao.prepareStatement(atualizarInfoQuarto); // Prepara a declaração SQL

                                // Define os parâmetros para a declaração, sendo estes para adicionar as informações postas pelo usuário e o seu id ao quarto
                                pst.setString(1, Validacoes.converterDataParaFormatoSQL(dataEntrada));
                                pst.setString(2, Validacoes.converterDataParaFormatoSQL(dataSaida));
                                pst.setString(3, String.valueOf(TelaLogin.getUserID()));
                                pst.setString(4, String.valueOf(id));

                                int atualizado = pst.executeUpdate(); // Executa a declaração SQL

                                // Se der certo, mostra uma mensagem de sucesso, encerra o loop e fecha a tela de reservar
                                if(atualizado > 0) {

                                    JOptionPane.showMessageDialog(null, "Quarto encaminhado ao carrinho! Vá para a tela de pagamento para efetuar o pagamento e concluir a reserva");

                                    loop = false;

                                    JInternalFrame parentFrame = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, (Component) e.getSource());
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
            
            // Atribuí uma ação ao botão
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
