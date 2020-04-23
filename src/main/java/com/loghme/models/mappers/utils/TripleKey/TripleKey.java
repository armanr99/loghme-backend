package com.loghme.models.mappers.utils.TripleKey;

public class TripleKey<F, S, T> {
    private F firstKey;
    private S secondKey;
    private T thirdKey;

    public TripleKey(F firstKey, S secondKey, T thirdKey) {
        this.firstKey = firstKey;
        this.secondKey = secondKey;
        this.thirdKey = thirdKey;
    }

    public String getFirstKeyAsString() {
        return firstKey.toString();
    }

    public String getSecondKeyAsString() {
        return secondKey.toString();
    }

    public String getThirdKeyAsString() {
        return thirdKey.toString();
    }
}
