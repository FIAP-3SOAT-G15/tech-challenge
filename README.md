# Tech Challenge

Projeto de Pós-graduação em Arquitetura de Software da FIAP.

## Grupo

Grupo 15:

- Bleno Humberto Claus
- Giovanni Di Luca Evangelista
- Lucas Gabriel dos Santos
- Mateus Sales Albino
- Wellyson de Freitas Santos

## Desenvolvimento

### Mappers

[MapStruct](https://mapstruct.org) é usado para mapear entities e models.

Implementaçōes para os mappers anotados com `@Mapper` são geradas na compilação:

```
mvn clean install
```

### Formatação

`ktlint`:

```
mvn antrun:run@ktlint
```

`ktlint-format`:

```
mvn antrun:run@ktlint-format
```

## Documentação

Acesse: [http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com](http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com/)

Preview:

```bash
make website
```

A documentação é gerada do Markdown presente no diterório `docs` usando [MkDocs](https://www.mkdocs.org) dockerizado e o resultado é publicado em um S3 bucket habilitado como website através de um GitHub Actions workflow.
