package validacao;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validacoes {
    
        // Método para formatar a data para o formato do Brasil
    public static boolean validarData(String dataStr) {
        
        // Verifica se a string de entrada é nula ou vazia
        if (dataStr == null || dataStr.isEmpty()) {

            return false; // Se nulo ou vazio, retorna false indicando dados inválidos
        }

        // Verifica se a string de entrada corresponde ao padrão de uma data no formato dd/MM/yyyy
        if (!dataStr.matches("\\d{2}/\\d{2}/\\d{4}")) {

            return false; // Se não corresponder ao padrão, retorna false indicando dados inválidos
        }

        try {
            
            DateTimeFormatter estiloFormatado = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Cria um objeto DateTimeFormatter para construir o formatador
            estiloFormatado.withResolverStyle(ResolverStyle.STRICT); // Impõe uma análise estrita da data
            
            DateTimeFormatter estiloSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formata a data de dd/mm/yyyy para yyyy/mm/dd para que seja aceitável ao LocalDate
            
            LocalDate diaAtual = LocalDate.now(); // Pega a data atual
            
            LocalDate data = LocalDate.parse(dataStr, estiloFormatado); // Analisa a string da data em um objeto LocalDate usando o formato "dd/MM/yyyy"
            
            String dataSQL = data.format(estiloSQL); // Formata a data analisada no formato de data SQL "yyyy-MM-dd"
            
            LocalDate diaEntrado = LocalDate.parse(dataSQL, estiloSQL); // Converte a data SQL do tipo String para LocalDate
            
            // Compara se a data inserida não é igual ou anterior a data atual
            if (!diaEntrado.isBefore(diaAtual)) {

                return false; // Se a data não for igual ou anterior ao dia de hoje, retorna false indicando dados inválidos
            }
            
            return true; // Se for bem-sucedido, retorna true indicando dados válidos
        } catch (DateTimeParseException e) {

            return false;  // Se a análise falhar devido a um DateTimeParseException, retorna false indicando dados inválidos
        }
    }

    // Método para reformatar a data ao enviá-lo para o banco de dados, num formato aceito pelo SQL
    public static String converterDataParaFormatoSQL(String dataFormatada) {
        
        // Cria objetos SimpleDateFormat para os formatos de data de entrada e saída
        DateTimeFormatter estiloFormatado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter estiloSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            
            LocalDate data = LocalDate.parse(dataFormatada, estiloFormatado); // Analisa a string de entrada em um objeto Date usando o formato "dd/MM/yyyy"
            return data.format(estiloSQL); // Formata a data analisada no formato de data SQL "yyyy-MM-dd"
        } catch (DateTimeParseException e) {
            
            return null; // Se a análise falhar devido a DateTimeParseException, retorna null
        }
    }

    // Método para formatar a data do SQL para o mesmo formato usado pelo programa
    public static String converterDataSQLParaFormatado(String dataSQL) {
        
        // Cria objetos SimpleDateFormat para os formatos de data de entrada e saída
        DateTimeFormatter estiloFormatado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter estiloSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            
            LocalDate data = LocalDate.parse(dataSQL, estiloSQL); // Analisa a string de data SQL em um objeto Date usando o formato "yyyyy-MM-dd".
            
            return data.format(estiloFormatado); // Formata a data analisada no formato "dd/MM/yyyy"
        } catch (DateTimeParseException e) {
            
            return null; // Se a análise falhar devido a DateTimeParseException, retorna null
        }
    }

    // Método para validar o CPF
    public static boolean validarCPF(String cpf) {
        
        // Verifica se o cpf contém apenas números
        if(!cpf.matches("[0-9]+")) {

            return false; // Se há qualquer coisa que não seja um dígito, retorna falso
        }
        
        // Verifica se há exatamente 11 dígitos
        if(cpf.length() != 11) {

            return false; // Se houver mais ou menos que 11 dígitos, retorna falso
        }
        
        int[] intArray = new int[cpf.length()]; // Cria uma array de números inteiros para armazenar cada caractere do CPF como um número inteiro
        
        // Converte cada caractere da string em seu número inteiro correspondente
        for (int i = 0; i < cpf.length(); i++) {
            
            intArray[i] = Character.getNumericValue(cpf.charAt(i));
        }
        
        // Calcula o primeiro dígito de verificação
	int multiplicacao1 = 10;
        int soma1 = 0;
        int index1 = 0;
		
        while(index1 < 9) {
            
            soma1 += (intArray[index1] * multiplicacao1);
            index1++;
            multiplicacao1--;
        }
     
        int divisao1 = (soma1 * 10) % 11;
        
        if (divisao1 == 10 || divisao1 == 11) {
            
            divisao1 = 0;
        } else {
            
            divisao1 = (soma1 * 10) % 11;
        }
        
        // Calcula o segundo dígito de verificação
        int multiplicacao2 = 11;
        int soma2 = 0;
        int index2 = 0;

        while(index2 < 10) {
            
           soma2 += (intArray[index2] * multiplicacao2);
           index2++;
           multiplicacao2--;
        }			
         
        int divisao2 = (soma2 * 10) % 11;
         
        if(divisao2 == 10 || divisao2 == 11) {
            
            divisao2 = 0;
        } else {
            
            divisao2 = (soma2 * 10) % 11;
        }
        
        // Verifica se os dígitos de verificação calculados correspondem aos fornecidos
        if(intArray[9] == divisao1 && intArray[10] == divisao2) {
        	
            return true; // Se corresponderem, retorna true indicando um CPF válido
        } else {
            
            return false; // Se não corresponderem, retorna false indicando um CPF inválido
        }
    }

    // Método para adicionar um texto provisório com instruções a cada text field
    public static void placeholderTextField(javax.swing.JTextField input, String texto) {
        
        // Define o texto e a cor do placeholder inicial
        input.setText(texto);
        input.setForeground(Color.GRAY);

        // Adiciona FocusListener para lidar com o comportamento do placeholder
        input.addFocusListener(new FocusListener() {
            
            @Override
            public void focusGained(FocusEvent e) {
                
                // Quando o text field ganhar foco, limpa o texto do placeholder e altere a cor do texto para padrão (preto)
                if (input.getText().equals(texto)) {
                    
                    input.setText("");
                    input.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                // Quando o text field perder o foco, se ele estiver vazio, define o texto e a cor do placeholder
                if (input.getText().isEmpty()) {
                    
                    input.setForeground(Color.GRAY);
                    input.setText(texto);
                }
            }
        });
    }
}
