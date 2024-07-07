# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/#build-image)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Following are two reference materials
* https://sofienebk.medium.com/change-data-capture-made-easy-debezium-integration-with-spring-boot-mongodb-and-postgres-96dc9772bb86
* https://www.baeldung.com/debezium-intro

### docker command to start mysql db
docker-compose up -d
docker images
docker ps

### Create schema start
drop table Product;

CREATE TABLE Product (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
price FLOAT,
description TEXT,
sourceCollection VARCHAR(255),
mysqlId VARCHAR(255)
);

select * from Product;

INSERT INTO Product (name, price, description, mysqlId) VALUES ('Sample Product', 99.99, 'This is a sample product description.', 'mysql123');
### Create schema end

### Output 
* start mysql instances
* start the application 
* Use above insert statement to create records
* On console you will find one output "Got one event to handle" for every insert statement !
* Play with other possible scenarios.
* TODO Update the code : HandlerUtils.getData where I have hard coded the jspn string representation of the Product object


