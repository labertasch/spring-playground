package ch.napohaku.playground.api.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.napohaku.playground.api.security.UserInfoController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserInfoController userInfoController;

    @Test
    public void shouldReturnAnonymousUser() throws Exception {
        mockMvc.perform(get("/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.userName").value("anonymousUser"));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void shouldReturnUser() throws Exception {
        mockMvc.perform(get("/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.userName").value("user"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void shouldReturnAdminUser() throws Exception {
        mockMvc.perform(get("/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.userName").value("admin"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = { "ADMIN", "USER" })
    public void shouldReturnWithRoleAdminAndUser() throws Exception {
        this.mockMvc.perform(get("/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles").exists())
                .andExpect(jsonPath("$..roles[?(@ === \"ROLE_ADMIN\")]").exists())
                .andExpect(jsonPath("$..roles[?(@ === \"ROLE_USER\")]").exists());

    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = { "USER" })
    public void shouldReturnWithRoleUser() throws Exception {
        this.mockMvc.perform(get("/me")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.roles").exists())
                .andExpect(jsonPath("$..roles[?(@ === \"ROLE_ADMIN\")]").doesNotExist())
                .andExpect(jsonPath("$..roles[?(@ === \"ROLE_USER\")]").exists());
    }
}