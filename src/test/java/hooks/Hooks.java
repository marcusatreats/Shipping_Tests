package hooks;

import com.yourcompany.shipping.world.ShippingWorld;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private final ShippingWorld world;

    // Cucumber injects the shared World via constructor
    public Hooks(ShippingWorld world) {
        this.world = world;
    }

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("▶ Starting scenario: " + scenario.getName());
        world.init();
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("■ Finished scenario: " + scenario.getName()
                + " | Status: " + scenario.getStatus());
        world.tearDown(scenario);
    }
}
