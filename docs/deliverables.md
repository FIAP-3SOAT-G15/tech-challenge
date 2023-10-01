# Entregáveis

## Fase 1

### Entregável 01

Documentação do sistema (DDD) utilizando a linguagem ubíqua, dos seguintes fluxos:

1. Realização do pedido e pagamento
2. Preparação e entrega do pedido

### Entregável 02

Uma aplicação para todo sistema de backend (monolito) que deverá ser desenvolvido seguindo os padrões apresentados nas aulas:

1. Utilizando arquitetura hexagonal
2. APIs
    1. Cadastro do Cliente
    2. Identificação do Cliente via CPF
    3. Criar, editar e remover de produto
    4. Buscar produtos por categoria
    5. Fake checkout, apenas enviar os produtos escolhidos para a fila
    6. Listar os pedidos
3. Aplicação deverá ser escalável para atender grandes volumes nos horários de pico
4. Banco de dados a sua escolha
    1. Inicialmente deveremos trabalhar e organizar a fila dos pedidos apenas em banco de dados

### Entregável 03

A aplicação deve ser entregue com um Dockerfile configurado para executá-la corretamente.

Para validação da POC, temos a seguinte limitação de infraestrutura:

- 1 instância para banco de dados
- 1 instâncias para executar aplicação

Não será necessário o desenvolvimento de interfaces para o frontend, o foco deve ser total no backend.
