package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.domain.SwimReport;

import java.util.List;

public interface GetSwimReportsPort {

    List<SwimReport> execute(String startDateTime, String endDateTime, RequiredModel requiredModel);
}
