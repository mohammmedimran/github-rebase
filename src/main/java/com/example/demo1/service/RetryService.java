package com.example.demo1.service;

import com.example.demo1.entity.Address;
import com.example.demo1.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetryService {
    private int count=0;
    private int id;
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 500))
    public void update(Person p) {
        count++;
        System.out.println("Attempting operation..."+count);
        if(count ==4 && p.getName().equals("name1")){
            p.setAddress(new Address("setting city"));
        }
        String city = p.getAddress().getCity();

    }


}
