# CTSE Lab 5 - Microservices (Spring Boot + API Gateway)

## IT22219084 - B.M.P.C. Senavirathna

---

This project is a simple microservices system with an API Gateway and three backend services:

- `item-service` (Spring Boot)
- `order-service` (Spring Boot)
- `payment-service` (Spring Boot)
- `api-gateway` (Spring Cloud Gateway)

All services run in Docker and use in-memory storage (data resets when containers restart).

## Architecture

- Client requests go to `api-gateway` on port `8080`
- Gateway routes requests by path:
	- `/items/**` -> `item-service:8081`
	- `/orders/**` -> `order-service:8082`
	- `/payments/**` -> `payment-service:8083`

## Project Structure

```text
.
├── api-gateway/        # Spring Cloud Gateway (Java)
├── item-service/       # Spring Boot (Java)
├── order-service/      # Spring Boot (Java)
├── payment-service/    # Spring Boot (Java)
├── docker-compose.yml
└── README.md
```

## Services and Endpoints

### 1. Item Service (`8081`)

Manages a simple in-memory list of items.

- `GET /items` - List all items
- `POST /items` - Add an item (plain text body)
- `GET /items/{id}` - Get item by index (0-based)

Example `POST /items` body:

```text
Tablet
```

### 2. Order Service (`8082`)

Manages orders in memory.

- `GET /orders` - List all orders
- `POST /orders` - Place an order
- `GET /orders/{id}` - Get order by ID

Example request body:

```json
{
	"item": "Laptop",
	"quantity": 2,
	"customerId": "C001"
}
```

The service adds:
- `id`
- `status: "PENDING"`

### 3. Payment Service (`8083`)

Manages payments in memory.

- `GET /payments` - List all payments
- `POST /payments/process` - Process a payment
- `GET /payments/{id}` - Get payment by ID

Example request body:

```json
{
	"orderId": 1,
	"amount": 1299.99,
	"method": "CARD"
}
```

The service adds:
- `id`
- `status: "SUCCESS"`

### 4. API Gateway (`8080`)

Routes incoming requests to the correct backend service using Spring Cloud Gateway.

Use the gateway for testing:

- `http://localhost:8080/items`
- `http://localhost:8080/orders`
- `http://localhost:8080/payments`

## Prerequisites

- Docker
- Docker Compose

## Running the Project

From the project root:

```bash
docker compose up --build
```

Services will be available on:

- Gateway: `http://localhost:8080`
- Item Service: `http://localhost:8081`
- Order Service: `http://localhost:8082`
- Payment Service: `http://localhost:8083`

To stop:

```bash
docker compose down
```

## Quick Test (via API Gateway)

### Items

```bash
curl http://localhost:8080/items
curl -X POST http://localhost:8080/items -H "Content-Type: text/plain" -d "Tablet"
curl http://localhost:8080/items/0
```

### Orders

```bash
curl http://localhost:8080/orders
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" -d '{"item":"Laptop","quantity":2,"customerId":"C001"}'
curl http://localhost:8080/orders/1
```

### Payments

```bash
curl http://localhost:8080/payments
curl -X POST http://localhost:8080/payments/process -H "Content-Type: application/json" -d '{"orderId":1,"amount":1299.99,"method":"CARD"}'
curl http://localhost:8080/payments/1
```

## Notes

- Data is stored in memory only (not persisted).
- Services communicate through the Docker network (`microservices-net`) and are reachable by service name inside Docker.
- For lab/demo purposes, there is no database, authentication, or service-to-service calls.
