package io.jenkins.plugins.railflow.jenkins.admin;

/**
 * Default implementation of {@link GlobalConfigFactory}
 * 
 * @author Sergey Oplavin
 */
public enum GlobalConfigFactoryImpl implements GlobalConfigFactory {
	THE_INSTANCE;

	@Override
	public GlobalConfig create() {
		return GlobalConfig.all().get(GlobalConfig.class);
	}
}
