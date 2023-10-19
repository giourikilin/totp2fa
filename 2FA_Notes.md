
# Implementation of a TOTP F2A application

## Implementation
- qr-code scanner to allow users to register secret, label and issuer 
- implementation of TOTP algrorithm to generate the TOTP from the secret and the time (documented [here](https://datatracker.ietf.org/doc/html/rfc6238)) - maybe available from a library
- set up backup mechanism: encrypt the TOTP fields
- store the encrypted TOTP fields in one location
- store the key in another
...

### Questions to discuss
- Where and how do we store the secret, label and issuer that was registered in the app? (locally? encrypted?)


### Interesting links
- combining honeytokens and google authenticator: https://arxiv.org/pdf/2112.08431.pdf 
- the design and implementation of a 2FA application: https://www.usenix.org/system/files/soups2022-poster69_smith_abstract_final.pdf


