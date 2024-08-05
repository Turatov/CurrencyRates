## Local execution

[Api swagger Documentation ](https://turatov.github.io/CurrencyAPIDocs/ "Visit documentation")

mvn clean install
mvn spring-boot:run -f ./currency

Try:

```
curl localhost:8080/api //get  return List of currencies 
curl localhost:8080/api/{code} //get  return currency by code
curl localhost:8080/api //post body = { name : String , rate = Double}
curl localhost:8080/api //put  body example {"code": "USD","rate": 90.8 }
```

## Docker run application

```
Docker run application
docker-compose up --build -d
docker-compose stop
```
