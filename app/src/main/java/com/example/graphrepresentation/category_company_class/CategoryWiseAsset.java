package com.example.graphrepresentation.category_company_class;

public class CategoryWiseAsset {
    String sCategory,fPurchaseValue,fDepValue,fCurrentValue;

    public CategoryWiseAsset() {
    }

    public CategoryWiseAsset(String sCategory, String fPurchaseValue, String fDepValue, String fCurrentValue) {
        this.sCategory = sCategory;
        this.fPurchaseValue = fPurchaseValue;
        this.fDepValue = fDepValue;
        this.fCurrentValue = fCurrentValue;
    }

    public String getsCategory() {
        return sCategory;
    }

    public void setsCategory(String sCategory) {
        this.sCategory = sCategory;
    }

    public String getfPurchaseValue() {
        return fPurchaseValue;
    }

    public void setfPurchaseValue(String fPurchaseValue) {
        this.fPurchaseValue = fPurchaseValue;
    }

    public String getfDepValue() {
        return fDepValue;
    }

    public void setfDepValue(String fDepValue) {
        this.fDepValue = fDepValue;
    }

    public String getfCurrentValue() {
        return fCurrentValue;
    }

    public void setfCurrentValue(String fCurrentValue) {
        this.fCurrentValue = fCurrentValue;
    }
}
