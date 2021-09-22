package ro.ubb.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.core.domain.User;
import ro.ubb.core.service.UserService;
import ro.ubb.web.converter.UserConverter;
import ro.ubb.web.dto.UserDto;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

public class UserControllerTest {

    private MockMvc mockMvc;

    private User user1, user2;
    private UserDto userDto1, userDto2;

    @InjectMocks
    private UserController controller;

    @Mock
    public UserService service;

    @Mock
    public UserConverter converter;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        initData();
    }

    @Test
    public void getUsers() throws Exception{
        List<User> users = Arrays.asList(user1, user2);
        List<UserDto> dtos = Arrays.asList(userDto1, userDto2);

        when(service.getUsers()).thenReturn(users);
        when(converter.convertModelsToDtos(users)).thenReturn(dtos);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Marcel")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Mario")));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);

        verify(service, times(1)).getUsers();
        verify(converter, times(1)).convertModelsToDtos(users);
        verifyNoMoreInteractions(service, converter);

    }

    @Test
    public void addUser() throws Exception {
        User newUser = User.builder().name("Dorinel").build();
        newUser.setId(5);
        UserDto dto = createUserDto(newUser);

        when(converter.convertDtoToModel(dto)).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/add", dto)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andExpect(status().isOk());
        verify(service, times(1)).addUser(newUser.getName());
        verify(converter, times(1)).convertDtoToModel(dto);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void updateUser() throws Exception {

        when(converter.convertDtoToModel(userDto1)).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/users/update", userDto1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(userDto1)))
                .andExpect(status().isOk());
        verify(service, times(1)).updateUser(user1.getId(), user1.getName());
        verify(converter, times(1)).convertDtoToModel(userDto1);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/delete/{id}", user1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteUser(user1.getId());
    }


    private void initData() {
        user1 = User.builder().name("Marcel").build();
        user1.setId(1);
        user2 = User.builder().name("Mario").build();
        user2.setId(2);
        
        userDto1 = createUserDto(user1);
        userDto2= createUserDto(user2);
    }

    private UserDto createUserDto(User user) {
        UserDto dto = UserDto.builder()
                .name(user.getName())
                .build();
        dto.setId(user.getId());
        return dto;
    }

    private String toJsonString(UserDto userDto) {
        try {
            return new ObjectMapper().writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
