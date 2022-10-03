package com.lunar.lunarMgmt.api;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlgorhyTest {

    @Test
    public void poketmontAlgoTest(){
        //given
       int [] nums = {3,1,2,3};
       List<Integer>list = new ArrayList<>();
       for(int i=0; i<nums.length; i++){
           list.add(nums[i]);
       }
       list = list.stream().distinct().collect(Collectors.toList());
       if(nums.length/2<list.size()){
           System.out.println(nums.length/2);
       }else{
           System.out.println(list.size());
       }

    }

    @Test
    public void test(){

    }
}
