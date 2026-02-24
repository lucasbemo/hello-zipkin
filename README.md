# Zipkin Hello World (Spring Boot + Gateway + Payment)

This repo contains two Java 21 Spring Boot services:
- `api-gateway` (Spring Cloud Gateway)
- `payment-service`
- `zipkin-service` (Zipkin server)

Tracing is enabled via Micrometer + Zipkin. The only verification step is checking Zipkin spans after calling the gateway.

## Getting Started (Docker)

### Prerequisites

- Java 21 (only needed if you build locally outside Docker)
- Docker + Docker Compose
- `curl`

### Start Services (Docker Compose)

```bash
docker compose up --build
```

### Verify Traces

1) Call the gateway:

```bash
curl -s http://localhost:8080/payments/hello
```

Or call the payment service directly:

```bash
curl -s http://localhost:8081/payments/hello
```

2) Confirm Zipkin received spans from both services.

Check services list:
```bash
curl -s http://localhost:9411/api/v2/services
```

Get a recent trace for the gateway and verify the payment span exists:
```bash
TRACE_ID=$(curl -s "http://localhost:9411/api/v2/traces?serviceName=api-gateway&limit=1" | sed -n 's/.*"traceId":"\([^"]*\)".*/\1/p' | head -n 1)

curl -s "http://localhost:9411/api/v2/trace/$TRACE_ID" | grep -E "api-gateway|payment-service"
```

If both service names appear in the trace output, Zipkin has spans for the gateway and the payment service.

### Troubleshooting

- No traces: confirm `ZIPKIN_ENDPOINT` is `http://zipkin:9411/api/v2/spans`.
- Ensure all containers are attached to `zipkin-net`.
- Check Zipkin UI is reachable at `http://localhost:9411/zipkin/`.
