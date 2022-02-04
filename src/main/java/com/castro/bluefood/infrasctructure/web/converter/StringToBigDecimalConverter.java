package com.castro.bluefood.infrasctructure.web.converter;

import com.castro.bluefood.util.FormatUtils;
import com.castro.bluefood.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//pra ser gerenciado pelo spring: @Component
@Component
public class StringToBigDecimalConverter implements Converter<String,BigDecimal> {

    @Override
    public BigDecimal convert(String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        value = value.replace(",",".").trim();
        return BigDecimal.valueOf(Double.valueOf(value));
    }

}
