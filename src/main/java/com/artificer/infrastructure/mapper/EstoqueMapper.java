package com.artificer.infrastructure.mapper;

import com.artificer.domain.output.ValorItensEstoque;
import com.artificer.infrastructure.projections.ValorItensEstoqueProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {

    ValorItensEstoque toDto(ValorItensEstoqueProjection projection);
}
