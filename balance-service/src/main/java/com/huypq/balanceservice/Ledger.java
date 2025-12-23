package com.huypq.balanceservice;

import com.huypq.balanceservice.account.Account;
import com.huypq.balanceservice.account.AccountDetails;
import com.huypq.balanceservice.account.AccountingEntry;
import com.huypq.balanceservice.chartofaccounts.ChartOfAccounts;
import com.huypq.balanceservice.transaction.AccountingTransaction;
import com.huypq.balanceservice.transaction.AccountingTransactionBuilder;
import com.huypq.balanceservice.utils.MoreObjects;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;

import static com.huypq.balanceservice.utils.Preconditions.checkArgument;

final public class Ledger {
    final private HashMap<String, Account> accountNumberToAccount = new HashMap<>();

    @Getter
    final private Journal journal = new Journal();
    @Getter
    final private ChartOfAccounts coa;

    public Ledger(ChartOfAccounts coa) {
        this.coa = coa;
        // Create coa accounts
        coa.getAccountNumberToAccountDetails().values().forEach(this::addAccount);
    }

    public Ledger(Journal journal, ChartOfAccounts coa) {
        this(coa);
        // Add transactions
        journal.getTransactions().forEach(this::commitTransaction);
    }

    public AccountingTransactionBuilder createTransaction(@Nullable Map<String, String> info) {
        return AccountingTransactionBuilder.create(info);
    }

    public void commitTransaction(AccountingTransaction transaction) {
        // Add entries to accounts
        transaction.getEntries().forEach(this::addAccountEntry);
        journal.addTransaction(transaction);
    }

    public TrialBalanceResult computeTrialBalance() {
        return new TrialBalanceResult(new HashSet<>(accountNumberToAccount.values()));
));
    }

    public BigDecimal getAccountBalance(String accountNumber) {
        return accountNumberToAccount.get(accountNumber).getBalance();
    }

    private void addAccount(AccountDetails accountDetails) {
        String newAccountNumber = accountDetails.getAccountNumber();
        boolean accountNumberNotInUse = !accountNumberToAccount.containsKey(newAccountNumber);
        checkArgument(accountNumberNotInUse,
                newAccountNumber);
        accountNumberToAccount.put(accountDetails.getAccountNumber(), new Account(accountDetails));
    }




    private void addAccountEntry(AccountingEntry entry) {
        var account = accountNumberToAccount.get(entry.getAccountNumber());
        if (account == null) {
            throw new IllegalStateException("Entry references missing account");
        }
        account.addEntry(entry);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountNumberToAccountMap", accountNumberToAccount)
                .add("journal", journal)
                .add("chartOfAccounts", coa)
                .toString();
    }
}