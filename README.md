# shanyan-spring-boot-starter

#### 组件简介

> 基于 [创蓝闪验](https://shanyan.253.com/) 服务端API 实现的手机号码一键登录


#### 使用说明

##### 1、Spring Boot 项目添加 Maven 依赖

``` xml
<dependency>
	<groupId>io.github.hiwepy</groupId>
	<artifactId>shanyan-spring-boot-starter</artifactId>
	<version>${project.version}</version>
</dependency>
```

##### 2、在`application.yml`文件中增加如下配置

```yaml
#################################################################################################
### 创蓝闪验 配置：
#################################################################################################
shanyan:
  app-id: 应用ID
  app-key: 应用Key
  encrypt-type: 手机号加解密方式，值包含：0（AES加密）、1（RSA加密）缺省为0，如使用RSA方式则在创建应用时必须填写RSA公钥。
  private-key: 手机号解密私钥，encryptType 为 1（RSA加密）时，必须填写 RSA私钥
```

##### 3、使用示例

 
```java

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanyan.spring.boot.dto.FlashLoginResponse;
import com.shanyan.spring.boot.dto.FlashValidateResponse;

@SpringBootApplication
public class FlashMobileApplication_Test {
	
	@Autowired
	private FlashMobileTemplate template;
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostConstruct
	public void testLogin() {

		try {
			FlashLoginResponse response = template.login("127.0.0.1", "00001");
			System.out.println(objectMapper.writeValueAsString(response));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@PostConstruct
	public void testValidate() {

		try {
			
			FlashValidateResponse response2 = template.validate("", "");
			System.out.println(objectMapper.writeValueAsString(response2));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FlashMobileApplication_Test.class, args);
	}

}
```

## Jeebiz 技术社区

Jeebiz 技术社区 **微信公共号**、**小程序**，欢迎关注反馈意见和一起交流，关注公众号回复「Jeebiz」拉你入群。

|公共号|小程序|
|---|---|
| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/qrcode_for_gh_1d965ea2dfd1_344.jpg)| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/gh_09d7d00da63e_344.jpg)|