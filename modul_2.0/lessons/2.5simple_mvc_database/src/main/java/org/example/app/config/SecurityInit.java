package org.example.app.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//аналогично фильтру из web.xml, но здесь достаточно наследоваться от класса AbstractSecurityWebApplicationInitializer
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
