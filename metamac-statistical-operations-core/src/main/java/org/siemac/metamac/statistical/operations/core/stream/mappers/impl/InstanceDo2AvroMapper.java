package org.siemac.metamac.statistical.operations.core.stream.mappers.impl;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.operations.core.domain.Instance;
import org.siemac.metamac.statistical.operations.core.stream.mappers.Do2AvroMapper;
import org.siemac.metamac.statistical.operations.core.stream.messages.InstanceAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstanceDo2AvroMapper implements Do2AvroMapper<Instance, InstanceAvro> {

    @Autowired
    DatetimeDo2AvroMapper            datetimeDo2AvroMapper;

    @Autowired
    InternationalStringDo2AvroMapper internationalStringDo2AvroMapper;

    @Autowired
    ProcStatusDo2AvroMapper          procStatusDo2AvroMapper;

    @Autowired
    OperationDo2AvroMapper           operationDo2AvroMapper;

    @Autowired
    InstanceTypeDo2AvroMapper        instanceTypeDo2AvroMapper;

    @Autowired
    SurveySourceDo2AvroMapper        surveySourceDo2AvroMapper;

    @Autowired
    CollMethodDo2AvroMapper          collMethodDo2AvroMapper;

    @Autowired
    ExternalItemDo2AvroMapper        externalItemDo2AvroMapper;

    @Autowired
    CostDo2AvroMapper                costDo2AvroMapper;

    @Override
    public InstanceAvro toAvro(Instance source) throws MetamacException {
        if (source == null) {
            return null;
        }
        return InstanceAvro.newBuilder().setOrder(source.getOrder()).setCode(source.getCode()).setUrn(source.getUrn()).setBasePeriod(source.getBasePeriod())
                .setInternalInventoryDate(datetimeDo2AvroMapper.toAvro(source.getInternalInventoryDate())).setInventoryDate(datetimeDo2AvroMapper.toAvro(source.getInventoryDate()))
                .setUpdateDate(datetimeDo2AvroMapper.toAvro(source.getUpdateDate())).setUuid(source.getUuid()).setCreatedDate(datetimeDo2AvroMapper.toAvro(source.getCreatedDate()))
                .setCreatedBy(source.getCreatedBy()).setLastUpdated(datetimeDo2AvroMapper.toAvro(source.getLastUpdated())).setLastUpdatedBy(source.getLastUpdatedBy()).setVersion(source.getVersion())
                .setTitle(internationalStringDo2AvroMapper.toAvro(source.getTitle())).setAcronym(internationalStringDo2AvroMapper.toAvro(source.getAcronym()))
                .setOperation(operationDo2AvroMapper.toAvro(source.getOperation())).setDataDescription(internationalStringDo2AvroMapper.toAvro(source.getDataDescription()))
                .setStatisticalPopulation(internationalStringDo2AvroMapper.toAvro(source.getStatisticalPopulation()))
                .setGeographicComparability(internationalStringDo2AvroMapper.toAvro(source.getGeographicComparability()))
                .setTemporalComparability(internationalStringDo2AvroMapper.toAvro(source.getTemporalComparability())).setStatConcDef(internationalStringDo2AvroMapper.toAvro(source.getStatConcDef()))
                .setClassSystem(internationalStringDo2AvroMapper.toAvro(source.getClassSystem())).setInstanceType(instanceTypeDo2AvroMapper.toAvro(source.getInstanceType()))
                .setProcStatus(procStatusDo2AvroMapper.toAvro(source.getProcStatus())).setDocMethod(internationalStringDo2AvroMapper.toAvro(source.getDocMethod()))
                .setSurveySource(surveySourceDo2AvroMapper.toAvro(source.getSurveySource())).setCollMethod(collMethodDo2AvroMapper.toAvro(source.getCollMethod()))
                .setDataValidation(internationalStringDo2AvroMapper.toAvro(source.getDataValidation())).setDataCompilation(internationalStringDo2AvroMapper.toAvro(source.getDataCompilation()))
                .setAdjustment(internationalStringDo2AvroMapper.toAvro(source.getAdjustment())).setCostBurden(internationalStringDo2AvroMapper.toAvro(source.getCostBurden()))
                .setQualityDoc(internationalStringDo2AvroMapper.toAvro(source.getQualityDoc())).setQualityAssure(internationalStringDo2AvroMapper.toAvro(source.getQualityAssure()))
                .setQualityAssmnt(internationalStringDo2AvroMapper.toAvro(source.getQualityAssmnt())).setUserNeeds(internationalStringDo2AvroMapper.toAvro(source.getUserNeeds()))
                .setUserSat(internationalStringDo2AvroMapper.toAvro(source.getUserSat())).setCompleteness(internationalStringDo2AvroMapper.toAvro(source.getCompleteness()))
                .setTimeliness(internationalStringDo2AvroMapper.toAvro(source.getTimeliness())).setPunctuality(internationalStringDo2AvroMapper.toAvro(source.getPunctuality()))
                .setAccuracyOverall(internationalStringDo2AvroMapper.toAvro(source.getAccuracyOverall())).setSamplingErr(internationalStringDo2AvroMapper.toAvro(source.getSamplingErr()))
                .setNonsamplingErr(internationalStringDo2AvroMapper.toAvro(source.getNonsamplingErr())).setCoherXDomain(internationalStringDo2AvroMapper.toAvro(source.getCoherXDomain()))
                .setCoherInternal(internationalStringDo2AvroMapper.toAvro(source.getCoherInternal())).setComment(internationalStringDo2AvroMapper.toAvro(source.getComment()))
                .setNotes(internationalStringDo2AvroMapper.toAvro(source.getNotes())).setStatisticalUnit(externalItemDo2AvroMapper.toAvros(source.getStatisticalUnit()))
                .setGeographicGranularity(externalItemDo2AvroMapper.toAvros(source.getGeographicGranularity()))
                .setTemporalGranularity(externalItemDo2AvroMapper.toAvros(source.getTemporalGranularity())).setUnitMeasure(externalItemDo2AvroMapper.toAvros(source.getUnitMeasure()))
                .setStatConcDefList(externalItemDo2AvroMapper.toAvros(source.getStatConcDefList())).setClassSystemList(externalItemDo2AvroMapper.toAvros(source.getClassSystemList()))
                .setPublicInformationSuppliers(externalItemDo2AvroMapper.toAvros(source.getPublicInformationSuppliers()))
                .setPrivateInformationSuppliers(internationalStringDo2AvroMapper.toAvro(source.getPrivateInformationSuppliers()))
                .setFreqColl(externalItemDo2AvroMapper.toAvros(source.getFreqColl()))
                .setCost(costDo2AvroMapper.toAvros(source.getCost())).build();
    }
}
