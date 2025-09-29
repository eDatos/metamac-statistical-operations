package org.siemac.metamac.statistical.operations.core.stream.mappers.impl;

import org.siemac.metamac.statistical.operations.core.enume.domain.EdatosMigrationStatusEnum;
import org.siemac.metamac.statistical.operations.core.stream.mappers.Do2AvroMapper;
import org.siemac.metamac.statistical.operations.core.stream.messages.EdatosMigrationStatusEnumAvro;
import org.springframework.stereotype.Component;

@Component
public class EdatosMigrationStatusDo2AvroMapper implements Do2AvroMapper<EdatosMigrationStatusEnum, EdatosMigrationStatusEnumAvro> {
    @Override
    public EdatosMigrationStatusEnumAvro toAvro(EdatosMigrationStatusEnum source) {
        return source != null ? EdatosMigrationStatusEnumAvro.valueOf(source.name()) : null;
    }
}
