package org.siemac.metamac.statistical.operations.core.repositoryimpl;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for OperationUrl
 */
@Repository("operationUrlRepository")
public class OperationUrlRepositoryImpl extends OperationUrlRepositoryBase {
    public OperationUrlRepositoryImpl() {
    }

    public void findByOperationId() {

        throw new UnsupportedOperationException(
            "findByOperationId not implemented");

    }
}
