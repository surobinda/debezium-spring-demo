# Getting Started

### Following are two reference materials
* https://sofienebk.medium.com/change-data-capture-made-easy-debezium-integration-with-spring-boot-mongodb-and-postgres-96dc9772bb86
* https://www.baeldung.com/debezium-intro

### docker command to start mysql db
docker-compose up -d
docker images
docker ps

### database connection for for both source and target
* jdbc:mysql://localhost:3305/sourcedb?allowPublicKeyRetrieval=true&useSSL=false
* jdbc:mysql://localhost:3306/targetdb?allowPublicKeyRetrieval=true&useSSL=false

### source db Create schema and DML start
drop table Product;
CREATE TABLE Product (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
price FLOAT,
description TEXT
);
select * from sourcedb.Product p;
INSERT INTO Product (name, price, description) VALUES ('Sample Product', 99.99, 'This is a sample product description.');
delete from sourcedb.Product p where id = 4;
commit;
### source db Create schema and DML END

### target db Create schema start
drop table product;
CREATE TABLE product (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
price FLOAT,
description TEXT
);
select * from targetdb.product;
### target db Create schema and DML end

### Output 
* start mysql instances
* start the application 
* Use above insert statement to create records at source db
* On console you will find one output "Got one event to handle" for every insert statement !
* Check in the destination db a new record will be inserted 
* Once you delete a recordat source db, same record will be deleted at target db as well.


