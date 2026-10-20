# HTTPS Exercícios - Spring Boot

## Antes de começar

1. Certifique-se de ter Java 17+ e Maven 3.8+ instalados
2. Execute o script de geração de certificado:
   ```bash
   # Linux/Mac
   ./gerar-certificado.sh

   # Windows
   gerar-certificado.bat
   ```
3. O certificado será gerado em `src/main/resources/myapp.p12` com senha `changeit`


## Checklist Final

Após completar todos os exercícios, verifique:

- [ ] `https://localhost:443/hello` responde com HTTPS
- [ ] `http://localhost:8080/hello` redireciona para HTTPS
- [ ] Header `Strict-Transport-Security` presente nas respostas
- [ ] Login cria cookie com `HttpOnly`, `Secure`, `SameSite=Strict`
- [ ] Profile só funciona com cookie válido
- [ ] TLS 1.1 é rejeitado
- [ ] TLS 1.3 é aceito

