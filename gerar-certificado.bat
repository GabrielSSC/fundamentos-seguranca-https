@echo off
REM Gera certificado autoassinado para desenvolvimento/aula

echo Gerando certificado SSL...

keytool -genkeypair ^
    -alias myapp ^
    -keyalg RSA ^
    -keysize 2048 ^
    -storetype PKCS12 ^
    -keystore src\main\resources\myapp.p12 ^
    -validity 365 ^
    -dname "CN=localhost, OU=Aula, O=ADA, L=Cidade, ST=Estado, C=BR" ^
    -storepass changeit ^
    -keypass changeit

echo.
echo Certificado gerado em: src\main\resources\myapp.p12
echo Senha do keystore: changeit
pause
