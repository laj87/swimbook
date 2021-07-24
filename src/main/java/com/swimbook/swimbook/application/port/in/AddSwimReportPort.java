package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface AddSwimReportPort {

    Boolean execute(String bait, String castingDistance, String castingDirection, String method, String comments, RequiredModel requiredModel) throws IllegalArgumentException;
}
