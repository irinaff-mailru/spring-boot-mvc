package dev.sorokin.springbootmvc.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sorokin.springbootmvc.api.dto.RequestUpdateUserDto;
import dev.sorokin.springbootmvc.api.dto.RequestUserDto;
import dev.sorokin.springbootmvc.api.dto.ResponseUserDto;
import dev.sorokin.springbootmvc.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers =  UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    public static final String BASE_URL = "/api/v1/user";
    public static final String ADD_USER_URL = BASE_URL + "/add";
    public static final String UPDATE_USER_URL = BASE_URL + "/update/{id}";
    public static final String DELETE_USER_URL = BASE_URL + "/delete/{id}";
    public static final String GET_USER_URL = BASE_URL + "/get/{id}";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Test add user")
    public void add() throws Exception {
        RequestUserDto requestDto = getRequestUserDto();
        ResponseUserDto responseDto = getResponseUserDto();
        when(userService.addUser(any())).thenReturn(responseDto);

        String response = mockMvc.perform(post(ADD_USER_URL)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResponseUserDto actual = objectMapper.readValue(response,ResponseUserDto.class);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        verify(userService).addUser(requestDto);
    }

    @Test
    @DisplayName("Test add user with not valid request")
    public void add2() throws Exception {
        String dto = """
                {
                    "name": "",
                    "userId": 0
                }""";

        mockMvc.perform(post(ADD_USER_URL)
                        .content(dto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Test update user")
    public void update() throws Exception {
        RequestUpdateUserDto requestDto = new RequestUpdateUserDto("ivan555@post.ru",24);
        ResponseUserDto responseDto = getResponseUserDto();

        when(userService.updateUser(1L, requestDto)).thenReturn(responseDto);

        String response = mockMvc.perform(put(UPDATE_USER_URL, "1")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseUserDto actual = objectMapper.readValue(response, ResponseUserDto.class);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        verify(userService).updateUser(1L, requestDto);
    }

    @Test
    @DisplayName("Test delete user")
    public void delete() throws Exception {
        doNothing().when(userService).deleteUser(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_USER_URL, "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("Test get user")
    public void get() throws Exception {
        ResponseUserDto responseDto = getResponseUserDto();
        when(userService.getUser(1L)).thenReturn(responseDto);

        String response = mockMvc.perform(MockMvcRequestBuilders.get(GET_USER_URL, "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseUserDto actual = objectMapper.readValue(response, ResponseUserDto.class);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        verify(userService).getUser(1L);
    }

    private static RequestUserDto getRequestUserDto() {
        return new RequestUserDto("Ivan","ivan@post.ru",24);
    }

    private static ResponseUserDto getResponseUserDto() {
        return new ResponseUserDto(1L,"Ivan","ivan555@post.ru",24, null);
    }
}
