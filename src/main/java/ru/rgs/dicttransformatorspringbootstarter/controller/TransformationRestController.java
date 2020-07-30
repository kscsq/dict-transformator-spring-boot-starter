package ru.rgs.dicttransformatorspringbootstarter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rgs.dicttransformatorspringbootstarter.service.TransformationService;

@RestController
@RequestMapping(method = RequestMethod.POST, value = "transform")
public class TransformationRestController {

    @Autowired
    private TransformationService service;

    @PostMapping(value = "REF_4_1_9")
    public String transform_REF_4_1_9() throws Exception {
        return service.requestREF_4_1_9().toString();
    }

    @PostMapping(value = "REF_5_1_18")
    public String transform_REF_5_1_18() throws Exception {
        return service.requestREF_5_1_18().toString();
    }
}
