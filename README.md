# project1-bank-accounts
Java Spring Boot Backend connected to mongodb.

### Command for run Dockerfile and start container
cd /project1-bank-accounts

docker build -t "accounts"

docker run --restart always --name accounts -8080:9000 -d accounts:latest

### Sonarqube
docker run -d --name sonarqube -p 8090:9000 sonarqube

## Arquitectura de Microservicio
![Arquitectura](arquitectura.png)