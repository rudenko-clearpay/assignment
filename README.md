[![Java CI with Maven](https://github.com/rudenko-clearpay/assignment/actions/workflows/build-artifacts.yml/badge.svg)](https://github.com/rudenko-clearpay/assignment/actions/workflows/build-artifacts.yml)

##Run within docker-compose:
```
docker-compose up
```

##Run locally:
```
1. Install and run MongoDB locally
2. cd server
   mvn spring-boot:run -Dspring-boot.run.profiles=test -f pom.xml
3. cd client 
   yarn
   yarn run start

```
