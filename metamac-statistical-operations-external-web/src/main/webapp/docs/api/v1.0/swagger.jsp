<%@page import="org.siemac.metamac.core.common.util.swagger.SwaggerUtils"%>
<%@page pageEncoding="UTF-8"%>
{
   "swagger":"2.0",
   "info":{
      "description":"Las operaciones estadísticas son la unidad básica de planificación de la actividad estadística. Todos los recursos que se publican guardan relación de manera directa o indirecta con alguna operación. Esta API permite consultar el inventario de operaciones estadísticas, las diferencias que existen entre las diferentes realizaciones de una misma operación estadística (instancias) y su agrupación en familias. Asi por ejemplo tenemos que el \"Censo de Población y Viviendas\" es una operación estadística de la que podremos encontrar distintas realizaciones o instanacias (Censo 2011, Censo 2001, Censo 1991, etc) y que podría agruparse con otras operaciones dentro de una famlia \"Estadísticas poblacionales\". Los datos de las operaciones estadísticas y de sus instancias están armonizados con la propuesta de Eurostat para el Sistema Estadístico Europeo y definida en el documento EURO-SDMX Metadata Structure (release 4, December 2014)",
      "version":"1.0",
      "title":"API de operaciones estadísticas v1.0"
   },
   "host":"<%=SwaggerUtils.getApiBaseURLForSwagger()%>",
   "schemes":[

   ],
   "tags":[
      {
         "name":"Familias de operaciones",
         "description":""
      },
      {
         "name":"Operaciones estadísticas",
         "description":""
      },
      {
         "name":"Tablas de valores auxiliares",
         "description":""
      }
   ],
   "definitions":{
      "ClassSystems":{
         "type":"object",
         "title":"ClassSystems",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "classSystem":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Clasificaciones usadas en la instancia de la operación estadística"
      },
      "CollMethods":{
         "type":"object",
         "title":"CollMethods",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "collMethod":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Item"
                  }
               }
            }
         ],
         "description":"Valores aceptados para los métodos de recolección de los datos"
      },
      "Costs":{
         "type":"object",
         "title":"Costs",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "cost":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Item"
                  }
               }
            }
         ],
         "description":"Valores aceptados para los tipos de costes asociados a la operación"
      },
      "DataSharings":{
         "type":"object",
         "title":"DataSharings",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "dataSharing":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/InternationalString"
                  }
               }
            }
         ],
         "description":"Acuerdos y convenios existentes entre organizaciones productoras de datos para la coordinación y compartición de los mismos. "
      },
      "Families":{
         "type":"object",
         "title":"Families",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "family":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Listado de familias en las que se agrupan las diferentes operaciones estadísticas"
      },
      "Family":{
         "type":"object",
         "title":"Family",
         "allOf":[
            {
               "properties":{
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso",
                     "type":"string"
                  },
                  "acronym":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Acrónimo de la familia de operaciones",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "childLinks":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Recurso de la API al que se puede acceder desde el recurso actual",
                     "$ref":"#/definitions/ChildLinks"
                  },
                  "description":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de la familia de operaciones",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "id":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificador del recurso",
                     "type":"string"
                  },
                  "inventoryDate":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Fecha en la que se hace pública la familia de operaciones por primera vez",
                     "type":"string"
                  },
                  "name":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Nombre de la familia de operaciones",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "parentLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enlace al recurso padre de la API",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "selfLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enlace al propio recurso",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "urn":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"urn del recurso",
                     "type":"string"
                  }
               }
            }
         ],
         "description":"Familia de operaciones estadísticas"
      },
      "FreqColls":{
         "type":"object",
         "title":"FreqColls",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "freqColl":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Frecuencias de recolección de los datos"
      },
      "GeographicGranularities":{
         "type":"object",
         "title":"GeographicGranularities",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "geographicGranularity":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Granularidades geográficas"
      },
      "InformationSuppliers":{
         "type":"object",
         "title":"InformationSuppliers",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "informationSupplier":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Organizaciones obligadas a suministrar información para la elaboración de la operación estadística"
      },
      "Instance":{
         "type":"object",
         "title":"Instance",
         "allOf":[
            {
               "properties":{
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso",
                     "type":"string"
                  },
                  "accuracyOverall":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Evaluación de la exactitud de un conjunto de datos o de un dominio de análisis. Proporciona un resumen de las principales fuentes de error y una evaluación de la posibilidad de sesgo (signo y orden de magnitud ) para cada indicador clave en términos cuantitativos o cualitativos",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "acronym":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Acrónimo de la instancia de la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "adjustment":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de los procedimientos estadísticos utilizados para el ajuste de las series de datos (como métodos de ajuste estacional, descomposición de series de tiempo u otros métodos similares)",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "basePeriod":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Período utilizado como base de un número de índice, o al que se refiere una serie constante",
                     "type":"string"
                  },
                  "childLinks":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Recurso de la API al que se puede acceder desde el recurso actual",
                     "$ref":"#/definitions/ChildLinks"
                  },
                  "classSystems":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación de las clasificaciones utilizadas en la instancia de la operación. Las clasificaciones identificadas se vinculan con las distribuidas en la API de recursos estructurales de e-Semántica",
                     "$ref":"#/definitions/ClassSystems"
                  },
                  "classSystemsDescription":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enumeración y descripción de las clasificacoines utilizadas",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "coherInternal":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción del grado de coherencia de los datos dentro de la instancia estadísticas y con los de otras instancias de la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "coherXDom":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción del grado de coherencia de los datos con los de otras operaciones estadísticas del mismo dominio de análisis",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "collMethod":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Método principal de recogida de datos",
                     "$ref":"#/definitions/Item"
                  },
                  "comment":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Observaciones y notas sobre la instancia estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "completeness":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de la medida en que todas los datos que se necesitan son aportados por la instancia estadística. Proporciona información sobre la exhaustividad en comparación con los reglamentos y directrices pertinentes",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "dataCompilation":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción del proceso de recolección y tratamiento de datos (por ejemplo: método de muestreo, validación e imputación, tratamiento de la falta de respuesta, ponderación y calibración, uso de modelos, etc. )",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "dataDescription":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Describe, de una manera fácilmente comprensible, los principales datos e indicadores difundidos. Esta breve descripción debe ser entendida inmediatamente y fácilmente por los usuarios",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "dataValidation":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de los procedimientos de comprobación y validación de los datos recolectados",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "docMethod":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción metodológica y referencias a documentos metodológicos disponibles. Indicar la disponibilidad de importantes documentos metodológicos, documentos de síntesis u otros manuales importantes",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "freqColls":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Frecuencia de recogida de los datos (mensual, trimestral, anual, bianual, quinquenal, cada 10 años, etc.)",
                     "$ref":"#/definitions/FreqColls"
                  },
                  "geographicComparability":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción del grado en que las estadísticas son comparables entre zonas geográficas",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "geographicGranularity":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Nivel más bajo de agregación territorial utilizado en la difusión de los datos",
                     "$ref":"#/definitions/GeographicGranularities"
                  },
                  "id":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificador del recurso",
                     "type":"string"
                  },
                  "informationSuppliers":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Entidades obligadas a suministrar información",
                     "$ref":"#/definitions/InformationSuppliers"
                  },
                  "inventoryDate":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Fecha en la que se hace pública la instancia estadística por primera vez",
                     "type":"string"
                  },
                  "measures":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Conceptos de medida utilizados en la instancia de la operación",
                     "$ref":"#/definitions/Measures"
                  },
                  "name":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Nombre de la instancia de la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "nonsamplingErr":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción y medida de los errores ajenos al muestreo",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "parentLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enlace al recurso padre de la API",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "predecessor":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación de la instancia de operación predecesora, si la hubiera",
                     "$ref":"#/definitions/Resource"
                  },
                  "punctuality":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción, medida y explicación del lapso de tiempo entre la fecha de difusión real de los datos y la fecha programada",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "qualityAssmnt":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Valoración global de la calidad de los datos",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "qualityAssure":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de las acciones para asegurar la calidad de los datos",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "qualityDoc":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación y descripción de la documentación sobre los procedimientos aplicados en la gestión y evaluación de la calidad de los datos",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "samplingErr":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción y medida de los errores de muestreo",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "selfLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enlace al propio recurso",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "statConcDefs":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación de los conceptos utilizados en la instancia de la operación. Los conceptos identificados se vinculan con los distribuidos en la API de recursos estructurales de e-Semántica",
                     "$ref":"#/definitions/StatConcDefs"
                  },
                  "statConcDefsDescription":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enumeración y descripción de los conceptos y definiciones utilizados",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "statisticalOperation":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación de la operación estadística a la que se asocia la instancia",
                     "$ref":"#/definitions/Resource"
                  },
                  "statisticalOperationSource":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Forma de recogida de datos",
                     "$ref":"#/definitions/Item"
                  },
                  "statisticalPopulation":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de la población objetivo de análisis",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "statisticalUnits":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación de las unidades de análisis",
                     "$ref":"#/definitions/StatisticalUnits"
                  },
                  "successor":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación de la instancia de operación sucesora, si la hubiera",
                     "$ref":"#/definitions/Resource"
                  },
                  "temporalComparability":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción del grado en que las estadísticas son comparables o pueden conciliarse a lo largo del tiempo. Información sobre la longitud de las series comparables, los períodos de referencia en los que se producen roturas de la serie, las razones de las pausas y los tratamientos asociados",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "temporalGranularity":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificación de los periodos de tiempo o fechas de referencia mínima para los datos publicados",
                     "$ref":"#/definitions/TemporalGranularities"
                  },
                  "timeliness":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción del tiempo transcurrido entre la difusión de los datos y el evento o fenómeno que describen",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "urn":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"urn del recurso",
                     "type":"string"
                  },
                  "userNeeds":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de los usuarios y sus respectivas necesidades con respecto a los datos de la instancia estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "userSat":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción de las medidas para determinar la satisfacción de los usuarios",
                     "$ref":"#/definitions/InternationalString"
                  }
               }
            }
         ],
         "description":"La instancia de operación estadística es cada una de las realizaciones de una misma operación estadística. Las instancias pueden ser de dos tipos: SERIE o SECCIÓN. Las instancias de tipo serie proveen información de una serie temporal completa y la instancia acaba cuando se produce una rotura de la serie. Las instancias de tipo sección proveen información de un único instante de tiempo"
      },
      "InstanceTypes":{
         "type":"object",
         "title":"InstanceTypes",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "instanceType":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Item"
                  }
               }
            }
         ],
         "description":"Tipos de instancias"
      },
      "Instances":{
         "type":"object",
         "title":"Instances",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "instance":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Listados de instancias de operaciones estadísticas"
      },
      "LegalActs":{
         "type":"object",
         "title":"LegalActs",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "legalActs":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/InternationalString"
                  }
               }
            }
         ],
         "description":"Leyes u otro tipo de convenios (formales o informales) que otorgan reponsabilidad y autoridad al organismo para la recolección, procesado y diseminación de la estadística"
      },
      "Measures":{
         "type":"object",
         "title":"Measures",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "measure":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Unidades en las que se miden los datos"
      },
      "OfficialityTypes":{
         "type":"object",
         "title":"OfficialityTypes",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "officialityType":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Item"
                  }
               }
            }
         ],
         "description":"Tipos de oficialidad que se le puede asignar a una operación estadística"
      },
      "Operation":{
         "type":"object",
         "title":"Operation",
         "allOf":[
            {
               "properties":{
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":"Tipo del recurso"
                     },
                     "description":"",
                     "type":"string"
                  },
                  "acronym":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Acrónimo de la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "childLinks":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Recurso de la API al que se puede acceder desde el recurso actual",
                     "$ref":"#/definitions/ChildLinks"
                  },
                  "notes":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Observaciones y notas sobre la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "confidentialityDataTreatment":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Reglas utilizadas en el tratamiento de los datos con el fin de garantizar el secreto estadístico",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "confidentialityPolicy":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Medidas legislativas u otros procedimientos formales que regulan la divulgación de datos sujetos a secreto estadístico",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "contact":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Organización de contacto para los datos o metadatos de la operación estadística",
                     "$ref":"#/definitions/Resource"
                  },
                  "currentInstance":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Instancia actual, normalmente será la última instancia pública",
                     "$ref":"#/definitions/Resource"
                  },
                  "currentlyActive":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Indica si la operación estadística está actualmente en uso o no",
                     "type":"boolean"
                  },
                  "dataSharings":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Mecanismos o procedimientos asociados a la operación estadística para el intercambio de datos y la coordinación entre los organismos vinculados a la misma",
                     "$ref":"#/definitions/DataSharings"
                  },
                  "description":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción detallada de la operación detallada y de los objetivos específicos que se pretenden alcanzar",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "id":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Identificador del recurso",
                     "type":"string"
                  },
                  "indicatorSystem":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Indica si una operación estadística genera un sistema de indicadores. La información del sistema de indicadores asociado se puede consultar en la API de e-Indicadores",
                     "type":"boolean"
                  },
                  "inventoryDate":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Fecha en la que se hace pública la operación estadística por primera vez",
                     "type":"string"
                  },
                  "legalActs":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Indica que la actividad estadística está cubierta por un acto jurídico o por algún acuerdo formal. Se relacionan los actos jurídicos o acuerdos que asignen responsabilidad o autoridad de una agencia para la recolección, procesamiento y difusión de datos de la operación estadística",
                     "$ref":"#/definitions/LegalActs"
                  },
                  "name":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Nombre de la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "objective":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Descripción del objetivo general de la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "officialityType":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Tipo de oficialidad de la operación estadística",
                     "$ref":"#/definitions/Item"
                  },
                  "parentLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enlace al recurso padre de la API",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "producers":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Organismos nacionales o regionales responsables de la producción de la operación estadística",
                     "$ref":"#/definitions/Producers"
                  },
                  "publishers":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Organismos regionales responsables de la difusión de datos de la operación estadística",
                     "$ref":"#/definitions/Publishers"
                  },
                  "regionalContributors":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Organismos regionales colaboradores de la operación estadística",
                     "$ref":"#/definitions/RegionalContributors"
                  },
                  "regionalResponsibles":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Organismos regionales responsables de la operación a escala regional",
                     "$ref":"#/definitions/RegionalResponsibles"
                  },
                  "relPolUsAc":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Política de difusión de los datos de la operación, incluyendo información sobre accesos privilegiados",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "releaseCalendar":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Información sobre la disponibilidad de un calendario de difusión de los datos de la operación estadística",
                     "type":"boolean"
                  },
                  "releaseCalendarAccess":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enlace al calendario de difusión de los datos de la operación estadística",
                     "type":"string"
                  },
                  "revPolicy":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Política general de la revisión de datos de la operación estadística",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "revPractice":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Describir las principales revisiones de datos programadas y las posibles revisiones menores",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "secondarySubjectAreas":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Areas temáticas secundarias en las que está encuadrada la operación",
                     "$ref":"#/definitions/SecondarySubjectAreas"
                  },
                  "selfLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Enlace al propio recurso",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "statisticalOperationType":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Tipo de operación estadística",
                     "$ref":"#/definitions/Item"
                  },
                  "status":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Estado de la operación estadística: planificación, diseño, producción o descatalogada",
                     "$ref":"#/definitions/Status"
                  },
                  "subjectArea":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Area temática principal en las que está encuadrada la operación",
                     "$ref":"#/definitions/Resource"
                  },
                  "updateFrequencies":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Frecuencia con la que se actualizan los datos públicos de la operación estadística",
                     "$ref":"#/definitions/UpdateFrequencies"
                  },
                  "urn":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"urn del recurso",
                     "type":"string"
                  },
                  "genderPerspective":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"Perspectiva de género",
                     "type":"#/definitions/InternationalString"
                  }
               }
            }
         ],
         "description":"Conjunto de actividades, incluidas las preparatorias, que conducen a la obtención y/o difusión de resultados estadísticos sobre un determinado sector o tema o territorio. También se incluyen en el ámbito de esta definición los trabajos de infraestructura y de normalización estadística que posibilitan la coordinación, homogeneización e integración de las estadísticas, así como la recopilación de resultados y la confección de síntesis"
      },
      "Operations":{
         "type":"object",
         "title":"Operations",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "operation":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Listado de operaciones estadísticas"
      },
      "Producers":{
         "type":"object",
         "title":"Producers",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "producer":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Listado de organismos que colaboran en la producción de la estadística"
      },
      "Publishers":{
         "type":"object",
         "title":"Publishers",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "publisher":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Listado de organismos que difunden los resultados de la operación estadística"
      },
      "RegionalContributors":{
         "type":"object",
         "title":"RegionalContributors",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "regionalContributor":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Organismos regionales colaboradores de la operación"
      },
      "RegionalResponsibles":{
         "type":"object",
         "title":"RegionalResponsibles",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "regionalResponsible":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Organismos regionales responsables de la operación"
      },
      "SecondarySubjectAreas":{
         "type":"object",
         "title":"SecondarySubjectAreas",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "secondarySubjectArea":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Áreas temáticas secundarias relacionadas con la operación"
      },
      "StatConcDefs":{
         "type":"object",
         "title":"StatConcDefs",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "statConcDef":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Listado de conceptos que se manejan en la operación"
      },
      "StatisticalOperationSources":{
         "type":"object",
         "title":"StatisticalOperationSources",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "statisticalOperationSource":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Item"
                  }
               }
            }
         ],
         "description":"Fuentes usadas para la elaboración de la operación estadística"
      },
      "StatisticalOperationTypes":{
         "type":"object",
         "title":"StatisticalOperationTypes",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "statisticalOperationType":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Item"
                  }
               }
            }
         ],
         "description":"Tipos de operaciones estadísticas"
      },
      "StatisticalUnits":{
         "type":"object",
         "title":"StatisticalUnits",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "statisticalUnit":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Unidades básicas de observación estadística para las que se proporcionan los datos"
      },
      "Status":{
         "type":"string",
         "title":"Status",
         "enum":[
            "PLANNING",
            "DESIGN",
            "PRODUCTION",
            "OUT_OF_PRINT"
         ],
         "description":"Estado de la operación estadística"
      },
      "TemporalGranularities":{
         "type":"object",
         "title":"TemporalGranularities",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "temporalGranularity":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Granularidades temporales que contemplan los datos de la operación estadística"
      },
      "UpdateFrequencies":{
         "type":"object",
         "title":"UpdateFrequencies",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "updateFrequency":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/statistical-operations/v1.0/domain"
                     },
                     "description":"",
                     "$ref":"#/definitions/Resource"
                  }
               }
            }
         ],
         "description":"Frecuencias de acualización de los datos"
      },
      "ChildLinks":{
         "type":"object",
         "title":"ChildLinks",
         "allOf":[
            {
               "properties":{
                  "total":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Número total de resultados existentes",
                     "type":"number"
                  },
                  "childLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Recurso de la API al que se puede acceder desde el recurso actual",
                     "$ref":"#/definitions/ResourceLink"
                  }
               }
            }
         ],
         "description":"Recursos a los que se puede acceder desde el presente recurso"
      },
      "InternationalString":{
         "type":"object",
         "title":"InternationalString",
         "allOf":[
            {
               "properties":{
                  "text":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Texto en múltiples idiomas",
                     "$ref":"#/definitions/LocalisedString"
                  }
               }
            }
         ],
         "description":"Texto en múltiples lenguajes"
      },
      "Item":{
         "type":"object",
         "title":"Item",
         "allOf":[
            {
               "properties":{
                  "id":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Identificador del recurso",
                     "type":"string"
                  },
                  "name":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Nombre o título del recurso",
                     "$ref":"#/definitions/InternationalString"
                  }
               }
            }
         ],
         "description":""
      },
      "ListBase":{
         "type":"object",
         "title":"ListBase",
         "allOf":[
            {
               "properties":{
                  "firstLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la primera página. Si no se muestra es porque ya estamos en ella. Tener en cuenta que cuando sólo existe una página, no existirá ni primera ni última",
                     "type":"string"
                  },
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso",
                     "type":"string"
                  },
                  "lastLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la última página. Si no se muestra es porque ya estamos en ella. Tener en cuenta que cuando sólo existe una página, no existirá ni primera ni última",
                     "type":"string"
                  },
                  "limit":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Número máximo de resultados a obtener",
                     "type":"number"
                  },
                  "nextLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la página siguiente a la que nos encontramos. Si no se muestra es porque no existe siguiente",
                     "type":"string"
                  },
                  "offset":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Desplazamiento. Número a partir del cual se comienzan a obtener los resultados",
                     "type":"number"
                  },
                  "previousLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la página anterior a la que nos encontramos. Si no se muestra es porque no existe siguiente",
                     "type":"string"
                  },
                  "selfLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Enlace al propio recurso. Dado un resultado nos permite saber cómo realizar la petición a la API para volver a obtenerlo",
                     "type":"string"
                  },
                  "total":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Número total de resultados existentes",
                     "type":"number"
                  }
               }
            }
         ],
         "description":""
      },
      "LocalisedString":{
         "type":"object",
         "title":"LocalisedString",
         "allOf":[
            {
               "properties":{
                  "lang":{
                     "xml":{
                        "attribute":true,
                        "namespace":"http://www.w3.org/XML/1998/namespace"
                     },
                     "description":"Idioma para el que se especifica la traducción",
                     "type":"string"
                  },
                  "(value)":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Traducción en el idioma especificado",
                     "type":"string"
                  }
               }
            }
         ],
         "description":"Texto en un idioma en particular"
      },
      "Resource":{
         "type":"object",
         "title":"Resource",
         "allOf":[
            {
               "properties":{
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso",
                     "type":"string"
                  },
                  "id":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Identificador del recurso",
                     "type":"string"
                  },
                  "name":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Nombre o título del recurso",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "nestedId":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Identifificador del recurso en el que se incluyen los identificadores de los recursos de los que hereda. Los distintos identificadores se separan mediante \".\"",
                     "type":"string"
                  },
                  "selfLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Enlace al propio recurso",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "urn":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"urn del recurso",
                     "type":"string"
                  }
               }
            }
         ],
         "description":""
      },
      "ResourceLink":{
         "type":"object",
         "title":"ResourceLink",
         "allOf":[
            {
               "properties":{
                  "href":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Enlace al recurso",
                     "type":"string"
                  },
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso",
                     "type":"string"
                  }
               }
            }
         ],
         "description":""
      }
   },
   "paths":{
      "/v1.0/families":{
         "get":{
            "tags":[
               "Familias de operaciones"
            ],
            "description":"Permite obtener el listado de familias de operaciones estadísticas",
            "operationId":"resource_StatisticalOperationsV1_0_findFamilies_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"limit",
                  "in":"query",
                  "type":"string",
                  "description":"Número máximo de resultados a obtener"
               },
               {
                  "name":"offset",
                  "in":"query",
                  "type":"string",
                  "description":"Desplazamiento. Número a partir del cual se comienzan a obtener los resultados"
               },
               {
                  "name":"orderBy",
                  "in":"query",
                  "type":"string",
                  "description":"Permite ordenar la lista de resultados según un determinado metadato. El orden se especifica mediante un metadato y el sentido del orden (operador) que se le quiere aplicar.<br/>\r\n Los posibles operadores son ASC y DESC.<br/>\r\n El metadato que se puede usar es ID. <br/>Ejemplos:<br/>\r\n- ID ASC<br/>\r\n- ID DESC"
               },
               {
                  "name":"query",
                  "in":"query",
                  "type":"string",
                  "description":"Permite realizar una búsqueda sobre los resultados. <br/>\r\n Los metadatos sobre los que se puede buscar son: ID, URN, TITLE, ACRONYM, DESCRIPTION e INVENTORY_DATE.<br/>\r\n Los operadores lógicos que se permite usar son: AND y OR.  <br/>\r\n Los operadores de comparación que se permite usar son: EQ, IEQ, LIKE, ILIKE, NE, LT, LE, GT, GE, IS_NULL, IS_NOT_NULL e IN.  <br/>\r\n Ejemplos: <br/>\r\n- ID LIKE \"E303\" <br/>\r\n- (ID LIKE \"Family1\" AND ACRONYM EQ \"Family1\") OR (DESCRIPTION IS_NOT_NULL)"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Families"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/families/{id}":{
         "get":{
            "tags":[
               "Familias de operaciones"
            ],
            "description":"Permite obtener una familia en particular",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveFamilyById_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"id",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la familia"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Family"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/families/{id}/operations":{
         "get":{
            "tags":[
               "Familias de operaciones"
            ],
            "description":"Permite obtener todas las operaciones que forman parte de una determina familia",
            "operationId":"resource_StatisticalOperationsV1_0_findOperationsByFamily_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"id",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la familia estadística"
               },
               {
                  "name":"limit",
                  "in":"query",
                  "type":"string",
                  "description":"Número máximo de resultados a obtener"
               },
               {
                  "name":"offset",
                  "in":"query",
                  "type":"string",
                  "description":"Desplazamiento. Número a partir del cual se comienzan a obtener los resultados"
               },
               {
                  "name":"orderBy",
                  "in":"query",
                  "type":"string",
                  "description":"Permite ordenar la lista de resultados según un determinado metadato. El orden se especifica mediante un metadato y el sentido del orden (operador) que se le quiere aplicar.<br/>\r\n Los posibles operadores son ASC y DESC.<br/>\r\n El metadato que se puede usar es ID. <br/>Ejemplos:<br/>\r\n- ID ASC<br/>\r\n- ID DESC"
               },
               {
                  "name":"query",
                  "in":"query",
                  "type":"string",
                  "description":"Permite realizar una búsqueda sobre los resultados. <br/>\r\n Los metadatos sobre los que se puede buscar son: ID, URN, TITLE, ACRONYM, SUBJECT_AREA_URN, SECONDARY_SUBJECT_AREA_URN, DESCRIPTION, STATISTICAL_OPERATION_TYPE_ID, OFFICIALITY_TYPE_ID, IS_INDICATORS_SYSTEM, PRODUCER_URN, CURRENTLY_ACTIVE, STATUS, PUBLISHER_URN e INVENTORY_DATE.<br/>\r\n Los operadores lógicos que se permite usar son: AND y OR.  <br/>\r\n Los operadores de comparación que se permite usar son: EQ, IEQ, LIKE, ILIKE, NE, LT, LE, GT, GE, IS_NULL, IS_NOT_NULL e IN.  <br/>\r\n Ejemplos: <br/>\r\n- ID LIKE \"E303\" <br/>\r\n- (ID LIKE \"E303\" AND CONTACT_URN LIKE \"urn:contact:1\") OR (CONTACT_URN EQ \"urn:contact:2\")"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Operations"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/operations":{
         "get":{
            "tags":[
               "Operaciones estadísticas"
            ],
            "description":"Permite obtener el listado de operaciones estadísticas existentes en el inventario",
            "operationId":"resource_StatisticalOperationsV1_0_findOperations_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"limit",
                  "in":"query",
                  "type":"string",
                  "description":"Número máximo de resultados a obtener"
               },
               {
                  "name":"offset",
                  "in":"query",
                  "type":"string",
                  "description":"Desplazamiento. Número a partir del cual se comienzan a obtener los resultados"
               },
               {
                  "name":"orderBy",
                  "in":"query",
                  "type":"string",
                  "description":"Permite ordenar la lista de resultados según un determinado metadato. El orden se especifica mediante un metadato y el sentido del orden (operador) que se le quiere aplicar.<br/>\r\n Los posibles operadores son ASC y DESC.<br/>\r\n El metadato que se puede usar es ID. <br/>Ejemplos:<br/>\r\n- ID ASC<br/>\r\n- ID DESC"
               },
               {
                  "name":"query",
                  "in":"query",
                  "type":"string",
                  "description":"Permite realizar una búsqueda sobre los resultados. <br/>\r\n Los metadatos sobre los que se puede buscar son: ID, URN, TITLE, ACRONYM, SUBJECT_AREA_URN, SECONDARY_SUBJECT_AREA_URN, DESCRIPTION, STATISTICAL_OPERATION_TYPE_ID, OFFICIALITY_TYPE_ID, IS_INDICATORS_SYSTEM, PRODUCER_URN, CURRENTLY_ACTIVE, STATUS, PUBLISHER_URN e INVENTORY_DATE.<br/>\r\n Los operadores lógicos que se permite usar son: AND y OR.  <br/>\r\n Los operadores de comparación que se permite usar son: EQ, IEQ, LIKE, ILIKE, NE, LT, LE, GT, GE, IS_NULL, IS_NOT_NULL e IN.  <br/>\r\n Ejemplos: <br/>\r\n- ID LIKE \"E303\" <br/>\r\n- (ID LIKE \"E303\" AND CONTACT_URN LIKE \"urn:contact:1\") OR (CONTACT_URN EQ \"urn:contact:2\")"
               }
               {
                  "name":"fields",
                  "in":"fields",
                  "type":"string",
                  "description":"Permite personalizar la respuesta mediante la exclusión o inclusión de campos de la misma. Los posibles valores son \"+subjectArea\".<br/>Ejemplos: <br/>\r\n- fields=+subjectArea<br/>"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Operations"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/operations/{id}":{
         "get":{
            "tags":[
               "Operaciones estadísticas"
            ],
            "description":"Permite obtener una operación estadística en concreto",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveOperationById_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"id",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la operación estadística"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Operation"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/operations/{operationId}/instances":{
         "get":{
            "tags":[
               "Operaciones estadísticas"
            ],
            "description":"Permite obtener el listado de instancias relacionadas con una operación estadística en concreto",
            "operationId":"resource_StatisticalOperationsV1_0_findInstances_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"operationId",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la operación"
               },
               {
                  "name":"limit",
                  "in":"query",
                  "type":"string",
                  "description":"Número máximo de resultados a obtener"
               },
               {
                  "name":"offset",
                  "in":"query",
                  "type":"string",
                  "description":"Deplazamiento. Número a partir del cual se comienzan a obtener los resultados"
               },
               {
                  "name":"orderBy",
                  "in":"query",
                  "type":"string",
                  "description":"Permite ordenar la lista de resultados según un determinado metadato. El orden se especifica mediante un metadato y el sentido del orden (operador) que se le quiere aplicar.<br/>\r\n Los posibles operadores son ASC y DESC.<br/>\r\n El metadato que se puede usar es ID. <br/>Ejemplos:<br/>\r\n- ID ASC<br/>\r\n- ID DESC"
               },
               {
                  "name":"query",
                  "in":"query",
                  "type":"string",
                  "description":"Permite realizar una búsqueda sobre los resultados. <br/>\r\n Los metadatos sobre los que se puede buscar son: ID, URN, TITLE, ACRONYM, DATA_DESCRIPTION, GEOGRAPHIC_GRANULARITY_URN, TEMPORAL_GRANULARITY_URN e INVENTORY_DATE.<br/>\r\n Los operadores lógicos que se permite usar son: AND y OR.  <br/>\r\n Los operadores de comparación que se permite usar son: EQ, IEQ, LIKE, ILIKE, NE, LT, LE, GT, GE, IS_NULL, IS_NOT_NULL e IN.  <br/>\r\n Ejemplos: <br/>\r\n- ID LIKE \"E303\" <br/>\r\n- (ID LIKE \"Instance1\" AND DATA_DESCRIPTION EQ \"DataDescription1\") OR (ACRONYM EQ \"Instance1\")"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Instances"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/operations/{operationId}/instances/{id}":{
         "get":{
            "tags":[
               "Operaciones estadísticas"
            ],
            "description":"Permite obtener una instancia en particular de una operación estadística",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveInstanceById_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"id",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la instancia"
               },
               {
                  "name":"operationId",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la operación estadística"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Instance"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/operations/{id}/families":{
         "get":{
            "tags":[
               "Operaciones estadísticas"
            ],
            "description":"Permite obtener el listado de familias estadísticas en las que se engloba una operación en concreto",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveFamiliesByOperation_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[
               {
                  "name":"id",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la operación estadística"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Families"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/statisticalOperationSources":{
         "get":{
            "tags":[
               "Tablas de valores auxiliares"
            ],
            "description":"Permite obtener todos los posibles tipos de orígenes de datos disponibles para las operaciones estadísticas",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveStatisticalOperationSources_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[

            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/StatisticalOperationSources"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/officialityTypes":{
         "get":{
            "tags":[
               "Tablas de valores auxiliares"
            ],
            "description":"Permite obtener todos los tipos de oficialidad que se le pueden asignar a las diferentes operaciones estadísticas",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveOfficialityTypes_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[

            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/OfficialityTypes"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/costs":{
         "get":{
            "tags":[
               "Tablas de valores auxiliares"
            ],
            "description":"Permite obtener el listado de todos los tipos de costes disponibles para las operaciones estadísticas",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveCosts_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[

            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Costs"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/collMethods":{
         "get":{
            "tags":[
               "Tablas de valores auxiliares"
            ],
            "description":"Permite obtener todos los métodos de recolección de datos existentes",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveCollMethods_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[

            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/CollMethods"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/instanceTypes":{
         "get":{
            "tags":[
               "Tablas de valores auxiliares"
            ],
            "description":"Permite obtener todos los tipos de instancias de operaciones que existen",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveInstanceTypes_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[

            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/InstanceTypes"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      },
      "/v1.0/statisticalOperationTypes":{
         "get":{
            "tags":[
               "Tablas de valores auxiliares"
            ],
            "description":"Permite obtener todos los tipos de operaciones estadísticas existentes",
            "operationId":"resource_StatisticalOperationsV1_0_retrieveStatisticalOperationTypes_GET",
            "produces":[
               "application/xml", "application/json"
            ],
            "parameters":[

            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/StatisticalOperationTypes"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente"
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido"
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado"
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal"
               }
            }
         }
      }
   }
}