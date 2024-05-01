package org.siemac.metamac.statistical.operations.core.stream.mappers.impl;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryResourceInternal;
import org.siemac.metamac.statistical.operations.core.stream.mappers.Do2AvroMapper;
import org.siemac.metamac.statistical.operations.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.operations.core.utils.InternationalStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryRest2AvroMapper implements Do2AvroMapper<CategoryResourceInternal, ExternalItemAvro> {

    @Autowired
    InternationalStringDo2AvroMapper   internationalStringDo2AvroMapper;

    @Autowired
    TypeExternalArtifactsDo2AvroMapper typeExternalArtifactsDo2AvroMapper;

    @Override
    public ExternalItemAvro toAvro(CategoryResourceInternal source) {
        if (source == null) {
            return null;
        }
        return ExternalItemAvro.newBuilder().setCode(source.getId()).setCodeNested(source.getNestedId()).setUrn(source.getUrn()).setUrnProvider(source.getUrnProvider())
                .setManagementAppUrl(source.getManagementAppLink()).setType(typeExternalArtifactsDo2AvroMapper.toAvro(TypeExternalArtefactsEnum.CATEGORY))
                .setTitle(internationalStringDo2AvroMapper.toAvro(InternationalStringUtils.getCommonInternationalStringFromRestInternationalString(source.getName()))).build();
    }

}
