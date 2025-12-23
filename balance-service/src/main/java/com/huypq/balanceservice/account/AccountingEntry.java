package com.huypq.balanceservice.account;

import com.huypq.balanceservice.transaction.AccountingTransaction;
import com.huypq.balanceservice.utils.MoreObjects;
import lombok.Getter;

import java.math.BigDecimal;

import static com.huypq.balanceservice.utils.Preconditions.checkArgument;
import static com.huypq.balanceservice.utils.Preconditions.checkNotNull;

public final class AccountingEntry {

    @Getter
    private final BigDecimal amount;

    @Getter
    private final AccountSide accountSide;

    @Getter
    private final String accountNumber;

    private AccountingTransaction transaction;

    private boolean freeze = false;

    public AccountingEntry(BigDecimal amount, String accountNumber, AccountSide accountSide) {
        this.amount = checkNotNull(amount);
        this.accountNumber = checkNotNull(accountNumber);
        this.accountSide = checkNotNull(accountSide);
        checkArgument(amount.signum() == 1, "Accounting entries can't have a negative amount.");
        checkArgument(!accountNumber.isEmpty());
    }

    public AccountingTransaction getTransaction() {
        checkNotNull(transaction, "Getter returning null. You have to set a transaction.");
        return transaction;
    }

    public void setTransaction(AccountingTransaction transaction) {
        checkArgument(!freeze, "An AccountingEntry's transaction can only be set once");
        checkNotNull(transaction);
        this.transaction = transaction;
        freeze = true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("amount", amount.toString())
                .addValue(accountSide)
                .toString();
    }

}
