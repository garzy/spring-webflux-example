# Spring Webflux REST Service Example with SQL Database

_It's only an example of Spring-Webflux usage_

## What has been done here?

1. CSV Loading file with [OpenCSV](http://opencsv.sourceforge.net/)
2. Auto-mapping of loaded CSV data to model entities with [MapStruct](https://mapstruct.org/)
3. SQL H2 'in-memory' database update and sql queries of loaded CSV data through reactive way using [R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
4. Reactive REST Service with [Spring Webflux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
5. Auto-generation of entity getters/setters/constructor... with [Lombok](https://projectlombok.org/)
6. Some unit tests and integration tests with [Junit5](https://junit.org/junit5/), [Assertj](https://joel-costigliola.github.io/assertj/), [Mockito](https://site.mockito.org/), [reactor-test](https://projectreactor.io/docs/core/release/reference/#testing) and [spring-test](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html)


