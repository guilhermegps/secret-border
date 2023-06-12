# Secret Border

A safe Nostr identity generator.

## Why use Secret Border?

This tool is a easy and safe way to create your personal identity on [Nostr](https://nostr.com/) protocol. Using it you can to generate a pair of Nostr keys and backup them in a safe way.

## What is the security guarantees that Secret Border supply?

 - It can be used completely offline.
 - Use **cryptographically strong pseudo-random number generator** ([CSPRNG](https://en.wikipedia.org/wiki/Cryptographically_secure_pseudorandom_number_generator)).
 - It don't save your secret anywhere (**unless you request it**).
 - You can backup your secret in a encrypted file with a password (that you specify). 

## Features

 - [x] Generate pair of key randomly
 - [x] Show keys
 - [x] Not connect externally with whatever
 - [ ] Export private key to an encrypted file ⏳
 - [ ] Import an encrypted file with a private key ⏳
   

## Building

For building you will need to have **Java 17** installed on your system. 

Now just run:
  

```bash
javac -sourcepath ./src/ -d . ./src/secretborder/window/HOME.java
echo Main-Class: secretborder.window.HOME > MANIFEST.MF
jar -cvmf MANIFEST.MF secret-border.jar ./secretborder/*
```
   

## Running

Use **Java 17** installed on your system to run the Jar file.

You can run it on your terminal/bash:
  
```bash
java -jar secret-border.jar
```
