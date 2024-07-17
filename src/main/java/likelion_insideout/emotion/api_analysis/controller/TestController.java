package likelion_insideout.emotion.api_analysis.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(name = "Swagger 연동 테스트용", description = "Swagger를 이용한 API 연동 테스트용 컨트롤러")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Swagger!";
    }
}
