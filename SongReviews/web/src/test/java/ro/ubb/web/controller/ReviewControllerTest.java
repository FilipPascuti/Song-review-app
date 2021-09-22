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
import ro.ubb.core.domain.*;
import ro.ubb.core.service.ReviewService;
import ro.ubb.web.converter.ReviewConverter;
import ro.ubb.web.dto.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest {
    private MockMvc mockMvc;

    private Artist artist;
    private User user;
    private Song song;
    private Review review1, review2;
    private ReviewDto reviewDto1, reviewDto2;

    @InjectMocks
    private ReviewController controller;

    @Mock
    public ReviewService service;

    @Mock
    public ReviewConverter converter;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        initData();
    }

    @Test
    public void getSongs() throws Exception {
        List<Review> reviews = Arrays.asList(review1, review2);
        List<ReviewDto> dtos = Arrays.asList(reviewDto1, reviewDto2);

        when(service.getAllReviews()).thenReturn(reviews);
        when(converter.convertModelsToDtos(reviews)).thenReturn(dtos);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].stars", is(3)))
                .andExpect(jsonPath("$[0].reviewText", is("nice ish")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].stars", is(1)))
                .andExpect(jsonPath("$[1].reviewText", is("not nice")));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);

        verify(service, times(1)).getAllReviews();
        verify(converter, times(1)).convertModelsToDtos(reviews);
        verifyNoMoreInteractions(service, converter);

    }

    @Test
    public void addSong() throws Exception {
        Review review = Review.builder().user(user).song(song).stars(5).reviewText("very nice").build();
        review.setId(5);
        ReviewDto dto = createReviewDto(review);

        when(converter.convertDtoToModel(dto)).thenReturn(review);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/reviews/add", dto)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andExpect(status().isOk());
        verify(service, times(1)).addReview(review.getUser().getId(), review.getSong().getId(), review.getStars(), review.getReviewText());
        verify(converter, times(1)).convertDtoToModel(dto);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void updateSong() throws Exception {

        when(converter.convertDtoToModel(reviewDto1)).thenReturn(review1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/reviews/update", reviewDto1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(reviewDto1)))
                .andExpect(status().isOk());
        verify(service, times(1)).updateReview(review1.getUser().getId(), review1.getSong().getId(), review1.getStars(), review1.getReviewText());
        verify(converter, times(1)).convertDtoToModel(reviewDto1);
        verifyNoMoreInteractions(service, converter);
    }

    @Test
    public void deleteSong() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/reviews/delete/{userId}/{songId}", review1.getUser().getId(), review1.getSong().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteReview(review1.getUser().getId(), review1.getSong().getId());
    }


    private void initData() {
        user = User.builder().name("Mario").build();
        user.setId(1);

        artist = Artist.builder().name("Marcel").description("rapper").build();
        artist.setId(1);

        song = Song.builder().details(new SongDetails("Turtles", 100, 'A')).artist(artist).build();
        song.setId(1);

        review1 = Review.builder()
                .user(user)
                .song(song)
                .stars(3)
                .reviewText("nice ish")
                .build();
        review1.setId(1);

        review2 = Review.builder()
                .user(user)
                .song(song)
                .stars(1)
                .reviewText("not nice")
                .build();
        review2.setId(2);

        reviewDto1 = createReviewDto(review1);
        reviewDto2 = createReviewDto(review2);
    }

    private ReviewDto createReviewDto(Review review) {
        UserDto userDto = UserDto.builder()
                .name(review.getUser().getName())
                .build();
        userDto.setId(review.getUser().getId());

        Song song = review.getSong();

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

        ReviewDto reviewDto = ReviewDto.builder()
                .user(userDto)
                .song(songDto)
                .stars(review.getStars())
                .reviewText(review.getReviewText())
                .build();
        reviewDto.setId(review.getId());

        return reviewDto;
    }

    private String toJsonString(ReviewDto reviewDto) {
        try {
            return new ObjectMapper().writeValueAsString(reviewDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
