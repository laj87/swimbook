package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface BookAvailableSwimPort {

    public Boolean execute(String startDateTime, String endDateTime, RequiredModel requiredModel) throws IllegalArgumentException;
}
