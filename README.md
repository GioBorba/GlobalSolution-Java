# Plataforma de Eficiência Energética para Prédios Inteligentes 🌱

## Descrição do Sistema

Este projeto é uma **plataforma de eficiência energética** desenvolvida para monitorar e controlar o consumo de energia em prédios inteligentes. O objetivo principal é reduzir desperdícios, priorizar fontes renováveis e promover a sustentabilidade energética.
Na premissa do nosso projeto os dados de consumo de cada morador/usuário viria de forma automática para o sistema, porém como não temos acesso a nenhum prédio essa versão base consta com a colocação dos dados de consumo manualmente.

## Funcionalidades Principais

- **Monitoramento de Consumo:** Registra o consumo de energia para cada usuário.
- **Alertas Personalizados:** Gera mensagens de alerta para usuários que excedem o consumo médio.
- **Lembretes Automatizados:** Permite criar alarmes para práticas sustentáveis, como desligar aparelhos.
- **HATEOAS:** Navegação simples entre os recursos da API.
- **SWAGGER:** Anotação Swagger para facilitar o entendimento.

---

## Estrutura do Projeto

Aqui está uma breve descrição das classes principais:

### 1. **ControleEnergetico**
**Objetivo:** Gerenciar o consumo energético dos usuários.
- **Atributos:** `id`, `usuario`, `consumo`, `dataRegistro`.

### 2. **Alerta**
**Objetivo:** Gerar notificações para usuários com base no consumo.
- **Atributos:** `id`, `usuario`, `mensagem`.

### 3. **Lembrete**
**Objetivo:** Criar alarmes para ajudar usuários em práticas sustentáveis.
- **Atributos:** `id`, `usuario`, `acao`, `horario`.

### 4. **Usuario**
**Objetivo:** Representar os moradores do prédio.
- **Atributos:** `id`, `nome`, `email`, `senha`, `unidade`.


## Links: 
- ### Link funcionamento do software: https://www.youtube.com/watch?v=Ed4ilqtHSpI
- ### Link Vídeo Pitch: https://www.youtube.com/watch?v=kfenPoqhcSY


## Aplicação na porta 8080
## Caso for rodar pela aplicação java clonada: http://localhost:8080
## Link do Deploy no Render: https://java-gs.onrender.com
### OBS: No render, a aplicação demora para ficar em Live pois ela fica inativa quando não tem ninguém utilizando, e até inicializa-la leva um tempo.


## Abaixo temos os endpoints juntamentes com os testes feitos no Postman

---

## Endpoints da API

### 1. Criar Usuário
Cria um novo usuário com os dados fornecidos.

- **Método:** `POST`
- **URL:** `/api/usuarios`
- **Corpo da Requisição:**
```json
{
  "nome": "Sales",
  "email": "vitorsales@example.com",
  "senha" : "sales23343",
  "unidade": "Apto 102"
}

```
![criarUsuario](https://github.com/user-attachments/assets/415f3bb1-16c4-4a80-93a9-8f5f36be1aa3)


## 2. Buscar Usuário por ID

**Endpoint:** `GET /api/usuarios/{id}`  
**Descrição:** Obtém um usuário com base no ID fornecido.  
**Parâmetro:**  
- `id`: ID do usuário (ex: `1`)

**Exemplo de Requisição:**

- URL: `GET /api/usuarios/1`

Corpo da Resposta:
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao.silva@example.com",
  "_links": {
    "self": { "href": "/api/usuarios/1" },
    "all-users": { "href": "/api/usuarios" },
    "delete-user": { "href": "/api/usuarios/1" }
  }
}

