-- ---------------------------------------------------------------------------------------------------
-- EDATOS-4564 statistical-operations - Llamadas internas a apis internas con autenticación a través de API key
    -- "metamac.statistical-operations.rest.api_key"
-- ---------------------------------------------------------------------------------------------------


insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE,EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,true,'metamac.statistical_operations.rest.api_key',FILL_ME,false);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

commit;
/* Key para todos los entornos
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE,EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,true,'metamac.statistical_operations.rest.api_key','2tL4qJA8JJ6jyg1SlogSVL62RLkQZjITS3j2LXGMVBZ5PnyRHmT7gNJuyYt5Hw8q',false);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

commit;

*/

