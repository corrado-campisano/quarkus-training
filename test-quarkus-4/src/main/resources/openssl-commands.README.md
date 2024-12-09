# 
# openssl commands to be executed
# 

1) generate PAIR:
openssl genrsa -out basekey.pem 2048

2) export PRIVATE:
openssl pkcs8 -topk8 -inform PEM -in basekey.pem -out privatekey.pem -nocrypt

3) export PUBLIC:
not this:
openssl rsa -in basekey.pem -pubout privatekey.pem -outform PEM -out publickey.pem
as specified in the tutorial, but just this:
openssl rsa -in basekey.pem -outform PEM -out publickey.pem
