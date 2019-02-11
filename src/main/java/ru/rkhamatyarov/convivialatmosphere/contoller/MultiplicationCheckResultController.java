package ru.rkhamatyarov.convivialatmosphere.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rkhamatyarov.convivialatmosphere.contoller.rs.MultiplicationSuccessRs;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.service.MultiplicationService;

@RestController
@RequestMapping("/results")
final class MultiplicationCheckResultController {

    private final MultiplicationService multiplicationService;

    @Autowired
    MultiplicationCheckResultController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

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

}
