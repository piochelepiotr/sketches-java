/* Unless explicitly stated otherwise all files in this repository are licensed under the Apache License 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2021 Datadog, Inc.
 */

package com.datadoghq.sketch.ddsketch.benchmarks;

import com.datadoghq.sketch.ddsketch.DDSketch;
import com.datadoghq.sketch.ddsketch.DDSketchOption;
import com.datadoghq.sketch.ddsketch.DataGenerator;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public abstract class BuiltSketchState {
    @Param
    DataGenerator generator;

    @Param({"NANOSECONDS", "MICROSECONDS", "MILLISECONDS"})
    TimeUnit unit;

    @Param
    DDSketchOption sketchOption;

    @Param("100000")
    int count;

    @Param({"0.01"})
    double relativeAccuracy;

    DDSketch sketch;

    @Setup(Level.Trial)
    public void init() {
        this.sketch = sketchOption.create(relativeAccuracy);
        for (int i = 0; i < count; ++i) {
            sketch.accept(unit.toNanos(Math.abs(Math.round(generator.nextValue()))));
        }
    }
}
