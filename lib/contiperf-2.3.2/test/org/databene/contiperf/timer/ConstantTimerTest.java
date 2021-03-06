/*
 * (c) Copyright 2012 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU Lesser General Public License (LGPL), Eclipse Public License (EPL) 
 * and the BSD License.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.contiperf.timer;

import static org.junit.Assert.*;

import org.databene.contiperf.WaitTimer;
import org.junit.Test;

/**
 * Tests the {@link ConstantTimer}.<br/><br/>
 * Created: 06.04.2012 18:10:41
 * @since 2.1.0
 * @author Volker Bergmann
 */
public class ConstantTimerTest {

	@Test
	public void testEmptyInitialization() throws Exception {
		WaitTimer timer = ConstantTimer.class.newInstance();
		timer.init(new double[0]);
		for (int i = 0; i < 1000; i++)
			assertEquals(1000, timer.getWaitTime());
	}
	
	@Test
	public void testNormalInitialization() throws Exception {
		WaitTimer timer = ConstantTimer.class.newInstance();
		timer.init(new double[] { 123 });
		for (int i = 0; i < 1000; i++)
			assertEquals(123, timer.getWaitTime());
	}
	
	@Test
	public void testTooManyParams() throws Exception {
		WaitTimer timer = ConstantTimer.class.newInstance();
		timer.init(new double[] { 234, 456 });
		for (int i = 0; i < 1000; i++)
			assertEquals(234, timer.getWaitTime());
	}
	
}
