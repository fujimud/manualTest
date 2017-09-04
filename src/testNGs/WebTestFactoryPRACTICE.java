package testNGs;

import org.testng.annotations.Factory;

public class WebTestFactoryPRACTICE {
	@Factory
	public Object[] createInstance() {
		Object[] result = new Object[10];
		for (int i = 0; i < 10; i++) {
			result[i] = new WebTest(i*10);
		}
		return result;
	}
}

