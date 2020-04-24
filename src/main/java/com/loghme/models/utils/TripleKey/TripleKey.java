package com.loghme.models.utils.TripleKey;

public class TripleKey<F, S, T> {
    private String firstKeyStr;
    private String secondKeyStr;
    private String thirdKeyStr;

    public TripleKey(F firstKey, S secondKey, T thirdKey) {
        this.firstKeyStr = firstKey.toString();
        this.secondKeyStr = secondKey.toString();
        this.thirdKeyStr = thirdKey.toString();
    }

    public String getFirstKeyStr() {
        return firstKeyStr;
    }

    public String getSecondKeyAsStr() {
        return secondKeyStr;
    }

    public String getThirdKeyAsStr() {
        return thirdKeyStr;
    }
}
