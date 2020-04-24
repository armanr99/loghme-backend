package com.loghme.models.utils.PairKey;

public class PairKey<F, S> {
    private String firstKeyStr;
    private String secondKeyStr;

    public PairKey(F firstKey, S secondKey) {
        this.firstKeyStr = firstKey.toString();
        this.secondKeyStr = secondKey.toString();
    }

    public String getFirstKeyStr() {
        return firstKeyStr;
    }

    public String getSecondKeyStr() {
        return secondKeyStr;
    }
}
