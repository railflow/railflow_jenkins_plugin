package io.railflow.commons;

/**
 * Available options for creating and updating test case.
 *
 * @author Liu Yang
 */
public enum UploadMode {
	CREATE_NO_UPDATE(0, true, false, "Create new test cases and do not overwrite existing ones"),
	CREATE_UPDATE(1, true, true, "Create new cases and overwrite existing ones"),
	NO_CREATE_UPDATE(2, false, true, "Do not create new cases and overwrite existing ones"),
	NO_CREATE_NO_UPDATE(3, false, false, "Do not create new cases and do not overwrite existing ones");

	private final int id;
	private final boolean create;
	private final boolean update;
	private final String message;

	UploadMode(final int id, final boolean create, final boolean update, final String message) {
		this.id = id;
		this.create = create;
		this.update = update;
		this.message = message;
	}

	public int getId() {
		return this.id;
	}

	public boolean isCreate() {
		return this.create;
	}

	public boolean isUpdate() {
		return this.update;
	}

	public String getMessage() {
		return this.message;
	}
}
