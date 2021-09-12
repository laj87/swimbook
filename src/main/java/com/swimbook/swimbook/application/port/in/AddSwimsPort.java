package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface AddSwimsPort {

    void execute(String type, int capacity, String swimInfo, RequiredModel requiredModel);
}
