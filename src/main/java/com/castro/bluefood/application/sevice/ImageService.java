package com.castro.bluefood.application.sevice;

import com.castro.bluefood.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${bluefood.files.logotipo}")//inicializa a variavel logotipoDir com Value
    private String logotiposDir;

    @Value("${bluefood.files.categoria}")//inicializa a variavel logotipoDir com Value
    private String categoriasDir;

    @Value("${bluefood.files.comida}")//inicializa a variavel logotipoDir com Value
    private String comidasDir;

    public void uploadLogotipo(MultipartFile multipartFile, String fileName){
        try {
            IOUtils.copy(multipartFile.getInputStream(),fileName,logotiposDir);
        } catch (IOException e) {
            throw  new ApplicationServiceException(e);
        }
    }

    public void uploadComida(MultipartFile multipartFile, String fileName){
        try {
            IOUtils.copy(multipartFile.getInputStream(),fileName,comidasDir);
        } catch (IOException e) {
            throw  new ApplicationServiceException(e);
        }
    }

    public byte[] getBytes(String type, String imgName){

        try{

            String dir;

            if("comida".equals(type)){
                dir = comidasDir;
            }else if("logotipo".equals(type)){
                dir = logotiposDir;
            }else if("categoria".equals(type)){
                dir = categoriasDir;
            }else{
                throw  new Exception(type + " não é um tipo de imagem válido.");
            }

            return IOUtils.getBytes(Paths.get(dir,imgName));
        }catch (Exception e){
            throw  new ApplicationServiceException(e);
        }
    }
}
