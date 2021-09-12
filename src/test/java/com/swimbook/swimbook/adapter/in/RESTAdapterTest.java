package com.swimbook.swimbook.adapter.in;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class RESTAdapterTest {

    @MockBean
    RESTAdapter testAdapter;

    @Test
    void addVenue() {

        testAdapter.addVenue("blackwater", "river", "must use unhooking matt");


    }
}