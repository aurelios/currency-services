# api-gateway
This project uses **Spring Cloud Gateway**  to provide an effective way to route to APIs and provide cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.

## 🚀 How to run ?
1 - Run the application
```shell script
./mvnw spring-boot:run
```
2 - Calling API GATEWAY 

Adding Request Parameter and Request Header to the request http://httpbin.org/

http://localhost:9000/get


#### Uses service discovery to get services registered on Eureka


http://localhost:9000/CURRENCY-EXCHANGE/currency-exchange/from/USD/to/INR

http://localhost:9000/CURRENCY-CONVERSION/currency-conversion/from/USD/to/INR/quantity/10

http://localhost:9000/CURRENCY-CONVERSION/currency-conversion-feign/from/USD/to/INR/quantity/10


#### Custom Routes Configured

http://localhost:9000/currency-exchange/from/USD/to/INR

http://localhost:9000/currency-conversion/from/USD/to/INR/quantity/10

http://localhost:9000/currency-conversion-feign/from/USD/to/INR/quantity/10
