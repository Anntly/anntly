package com.anntly.shop.web;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码调用前端控制器
 */
@RequestMapping("/Qrcode")
@RestController
public class QrcodeController {


    @RequestMapping(value = "/getQ", method = { RequestMethod.POST, RequestMethod.GET })
    public void getqcode(HttpServletResponse resp,
                         @RequestParam("restaurantId") Long restaurantId,
                         @RequestParam("deskId") Long deskId) throws IOException {
        String url = "http://www.anntly.com/"+restaurantId+"/"+deskId;
        if (url != null && !"".equals(url)) {
            ServletOutputStream stream = null;
            try {

                int width = 200;//图片的宽度
                int height = 200;//高度
                stream = resp.getOutputStream();
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix m = writer.encode(url, BarcodeFormat.QR_CODE, height, width);
                MatrixToImageWriter.writeToStream(m, "png", stream);
            } catch (WriterException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
    }

}
