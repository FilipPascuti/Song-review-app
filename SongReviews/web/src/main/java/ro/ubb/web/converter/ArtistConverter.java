package ro.ubb.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Artist;
import ro.ubb.web.dto.ArtistDto;

@Component
public class ArtistConverter extends BaseConverter<Artist, ArtistDto> {
    @Override
    public Artist convertDtoToModel(ArtistDto artistDto) {

        Artist artist = Artist.builder()
                .name(artistDto.getName())
                .description(artistDto.getDescription())
                .build();
        artist.setId(artistDto.getId());
        return artist;
    }

    @Override
    public ArtistDto convertModelToDto(Artist artist) {
        ArtistDto dto = ArtistDto.builder()
                .name(artist.getName())
                .description(artist.getDescription())
                .build();
        dto.setId(artist.getId());
        return dto;
    }
}
