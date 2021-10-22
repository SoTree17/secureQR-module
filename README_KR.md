# secureQR-module
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
|void|**add(SecureQrHash _h, SecureQrCrypto _c)**|Add hash, crypto pair to crypto_arr.|
|SecureQrHash|getHash(int index)|Get the hash method at the index location.|
|SecureQrCrypto|getCrypto(int index)|Get the encryption method at the index location.|
|int|**addData(String _data)**|Add data to data_arr and return the index where the data is located. This index is required for secureQR authentication.|
|String|getData(int index)|Get the data at index position.|
<br>

### qr_util
> <b>class MethodPair</b>    
A pair of hash objects and cryptographic objects.  
 
 |Type|Method|Description|  
|----|------|-----------|
|Constructor|MethodPair(SecureQrHash _h, SecureQrCrypto _c)|Initialize MethodPair with parameters.|
|SecureQrCrypto|getCrypto()|Returns the stored cryptographic object.|
|SecureQrHash|getHash()|Returnsthe stored hash object.|

<br>

 > <b>class RandomString</b>       
A class that generates a random string.

|Type|Method|Description|  
|----|------|-----------|
|String|getString(int length)|Returns a random string of arbitrary length.|   
<br>

### qr
> **Interface Generatable** <br/>
> By implementing the Generatable interface, you can create secure QR code in two ways.

| Return Type | Method                | Params                                                       | Description                                                  |
| ----------- | --------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| byte[]      | createSecureQRcode()  | `SecureQrCryptoArray` arr : the array in which the data for encrypting is stored <br />`String `authUrl : Assigned authentication server URL <br />`int` c_index : index of Hasing method. <br />`int` d_index : index of Data stored in SecureQrCryptoArray<br />`int` width, `int` height : the size of QR image width and height | Returns byte array of image format `PNG`. <br />             |
| boolean     | createSecureQRImage() | `byte[]` qr_byte_arr : the data which is encrypted<br /> `int` off : starting offset<br />String path : your desired file path | Save secureQR on your local drive <br />then, Returns the boolean as a result |



> **class Generator** <br/>
> Generates an encrypted `secure QR` image with hashing method, encryption method. 

| Type   | Method                                                       | Description                                                  |
| ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| byte[] | **createSecureQRCode(SecureQrCryptoArray arr, String authUrl, int c_index, int d_index, int width, int height)** | To pass it to the server to decrypt secure QR image, it creates serialized Json form first.<br />Then, get an access to `SecureQrCryptoArray` arr with `c_index` and `d_index` to specify which data needs to be Encrypted.<br />Finally, Create QR code image, formatted PNG. <br /> Return its byte data to the caller |
| bool   | **createSecureQRImage(byte[] qr_byte_arr, int off, String path)** | To save `secureQR` image on caller's disk,<br />it tries to save on specific file path as argument entered. <br /> Returns its success or failure |


TODO reading



> <b>class AuthQR</b>    
A pair of hash objects and cryptographic objects.  
 
 |Type|Method|Description|  
|----|------|-----------|
|Constructor|**AuthQR(SecureQrCryptoArray arr)**|Initialize AuthQR by registering SecureQrCryptoArray to be used for secureQR authentication.|
|boolean|isSecureQR(String read_data)|Check whether the data is the same as the data format in secureQR.|
|String|**getOriginData(String encrypted, int c_index, int d_index)**|Decrypts the data using the encryption and hash method in c_index and checks whether the data is correct by comparing it with the data in d_index. If all checks have passed, return the original data.|
<br>

## Build
This project can be built with gradle.   
If you run <code>jar</code> among gradle tasks, you can find the compiled jar file in <code>/build/libs/secureQR-module-x.y.z.jar</code>

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
