package ru.rgs.dicttransformatorspringbootstarter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ДТО "Сопоставление марок и моделей", соответствующий справочнику 5.1.18 Сопоставление марок и моделей для Трансдекра
 */
@Getter
@Setter
@Builder
@ToString
public class CarTransdekraInfo {
    private String mapCarTransdekraId;
    private String manufacturer;
    private String model;
    private String brandRgs;
    private String modelRgs;
    private String code;
}
