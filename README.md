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

## References

* [Introduction to the Java Persistence API (Java World)](https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)
* [Guide to Spring Data JPA (Stack Abuse)](https://stackabuse.com/guide-to-spring-data-jpa/)
* [Declare dependency versions as variables in `gradle.properties`](https://stackoverflow.com/a/58691504/2675670)
* [Structuring Your Code (Spring Boot)](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-structuring-your-code)
* [Testing in Spring Boot (Baledung)](https://www.baeldung.com/spring-boot-testing)
* [Top 100 Albums of the 1990s (Pitchfork)](https://pitchfork.com/features/lists-and-guides/5923-top-100-albums-of-the-1990s/?page=10)
* [Testing (Spring Boot)](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/spring-boot-features.html#boot-features-testing)

[springboot-cli-macos-install]: https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-homebrew-cli-installation
[springboot-cli-init-app]: https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-cli.html#cli-init
