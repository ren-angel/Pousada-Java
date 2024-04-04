package telas;

import java.sql.*;
import dal.ModuloConexao;
import validacao.Validacoes;
import javax.swing.JOptionPane;
import java.awt.Color;

public class TelaContas extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null; // PreparedStatement é uma declaração SQL pré-compilada que permite queries parametrizadas, melhorando o desempenho e a segurança ao evitar ataques de intrusão de SQL
    ResultSet rs = null; // ResultSet é um objeto usado para recuperar e manipular dados de um banco de dados após a execução de uma query. Ele fornece métodos para percorrer as linhas de dados, acessar valores de colunas individuais e executar operações como atualizações e exclusões nos dados
    
    public TelaContas() {
        initComponents();
        
        conexao = ModuloConexao.connector(); // Estabelecimento de uma conexão com o banco de dados usando o método connector() da classe ModuloConexao
        
        // Chama o método que adiciona o texto provisório a caixa de entrada da data
        Validacoes.placeholderTextField(txtNome, "Digite o seu nome");
        Validacoes.placeholderTextField(txtEmail, "Digite o seu email");
        Validacoes.placeholderTextField(txtSenha, "Digite a sua senha");
        Validacoes.placeholderTextField(txtDDD, "00");
        Validacoes.placeholderTextField(txtCelular, "987654321");
        Validacoes.placeholderTextField(txtNascimento, "dd/mm/aaaa");
        Validacoes.placeholderTextField(txtCPF, "12345678901");
        Validacoes.placeholderTextField(txtEndereco, "Digite o seu endereço");
        Validacoes.placeholderTextField(txtEstado, "Digite o seu Estado");
        Validacoes.placeholderTextField(txtCidade, "Digite a sua Cidade");
    }
    
    // Método para mostrar os dados cadastrados
    private void mensagemResultado() {

        String sql = "SELECT MAX(id) AS maiorID FROM tbl_informacoes"; // Declaração SQL para pegar o maior ID da tabela, ou seja, o ID mais recente que acabou de ser adicionado

        try {

            pst = conexao.prepareStatement(sql); // Prepara a declaração SQL

            rs = pst.executeQuery(); // Executa a SQL query
            
            // Se um resultado for encontrado, mostra uma caixa de texto com os dados informados
            if(rs.next()) {
                
                StringBuilder resultado = new StringBuilder(); // Inicializa um objeto StringBuilder para concatenar as strings

                // Anexa as strings no StringBuilder
                resultado.append("ID: " + rs.getInt("maiorID") + "\n");
                resultado.append("Nome: " + txtNome.getText() + "\n");
                resultado.append("Celular: " + txtDDD.getText() + txtCelular.getText() + "\n");
                resultado.append("Email: " + txtEmail.getText() + "\n");
                resultado.append("Senha: " + txtSenha.getText() + "\n");
                resultado.append("Data de Nascimento: " + txtNascimento.getText() + "\n");
                resultado.append("CPF: " + txtCPF.getText() + "\n");
                resultado.append("Endereço: " + txtEndereco.getText() + "\n");
                resultado.append("Cidade: " + txtCidade.getText() + "\n");
                resultado.append("Estado: " + txtEstado.getText());
                
                JOptionPane.showMessageDialog(null, resultado.toString()); // Mostra a mensagem com o resultado
            }
        } catch (Exception e) {
            
        }
    }

    // Método para cadastrar o usuário
    private void adicionar() {
        
        // Declarações SQL para inserir as informações no banco de dados
        String sql = "INSERT INTO tbl_informacoes (nome,ddd,celular,email,senha,dataNascimento,cpf,endereco,cidade,estado) VALUES (?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO tbl_usuarios (email,senha) VALUES (?,?)";
        
        // Validações de cada entrada
        if(txtID.getText().length() != 0) {
            
            JOptionPane.showMessageDialog(null, "Deixe o valor de ID vazio para adicionar");
        } else if(txtNome.getText().length() < 1 || txtNome.getText().length() > 100) {
            
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
        } else if(!Validacoes.validarData(txtNascimento.getText())) {

            JOptionPane.showMessageDialog(null, "Insira uma data válida");
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
                pst.setString(2, txtDDD.getText());
                pst.setString(3, txtCelular.getText());
                pst.setString(4, txtEmail.getText());
                pst.setString(5, txtSenha.getText());
                pst.setString(6, Validacoes.converterDataParaFormatoSQL(txtNascimento.getText()));
                pst.setString(7, txtCPF.getText());
                pst.setString(8, txtEndereco.getText());
                pst.setString(9, txtCidade.getText());
                pst.setString(10, txtEstado.getText());

                PreparedStatement pst2 = conexao.prepareStatement(sql2); // Prepara a segunda declaração SQL

                // Define os parâmetros para a segunda declaração
                pst2.setString(1, txtEmail.getText());
                pst2.setString(2, txtSenha.getText());

                // Executa ambas as declarações SQL
                int  adicionado = pst.executeUpdate();
                int adicionado2 = pst2.executeUpdate();

                // Se ambas as inserções forem bem-sucedidas, mostra uma mensagem de sucesso e limpa os text fields
                if(adicionado > 0 && adicionado2 > 0) {

                    JOptionPane.showMessageDialog(null, "Usuário cadastrado");
                    
                    // Chama o método para mostrar os dados cadastrados
                    mensagemResultado();
                    
                    txtID.setText(null);
                    txtNome.setText(null);
                    txtDDD.setText(null);
                    txtCelular.setText(null);
                    txtEmail.setText(null);
                    txtSenha.setText(null);
                    txtNascimento.setText(null);
                    txtCPF.setText(null);
                    txtEndereco.setText(null);
                    txtCidade.setText(null);
                    txtEstado.setText(null);
                }
            } catch(Exception e) {

                JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
            }
        }
    }
    
    // Método para visualizar os dados de um usuário cadastrado
    private void consultar() {
        
        String sql = "SELECT * FROM tbl_informacoes WHERE id=?"; // Declaração SQL para buscar dados do banco de dados

        // Verifica se o usuário entrou o ID e se o ID é um digito antes de tentar visualizar algo
        if(txtID.getText().length() == 0) {
            
            JOptionPane.showMessageDialog(null, "Digite o ID do usuário para visualiza-lo");
        } else if(!txtID.getText().matches("[0-9]+")) {
        
            JOptionPane.showMessageDialog(null, "ID precisa ser um número");
        } else {

            try {

                pst = conexao.prepareStatement(sql); // Prepara a declaração SQL

                pst.setString(1, txtID.getText()); // Define o parâmetro para a declaração SQL baseado no ID selecionado

                rs = pst.executeQuery(); // Executa a SQL query

                // Se um resultado for encontrado, preenche os text fields com os dados recuperados
                if(rs.next()) {

                    // Muda a coloração cinza do placeholder de volta para a cor padrão
                    txtNome.setForeground(Color.BLACK);
                    txtDDD.setForeground(Color.BLACK);
                    txtCelular.setForeground(Color.BLACK);
                    txtEmail.setForeground(Color.BLACK);
                    txtSenha.setForeground(Color.BLACK);
                    txtNascimento.setForeground(Color.BLACK);
                    txtCPF.setForeground(Color.BLACK);
                    txtEndereco.setForeground(Color.BLACK);
                    txtCidade.setForeground(Color.BLACK);
                    txtEstado.setForeground(Color.BLACK);

                    // Pega os dados do banco e os inseri nos text fields
                    txtNome.setText(rs.getString(2));
                    txtDDD.setText(rs.getString(3));
                    txtCelular.setText(rs.getString(4));
                    txtEmail.setText(rs.getString(5));
                    txtSenha.setText(rs.getString(6));
                    txtNascimento.setText(converterDataSQLParaFormatado(rs.getString(7)));
                    txtCPF.setText(rs.getString(8));
                    txtEndereco.setText(rs.getString(9));
                    txtCidade.setText(rs.getString(10));
                    txtEstado.setText(rs.getString(11));
                } else {

                    // Se nenhum resultado for encontrado, exibe uma mensagem e limpa os text fields
                    JOptionPane.showMessageDialog(null, "Usuário não cadastrado...");
                    txtNome.setText(null);
                    txtDDD.setText(null);
                    txtCelular.setText(null);
                    txtEmail.setText(null);
                    txtSenha.setText(null);
                    txtNascimento.setText(null);
                    txtCPF.setText(null);
                    txtEndereco.setText(null);
                    txtCidade.setText(null);
                    txtEstado.setText(null);
                }
            } catch(Exception e) {

                JOptionPane.showMessageDialog(null, e); // Se ocorrer uma exceção, mostra uma mensagem de erro
            }
        }
    }
    
    // Método para editar os dados de um usuário cadastrado 
    private void alterar() {
        
        // Declarações SQL para atualizar as informações do banco de dados
        String sql = "UPDATE tbl_informacoes SET nome=?, ddd=?, celular=?, email=?, senha=?, dataNascimento=?, cpf=?, endereco=?, cidade=?, estado=? WHERE id=?";
        String sql2 = "UPDATE tbl_usuarios SET email=?, senha=? WHERE id=?";
        
        // Validações de cada entrada
        if(txtID.getText().length() == 0) {
            
            JOptionPane.showMessageDialog(null, "Digite o ID do usuário para edita-lo");
        } else if(!txtID.getText().matches("[0-9]+")) {
        
            JOptionPane.showMessageDialog(null, "ID precisa ser um número");
        } else if(txtNome.getText().length() < 1 || txtNome.getText().length() > 100) {
            
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
        } else if(!validarData(txtNascimento.getText())) {

            JOptionPane.showMessageDialog(null, "Insira uma data válida");
        } else if(!validarCPF(txtCPF.getText())) {

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
                pst.setString(2, txtDDD.getText());
                pst.setString(3, txtCelular.getText());
                pst.setString(4, txtEmail.getText());
                pst.setString(5, txtSenha.getText());
                pst.setString(6, converterDataParaFormatoSQL(txtNascimento.getText()));
                pst.setString(7, txtCPF.getText());
                pst.setString(8, txtEndereco.getText());
                pst.setString(9, txtCidade.getText());
                pst.setString(10, txtEstado.getText());
                pst.setString(11, txtID.getText());

                PreparedStatement pst2 = conexao.prepareStatement(sql2); // Prepara a segunda declaração SQL

                // Define os parâmetros para a segunda declaração;
                pst2.setString(1, txtEmail.getText());
                pst2.setString(2, txtSenha.getText());
                pst2.setString(3, txtID.getText());

                // Executa ambas as declarações SQL
                int adicionado = pst.executeUpdate();
                int adicionado2 = pst2.executeUpdate();

                // Se a atualização dos dados do usuário em ambas as tabelas for bem-sucedida, mostra uma mensagem de sucesso e limpa os text fields
                if(adicionado > 0 && adicionado2 > 0) {

                    JOptionPane.showMessageDialog(null, "Usuário atualizado");

                    txtID.setText(null);
                    txtNome.setText(null);
                    txtDDD.setText(null);
                    txtCelular.setText(null);
                    txtEmail.setText(null);
                    txtSenha.setText(null);
                    txtNascimento.setText(null);
                    txtCPF.setText(null);
                    txtEndereco.setText(null);
                    txtCidade.setText(null);
                    txtEstado.setText(null);
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
            String sql = "DELETE FROM tbl_informacoes WHERE id=?";
            String sql2 = "DELETE FROM tbl_usuarios WHERE id=?";

            // Verifica se o usuário entrou o ID e se o ID é um digito antes de tentar excluir algo
            if(txtID.getText().length() == 0) {

                JOptionPane.showMessageDialog(null, "Digite o ID do usuário para apaga-lo");
            } else if(!txtID.getText().matches("[0-9]+")) {
        
            JOptionPane.showMessageDialog(null, "ID precisa ser um número");
            } else {
                
                try {

                    pst = conexao.prepareStatement(sql); // Prepara a primeira declaração SQL

                    pst.setString(1, txtID.getText()); // Define o primeiro parâmetro para a declaração SQL baseado no ID selecionado

                    PreparedStatement pst2 = conexao.prepareStatement(sql2); // Prepara a segunda declaração SQL

                    pst2.setString(1, txtID.getText()); // Define o segundo parâmetro para a declaração SQL baseado no ID selecionado

                    // Executa ambas as declarações SQL
                    int adicionado = pst.executeUpdate();
                    int adicionado2 = pst2.executeUpdate();

                    // Se o deletar do usuário em ambas as tabelas for bem-sucedida, mostra uma mensagem de sucesso e limpa os text fields
                    if(adicionado > 0 && adicionado2 > 0) {

                        JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso");

                        txtID.setText(null);
                        txtNome.setText(null);
                        txtDDD.setText(null);
                        txtCelular.setText(null);
                        txtEmail.setText(null);
                        txtSenha.setText(null);
                        txtNascimento.setText(null);
                        txtCPF.setText(null);
                        txtEndereco.setText(null);
                        txtCidade.setText(null);
                        txtEstado.setText(null);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDDD = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnVisualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNascimento = new javax.swing.JTextField();
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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Manusear Formulários ");

        jLabel1.setText("TELA DE CADASTRO");

        jLabel2.setText("ID");

        jLabel3.setText("Nome");

        jLabel4.setText("Email");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnVisualizar.setText("Visualizar");
        btnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel5.setText("DDD");

        jLabel6.setText("Celular");

        txtCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularActionPerformed(evt);
            }
        });

        jLabel7.setText("Data de Nascimento");

        jLabel8.setText("CPF");

        txtCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFActionPerformed(evt);
            }
        });

        jLabel9.setText("Endereço");

        jLabel10.setText("Cidade");

        jLabel11.setText("Estado");

        jLabel12.setText("Senha");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(168, 168, 168))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAdicionar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVisualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcluir)
                        .addGap(61, 61, 61))))
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtDDD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEstado)
                    .addComponent(txtCidade)
                    .addComponent(txtEmail)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtCelular, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                    .addComponent(txtNascimento)
                    .addComponent(txtCPF)
                    .addComponent(txtSenha, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEndereco))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnVisualizar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed

        consultar(); // Atribuí o método de consultar ao botão Visualizar
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        adicionar(); // Atribuí o método de adicionar ao botão Adicionar
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        alterar(); // Atribuí o método de alterar ao botão Editar
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        apagar(); // Atribuí o método de apagar ao botão Excluir
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed

    private void txtCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtDDD;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNascimento;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSenha;
    // End of variables declaration//GEN-END:variables
}
