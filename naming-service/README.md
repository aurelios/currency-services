# naming-service
This project works as a service registry or naming server that uses **Spring Cloud Netflix**  to provide the pattern of
**Service Discovery (Eureka)** to register each instance of a microservice so it can be highly available for load balancing.

## 🚀 How to run ?
1 - Run the application
```shell script
./mvnw spring-boot:run
```
2 - Check Instances currently registered with Eureka

http://localhost:8761/