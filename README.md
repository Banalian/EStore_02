# EStore_02

This is a Web Service application allowing you to to CRUD operations on products. It also contains a call to an external API to convert prices into another currency.

Using Intellij and JBoss/Wildfly, the urls are
- `http://localhost:8080/EStore_02-1.0-SNAPSHOT/api/v1/`for the API
- `http://localhost:8080/EStore_02-1.0-SNAPSHOT/` for a simple website displaying a list of the current products in the database

# Installation
To install this project, you need to clone it, and create the datasource in your server (This was made using JBoss/Wildfly).
There is also a .sql file to generate some dummy data once you have create the schema ``estore_bd`` on your database.
For the first launch, you need to modify the `persistence.xml` file to put the action to `drop-and-create` instead of `none`. Once you've launched it once, you can put it back to `none`

# Endpoints

## 1. GET ``/products``
#### Description
get the information of all the products stored in the database
#### Produces
- application/xml
- application/json
#### Optional query parameters
- `category` : get all products of a specific category (example : "Alimentation")
- `currency` : get the result with priceInCurrency in a different currency with its code (example : "USD")
- `sort` : sort the result in ascending or descending order ("asc" or "desc");

#### Examples
``

## 2. GET ``/products/<id>``
#### Description
get the information for the product with a given `id`
#### Produces
- application/xml
- application/json


## 3. DELETE ``/products/<id>``
#### Description
delete a specific product with the given `id`. Will also delete any comments the product might have.


## 4. POST ``/products``
#### Description
create an entirely new product
#### Consumes
- application/xml
- application/json



## 5. PUT ``/products/<id>``
#### Description
completely modify the product with a given `id`
#### Consumes
- application/xml
- application/json


## 6. PATCH ``/products/<id>``
#### Description
partially  modify the product with a given `id`
#### Consumes
- application/xml
- application/json


## 7. GET ``/products/<id>/comments``
#### Description
get all the comments of a product with a given `id`
#### Produces
- application/xml
- application/json
