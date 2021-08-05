package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface EditVenuePort {

    void execute(String newName, String newType, String newRules, RequiredModel requiredModel);
}
