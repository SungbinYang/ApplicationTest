package me.sungbin.javatest.study;

import lombok.RequiredArgsConstructor;
import me.sungbin.javatest.domain.Study;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyController {

    final StudyRepository studyRepository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable Long id) {
        return studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study not found for '" + id + "'"));
    }

    @PostMapping("/study")
    public Study createStudy(@RequestBody Study study) {
        return studyRepository.save(study);
    }
}
