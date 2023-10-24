# ADR 0006: Banco de Dados

## Status
Aceito

## Contexto
De acordo com a especificação do entregável 2, o projeto permite a escolha do banco de dados. A decisão sobre o tipo (relacional, NoSQL, etc.) e o Sistema de Gerenciamento de Banco de Dados (SGBD) (PostgreSQL, DynamoDB, Redis, etc.) é crucial, especialmente considerando o requisito de escalabilidade.

## Decisão
Optamos por um banco de dados relacional, o PostgreSQL, com base nas seguintes considerações:
 
- **Modelagem adequada:** um banco de dados relacional é mais apropriado para dados estruturados, relações entre entidades, e consistência dos dados.

- **Recursos de escalabilidade:** réplicas, partições e soluções proprietárias como o Amazon Aurora permitem que o sistema atenda às demandas de escalabilidade.

- **Alinhamento com a indústria:** o PostgreSQL é amplamente adotado na área, alinhado com as melhores práticas da indústria, com extensa documentação e comunidade ativa.

## Consequências

A escolha do PostgreSQL como banco de dados implica na necessidade de familiarização do grupo com a tecnologia, mas proporciona benefícios como para modelagem de dados, escalabilidade e acesso a uma rica base de conhecimento e suporte da comunidade.