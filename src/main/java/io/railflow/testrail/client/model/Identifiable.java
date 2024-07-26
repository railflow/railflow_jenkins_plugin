package io.railflow.testrail.client.model;

/**
 * Interface foridentifiable objects.
 *
 * @author Sergey Oplavin
 *
 */
public interface Identifiable {

	/**
	 * @return id of this object.
	 */
	int getId();

	/**
	 * Sets the id to this object.
	 *
	 * @param id
	 *            the id.
	 */
	void setId(int id);

	/**
	 * @return type of this object.
	 */
	ObjectType getType();
}
