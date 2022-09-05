package org.siemac.metamac.statistical.operations.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType INVALID_PROC_STATUS                                        = create("exception.operations.invalid.proc_status");

    public static final CommonServiceExceptionType FAMILY_WITHOUT_PUBLISHED_INTERNALLY_OPERATIONS             = create("exception.operations.family.without_published_internally_operations");
    public static final CommonServiceExceptionType FAMILY_NOT_FOUND                                           = create("exception.operations.family.not_found");
    public static final CommonServiceExceptionType FAMILY_CODE_NOT_FOUND                                      = create("exception.operations.family_code.not_found");
    public static final CommonServiceExceptionType FAMILY_WITHOUT_PUBLISHED_EXTERNALLY_OPERATIONS             = create("exception.operations.family.without_published_externally_operations");
    public static final CommonServiceExceptionType FAMILY_WITHOUT_OPERATIONS                                  = create("exception.operations.family.without_operations");
    public static final CommonServiceExceptionType FAMILY_ALREADY_EXIST_CODE_DUPLICATED                       = create("exception.operations.family.already_exist.code_duplicated");

    public static final CommonServiceExceptionType OPERATION_NOT_FOUND                                        = create("exception.operations.operation.not_found");
    public static final CommonServiceExceptionType OPERATION_CODE_NOT_FOUND                                   = create("exception.operations.operation_code.not_found");
    public static final CommonServiceExceptionType OPERATION_ALREADY_EXIST_CODE_DUPLICATED                    = create("exception.operations.operation.already_exist.code_duplicated");

    public static final CommonServiceExceptionType OPERATION_TEC_IN_CHARGE_EQUALS_ASSISTANT_TEC               = create("exception.operations.operation.tec_in_charge_equals_assistant_tec");

    public static final CommonServiceExceptionType INSTANCE_INCORRECT_OPERATION_ID                            = create("exception.operations.instance.incorrect_operation_id");
    public static final CommonServiceExceptionType INSTANCE_INCORRECT_OPERATION_PROC_STATUS                   = create("exception.operations.instance.incorrect_operation_proc_status");
    public static final CommonServiceExceptionType INSTANCE_NOT_FOUND                                         = create("exception.operations.instance.not_found");
    public static final CommonServiceExceptionType INSTANCE_CODE_NOT_FOUND                                    = create("exception.operations.instance_code.not_found");
    public static final CommonServiceExceptionType INSTANCE_ID_NOT_FOUND                                      = create("exception.operations.instance_id.not_found");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION                                 = create("exception.operations.instance.without_operation");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION_PUBLISHED                       = create("exception.operations.instance.without_operation_published");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION_PUBLISHED_EXTERNALLY            = create("exception.operations.instance.without_operation_published_externally");
    public static final CommonServiceExceptionType INSTANCE_ALREADY_EXIST_CODE_DUPLICATED                     = create("exception.operations.instance.already_exist.code_duplicated");

    public static final CommonServiceExceptionType SURVEY_TYPE_NOT_FOUND                                      = create("exception.operations.list.survey_type.not_found");
    public static final CommonServiceExceptionType ACTIVITY_TYPE_NOT_FOUND                                    = create("exception.operations.list.activity_type.not_found");
    public static final CommonServiceExceptionType COLL_METHOD_NOT_FOUND                                      = create("exception.operations.list.coll_method.not_found");
    public static final CommonServiceExceptionType SOURCE_DATA_NOT_FOUND                                      = create("exception.operations.list.sources_data.not_found");
    public static final CommonServiceExceptionType OFFICIALITY_TYPE_NOT_FOUND                                 = create("exception.operations.list.officiality_types.not_found");
    public static final CommonServiceExceptionType COST_NOT_FOUND                                             = create("exception.operations.list.cost.not_found");

    public static final CommonServiceExceptionType SECURITY_ACCESS_OPERATION_NOT_ALLOWED                      = create("exception.operations.security.access_operation_not_allowed");

    public static final CommonServiceExceptionType UNABLE_TO_SEND_STREAM_MESSAGING_TO_STREAM_MESSAGING_SERVER = create("stream_message.operations.exception.send_message.fails");
    public static final CommonServiceExceptionType STREAM_MESSAGING_TOPIC_IS_INVALID                          = create("stream_message.operations.exception.topic.invalid");
    public static final CommonServiceExceptionType STREAM_MESSAGING_MISSING_MANDATORY_SETTINGS                = create("stream_message.operations.exception.config.missing_settings");

    public static final CommonServiceExceptionType EXPORTATION_TSV_ERROR                                      = create("exception.operations.exportation.tsv.error");

}
