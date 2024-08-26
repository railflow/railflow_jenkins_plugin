package io.jenkins.plugins.railflow.jenkins.util;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Liu Yang
 */
public final class ProjectUtils {
	private static final String UNKNOWN_VERSION = "unknown";

	private ProjectUtils() {
	}

	public static String getPluginVersion() {
		try {
			final Properties properties = new Properties();
			properties.load(ProjectUtils.class.getClassLoader().getResourceAsStream("railflow.properties"));
			final String pluginVersion = properties.getProperty("version");
			if (StringUtils.isEmpty(pluginVersion)) {
				return UNKNOWN_VERSION;
			}
			return pluginVersion;
		} catch (final Exception e) {
			return UNKNOWN_VERSION;
		}
	}
}
