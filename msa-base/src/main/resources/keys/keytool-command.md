# server keystore
command to generate keystore
```
keytool -genkeypair -alias springboot  -keyalg RSA -keysize 4096 -keystore springboot.jks -storepass password -validity 365 # private and pubulic key
keytool -list -v -keystore springboot.jks
keytool -list -rfc -keystore springboot.jks
```

# client keystore

generate client keystore

```
keytool -genkeypair -alias springboot  -keyalg RSA -keysize 4096 -keystore springboot.jks -storepass password -validity 365 # private and pubulic key
```

# truststore

```
keytool -exportcert -noprompt -rfc -alias springboot -file localhost.crt -keystore springboot.jks -storepass password -storetype JKS
keytool -importcert -noprompt -alias springboot -file localhost.crt -keypass password -keystore trust.jks -storepass password -storetype JKS
```

get trust.jks. It's the server trust itself.

similarly, import client certificate to trust.jks
```
keytool -exportcert -noprompt -rfc -alias springboot -file localhost.crt -keystore springboot.jks -storepass password -storetype JKS
keytool -importcert -noprompt -alias client -file client.crt -keypass password -keystore trust.jks -storepass password -storetype JKS
```


# curl

```
# convert to p12
keytool -importkeystore -srckeystore client.jks -destkeystore client.p12 -srcstoretype JKS -deststoretype PKCS12 -deststorepass password
## curl with client certificate
curl -k -v --cert-type p12 --cert client.p12:password https://localhost:8081/
```
