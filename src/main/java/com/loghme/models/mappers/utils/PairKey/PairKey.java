package com.loghme.models.mappers.utils.PairKey;

public class PairKey<F, S> {
    private F firstKey;
    private S secondKey;

    public PairKey(F firstKey, S secondKey) {
        this.firstKey = firstKey;
        this.secondKey = secondKey;
    }

    public F getFirstKey() {
        return firstKey;
    }

    public S getSecondKey() {
        return secondKey;
    }
}
