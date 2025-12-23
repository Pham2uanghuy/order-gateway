package com.huypq.balanceservice.account;

import com.huypq.balanceservice.utils.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.huypq.balanceservice.utils.Preconditions.checkArgument;
import static com.huypq.balanceservice.utils.Preconditions.checkNotNull;

@EqualsAndHashCode
public class AccountDetailsImpl implements AccountDetails{

    @Getter
    private final String accountNumber;

    @Getter
    private final AccountSide increaseSide;

    @Getter
    private final String name;

    public AccountDetailsImpl(String accountNumber, String name, AccountSide increaseSide) {
        this.accountNumber = checkNotNull(accountNumber);
        this.increaseSide = checkNotNull(increaseSide);
        this.name = checkNotNull(name);
        checkArgument(!accountNumber.isEmpty());
        checkArgument(!name.isEmpty());
    }
    @Override
    public String getAccountNumber() {
        return "";
    }

    @Override
    public AccountSide getIncreaseSide() {
        return null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountNumber", accountNumber)
                .add("name", name)
                .add("increaseSide", increaseSide)
                .toString();
    }
}
