package com.tarnavskyi.mik.Demo;

import com.tarnavskyi.mik.MyStreamSupport;
import com.tarnavskyi.mik.Spliterators.ArraySpliterator;
import com.tarnavskyi.mik.Spliterators.CollectionSpliterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DemoMain {
    public static void main(String[] args) {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Set<Integer> set = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= 10; i++) {
            stack.push(i);
        }

        System.out.println("Start streaming array and collecting result to list");
        List resultList = (List) doDemoStream(new ArraySpliterator<>(arr), Collectors.toList());
        System.out.println("\nresultList = " + resultList);
        System.out.println("Start streaming list and collecting result to set");
        Set resultSet = (Set) doDemoStream(new CollectionSpliterator<>(list), Collectors.toSet());
        System.out.println("\nresultSet = " + resultSet);
        System.out.println("Start streaming set and collecting result to string");
        String resultString = (String) doDemoStream(new CollectionSpliterator<>(set), Collectors.joining("_"));
        System.out.println("\nresultString = " + resultString);
        System.out.println("Start streaming stack and collecting result to map");
        Map resultMap = (Map) doDemoStream(new CollectionSpliterator<>(stack), Collectors.toMap(i -> i, i -> i));
        System.out.println("\nresultMap = " + resultMap);
    }

    private static Object doDemoStream(Spliterator<Integer> spliterator, Collector chosenCollector) {
        return MyStreamSupport.stream(spliterator)
                .filter(i -> i < 8)
                .skip(2)
                .peek(i -> System.out.print(i + " peek "))
                .limit(3)
                .map(i -> String.valueOf(i).repeat(i))
                .collect(chosenCollector);
    }
}
