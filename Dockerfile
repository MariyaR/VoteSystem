FROM tomcat:9.0.62-jdk17-corretto
COPY /target/VoteSystem.war /usr/local/tomcat/webapps/
