package AutoSign;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class HomeViewTests {

    @Test
    void getURLTest() {
        HomeView home = new HomeView();
        home.setURL("https://www.youtube.com/watch?v=8W7ec-FoUJ0");
        String result_test = home.getURL();

        Assert.assertEquals("https://www.youtube.com/watch?v=8W7ec-FoUJ0", result_test);
    }

    @Test
    void setURLTest() {
        HomeView home = new HomeView();
        home.setURL("https://www.youtube.com/watch?v=B-cMJ1qJ0kQ&t=4s");

        Assert.assertEquals("https://www.youtube.com/watch?v=B-cMJ1qJ0kQ&t=4s", home.getURL());
    }

}


