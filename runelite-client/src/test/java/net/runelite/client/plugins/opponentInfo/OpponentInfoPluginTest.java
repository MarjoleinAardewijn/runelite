package net.runelite.client.plugins.opponentInfo;

import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.plugins.opponentinfo.OpponentInfoConfig;
import net.runelite.client.plugins.opponentinfo.OpponentInfoPlugin;
import net.runelite.client.ui.overlay.OverlayManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class OpponentInfoPluginTest {

	@Spy
	@Bind
	private Client client;

	@Mock
	@Bind
	private OpponentInfoConfig config;

	@Mock
	@Bind
	RuneLiteConfig runeLiteConfig;

	@Mock
	@Bind
	ScheduledExecutorService executor;

	@Mock
	@Bind
	private OverlayManager overlayManager;

	@Inject
	private OpponentInfoPlugin opponentInfoPlugin;

	@Before
	public void before() {
		// Arrange
		MockitoAnnotations.initMocks(this);
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
	}

	@Test
	public void testHPStatusClass() {
		// Arrange
		GameStateChanged gameStateChanged = new GameStateChanged();
		gameStateChanged.setGameState(GameState.LOGIN_SCREEN);
		client.changeWorld(Mockito.any());

		// Act
		opponentInfoPlugin.onGameStateChanged(gameStateChanged);

		// Assert
		assertNotNull(opponentInfoPlugin.getHiscoreEndpoint());
	}
}
