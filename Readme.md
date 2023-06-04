# Java CRUD Project and Docker

Project using Java 11, JDBC, Mysql, Docker and Docker Compose

## Requirements

- Docker engine v24.0.2
- Docker Compose v2.18.1

## Project Setup

1. Clone the repository:

- git clone https://github.com/WojciechChuchro/docker.git

2. Navigate to the project directory:

- cd docker

## Running the Project

1. Run the containers with Docker Compose

- docker compose up -d

2. Connect to the java container and run the script

- docker exec -it myjava /app/run-java.sh

3. App is running, follow the steps on the terminal
