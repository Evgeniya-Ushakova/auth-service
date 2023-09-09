package com.evg.authservice.converter;

import com.evg.authservice.service.AESCryptService;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
@Component
@RequiredArgsConstructor
public class PasswordConverter implements AttributeConverter<String, String> {

    private final AESCryptService aesCryptService;

    @Override
    public String convertToDatabaseColumn(String password) {

        if (Objects.isNull(password)) {
            return StringUtils.EMPTY;
        }

        return aesCryptService.encrypt(password);

    }

    @Override
    public String convertToEntityAttribute(String password) {
        if (StringUtils.isBlank(password)) {
            return StringUtils.EMPTY;
        }

        return aesCryptService.decrypt(password);
    }
}
