package com.castro.bluefood.infrasctructure.web.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.castro.bluefood.util.FileType;
import org.springframework.web.multipart.MultipartFile;


//MultipartFile tipo de dado que vamos utilizar pra atualizar

public class UploadValidator implements ConstraintValidator<UploadConstraint, MultipartFile> {
    private List<FileType> acceptedFileTypes;

    @Override
    public void initialize(UploadConstraint constraintAnnotation) {
        acceptedFileTypes = Arrays.asList(constraintAnnotation.acceptedTypes());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null) {
            return true;
        }

        for (FileType fileType : acceptedFileTypes) {
            if (fileType.sameOf(multipartFile.getContentType())) {
                return true;
            }
        }

        return false;
    }
}
