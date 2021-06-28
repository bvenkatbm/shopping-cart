FROM centos

RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel

ENV JAVA_HOME /etc/alternatives/jre

COPY build/libs/shopping-cart-0.0.1-SNAPSHOT.jar /usr/local/bin

CMD java -jar \
    -Dspring.profiles.active=${PROFILE} \
    /usr/local/bin/shopping-cart-0.0.1-SNAPSHOT.jar

EXPOSE 8002