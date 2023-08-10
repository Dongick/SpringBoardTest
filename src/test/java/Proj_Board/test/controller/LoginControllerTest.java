package Proj_Board.test.controller;

import Proj_Board.test.model.User;
import Proj_Board.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.mock.web.MockHttpSession;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetLogin() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void testPostLoginSuccess() throws Exception{
        User user = new User();
        user.setUserId("testUser");
        user.setUserPw("testPassword");

        when(userService.findByUser("testUser", "testPassword")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("userid", "testUser")
                        .param("password", "testPassword")
                        .session(new MockHttpSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board"))
                .andExpect(request().sessionAttribute("userId", "testUser"));

        verify(userService).findByUser("testUser", "testPassword");
    }

    @Test
    public void testPostLoginFailure() throws Exception {
        when(userService.findByUser("invalidUser", "invalidPassword")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("userid", "invalidUser")
                        .param("password", "invalidPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));

        verify(userService).findByUser("invalidUser", "invalidPassword");
    }
}
