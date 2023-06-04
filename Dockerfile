FROM ubuntu:latest

COPY Main.java /app/Main.java

WORKDIR /app

RUN apt-get update && apt-get install -y openjdk-11-jdk wget

RUN wget https://downloads.mysql.com/archives/get/p/3/file/mysql-connector-j_8.0.32-1ubuntu22.04_all.deb

RUN dpkg-deb -xv mysql-connector-j_8.0.32-1ubuntu22.04_all.deb .

RUN javac -cp ./usr/share/java/mysql-connector-j-8.0.32.jar Main.java

COPY run-java.sh /app/run-java.sh

RUN chmod +x run-java.sh

CMD ["tail", "-f", "/dev/null"]


