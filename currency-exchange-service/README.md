# currency-exchange-service
This project provide a endpoint to return exchange informations stored into a h2 database about the currencies passed through path param.

## Frameworks
* Resilience 4j
* Eureka Client
* Actuator


## REST API
### GET /currency-exchange
Return exchange informations stored into a h2 database about the currencies passed through path param.
#### Request
```
curl -X GET http://localhost:8000/currency-exchange/from/USD/to/INR
```
#### Response
```
{"id":10001,"from":"USD","to":"INR","conversionMultiple":65.00,"environment":"8000"}
```

## Retry
### GET  /sample-api/retry

Resilience4j retries serveral times before returning the fallback response.

Console output:
```
2022-12-19 15:12:30.883  INFO [currency-exchange,bd32372327bf6a54,bd32372327bf6a54] 5300 --- [nio-8000-exec-7] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:12:31.889  INFO [currency-exchange,bd32372327bf6a54,bd32372327bf6a54] 5300 --- [nio-8000-exec-7] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:12:33.400  INFO [currency-exchange,bd32372327bf6a54,bd32372327bf6a54] 5300 --- [nio-8000-exec-7] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:12:35.663  INFO [currency-exchange,bd32372327bf6a54,bd32372327bf6a54] 5300 --- [nio-8000-exec-7] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:12:39.040  INFO [currency-exchange,bd32372327bf6a54,bd32372327bf6a54] 5300 --- [nio-8000-exec-7] c.i.m.c.CircuitBreakerController         : Sample api call received
```

## Circuit Break

### GET  /sample-api/circuit-breaker
You can use the command bellow on windows to fire several calls to the endpoint  /sample-api/circuit-breaker with circuit break configured and check out the console that after several errors the circuit break swich the state to OPEN/HALF_OPEN and starts to return the fallback-response directly and increase the time to try again.

`for /l %g in () do @(curl -GET http://localhost:8000/sample-api/circuit-breaker & timeout /t 1 /nobreak)`

Console output:
```
2022-12-19 15:02:53.127  INFO [currency-exchange,f6b86b86e9ac018f,f6b86b86e9ac018f] 5300 --- [nio-8000-exec-9] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:02:54.139  INFO [currency-exchange,91c3c8ff640e1fec,91c3c8ff640e1fec] 5300 --- [io-8000-exec-10] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:02:55.155  INFO [currency-exchange,8e80019653af3393,8e80019653af3393] 5300 --- [nio-8000-exec-1] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:03:55.167  INFO [currency-exchange,e8a2a9f2edcfb8d6,e8a2a9f2edcfb8d6] 5300 --- [nio-8000-exec-1] c.i.m.c.CircuitBreakerController         : Sample api call received
```

## Rate Limiter
### GET  /sample-api/rate-limiter
You can use the command bellow on windows to fire several calls to the endpoint  /sample-api/rate-limiter with rate limiter configured and check out the console that we are receiving only 2 request at each 10 seconds is what we configured in application.properties
`C:\Users\User>for /l %g in () do @(curl -GET http://localhost:8000/sample-api/rate-limiter & timeout /t 1 /nobreak)`

Console output:
```
2022-12-19 15:21:05.104  INFO [currency-exchange,ba794ca9fbc71fbd,ba794ca9fbc71fbd] 19048 --- [nio-8000-exec-2] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:21:06.134  INFO [currency-exchange,0e248869611095eb,0e248869611095eb] 19048 --- [nio-8000-exec-3] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:21:15.105  INFO [currency-exchange,5fd0e5d325fe1752,5fd0e5d325fe1752] 19048 --- [nio-8000-exec-5] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:21:16.128  INFO [currency-exchange,0d70bba603b59241,0d70bba603b59241] 19048 --- [nio-8000-exec-6] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:21:25.111  INFO [currency-exchange,bb8ec9a7f4807c6c,bb8ec9a7f4807c6c] 19048 --- [nio-8000-exec-8] c.i.m.c.CircuitBreakerController         : Sample api call received
2022-12-19 15:21:26.133  INFO [currency-exchange,2e9ba588b4769472,2e9ba588b4769472] 19048 --- [nio-8000-exec-1] c.i.m.c.CircuitBreakerController         : Sample api call received
```

## Bulkhead - Concurrent Calls
### GET  /sample-api/bulkhead


## 🚀 How to run ?
1 - Run  [ec-naming-server](https://github.com/aurelios/ce-naming-service)

2 - Run multiple instances of the application
```shell script
 ./mvnw spring-boot:run '-Dspring-boot.run.arguments=--server.port=8000'
```
```shell script
 ./mvnw spring-boot:run '-Dspring-boot.run.arguments=--server.port=8001'
```
```shell script
 ./mvnw spring-boot:run '-Dspring-boot.run.arguments=--server.port=8002'
```
3 - Check the instances registered with Eureka on [ec-naming-server](https://github.com/aurelios/ce-naming-service)
## Useful Url's
H2 Console Interface

http://localhost:8000/h2-console/

Spring Actuator

http://localhost:8000/actuator
