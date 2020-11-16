package com.example.graphrepresentation.category_company_class;

public class CompanyWise {
    String sName,fPurchaseValue,depreciatedValue,CurrentValue;

    public CompanyWise() {
    }

    public CompanyWise(String sName, String fPurchaseValue, String depreciatedValue, String currentValue) {
        this.sName = sName;
        this.fPurchaseValue = fPurchaseValue;
        this.depreciatedValue = depreciatedValue;
        CurrentValue = currentValue;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getfPurchaseValue() {
        return fPurchaseValue;
    }

    public void setfPurchaseValue(String fPurchaseValue) {
        this.fPurchaseValue = fPurchaseValue;
    }

    public String getDepreciatedValue() {
        return depreciatedValue;
    }

    public void setDepreciatedValue(String depreciatedValue) {
        this.depreciatedValue = depreciatedValue;
    }

    public String getCurrentValue() {
        return CurrentValue;
    }

    public void setCurrentValue(String currentValue) {
        CurrentValue = currentValue;
    }
}
