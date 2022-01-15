package AutoSign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AutoSignControllerTests {

    @Autowired
    private MockMvc mockmvc;


    @Test
    void HomeTest() throws Exception {
        mockmvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andReturn();
    }

    @Test
    void ContactUsTest() throws Exception {
        mockmvc.perform(get("/contactus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andReturn();
    }

    @Test
    void HistoryTest() throws Exception {
        mockmvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andReturn();
    }

    @Test
    void AboutUsTest() throws Exception {
        mockmvc.perform(get("/aboutus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andReturn();
    }

    @Test
    void DonateTest() throws Exception {
        mockmvc.perform(get("/donate"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andReturn();
    }


}

