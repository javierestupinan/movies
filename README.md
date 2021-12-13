# MOVIES PROJECT
project for a test interview

## How to deploy this project:

* It's need to update the following parameters in application.properties, they are going to be sended in the email.
```
spring.datasource.url=#
spring.datasource.username=#
spring.datasource.password=#
```
* it's a springboot project with maven.<br />
>* make sure you have installed a version of java 11 and also you need to have the JAVA_HOME with this version other case it won't work.<br />
>* make sure you have maven install to build the project.<br />

### Then you can follow these steps:<br />
1- Get the project from the git repository: `https://github.com/javierestupinan/movies`.<br />
2- Once you have the project, go into the main folder and run "mvn clean install"<br />
3- Now you can run the project runing following command "mvn spring-boot:run"<br />
<br /><br />
the project will be running in localhost, It also contains Swagger so you can test the API in following URL: http://localhost:8080/swagger-ui.html

