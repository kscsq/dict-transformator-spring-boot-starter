package ru.rgs.dicttransformatorspringbootstarter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ДТО "Вид документа", соответствующий справочнику 4.1.9 Классификатор видов документов, удостоверяющих личность
 */
@Getter
@Setter
@Builder
@ToString
public class DocumentInfo {
    private String code;
    private int rsaCode;
    private int equifaxCode;
    private String shortName;
    private String fullName;
    private String personal;
    private String pattern;
    private String remarks;
    private int isAdditional;
    private int isIdentityDocument;
}
