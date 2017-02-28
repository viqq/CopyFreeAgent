package com.free.agent.converter;

import com.free.agent.dto.SportUIDto;
import com.free.agent.model.Sport;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class Sport2SportUIDto implements Converter<Sport, SportUIDto> {

    @Override
    public SportUIDto convert(Sport sport) {
        SportUIDto dto = new SportUIDto();
        dto.setId(sport.getId());
        dto.setNameEn(sport.getNameEn());
        dto.setNameRu(sport.getNameRu());
        return dto;
    }
}
