# covid

Containerized rest app to download statistics for covid.

This is demo project for Docker.

### Running app on PROD

1. Application properties:

    Use spring.datasource.url for PROD, comment DEV

2. Run build script:

    ```./build```
    
    This script will:
    
    * build jar file for covid app
    * build docker image for covid app
    * run docker compose file: docker-prod-compose.yml
    * compose file will create mysql db container 
        and covid-app container
    * mysql will not be accessible from the local machine
    
3. Go into MySQL container, if needed:

    ```docker ps``` - get container name for MySQL
    
    ```docker exec -it <container-name> /bin/bash```
    
4. Run requests script:

    ```./requests```

### Running app for DEV purposes

1. Application properties:
 
    Use spring.datasource.url for DEV, comment PROD

2. Run docker compose to create MySQL db:

    ```docker-compose -f docker-dev-compose up -d```
    
3. Db can be access from host machine on port 3036 
    (thanks to port mapping in docker compose yaml file)
    
    - via mysql client tool
    - via mysql-workbench

4. Run project using Spring Boot

5. Run requests script:

    ```./requests```