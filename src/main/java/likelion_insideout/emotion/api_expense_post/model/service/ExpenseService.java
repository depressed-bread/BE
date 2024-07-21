package likelion_insideout.emotion.api_expense_post.model.service;

import jakarta.transaction.Transactional;
import likelion_insideout.emotion.api_expense_post.model.dto.ExpenseDeleteDto;
import likelion_insideout.emotion.api_expense_post.model.dto.ExpenseRequestDto;
import likelion_insideout.emotion.api_expense_post.model.dto.ExpenseResponseDto;
import likelion_insideout.emotion.api_expense_post.model.dto.ExpenseUpdateDto;
import likelion_insideout.emotion.api_expense_post.model.repository.EmotionsRepository;
import likelion_insideout.emotion.api_expense_post.model.repository.ExpensesRepository;
import likelion_insideout.emotion.api_expense_post.model.repository.UsersRepository;
import likelion_insideout.emotion.entity.Emotion;
import likelion_insideout.emotion.entity.Expense;
import likelion_insideout.emotion.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpensesRepository expenseRepository;
    private final EmotionsRepository emotionsRepository;
    private final UsersRepository userRepository;

    //Create : 지출 게시글 저장
    @Transactional
    public Long createExpense(Long id, ExpenseRequestDto expenseRequestDto)  {

        //1. 사용자가 설정한 감정 이름을 기준으로 감정 객체 반환 ex) ANGRY 감정 객체
        Optional<Emotion> emotionOptional = emotionsRepository.findByName(expenseRequestDto.getEmotionType());
        Emotion emotion;

        if (emotionOptional.isEmpty()) { //2. 만약 ANGRY 감정 객체가 없다면 객체 생성 후 DB에 저장
            emotion = Emotion.builder()
                    .name(expenseRequestDto.getEmotionType())
                    .build();

            emotionsRepository.save(emotion);

        } else { //3. ANGRY 감정 객체가 있다면 Opitional에 있는 객체 반환
            emotion = emotionOptional.get();
        }


        //4. id값을 통해 사용자
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 없습니다."));


        //5. 작성한 지출 게시글 생성 후 저장
        Expense expense = Expense.builder()
                .user(user)
                .emotion(emotion)
                .keyword(expenseRequestDto.getKeyword())
                .price(expenseRequestDto.getPrice())
                .date(expenseRequestDto.getDate())
                .content(expenseRequestDto.getContent())
                .build();

        return expenseRepository.save(expense).getId();
    }

    //Read : 선택 게시글 조회
    @Transactional
    public ExpenseResponseDto readExpense(Long id) {

        Expense expense = expenseRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("잘못된 게시글 ID 입니다."));

        return new ExpenseResponseDto(expense.getKeyword(), expense.getContent(),
                expense.getPrice(), expense.getEmotion().getName());

    }

    //UPDATE : 게시글 수정
    @Transactional
    public ExpenseUpdateDto updateExpense(Long id,  ExpenseRequestDto expenseRequestDto) {

        //1. expenseId를 통해 게시글 불러오기
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 게시글 ID 입니다."));

        //2. 감정 수정이 일어났을 때
        Optional<Emotion> emotionOptional = emotionsRepository.findByName(expenseRequestDto.getEmotionType());
        Emotion emotion;

        if (emotionOptional.isEmpty()) {
            emotion = Emotion.builder()
                    .name(expenseRequestDto.getEmotionType())
                    .build();

            emotionsRepository.save(emotion);

        } else {
            emotion = emotionOptional.get();
        }

        expense.update(expenseRequestDto, emotion);

        //3.반환
        return new ExpenseUpdateDto();

    }

    //DELETE : 게시글 삭제
    @Transactional
    public ExpenseDeleteDto deleteExpense(Long id) {

        expenseRepository.deleteById(id);

        return new ExpenseDeleteDto();
    }











}
