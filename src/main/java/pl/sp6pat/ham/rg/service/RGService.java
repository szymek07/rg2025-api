package pl.sp6pat.ham.rg.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import pl.sp6pat.ham.rg.utils.SimilarPatternMatch;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class RGService {

    @Value("${code1}")
    private String expectedCode1;

    @Value("${code2}")
    private String expectedCode2;

    @Value("${code3}")
    private String expectedCode3;

    @Value("${code4}")
    private String expectedCode4;


    public RGService() {
    }

    public Resource getImage(String call) throws IOException {
        Resource imageResource = new ClassPathResource("static/rg2025_template_cert.png");
        InputStream in = imageResource.getInputStream();
        BufferedImage image = ImageIO.read(in);

        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font f = loadFont();
        g.setFont(f);


        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(call.toUpperCase());
        int textHeight = fm.getHeight();
        int x = (image.getWidth() - textWidth) / 2;
        int y = ((image.getHeight() - textHeight) / 2 + fm.getAscent()); // fm.getAscent() jest potrzebne, aby pionowo wyśrodkować tekst w obliczonym obszarze.

        Color shadowColor = new Color(100, 100, 100, 150);
        int shadowOffset = 4;

        g.setColor(shadowColor);
        g.drawString(call.toUpperCase(), x + shadowOffset, y + shadowOffset);

        g.setColor(Color.BLACK);
        g.drawString(call.toUpperCase(), x, y);
        g.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);

        return new ByteArrayResource(os.toByteArray());
    }

    private Font loadFont() {
        Font customFont;
        try {
            InputStream fontStream = getClass().getResourceAsStream("/static/Museo-700/Museo500-Regular.ttf");
            if (fontStream == null) {
                throw new RuntimeException("Nie znaleziono pliku czcionki w zasobach.");
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 120f);
            fontStream.close();
            return customFont;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean validateCode(String code1, String code2, String code3, String code4) {
        if (code1 == null || code2 == null || code3 == null || code4 == null) {
            return false;
        }

        log.info("Correct codes: {}, {}, {}, {}", expectedCode1, expectedCode2, expectedCode3, expectedCode4);
        return  SimilarPatternMatch.matchesPattern(expectedCode1, code1) &&
                SimilarPatternMatch.matchesPattern(expectedCode2, code2) &&
                SimilarPatternMatch.matchesPattern(expectedCode3, code3) &&
                SimilarPatternMatch.matchesPattern(expectedCode4, code4);

    }
}
