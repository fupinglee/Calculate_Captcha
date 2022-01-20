package com;

import com.google.code.kaptcha.Producer;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args)  {
        String rootPath = "datasets";
        ArgumentParser parser = ArgumentParsers.newFor("java -jar CalculateCaptcha.jar").build()
                .defaultHelp(true)
                .description("generate Calculate Captcha .");
        parser.addArgument("-n", "--num")
                .required(true)
                .help("生成的验证码数量");
        parser.addArgument("-p", "--path")
                .setDefault("train")
                .help("验证码生成的位置");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        int num = 2000;
        try {
            num = Integer.parseInt(ns.getString("num"));
        } catch (NumberFormatException e) {
            System.err.printf("error:%s.\nreason: num is %s"
                    , e.getMessage(),ns.getString("num"));
            System.exit(1);
        }

        String dataPath = ns.getString("path");
        String path = rootPath + File.separator + dataPath;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }

        System.out.printf("一共生成%s个样本，生成路径%s，开始生成中...\n",num,dataPath);
        Producer DefaultKaptcha = new CaptchaConfig().getKaptchaBeanMath();

        for(int i = 0 ;i < num;i++){
            String capText = DefaultKaptcha.createText();
            String capStr = capText.substring(0, capText.lastIndexOf("@"));
            BufferedImage bi = DefaultKaptcha.createImage(capStr);
            String fileName = dir.getPath()+File.separator+capStr.replace("/","÷").replace("*","×").replace("?","？") + "_" + DigestUtils.md5Hex(""+System.currentTimeMillis())+".jpg";
            try {
                ImageIO.write(bi, "jpg", new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("样本生成完毕，请查看目录"+path);
    }
}
