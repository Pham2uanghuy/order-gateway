package com.huypq.balanceservice.transaction;

import com.huypq.balanceservice.account.Account;
import com.huypq.balanceservice.account.AccountSide;
import com.huypq.balanceservice.account.AccountingEntry;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static com.huypq.balanceservice.utils.Preconditions.checkArgument;
import static com.huypq.balanceservice.utils.Preconditions.checkNotNull;
import org.springframework.lang.Nullable;


public class AccountingTransaction {

    private final Set<AccountingEntry> entries;

    @Getter
    private final Long bookingDateTimestamp;

    @Getter
    private final Map<String, String> info;

    public AccountingTransaction(Set<AccountingEntry> entries,
                                 @Nullable  Map<String, String> info,
                                 Long bookingDateTimestamp) {
        if (info == null) info = new HashMap<>();
        this.info = info;
        this.entries = checkNotNull(entries);
        this.bookingDateTimestamp = bookingDateTimestamp;
        checkArgument(!entries.isEmpty());
        checkArgument(entries.size() >= 2, "A transaction consists of at least two entries");
        checkArgument(isBalanced(), "Transaction unbalanced");
        entries.forEach(e -> e.setTransaction(this));
    }

    public AccountingTransaction(Set<AccountingEntry> entries, Map<String, String> info) {
        this(entries, info, Instant.now().toEpochMilli());
    }

    public AccountingTransaction(Set<AccountingEntry> entries) {
        this(entries, null, Instant.now().toEpochMilli());
    }

    public boolean isBalanced() {
        BigDecimal debits = entries.stream().map(e -> e.getAccountSide() == AccountSide.DEBIT ?
                e.getAmount() : e.getAmount().negate()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return debits.compareTo(BigDecimal.ZERO) == 0;
    }

    public List<AccountingEntry> getEntries() {
        return new ArrayList<>(entries);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction ")
                .append(Instant.ofEpochMilli(bookingDateTimestamp).toString())
                .append("\n");
        entries.forEach(e -> sb.append(e).append("\n"));
        return sb.toString();
    }

}
