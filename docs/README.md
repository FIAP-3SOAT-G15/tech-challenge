# Tech Challenge

Projeto de Pós-graduação em Arquitetura de Software da FIAP.

## Autores

- Bleno Humberto Claus
- Giovanni Di Luca Evangelista
- Lucas Gabriel dos Santos
- Mateus Sales Albino
- Wellyson de Freitas Santos

## Links externos

- [Repositório Git](https://github.com/FIAP-3SOAT-G15/tech-challenge)
- [Miro whiteboard](https://miro.com/app/board/uXjVMqdH21Q=/?share_link_id=101165721616)

## Documentação

Com base na [especificação](spec.md) do desafio proposto para o Tech Challenge Fase 1, a presente documentação descreve o problema em linguagem ubíqua, apresenta o storytelling com linguagem pictográfica, e demarca os contextos delimitados, além de disponibilizar uma documentação do event storming.

- [Glossário ubíquo](glossary.md)
- [Event storming](event-storming.md)
- [Storytelling](storytelling.md)
- [Mapa de Contexto](context-map.md)
- [Diagrama Entidade-Relacionamento](schema.md)
- [Máquinas de estados](state-machines.md)

### OpenAPI e Postman

Especificação OpenAPI é gerada a cada alteração mergeada e publicada automaticamente em API cadastrada no Postman:

[Postman API 🚀](https://fiap-3soat-g15.postman.co/workspace/tech-challenge~febf1412-7ce2-4cb4-8bca-50f4fdd3a479/api/c77ec61d-c410-443e-92f7-c204be16083b?action=share&creator=12986472)

A definição em JSON pode ser encontrada em:

[openapi.json](http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com/openapi.json)

Finalmente, com a aplicação em execução (por exemplo, através do Docker Compose), a interface interativa do Swagger UI pode ser acessada em:

`http://localhost:8080/swagger-ui/index.html`

Preview:

![Swagger UI](img/swagger-ui.png)
