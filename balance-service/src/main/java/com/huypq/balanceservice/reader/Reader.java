package com.huypq.balanceservice.reader;

import com.huypq.balanceservice.Ledger;

public interface Reader {
    Ledger read();
}