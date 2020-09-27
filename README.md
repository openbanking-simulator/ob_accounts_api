# Open Banking - Accounts API

## Setup

### Provision mongo container for testing

* Pull image and run mongo docker container
```
$ docker pull mongo:4.2.9-bionic

$ docker run -p 27017:27017 --name ob_accounts_mongodb -d mongo:4.2.9-bionic

$ docker logs ob_accounts_mongodb
```

* Login to shell and create database
```
$ docker exec -it ob_accounts_mongodb bash

root@XXXXX:/# mongo -host localhost -port 27017

> show dbs

> use ob_accounts

-- Create Sample collections

> db.account.insert({
    customerId: "customerId1",
    accountId: "accountId",
    creationDate: "2002-10-02T15:00:00Z",
    displayName: "DisplayName 1",
    nickname: "NickName 1",
    openStatus: "OPEN",
    isOwned: true,
    maskedNumber: "XXXX-XXX1234",
    productCategory: "TRANS_AND_SAVINGS_ACCOUNTS",
    productName: "Savings Product"
})

> db.account.insert({
    customerId: "customerId2",
    accountId: "accountId2",
    creationDate: "2002-10-02T15:00:00Z",
    displayName: "DisplayName 2",
    nickname: "NickName 2",
    openStatus: "CLOSED",
    isOwned: false,
    maskedNumber: "XXXX-XXX5432",
    productCategory: "TRANS_AND_SAVINGS_ACCOUNTS",
    productName: "Savings Product"
})

> exit

root@XXXXX:/# exit
```

* Stop mongo container
```
$ docker stop ob_accounts_mongodb
```