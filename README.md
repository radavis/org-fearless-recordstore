# org-fearless-recordstore

An example project for learning and understanding the Spring Data JPA library.

## Definitions

Java Persistence API (JPA)

> The Java ORM standard for storing, accessing, and managing Java objects in a
> relational database. - [javaworld](https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)

Spring Data JPA

> The goal of the Spring Data repository abstraction is to significantly reduce
> the amount of boilerplate code required to implement data access layers for
> various persistence stores. - [docs.spring.io](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories)

## Layers

![Spring Data JPA Layers](/assets/images/spring-data-jpa.png)

* Controller: Handles HTTP GET, POST, PUT/PATCH, and DELETE (and other) requests.
  - DispatcherServlet:
      * maps to the root, by default (e.g. - `GET /`)
      * catches all incoming HTTP requests
      * determines the 'request handler'
      * maps HTTP request path to a controller method
      * execute the controller method (invoke back-end _services_) -> return an object
      * marshall the object (e.g. - Object -> JSON, Object -> HTML)
      * return a HTTP response
  - `@Controller` annotated class
      * `RequestMapping("/resource", method=RequestMethod.{GET,POST,PUT,PATCH,DELETE})` annotated methods
* Service: holds the business logic
* Data Access Layer
  - Entity: The 'model' in MVC architecture. A class with the '@Entity' annotation describes a database table, objects of that class represent rows in that database table.
  - JPA Repository: Provides an `interface` for CRUDing Entities.
* Database Table: The 'persistence layer.'

## Spring "Stereotypes"

Annotating classes with a specific stereotype marks them as fulfilling a particular
role within a Spring Application. Spring autodetects and loads these annotated
classes.

- `@Component` - a Spring managed component. "Meta-annotations":
    * `@Service` - holds the "business logic" of the application.
    * `@Repository` - Data Access Object (DAO). Catches persistence-specific exceptions & rethrows.
    * `@Configuration` - Contain `@Bean` definition methods. (e.g. - How to initialize a particular )
    * `@Controller` - Handles HTTP requests, returns HTTP responses

## WTF is a Bean!?!?

> A bean is an object that is [...] managed by a Spring IoC container. [docs.spring.io](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-introduction)

Is a `@Component` also a `@Bean`?

## WTF is a Servlet!?!?  WTF is a Servlet Container!?!?

> IDK!

## Build a Spring Data JPA App

Install the `springboot` CLI tool. [docs][springboot-cli-macos-install]

```bash
# macOS
$ brew tap pivotal/tap
$ brew install springboot
```

View the options for generating a new Spring Boot app

```bash
$ spring init --list
```

Initialize a new Spring Boot application. [docs][springboot-cli-init-app]

```bash
$ spring init \
    --dependencies=web,data-jpa,mysql,lombok,flyway \
    --groupId=org.fearless \
    --type=gradle-project \
    recordstore
```

**Configure database**

```bash
# ~/.zprofile, or .wherever
alias load_dotenv='env $(grep -v '^#' .env | xargs)' $*
```

```bash
# .env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/recordstore_development?useUnicode=yes
SPRING_DATASOURCE_USERNAME=recordstore-admin
SPRING_DATASOURCE_PASSWORD=s3cR3+
```

```bash
$ load_dotenv ./bin/db_create  # see also ./bin/db_destroy
$ load_dotenv ./bin/db_info
```

**Add flyway plugin and dependency to `build.gradle`**

```bash
$ ./gradlew build --refresh-dependencies
$ ./gradlew tasks --all
```

**Add `./bin/db_create_migration` script**

```bash
$ ./bin/db_create_migration Create_Table_Album
```

```sql
-- src/main/resources/db/migration/VYYYYMMDDHHMMSS__Create_Table_Album.sql
-- Create_Table_Album

create table album (
  id serial,
  artist varchar(255) not null,
  title varchar(255) not null,
  year smallint not null
);
```

**Run Migration**

```bash
$ load_dotenv ./gradlew flywayMigrate --info
```

**Seed Albums**

```bash
$ ./bin/db_create_migration Seed_Albums
```

```sql
-- src/main/resources/db/migration/VYYYYMMDDHHMMSS__Seed_Albums.sql
-- Seed_Albums

insert into album(artist, title, year)
values
  ('Nirvana', 'Nevermind', 1991),
  ('REM', 'Automatic for the People', 1992),
  ('Dr. Dre', 'The Chronic', 1992);
```

**Run the Migration**

## A Typical `Application.java` with Spring Boot

```java
package com.example.myapplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // same as @Configuration @EnableAutoConfiguration
@ComponentScan  // find your beans automatically
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## What does `@Autowired` mean?


## `spring-boot-devtools`

* auto-restarts the application when files on the classpath change
* troubleshoot remote applications

## Serving Static Content

Use one of the following folders:

* `/static`
* `/public`
* `/resources`
* `/META-INF/resources`

Configure this via the `spring.resources.static-locations` property.

## Next Steps

* Create a Controller to Create, Read, Update, and Delete the Album resources.

## References

* [Introduction to the Java Persistence API (Java World)](https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)
* [Guide to Spring Data JPA (Stack Abuse)](https://stackabuse.com/guide-to-spring-data-jpa/)
* [Declare dependency versions as variables in `gradle.properties`](https://stackoverflow.com/a/58691504/2675670)
* [Structuring Your Code (Spring Boot)](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-structuring-your-code)
* [Testing in Spring Boot (Baledung)](https://www.baeldung.com/spring-boot-testing)
* [Top 100 Albums of the 1990s (Pitchfork)](https://pitchfork.com/features/lists-and-guides/5923-top-100-albums-of-the-1990s/?page=10)
* [Testing (Spring Boot)](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/spring-boot-features.html#boot-features-testing)
* [HTTP Request-Response Lifecycle in Spring (DZone)](https://dzone.com/articles/lifecycle-of-a-request-response-process-for-a-spri)
* [Spring Controller Request Mapping (Stack Tips)](https://www.stacktips.com/tutorials/spring/how-spring-controller-request-mapping-works-in-spring-mvc)
* [Spring Boot Best Practices](https://www.e4developer.com/2018/08/06/spring-boot-best-practices/)
* [GRASP object-oriented design](https://en.wikipedia.org/wiki/GRASP_(object-oriented_design))

[springboot-cli-macos-install]: https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-homebrew-cli-installation
[springboot-cli-init-app]: https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-cli.html#cli-init
