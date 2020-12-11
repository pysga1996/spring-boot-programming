package com.tutorial.soap;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

/**
 * @author thanhvt
 * @project spring-soap-web-service-demo
 */
public class SchemaGenerator {
    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance();
        /* truyền các model Pojo Class vào làm đối số trong method newInstance ở trên rồi chạy main để sinh ra file schema .xsd
         * file .xsd sinh ra nằm trong thư mục root của project
         * chạy mvn compile để sinh ngược lại các POJO class từ file .xsd (cần cấu hình JAXB plugin trong pom.xml trước)
         */
        jc.generateSchema(new SchemaOutputResolver() {

            @Override
            public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
                return new StreamResult(suggestedFileName);
            }

        });

    }
}
