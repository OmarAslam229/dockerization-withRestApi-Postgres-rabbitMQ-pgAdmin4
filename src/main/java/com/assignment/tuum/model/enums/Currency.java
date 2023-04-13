package com.assignment.tuum.model.enums;

public enum Currency {
    EUR,
    SEK,
    GBP,
    USD;

    public static boolean contains(Currency c)
    {
        for(Currency Currency:values())
            if (Currency.name().equals(c))
                return true;
        return false;
    }
}
