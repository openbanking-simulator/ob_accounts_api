package com.openbanking.simulator.api.accounts.service;

import com.openbanking.simulator.api.accounts.dao.AccountsDao;
import com.openbanking.simulator.api.accounts.dto.AccountRequestDTO;
import com.openbanking.simulator.api.accounts.model.Account;
import com.openbanking.simulator.api.spec.model.LinksPaginated;
import com.openbanking.simulator.api.spec.model.MetaPaginated;
import com.openbanking.simulator.api.spec.model.ResponseBankingAccountList;
import com.openbanking.simulator.api.spec.model.ResponseBankingAccountListData;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class AccountsService {

    private AccountsDao accountsDao;

    public AccountsService(AccountsDao accountsDao) {
        this.accountsDao = accountsDao;
    }

    public Mono<ResponseBankingAccountList> getAccounts(final AccountRequestDTO requestDTO) {
        Mono<Page<Account>> pagedAccountsMono = accountsDao.findAccounts(requestDTO);
        return pagedAccountsMono.flatMap(pagedAccounts -> {

            System.out.println("==> count :: "+pagedAccounts.getTotalElements());
            System.out.println("==> total pages :: "+pagedAccounts.getTotalPages());

            ResponseBankingAccountListData data = new ResponseBankingAccountListData();
            data.setAccounts(pagedAccounts.get().collect(Collectors.toList()));

            ResponseBankingAccountList response = new ResponseBankingAccountList();
            response.setData(data);
            response.setLinks(preparePaginatedLinks());
            response.setMeta(preparePaginatedMeta());

            return Mono.just(response);
        }).defaultIfEmpty(prepareDefaultResponse(requestDTO));
    }

    private LinksPaginated preparePaginatedLinks() {
        LinksPaginated links = new LinksPaginated();
        links.setFirst("");
        links.setLast("");
        links.setNext("");
        links.setPrev("");
        links.setSelf("");

        return links;
    }

    private MetaPaginated preparePaginatedMeta() {
        MetaPaginated meta = new MetaPaginated();
        meta.setTotalPages(10);
        meta.setTotalRecords(10);

        return meta;
    }

    private ResponseBankingAccountList prepareDefaultResponse(final AccountRequestDTO requestDTO) {
        ResponseBankingAccountListData data = new ResponseBankingAccountListData();

        ResponseBankingAccountList response = new ResponseBankingAccountList();
        response.setData(data);
        response.setLinks(preparePaginatedLinks());
        response.setMeta(preparePaginatedMeta());

        return response;
    }
}
