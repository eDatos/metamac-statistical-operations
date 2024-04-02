package org.siemac.metamac.statistical.operations.core.stream.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;

/**
 * @param <D> Domain entity
 * @param <A> Avro entity
 */
public interface Do2AvroMapper<D, A> {

    A toAvro(D source) throws MetamacException;

    default List<A> toAvros(Collection<D> source) throws MetamacException {
        if (source == null) {
            return new ArrayList<>();
        }
        List<A> list = new ArrayList<>();
        for (D d : source) {
            A a = toAvro(d);
            list.add(a);
        }
        return list;
    }
}
