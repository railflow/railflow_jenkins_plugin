package io.jenkins.plugins.railflow.jenkins.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.slf4j.event.Level;

import hudson.util.FormValidation;

public class RailflowUtilsTest {

    @Test
    public void getSl4jToJULMappingTest(){
        assertLogLevel(java.util.logging.Level.FINEST, Level.TRACE);
        assertLogLevel(java.util.logging.Level.FINE, Level.DEBUG);
        assertLogLevel(java.util.logging.Level.INFO, Level.INFO);
        assertLogLevel(java.util.logging.Level.WARNING, Level.WARN);
        assertLogLevel(java.util.logging.Level.SEVERE, Level.ERROR);
        assertException(null, "level is null");
    }

    private void assertLogLevel(final java.util.logging.Level expectedLevel, final Level incomingSl4jLevel) {
        assertEquals(expectedLevel, RailflowUtils.getSl4jToJULMapping(incomingSl4jLevel));
    }

    private void assertException(final Level incomingSl4jLevel, final String message) {
        final Throwable exception = assertThrows(NullPointerException.class, () -> RailflowUtils.getSl4jToJULMapping(incomingSl4jLevel));
        assertEquals(message, exception.getMessage());
    }

	@Test
	public void validateRunId_empty_optional() {
		assertEquals(FormValidation.ok(), RailflowUtils.validateRunId(null));
		assertEquals(FormValidation.ok(), RailflowUtils.validateRunId(""));
		assertEquals(FormValidation.ok(), RailflowUtils.validateRunId("   "));
	}

	@Test
	public void validateRunId_valid_runId() {
		assertEquals(FormValidation.ok(), RailflowUtils.validateRunId("123"));
		assertEquals(FormValidation.ok(), RailflowUtils.validateRunId("r123"));
		assertEquals(FormValidation.ok(), RailflowUtils.validateRunId("R123"));
	}

	@Test
	public void validateRunId_invalid_runId() {
		final String errorMessage = "The value of Test Run ID must be positive integer or string prefixed with `R`, e.g.: 123, R123, r123";
		assertEquals(errorMessage, RailflowUtils.validateRunId("a123").getMessage());
		assertEquals(errorMessage, RailflowUtils.validateRunId("r123a").getMessage());
		assertEquals(errorMessage, RailflowUtils.validateRunId("r-123").getMessage());
	}
}
