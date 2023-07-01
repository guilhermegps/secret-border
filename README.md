# Secret Border

> A safe Nostr identity generator.

![](https://guilhermegps.com.br/img/secret_border.png)

## Why use Secret Border?

This tool is an easy and safe way to create your personal identity on [Nostr](https://nostr.com/) protocol. Using it you can generate a pair of Nostr keys and backup them in a safe way.

## What are the security guarantees that Secret Border supply?

 - It can be used completely offline.
 - Use **cryptographically strong pseudo-random number generator** ([CSPRNG](https://en.wikipedia.org/wiki/Cryptographically_secure_pseudorandom_number_generator)).
 - It doesn't save your secret anywhere (**unless you request it**).
 - You can backup your secret in an encrypted file with a password (that you specify). 
 - Code full open source.
 - It can backup secret in a encrypted file using **AES256**.
 - Allow user to add entropy at the generate key process.

## Features

 - [x] Generate pair of keys randomly
 - [x] Show keys
 - [x] Not connect externally with whatever
 - [x] Export private key to an encrypted file
 - [x] Import an encrypted file with a private key
 - [x] Enter entropy to complement key generation
 - [] Support for basic key derivation from mnemonic seed phrase - [NIP06](https://github.com/nostr-protocol/nips/blob/master/06.md) ⏳
   

## Building

To build it you will need to have **Java 17** and **Maven** installed on your system. 

Now just run:
  

```bash
mvn install
mvn package
```
   

## Running

Use **Java 17** installed on your system to run the JAR file.

You can run it on your terminal/bash:
  
```bash
java -jar target/secret-border.jar
```
