# Tech Challenge

Projeto de Pós-graduação em Arquitetura de Software da FIAP.

## Grupo

Grupo 15:

- Bleno Humberto Claus
- Giovanni Di Luca Evangelista
- Lucas Gabriel dos Santos
- Mateus Sales Albino
- Wellyson de Freitas Santos

## Documentação

Accesse: [http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com](http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com/)

Preview:

```bash
make website
```

A documentação é gerada do Markdown presente no diterório `docs` usando [MkDocs](https://www.mkdocs.org) dockerizado e o resultado é publicado em um S3 bucket habilitado como website através de um GitHub Actions workflow.
