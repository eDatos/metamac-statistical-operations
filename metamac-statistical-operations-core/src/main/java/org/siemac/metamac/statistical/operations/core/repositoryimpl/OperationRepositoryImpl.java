package org.siemac.metamac.statistical.operations.core.repositoryimpl;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementation for Operation
 */
@Repository("operationRepository")
public class OperationRepositoryImpl extends OperationRepositoryBase {
    public OperationRepositoryImpl() {
    }

    public void findByIdIn(List<Long> operationsId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByIdIn not implemented");

    }
}
