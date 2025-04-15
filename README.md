# Order Manager Challenge

## Objetivo: 
 Criar uma aplicação backend utilizando Java com o framework Spring Boot, que implemente uma arquitetura de microservices, se comunique com um banco de dados e troque mensagens entre serviços utilizando um broker de mensagens.

## Implementação:
- Versão do 17 Java
- Springframework versão 3.4.4
- Microserviços com docker
- Banco de dados foi utilizado o PostgreSQL
- Broker de mensagens feito com kafka

A implementação do projeto foi feita toda com microserviços com o MS order_db que se comunica com payment sendo o consumer e o MS order o producer e a comunicação entre eles é feita pelo MS do kafka e MS zookeper
![system_design](https://github.com/user-attachments/assets/5369de12-fd11-44b7-8b72-ec07d1df5647)

## Execução
Para facilitar a execução do projeto criei uma pasta de microservice já com o .jar do payment e .jar do order
com o docker devidamente instalado, basta rodar na raiz do projeto:
- **docker-compose up**
- caso conteça algum erro basta executar:
**docker-compose down -v**
e tentar novamente.

## Teste
Para testar a comunicação por mensageria abaixo estão os Culrls para teste:
- Criar pedido:
```bash
curl --location 'http://localhost:8081/orders' \
--header 'Content-Type: application/json' \
--data '{
    "customerName": "Linda Davis",
    "status": "PENDING"
}'
```
- Listar pagamentos:
```bash
curl --location 'http://localhost:8082/payments'
```
- Listar pedidos:
```bash
curl --location 'http://localhost:8081/orders'
```
- Atualizar status de pagamento
```bash
curl --location --request PUT 'http://localhost:8081/orders/1/status' \
--header 'Content-Type: application/json' \
--data 'COMPLETED'
```
Obs: Aqui você pode encontrar o repositório do projeto [order-manager-payment](https://github.com/AnselmoAlvesJunior/order-manager-payment).
