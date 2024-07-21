package likelion_insideout.emotion.api_emoticon.model.service;

import jakarta.transaction.Transactional;
import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmotionListService {

    @Transactional
    public List<String> AbleList() {

        /**영어로 반환하려면 반환 타입 : EnumType []
         * return EmotionType.values();
         */

       return Arrays.stream(EmotionType.values())
                .map(EmotionType::getDescription)
                .collect(Collectors.toList());


    }

}
