package com.usuario_microservice.feignClient;

import com.usuario_microservice.models.AutoDto;
import com.usuario_microservice.models.MotoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "auto-microservice")
public interface AutoFeignClient {

    @PostMapping("/auto")
    public AutoDto save(@RequestBody AutoDto autoDto);

    @GetMapping("/auto/usuario/{usuarioId}")
    public List<AutoDto> getAutos(@PathVariable("usuarioId") int usuarioId);

}
