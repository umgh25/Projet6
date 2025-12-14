package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.Topic;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicDto asTopicDto (Topic topic);

    List<TopicDto> asTopicDtos (List<Topic> topic);
}