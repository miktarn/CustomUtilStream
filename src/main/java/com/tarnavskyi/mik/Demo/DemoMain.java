package com.tarnavskyi.mik.Demo;

import com.tarnavskyi.mik.CustomReferencePipeline;
import com.tarnavskyi.mik.MyStreamSupport;
import com.tarnavskyi.mik.Spliterators.ArraySpliterator;
import java.util.List;
import java.util.Spliterator;

public class DemoMain {
    public static void main(String[] args) {
        Spliterator spliterator = new ArraySpliterator<>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        CustomReferencePipeline stream = MyStreamSupport.stream(spliterator)
                .filter(i -> (Integer) i < 6)
                .map(i -> String.valueOf(i).repeat((Integer) i));
        List list = stream.collectToList();
        System.out.println(list);

        //Should throw an exception because we trying to use stream again
        try {
            List shouldThrow = stream.collectToList();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
