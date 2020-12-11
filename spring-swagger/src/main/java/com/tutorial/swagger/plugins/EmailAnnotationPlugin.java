package com.tutorial.swagger.plugins;

import static springfox.bean.validators.plugins.Validators.annotationFromBean;

import java.util.Optional;
import javax.validation.constraints.Email;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.plugins.Validators;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

@Order(Validators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class EmailAnnotationPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<Email> email = annotationFromBean(context, Email.class);
        if (email.isPresent()) {
            context.getBuilder().pattern(email.get().regexp());
            context.getBuilder().example("pysga1996@gmail.com");
        }
    }
}
