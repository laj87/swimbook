package com.swimbook.swimbook.application.port.out;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface CommitDomainModel {

    void commitDomainModel(RequiredModel requiredModel);

    RequiredModel requestEmptyRequiredModel();
}
