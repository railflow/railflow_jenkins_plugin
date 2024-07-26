package io.railflow;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Represents a valid or zero run ID.
 *
 * @author Sergey Oplavin
 */
public class RunId implements Serializable {
	private static final String ERROR_MESSAGE = "Test Run ID [%s] is invalid. It must be positive integer or string prefixed with `R`, e.g.: 123, R123, r123";
	private final int runId;

	private RunId(final int runId) {
		this.runId = runId;
	}

	public static RunId create(final int runId) {
		if (runId < 0) {
			throw new IllegalArgumentException(String.format(ERROR_MESSAGE, runId));
		}
		return new RunId(runId);
	}

	public static RunId create(final String runId) {
		return new RunId(getIdFromString(runId));
	}

	private static int getIdFromString(final String runIdStr) {
		final String trimmedRunId = StringUtils.trimToNull(runIdStr);
		if (trimmedRunId == null) {
			return 0;
		}
		final String numericRunId = trimmedRunId.toLowerCase().startsWith("r") ? trimmedRunId.substring(1).trim() : trimmedRunId;
		if (NumberUtils.isParsable(numericRunId)) {
			final int id = NumberUtils.toInt(numericRunId, 0);
			if (id >= 0) {
				return id;
			}
		}
		throw new IllegalArgumentException(String.format(ERROR_MESSAGE, runIdStr));
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final RunId runId1 = (RunId) o;

		return this.runId == runId1.runId;
	}

	@Override
	public int hashCode() {
		return this.runId;
	}

	public int getId() {
		return this.runId;
	}
}
