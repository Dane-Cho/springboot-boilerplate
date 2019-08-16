package com.ethon.springboot.test.service;

import com.ethon.springboot.test.model.*;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import static com.ethon.springboot.Tables.*;

@Slf4j
@Service
public class TestService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    protected DSLContext jooq;


    public List<UserModel> testJooq() throws Exception {

        List<UserModel> users = jooq.select().from(TBL_USER).fetchInto(UserModel.class);

        return null;
    }
}
