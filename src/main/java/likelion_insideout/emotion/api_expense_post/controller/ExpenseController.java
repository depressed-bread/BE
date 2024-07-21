package likelion_insideout.emotion.api_expense_post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion_insideout.emotion.api_expense_post.model.dto.*;
import likelion_insideout.emotion.api_expense_post.model.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "지출 게시글 api", description = "지출 게시글 CRUD api")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @Operation(summary = "Create 지출 게시글 생성")
    public ResponseEntity<ExpenseCreateDto> createExpense(@RequestParam("id") Long id, //사용자 id
                                                          @RequestBody ExpenseRequestDto expenseRequestDto) {

        Long expenseId = expenseService.createExpense(id, expenseRequestDto);
        ExpenseCreateDto expenseCreateDto = new ExpenseCreateDto();

        return new ResponseEntity<>(expenseCreateDto, HttpStatus.CREATED);

    }

    @GetMapping("/{expenseId}")
    @Operation(summary = "Read 지출 게시글 조회")
    public ResponseEntity<ExpenseResponseDto> ReadExpense(@PathVariable("expenseId") Long id) {



        return new ResponseEntity<>(expenseService.readExpense(id), HttpStatus.OK);

    }

    @PutMapping("/{expenseId}")
    @Operation(summary = "Update 지출 게시글 수정")
    public ResponseEntity<ExpenseUpdateDto> UpdateExpense(@PathVariable("expenseId") Long id,
                                                          @RequestBody ExpenseRequestDto expenseRequestDto) {

        return new ResponseEntity<>(expenseService.updateExpense(id, expenseRequestDto), HttpStatus.OK);

    }

    @DeleteMapping("/{expenseId}")
    @Operation(summary = "Delete 지출 게시글 삭제")
    public ResponseEntity<ExpenseDeleteDto> DeleteExpense(@PathVariable("expenseId") Long id) {

        return new ResponseEntity<>(expenseService.deleteExpense(id), HttpStatus.OK);

    }
}
