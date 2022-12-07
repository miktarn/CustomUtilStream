package com.tarnavskyi.mik;

import java.util.Objects;
import java.util.Spliterator;

public class MyStreamSupport {
    static public <In, Out> CustomReferencePipeline <In, Out>  stream(Spliterator<Out> spliterator) {
        Objects.requireNonNull(spliterator);
        return new CustomReferencePipeline.Head<>(spliterator);
    }
}
