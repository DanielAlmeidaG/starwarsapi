package br.com.b2w.starwarsapi.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private static Locale locale = Locale.getDefault();

    private final MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }

}


