package ru.rkhamatyarov.convivialatmosphere.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.service.MultiplicationService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/results")
final class MultiplicationCheckResultController {

    private final MultiplicationService multiplicationService;

    @PostMapping
    ResponseEntity<MultiplicationResultTry> postResult(@RequestBody MultiplicationResultTry resultTry) {
        Boolean isRightResult = multiplicationService.checkTry(resultTry);

        MultiplicationResultTry copyResultTry = new MultiplicationResultTry(
                resultTry.getUser(),
                resultTry.getMultiplication(),
                resultTry.getMultiplicationResult(),
                isRightResult
        );


        return ResponseEntity.ok(
                copyResultTry
        );
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultTry>> getMultiplicationTries(@RequestParam("login") String login) {
        log.debug(">> Handler layer: multiplication try list {}", multiplicationService.getUserMultiplicationCountOfTries(login));
        return ResponseEntity.ok(multiplicationService.getUserMultiplicationCountOfTries(login));
    }

}
