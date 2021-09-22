package ro.ubb.songs.converter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseConverter<Model, Dto> implements Converter<Model, Dto> {

    public List<Dto> convertModelsToDtos(Iterable<Model> models) {
        return StreamSupport.stream(models.spliterator(),false)
                .map(model -> convertModelToDto(model))
                .collect(Collectors.toList());
    }
}
