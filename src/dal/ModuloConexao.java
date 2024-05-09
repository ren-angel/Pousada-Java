package dal;

import java.sql.*;

public class ModuloConexao {

    // Método para estabelecer uma conexão com o banco de dados, retornando um objeto Connection
    public static Connection connector() {
        
        java.sql.Connection conexao = null; // Declaração de um objeto Connection chamado "conexao" e sua inicialização como nulo
        
        String driver = "com.mysql.cj.jdbc.Driver"; // Driver JDBC para MySQL.
        String url = "jdbc:mysql://localhost:3307/pousada"; // URL para conexão com o banco de dados MySQL chamado "agenda" em execução no localhost com a porta 3307
        String user = "root"; // Nome de usuário para autenticação do banco de dados
        String password = ""; // Senha para autenticação do banco de dados
        
        try {
            
            Class.forName(driver); // Carregando a classe do driver JDBC dinamicamente
            
            conexao = DriverManager.getConnection(url, user, password); // Estabelecendo uma conexão com o banco de dados usando a classe DriverManager
            
            return conexao;
        } catch (Exception e) {
            
            return null;
        }
    }
}