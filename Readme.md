## Local execution

```
mvn clean install
mvn spring-boot:run -f ./currency

Try:
curl localhost:8080/api  //get  
curl localhost:8080/api  //post    body = { name : String , rate = Double}

```

## Docker run application

```
Docker run application
docker-compose up --build -d
docker-compose stop
```
