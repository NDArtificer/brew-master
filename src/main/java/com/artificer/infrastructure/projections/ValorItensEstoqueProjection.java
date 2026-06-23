package com.artificer.infrastructure.projections;

import java.math.BigDecimal;

public interface ValorItensEstoqueProjection {
    BigDecimal getValor();

    Long getTotalItens();
}