package com.yinhetianze.business.customer.util;

import com.github.binarywang.utils.qrcode.BufferedImageLuminanceSource;
import com.github.binarywang.utils.qrcode.MatrixToImageWriter;
import com.github.binarywang.utils.qrcode.MatrixToLogoImageConfig;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yinhetianze.core.utils.MD5CoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Map;

import java.nio.channels.FileChannel.MapMode;

public class CustomerUtil {


    /**
     * 校验密码是否一致
     * @param passwordFromDatabase 数据库存储密码
     * @param passwordFromInput 页面输入密码
     * @return
     */
    public static boolean checkPassword(String passwordFromDatabase,String passwordFromInput){
        if(passwordFromDatabase == null || passwordFromInput == null)
            return false;
        String ma5Password = MD5CoderUtil.encode(MD5CoderUtil.encode(passwordFromInput));
        return (ma5Password.equalsIgnoreCase(passwordFromDatabase));
    }


    public static String createPassword(String passwordFromInput){
        return MD5CoderUtil.encode(MD5CoderUtil.encode(passwordFromInput));
    }



    private static final int DEFAULT_LENGTH = 400;
    private static final String FORMAT = "jpg";
    private static Logger logger = LoggerFactory.getLogger(QrcodeUtils.class);

    private static BitMatrix createQrcodeMatrix(String content, int length) {
        Map<EncodeHintType, Object> hints = Maps.newEnumMap(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 0);

        try {
            return (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, length, length, hints);
        } catch (Exception var4) {
            logger.warn("内容为：【" + content + "】的二维码生成失败！", var4);
            return null;
        }
    }

    public static byte[] createQrcode(String content, int length, File logoFile) {
        if (logoFile != null && !logoFile.exists()) {
            throw new IllegalArgumentException("请提供正确的logo文件！");
        } else {
            BitMatrix qrCodeMatrix = createQrcodeMatrix(content, length);
            if (qrCodeMatrix == null) {
                return null;
            } else {
                try {
                    File file = Files.createTempFile("qrcode_", ".jpg").toFile();
                    logger.debug(file.getAbsolutePath());
                    MatrixToImageWriter.writeToFile(qrCodeMatrix, "jpg", file);
                    if (logoFile != null) {
                        BufferedImage img = ImageIO.read(file);
                        overlapImage(img, "jpg", file.getAbsolutePath(), logoFile, new MatrixToLogoImageConfig());
                    }

                    return toByteArray(file);
                } catch (Exception var6) {
                    logger.warn("内容为：【" + content + "】的二维码生成失败！", var6);
                    return null;
                }
            }
        }
    }

    public static byte[] createQrcode(String content, File logoFile) {
        return createQrcode(content, 400, logoFile);
    }

    private static byte[] toByteArray(File file) {
        try {
            FileChannel fc = (new RandomAccessFile(file, "r")).getChannel();
            Throwable var2 = null;

            byte[] var5;
            try {
                MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0L, fc.size()).load();
                byte[] result = new byte[(int)fc.size()];
                if (byteBuffer.remaining() > 0) {
                    byteBuffer.get(result, 0, byteBuffer.remaining());
                }

                var5 = result;
            } catch (Throwable var15) {
                var2 = var15;
                throw var15;
            } finally {
                if (fc != null) {
                    if (var2 != null) {
                        try {
                            fc.close();
                        } catch (Throwable var14) {
                            var2.addSuppressed(var14);
                        }
                    } else {
                        fc.close();
                    }
                }

            }

            return var5;
        } catch (Exception var17) {
            logger.warn("文件转换成byte[]发生异常！", var17);
            return null;
        }
    }

    private static void overlapImage(BufferedImage image, String format, String imagePath, File logoFile, MatrixToLogoImageConfig logoConfig) throws IOException {
        try {
            BufferedImage logo = ImageIO.read(logoFile);
            Graphics2D g = image.createGraphics();
            int width = image.getWidth() / logoConfig.getLogoPart();
            int height = image.getHeight() / logoConfig.getLogoPart();
            int x = (image.getWidth() - width) / 2;
            int y = (image.getHeight() - height) / 2;
            g.drawImage(logo, x, y, width, height, (ImageObserver)null);
            g.setStroke(new BasicStroke((float)logoConfig.getBorder()));
            //g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, width, height);
            g.dispose();
            ImageIO.write(image, format, new File(imagePath));
        } catch (Exception var11) {
            throw new IOException("二维码添加logo时发生异常！", var11);
        }
    }

    public static String decodeQrcode(File file) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = Maps.newEnumMap(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, Charsets.UTF_8.name());
        return (new MultiFormatReader()).decode(binaryBitmap, hints).getText();
    }
/*
    public static void main(String[] args) throws Exception{
            File logFile = new File("C:\\Users\\Administrator\\Desktop\\qrCode_logo.png");
            String dir = "C:\\Users\\Administrator\\Desktop\\";
            byte[] b = CustomerUtil.createQrcode("" + "?recommendCode=", logFile);
            java.io.File fileDir = new java.io.File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            java.io.File qrcodeFile = new java.io.File(dir + "abc3.png");
            FileOutputStream fos = new FileOutputStream(qrcodeFile);
            fos.write(b);
    }
    */
}
