package de.flapdoodle.swing.tests;

import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.junit.runner.FailureScreenshotTaker;
import org.assertj.swing.junit.runner.ImageFolderCreator;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import static org.assertj.swing.annotation.GUITestFinder.isGUITest;
import static org.assertj.swing.junit.runner.Formatter.testNameFrom;

import java.lang.reflect.Method;

public class GUITestExtension implements Extension, InvocationInterceptor {
	private final FailureScreenshotTaker screenshotTaker;

	public GUITestExtension() {
		screenshotTaker = new FailureScreenshotTaker(new ImageFolderCreator().createImageFolder());
	}

	@VisibleForTesting
	GUITestExtension(FailureScreenshotTaker screenshotTaker) {
		this.screenshotTaker = screenshotTaker;
	}

	@Override
	public void interceptTestMethod(
		Invocation<Void> invocation,
		ReflectiveInvocationContext<Method> invocationContext,
		ExtensionContext extensionContext)
		throws Throwable {
		try {
			invocation.proceed();
		} catch (Throwable t) {
			takeScreenshot(invocationContext.getExecutable());
			throw t;
		}
	}

	private void takeScreenshot(Method method) {
		Class<?> testClass = method.getDeclaringClass();
		if (!(isGUITest(testClass, method))) {
			return;
		}
		screenshotTaker.saveScreenshot(testNameFrom(testClass, method));
	}
}