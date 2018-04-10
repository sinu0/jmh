/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package test34;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyBenchmark {

    @State(Scope.Thread)
    public static class StateClass {
        public List<String> testList = new ArrayList<String>();

        @Setup(Level.Trial)
        public void setUpClass() {
            List<String> collect = IntStream.range(0, 1000).mapToObj(number -> UUID.randomUUID()).map(UUID::toString).collect(Collectors.toList());
            testList.addAll(collect);
        }

        @TearDown(Level.Trial)
        void tearDown() {
            testList.clear();
        }
    }

    @Benchmark
    @Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 2)
    public void testMethod(StateClass testClass) {
        testClass.testList.sort(String::compareTo);
    }


}
