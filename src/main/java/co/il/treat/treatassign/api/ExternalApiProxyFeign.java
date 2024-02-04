package co.il.treat.treatassign.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="externalApi", url = "https://api.petfinder.com/v2", configuration = FeignConfig.class)
public interface ExternalApiProxyFeign {

    @GetMapping("/animals")
    Object getAllDogs (
            @RequestParam String organization,
            @RequestParam String gender,
            @RequestParam String location,
            @RequestParam String breed,
            @RequestParam String type,
            @RequestParam int page,
            @RequestParam int limit
            );
}
