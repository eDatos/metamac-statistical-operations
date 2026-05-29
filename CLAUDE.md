# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

METAMAC Statistical Operations is an enterprise Java application for managing statistical operations metadata (operations, families, and instances). It's a multi-module Maven project with REST APIs, a GWT-based administrative UI, event-driven Kafka integration, and PostgreSQL persistence.

**Main branch for PRs**: `develop`

## Build & Development Commands

### Building the Project

> **IMPORTANT — Java version**: This project requires **JDK 1.8**. The machine has multiple JDKs installed
> (11, 21, 1.8). Always verify that `JAVA_HOME` points to the 1.8 installation before building:
> - JDK 1.8: `C:\Program Files\Java\jdk1.8.0_202`
>
> ```bash
> # Verify active Java version (must show 1.8.x)
> java -version
>
> # If wrong version, set JAVA_HOME before running Maven:
> export JAVA_HOME="C:/Program Files/Java/jdk1.8.0_202"
> export PATH="$JAVA_HOME/bin:$PATH"
> ```

```bash
# Build entire project (all modules)
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Build specific module
cd metamac-statistical-operations-core
mvn clean install

# Package WAR files for deployment
mvn clean package
```

### Running Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
cd metamac-statistical-operations-core
mvn test

# Run single test class
mvn test -Dtest=StatisticalOperationsServiceFacadeTest

# Run specific test method
mvn test -Dtest=StatisticalOperationsServiceFacadeTest#testFindOperationById
```

### GWT Development (Web UI)

```bash
# Compile GWT application
cd metamac-statistical-operations-web
mvn gwt:compile

# Run in GWT development mode (requires proper configuration)
mvn gwt:run
```

### Code Generation

The project uses Fornax Sculptor for generating domain model base classes and Avro for Kafka message schemas. These are generated during Maven build:

```bash
# Regenerate Sculptor-generated classes (domain + service interfaces)
cd metamac-statistical-operations-core
mvn clean generate-sources

# Regenerate Avro schemas (Kafka module)
cd metamac-statistical-operations-kafka
mvn clean generate-sources
```

## Module Architecture

The project consists of 7 modules organized by layer and responsibility:

### Core Layer
- **metamac-statistical-operations-parent**: Parent POM with shared dependencies and versions
- **metamac-statistical-operations-kafka**: Kafka Avro schemas (15 schemas) for event publishing
- **metamac-statistical-operations-core**: Business logic, domain entities, repositories, services

### REST API Layer
- **metamac-statistical-operations-rest-internal-impl**: Internal API implementation (administrative CRUD)
- **metamac-statistical-operations-rest-external-impl**: External API implementation (public read-only)

> **Note**: REST API contracts (JAXB types) live in external JARs (`metamac-statistical-operations-rest-internal-api` and `metamac-statistical-operations-rest-external-api`), not in this project.

### Web UI Layer
- **metamac-statistical-operations-web**: GWT-based administrative web application (WAR) — also hosts Kafka stream infrastructure
- **metamac-statistical-operations-external-web**: Public-facing web application with Swagger UI (WAR)

### Dependency Flow
```
Core (domain + services)
  ↓
REST API Implementation (facades + mappers)
  ↓
Web Applications (GWT UI + REST consumers)

Kafka (parallel integration for events)
```

## Technology Stack

### Backend
- **Framework**: Spring 3.0 (XML configuration)
- **ORM**: Hibernate + JPA annotations
- **Database**: PostgreSQL with manual SQL migration scripts
- **Code Generation**: Fornax Sculptor (domain model), Avro (Kafka)
- **REST**: Apache CXF with JAX-RS
- **Messaging**: Apache Kafka with Avro serialization

### Frontend
- **Framework**: Google Web Toolkit (GWT) 2.5+
- **UI Components**: SmartGWT
- **Architecture**: MVP pattern via GWT Platform (GWTP)
- **Dependency Injection**: Google GIN (GWT Injection)

### Testing
- **Unit**: JUnit 4.x
- **Mocking**: Mockito
- **Database**: DBUnit for fixtures
- **Integration**: Spring Test framework

## Domain Model

The core domain consists of three main entities:

```
Family
  └── Operation (many operations per family)
        └── Instance (many instances per operation)
