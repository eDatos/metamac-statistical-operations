package org.siemac.metamac.statistical.operations.core.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Statistical operation
 */
@Entity
@Table(name = "TB_OPERATIONS", uniqueConstraints = {@UniqueConstraint(columnNames = {"CODE"})})
public class Operation extends org.siemac.metamac.statistical.operations.core.domain.OperationBase {

    private static final long serialVersionUID = 1L;

    public Operation() {
    }
}
