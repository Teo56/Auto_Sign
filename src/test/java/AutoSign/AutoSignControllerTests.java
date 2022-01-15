package AutoSign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This class contains the tests for the autosign controller methods
// these mostly include mapping tests (checking the status of the response after the get/post mapping is done)

@WebMvcTest
public class AutoSignControllerTests {

    @Autowired
    private MockMvc mockmvc; // Creates a mock client to submit the requests


    @Test
    void HomeTest() throws Exception {
        mockmvc.perform(get("/"))
                .andExpect(status().isOk()) // Checks that the status code of the sesponse is 200 (=OK -> request properly sent)
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8"))) // Checks that the content associated to that requests is of type html
                .andReturn(); // Defines return value
    }

    @Test
    void ContactUsTest() throws Exception {
        mockmvc.perform(get("/contactus"))
                .andExpect(status().isOk()) // Checks that the status code of the sesponse is 200 (=OK -> request properly sent)
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8"))) // Checks that the content associated to that requests is of type html
                .andReturn(); // Defines return value
    }

    @Test
    void HistoryTest() throws Exception {
        mockmvc.perform(get("/history"))
                .andExpect(status().isOk()) // Checks that the status code of the sesponse is 200 (=OK -> request properly sent)
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8"))) // Checks that the content associated to that requests is of type html
                .andReturn(); // Defines return value
    }

    @Test
    void AboutUsTest() throws Exception {
        mockmvc.perform(get("/aboutus"))
                .andExpect(status().isOk()) // Checks that the status code of the sesponse is 200 (=OK -> request properly sent)
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8"))) // Checks that the content associated to that requests is of type html
                .andReturn(); // Defines return value
    }

    @Test
    void DonateTest() throws Exception {
        mockmvc.perform(get("/donate"))
                .andExpect(status().isOk()) // Checks that the status code of the sesponse is 200 (=OK -> request properly sent)
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8"))) // Checks that the content associated to that requests is of type html
                .andReturn(); // Defines return value
    }


}

