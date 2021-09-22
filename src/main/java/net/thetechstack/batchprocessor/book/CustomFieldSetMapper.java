package net.thetechstack.batchprocessor.book;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class CustomFieldSetMapper implements FieldSetMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> mapFieldSet(FieldSet fieldSet) throws BindException {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < fieldSet.getValues().length; i++) {
            map.put(fieldSet.getNames()[i], fieldSet.getValues()[i]);
        }
        return map;
    }
}
