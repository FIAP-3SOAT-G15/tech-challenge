# Limitacōes conhecidas

A aplicação não possui validação exaustiva de entrada do usuário. Por exemplo: e-mails e CPF não são validados. Reconhecemos, no entanto, a importância de validações como essa para a integridade do negócio. Value Objects poderiam ser criados para essas abstrações na camada de domínio.

Uma outra observação pertinente é a prevenção de modelos de domínios anêmicos. Esse anti-pattern sugere muitas regras de negócio no serviços, deixando modelos caracterizados apenas por um agrupamento de dados. Apesar de fazermos uso de data classes do Kotlin, buscamos reduzir esse problema, embora ainda presente.

A autenticação e autorização da API foi simulada visando simplicidade. Para endpoints de administração, prefixados com `/admin`, um cabeçalho é necessário: `x-admin-token`. Atualmente, um valor fixo deve ser usado, que pode ser encontrado no arquivo de propriedades de configuração do projeto.

O provedor de pagamento foi semelhantemente simulado. Uma interface garante que qualquer ator conduzido possa implementar a especificação necessária para os casos de uso. Atualmente, o mock aprova todos os pagamentos, e retorna um QR code fictício em formato Base64.

Por fim, gostaríamos de ressaltar um disclaimer: o projeto não tem 80% de cobertura de testes, o que é geralmente o quality gate comum da indústria. Apesar disso, testes unitários e de integração para complexidades importantes foram implementados e podem ser executados de acordo com o arquivo README.md.
