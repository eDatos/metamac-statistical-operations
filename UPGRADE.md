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
