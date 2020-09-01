# DWP Online Test - Subhasis Swain

## Requirement

Using the language of your choice please build your own API which calls the API at https://dwp-techtest.herokuapp.com/, 
and returns people who are listed as either living in London, or whose current coordinates are within 50 miles of London. 

## Solution

I have developed a simple REST API **_DWPUserFinder_** in Java and spring boot. It provides 1 REST endpoint with the ability 
to pass in a location and distance. Currently the location is defaulted to 50 miles of London.

##### DWPUserFinder  
- It provides capability to
    
    * Calls to https://dwp-techtest.herokuapp.com/
    * Displays list of Users with in the specified distance of a City 
    * Log an error to console when the service does not respond properly.
    * Show error message as API Response if there is an exception. 
    
###### UserController
Represents REST endpoint to return all users living in London or whose current coordinates are within 
 
If Distance is not provided, This API returns all users within 50 miles of a city 
 
 Method    | URL                                                | Description
 ----------| ---------------------------------------------------|----------------------------------------------------------------------------------------------
 **_GET_** | **/api/finder/city/{city}/users**                  | Returns all users within 50 miles of a city
 **_GET_** | **/api/finder/city/{city}/users?distance={200}**   | Returns all users within 200 miles of a London
 
Various cities such as London, Leeds, Manchester can be used for API request however there might be no user within the default 50 miles range. Hence request might need _distance_ parameter

##### How to access API
 You can use either SoapUI / Postman or a simple web browser to access the following APIs. 
 
 Method    | URL                                                                   | Description
 ----------| ----------------------------------------------------------------------|----------------------------------------------------------------------------------------------
 **_GET_** | **http://localhost:8282/api/finder/city/London/users**                | Returns all users within 50 miles of a London
 **_GET_** | **http://localhost:8282/api/finder/city/London/users?distance=200**   | Returns all users within 200 miles of a London
 
 
## Documentation
Swagger API Documentation is available for this application at

http://localhost:8282/swagger-ui.html

## Prerequisites
- Java 8
- Maven
- Lombok
- IntelliJ

### How to run using Maven

If you have maven installed, please run the following command in a terminal window from the _root_ folder:

    mvnw spring-boot:run

### How to run using java
If you do not have maven installed, please run the following command in a terminal window from the _root/target_ folder:

    java -jar dwp-user-finder-0.0.1-SNAPSHOT.jar

#### Error handling

Exception handler has been added to manage error handling i.e. in cases where a location is invalid or not found

#### Docker Image

Docker image for this application is available to download at Docker https://hub.docker.com/r/subswai/dwponlinetest  

    docker pull subswai/dwponlinetest

## Advance stage of Development
Due to time constraint, only MVP items are scoped as part of this development. There are additional tasks which 
should be considered for future development.

- Secure Rest APIs.
- Introducing integration pattern [EAI Pattern] to make integration more robust between DWPUserFinder and _dwp-techtest.herokuapp.com_
- Validation for API
- Build another API to get a specific user based on ID

 
