# Tech Challenge

Projeto de Pós-graduação em Arquitetura de Software da FIAP.

## Autores

- Bleno Humberto Claus
- Giovanni Di Luca Evangelista
- Lucas Gabriel dos Santos
- Mateus Sales Albino
- Wellyson de Freitas Santos

## Documentação

Acesse:

[http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com](http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com/)

Preview:

```bash
make website
```

## OpenAPI e Postman

Especificação OpenAPI em formato JSON (atualizado a cada build):

[Open API JSON](http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com/openapi.json)

API cadastrada no Postman (atualizada a cada build):

[Postman API](https://fiap-3soat-g15.postman.co/workspace/tech-challenge~febf1412-7ce2-4cb4-8bca-50f4fdd3a479/api/c77ec61d-c410-443e-92f7-c204be16083b?action=share&creator=12986472)

Use o seguinte token como header `x-admi-token` para testar endpoints `/admin`:

```
Z0VdRIeUZzHTFAy9n2kRc=w=tUZikgaUyO6s64sO986g-5uWestRONqUJbHxTxw821/We-DMe9lxEuKkuzCTW=WJ!R8SrC9cI8oXfcc!BbZOQ21QFvj92?sjEIGRjyWbWhrb0rMl5/2TBHE?x6FS2hd9JZdcR9UQzTak?wan-4LMPnOvTrhlSHRFDhXzyFOojo/SnjbwkMEaO3GJ6FIU0?jLC-rLuvtzUNcT9KU0TrLMFmS2m03PvKRzlO5=qeqc

```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

Preview:

![Swagger UI](docs/img/swagger-ui.png)

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

Para incluir testes de integração:

```
mvn clean verify -DskipITs=false
```

### Formatação

```
mvn antrun:run@ktlint-format
```
