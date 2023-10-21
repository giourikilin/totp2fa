
# Implementation of a TOTP 2FA application

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

### Components
0. Apllication intitalizer: generates the private key when the app is started (from password or random), and stores it in a secure location (android key storage)
1. QR-code scanner: deals with scanning the qr-codes on websites and storing the TOTP fields on the device
2. OTP generator: Generates the OTPs based on the TOTP algorithm and the current time
3. Backup manager: encrypts the TOTP fields (argon2) using the symmetric key and sends the fields to storage location
4. Recovery manager: deals with backup, needs to authenticate the data

### Questions to discuss
- Where and how do we store the secret, label and issuer that was registered in the app? (locally? encrypted?)


### Interesting links
- base paper: https://www.usenix.org/system/files/usenixsecurity23-gilsenan.pdf
- combining honeytokens and google authenticator: https://arxiv.org/pdf/2112.08431.pdf 
- the design and implementation of a 2FA application: https://www.usenix.org/system/files/soups2022-poster69_smith_abstract_final.pdf
- definition on how to transfer issuer, label and secret via QR codes: https://github.com/google/google-authenticator/wiki/Key-Uri-Format
- account recovery methods for 2FA: https://digitalcommons.odu.edu/cgi/viewcontent.cgi?article=1351&context=psychology_etds

### Prerequisites
- Apps should consider not supporting plaintext backups.
- Apps should encrypt all TOTP fields, including the secret, issuer, and label.
- Apps that rely on remote key servers to generate/store random keys should choose different entities for storage of keys and ciphertext
- We have several recommendations for apps that derive keys from passwords. First, they should implement wellestablished best practices to encourage users to create strong passwords.
- NEVER allow the backup password to leave the app.
-  Once the key is derived from the password, the password should be wiped from memory and the key should be securely stored on the device using the Android Key Store 24 so that it can be used to encrypt TOTP accounts added in the future.
-  Finally, we recommend that TOTP apps that derive keys from passwords should capitalize on the fact that the KDF
operation happens so infrequently and configure it to run significantly slower than existing recommendations for password
storage.
- TOTP apps should only ever perform key derivation two times: when the user first enables the backup mechanism, and when the user is attempting to recover.
- Show a progress bar and why it takes so long to derive the key. Explain security risk metigation to user by running longer time.
- TOTP apps that derive keys from passwords should adopt the Argon2 KDF.

