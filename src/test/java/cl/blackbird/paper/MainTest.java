package cl.blackbird.paper;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void testSaludar() throws Exception {
        Main app = new Main();
        assertEquals("la aplicación saludará", "Hola, Mundo!", app.saludar());
    }
}
