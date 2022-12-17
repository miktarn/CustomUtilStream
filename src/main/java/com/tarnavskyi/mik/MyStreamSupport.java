package com.tarnavskyi.mik;

import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Stream;

public class MyStreamSupport {
    static public <Out> Stream<Out> stream(Spliterator<Out> spliterator) {
        Objects.requireNonNull(spliterator);
        return new CustomReferencePipeline.Head<>(spliterator);
    }
}
