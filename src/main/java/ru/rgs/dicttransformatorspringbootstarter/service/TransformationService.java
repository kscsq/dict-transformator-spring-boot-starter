package ru.rgs.dicttransformatorspringbootstarter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.rgs.dicttransformatorspringbootstarter.dto.CarTransdekraInfo;
import ru.rgs.dicttransformatorspringbootstarter.dto.DocumentInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransformationService {

    @Autowired
    @Qualifier("dictNamedParamJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public String requestREF_4_1_9(String mapField, String remapFields) {

        List<DocumentInfo> documents = jdbcTemplate.query("SELECT * FROM REF_4_1_9",
                (rs, rowNum) -> DocumentInfo.builder()
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

        // Конвертируем все записи словаря в коллекцию строк
        List<String> allDataAsStrings = documents.stream().map(Objects::toString).collect(Collectors.toList());

        return getMapFieldValue(allDataAsStrings, remapFields, mapField);
    }

    public String requestREF_5_1_18(String mapField, String remapFields) {
        Map<String, Object> parameters = new HashMap<>();

        List<CarTransdekraInfo> cars = jdbcTemplate.query("SELECT * FROM REF_5_1_18", parameters, (rs, rowNum) -> CarTransdekraInfo.builder()
                .mapCarTransdekraId(rs.getString("MAP_CAR_TRANSDEKRA_ID"))
                .manufacturer(rs.getString("MANUFACTURER"))
                .model(rs.getString("MODEL"))
                .brandRgs(rs.getString("BRAND_RGS"))
                .modelRgs(rs.getString("MODEL_RGS"))
                .code(rs.getString("CODE"))
                .build());

        // Конвертируем все записи словаря в коллекцию строк
        List<String> allDataAsStrings = cars.stream().map(Objects::toString).collect(Collectors.toList());

        return getMapFieldValue(allDataAsStrings, remapFields, mapField);
    }

    /**
     *
     * @param allDataAsStrings - коллекция всех записей словаря в виде строк
     * @param remapFields - поля, по значениям который осуществляется поиск
     * @param mapField - наименование поля, значение которого требуется получить
     * @return
     *          значение поля mapField
     */
    private String getMapFieldValue(List<String>allDataAsStrings, String remapFields, String mapField) {
        ObjectMapper mapper = new ObjectMapper();
        Map remapFieldsMap;
        try {
            remapFieldsMap = mapper.readValue(remapFields, Map.class);
        } catch (JsonProcessingException e) {
            return "Wrong JSON format for remap fields";
        }

        for (Object s : remapFieldsMap.values()) {
            allDataAsStrings = allDataAsStrings.stream().filter(c -> c.contains(s.toString())).collect(Collectors.toList());
        }

        if (allDataAsStrings.isEmpty()) {
            return "MapField value not found";
        }

        String foundRow = allDataAsStrings.get(0);

        if (foundRow.contains(mapField)) {
            int keyIndex = foundRow.indexOf(mapField); //находим, где начинается искомое поле (ключ)
            int valueIndex = foundRow.indexOf("=", keyIndex); // находим, где начинается искомое поле (значение)
            int nextEqualIndex = foundRow.indexOf("=", ++valueIndex);// находим, где следующий знак равенства
            int blankIndex = foundRow.lastIndexOf(" ", nextEqualIndex); // ищем от него влево пробел
            if ((nextEqualIndex > 0) ) { // искомое поле не последнее в строке (еще есть знаки равенства далее в строке)
                return foundRow.substring(valueIndex, --blankIndex);
            } else { // искомое поле последнее в строке (знак равенства не найден)
                return foundRow.substring(valueIndex, foundRow.length() - 1);
            }
        } else {
            return "MapField column name not found";
        }
    }
}
