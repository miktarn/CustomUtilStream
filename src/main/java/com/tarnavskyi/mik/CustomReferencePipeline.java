package com.tarnavskyi.mik;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public abstract class CustomReferencePipeline<In, Out> extends AbstractPipeline<In, Out> implements Stream<Out> {
    private Spliterator<Out> sourceSpliterator;

    private CustomReferencePipeline<In, Out> prevStage;
    private CustomReferencePipeline<In, Out> nextStage;
    private final CustomReferencePipeline<In, Out> head;
    private boolean isClosed;

    private CustomReferencePipeline(Spliterator<Out> sourceSpliterator) {
        this.sourceSpliterator = sourceSpliterator;
        this.head = this;
    }

    private CustomReferencePipeline(CustomReferencePipeline<In, Out> prevStage) {
        this.prevStage = prevStage;
        this.head = prevStage.head;
        prevStage.nextStage = this;
    }


    public static class Head<In, Out> extends CustomReferencePipeline<In, Out> {

        public Head(Spliterator<Out> sourceSpliterator) {
            super(sourceSpliterator);
        }

        @Override
        protected Consumer opWrapSink(Consumer sink) {
            throw new RuntimeException("Not supposed to be called");
        }
    }


    abstract class Operation<In, Out> extends CustomReferencePipeline<In, Out> {
        public Operation(CustomReferencePipeline<In, Out> prevStage) {
            super(prevStage);
        }

    }

    public Stream<Out> filter(Predicate<? super Out> predicate) {
        checkPermission();
        return new Operation<In, Out>(this) {
            @Override
            Consumer<Out> opWrapSink(Consumer sink) {
                return (elem) -> {
                    if (predicate.test(elem)) {
                        sink.accept(elem);
                    }
                };
            }
        };
    }

    @Override
    public <R> Stream<R> map(Function<? super Out, ? extends R> mapper) {
        checkPermission();
        return (Stream<R>) new Operation<>(this) {
            @Override
            Consumer<Out> opWrapSink(Consumer sink) {
                return (elem) -> sink.accept(mapper.apply(elem));
            }
        };
    }

    @Override
    public Stream<Out> peek(Consumer<? super Out> action) {
        checkPermission();
        return new Operation<In, Out>(this) {
            @Override
            Consumer<Out> opWrapSink(Consumer sink) {
                return (elem) -> {
                    action.accept(elem);
                    sink.accept(elem);
                };
            }
        };
    }

    @Override
    public Stream<Out> limit(long maxSize) {
        checkPermission();
        return new Operation<In, Out>(this) {
            int size = 0;
            @Override
            Consumer<Out> opWrapSink(Consumer sink) {
                return (elem) -> {
                    if (size < maxSize) {
                        size++;
                        sink.accept(elem);
                    }
                };
            }
        };
    }

    @Override
    public Stream<Out> skip(long numberOfSkips) {
        checkPermission();
        return new Operation<In, Out>(this) {
            int skipCounter = 0;
            @Override
            Consumer<Out> opWrapSink(Consumer sink) {
                return (elem) -> {
                    if (skipCounter < numberOfSkips) {
                        skipCounter++;
                    } else {
                        sink.accept(elem);
                    }
                };
            }
        };
    }

    @Override
    public <R, A> R collect(Collector<? super Out, A, R> collector) {
        checkPermission();
        Supplier<A> supplier = collector.supplier();
        BiConsumer<A, ? super Out> accumulator = collector.accumulator();
        A container = supplier.get();

        Consumer<? super Out> chainConsumer = (elem) -> accumulator.accept(container, elem);

        CustomReferencePipeline iterStage = this;
        while (iterStage.prevStage != null) {
            chainConsumer = iterStage.opWrapSink(chainConsumer);
            iterStage = iterStage.prevStage;
        }

        Spliterator<Out> headSpliterator = head.sourceSpliterator;

        while (headSpliterator.tryAdvance(chainConsumer)) {
        }

        close();
        return collector.finisher().apply(container);

    }

    @Deprecated
    public List<Out> collectToList() {
        checkPermission();
        List<Out> result = new ArrayList<>();

        Consumer<Out> ChainConsumer = (elem) -> result.add(elem);

        CustomReferencePipeline iterStage = this;

        while (iterStage.prevStage != null) {
            ChainConsumer = iterStage.opWrapSink(ChainConsumer);
            iterStage = iterStage.prevStage;
        }
        Spliterator<Out> headSpliterator = head.sourceSpliterator;

        while (headSpliterator.tryAdvance(ChainConsumer)) {
        }

        close();
        return result;
    }

    @Override
    public void close() {
        head.isClosed = true;
    }

    private void checkPermission() {
        if (head.isClosed) {
            throw new IllegalStateException("Stream is already closed");
        }
        if (this.nextStage != null) {
            throw new IllegalStateException("This has already been operated upon");
        }
    }
}
