package org.siemac.metamac.statistical.operations.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class InstanceDS extends DataSource {

    // IDENTIFIERS
    public static final String ID                           = "in-id";
    public static final String CODE                         = "in-code";
    public static final String CODE_VIEW                    = "in-code-view";       // Not mapped in DTO
    public static final String URN                          = "in-urn";
    public static final String TITLE                        = "in-title";
    public static final String ACRONYM                      = "in-acron";
    // CONTENT CLASSIFIERS

    // CONTENT DESCRIPTORS
    public static final String DATA_DESCRIPTION             = "in-ddesc";
    public static final String STATISTICAL_POPULATION       = "in-sta-pop";
    public static final String STATISTICAL_UNIT             = "in-sta-unit";
    public static final String GEOGRAPHIC_GRANULARITIES     = "in-geo-gran";
    public static final String GEOGRAPHIC_COMPARABILITY     = "in-geo-com";
    public static final String TEMPORAL_GRANULARITIES       = "in-tem-gran";
    public static final String TEMPORAL_COMPARABILITY       = "in-tem-com";
    public static final String BASE_PERIOD                  = "in-basep";
    public static final String MEASURES                     = "in-umeas";
    public static final String STAT_CONC_DEF_DESCRIPTION    = "in-sta-con";
    public static final String STAT_CONC_DEF                = "in-sta-con-list";
    public static final String CLASS_SYSTEM_DESCRIPTION     = "in-class-sys";
    public static final String CLASS_SYSTEM_LIST            = "in-class-sys-list";
    // CLASS DESCRIPTORS
    public static final String INSTANCE_TYPE                = "in-intype";
    // PRODUCTION DESCRIPTORS
    public static final String INTERNAL_INVENTORY_DATE      = "in-int-inv-date";
    public static final String PROC_STATUS                  = "in-proc-status";
    public static final String PROC_STATUS_VIEW             = "in-proc-status-view"; // Not mapped in DTO
    public static final String DOC_METHOD                   = "in-doc-met";
    public static final String STATISTICAL_OPERATION_SOURCE = "in-ssource";
    public static final String COLL_METHOD                  = "in-coll-met";
    public static final String INFORMATION_SUPPLIERS        = "in-suppliers";
    public static final String FREQ_COLL                    = "in-freq-coll";
    public static final String DATA_VALIDATION              = "in-valid";
    public static final String DATA_COMPILATION             = "in-compil";
    public static final String ADJUSTMENT                   = "in-adjust";
    public static final String SEASONAL_ADJUSTMENT          = "in-season-adjust";
    public static final String COST_BURDEN                  = "in-cost-burden";
    public static final String COST_DETAIL                  = "in-cost-detail";
    public static final String COST                         = "in-cost";
    public static final String CREATED_DATE                 = "in-created-date";
    // DIFFUSION DESCRIPTORS
    public static final String INVENTORY_DATE               = "in-indate";
    // QUALITY DESCRIPTORS
    public static final String QUALITY_DOC                  = "in-qdoc";
    public static final String QUALITY_ASSURE               = "in-qassu";
    public static final String QUALITY_ASSMNT               = "in-qassm";
    public static final String USER_NEEDS                   = "in-usern";
    public static final String USER_SAT                     = "in-users";
    public static final String COMPLETENESS                 = "in-comp";
    public static final String TIMELINESS                   = "in-tim";
    public static final String PUNCTUALITY                  = "in-punc";
    public static final String ACCURACY_OVERALL             = "in-accu";
    public static final String SAMPLING_ERROR               = "in-samp";
    public static final String NONSAMPLING_ERR              = "in-nons";
    public static final String COVERAGE_ERROR               = "in-cover";
    public static final String MEASUREMENT_ERROR            = "in-measure";
    public static final String NONRESPONSE_ERROR            = "in-nonresp";
    public static final String PROCESSING_ERROR             = "in-process";
    public static final String MODEL_ERROR                  = "in-model";

    public static final String COHER_X_DOM                  = "in-coher";
    public static final String COHER_SUBANUAL_ANUAL         = "in-sub-anual";
    public static final String COHER_NATIONAL_ACCOUNTS      = "in-nat";
    public static final String COHER_INTERNAL               = "in-intl";
    // ANNOTATIONS
    public static final String COMMENTS                     = "op-com";
    public static final String NOTES                        = "op-not";

    public static final String ORDER                        = "in-order";

    public InstanceDS() {
        DataSourceTextField id = new DataSourceTextField(CODE);
        id.setPrimaryKey(true);
        addField(id);
    }
}
