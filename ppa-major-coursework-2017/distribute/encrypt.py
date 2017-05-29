import binascii

from Crypto.Cipher import AES

from cryptor import Cryptor

# https://gist.github.com/marcoslin/8026990
def encrypt(key, plaintext):
    
    iv, encrypted = Cryptor.encrypt(plaintext, key, "")

    result = {
        "key": Cryptor.KEY,
        "iv": iv,
        "ciphertext": binascii.b2a_base64(encrypted).rstrip()
    }

    return result["ciphertext"]

#def __init__():