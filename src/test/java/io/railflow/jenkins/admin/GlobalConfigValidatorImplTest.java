package io.railflow.jenkins.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import hudson.util.Secret;
import jenkins.model.Jenkins;

/**
 * Tests for {@link GlobalConfigValidatorImpl}.
 *
 * @author Sergey Oplavin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Jenkins.class, Secret.class })
public class GlobalConfigValidatorImplTest {

	private final GlobalConfigValidatorImpl validator = GlobalConfigValidatorImpl.THE_INSTANCE;
	private final Jenkins jenkins = mock(Jenkins.class);
	private GlobalConfig config;

	@Before
	public void setUp() {
		PowerMockito.mockStatic(Jenkins.class);
		PowerMockito.when(Jenkins.get()).thenReturn(this.jenkins);
		this.config = new GlobalConfig();
	}

	@Test
	public void validate_valid() {

		this.config.setLicenseContent(mock(Secret.class));
		this.config.setTestRailServers(Collections.singletonList(new TestRailServerConfig()));
		this.validator.validate(this.config);
	}

	@Test
	public void validate_globalConfig_is_null_error() {
		final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> this.validator.validate(this.config));
		assertEquals("Railflow license is missing, please upload a valid license using Jenkins Global Configuration page",
				illegalArgumentException.getMessage());
	}

	@Test
	public void validate_testRailServers_is_null_error() {
		this.config.setLicenseContent(mock(Secret.class));
		this.config.setTestRailServers(null);
		final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> this.validator.validate(this.config));
		assertEquals("TestRail Server list is null, please add at least one TestRail server on Jenkins Global Configuration page",
				illegalArgumentException.getMessage());
	}

	@Test
	public void validate_testRailServers_is_empty_error() {
		this.config.setLicenseContent(mock(Secret.class));
		final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> this.validator.validate(this.config));
		assertEquals("TestRail Server list is empty, please add at least one TestRail server on Jenkins Global Configuration page",
				illegalArgumentException.getMessage());
	}

}