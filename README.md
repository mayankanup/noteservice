# Steps for initial setup

## 1. Go to Spring Initializer (start.spring.io)
### a. Project = maven
### b. Language = Java
### c. Spring Boot = Latest non snapshot version (In my case it was 3.5.3)
### d. package = mayank.anup
### e. artifact = noteservice
### f. Add dependencies
#### i. Spring Web
#### ii. Spring Data JPA
#### iii. H2 DB (In memory low footprint db)
#### iv. Rest Repositories
### g. Packaging = Jar
### h. Java version - 24 (At the time)

## 2. Generate zip. Download it and unzip in your chosen folder

## 3. Compile
```
mvn clean install
```
If successful it will generate a target folder with noteservice-*.jar

## 4. Run
```
java -jar target\noteservice-*.jar
```
You should see log message on console saying Tomcat started on port 8080

## 5. Verify in browser
Goto http://localhost:8080/
You should see
```
{
  "_links" : {
    "profile" : {
      "href" : "http://localhost:8080/profile"
    }
  }
}