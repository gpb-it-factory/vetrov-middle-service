package ru.omon4412.minibank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.omon4412.minibank.dto.UserDto;
import ru.omon4412.minibank.service.RegistrationService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {
    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController)
                .build();
    }

    @Test
    void test_registerUser_success() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(10101L);
        userDto.setUserName("TestUser");

        doNothing().when(registrationService).registerUser(any(UserDto.class));

        mockMvc.perform(post("/users").content(new ObjectMapper().writeValueAsString(userDto))
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
        verify(registrationService, times(1)).registerUser(userDto);
    }

    @Test
    void testRegisterUser_ifUserDtoIsNull_thenReturn400() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterUser_ifUserIdIsNull_thenReturn400() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(null);
        mockMvc.perform(post("/users").content(new ObjectMapper().writeValueAsString(userDto))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}