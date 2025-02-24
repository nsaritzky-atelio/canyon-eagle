package org.atelio.eagle.api;

import org.atelio.eagle.service.ICaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice(assignableTypes = CaseController.class)
public class CaseValidationAdvice {
    private final ICaseService caseService;

    public CaseValidationAdvice(ICaseService caseService) {
        this.caseService = caseService;
    }

    @ModelAttribute
    public void validateCaseRef(@PathVariable String caseRef) {
        if (caseService.getCaseId(caseRef).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Open case not found");
        }
    }
}
