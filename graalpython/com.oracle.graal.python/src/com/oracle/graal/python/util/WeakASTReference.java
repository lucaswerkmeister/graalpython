/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 *
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 *
 * (a) the Software, and
 *
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 *
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 *
 * This license is subject to the following condition:
 *
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.oracle.graal.python.util;

import java.lang.ref.WeakReference;

/**
 * A class to hold on weakly to objects cached in the AST. Be careful when using it! In general, you
 * should only hold on to user objects in single-context mode, so you probably want to check
 * {@link com.oracle.graal.python.PythonLanguage#singleContextAssumption
 * PythonLanguage#singleContextAssumption} in your AST.
 *
 * Furthermore, you will want to consider races. Make sure you don't guard on references being the
 * same as object fields that could change in between the guard and the specialization execution,
 * for example. I.e., if you're weakly holding on to an object argument <emph>and</emph> a field in
 * that object, and that field may change, you cannot just guard on the field being the same as the
 * cached value, for example, because only the argument object is being held alive.
 */
public final class WeakASTReference extends WeakReference<Object> {
    public WeakASTReference(Object referent) {
        super(referent);
    }

    public boolean is(Object obj) {
        return get() != null && get() == obj;
    }

    public boolean isNot(Object obj) {
        return get() != null && get() != obj;
    }

    public boolean notNull() {
        return get() != null;
    }
}
