# EStore_02

This is a Web Service application allowing you to to CRUD operations on products. It also contains a call to an external API to convert prices into another currency.

Using Intellij and JBoss/Wildfly, the urls are
- `http://localhost:8080/EStore_02-1.0-SNAPSHOT/api/v1/`for the API
- `http://localhost:8080/EStore_02-1.0-SNAPSHOT/` for a simple website displaying a list of the current products in the database

# Installation
To install this project, you need to clone it, and create the datasource in your server (This was made using JBoss/Wildfly).
There is also a .sql file to generate some dummy data once you have create the schema ``estore_bd`` on your database.
For the first launch, you need to modify the `persistence.xml` file to put the action to `drop-and-create` instead of `none`. Once you've launched it once, you can put it back to `none`

#### Examples
typical returned objects in JSON form:

    {
        "productId": 2,
        "label": "Lave Vaisselle",
        "category": "Electroménager",
        "priceInEuro": 219.98,
        "stock": 8,
        "comments": []
    }
    
and xml form:

    <product>
        <category>Electroménager</category>
        <label>Lave Vaisselle</label>
        <priceInEuro>219.98</priceInEuro>
        <productId>2</productId>
        <stock>8</stock>
    </product>
    
#### Comments
About the price conversion while getting multiple products, we decided, rather than converting prices one by one for each product by requesting the api, we get the conversion rate a single time and use it to update all products in the list.
This approach saves us some requests, mainly to stay under the request limit, it also optimises delay during the operation a bit.

The original project was an eclipse project, which we extracted to be able to create a maven project for easy dependency managing.
We tried to keep as much of the original structure as possible (segmenting business, DAO, models and webservices), only making minor adjustments to the DAO and business to add needed features like the currency part.

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

#### Request example
- `/products?currency=USD?sort=asc?category=Alimentation`
This specific query with the GET verb requests all products of the "Alimentation" category, with prices converted to USD, and sorts the resulting list by ascending price.

## 2. GET ``/products/<id>``
#### Description
get the information for the product with a given `id`
#### Produces
- application/xml
- application/json
#### Optional query parameter
- `currency` : get the result with priceInCurrency in a different currency with its code (example : "USD")

#### Request example
- `/products/15?currency=USD`
This specific query with the GET verb requests the product of Id 15 with its price converted to USD.

## 3. DELETE ``/products/<id>``
#### Description
delete a specific product with the given `id`. Will also delete any comments the product might have.

#### Request example
- `/products/18`
This specific query with the DELETE verb requests the deletion of the product of id 18.

## 4. POST ``/products``
#### Description
create an entirely new product
#### Consumes
- application/xml
- application/json

#### Request example
- `/products`
This specific query with the POST verb with an included product in "typical" format (as shown in the beginning examples) requests the creation of a product with id auto generated.


## 5. PUT ``/products/<id>``
#### Description
completely modify the product with a given `id`
#### Consumes
- application/xml
- application/json

#### Request example
- `/products/28`
This specific query with the PUT verb with an included product in "typical" format (as shown in the beginning examples) requests the modification of the product of id 28 to match the product object that was sent.


## 6. PATCH ``/products/<id>``
#### Description
partially  modify the product with a given `id`
#### Consumes
- application/xml
- application/json

#### Request example
- `/products/28`
This specific query with the PATCH verb with an included product in "typical" format (as shown in the beginning examples) requests the modification of the product of id 28 to match the product object that was sent, the object can be incomplete, in that case the unentered values will remain the same.

## 7. GET ``/products/<id>/comments``
#### Description
get all the comments of a product with a given `id`
#### Produces
- application/xml
- application/json

#### Request example
- `/products/3/comments`
This specific query with the GET verb requests all the comments about the product of Id 3, in a arbitrarily sorted list.
