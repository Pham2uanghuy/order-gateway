package com.huypq.balanceservice;

import com.huypq.balanceservice.transaction.AccountingTransaction;
import com.huypq.balanceservice.utils.MoreObjects;

import java.util.ArrayList;
import java.util.List;

import static com.huypq.balanceservice.utils.Preconditions.checkNotNull;

public class Journal {
    final private List<AccountingTransaction> transactions = new ArrayList<>();

    public void addTransaction(AccountingTransaction transaction) {
        checkNotNull(transaction);
        transactions.add(transaction);
    }

    public List<AccountingTransaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("transactions", transactions)
                .toString();
    }
}