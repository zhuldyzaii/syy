package com.example.syy.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Brand {

    private String brandName;
    private String status;
    private String certificateUrl;

    // Пустой конструктор необходим для Firestore
    public Brand() {
    }

    public Brand(String brandName, String status, String certificateUrl) {
        this.brandName = brandName;
        this.status = status;
        this.certificateUrl = certificateUrl;
    }

    // Геттеры и сеттеры
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }
}
