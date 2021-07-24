package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface CreateFishingVenuePort {

    public Boolean execute(String name, String type, String rules, RequiredModel requiredModel) throws IllegalArgumentException;
}
