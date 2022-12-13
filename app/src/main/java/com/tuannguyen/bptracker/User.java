package com.tuannguyen.bptracker;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class User {
    public String fullname, email;
    public List<Integer> systolic,diastolic,pulse,weight;
    public User(){
    }
    public User(String fullname,String email,List systolic,List diastolic,List pulse,List weight){
        this.fullname =fullname;
        this.email =email;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.weight = weight;
    }
}
