
Local execution
mvn clean install
mvn spring-boot:run -f ./eureka-server
mvn spring-boot:run -f ./currency

Try:
curl localhost:8080/api/ping  //get 
curl localhost:8080/api  //post    body = { name : String , rate = Double}

Docker run application
docker-compose up --build -d
docker-compose stop
