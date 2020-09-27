package com.openbanking.simulator.api.accounts.dto;

import lombok.Data;

@Data
public class AccountRequestDTO {

    private String productCategory;
    private String openStatus;
    private Boolean owned;

    private Integer page;
    private Integer pageSize;
}
