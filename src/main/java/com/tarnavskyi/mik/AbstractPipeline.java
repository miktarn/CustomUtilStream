package com.tarnavskyi.mik;

import java.util.function.Consumer;

public abstract class AbstractPipeline<In, Out>  extends NotImplemented<In, Out>{

   abstract Consumer<Out> opWrapSink(Consumer<In> sink);
}
