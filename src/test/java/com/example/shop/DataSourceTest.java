package com.example.shop;


import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void connectionTest()throws Exception{
        log.info("datasource: {}" , dataSource);
        log.info("connection: {}" , dataSource.getConnection());


    }
}
