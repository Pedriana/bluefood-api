package com.castro.bluefood.infrasctructure.web.converter;

import com.castro.bluefood.util.FormatUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//pra ser gerenciado pelo spring: @Component
@Component
public class BigDecimalToStringConverter implements Converter<BigDecimal,String> {

    @Override
    public String convert(BigDecimal value) {
        return FormatUtils.formatCurrency(value);
    }

}
