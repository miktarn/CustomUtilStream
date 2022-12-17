package com.tarnavskyi.mik;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class NotImplemented<In, Out> {

    public IntStream mapToInt(ToIntFunction<? super Out> mapper) {
        return null;
    }

    public LongStream mapToLong(ToLongFunction<? super Out> mapper) {
        return null;
    }

    public DoubleStream mapToDouble(ToDoubleFunction<? super Out> mapper) {
        return null;
    }

    public <R> Stream<R> flatMap(Function<? super Out, ? extends Stream<? extends R>> mapper) {
        return null;
    }

    public IntStream flatMapToInt(Function<? super Out, ? extends IntStream> mapper) {
        return null;
    }

    public LongStream flatMapToLong(Function<? super Out, ? extends LongStream> mapper) {
        return null;
    }

    public DoubleStream flatMapToDouble(Function<? super Out, ? extends DoubleStream> mapper) {
        return null;
    }

    public Stream<Out> distinct() {
        return null;
    }

    public Stream<Out> sorted() {
        return null;
    }

    public Stream<Out> sorted(Comparator<? super Out> comparator) {
        return null;
    }

    public Stream<Out> peek(Consumer<? super Out> action) {
        return null;
    }

    public Stream<Out> limit(long maxSize) {
        return null;
    }

    public Stream<Out> skip(long n) {
        return null;
    }

    public void forEach(Consumer<? super Out> action) {

    }

    public void forEachOrdered(Consumer<? super Out> action) {

    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <A> A[] toArray(IntFunction<A[]> generator) {
        return null;
    }

    public Out reduce(Out identity, BinaryOperator<Out> accumulator) {
        return null;
    }

    public Optional<Out> reduce(BinaryOperator<Out> accumulator) {
        return Optional.empty();
    }

    public <U> U reduce(U identity, BiFunction<U, ? super Out, U> accumulator, BinaryOperator<U> combiner) {
        return null;
    }

    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Out> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    public <R, A> R collect(Collector<? super Out, A, R> collector) {
        return null;
    }

    public Optional<Out> min(Comparator<? super Out> comparator) {
        return Optional.empty();
    }

    public Optional<Out> max(Comparator<? super Out> comparator) {
        return Optional.empty();
    }

    public long count() {
        return 0;
    }

    public boolean anyMatch(Predicate<? super Out> predicate) {
        return false;
    }

    public boolean allMatch(Predicate<? super Out> predicate) {
        return false;
    }

    public boolean noneMatch(Predicate<? super Out> predicate) {
        return false;
    }

    public Optional<Out> findFirst() {
        return Optional.empty();
    }

    public Optional<Out> findAny() {
        return Optional.empty();
    }

    public Iterator<Out> iterator() {
        return null;
    }

    public Spliterator<Out> spliterator() {
        return null;
    }

    public boolean isParallel() {
        return false;
    }

    public Stream<Out> sequential() {
        return null;
    }

    public Stream<Out> parallel() {
        return null;
    }

    public Stream<Out> unordered() {
        return null;
    }

    public Stream<Out> onClose(Runnable closeHandler) {
        return null;
    }
}
