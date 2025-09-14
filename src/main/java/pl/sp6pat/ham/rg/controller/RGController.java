package pl.sp6pat.ham.rg.controller;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sp6pat.ham.rg.service.RGService;

@RestController
@RequestMapping("/api")
@Slf4j
public class RGController {

    private final RGService rgService;

    public RGController(RGService rgService) {
        this.rgService = rgService;
    }


    @GetMapping("/validate-code")
    public Boolean validateCode(@PathParam("code1") String code1,
                                                 @PathParam("code2") String code2,
                                                 @PathParam("code3") String code3,
                                                 @PathParam("code4") String code4) {
        log.info("Codes: {}, {}, {}, {}", code1, code2, code3, code4);
        return rgService.validateCode(code1, code2, code3, code4);
    }

    @GetMapping("/rg-get-image")
    public ResponseEntity<Resource> getImage(@PathParam("call") String call) {
        log.info("Call: " + call);
        try {
            Resource image = rgService.getImage(call);
            if (!image.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
