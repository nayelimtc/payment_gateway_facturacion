package com.banquito.gateway.facturacion.banquito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Payment Gateway - Facturación y Comisiones API", version = "1.0.0", description = "API para gestión de facturación y comisiones del payment gateway"))
public class BanquitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanquitoApplication.class, args);
	}

}
