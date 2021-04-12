package com.elvin.latihandto.mapper;

import com.elvin.latihandto.dto.SalesDTO;
import com.elvin.latihandto.model.Sales;

public class SalesMapper {

    public static Sales DtoToEntity(SalesDTO salesDTO){
        return new Sales().setProductId(salesDTO.getProductId())
                            .setQty(salesDTO.getQty());
    }

    public static SalesDTO EntityToDto(Sales sales){
        return new SalesDTO().setProductId(sales.getProductId())
                                .setQty(sales.getQty());
    }
}
