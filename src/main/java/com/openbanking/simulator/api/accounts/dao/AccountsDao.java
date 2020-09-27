package com.openbanking.simulator.api.accounts.dao;

import com.openbanking.simulator.api.accounts.dto.AccountRequestDTO;
import com.openbanking.simulator.api.accounts.model.Account;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class AccountsDao {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public AccountsDao(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<Page<Account>> findAccounts(AccountRequestDTO requestDTO) {

        Query query = new Query();

        if(StringUtils.isNotEmpty(requestDTO.getProductCategory())) {
            query.addCriteria(Criteria.where("productCategory").is(requestDTO.getProductCategory()));
        }

        if(StringUtils.isNotEmpty(requestDTO.getOpenStatus()) && !requestDTO.getOpenStatus().equals("ALL")) {
            query.addCriteria(Criteria.where("openStatus").is(requestDTO.getOpenStatus()));
        }

        if(Objects.nonNull(requestDTO.getOwned())) {
            query.addCriteria(Criteria.where("isOwned").is(requestDTO.getOwned().booleanValue()));
        }

        // Apply pagination on top of the provided filter criteria
        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");
        Pageable pageable = PageRequest.of(requestDTO.getPage() - 1, requestDTO.getPageSize(), sort);

        query = query.with(sort).with(pageable);

        Mono<Long> countMono = reactiveMongoTemplate.count(Query.of(query).limit(-1).skip(-1), Account.class);
        Flux<Account> accounts = reactiveMongoTemplate.find(query, Account.class);

        return Mono.zip(accounts.collectList(),countMono).map(tuple2 -> {
            return PageableExecutionUtils.getPage(
                tuple2.getT1(),
                pageable,
                () -> tuple2.getT2());
        });
    }
}