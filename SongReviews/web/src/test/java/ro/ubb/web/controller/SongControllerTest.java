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
import ro.ubb.core.domain.Song;
import ro.ubb.core.domain.SongDetails;
import ro.ubb.core.service.SongService;
import ro.ubb.web.converter.SongConverter;
import ro.ubb.web.dto.ArtistDto;
import ro.ubb.web.dto.SongDetailsDto;
import ro.ubb.web.dto.SongDto;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SongControllerTest {

    private MockMvc mockMvc;

    private Artist artist;
    private Song song1, song2;
    private SongDto songDto1, songDto2;

    @InjectMocks
    private SongController controller;

    @Mock
    public SongService service;

    @Mock
    public SongConverter converter;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        initData();
    }

    @Test
    public void getSongs() throws Exception {
        List<Song> songs = Arrays.asList(song1, song2);
        List<SongDto> dtos = Arrays.asList(songDto1, songDto2);

        when(service.getSongs()).thenReturn(songs);
        when(converter.convertModelsToDtos(songs)).thenReturn(dtos);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/songs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].details.title", is("Stars")))
                .andExpect(jsonPath("$[0].details.length", is(100)))
                .andExpect(jsonPath("$[0].details.key", is("A")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].details.title", is("Borcane")))
                .andExpect(jsonPath("$[1].details.length", is(200)))
                .andExpect(jsonPath("$[1].details.key", is("B")));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);

        verify(service, times(1)).getSongs();
        verify(converter, times(1)).convertModelsToDtos(songs);
        verifyNoMoreInteractions(service, converter);

    }

    @Test
    public void addSong() throws Exception {
        Song song = Song.builder().details(new SongDetails("Turtles", 300, 'C')).artist(artist).build();
        song.setId(5);
        SongDto dto = createSongDto(song);

        when(converter.convertDtoToModel(dto)).thenReturn(song);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/songs/add", dto)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andExpect(status().isOk());
        verify(service, times(1)).addSong(song.getDetails().getTitle(), song.getDetails().getLength(), song.getDetails().getKey(), song.getArtist().getId());
        verify(converter, times(1)).convertDtoToModel(dto);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void updateSong() throws Exception {

        when(converter.convertDtoToModel(songDto1)).thenReturn(song1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/songs/update", songDto1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(songDto1)))
                .andExpect(status().isOk());
        verify(service, times(1)).updateSong(song1.getId(), song1.getDetails().getTitle(), song1.getDetails().getLength(), song1.getDetails().getKey());
        verify(converter, times(1)).convertDtoToModel(songDto1);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void deleteSong() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/songs/delete/{id}", song1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteSong(song1.getId());
    }


    private void initData() {
        artist = Artist.builder().name("Marcel").description("rapper").build();
        artist.setId(1);

        song1 = Song.builder().details(new SongDetails("Stars", 100, 'A')).artist(artist).build();
        song1.setId(1);

        song2 = Song.builder().details(new SongDetails("Borcane", 200, 'B')).artist(artist).build();
        song2.setId(2);

        songDto1 = createSongDto(song1);
        songDto2 = createSongDto(song2);
    }

    private SongDto createSongDto(Song song) {
        ArtistDto artistDto = ArtistDto.builder()
                .name(song.getArtist().getName())
                .description(song.getArtist().getDescription())
                .build();
        artistDto.setId(song.getArtist().getId());

        SongDto songDto = SongDto.builder()
                .details(new SongDetailsDto(song.getDetails().getTitle(), song.getDetails().getLength(), song.getDetails().getKey()))
                .artist(artistDto)
                .build();
        songDto.setId(song.getId());
        return songDto;
    }

    private String toJsonString(SongDto songDto) {
        try {
            return new ObjectMapper().writeValueAsString(songDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
