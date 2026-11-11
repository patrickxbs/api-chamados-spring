# API de Chamados de Suporte

Trabalho da disciplina **Linguagens para Aplicação Comercial** — Professor Joselito Júnior.

API REST para registrar e consultar chamados técnicos. Projeto com Java 17, Spring Boot e JSON. Os dados ficam em uma coleção em memória e são reiniciados quando a aplicação é encerrada.

## Executar

Com JDK 17 e Maven instalados:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`. A aplicação inicia com 10 chamados fictícios.

## Endpoints

| Método | Caminho | Uso |
|---|---|---|
| GET | `/chamados` | Lista todos |
| GET | `/chamados/{id}` | Consulta por identificador; retorna 404 se não existir |
| GET | `/chamados/setor?nome=financeiro` | Filtra pelo setor, sem diferenciar maiúsculas/minúsculas |
| GET | `/chamados/prioridade?nivel=alta` | Filtra por BAIXA, MEDIA ou ALTA |
| GET | `/chamados/busca?texto=impressora` | Busca trecho no título, sem diferenciar maiúsculas/minúsculas |
| GET | `/chamados/criticos` | Chamados de prioridade ALTA cujo status não é RESOLVIDO |
| GET | `/chamados/resumo` | Quantidades por status e total |
| POST | `/chamados` | Cadastra e retorna HTTP 201 com o chamado criado |

### Exemplo de cadastro

```json
{
  "method": "POST",
  "url": "http://localhost:8080/chamados",
  "headers": {
    "Accept": "application/json",
    "Content-Type": "application/json"
  },
  "body": {
    "titulo": "Impressora do laboratório sem papel",
    "solicitante": "Marina Santos",
    "setor": "Laboratório",
    "prioridade": "MEDIA",
    "status": "ABERTO"
  }
}
```

O identificador é gerado pelo servidor. Os campos de prioridade aceitam `BAIXA`, `MEDIA`, `ALTA`; status aceita `ABERTO`, `EM_ANDAMENTO`, `RESOLVIDO`.

## Evidências dos testes

Requisições executadas localmente em 10/11/2026, com a aplicação iniciada em `http://localhost:8080`. As respostas abaixo foram obtidas diretamente da API. A sequência inclui 11 requisições; o POST cria o chamado 11, que é verificado logo depois.

### 1. Listar todos os chamados

Requisição: `GET /chamados`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
[{"id":1,"titulo":"Computador não liga","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":2,"titulo":"Impressora sem conexão","solicitante":"Bruno Lima","setor":"Recursos Humanos","prioridade":"MEDIA","status":"EM_ANDAMENTO"},{"id":3,"titulo":"Acesso ao sistema bloqueado","solicitante":"Carla Reis","setor":"Financeiro","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":4,"titulo":"Instalar editor de texto","solicitante":"Diego Alves","setor":"Marketing","prioridade":"BAIXA","status":"RESOLVIDO"},{"id":5,"titulo":"Rede instável no escritório","solicitante":"Elisa Costa","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"},{"id":6,"titulo":"Configurar e-mail corporativo","solicitante":"Felipe Rocha","setor":"Operações","prioridade":"MEDIA","status":"RESOLVIDO"},{"id":7,"titulo":"Teclado com teclas defeituosas","solicitante":"Giovana Melo","setor":"Marketing","prioridade":"BAIXA","status":"ABERTO"},{"id":8,"titulo":"VPN não conecta","solicitante":"Heitor Nunes","setor":"Tecnologia","prioridade":"ALTA","status":"RESOLVIDO"},{"id":9,"titulo":"Monitor piscando","solicitante":"Isabela Dias","setor":"Financeiro","prioridade":"MEDIA","status":"EM_ANDAMENTO"},{"id":10,"titulo":"Solicitar acesso à pasta compartilhada","solicitante":"João Pedro","setor":"Recursos Humanos","prioridade":"BAIXA","status":"ABERTO"}]
```

Explicação: a resposta contém os 10 chamados fictícios carregados ao iniciar a aplicação.

### 2. Consultar chamado existente

Requisição: `GET /chamados/1`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/1",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
{"id":1,"titulo":"Computador não liga","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"}
```

Explicação: o ID 1 existe e seus dados são retornados como um objeto JSON.

### 3. Consultar chamado inexistente

Requisição: `GET /chamados/999`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/999",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 404:

```json
{"erro":"Chamado não encontrado"}
```

Explicação: como não há chamado com esse ID, a API responde com `NOT_FOUND`.

### 4. Filtrar setor sem diferenciar maiúsculas e minúsculas

Requisição: `GET /chamados/setor?nome=fInAnCeIrO`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/setor?nome=fInAnCeIrO",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
[{"id":1,"titulo":"Computador não liga","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":3,"titulo":"Acesso ao sistema bloqueado","solicitante":"Carla Reis","setor":"Financeiro","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":9,"titulo":"Monitor piscando","solicitante":"Isabela Dias","setor":"Financeiro","prioridade":"MEDIA","status":"EM_ANDAMENTO"}]
```

Explicação: o filtro encontra os três chamados do Financeiro apesar da combinação de letras maiúsculas e minúsculas na busca.

### 5. Filtrar por prioridade

Requisição: `GET /chamados/prioridade?nivel=alta`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/prioridade?nivel=alta",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
[{"id":1,"titulo":"Computador não liga","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":3,"titulo":"Acesso ao sistema bloqueado","solicitante":"Carla Reis","setor":"Financeiro","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":5,"titulo":"Rede instável no escritório","solicitante":"Elisa Costa","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"},{"id":8,"titulo":"VPN não conecta","solicitante":"Heitor Nunes","setor":"Tecnologia","prioridade":"ALTA","status":"RESOLVIDO"}]
```

