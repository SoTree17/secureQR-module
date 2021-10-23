# secureQR-module
[ENGLISH README](https://github.com/SoTree17/secureQR-module/blob/main/README_EN.md)   
SecureQR은 자바에서 사용가능한 위변조 방지 기능이 있는 QR 코드 라이브러리 입니다.

# Get Started
Java 프로젝트에 JAR 파일을 외부 라이브러리로 추가함으로써 SecureQR을 사용하실 수 있습니다.

## Download
Github 릴리즈 탭에서 [JAR 파일](https://github.com/SoTree17/secureQR-module/releases)을 다운받으실 수 있습니다.

## How to SecureQR Works?
![howtowork](https://user-images.githubusercontent.com/48395704/137630194-610db5c1-fffc-44ee-b37c-f7b2d4bc0ea3.gif)
1. 보안 QR 리더기를 통해 보안 QR코드(secureQR-module에 의해 생성된 QR코드)를 스캔합니다.
2. QR코드 속 데이터와 몇 가지 정보가 secureQR-module-android에 의해 서버로 전송됩니다.
3. 서버는 데이터를 복호화하고 올바른 데이터인지 검증합니다.
4. 만약 복호화와 검증에 성공하였다면, QR코드 속 원본 데이터를 돌려줍니다.

### secureQR 예시

<img style="width:70%; height:70%" src="https://user-images.githubusercontent.com/48395704/138459799-3e65b3a9-ae1c-495d-a104-aa51ca927dac.png">


## Usage
### crypto
 > <b>Interface SecureQrCrypto</b>  
SecureQrCrypto 인터페이스를 구현함으로써, 사용자 정의 암호화 객체를 secureQR에서 사용할 수 있습니다.

|Type|Method|Description|  
|----|------|-----------|
|String|getInstanceType()|현재 인스턴스 타입을 반환합니다. 만약 암호화에 관한 객체라면 "CRYPTO"를 반환합니다.|
|String|getMethodType()|암호화 방식을 반환합니다.|
|String|encrypt(String message)|암호화된 문자열을 반환합니다.|
|String|decrypt(String message)|복호화된 문자열을 반환합니다.|
<br>

 > <b>Interface SecureQrHash</b>      
 SecureQrHash 인터페이스를 구현함으로써, 사용자 정의 해시함수를 secureQR에서 사용할 수 있습니다.
 
  |Type|Method|Description|  
|----|------|-----------|
|String|getInstanceType()|현재 인스턴스 타입을 반환합니다. 만약 해시에 관한 객체라면 "HASH"를 반환합니다.|
|String|getMethodType()|해시 방식을 반환합니다.|
|String|hashing(String message)|해시된 메시지를 반환합니다.|
<br>

> <b>class SecureQrCryptoArray</b>    
SecureQrHash, SecureQrCrypto 그리고 데이터를 저장하는 배열입니다. 해시, 암호화 방식은 MethodPair를 통해 crypto_arr에 저장되고 데이터는 data_arr에 저장됩니다.
 
 |Type|Method|Description|  
|----|------|-----------|
|Constructor|SecureQrCryptoArray()|crypto_arr 와 data_arr 를 빈 ArrayList로 초기화합니다.|
|int|crypto_size()|crypto_arr의 이즈를 반환합니다.|
|int|data_size()|data_arr의 사이즈를 반환합니다.|
|void|**add(SecureQrHash _h, SecureQrCrypto _c)**|(해시, 암호화) 쌍을 crypto_arr에 추가합니다.|
|SecureQrHash|getHash(int index)|index 위치에 있는 해시함수를 반환합니다.|
|SecureQrCrypto|getCrypto(int index)|index 위치에 있는 암호화 방식을 반환합니다.|
|int|**addData(String _data)**|데이터를 data_arr에 추가하고 그 데이터가 들어있는 index를 반환합니다. 이 index는 secureQR 인증에 필요합니다.|
|String|getData(int index)|index 위치에 있는 데이터를 반환합니다.|
<br>

### qr_util
> <b>class MethodPair</b>    
해시, 암호화 객체 쌍을 저장하는 객체입니다.  
 
 |Type|Method|Description|  
|----|------|-----------|
|Constructor|MethodPair(SecureQrHash _h, SecureQrCrypto _c)|MethodPair를 매개변수로 초기화합니다.|
|SecureQrCrypto|getCrypto()|저장된 암호화 객체를 반환합니다.|
|SecureQrHash|getHash()|저장된 해시 객체를 반환합니다.|

<br>

 > <b>class RandomString</b>       
랜덤 문자열을 생성하는 객체입니다.

|Type|Method|Description|  
|----|------|-----------|
|String|getString(int length)|length 길이의 랜덤 문자열을 반환합니다.|   
<br>

### qr
> **Interface Generatable** <br/>
> Generatable 인터페이스를 구현함으로써, 보안 QR코드를 생성할 수 있습니다.

| Return Type | Method                | Params                                                       | Description                                                  |
| ----------- | --------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| byte[]      | createSecureQRcode()  | `SecureQrCryptoArray` arr : 해시,암호화방식, 데이터가 저장된 SecureQrCryptoArray 객체입니다. <br />`String `authUrl : 보안 QR코드를 인증할 서버의 URL입니다. <br />`int` c_index : SecureQrCryptoArray에서 사용할 (암호화, 해시) 쌍이 저장된 index입니다. <br />`int` d_index : SecureQrCryptoArray에서 사용할 데이터의 index입니다. <br />`int` width, `int` height : QR 이미지의 가로, 세로 크기를 지정합니다. | 보안 QR코드 이미지에 대한 `PNG` byte array를 반환합니다. <br />             |
| boolean     | createSecureQRImage() | `byte[]` qr_byte_arr : 보안 QR코드 이미지의 byte array 입니다.<br /> `int` off : 시작 offset (일반적으로 0)<br />String path : 이미지를 저장할 경로 | 보안 QR 코드를 저장하고, 성공했으면 True, 아니면 False를 반환합니다. |



> **class Generator** <br/>
> 보안 QR코드를 생성하는 클래스 입니다.

| Type   | Method                                                       | Description                                                  |
| ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| byte[] | **createSecureQRCode(SecureQrCryptoArray arr, String authUrl, int c_index, int d_index, int width, int height)** | `SecureQrCryptoArray`의 `c_index에` 있는 암호화 방식을 사용하여 `d_index`에 있는 데이터를 암호화합니다. 그리고 `authURL`, `c_index`, `d_index`, 암호화된 데이터를 함께 JSON으로 묶은 뒤 이를 저장한 QR코드를 생성합니다. 생성된 QR코드 이미지를 byte array 형태로 반환합니다. |
| bool   | **createSecureQRImage(byte[] qr_byte_arr, int off, String path)** | `qr_byte_arr`로 들어온 byte array 를 QR코드 이미지로 변환한 뒤, `path`에 저장합니다. |

> **Interface Readable**<br/>
> Readable 인터페이스를 구현함으로써,  QR 이미지 파일을 읽고 데이터를 추출할 수 있습니다. 

| Return Type | Method                                 | Params             | Description                                                  |
| ----------- | -------------------------------------- | ------------------ | ------------------------------------------------------------ |
| String      | **readSecureQRCode(File qrCodeImage)** | `File` qrCodeImage | QR 이미지 파일을 인자로 받아서, QR 이미지에 담긴 데이터를 호출자에게 반환합니다. |
| String      | **readSecureQRCode(String filepath)**  | `String` filepath  | QR 이미지 파일의 경로를 인자로 받아서, QR 이미지에 담긴 데이터를 호출자에게 반환합니다. |

> **class Reader** <br/>
> QR코드를 읽어서 QR 코드내의 데이터를 추출하는 클래스입니다.

| Type   | Method                                 | Description                                                  |
| ------ | -------------------------------------- | ------------------------------------------------------------ |
| String | **readSecureQRCode(File qrCodeImage)** | QR 코드 이미지 파일을 읽고, <br />안에 담긴 데이터값을 반환합니다. |
| String | **readSecureQRCode(String filepath)**  | QR 이미지 파일이 저장된 파일 경로를 읽어<br />값을 반환하는 메소드입니다. |



> <b>class AuthQR</b>    
secureQR을 인증하는 클래스입니다. 
 
 |Type|Method|Description|  
|----|------|-----------|
|Constructor|**AuthQR(SecureQrCryptoArray arr)**|secureQR 인증에 사용될 SecureQrCryptoArray를 등록함으로써 authQR을 초기화합니다.|
|boolean|isSecureQR(String read_data)|데이터가 secureQR의 형식과 같은지 확인합니다.|
|String|**getOriginData(String encrypted, int c_index, int d_index)**|`SecureQrCryptoArray `의 `c_index`에 있는 해시 함수와 암호화 방식으로 `encrypted`를 복호화하고, `d_index`에 있는 데이터와 비교하여 secureQR을 인증합니다. 만약 모든 인증에 통과했다면, 원본 데이터를 반환합니다. |
<br>

## Build 
이 프로젝트는 Gradle로 빌드할 수 있습니다.  
gradle tasks 중에 <code>jar</code>을 실행하면 , 컴파일 된 jar파일을 <code>/build/libs/secureQR-module-x.y.z.jar</code> 에서 찾으실 수 있습니다.

### dependency
* zxing:core 3.4.1
* zxing:javase 3.4.1
* gson 2.8.8

## Open Source Lisence
### Copyright

|Component|Version|Homepage|License|
|----|------|-----------|------|
|Zxing|3.4.1|https://github.com/zxing/zxing|Apache-2.0 License|
|Gson|2.8.8|https://github.com/google/gson|Apache-2.0 License|
|Junit|4.13.2|https://junit.org/junit4|Eclipse Public License 1.0|

## Contribution
### Reporting bugs
Bugs are tracked as [GitHub issues](https://github.com/SoTree17/secureQR-module/issues).  
Create an issue on this repository and if possible, please provide the following information.  
* Use a clear and descriptive title
* Describe the exact steps which reproduce the problem
* Include screenshots and animated GIFs  
  
### Suggesting Enhancements
Enhancement suggestions are tracked as [GitHub issues](https://github.com/SoTree17/secureQR-module/issues).  
Create an issue on this repository and if possible, please provide the following information.  
* Describe the current behavior and explain which behavior you expected to see instead and why
* Include screenshots and animated GIFs
* Explain why this enhancement would be useful  

### Open Pull Requests 
A [Pull Requests](https://github.com/SoTree17/secureQR-module/pulls) (PR) is the step where you submit patches to this repository.   
(e.g. adding features, renaming a variable for clarity, translating into a new language)  
  
If you're not familiar with pull requests, you can follow these steps.  
1. Fork this project and clone your fork    
~~~
git clone https://github.com/<user-name>/secureQR-module.git
cd secureQR-module
~~~
2. Create a new topic branch (off the main project development branch) to contain your feature, change, or fix
~~~
git checkout -b <topic-branch-name>
git pull
~~~
3. Developing a new feature
4. Push the feature to your fork
~~~
git push origin <topic-branch-name>
~~~
5. Open a [Pull Requests](https://github.com/SoTree17/secureQR-module/pulls) with a description
