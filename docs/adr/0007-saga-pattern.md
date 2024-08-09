# ADR 0007: Saga Pattern

## Status

Aceito

## Contexto

Os microserviços devem interagir para completar uma transação de negócio (ou seja, processar um pedido) usando o padrão Saga. Precisamos decidir entre duas abordagens: Orquestração e Coreografia.

## Decisão

Decidimos implementar o padrão Saga usando Coreografia.

Nossa solução incorpora filas AWS SQS e tópicos SNS.
- Cada microserviço lê de uma fila SQS.
- Os microserviços postam os eventos de negocio em um tópico SNS
- As filas estão inscritas nos tópicos de interesse, assim apenas as mensagens importantes para o microserviço são entregues.  

### Justificativa

1. Baixo Acoplamento:
   - A coreografia promove um baixo acoplamento entre microserviços. Cada microserviço opera de forma independente e só precisa se inscrever nos eventos que lhe interessam, reduzindo as dependências entre os serviços.
1. Escalabilidade:
   - À medida que novos serviços são adicionados, eles podem se inscrever facilmente em tópicos existentes sem precisar de modificações nos serviços existentes ou em um orquestrador central. Isso melhora a escalabilidade da nossa arquitetura.
1. Flexibilidade:
   - A coreografia permite que cada microserviço evolua de forma independente. Como não há um orquestrador central, mudanças em um serviço não exigem mudanças em um controlador de lógica central, facilitando a introdução de novos recursos ou serviços.
1. Resiliência:
   - Em caso de falha de um serviço, o impacto é localizado, pois não há um ponto único de falha (ou seja, um orquestrador). Cada serviço lida com seus próprios eventos e erros, tornando o sistema mais resiliente.
1. Simplicidade:
   - A abordagem de coreografia simplifica nossa implementação. Ao aproveitar os serviços gerenciados pela cloud (SNS e SQS), evitamos a complexidade de gerenciar um orquestrador central, tornando o desenvolvimento e a manutenção do sistema mais simples.

## Consequências

- Consistência Eventual: Com a Coreografia, há um potencial para aumento da complexidade na gestão da consistência eventual entre os serviços. No entanto, essa troca é aceitável, dadas as vantagens em escalabilidade, resiliência e, principalmente, o tamanho reduzido do nosso ecosistema.
- Observabilidade: Pode ser mais difícil rastrear o fluxo geral de uma saga, pois não há um orquestrador central. Precisaremos implementar boas práticas de log e monitoramento para garantir que possamos observar e solucionar problemas no sistema de forma eficaz.
