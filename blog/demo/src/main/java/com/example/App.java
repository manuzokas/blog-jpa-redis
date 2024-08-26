package com.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* comando para testar o redis LEITOR:
 * curl -X POST http://localhost:8080/api/leitores -H "Content-Type: application/json" -d "{\"nome\": \"Leitor Teste\", \"email\": \"leitor@teste.com\", \"senha\": \"senha123\", \"endereco\": {\"bairro\": \"Centro\", \"rua\": \"Rua A\", \"numero\": \"123\", \"complemento\": \"Apt 101\", \"cep\": \"12345-678\"}}"
   HGETALL Leitor <id>
   retorna o json serializado
 */

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}