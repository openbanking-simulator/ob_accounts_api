package com.openbanking.simulator.api.accounts.model;

import com.openbanking.simulator.api.spec.model.BankingAccount;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "account")
@Data
public class Account extends BankingAccount {

}