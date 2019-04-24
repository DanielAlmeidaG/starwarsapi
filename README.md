# StarWarsApi - B2W

<!-- TOC -->

- [StarWarsApi - B2W](#starwarsapi---b2w)
    - [Arquitetura](#arquitetura)
    - [Endpoints](#api-endpoints)
        - [GET /planets/](#get-planets)
        - [GET /planets/{id}](#get-planetsid)
        - [GET /planets/search?name=PlanetName](#get-planetssearchnameplanetname)
        - [POST /planets/](#post-planets)
        - [DELETE /planets/{id}](#delete-planetsid)
    - [Observações](#observacoes)
    - [Executando a app](#executando-a-app)
    - [Executando os testes de integração](#executando-os-testes-de-integração)

## Arquitetura

<!-- TOC -->

- A aplicação StarWarsApi - B2W foi totalmente desenvolvida em Java 8 utilizando as facilidades do framework SpringBoot.

- Para suportar as necessidades de persistência de dados foi utilizado o banco NoSQL MongoDB hospedado em um serviço na nuvem chamado MongoDB Atlas.

- Visando otimizar a performance e utilização de recursos foi configurado um mecanismo de cache utilizando a implementação EhCahce.

- Para manter a estabilidade da API foi implementado um Rate Limiting na chamada dos serviços expostos.

- Como parte do pacote de infra estrutura foi implementado um mecanismo de log eficiente em cada requisição efetuada para a API utilizando o provider logback.

- A fim de diminuir a verbosidade e legibilidade do código foi utilizada a biblioteca lombok que provê a criação de getters, setters, constructors e outras facilidades de forma implícita.


## API endpoints

### GET /planets/
**Description:** retorna todos os planetas cadastrados.  
**Response Codes:** 200 - Successo.  
**Response Data:** O Json com a lista de planetas cadastrados. Se não houverem planetas cadastrados será retornado HTTP 200 e no body um json de um array vazio `[]`     

### GET /planets/{id}
**Description:** busca um planeta usando o seu id  
**Response Codes:** 200 - Successo. 404 - Planeta não encontrado.  
**Response Data:** Retorna o json de um planeta   
Ex: `{"id":"5ae9065ee101103fdca994c1","name":"Hoth","climate":"calor","terrain":"deserto","numberAppearancesFilms":1}`  

### GET /planets/search?name=PlanetName
**Description:** busca um planeta usando o seu nome  
**Query Params:** `name`, obrigatório, o nome do planeta a ser buscado.  
**Response Codes:** 200 - Successo. 404 - Planeta não encontrado.  
**Response Data:** Retorna o json de um planeta  
Ex: `{"id":"5ae9065ee101103fdca994c1","name":"Hoth","climate":"calor","terrain":"deserto","numberAppearancesFilms":1}`  

### POST /planets/
**Description:** Insere um novo planeta.  
**Request body:** Enviar um JSON com os campos "name", "climate" e "terrain". O campo "name" não pode ser nulo, não pode ser o mesmo que o de um planeta já cadastrado e precisa constar na base de dados da SWAPI. Os campos "climate" e "terrain" também não podem ser vazios e nem nulos.  
Ex: 
```
{
  "name": "Naboo",
  "climate": "temperate",
  "terrain": "rocks"
}
```  
**Response Codes:** 201 (Created) - Criado com sucesso. 409 (Conflict) - Já existe um planeta cadastrado com este nome. 404 (Not Found) - O nome do planeta não pode ser encontrado na SWAPI.  
**Response Data:** Retorna o objeto criado, com os campos id 'numberAppearancesFilms' preenchidos.  
Ex: `{"id":"5aebc67ce1011035d8c31c0b","name":"Naboo","climate":"temperate","terrain":"rocks","numberAppearancesFilms":4}`

### DELETE /planets/{id}
**Description:** deleta o planeta identificado pelo id.  
**Response Codes:** 204 (No Content) - Planeta deletado com Successo. 404 - Planeta não encontrado.  
**Response Data:**  não tem response data.  


## Observacoes

- A aplicação faz testes de integração que criam objetos na base de dados. Para os fins deste projetos foram criadas duas bases de dados no Cloud Service MongoDB Atlas, uma para desenvolvimento e uma para testes. Toda a configuração necessária para conexão com o serviço está dentro do arquivo "application.yml". Desta forma é eliminada a necessidade de uma instalação do mongoDB rodando localmente para execução deste projeto. 

- Os testes de integração fazem requests à Internet para consultar a SWAPI e acesar a base de dados MongoDB Atlas, portanto o acesso à internet deve estar liberado durante à execução dos testes.

## Executando a app  

Uma vez que o código da aplicação disponível em uma pasta local, entrar no diretório onde está o pom.xml e rodar os seguintes comandos:

**rodar a aplicação:** `mvnw spring-boot:run`

## Executando os testes de integração

**rodar os testes:** `mvnw test`  
