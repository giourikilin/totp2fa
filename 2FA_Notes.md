
# Implementation of a TOTP F2A application

## Implementation
- qr-code scanner to allow users to register secret, label and issuer (the transfer format is defined [here](https://github.com/google/google-authenticator/wiki/Key-Uri-Format))
- implementation of TOTP algrorithm to generate the TOTP from the secret and the time (documented [here](https://datatracker.ietf.org/doc/html/rfc6238)) - maybe available from a library
- set up backup mechanism: encrypt the TOTP fields, choose the key derivation function (with or without password - "Using random keys is cryptographically ideal, but it
introduces a significant key management challenge")
- if we use a password, implement the best password policy
- use an adequate KDF: argon2! Also, salt the data properly
- store the encrypted TOTP fields and the encryption key in separate locations!

- verify ciphertext integrity /authenticate the ciphertext eg HMAC-SHA256 integrity (make sure the ciphertext has not been tampered with in transit or storage - needs to be done before decryption)

- if we support android auto backup, no backing up of the TOTP fields!

- implement recovery (how do we auhtenticate users during recovery? Maybe fingerprint data?, or use [this](https://arxiv.org/pdf/2112.08431.pdf) approach using honeywords?)
- Account recovery is essentially a bypass of the system’s main
security protocols and needs to be handled as an alternative authentication process
- maybe use an account recovery code 
...

### Questions to discuss
- Where and how do we store the secret, label and issuer that was registered in the app? (locally? encrypted?)


### Interesting links
- base paper: https://www.usenix.org/system/files/usenixsecurity23-gilsenan.pdf
- combining honeytokens and google authenticator: https://arxiv.org/pdf/2112.08431.pdf 
- the design and implementation of a 2FA application: https://www.usenix.org/system/files/soups2022-poster69_smith_abstract_final.pdf
- definition on how to transfer issuer, label and secret via QR codes: https://github.com/google/google-authenticator/wiki/Key-Uri-Format
- account recovery methods for 2FA: https://digitalcommons.odu.edu/cgi/viewcontent.cgi?article=1351&context=psychology_etds


