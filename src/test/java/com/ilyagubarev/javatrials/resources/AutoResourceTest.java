/*
 * Project:  Java Trials
 * Outline:  Common Java programming practices
 *
 * File:     AutoResourceTest.xml
 * Folder:   /src/test/java/com/ilyagubarev/javatrials/resources
 * Revision: 1.00, 13 January 2015
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

import org.junit.Assert;
import org.junit.Test;

import com.ilyagubarev.javatrials.resources.errors.ClosureException;
import com.ilyagubarev.javatrials.resources.errors.CreationException;
import com.ilyagubarev.javatrials.resources.errors.FirstInvocationException;
import com.ilyagubarev.javatrials.resources.errors.InvocationException;
import com.ilyagubarev.javatrials.resources.errors.SecondInvocationException;

public class AutoResourceTest {

    @Test(expected = ClosureException.class)
    public void failOnClosure() throws Exception {
        try (AutoResource resource = new AutoResource(false, false, true)) {
            resource.invokeFirst();
            resource.invokeSecond();
        }
    }

    @Test(expected = CreationException.class)
    public void failOnCreation() throws Exception {
        try (AutoResource resource = new AutoResource(true, true, true)) {
            resource.invokeFirst();
            resource.invokeSecond();
        }
    }

    @Test(expected = InvocationException.class)
    public void failOnInvocation() throws Exception {
        try (AutoResource resource = new AutoResource(false, true, false)) {
            resource.invokeFirst();
            resource.invokeSecond();
        }
    }

    @Test(expected = InvocationException.class)
    public void failOnInvocationWithClosureCheck() throws Exception {
        AutoResource res = null;
        try (AutoResource resource = new AutoResource(false, true, false)) {
            res = resource;
            resource.invokeFirst();
            resource.invokeSecond();
        } finally {
            Assert.assertNotNull(res);
            Assert.assertTrue(res.isClosed());
        }
    }

    @Test(expected = InvocationException.class)
    public void failOnInvocationRethrown() throws Exception {
        try (AutoResource resource = new AutoResource(false, true, false)) {
            resource.invokeFirst();
            resource.invokeSecond();
        } catch (FirstInvocationException | SecondInvocationException e) {
            throw e;
        }
    }

    @Test(expected = InvocationException.class)
    public void failOnInvocationRethrownAuto() throws Exception {
        try (AutoResource resource = new AutoResource(false, true, true)) {
            resource.invokeFirst();
            resource.invokeSecond();
        }
    }
}
