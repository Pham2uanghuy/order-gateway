package com.huypq.balanceservice.account;

import com.huypq.balanceservice.utils.MoreObjects;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.huypq.balanceservice.utils.Preconditions.checkArgument;
import static com.huypq.balanceservice.utils.Preconditions.checkNotNull;

final public class Account {
    final private List<AccountingEntry> entries = new ArrayList<>();

    @Getter
    final private AccountDetails accountDetails;

    public Account(AccountDetails accountDetails) {
        this.accountDetails = checkNotNull(accountDetails);
    }

    public Account(String accountNumber, String name, AccountSide increaseSide) {
        this.accountDetails = new AccountDetailsImpl(accountNumber, name, increaseSide);
    }

    public void addEntry(AccountingEntry entry) {
        checkNotNull(entry);
        checkArgument(entry.getAccountNumber().equals(accountDetails.getAccountNumber()));
        entries.add(entry);
    }

    public BigDecimal getBalance() {
        BigDecimal signum = accountDetails.getIncreaseSide() == AccountSide.DEBIT
                ? BigDecimal.ONE : BigDecimal.ONE.negate();
        return getRawBalance().multiply(signum);
    }

    public BigDecimal getRawBalance() {
        return entries.stream()
                .map(e -> e.getAccountSide() == AccountSide.DEBIT ? e.getAmount() : e.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("entries", entries)
                .add("accountDetails", accountDetails)
                .toString();
    }

}