```

All three entities share audit fields (CREATED_BY, LAST_UPDATED, etc.) and have:
- **PROC_STATUS**: workflow state (DRAFT → PUBLISH_INTERNALLY → PUBLISH_EXTERNALLY)
- **URN**: unique resource name
- **InternationalString**: multi-language fields (title, description, etc.)
- **ExternalItem**: links to SRM, Common Metadata

### Key Domain Patterns
- **No versioning**: Unlike Statistical Resources, operations have no version history — entities are updated in place
- **Lifecycle States**: DRAFT → PUBLISH_INTERNALLY → PUBLISH_EXTERNALLY
- **Internationalization**: InternationalString entity with Translation entries for multi-language support (es, ca, en, pt)
- **External References**: ExternalItem for linking to SRM, Common Metadata
- **Code Generation**: Base classes (*Base.java) are auto-generated by Sculptor; extend them in src/main/java

## Database Management

### Schema Location
- Creation scripts: `etc/db/statistical-operations/postgresql/01-create/`
- Drop scripts: `etc/db/statistical-operations/postgresql/02-drop/`

### Migration Process
1. Database changes are applied manually via SQL scripts
2. Scripts are versioned in `etc/changes-from-release/{version}/db/`
3. Follow the upgrade path in `UPGRADE.md` when moving between versions

### Key Tables
- `TB_FAMILIES`
- `TB_OPERATIONS`
- `TB_INSTANCES`
- `TB_INTERNATIONAL_STRINGS`, `TB_TRANSLATIONS`
- `TB_EXTERNAL_ITEMS`
- `TB_LIS_SURVEY_TYPES`, `TB_LIS_OFFICIALITY_TYPES`, `TB_LIS_INSTANCE_TYPES`
- `TB_LIS_COLL_METHODS`, `TB_LIS_COSTS`, `TB_LIS_SURVEY_SOURCES`

### Sequences
Primary keys use a custom sequence table (not native PostgreSQL sequences):
- Table `TB_SEQUENCES` with rows per entity type: `SURVEY_TYPES`, `INSTANCE_TYPES`, `COLL_METHODS`, `COSTS`, `SURVEY_SOURCES`, `OFFICIALITY_TYPES`, `OPERATION_URLS`
- Helper function: `GET_NEXT_SEQUENCE_VALUE(sequence_name_in)`

## REST API Design

### Internal API (Administrative)
- **Base Path**: `/operations-internal/`
- **Purpose**: Full CRUD operations, lifecycle management, administrative tasks
- **Facade**: `StatisticalOperationsRestInternalFacadeV10Impl`
- **Authentication**: Required via METAMAC SSO
- **API contract types**: External JAR `metamac-statistical-operations-rest-internal-api`

### External API (Public)
- **Base Path**: `/operations/`
- **Purpose**: Read-only access to published resources
- **Facade**: `StatisticalOperationsRestExternalFacadeV10Impl`
- **Authentication**: Public (no auth required)
- **API contract types**: External JAR `metamac-statistical-operations-rest-external-api`

### API Patterns
- JAXB-based: types are pre-generated from XSD in external API JARs
- Mappers: Domain → Do2RestInternalMapperV10 / Do2RestExternalMapperV10 → REST DTO → JSON/XML
- Versioned endpoints: API v1.0

## Kafka Integration

### Avro Schemas
Located in `metamac-statistical-operations-kafka/src/main/resources/avro-schemas/`:
- Core schemas: `datetime.avsc`, `international-string.avsc`, `external-item.avsc`
- Domain schemas: `operation.avsc`, `family.avsc`, `instance.avsc`
- Enum schemas: `proc-status.avsc`, `status.avsc`, `instance-type.avsc`, `officiality-type.avsc`, `survey-source.avsc`, `survey-type.avsc`, `coll-method.avsc`, `cost.avsc`
- Migration: `edatos-migration-status.avsc`

### Event Publishing
- **Service**: `StreamMessagingServiceImpl`
- **Topic**: Configured via `configurationService.retrieveKafkaTopicOperationsPublication()` (set in environment.xml)
- **Trigger Points**: Operation lifecycle transitions (publish internally, publish externally, update)

### Kafka Commands
```bash
# Delete schema from registry
curl -X DELETE http://localhost:8081/subjects/OPERATIONS_PUBLICATIONS-value

# Clear topic messages (set retention to 100ms, wait, restore)
/servers/kafka/confluent/bin/kafka-configs --bootstrap-server localhost:19092 \
  --entity-type topics --entity-name OPERATIONS_PUBLICATIONS \
  --add-config retention.ms=100 --alter

# After 1 minute, restore retention
/servers/kafka/confluent/bin/kafka-configs --bootstrap-server localhost:19092 \
  --entity-type topics --entity-name OPERATIONS_PUBLICATIONS \
  --delete-config retention.ms --alter
