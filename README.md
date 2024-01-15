# How to run application
1. Run docker containers.
```shell
docker-compose up
```
----------------------------------------------------
2. Start Eureka Server.
----------------------------------------------------
3. Run multiple instances of application
```shell
java "-Dserver.port=8081" -jar OnlineBankSystem-0.0.1-SNAPSHOT.jar
java "-Dserver.port=8082" -jar OnlineBankSystem-0.0.1-SNAPSHOT.jar
```
----------------------------------------------------
4. Run NGINX reverse proxy (check [localhost](localhost))
----------------------------------------------------
