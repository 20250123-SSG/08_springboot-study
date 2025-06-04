package com.minkook.section01;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EntityManagerLifeCycleTest {
    public int[] solution(int[] numbers) {
        Set<Integer> set = new HashSet<>();

        for(int i = 0; i<numbers.length; i++){
            for(int j = i+1; j<numbers.length; j++){
                set.add(numbers[i] + numbers[j]);
            }
        }
        //System.out.println(set);
        int[] answer = {};

        answer = set.stream().mapToInt(Integer::intValue).toArray();

        Arrays.sort(answer);

        return answer;
    }
}
