# org-fearless-recordstore

An example project for learning and understanding the Spring Data JPA library.

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

## Next Steps

* Create a Controller to Create, Read, Update, and Delete the Album resources.

## Resources

* [Declare dependency versions as variables in `gradle.properties`](https://stackoverflow.com/a/58691504/2675670)
* [Top 100 Albums of the 1990s (Pitchfork)](https://pitchfork.com/features/lists-and-guides/5923-top-100-albums-of-the-1990s/?page=10)
* [Spring Controller Request Mapping (Stack Tips)](https://www.stacktips.com/tutorials/spring/how-spring-controller-request-mapping-works-in-spring-mvc)

[springboot-cli-macos-install]: https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-homebrew-cli-installation
[springboot-cli-init-app]: https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-cli.html#cli-init
