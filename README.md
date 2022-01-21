# Calculate_Captcha
若依验证码生成器，用于训练用.

## 1.使用
1.自行打包使用

```
git clone https://github.com/fupinglee/Calculate_Captcha
cd Calculate_Captcha
mvn clean package -DskipTests=true
```
然后执行target下的jar文件

2.直接下载使用

https://github.com/fupinglee/Calculate_Captcha/releases
## 2.使用说明

### 1.查看帮助
```bash
java -jar  CalculateCaptcha.jar -h 
```
结果：
```
usage: java -jar CalculateCaptcha.jar [-h] -n NUM [-p PATH]
generate Calculate Captcha .

named arguments:
  -h, --help             show this help message and exit
  -n NUM, --num NUM      生成的验证码数量
  -p PATH, --path PATH   验证码生成的位置 (default: train)
```

### 2.生成验证码
如生成200个验证码，目录为`test`（默认生成目录为`train`）
```bash
java -jar  CalculateCaptcha.jar -n 200 -p test
一共生成200个样本，生成路径test，开始生成中...
样本生成完毕，请查看目录datasets/test
```

## 3.说明
直接使用的若依的生成验证码的程序。

在生成验证码时，由于会出现`*`、`/`、`?`等，在使用其作为文件名时不合法，所以进行了替换。

例如将除号`/`替换为`÷`，乘号`*`替换为`×`，问号`?`替换为`？`。

同时为了避免生成的验证码重复，生成的文件名拼接了时间戳的md5，并用`_`连接。

主要代码如下：
```java
import com.google.code.kaptcha.Producer;
import org.apache.commons.codec.digest.DigestUtils;

public class GenCalculateCaptchaTest {
    public static void main(String[] args) {
        Producer kaptcha = new CaptchaConfig().getKaptchaBeanMath();
        String capText = kaptcha.createText();
        String capStr = capText.substring(0, capText.lastIndexOf("@"));
        System.out.println(capStr);
        String newCapStr = capStr.replace("/","÷").replace("*","×").replace("?","？") + "_" + DigestUtils.md5Hex(""+System.currentTimeMillis())+".jpg";
        System.out.println(newCapStr);
    }
}

```
结果:
```
生成的验证码：0*6=?
保存的文件名：0×6=？_a21a06df03aac093674100949e8ff582.jpg
```

图片示例：

`2÷1=？_c893df08dbb80e70e26b7e352af0d4a4.jpg`

![image](images/2÷1=？_c893df08dbb80e70e26b7e352af0d4a4.jpg)

`3×5=？_44385059acbc138515e324c8e08af20c.jpg`

![image](images/3×5=？_44385059acbc138515e324c8e08af20c.jpg)



