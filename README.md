# VitalMed API 🏥

API para controle de estoque e distribuição de insumos hospitalares.

## 🚀 Tecnologias
- Java 17 / Spring Boot
- Spring Security + JWT
- MySQL 8.0
- Maven

## 🛠️ Funcionalidades
- Autenticação e controle de cargos (Admin, Almoxarife, etc).
- Gestão de Insumos e Categorias.
- Registro automático de movimentações de estoque.
- Validações de regras de negócio (estoque mínimo/negativo).

## 📦 Como rodar
1. Configure o banco MySQL com o script em `/docs/script.sql`.
2. Ajuste o `application.properties` com suas credenciais.
3. Execute `./mvnw spring-boot:run`.
