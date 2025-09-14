package pl.sp6pat.ham.rg.controller;

import jakarta.websocket.server.PathParam;
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
public class RGController {

    private final RGService rgService;

    public RGController(RGService rgService) {
        this.rgService = rgService;
    }


    @GetMapping("/rg-get-image")
    public ResponseEntity<Resource> getImage(@PathParam("call") String call) {
        System.out.println("Call: " + call);
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
