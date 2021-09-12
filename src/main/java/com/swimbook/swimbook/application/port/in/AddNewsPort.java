package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;

public interface AddNewsPort {

    void execute(String title, String uploadDate, String content, RequiredModel requiredmodel);
}
