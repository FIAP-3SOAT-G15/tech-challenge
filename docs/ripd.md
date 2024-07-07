# Relatório de Impacto à Proteção de Dados (RIPD)

## Sistema de Tratamento de Dados Pessoais

### a) Descrição dos Tipos de Dados Pessoais Coletados ou Tratados

Nosso sistema coleta e trata os seguintes dados pessoais dos usuários:

- **Nome**: Identificação pessoal do usuário.
- **Email**: Meio de comunicação e identificação eletrônica do usuário.
- **CPF**: Identificação única do cidadão brasileiro.
- **Número de telefone**: Meio de contato direto com o usuário.
- **Endereço**: Localização física do usuário para possíveis entregas e correspondências.

### b) Metodologia Usada para o Tratamento e para a Garantia da Segurança das Informações

Nossa infraestrutura de segurança é robusta e construída com os seguintes componentes e práticas da AWS:

1. **Repositórios Privados no Amazon Elastic Container Registry (ECR)**:
   - **Uso**: Armazenamento seguro de imagens de contêiner.
   - **Segurança**: Acesso controlado através de políticas de IAM (Identity and Access Management).

2. **Cluster do Amazon Elastic Kubernetes Service (EKS)**:
   - **Uso**: Orquestração de contêineres para a aplicação.
   - **Segurança**: Configurações de rede seguras e controles de acesso rigorosos. Uso de namespaces e políticas de rede para isolar cargas de trabalho.

3. **Instâncias do Relational Database Service (RDS) for PostgreSQL**:
   - **Uso**: Armazenamento de dados estruturados.
   - **Segurança**: Criptografia em repouso e em trânsito. Backups automáticos e snapshots para recuperação de dados. Implementação de grupos de segurança para controle de acesso.

4. **Tabela do DynamoDB**:
   - **Uso**: Armazenamento de dados não estruturados.
   - **Segurança**: Criptografia em repouso e em trânsito. Controle de acesso através de IAM.

5. **Secrets no AWS Secrets Manager**:
   - **Uso**: Armazenamento seguro de segredos como credenciais de banco de dados e chaves de API (ex: Mercado Pago).
   - **Segurança**: Rotação automática de segredos, acesso controlado por IAM, criptografia em repouso.

6. **Parâmetros de Sistema no SSM Parameter Store**:
   - **Uso**: Armazenamento de parâmetros de configuração do sistema.
   - **Segurança**: Criptografia em repouso, controle de acesso granular com IAM.

7. **API Gateway**:
   - **Uso**: Gerenciamento de APIs, atuando como load balancer com EKS como target.
   - **Segurança**: Autenticação e autorização com AWS Cognito e IAM, proteção contra ataques DDoS com AWS Shield.

8. **User Pool de Clientes no Cognito**:
   - **Uso**: Gerenciamento de autenticação de usuários.
   - **Segurança**: Autenticação multifator (MFA), verificação de e-mail e senha forte, integração com outras fontes de identidade.

9. **Funções Lambda para Autenticação**:
   - **Uso**: Execução de lógica de autenticação personalizada.
   - **Segurança**: Controle de acesso com IAM, ambientes de execução isolados.

### c) Análise do Controlador com Relação a Medidas, Salvaguardas e Mecanismos de Mitigação de Riscos Adotados

#### Medidas Implementadas

1. **Avaliação de Riscos**:
   - Realizamos avaliações regulares de riscos para identificar e mitigar vulnerabilidades no sistema.
   - Executamos testes de penetração para avaliar a segurança das nossas aplicações e infraestrutura.

2. **Treinamento e Conscientização**:
   - Nossos colaboradores são treinados continuamente sobre práticas de segurança da informação e proteção de dados.
   - Promovemos campanhas de conscientização sobre a importância da proteção dos dados pessoais e a conformidade com a LGPD.

#### Salvaguardas Adotadas

1. **Políticas de Privacidade**:
   - Desenvolvemos e mantemos políticas de privacidade claras e transparentes, informando os usuários sobre a coleta e o uso de seus dados pessoais.
   - Realizamos revisões periódicas das políticas para garantir conformidade com a LGPD e outras regulamentações.

2. **Anonimização e Pseudonimização**:
   - Aplicamos técnicas de anonimização e pseudonimização aos dados pessoais, sempre que apropriado, para reduzir os riscos em caso de violação de dados.

#### Mecanismos de Mitigação de Riscos

1. **Resposta a Incidentes**:
   - Implementamos um plano de resposta a incidentes que nos permite detectar, responder e recuperar rapidamente de qualquer violação de segurança.
   - Comunicamos imediatamente os titulares dos dados e as autoridades competentes em caso de incidentes que envolvam dados pessoais.

2. **Auditorias e Revisões**:
   - Realizamos auditorias regulares e revisões de segurança para garantir a eficácia das medidas de segurança adotadas.
   - Melhoramos continuamente nossos processos e políticas com base nos resultados das auditorias e na evolução do cenário de ameaças.

3. **Proteção de Dados no Ciclo de Vida**:
   - Garantimos a proteção dos dados pessoais durante todo o ciclo de vida, desde a coleta até a exclusão.
   - Implementamos políticas de retenção e descarte seguro de dados pessoais para minimizar riscos de exposição indevida.

---

Este relatório detalha as práticas e medidas adotadas pelo nosso sistema para garantir a proteção dos dados pessoais, em conformidade com a LGPD. As ações descritas visam mitigar riscos e assegurar a privacidade e segurança dos dados dos nossos usuários.

** Alguns pontos destes relatórios, como auditorias, treinamento, etc, não representam a realidade por não se tratar de um cenário real, mas podem ser considerados como modelo a seguir se fôssemos uma empresa real.