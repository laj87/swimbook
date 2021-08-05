package com.swimbook.swimbook.application.port.in;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.domain.Swim;
import java.util.List;

public interface GetAvailableSwimsPort {

    List<Swim> execute(String date, String time, RequiredModel requiredModel);
}
