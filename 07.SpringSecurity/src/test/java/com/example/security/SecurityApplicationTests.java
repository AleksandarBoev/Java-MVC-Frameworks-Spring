package com.example.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityApplicationTests {
    @Test
    public void contextLoads() {
        byte[] result = Base64.getDecoder().decode("MTIz");
        String stringResult = new String(result);
        System.out.println(stringResult);
    }
}