```

## Configuration

### Spring Configuration
- **Core**: `metamac-statistical-operations-core/src/main/resources/spring/statistical-operations/applicationContext.xml`
- **REST Internal**: `metamac-statistical-operations-rest-internal-impl/src/main/resources/spring/statistical-operations-rest-internal/applicationContext.xml`
- **REST External**: `metamac-statistical-operations-rest-external-impl/src/main/resources/spring/statistical-operations-rest-external/applicationContext.xml`

### Environment Configuration
- **Location**: `classpath:metamac/environment.xml` (can override via environment variable)
- **Contains**:
  - Database connection settings
  - External service endpoints (SRM, Common Metadata)
  - Kafka broker configuration and topic name
  - Feature flags

### External Service Dependencies
The application integrates with:
- **SRM** (Structural Resources Manager): Subject areas, classifications
- **Common Metadata**: Shared metadata definitions
- **Notifications**: Event notification system

## Important Development Patterns

### Generated vs Manual Code
- **Generated**: `src/generated/java` - Do not modify! Regenerated by Sculptor on build
- **Manual**: `src/main/java` - Extend generated base classes here
- **Example**: `OperationBase` (generated) → `Operation` (manual extensions)

### Service Layer Pattern
- Services are Spring beans
- Implement business logic and orchestration
- Call repositories for persistence
- Publish events to Kafka after state transitions
- Validate inputs using validator classes

### Mapper Pattern
Two-tier REST mapping (no separate Do layer — domain objects used directly):
1. **Domain entities** (JPA entities in core)
2. **REST DTOs** (JAXB types from external API JARs)

Mappers: `Do2RestInternalMapperV10`, `Do2RestExternalMapperV10`, `RestCriteria2SculptorCriteriaMapper`

### Testing Pattern
- Base test classes: `*BaseTest` with common setup
- DBUnit fixtures: `src/test/resources/dbunit/` (XML files)
  - `StatisticalOperationsBaseServiceTest.xml`
  - `StatisticalOperationsServiceFacadeTest.xml`
  - `StatisticalOperationsListsServiceTest.xml`
  - `StreamMessagingServiceTest.xml` / `StreamMessagingServiceFacadeTest.xml`
- Test Spring context: `applicationContext-test.xml`
- Transaction rollback: Tests run in transactions, rolled back after

### Internationalization
All user-facing text uses `InternationalString`:
```java
InternationalString title = new InternationalString();
title.addLocalizedString(new LocalizedString("es", "Título en español"));
title.addLocalizedString(new LocalizedString("en", "Title in English"));
```

### Catalan properties — apostrophe escaping

`MessageFormat.format()` treats `'` as an escape character (consumed, not displayed).
In this module, `NoticesRestInternalFacadeImpl` uses `LocaleUtil` only — **no MessageFormat applied to notifications**.
Rule:

| File location | Processing path | Use |
|---|---|---|
| `messages-*_ca.properties` (all prefixes) | No MessageFormat anywhere (LocaleUtil for notices; `String.replace()` for exceptions) | `'` |
| `*Messages_ca.properties` in `src/main/java/` | GWT `@Messages` interface — MessageFormat always applied | `''` |

Example: `S'ha actualitzat {0}` (messages file) vs `S''ha produït {0}` (GWT Messages file).

## Code Locations

### Finding Key Components
- **Domain entities**: `metamac-statistical-operations-core/src/main/java/org/siemac/metamac/statistical/operations/core/domain/`
- **Services**: `metamac-statistical-operations-core/src/main/java/org/siemac/metamac/statistical/operations/core/serviceimpl/`
- **REST external endpoints**: `metamac-statistical-operations-rest-external-impl/src/main/java/org/siemac/metamac/statistical_operations/rest/external/v1_0/service/`
- **REST internal endpoints**: `metamac-statistical-operations-rest-internal-impl/src/main/java/org/siemac/metamac/statistical_operations/rest/internal/v1_0/service/`
- **GWT UI**: `metamac-statistical-operations-web/src/main/java/org/siemac/metamac/statistical/operations/web/client/`
- **Kafka Avro schemas**: `metamac-statistical-operations-kafka/src/main/resources/avro-schemas/`
- **Kafka stream infrastructure**: `metamac-statistical-operations-core/src/main/java/org/siemac/metamac/statistical/operations/web/server/stream/`
- **Tests**: `*/src/test/java/` (mirrors main source structure)

### Common Files to Edit
- **Adding new service method**: `metamac-statistical-operations-core/src/main/java/org/siemac/metamac/statistical/operations/core/serviceimpl/StatisticalOperationsServiceFacadeImpl.java`
- **Adding REST endpoint**: REST impl modules under `v1_0/service/` package
- **Updating domain model**: btdesign files (then regenerate) or extend generated entities
- **Adding Kafka event field**: Modify `.avsc` file in kafka module, regenerate sources

## Docker and Deployment

### Docker Configuration
- **Location**: `dockerfiles/`
- **External web**: `dockerfiles/statistical-operations-external/`
- **Internal web**: `dockerfiles/statistical-operations-internal/`
- **Kafka Environment**: Hosted in parent project `metamac-parent/etc/docker`

