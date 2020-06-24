# DemoConsoleApplication
This is a project with the implementation of a simple REST API with its basic methods (GET, POST, PUT, DELETE) using a component based architecture (best practices and design principles with Driver Domain Design)

Technologies used: Spring Boot and HATEOAS for API REST, JPA in persistence, Spring Rest Docs for API documentation, and lombok. 
Persisting traces in mongodb.

## Contenido
### Step 1 Dependencies' injection 
 - Adding the first project dependencies
 - Adding services and repositories
 - Injection of dependency by annotations such as: @SpringBootService, @Service, @Repository, @Autowired, @Qualifer
 
 ### Step 2 Spring MVC
  - Adding controller class and work with annotations as: @Cotroller, @GetMapping, @RequestParam
  - Using the Thymeleaf dependency to develop HTML web pages. Create HTML page
  - Create DemoWebApplication and annotate it as: @SpringBootApplication to run the project

 ### Step 3 Spring Data JPA
  - Adding the dependencies for project of JPA
  - Additing the classes of the model. Annotation used in model classes: @Entity, @Id, @Column
  - Setting access to data at the file: application.properties
  - Work with JpaRepository
  - Implement `List student` and others
  
 ### Step 4 Implementation business logical
 - Implementation class following DDD principles
  
 ### Step 5 REST Services
  - Implement RestDemoController and annotate it as @RestController
  - Install <a href="https://www.postman.com/downloads/" target="_blank">**Postman**</a> 
  - Work with methods as GET and POST and JSON in Postman.
  - Work with Request
    
 ### Step 6 Spring HATEOAS
  - Add model request and model response, and assembler. Add links.
     
## Installation
   - All the `code` required to get started. Clone: `https://github.com/grleyvaj/DDD_SpringBoot_APIRest.git`
   - You will need to define the `JAVA_HOME` and `MAVEN_HOME` environment variables to deploy this project