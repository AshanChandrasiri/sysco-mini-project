package com.sysco.miniproject.respository.models;

import java.time.ZonedDateTime;

public interface CartDetails {

    Long getId();

    String getName();

    int getTotalItems();

    double getTotalPrice();

    String getLastUpdateDate();

}