### Build Artifacts
- **WARs**: `metamac-statistical-operations-web/target/*.war`, `metamac-statistical-operations-external-web/target/*.war`
- **JARs**: All other modules produce JARs for library reuse

## Common Development Workflows

### Adding a New Field to Operation
1. Update database: Create SQL script in `etc/changes-from-release/`
2. Update entity: Extend `Operation` class (if not in generated base) or modify btdesign + regenerate
3. Update Avro schema: Modify `operation.avsc` and regenerate
4. Update mappers: Add field to `Do2RestInternalMapperV10Impl`, `Do2RestExternalMapperV10Impl`
5. Add tests: Update DBUnit fixtures and add test cases
6. Update Kafka schema registry: Delete old schema, republish

### Publishing an Operation
Lifecycle: DRAFT → PUBLISH_INTERNALLY → PUBLISH_EXTERNALLY

Code locations:
- `StatisticalOperationsServiceFacadeImpl.publishInternallyOperation()`
- `StatisticalOperationsServiceFacadeImpl.publishExternallyOperation()`
- Publishes Kafka event via `StreamMessagingServiceImpl`
- Updates `TB_OPERATIONS.PROC_STATUS`

### Updating External Service Integration
External service clients in `metamac-statistical-operations-rest-*-impl`:
- `SrmRestExternalFacade` / `SrmRestInternalFacade`: SRM integration
- `CommonMetadataRestExternalFacade`: Common metadata

Update endpoint configuration in `environment.xml`

### Nomenclatura y Codigo

- **Idioma del codigo**: SIEMPRE en ingles
  - Clases, metodos, variables, constantes, comentarios
  - Ejemplo: `OperationService`, `findActiveOperations()`, `MAX_CODE_LENGTH`

- **CamelCase**: Para variables y metodos
  ```java
  String operationCode;
  boolean isActiveOperation;
  void assignFamilyToOperation(Long operationId, Long familyId);
  ```

### Restricciones Java 7

Este es un proyecto **legacy** - mantener compatibilidad Java 7:

#### No Usar (Java 8+)

```java
// No lambdas
operations.forEach(o -> LOG.info(o.getCode()));

// No Streams
List<String> codes = operations.stream()
    .map(Operation::getCode)
    .collect(Collectors.toList());

// No Optional
Optional<Operation> op = operationRepository.findById(id);

// No LocalDate/LocalDateTime
LocalDate now = LocalDate.now();

// No method references
operations.sort(Comparator.comparing(Operation::getCode));
```

#### Usar (Java 7)

```java
// Bucles tradicionales
for (Operation operation : operations) {
    LOG.info(operation.getCode());
}

// Iteracion manual para mapeo
List<String> codes = new ArrayList<String>();
for (Operation operation : operations) {
    codes.add(operation.getCode());
}

// Null checks tradicionales
Operation operation = operationRepository.findById(id);
if (operation != null) {
    // ...
}

// Joda Time
DateTime now = new DateTime();
DateTime tomorrow = now.plusDays(1);

// Comparadores anonimos
Collections.sort(operations, new Comparator<Operation>() {
    @Override
    public int compare(Operation o1, Operation o2) {
        return o1.getCode().compareTo(o2.getCode());
    }
});
```

#### Inferencia de Tipos en Genericos

**Depende del modulo**:

```java
// Frontend (GWT) - NO PERMITIDA inferencia (limitacion GWT 2.3.0)
Map<String, String> map = new LinkedHashMap<String, String>();
List<OperationDto> ops = new ArrayList<OperationDto>();

// Backend/REST - SI PERMITIDA inferencia (Java 7)
Map<String, String> map = new LinkedHashMap<>();
List<OperationDto> ops = new ArrayList<>();
```

### Joda Time (Fechas)

**NO usar** `java.time.*` (Java 8+)

```java
// Usar Joda Time
import org.joda.time.DateTime;

DateTime createdDate = new DateTime();
DateTime yesterday = createdDate.minusDays(1);

// Persistencia JPA/Hibernate
@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
@Column(name = "CREATED_DATE")
private DateTime createdDate;
```

## Seguridad

### Autenticacion
- **CAS 5.3.12.1**: Central Authentication Service
- **METAMAC SSO 4.11.2**: Single Sign-On personalizado

### Autorizacion

**3 niveles de validacion**:

1. **Frontend (Web)**:
   - Ocultar/deshabilitar acciones segun permisos
   - Mejora UX pero NO es seguridad real

2. **Backend (Core)**:
   - Validacion REAL antes de ejecutar operaciones
   - Custom interceptors y validators

3. **API REST**:
   - Endpoint de permisos para otros sistemas
   - Cache agresivo para performance
