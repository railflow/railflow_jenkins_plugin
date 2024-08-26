package io.jenkins.plugins.railflow.testrail.client.model;

/**
 * Indicates that object has name.
 *
 * @author Sergey Oplavin
 *
 */
public interface HasName extends Identifiable {

	/**
	 * @return name.
	 */
	String getName();

	/**
	 * Sets name
	 *
	 * @param name
	 *            name.
	 */
	void setName(String name);
}
