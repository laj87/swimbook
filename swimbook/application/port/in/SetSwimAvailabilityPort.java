package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface SetSwimAvailabilityPort {

    void execute(RequiredModel requiredModel);

    void execute(String startDateTime, String endDateTime, String reason, RequiredModel requiredModel);
}
