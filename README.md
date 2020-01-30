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

* Controller
* Service
* Data Access Layer
  - Entity: The 'model' in MVC architecture. A class with the '@Entity' annotation describes a database table, objects of that class represent rows in that database table.
  - JPA Repository: Provides an `interface` for CRUDing Entities.
* Database Table: The 'persistence layer.'

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

## References

[springboot-cli-macos-install]: https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-homebrew-cli-installation
[springboot-cli-init-app]: https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-cli.html#cli-init
