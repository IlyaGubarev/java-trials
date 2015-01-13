/*
 * Project:  Java Trials
 * Outline:  Common Java programming practices
 *
 * File:     AutoResource.xml
 * Folder:   /src/main/java/com/ilyagubarev/javatrials/resources
 * Revision: 1.04, 13 January 2015
 * Created:  13 January 2015
 * Authors:  Ilya Gubarev
 *
 * Copyright (c) 2015 Ilya Gubarev.
 * Contact information is available at "http://www.ilyagubarev.com".
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       "http://www.opensource.org/licenses/MIT".
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
package com.ilyagubarev.javatrials.resources;

import com.ilyagubarev.javatrials.resources.errors.ClosureException;
import com.ilyagubarev.javatrials.resources.errors.CreationException;
import com.ilyagubarev.javatrials.resources.errors.FirstInvocationException;
import com.ilyagubarev.javatrials.resources.errors.SecondInvocationException;

public class AutoResource implements AutoCloseable {

    private final boolean failOnClosure;
    private final boolean failOnInvocation;

    private boolean closed;
    private boolean created;

    public AutoResource(boolean failOnCreation, boolean failOnInvocation,
            boolean failOnClosure) throws CreationException {
        if (failOnCreation) {
            throw new CreationException();
        }
        this.failOnClosure = failOnClosure;
        this.failOnInvocation = failOnInvocation;
        created = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isCreated() {
        return created;
    }

    @Override
    public void close() throws Exception {
        if (failOnClosure) {
            throw new ClosureException();
        }
        closed = true;
    }

    public void invokeFirst() throws FirstInvocationException {
        if (failOnInvocation) {
            throw new FirstInvocationException();
        }
    }

    public void invokeSecond() throws SecondInvocationException {
        if (failOnInvocation) {
            throw new SecondInvocationException();
        }
    }
}
