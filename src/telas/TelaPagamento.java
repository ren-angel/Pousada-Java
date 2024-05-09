package telas;
import dal.ModuloConexao;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import validacao.Validacoes;

public class TelaPagamento extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados

    public TelaPagamento() {
        initComponents();
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
        
        total(); // Chama o método total quando a pagina carrega
        
        // Tira a borda do JInternalFrame
        this.setBorder(null);
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        
        mostrarQuartos(); // Chama o método para mostrar os quartos que o usuário reservou
    }
    
    // Método para mostrar todos os quartos que o usuário atualmente logado reservou
    private void mostrarQuartos(){
        
        // Pega no banco nome, descricao, periodo e preco da tabela quartos
        String sql = "SELECT nome, descricao, disponibilidade, dataEntrada, dataSaida, preco FROM tbl_quartos WHERE idUsuario=? AND pago=0";
        
        String idUsuario = String.valueOf(TelaLogin.getUserID()); // Chama o método que pega o ID do usuário atualmente logado
        
        try {
            
            pst = conexao.prepareStatement(sql); // Prepara a declaração SQL
            
            pst.setString(1, idUsuario); // Define o id do usuário para a declaração
            
            rs = pst.executeQuery(); // Executa a SQL query
            
            int i = 0; // Variável para calcular o valor do eixo y dos painéis dos quartos
            
            // Enquanto houver um resultado...
            while(rs.next()){
                
                // Array com todos os valores consultados na query
                String[] labelTexts = new String[6];
                labelTexts[0] = rs.getString("nome");
                labelTexts[1] = rs.getString("descricao");
                labelTexts[2] = rs.getString("disponibilidade");
                labelTexts[3] = rs.getString("dataEntrada");
                labelTexts[4] = rs.getString("dataSaida");
                labelTexts[5] = rs.getString("preco");
                
                // Calcula o eixo y do painel que conterá as informações dos quartos
                int y = 124;
                y *= i;
                y -= (4 * (i - 1));
                i++;

                javax.swing.JPanel paineis = criarPainel(labelTexts, y); // Chama o método que monta os painéis dos quartos na inteface

                painelQuartos.add(paineis); // adiciona os paineis dos quartos dentro da ScrollPane
            }
        } catch(Exception e){
            
            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
    
    // Método para montar os painéis com as informações dos quartos do usuário
    private javax.swing.JPanel criarPainel(String[] labelTexts, int y) {
        
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
        
        javax.swing.JLabel disponibilidade = new javax.swing.JLabel();
        
        // Condição para ajustar o texto da disponibilidade baseado no seu valor
        if (Integer.parseInt(labelTexts[2]) == 0) {

            disponibilidade.setText("Ocupado");
        } else {

            disponibilidade.setText("Disponível");
        }
        
        disponibilidade.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(disponibilidade);
        
        javax.swing.JLabel dataEntrada = new javax.swing.JLabel();
        dataEntrada.setText("Data de entrada: " + Validacoes.converterDataSQLParaFormatado(labelTexts[3]));
        dataEntrada.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(dataEntrada);
        
        javax.swing.JLabel dataSaida = new javax.swing.JLabel();
        dataSaida.setText("Data de saída: " + Validacoes.converterDataSQLParaFormatado(labelTexts[4]));
        dataSaida.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(dataSaida);

        javax.swing.JLabel preco = new javax.swing.JLabel();
        preco.setText("R$ " + labelTexts[5]);
        preco.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(preco);

        return painel;
    }
    
    // Método para calcular o valor total de todos os quartos contando os dias de uso de cada quarto
    private void total(){
        
        String idusuario = String.valueOf(TelaLogin.getUserID()); // Chama o método que pega o ID do usuário atualmente logado
        
        // Pega as informações necessárias dos quartos não pagos para calcular o valor total
        String sql = "SELECT preco, dataEntrada, dataSaida FROM tbl_quartos WHERE idUsuario=? AND pago=0"; 
        
        float total = 0; // Variável que guardará o valor total dos quartos somados
        
        try {
            
            pst = conexao.prepareStatement(sql); // Prepara a declaração SQL
            
            pst.setString(1, idusuario); // Define o id do usuário para a declaração
            
            rs = pst.executeQuery(); // Executa a SQL query
            
            // Enquanto houver um resultado...
            while(rs.next()){
                
                String preco = rs.getString("preco"); // Pega o preço dos quartos que o usuario selecionou.
                // Pega os dias de entrada e saída para calcular o preço do quarto com os dias somados
                String dataEntrada = rs.getString("dataEntrada");
                String dataSaida = rs.getString("dataSaida");
                
                // Converte-os de String para data
                LocalDate dataEntradaConvertida = LocalDate.parse(dataEntrada);
                LocalDate dataSaidaConvertida = LocalDate.parse(dataSaida);
                
                long qdtDias = ChronoUnit.DAYS.between(dataEntradaConvertida, dataSaidaConvertida) + 1; // Pega a diferença de dias comparando a entrada com a saída
                
                float precoTotal = Float.parseFloat(preco) * qdtDias; // Calcula o preço total do quarto levando os dias em conta
                
                total += precoTotal; // Soma o preço de todos os quartos

                lblValor.setText(String.valueOf(total)); // Exibe o valor total dos quartos
            }
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
    
    // Método para cancelar a reserva no caso de mudança de planos, data colocada errada, ou qualquer outro motivo
    private void cancelarReserva() {
        
        // Atualiza os valores necessários para disponibilizar o quarto novamente
        String sql = "UPDATE tbl_quartos SET idUsuario=?, disponibilidade=? WHERE idUsuario=? AND pago=0";
        
        String idusuario = String.valueOf(TelaLogin.getUserID()); // Chama o método que pega o ID do usuário atualmente logado
        
        try {

            pst = conexao.prepareStatement(sql); // Prepara a declaração SQL

            // Define os parâmetros para a declaração, sendo estes para retornar aos valores padrões
            pst.setString(1, null);
            pst.setString(2, "1");
            pst.setString(3, idusuario);

            int cancelado = pst.executeUpdate(); // Executa a declaração SQL

            // Se o cancelar da reserva for bem-sucedida, mostra uma mensagem de sucesso
            if(cancelado > 0) {

                JOptionPane.showMessageDialog(null, "Reserva cancelada");
            }

            this.dispose(); // Fecha a tela de pagamento
        } catch(Exception e) {

            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
    
    // Método que efetua o pagamento da reserva
    private void pagamento(){
                
        // Pega informações sobre os quartos para validação
        String sql = "SELECT nome, disponibilidade FROM tbl_quartos WHERE idUsuario=? AND pago=0";
        
        String idusuario = String.valueOf(TelaLogin.getUserID()); // Chama o método que pega o ID do usuário atualmente logado
        
        try{
            
            int pagamento = JOptionPane.showConfirmDialog(null, "Confirmar Pagamento?", "Atenção", JOptionPane.YES_NO_OPTION);

            // Verificando se o usuário clicou em "Yes"
            if (pagamento == JOptionPane.YES_OPTION) {
            
                pst = conexao.prepareStatement(sql); // Prepara a declaração SQL
                
                pst.setString(1, idusuario); // Define o id do usuário para a declaração

                rs = pst.executeQuery(); // Executa a SQL query

                // Enquanto houver um resultado...
                while(rs.next()) {
                    
                    // Pega o nome e a disponibilidade do quarto para validar a disponibilidade
                    String nome = rs.getString("nome");
                    String disponibilidade = rs.getString("disponibilidade");
                                    
                    // Se algum quarto estiver reservado, exibe uma mensagem. Senão, efetua o pagamento
                    if (disponibilidade == "0"){

                        JOptionPane.showMessageDialog(null, nome + " já está reservado");
                    } else {

                        // Atualiza as informações do quarto que devem mudar ao o pagamento ser concluído
                        sql = "UPDATE tbl_quartos SET disponibilidade=?, pago=? WHERE idUsuario=? AND pago=0";

                        pst = conexao.prepareStatement(sql); // Prepara a declaração SQL

                        // Define os parâmetros para a declaração, sendo estes para tornar a disponibilidade como ocupado e o pago como verdadeiro
                        pst.setString(1, "0");
                        pst.setString(2, "1");
                        pst.setString(3, idusuario);

                        int pago = pst.executeUpdate(); // Executa a declaração SQL
                        
                        // Se o pagamento for bem-sucedido, exibe a mensagem
                        if (pago > 0) {
                            
                            JOptionPane.showMessageDialog(null, nome + " reservado!");
                        }
                    }
                }
                
                this.dispose();  // Fecha a tela de pagamento
            }
        }
        catch(Exception e){
            
            JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
        }
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pagar = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        painelQuartos = new javax.swing.JPanel();
        cancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle("Pagamento");

        jLabel1.setText("Quartos");

        lblValor.setText("0");

        jLabel3.setText("Total a pagar:");

        pagar.setText("Pagar");
        pagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagarActionPerformed(evt);
            }
        });

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout painelQuartosLayout = new javax.swing.GroupLayout(painelQuartos);
        painelQuartos.setLayout(painelQuartosLayout);
        painelQuartosLayout.setHorizontalGroup(
            painelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );
        painelQuartosLayout.setVerticalGroup(
            painelQuartosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 825, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(painelQuartos);

        cancelar.setText("Cancelar Reserva");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        jButton1.setText("Voltar");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(272, 272, 272)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(141, 141, 141)
                                .addComponent(jLabel3)
                                .addGap(212, 212, 212)
                                .addComponent(lblValor)))
                        .addGap(0, 159, Short.MAX_VALUE))
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(cancelar)
                .addGap(39, 39, 39)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagar)
                .addGap(135, 135, 135))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValor)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pagar)
                    .addComponent(cancelar)
                    .addComponent(jButton1))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagarActionPerformed
        
        pagamento(); // Atribuí o método pagamento ao botão Pagar
    }//GEN-LAST:event_pagarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        
        cancelarReserva(); // Atribuí o método cancelarReserva ao botão Cancelar
    }//GEN-LAST:event_cancelarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();  // Fecha a tela de pagamento
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblValor;
    private javax.swing.JButton pagar;
    private javax.swing.JPanel painelQuartos;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
