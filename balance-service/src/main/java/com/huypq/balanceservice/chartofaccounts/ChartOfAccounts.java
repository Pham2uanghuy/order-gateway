package com.huypq.balanceservice.chartofaccounts;

import com.huypq.balanceservice.account.AccountDetails;
import com.huypq.balanceservice.utils.MoreObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.huypq.balanceservice.utils.Preconditions.checkArgument;
import static com.huypq.balanceservice.utils.Preconditions.checkNotNull;

public class ChartOfAccounts {
    private final Map<String, AccountDetails> accountNumberToAccountDetails;

    public ChartOfAccounts(Set<AccountDetails> accountDetails) {
        checkNotNull(accountDetails);
        checkArgument(!accountDetails.isEmpty());
        this.accountNumberToAccountDetails = new HashMap<>();
        accountDetails.forEach(ad -> this.accountNumberToAccountDetails.put(ad.getAccountNumber(), ad));
    }

    public Map<String, AccountDetails> getAccountNumberToAccountDetails() {
        return new HashMap<>(accountNumberToAccountDetails);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accounts", accountNumberToAccountDetails.values())
                .toString();
    }
}