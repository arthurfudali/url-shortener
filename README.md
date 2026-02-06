# Encurtador de URLs em Java (Spring Boot)
### Este é um projeto de uma API REST para encurtamento de URLs, desenvolvida com Java e Spring Boot. O objetivo é transformar links longos em identificadores curtos e gerir o redirecionamento com lógica de validade.
O projeto foi desenvolvido com o intuito de ser uma resolução de um desafio do [Backend-br](https://github.com/backend-br/desafios/blob/master/url-shortener/PROBLEM.md).

## Funcionalidades
- Encurtamento Customizado: Gera códigos alfanuméricos aleatórios entre 5 e 10 caracteres.
- Validação de Entrada: Garante que a URL não seja vazia e contenha os protocolos http:// ou https://.
- Gestão de Validade: Cada link tem um prazo de expiração definido (ex: 30 dias).
- Redirecionamento Inteligente: Redireciona links válidos (Status 302) e retorna erro (Status 404) para links inexistentes ou expirados.
- Arquitetura Limpa: Uso de DTOs, Mappers e Global Exception Handler.

## Tecnologias Utilizadas

- Linguagem: Java 25
- Framework: Spring Boot 4+
- Banco de Dados: H2 Database (Em memória).
- Dependências: Spring Data JPA.
- Spring Web.
- Apache Commons Lang3 (Geração de strings aleatórias).

## API Endpoints
### 1. Encurtar URL
Cria um novo link encurtado.

- URL: `POST /api/shorten-url`

- Corpo da Requisição (JSON):


``` JSON
{
  "url": "https://www.google.com/search?q=java+spring+boot"
}
```
- Resposta (JSON - 200 OK):

``` JSON
{
  "url": "http://localhost:8080/aB3dE"
}
```

### 2. Redirecionar
Acesse a URL original através do código curto.

- URL: `GET /{shortUrl}`

- Respostas:

`302 Found`: Redirecionamento bem-sucedido.

`404 Not Found`: Link não existe ou já expirou.

`400 Bad Request`: Formato de link inválido.

## Decisões de Design

- Records como DTOs: Uso de Java Records para garantir a imutabilidade dos dados trafegados na rede.
- Optional para Fluxo de Controle: Em vez de usar exceções para casos de "Não Encontrado", o sistema utiliza Optional no serviço, tratando o `not found` como um caso esperado no Controller.
- Exceções Customizadas: Criação da `InvalidUrlException` para tratar erros de entrada do usuário (`400 Bad Request`) de forma centralizada.
