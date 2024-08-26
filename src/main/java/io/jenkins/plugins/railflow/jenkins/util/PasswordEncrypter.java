package io.jenkins.plugins.railflow.jenkins.util;

import java.io.Serializable;

import hudson.util.Secret;

/**
 * Provides password encryption/decryption functionality
 *
 * @author Sergey Oplavin
 */
public interface PasswordEncrypter extends Serializable {

	/**
	 * Encrypt the plain text password.
	 *
	 * @param plainTextPassword
	 *            the plain text password
	 * @return the encrypted value
	 */
	Secret encrypt(String plainTextPassword);

	/**
	 * Decrypt the encrypted password
	 *
	 * @param encryptedPassword
	 *            the encrypted password
	 * @return the plain text
	 */
	String decrypt(Secret encryptedPassword);

}
