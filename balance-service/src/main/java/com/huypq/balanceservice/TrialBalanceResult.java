package com.huypq.balanceservice;

import com.huypq.balanceservice.account.Account;
import com.huypq.balanceservice.account.AccountDetails;
import com.huypq.balanceservice.utils.MoreObjects;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.huypq.balanceservice.utils.Preconditions.checkArgument;
import static com.huypq.balanceservice.utils.Preconditions.checkNotNull;

public class TrialBalanceResult {
    final private Map<AccountDetails, BigDecimal> accountDetailsToBalance =
            new TreeMap<>(Comparator.comparing(AccountDetails::getAccountNumber));
    final private long creationTimestamp;
    @Getter
    final private boolean isBalanced;

    public TrialBalanceResult(Set<Account> accounts) {
        checkNotNull(accounts);
        checkArgument(!accounts.isEmpty());
        accounts.forEach(
                a -> accountDetailsToBalance.put(a.getAccountDetails(), a.getRawBalance())
        );
        creationTimestamp = Instant.now().toEpochMilli();
        BigDecimal balance = accounts.stream()
                .reduce(
                        BigDecimal.ZERO,
                        (acc, next) -> acc.add(next.getRawBalance()),
                        BigDecimal::add
                );
        isBalanced = balance.equals(BigDecimal.ZERO);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("isBalanced", isBalanced)
                .add("accountDetailsToBalance", accountDetailsToBalance)
                .add("creationTimestamp", Instant.ofEpochMilli(creationTimestamp).toString())
                .toString();
    }
}