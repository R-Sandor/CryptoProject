# CryptoProject

- Raphael J. Sandor
- Fall Semester 2022

Cryptography algorithms for cybersecurity Final Project
Bench marks the performance of Diffie-Hellman using different typos attacks

- Naive Brute Force
- Baby Step Giant Step
- Pollard's Rho Method

# Objectives

Demonstrate the algorithmic complexity of both the cryptographic algorithms and the various attacks in which the susceptible.
In the process objectives of the course cs563 were also applied, for example the topic of certificate management and secure connections
between host and client. The application of this topic can be seen with www.findfirst.dev which has a secure connection between client and
server using an SSL certificate. The certificate aquired from a CA in this it was LetsEncrypt.org, a open source project that provides certificates.

To see a running demo visit
http://www.findfirst.dev

# Requirements

- Java 17 JDK
- Node package manager (8.5.5+)
- Node.js (16.15.0+)

### To run locally call the utility startup script on a unix terminal.

chmod u+x startCrypto.sh \
./startCrypto.sh

Navigate to http://localhost:8080

### Notes

- Not optimized for mobile devices.
- Currently only diffie-hellman has attacks.
