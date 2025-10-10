# UPGRADE - Proceso de actualización entre versiones

*Para actualizar de una versión a otra es suficiente con actualizar el WAR a la última versión. El siguiente listado
presenta aquellos cambios de versión en los que no es suficiente con actualizar y que requieren por parte del instalador
tener más cosas en cuenta. Si el cambio de versión engloba varios cambios de versión del listado, estos han de
ejecutarse en orden de más antiguo a más reciente.*

*De esta forma, si tuviéramos una instalación en una versión **A.B.C** y quisieramos actualizar a una versión
posterior **X.Y.Z** para la cual existan versiones anteriores que incluyan cambios listados en este documento, se deberá
realizar la actualización pasando por todas estas versiones antes de poder llegar a la versión deseada.*

*EJEMPLO: Queremos actualizar desde la versión 1.0.0 a la 3.0.0 y existe un cambio en la base de datos en la
actualización de la versión 1.0.0 a la 2.0.0.*

*Se deberá realizar primero la actualización de la versión 1.0.0 a la 2.0.0 y luego desde la 2.0.0 a la 3.0.0*


## 4.8.1 a 4.8.2-SNAPSHOT
* Es necesario ejecutar el script SQL contenido en la carpeta
  ```shell
  etc/changes-from-release/4.8.1/db/common-metadata/postgresql/20250806_add_columns_tb_operations.sql
  ```
  
* Esta versión depende de la versión 3.11.2-SNAPSHOT de eUsuarios. Por lo tanto, no se puede subir la versión 4.8.2-SNAPSHOT de metamac-statistical-operations  sin haber subido la versión 3.11.2-SNAPSHOT de eUsuarios y viceversa.
* Se debe resetear el schema registry para el topic OPERATION_PUBLICATIONS debido a que se añade el nuevo estado _PRE_PLANNING_
  
```shell 
  curl -X DELETE http://localhost:8081/subjects/OPERATION_PUBLICATIONS-value
  ````

* Se han de Borrar los mensajes existentes en el topic OPERATION_PUBLICATIONS. Para ello,

```shell 
 /servers/kafka/confluent/bin/kafka-configs --bootstrap-server localhost:19092 --entity-type topics --entity-name OPERATION_PUBLICATIONS --add-config retention.ms=100 --alter
 /servers/kafka/confluent/bin/kafka-configs --bootstrap-server localhost:19092 --entity-type topics --entity-name OPERATION_PUBLICATIONS --describe retention.ms
  ````

*  Esperar 1 minuto antes de volver a restaurar con la siguiente sentencia
```shell
 /servers/kafka/confluent/bin/kafka-configs --bootstrap-server localhost:19092 --entity-type topics --entity-name OPERATION_PUBLICATIONS --delete-config retention.ms --alter
````

## 4.6.1 a 4.7.0
Esta versión tiene como dependencia complementos-apps en su versión 8.13.0

## 4.4.0 a 4.5.0
•Se han realizado cambios en la base de datos PostgreSQL, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión.Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: etc/changes-from-release/4.4.0/db/

## 4.2.1 a 4.3.0
•Se han realizado cambios en la base de datos PostgreSQL, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión.Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: etc/changes-from-release/4.2.1/db

## 4.0.0 a 4.1.0
•Se han realizado cambios en la base de datos PostgreSQL, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión.Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: etc/changes-from-release/4.0.0/db 

## 3.5.1 a 3.5.2
•Se han realizado cambios en la base de datos PostgreSQL, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión.Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: etc/changes-from-release/3.4.0/db 


## 3.4.0 a 3.5.0
•Se han realizado cambios en la base de datos PostgreSQL, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión.Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: etc/changes-from-release/3.4.0/db 


## 3.3.0 a 3.3.1
* Se han realizado cambios en la base de datos PostgreSQL, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión.
 Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: [etc/changes-from-release/3.3.0/db](etc/changes-from-release/3.3.0/db) 

## 3.2.0 a 3.3.0
* Es necesario ejecutar el script SQL contenido en la carpeta
  ```shell
  etc/changes-from-release/3.2.0/db/common-metadata/postgresql/20221122_delete_metamac_statistical_operations_data_path.sql
  etc/changes-from-release/3.2.0/db/common-metadata/postgresql/20230413_deprecate_metamac_statistical_operations_dialect.sql
  ```

## 3.0.0 a 3.1.0
* Es necesario ejecutar el script SQL contenido en la carpeta
  ```shell
  etc/changes-from-release/3.0.0/db/common-metadata/postgresql/20221116_deprecate_metamac_statistical_operations_data_path.sql
  ```

* Es necesario ejecutar el script SQL contenido en la carpeta
  ```shell
  etc/changes-from-release/3.0.0/db/statistical-operations/postgresql/20220707_add-columns-to-tb_operations.sql
  etc/changes-from-release/3.0.0/db/statistical-operations/postgresql/20220816_add-column-gender-perspective-to-tb_operations.sql
  ```

## 2.6.1 a 3.0.0

* A partir de esta versión de la aplicación se elimina el soporte para bases de datos Oracle o Sql Server, siendo PostgreSQL la única base de datos con soporte.

## 2.5.1 a 2.6.0

* Es necesario ejecutar el script SQL contenido en la carpeta
  ```shell
  etc/changes-from-release/2.5.1/db/statistical-operations/$BD/20210708_add-column-stream-message-status-to-tb_operations.sql
  ```
  donde `$DBMS` se corresponde con el sistema de gestión de base de datos que esté utilizando.
* Se debe modificar el fichero logback-indicators-internal-web.xml para añadir la siguiente entrada justo después del
  inicio del tag configuration. La siguiente entrada configura un filtro a nivel de logs que evita que se emitan
  mensajes de logs duplicados de forma indefinida a los que la nueva versión de Kafka es propenso.
  ```xml
   <turboFilter class="org.siemac.edatos.core.common.util.ExpiringDuplicateMessageFilter">
      <allowedRepetitions>5</allowedRepetitions>
      <cacheSize>500</cacheSize>
      <expireAfterWriteSeconds>900</expireAfterWriteSeconds>
  </turboFilter>
  ```

## 0.0.0 a 2.5.1

* El proceso de actualizaciones entre versiones para versiones anteriores a la 1.7.1 está definido en "Metamac - Manual
  de instalación.doc"
