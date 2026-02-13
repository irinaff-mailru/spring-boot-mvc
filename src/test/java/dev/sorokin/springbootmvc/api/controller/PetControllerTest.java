package dev.sorokin.springbootmvc.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sorokin.springbootmvc.api.dto.RequestPetDto;
import dev.sorokin.springbootmvc.api.dto.ResponsePetDto;
import dev.sorokin.springbootmvc.service.PetService;
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

@WebMvcTest({PetController.class})
@AutoConfigureMockMvc
public class PetControllerTest {

    public static final String BASE_URL = "/api/v1/pet";
    public static final String ADD_PET_URL = BASE_URL + "/add";
    public static final String UPDATE_PET_URL = BASE_URL + "/update/{id}";
    public static final String DELETE_PET_URL = BASE_URL + "/delete/{id}";
    public static final String GET_PET_URL = BASE_URL + "/get/{id}";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test add pet")
    public void add() throws Exception {
        RequestPetDto requestDto = getRequestPetDto();
        ResponsePetDto responseDto = getResponsePetDto();
        when(petService.addPet(any())).thenReturn(responseDto);

        String response = mockMvc.perform(post(ADD_PET_URL)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResponsePetDto actual = objectMapper.readValue(response,ResponsePetDto.class);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        verify(petService).addPet(requestDto);
    }

    @Test
    @DisplayName("Test add pet with not valid request")
    public void add2() throws Exception {
        String dto = """
                {
                    "name": "",
                    "userId": 0
                }""";

        mockMvc.perform(post(ADD_PET_URL)
                        .content(dto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Test update pet")
    public void update() throws Exception {
        ResponsePetDto responseDto = getResponsePetDto();

        when(petService.updatePet(1L, 2L)).thenReturn(responseDto);

        String response = mockMvc.perform(put(UPDATE_PET_URL, "1")
                        .param("ownerId", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponsePetDto actual = objectMapper.readValue(response, ResponsePetDto.class);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        verify(petService).updatePet(1L, 2L);
    }

    @Test
    @DisplayName("Test delete pet")
    public void delete() throws Exception {
        doNothing().when(petService).deletePet(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PET_URL, "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("Test get pet")
    public void get() throws Exception {
        ResponsePetDto responseDto = getResponsePetDto();
        when(petService.getPet(1L)).thenReturn(responseDto);

        String response = mockMvc.perform(MockMvcRequestBuilders.get(GET_PET_URL, "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponsePetDto actual = objectMapper.readValue(response, ResponsePetDto.class);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
        verify(petService).getPet(1L);
    }

    private static RequestPetDto getRequestPetDto() {
        return new RequestPetDto("Kitty",1L);
    }

    private static ResponsePetDto getResponsePetDto() {
        return new ResponsePetDto(1L,"Kitty",1L);
    }
}