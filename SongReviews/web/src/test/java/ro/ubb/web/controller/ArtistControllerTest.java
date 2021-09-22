package ro.ubb.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.service.ArtistService;
import ro.ubb.web.converter.ArtistConverter;
import ro.ubb.web.dto.ArtistDto;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArtistControllerTest {

    private MockMvc mockMvc;

    private Artist artist1, artist2;
    private ArtistDto artistDto1, artistDto2;

    @InjectMocks
    private ArtistController controller;

    @Mock
    public ArtistService service;

    @Mock
    public ArtistConverter converter;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        initData();
    }

    @Test
    public void getArtists() throws Exception{
        List<Artist> artists = Arrays.asList(artist1, artist2);
        List<ArtistDto> dtos = Arrays.asList(artistDto1, artistDto2);

        when(service.getArtists()).thenReturn(artists);
        when(converter.convertModelsToDtos(artists)).thenReturn(dtos);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/artists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Marcel")))
                .andExpect(jsonPath("$[0].description", is("rapper")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Mario")))
                .andExpect(jsonPath("$[1].description", is("rapper")));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);

        verify(service, times(1)).getArtists();
        verify(converter, times(1)).convertModelsToDtos(artists);
        verifyNoMoreInteractions(service, converter);

    }

    @Test
    public void addArtist() throws Exception {
        Artist artist = Artist.builder().name("Dorinel").description("trapper").build();
        artist.setId(5);
        ArtistDto dto = createArtistDto(artist);

        when(converter.convertDtoToModel(dto)).thenReturn(artist);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/artists/add", dto)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andExpect(status().isOk());
        verify(service, times(1)).addArtist(artist.getName(), artist.getDescription());
        verify(converter, times(1)).convertDtoToModel(dto);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void updateArtist() throws Exception {

        when(converter.convertDtoToModel(artistDto1)).thenReturn(artist1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/artists/update", artistDto1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(artistDto1)))
                .andExpect(status().isOk());
        verify(service, times(1)).updateArtist(artist1.getId(), artist1.getName(), artist1.getDescription());
        verify(converter, times(1)).convertDtoToModel(artistDto1);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void deleteArtist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/artists/delete/{id}", artist1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteArtist(artist1.getId());
    }


    private void initData() {
        artist1 = Artist.builder().name("Marcel").description("rapper").build();
        artist1.setId(1);
        artist2 = Artist.builder().name("Mario").description("rapper").build();
        artist2.setId(2);

        artistDto1 = createArtistDto(artist1);
        artistDto2 = createArtistDto(artist2);
    }

    private ArtistDto createArtistDto(Artist artist) {
        ArtistDto dto = ArtistDto.builder()
                .name(artist.getName())
                .description(artist.getDescription())
                .build();
        dto.setId(artist.getId());
        return dto;
    }

    private String toJsonString(ArtistDto artistDto) {
        try {
            return new ObjectMapper().writeValueAsString(artistDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
