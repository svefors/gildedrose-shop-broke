Gilded Shop...

### Running application
spring-boot:run

### Try it out 
By default the application is running with the spring dev profile that has loaded some items.

Get some items for sale.

The default application.properties has a surge pricing that will be active if there 
are more than 10 product catalog views per hour.
```
curl -X GET  http://localhost:8080/items -H "Content-Type: application/json"
```
Buy an item (user needs to be authenticated)
```
curl -X POST  http://localhost:8080/users/myself/purchases -H "Content-Type: application/json" -d '{"itemId": 1, "seenPrice":1000}' -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0IiwiaWF0IjoxNTcxMjQ3Mzk2LCJleHAiOjE2MDI3ODMzOTYsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3QiLCJzdWIiOiJqcm9ja2V0QGV4YW1wbGUuY29tIn0.tiVC2YPWi88sPWZBPQNCQYRRjTR5wTASYnEE7kJyJqs"
```
Get your purchases
```
curl -X GET  http://localhost:8080/users/myself/purchases -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0IiwiaWF0IjoxNTcxMjQ3Mzk2LCJleHAiOjE2MDI3ODMzOTYsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3QiLCJzdWIiOiJqcm9ja2V0QGV4YW1wbGUuY29tIn0.tiVC2YPWi88sPWZBPQNCQYRRjTR5wTASYnEE7kJyJqs"
```
### How was it made
It is built using libraries from Spring Boot.

### Testing
It has a both Integration and Unit testing. POJOs annotated with Lombok were not tested. 

### Data Format
Using HATEOAS with 'application/hal+json' for items
```json
{
  "_embedded" : {
    "items" : [ {
      "name" : "Item_1",
      "description" : "This is Item 1",
      "version" : null,
      "basePrice" : 1000,
      "price" : 1100,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/items/1"
        },
        "item" : {
          "href" : "http://localhost:8080/items/1"
        },
        "buy-item" : {
          "href" : "http://localhost:8080/users/myself/purchases"
        }
      }
    }
  ]
  }
}
```
buying an item:
```json
{"itemId": 1, "seenPrice":1000}
```

### Authentication
It uses JWT. Mainly because I've used it before. Also i perceive that it has better support for 
iOS/Android. It uses symmetric authentication with a secret key. It seemed easier than 
setting its counterpart. But there seem to be some support in Spring to handle that so
it could be worth doing later.

Here is a token that can be used for testing:
```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0IiwiaWF0IjoxNTcxMjQ3Mzk2LCJleHAiOjE2MDI3ODMzOTYsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3QiLCJzdWIiOiJqcm9ja2V0QGV4YW1wbGUuY29tIn0.tiVC2YPWi88sPWZBPQNCQYRRjTR5wTASYnEE7kJyJqs
``` 

### Surge Price Mechanism
It was designed with a Hexagonal Ports and Adapter architecture/pattern in mind. For example, 
CatalogViewRequestInterceptor is an Actor using the Primary Port LogCatalogViewService to register catalog views.

The calculation of the surge pricing is done within H2 database using a native sql query that updates the item table.


h4. Caveat

Ok, I may have gold plated some parts of the application. But it wanted to try some of the additions to 
Spring that I haven't used before.

Arguably, you could make the argument that it is better to not store the surge price in the database, but have a flag 
and leave the surge price calculation to the client. The argument being that there is now couping with the use of native
wuery.

 
