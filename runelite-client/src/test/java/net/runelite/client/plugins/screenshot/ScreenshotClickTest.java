/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.screenshot;


import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.Notifier;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.ClientUI;
import net.runelite.client.ui.DrawManager;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ImageCapture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScreenshotClickTest
{

	@Mock
	@Bind
	private Client client;

	@Mock
	@Bind
	private ScreenshotConfig screenshotConfig;

	@Spy
	@Bind
	private ScreenshotPlugin screenshotPlugin;

	@Mock
	@Bind
	Notifier notifier;

	@Mock
	@Bind
	ClientUI clientUi;

	@Mock
	@Bind
	DrawManager drawManager;

	@Mock
	@Bind
	private OverlayManager overlayManager;

	@Mock
	@Bind
	private InfoBoxManager infoBoxManager;

	@Mock
	@Bind
	RuneLiteConfig config;

	@Mock
	@Bind
	ScheduledExecutorService executor;

	@Mock
	@Bind
	ImageCapture imageCapture;

	@Mock
	@Bind
	ScreenshotOverlay screenshotOverlay;

	@Before
	public void before() {
		// Arrange
		MockitoAnnotations.initMocks(this);
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);

	}

	@Test
	public void testManualScreenshot() {
		// Arrange
		client.setGameState(GameState.LOGGED_IN);

		// Act
		System.out.println(screenshotPlugin.getName());
		screenshotPlugin.manualScreenshot();

		// Assert
		verify(screenshotPlugin, times(1)).manualScreenshot();
		verify(client, times(1)).getGameState();
	}

	@Test
	public void testManualScreenshotLoginScreen() {
		// Arrange
		client.setGameState(GameState.LOGIN_SCREEN);

		// Act
		System.out.println(screenshotPlugin.getName());
		screenshotPlugin.manualScreenshot();

		// Assert
		verify(screenshotPlugin, times(1)).manualScreenshot();
		verify(client, times(1)).getGameState();
		verify(screenshotOverlay, times(0)).queueForTimestamp(
				Mockito.any());
	}
}
