# Tech Challenge

Projeto do curso de Pós-graduação em Arquitetura de Software da FIAP.

Autores membros do Grupo 15:

- Bleno Humberto Claus
- Giovanni Di Luca Evangelista
- Lucas Gabriel dos Santos
- Mateus Sales Albino
- Wellyson de Freitas Santos

> **Para o avaliador da Fase 1 ✨**
> 
> Queira encontrar mais informações nas seções deste arquivo, incluindo informacões a respeito de [OpenAPI (Swagger)](#openapi-swagger) e [Postman](#postman), e também na [documentação](#documentação), contendo os entregáveis de DDD e as limitações conhecidas.

## Requisitos

Este projeto compreende uma solução possível para a especificação inicial referente a um sistema de autoatendimento de restaurante, com quiosques ou terminais de autoatendimento.

[Confira os requisitos](docs/requirements.md)

De forma geral, clientes e administradores usarão o sistema, que depende de um serviço de pagamento externo. Entre os requisitos não funcionais está a escalabilidade.

![Diagrama de Contexto C4](docs/diagrams/c4-context.png)

## Domain-Driven Development (DDD)

DDD foi a abordagem utilizada para o desenvolvimento, com as seguintes saídas documentadas:

- Glossário ubíquo
- Event storming
- Storytelling
- Mapa de Contexto

[Consulte a documentação](#documentação) para saber mais.

## Arquitetura

[Arquitetura Hexagonal](https://alistair.cockburn.us/hexagonal-architecture) (Ports and Adapters) é estritamente adotada no projeto.

## Tecnologia

Este é um projeto para JVM. Foi implementado em [Kotlin](https://kotlinlang.org) usando o [Maven](https://maven.apache.org) como gerenciador de dependências. Fora da camada de domínio algumas bibliotecas foram utilizadas, incluindo:

- [Spring Framework](https://spring.io) como base do projeto
- [MapStruct](https://mapstruct.org) para conversões (ex.: entity para model)
- [Flyway](https://flywaydb.org) para migrações de BD, permitindo [design evolutivo](https://martinfowler.com/articles/evodb.html)
- [Hibernate](https://hibernate.org) para mapeamento objeto-relacional

A aplicação faz uso de uma instância de banco de dados [Postgres](https://www.postgresql.org) e um provedor externo de pagamento, que no momento se trata apenas de um [stub](https://martinfowler.com/eaaCatalog/serviceStub.html).

![Diagrama de Container C4](docs/diagrams/c4-container.png)

## Como executar o projeto

A forma mais simples é utilizando o [Docker Compose](https://docs.docker.com/compose):

```bash
docker compose up
```

## Documentação

A documentação está presente no diretório `/docs`, também publicado a cada build em:

[http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com](http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com/)

Além de documentações sobre DDD, documentos adicionais podem ser encontrados, como diagramas C4, de banco de dados, e de máquinas de estado.

## OpenAPI (Swagger)

Especificação OpenAPI em formato JSON atualizado a cada build:

[Open API JSON](http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com/openapi.json)

Com a aplicação em execução, acesse o Swagger UI em:

```
http://localhost:8080/swagger-ui/index.html
```

Preview:

![Swagger UI](docs/img/swagger-ui.png)

## Postman

API cadastrada no Postman atualizada a cada build:

[Postman API](https://fiap-3soat-g15.postman.co/workspace/tech-challenge~febf1412-7ce2-4cb4-8bca-50f4fdd3a479/api/c77ec61d-c410-443e-92f7-c204be16083b?action=share&creator=12986472)

Uma collection também está disponível em:

[./postman-collection.json](postman-collection.json)

**Para os avaliadores:** use o seguinte token fixo como header `x-admin-token` para testar endpoints prefixados com `/admin`:

```
Z0VdRIeUZzHTFAy9n2kRc=w=tUZikgaUyO6s64sO986g-5uWestRONqUJbHxTxw821/We-DMe9lxEuKkuzCTW=WJ!R8SrC9cI8oXfcc!BbZOQ21QFvj92?sjEIGRjyWbWhrb0rMl5/2TBHE?x6FS2hd9JZdcR9UQzTak?wan-4LMPnOvTrhlSHRFDhXzyFOojo/SnjbwkMEaO3GJ6FIU0?jLC-rLuvtzUNcT9KU0TrLMFmS2m03PvKRzlO5=qeqc

```

## CI / CD

Pipelines foram configuradas usando o [GitHub Actions](https://github.com/features/actions), conforme descrito em código em `.github/workflows`.

- **app:** verificação, incluindo testes unitários e de integração, e análise estática.
- **docs:** geração e publicação do website de documentação usando [MkDocs](https://www.mkdocs.org/).
- **iac:** definição de infraestrutura em [Terraform](https://www.terraform.io) dos recursos usados na [AWS](https://aws.amazon.com) ([S3](https://docs.aws.amazon.com/AmazonS3/latest/userguide/WebsiteHosting.html), etc).
- **openapi:** geração OpenAPI em JSON e sincronização com Postman API.

Diversas imagens e containers Docker foram utilizadas para implementação dessas pipelines, que podem ser verificados no [Makefile](Makefile).

## Desenvolvimento

### Mappers

[MapStruct](https://mapstruct.org) é usado para mapear entities e models.

Implementaçōes para os mappers anotados com `@Mapper` são geradas na compilação:

```
mvn clean compile
```

### Testes

```
mvn clean verify
```

Para incluir os testes de integração:

```
mvn clean verify -DskipITs=false
```

### ktlint

```
mvn antrun:run@ktlint-format
```
