FROM docker.br.hmheng.io/base-ubuntu:java_1.8.0_74-b02
WORKDIR /
# install mesos
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF
RUN echo "deb http://repos.mesosphere.io/ubuntu trusty main" | sudo tee /etc/apt/sources.list.d/mesosphere.list

RUN apt-get -y update
RUN apt-get install -y mesos=1.0.0-2.0.89.ubuntu1404 jq

RUN wget -O /spark-2.2.0-SNAPSHOT-bin-hadoop2.7.tgz https://www.apache.org/dist/spark/spark-2.2.0/spark-2.2.0-bin-hadoop2.7.tgz
RUN tar -xzf /spark-2.2.0-SNAPSHOT-bin-hadoop2.7.tgz -C / --strip 1
ENV JAVA_HOME=/usr/lib/jvm/java-8-oracle

ADD @project.build.finalName@.@project.packaging@ app.@project.packaging@