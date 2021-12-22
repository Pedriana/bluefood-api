package com.castro.bluefood.infrasctructure.web.validator;

import com.castro.bluefood.util.FileType;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Retention(RUNTIME) //quer dizer disponivel pra JVM
@Target({ FIELD, METHOD }) //pode ser usado em metodos ou atributos
@Constraint(validatedBy = UploadValidator.class) //qual o nome da classe que trabalha em conjunto com esta anotation
public @interface UploadConstraint {

    String message() default "Arquivo inválido";
    FileType[] acceptedTypes();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
