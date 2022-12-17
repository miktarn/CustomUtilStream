package com.tarnavskyi.mik.Spliterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CollectionSpliterator<T> implements Spliterator<T> {

    private final Collection<T> sourceCollection;
    private final Iterator<T> iterator;

    public CollectionSpliterator(Collection<T> list) {
        this.sourceCollection = list;
        this.iterator = sourceCollection.iterator();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (iterator.hasNext()) {
            action.accept(iterator.next());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<T> trySplit() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public long estimateSize() {
        return sourceCollection.size();
    }

    @Override
    public int characteristics() {
        throw new RuntimeException("Not implemented");
    }
}
