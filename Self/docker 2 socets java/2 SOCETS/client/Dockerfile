FROM java:8  
COPY . /var/www/java
EXPOSE 9090  
WORKDIR /var/www/java  
RUN javac client.java  
CMD ["java", "Client"]
