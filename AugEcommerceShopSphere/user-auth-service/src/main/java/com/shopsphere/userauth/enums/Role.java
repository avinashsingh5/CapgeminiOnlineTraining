package com.shopsphere.userauth.enums;


public enum Role {

    CUSTOMER,
    ADMIN;


    public String toSpringRole() {
        return "ROLE_" + this.name();
    }
}
