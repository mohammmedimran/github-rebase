package com.example.demo1.controller;

import com.example.demo1.entity.Address;
import com.example.demo1.entity.Person;
import com.example.demo1.service.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RequestMapping
@RestController
public class BatchController {
    @Autowired
    private RetryService retryService;
    @GetMapping("/test/batch")
    public List<Person> testBatch(){
//        retryService.performTask();
        List<Person> persons= getPersonList();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();
        Future<String> future = executor.submit(()-> performTask(persons.subList(0,1)));
        futures.add(future);
//        Future<String> future1 = executor.submit(()->performTask(persons.subList(11,20)));
//        futures.add(future1);
//        Future<String> future2 = executor.submit(()-> performTask(persons.subList(21,30)));
//        futures.add(future2);
        for (Future<String> f : futures) {
              try{
                  String name = f.get();
                  System.out.println(name);
              }catch(Exception e){

              }
        }
        return persons;
    }
    private List<Person> getPersonList(){
        List<Person> persons = new ArrayList<>();
        Address ad;Person person;
        for(int i=0;i<30;i++){
            if(i==0){
                ad = new Address("bangalore");
                person = new Person(i,"name1",null);
                persons.add(person);
            }
            else if(i>=1 && i<=10){
                ad = new Address("bangalore");
                person = new Person(i,"name1",ad);
                persons.add(person);
            }else if(i>=11 && i<=14){
                ad = new Address("Bambay");
                Person person10 = new Person(i,"name2",ad);
                persons.add(person10);
            }else if(i>14 && i<=20){
                ad = new Address("Bambay");
                person = new Person(i,"name2",null);
                persons.add(person);
            }else{
                ad = new Address("Pune");
                person = new Person(i,"name3",ad);
                persons.add(person);
            }

        }
        return persons;
    }

    public String performTask(List<Person> persons) {
        String name = "";
        for(Person p: persons){
            try {
                retryService.update(p);
            }catch(Exception exception ){
                System.out.println(exception.getMessage());
                return p.getName()+"-----taskfailed----------"+p.getId();
            }
//            System.out.println(name);
        }
//            System.out.println(count++);
        return persons.get(0).getName()+"-----taskpassed";
    }

}
