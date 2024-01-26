# Mercado Pago

### Crie uma loja

https://www.mercadopago.com.br/developers/pt/reference/stores/_users_user_id_stores/post

```
curl -X POST 'https://api.mercadopago.com/users/{userId}/stores' \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer TEST-344********79723-01********6df35576********994e704d********9641402' \
-d '{
    "name": "Store for tests",
    "location": {
        "street_name": "Av. Lins de Vasconcelos",
        "street_number": "1222",
        "city_name": "São Paulo",
        "state_name": "São Paulo",
        "latitude": -23.5741903,
        "longitude": -46.6235053,
        "reference": null
    }
}'
```

Exemplo de resposta:

```
{
    "id": "12345678",
    "name": "Store for tests",
    "date_creation": "2024-01-26T07:40:41.079Z",
    "location": {
        "address_line": "Av. Lins de Vasconcelos 1222, São Paulo, São Paulo, Brasil",
        "latitude": -23.5741903,
        "longitude": -46.6235053,
        "id": "BR-SP-44",
        "type": "city",
        "city": "São Paulo",
        "state_id": "BR-SP"
    }
}
```

### Crie um caixa para a loja

POS (Point of Sale)

https://www.mercadopago.com.br/developers/pt/reference/pos/_pos/post

```
curl -X POST 'https://api.mercadopago.com/pos' \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer TEST-344********79723-01********6df35576********994e704d********9641402' \
-d '{
    "name": "POS for tests",
    "store_id": 12345678,
    "external_id": "POS4TEST",
    "fixed_amount": false
}'
```