```
![usuarioPorId](https://github.com/user-attachments/assets/22f0bf2c-83b7-4b4b-b532-c439fc2c5e0c)




## 3. Listar Todos os Usuários

**Endpoint:** `GET /api/usuarios`  
**Descrição:** Obtém uma lista paginada de usuários.  
**Parâmetros:**  
- `page`: Número da página (ex: `0`)
- `size`: Número de itens por página (ex: `10`)

**Exemplo de Requisição:**

- URL: `GET /api/usuarios?page=0&size=10`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "nome": "João da Silva",
      "email": "joao.silva@example.com",
      "_links": {
        "self": { "href": "/api/usuarios/1" },
        "all-users": { "href": "/api/usuarios" },
        "delete-user": { "href": "/api/usuarios/1" }
      }
    }
  ],
  "pageable": {
    "pageSize": 10,
    "pageNumber": 0
  },
  "totalElements": 1,
  "totalPages": 1
}

```
![allUsuarios](https://github.com/user-attachments/assets/d97c177b-f682-40a6-968b-ad9a00c37c99)




## 4. Atualizar Usuário

**Endpoint:** `PUT /api/usuarios/{id}`  
**Descrição:** Atualiza os dados de um usuário com base no ID fornecido.  
**Parâmetros:**  
- `id`: ID do usuário (ex: `1`)

**Exemplo de Requisição:**

- URL: `PUT /api/usuarios/1`

**Corpo da Requisição:**
```json
{
    "nome": "João Seixa",
    "email": "joao.silva@example.com",
    "senha": "novaSenha123",
    "unidade" : "Apto 222"
}
```

![atualizarUsuario](https://github.com/user-attachments/assets/921490cd-26a8-41f3-a22b-41a523164f7a)



## 5. Excluir Usuário

**Endpoint:** `DELETE /api/usuarios/{id}`  
**Descrição:** Exclui um usuário com base no ID fornecido.  
**Parâmetro:**  
- `id`: ID do usuário (ex: `1`)

**Exemplo de Requisição:**

- URL: `DELETE /api/usuarios/1`


# Guia de Testes dos Endpoints de Lembretes

A seguir, você encontra exemplos de como testar os endpoints da API de Gestão de Lembretes. Todos os testes devem ser realizados utilizando o método HTTP apropriado, junto com os parâmetros e corpo de requisição conforme especificado.

## 1. Criar um Novo Lembrete

**Endpoint:** `POST /api/lembretes`  
**Descrição:** Cria um novo lembrete com os dados fornecidos.  
**Corpo da Requisição:**
```json
{
    "acao": "Ligar Ar",
    "horario": "2024-10-19T23:30:00",
    "usuario": {
        "id": 1
    }
}
```
![criarLembrete](https://github.com/user-attachments/assets/9364dbc3-dd7d-4471-849c-b2f750222283)




## 2. Atualizar um Lembrete

**Endpoint:** `PUT /api/lembretes/{id}`  
**Descrição:** Atualiza um lembrete com base no ID fornecido.  
**Parâmetros:**  
- `id`: ID do lembrete a ser atualizado (ex: `1`)

**Corpo da Requisição:**
```json
{
  "acao": "Desligar Ar",
  "horario": "2024-10-19T06:00:00",
  "usuario": {
    "id": 1
  }
}
```

![atualizarLembrete](https://github.com/user-attachments/assets/c6ae5786-5361-4211-b5a6-5522b3df5a6f)




## 3. Listar Todos os Lembretes

**Endpoint:** `GET /api/lembretes`  
**Descrição:** Obtém uma lista de todos os lembretes.  

**Exemplo de Requisição:**

- URL: `GET /api/lembretes`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "acao": "Ligar Ar",
      "horario": "2024-10-19T23:30:00",
      "_links": {
        "self": { "href": "/api/lembretes/1" },
        "all-lembretes": { "href": "/api/lembretes" },
        "delete-lembrete": { "href": "/api/lembretes/1" }
      }
    },
    {
      "id": 2,
      "acao": "Desligar Luz",
      "horario": "2024-10-20T07:00:00",
      "_links": {
        "self": { "href": "/api/lembretes/2" },
        "all-lembretes": { "href": "/api/lembretes" },
        "delete-lembrete": { "href": "/api/lembretes/2" }
      }
    }
  ]
}
```

![allLembretes](https://github.com/user-attachments/assets/9bb9edea-37d9-466a-bf23-a1a7dd387464)




## 4. Buscar Lembrete por ID

**Endpoint:** `GET /api/lembretes/{id}`  
**Descrição:** Obtém um lembrete com base no ID fornecido.  
**Parâmetros:**  
- `id`: ID do lembrete a ser buscado (ex: `5`)

**Exemplo de Requisição:**

- URL: `GET /api/lembretes/1`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "id": 1,
  "acao": "Ligar Ar",
  "horario": "2024-10-19T23:30:00",
  "_links": {
    "self": { "href": "/api/lembretes/1" },
    "all-lembretes": { "href": "/api/lembretes" },
    "delete-lembrete": { "href": "/api/lembretes/1" }
  }
}
```
![lembretePorId](https://github.com/user-attachments/assets/8210d32a-9959-4236-b8f3-5df357826520)





## 5. Excluir Lembrete por ID

**Endpoint:** `DELETE /api/lembretes/{id}`  
**Descrição:** Exclui um lembrete com base no ID fornecido.  
**Parâmetros:**  
- `id`: ID do lembrete a ser excluído (ex: `1`)

**Exemplo de Requisição:**

- URL: `DELETE /api/lembretes/1`

![deleteLembrete](https://github.com/user-attachments/assets/03a5b277-d9b8-4e06-b1da-6329603ab981)




# Guia de Testes dos Endpoints de Controle Energético

A seguir, você encontra exemplos de como testar os endpoints da API de Gestão de Controle Energético. Todos os testes devem ser realizados utilizando o método HTTP apropriado, junto com os parâmetros e corpo de requisição conforme especificado.


## 1. Criar Novo Registro de Consumo Energético

**Endpoint:** `POST /controle-energetico/{usuarioId}`  
**Descrição:** Registra o consumo energético de um usuário.  
**Parâmetros:**  
- `usuarioId`: ID do usuário que será associado ao consumo energético (ex: `1`)

**Exemplo de Requisição:**

- URL: `POST /controle-energetico/1`
- Corpo da Requisição:
```json
{
  "consumo": 150.5,
  "dataRegistro": "2024-10-19T23:30:00"
}
```
![criarControle](https://github.com/user-attachments/assets/4dec1557-edfe-4588-bb39-290400ce720f)





## 2. Listar Todos os Registros de Consumo Energético

**Endpoint:** `GET /controle-energetico`  
**Descrição:** Obtém uma lista paginada de todos os registros de consumo energético.  
**Parâmetros:**  
- `page`: Número da página (ex: `0`)
- `size`: Número de itens por página (ex: `10`)

**Exemplo de Requisição:**

- URL: `GET /controle-energetico?page=0&size=10`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "usuarioId": 1,
      "consumo": 150.5,
      "dataRegistro": "2024-10-19T23:30:00",
      "_links": {
        "self": { "href": "/controle-energetico/1" },
        "all-consumos": { "href": "/controle-energetico" }
      }
    }
  ],
  "pageable": {
    "pageSize": 10,
    "pageNumber": 0
  },
  "totalElements": 1,
  "totalPages": 1
}
```

![allControle](https://github.com/user-attachments/assets/db6c4fc7-c167-4f0b-b29c-b5af8b2e15e0)




## 3. Listar Registros de Consumo de um Usuário

**Endpoint:** `GET /controle-energetico/usuario/{usuarioId}`  
**Descrição:** Obtém uma lista paginada de registros de consumo energético de um usuário específico.  
**Parâmetros:**  
- `usuarioId`: ID do usuário (ex: `1`)
- `page`: Número da página (ex: `0`)
- `size`: Número de itens por página (ex: `10`)

**Exemplo de Requisição:**

- URL: `GET /controle-energetico/usuario/1?page=0&size=10`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "usuarioId": 1,
      "consumo": 150.5,
      "dataRegistro": "2024-10-19T23:30:00",
      "_links": {
        "self": { "href": "/controle-energetico/1" },
        "all-consumos": { "href": "/controle-energetico" }
      }
    }
  ],
  "pageable": {
    "pageSize": 10,
    "pageNumber": 0
  },
  "totalElements": 1,
  "totalPages": 1
}
```

![controlePorId](https://github.com/user-attachments/assets/e9b99009-56a3-421d-a776-7dc255c25ff1)




## 4. Atualizar Registro de Consumo Energético

**Endpoint:** `PUT /controle-energetico/{id}`  
**Descrição:** Atualiza um registro de consumo energético existente.  
**Parâmetros:**  
- `id`: ID do registro de consumo a ser atualizado (ex: `1`)

**Exemplo de Requisição:**

- URL: `PUT /controle-energetico/1`
- Corpo da Requisição:
```json
{
  "consumo": 180.0,
  "dataRegistro": "2024-10-20T12:00:00"
}
```
![atualizarControle](https://github.com/user-attachments/assets/75df8980-9272-4d40-8f94-cb927d2cddff)




## 5. Excluir Registro de Consumo Energético

**Endpoint:** `DELETE /controle-energetico/{id}`  
**Descrição:** Exclui um registro de consumo energético existente.  
**Parâmetros:**  
- `id`: ID do registro de consumo a ser excluído (ex: `1`)

**Exemplo de Requisição:**

- URL: `DELETE /controle-energetico/1`

![excluirControle](https://github.com/user-attachments/assets/a1cb0946-7fe8-4936-92ad-09a7978249a6)





# Guia de Testes dos Endpoints de Alerta

A seguir, você encontra exemplos de como testar os endpoints da API de Gestão de Alerta. Todos os testes devem ser realizados utilizando o método HTTP apropriado, junto com os parâmetros e corpo de requisição conforme especificado.

## 1. Criar Alerta para o Usuário

**Endpoint:** `POST /api/alertas/gerar`  
**Descrição:** Cria um alerta para o usuário com base no seu consumo de energia.  
**Parâmetros:**  
- `usuarioId`: ID do usuário que receberá o alerta (ex: `1`)
- `consumoAtual`: O valor do consumo energético atual do usuário (ex: `250.5`)

**Exemplo de Requisição:**

- URL: `POST /api/alertas/gerar?usuarioId=1&consumoAtual=250.5`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "id": 1,
  "usuarioId": 1,
  "mensagem": "Alerta: Consumo energético excessivo!",
  "_links": {
    "self": { "href": "/api/alertas/1" },
    "all-alertas": { "href": "/api/alertas" },
    "create-alerta": { "href": "/api/alertas/gerar" }
  }
}
```
![criarAlerta](https://github.com/user-attachments/assets/133a06ba-70e1-4d46-9973-dec5ecb37a91)




## 2. Listar Todos os Alertas

**Endpoint:** `GET /api/alertas`  
**Descrição:** Obtém uma lista de todos os alertas gerados.  

**Exemplo de Requisição:**

- URL: `GET /api/alertas`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "usuarioId": 1,
      "mensagem": "Alerta: Consumo energético excessivo!",
      "_links": {
        "self": { "href": "/api/alertas/1" },
        "all-alertas": { "href": "/api/alertas" },
        "create-alerta": { "href": "/api/alertas/gerar" }
      }
    }
  ]
}
```

![allAlertas](https://github.com/user-attachments/assets/a75a1029-2050-4d1d-a597-0af45e3aa68f)




## 3. Obter Alerta por ID

**Endpoint:** `GET /api/alertas/{id}`  
**Descrição:** Obtém os detalhes de um alerta específico pelo seu ID.  
**Parâmetros:**  
- `id`: ID do alerta a ser recuperado (ex: `1`)

**Exemplo de Requisição:**

- URL: `GET /api/alertas/1`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "id": 1,
  "usuarioId": 1,
  "mensagem": "Alerta: Consumo energético excessivo!",
  "_links": {
    "self": { "href": "/api/alertas/1" },
    "all-alertas": { "href": "/api/alertas" },
    "create-alerta": { "href": "/api/alertas/gerar" }
  }
}
```
![alertaPorId](https://github.com/user-attachments/assets/1559089a-940f-4519-8341-24700bba8a87)





## 4. Listar Alertas de um Usuário

**Endpoint:** `GET /api/alertas/usuario/{usuarioId}`  
**Descrição:** Obtém todos os alertas de um usuário específico. Retorna uma mensagem caso o usuário não tenha alertas.  
**Parâmetros:**  
- `usuarioId`: ID do usuário (ex: `1`)

**Exemplo de Requisição:**

- URL: `GET /api/alertas/usuario/1`

**Resposta Esperada:**  
Código HTTP: `200 OK`  
Corpo da Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "usuarioId": 1,
      "mensagem": "Alerta: Consumo energético excessivo!",
      "_links": {
        "self": { "href": "/api/alertas/1" },
        "all-alertas": { "href": "/api/alertas" },
        "create-alerta": { "href": "/api/alertas/gerar" }
      }
    }
  ]
}
```

![alertasUsuario](https://github.com/user-attachments/assets/d8119784-01f6-49b8-9c54-99723dd0ad8d)






