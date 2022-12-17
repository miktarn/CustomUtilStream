package com.tarnavskyi.mik.Spliterators;

import java.util.Spliterator;
import java.util.function.Consumer;

public class ArraySpliterator<T> implements Spliterator<T> {

    private T [] sourceArray;
    int count = 0;

    public ArraySpliterator(T... sourceArray) {
        this.sourceArray = sourceArray;
    }

    @Override
    public boolean tryAdvance(Consumer action) {
        if (count <= sourceArray.length - 1) {
            action.accept(sourceArray[count]);
            count++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator trySplit() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public long estimateSize() {
        return sourceArray.length;
    }

    @Override
    public int characteristics() {
        throw new RuntimeException("Not implemented");
    }
}
