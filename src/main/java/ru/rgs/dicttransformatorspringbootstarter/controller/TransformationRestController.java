package ru.rgs.dicttransformatorspringbootstarter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rgs.dicttransformatorspringbootstarter.service.TransformationService;

@RestController
@RequestMapping(method = RequestMethod.POST, value = "transform")
public class TransformationRestController {

    Logger log = LoggerFactory.getLogger(TransformationRestController.class);

    @Autowired
    private TransformationService service;

    @PostMapping(value = "/")
    public String transform(@RequestHeader("DictionaryCode") String dictCode,
                            @RequestHeader("MapField") String mapField,
                            @RequestBody String remapFields) {
        log.info("Dictionary code provided is: " + dictCode);
        log.info("MapField provided is: " + mapField);
        log.info("RemapFields provided is: " + remapFields);
        switch (dictCode) {
            case "REF_4_1_9":
                return service.requestREF_4_1_9(mapField, remapFields);
            case "REF_5_1_18":
                return service.requestREF_5_1_18(mapField, remapFields);
            default:
                return "";
        }
    }
}
