package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface BlockSwimBookerPort {

    void execute(String startDateTime, String endDateTime, RequiredModel requiredModel);
}