Explicação: são retornados os quatro chamados com prioridade `ALTA`, inclusive o resolvido, pois este filtro considera somente a prioridade.

### 6. Buscar no título sem diferenciar maiúsculas e minúsculas

Requisição: `GET /chamados/busca?texto=IMPRESSORA`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/busca?texto=IMPRESSORA",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
[{"id":2,"titulo":"Impressora sem conexão","solicitante":"Bruno Lima","setor":"Recursos Humanos","prioridade":"MEDIA","status":"EM_ANDAMENTO"}]
```

Explicação: o título foi encontrado mesmo com o texto de busca em letras maiúsculas.

### 7. Busca sem correspondência

Requisição: `GET /chamados/busca?texto=termo-inexistente`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/busca?texto=termo-inexistente",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
[]
```

Explicação: a consulta foi válida, mas nenhum título contém o termo; por isso a API retorna uma lista vazia.

### 8. Listar chamados críticos

Requisição: `GET /chamados/criticos`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/criticos",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
[{"id":1,"titulo":"Computador não liga","solicitante":"Ana Souza","setor":"Financeiro","prioridade":"ALTA","status":"ABERTO"},{"id":3,"titulo":"Acesso ao sistema bloqueado","solicitante":"Carla Reis","setor":"Financeiro","prioridade":"ALTA","status":"EM_ANDAMENTO"},{"id":5,"titulo":"Rede instável no escritório","solicitante":"Elisa Costa","setor":"Comercial","prioridade":"ALTA","status":"ABERTO"}]
```

Explicação: o resultado combina prioridade `ALTA` com status diferente de `RESOLVIDO`; o chamado de ID 8, embora prioritário, fica de fora por estar resolvido.

### 9. Consultar resumo por status

Requisição: `GET /chamados/resumo`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/resumo",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
{"abertos":4,"emAndamento":3,"resolvidos":3,"total":10}
```

Explicação: os totais correspondem aos 10 chamados iniciais, distribuídos entre os três status.

### 10. Cadastrar chamado

Requisição: `POST /chamados`

```json
{
  "method": "POST",
  "url": "http://localhost:8080/chamados",
  "headers": {
    "Accept": "application/json",
    "Content-Type": "application/json"
  },
  "body": {
    "titulo": "Impressora do laboratorio sem papel",
    "solicitante": "Marina Santos",
    "setor": "Laboratorio",
    "prioridade": "MEDIA",
    "status": "ABERTO"
  }
}
```

Resposta HTTP 201:

```json
{"id":11,"titulo":"Impressora do laboratorio sem papel","solicitante":"Marina Santos","setor":"Laboratorio","prioridade":"MEDIA","status":"ABERTO"}
```

Explicação: a API recebeu o JSON, atribuiu o próximo ID disponível (11), guardou o chamado em memória e respondeu com `CREATED`.

### 11. Verificar o chamado cadastrado

Requisição: `GET /chamados/11`

```json
{
  "method": "GET",
  "url": "http://localhost:8080/chamados/11",
  "headers": {
    "Accept": "application/json"
  }
}
```

Resposta HTTP 200:

```json
{"id":11,"titulo":"Impressora do laboratorio sem papel","solicitante":"Marina Santos","setor":"Laboratorio","prioridade":"MEDIA","status":"ABERTO"}
```

Explicação: a consulta confirma que o chamado criado pelo POST está disponível durante a execução atual da aplicação.

## Análise técnica

### Como o Spring Boot recebe e processa requisições HTTP

O servidor web embutido do Spring Boot (Tomcat, por padrão) escuta a porta configurada. O Spring MVC identifica o método do controller associado ao método HTTP e ao caminho, converte parâmetros e corpo da requisição, chama o método e transforma o retorno em uma resposta HTTP.

### Função do Controller

O `ChamadoController` define os caminhos REST, recebe parâmetros e JSON, delega as operações ao `ChamadoService` e devolve os resultados com os status HTTP apropriados. As regras de consulta e o acesso aos dados ficam no service.

### Conversão entre Java e JSON

O Spring Boot usa o Jackson, integrado ao starter Web, para converter o JSON recebido em `NovoChamadoRequest` e serializar os DTOs de resposta (`ChamadoResponse` e `ResumoChamadosResponse`) em JSON. O modelo `Chamado` continua como `record` no pacote `model`, enquanto os DTOs ficam no pacote `dto`. Os enums são representados pelos nomes das constantes.

### Persistência em memória

Os chamados ficam em uma coleção Java (`ArrayList`) dentro do processo da aplicação. A memória é volátil e não existe gravação em arquivo ou banco de dados; ao reiniciar, a coleção é criada novamente com os 10 dados fictícios, e os cadastros feitos durante a execução se perdem.
