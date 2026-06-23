package com.artificer.infrastructure.mapper;

import com.artificer.domain.model.Cerveja;
import com.artificer.domain.output.CervejaSummary;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CervejaMapper {

    CervejaSummary toModel(Cerveja cerveja);
    List<CervejaSummary> toModel(List<Cerveja> cervejas);
}
