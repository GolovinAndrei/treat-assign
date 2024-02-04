package co.il.treat.treatassign.service.mapper;

import java.util.List;

public interface CommonMapper<D, E> {

    D toDto(E e);

    E toEntity(D d);

    List<D> toListDto(List<E> entityList);

    List<E> toListEntity(List<D> dtoList);
}

