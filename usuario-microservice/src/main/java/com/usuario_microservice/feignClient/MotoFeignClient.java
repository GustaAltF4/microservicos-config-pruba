package com.usuario_microservice.feignClient;

import com.usuario_microservice.models.MotoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "moto-microservice")
public interface MotoFeignClient {

    @PostMapping("/moto")
    public MotoDto save(@RequestBody MotoDto motoDto);

    @GetMapping("/moto/usuario/{usuarioId}")
    public List<MotoDto> getMotos(@PathVariable("usuarioId") int usuarioId);
}
