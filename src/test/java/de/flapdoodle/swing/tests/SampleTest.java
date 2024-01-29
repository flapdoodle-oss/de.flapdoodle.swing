package de.flapdoodle.swing.tests;

import de.flapdoodle.swing.SampleApp;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;

import javax.swing.*;

@Extensions(@ExtendWith(GUITestExtension.class))
public class SampleTest {
	private FrameFixture window;
	private Robot robot;

	@BeforeEach
	protected void onSetUp() {
		FailOnThreadViolationRepaintManager.install();
		robot = BasicRobot.robotWithNewAwtHierarchy();

		JFrame frame = GuiActionRunner.execute(() -> new SampleApp("Sample"));
		window = new FrameFixture(robot, frame);
		window.show(); // shows the frame to test
	}

	@AfterEach
	protected void onTearDown() {
		robot.cleanUp();
		FailOnThreadViolationRepaintManager.uninstall();
	}

	@Test
	public void shouldCopyTextInLabelWhenClickingButton() {
		window.textBox("textToCopy").enterText("Some random text");
		window.button("copyButton").click();
		window.label("copiedText").requireText("Some random text");
	}
}

