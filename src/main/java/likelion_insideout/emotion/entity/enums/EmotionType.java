package likelion_insideout.emotion.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmotionType {

    PROUD("뿌듯"),
    JOY("기쁨"),
    SAD("슬픔"),
    DEPRESSION("우울"),
    ANXIETY("불안"),
    THRILL("설렘"),
    PANIC("당황"),
    ANGRY("화남");

    //감정을 저장할 필드(설명)
    private final String description;
}
