# currency-conversion-service
This project calls the microservice currency-exchange-service to retrieve currencies information and perform conversions.


## Frameworks

Spring Cloud OpenFeign

## REST API
### GET /currency-conversion
Invoke currency-exchange-service endpoint directly through **rest template** to retrieve currencies information and calculate the conversion multiple of quantity.
#### Request
```
curl -X GET http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
```
#### Response
```
{"id":10001,"from":"USD","to":"INR","quantity":10,"conversionMultiple":65.00,"totalCalculatedAmount":650.00,"environment":"8000 rest template"}
```
### GET /currency-conversion-feign
* Invokes currency-exchange-service endpoint through **Feign Client** to retrieve currencies information and calculate the conversion multiple of quantity.
  * **Feign Client** is actually going to Eureka(ec-naming-service) **asks for the instances** of currency-exchange-service and **load balances** between the instances that are active. 
#### Request
```
curl -X GET http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/10
```
#### Response
```
{"id":10001,"from":"USD","to":"INR","quantity":10,"conversionMultiple":65.00,"totalCalculatedAmount":650.00,"environment":"8001 feign"}
```

## 🚀 How to run ?
1 - Run  [ec-naming-server](https://github.com/aurelios/ce-naming-service)

2 - Run multiple instances of the application
```shell script
 ./mvnw spring-boot:run '-Dspring-boot.run.arguments=--server.port=8100'
```
```shell script
 ./mvnw spring-boot:run '-Dspring-boot.run.arguments=--server.port=8101'
```
```shell script
 ./mvnw spring-boot:run '-Dspring-boot.run.arguments=--server.port=8102'
```
3 - Check the instances registered with Eureka on [ec-naming-server](https://github.com/aurelios/ce-naming-service)