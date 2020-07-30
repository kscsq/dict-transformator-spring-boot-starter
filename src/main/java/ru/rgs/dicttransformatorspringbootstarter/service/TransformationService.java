package ru.rgs.dicttransformatorspringbootstarter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.rgs.dicttransformatorspringbootstarter.dto.CarTransdekraInfo;
import ru.rgs.dicttransformatorspringbootstarter.dto.DocumentInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransformationService {

    @Autowired
    @Qualifier("dictNamedParamJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<DocumentInfo> requestREF_4_1_9() {
        Map<String, Object> parameters = new HashMap<>();

        List<DocumentInfo> documents = jdbcTemplate.query("SELECT * FROM REF_4_1_9", parameters, (rs, rowNum) -> DocumentInfo.builder()
                .code(rs.getString("CODE"))
                .rsaCode(rs.getInt("RSA_CODE"))
                .equifaxCode(rs.getInt("EQUIFAX_CODE"))
                .shortName(rs.getString("SHORT_NAME"))
                .fullName(rs.getString("FULL_NAME"))
                .personal(rs.getString("PERSONAL"))
                .pattern(rs.getString("PATTERN"))
                .remarks(rs.getString("REMARKS"))
                .isAdditional(rs.getInt("IS_ADDITIONAL"))
                .isIdentityDocument(rs.getInt("IS_IDENTITY_DOCUMENT"))
                .build());

        return documents.stream().limit(10).collect(Collectors.toList());
    }

    public List<CarTransdekraInfo> requestREF_5_1_18() {
        Map<String, Object> parameters = new HashMap<>();

        List<CarTransdekraInfo> cars = jdbcTemplate.query("SELECT * FROM REF_5_1_18", parameters, (rs, rowNum) -> CarTransdekraInfo.builder()
                    .mapCarTransdekraId(rs.getString("MAP_CAR_TRANSDEKRA_ID"))
                    .manufacturer(rs.getString("MANUFACTURER"))
                    .model(rs.getString("MODEL"))
                    .brandRgs(rs.getString("BRAND_RGS"))
                    .modelRgs(rs.getString("MODEL_RGS"))
                    .code(rs.getString("CODE"))
                    .build());

        return cars.stream().limit(10).collect(Collectors.toList());
    }
}
