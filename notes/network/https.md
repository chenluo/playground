# http & https
https = TLS + http

TLS = private key + cacert(CN:common name, domain name, digital signature, public key, ...) + tls handshake

TLS handshake: use asymmetric (private key and public key) encryption exchanges a session key (key for symmetric encryption) for following http communication.

It has multiple ciper suites. RSA is the most commonly used one. D-H is another one.

TLS handshake has 2 types: 1-way and 2-way.
1-way means only client need to verify the server. Server always trusts the client.

2-way means client and server both need verify each other. Authencation is similar but take place in both client side and server side. In this case, client also need send its cert to server and server authencate client.

# network layer

# https concept: SSL/TLS cert, CA & symmetric/asymmetric encrypt, digital signature

**cert**: CN, DN, date, issuer(ca/root ca)

**private key**: server holds it and use it decrypts the pre-mastered key (random bytes)

**client random bytes**: client generated it and send to server

**server random bytes**: server generated it and sent to client

**pre-masterd key**: client generated it after authecated the server and send it to server. This key is encrypted by server's public key (contained in the server cert). Server decrypts the encrypted key by private key.

**session key**: client and server both know client random, server random and pre-mastered key. So they can generate the exactly same session key. Session key is used to encrypt/decrypt the following http communication.

**authecation**: client want to know the server connecting is the one we actually want to connect. It's digital signature do this. 

**cert chain**: CA uses its cert to issue certs. For manage large number of certs, only issuing cert with a single cert (root cert) is not easy to mainten, like to invalidate some certs, we need to invalidate 1 by 1 (but seems hard to find all of them). So cert chain is introduced to manage certs in a tree structure. Root Cert -> Cert A -> Cert B -> our cert. 

Root Cert is pre-loaded in browser/jdk/os, so the website that using certs from an existing ca will be automatically trusted. The authencation process is like, get issuer from the public cert from server, and then find its issuer's issuer. Finally, it goes to the root cert from a CA. Then we can verify the chain is valid or not. If we want to invalidate all the certs issued by a intermediate cert, then we just invalidate the intermediate cert. Then all done.

**encrption**: symmetric encryption with pre-exchanged session key

## practics
**p12, pem, cer, crt extension**

p12 files include private key and cert in the same file

pem:

cer/crt: only cert

These files are text files. Some are protected by pass. There exists binary formated cert files.

Many tools can generate and convert/extract these files.

**openssl**

**keytool**
tool specific for java. jks is specific for java. Recently, p12 is the new default format.

**tcpdump for TLS handshake**

# https in java: keytool, keystore & pratics in springboot

create new p12 cert: 

```
keytool -genkeypair -alias springboot -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore springboot.p12 -validity 3650 -storepass password 
```
View the cert:

```
keytool -list -v -keystore springboot.p12 
```
extract cert for client (self-sign cert is not trusted by default)

```
keytool -export -keystore springboot.p12 -alias springboot -file myCertificate.crt
```

extrac pem file for curl -cacert (different programs have different way to trust the ca. curl can using provided pem files in arguments. java seems have to add cacert to built-in jks or run with trustStore arguments. keystore in java is kind of database for certs, private keys. It can hold many X.509 entries.)

```
openssl pkcs12 -in springboot.p12 -out cacert.pem -nodes   
```

import cert to jre keystore (for writing a java client to call a https server)

```
keytool -importcert -file myCertificate.crt -alias springboot -keystore $JDK_HOME/jre/lib/security/cacerts
```

add the springboot.p12 file to src/main/resource and configure the application.yml pointing to this file with password.

