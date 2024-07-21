package likelion_insideout.emotion.api_emoticon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import likelion_insideout.emotion.api_emoticon.model.service.EmotionListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "감정 리스트 api", description = "감성 리스트 반환 api")
public class EmotionListController {

    public final EmotionListService emotionListService;

    @GetMapping("/api/emotions")
    public ResponseEntity<List<String>> emotionList() {

        /** EmotionType[] emotions = emotionListService.AbleList();
         * 영어로 반환하고 싶으면 쓰는 코드
         */

        return new ResponseEntity<>(emotionListService.AbleList(), HttpStatus.OK);

    }

}
