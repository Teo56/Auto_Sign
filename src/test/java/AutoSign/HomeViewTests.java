package AutoSign;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

// Class to test the get and set methods of HomeView
public class HomeViewTests {

    @Test
    void getURLTest() {
        HomeView home = new HomeView(); // Create new HomeView
        home.setURL("https://www.youtube.com/watch?v=8W7ec-FoUJ0"); // Set url
        String result_test = home.getURL(); // Get url

        Assert.assertEquals("https://www.youtube.com/watch?v=8W7ec-FoUJ0", result_test); // Check the gotten url is the same as the expected
    }

    @Test
    void setURLTest() {
        HomeView home = new HomeView(); // Create HomeView
        home.setURL("https://www.youtube.com/watch?v=B-cMJ1qJ0kQ&t=4s"); // Set url

        Assert.assertEquals("https://www.youtube.com/watch?v=B-cMJ1qJ0kQ&t=4s", home.getURL()); // Check the set url is the same as the expected by get
    }

}


