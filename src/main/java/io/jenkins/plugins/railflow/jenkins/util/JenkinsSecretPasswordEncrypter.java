package io.jenkins.plugins.railflow.jenkins.util;

import hudson.util.Secret;

/**
 * Default implementation which uses Jenkins {@link Secret};
 *
 * @author Sergey Oplavin
 */
public enum JenkinsSecretPasswordEncrypter implements PasswordEncrypter {
	THE_INSTANCE;

	@Override
	public Secret encrypt(String plainTextPassword) {
		return plainTextPassword != null ? Secret.fromString(plainTextPassword) : null;
	}

	@Override
	public String decrypt(Secret encryptedPassword) {
		return encryptedPassword != null ? encryptedPassword.getPlainText() : null;
	}
}
