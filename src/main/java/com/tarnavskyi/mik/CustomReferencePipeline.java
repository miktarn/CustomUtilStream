package com.tarnavskyi.mik;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class CustomReferencePipeline<In, Out> extends AbstractPipeline<In, Out> {
    private Spliterator<Out> sourceSpliterator;

    private CustomReferencePipeline<In, Out> prevStage;
    private final CustomReferencePipeline<In, Out> head;
    private boolean isOver;

    private CustomReferencePipeline(Spliterator<Out> sourceSpliterator) {
        this.sourceSpliterator = sourceSpliterator;
        this.head = this;
    }

    private CustomReferencePipeline(CustomReferencePipeline<In, Out> prevStage) {
        this.prevStage = prevStage;
        this.head = prevStage.head;
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


    abstract class Operation<In, Out> extends  CustomReferencePipeline<In, Out> {
        public Operation(CustomReferencePipeline<In, Out> prevStage) {
            super(prevStage);
        }

    }

   public CustomReferencePipeline<In, Out> filter(Predicate<? super Out> predicate) {
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

    public CustomReferencePipeline<In, Out> map(Function<Out, In> function) {
        return new Operation<In, Out>(this) {
            @Override
            Consumer<Out> opWrapSink(Consumer sink) {
                return (elem) -> sink.accept(function.apply(elem));
            }
        };
    }

    public List<Out> collectToList() {
        checkIsOver();
        List<Out> result = new ArrayList<>();

        Consumer<Out> ChainConsumer = (elem) -> result.add(elem);

        CustomReferencePipeline iterStage = this;

        while(iterStage.prevStage != null) {
            ChainConsumer = iterStage.opWrapSink(ChainConsumer);
            iterStage = iterStage.prevStage;
        }
        Spliterator<Out> headSpliterator = head.sourceSpliterator;

        while (headSpliterator.tryAdvance(ChainConsumer)) {}

        setIsOver();
        return result;
    }

    private void setIsOver() {
        head.isOver = true;
    }

    private void checkIsOver() {
        if (head.isOver) {
            throw new IllegalStateException("Stream is already iterated on this source");
        }
    }
}
