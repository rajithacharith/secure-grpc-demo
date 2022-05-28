echo "----------------------------------------------------------------------------------"
echo "|      Generating private keys and certs for CarParkServer and CarParkClient     |"
echo "----------------------------------------------------------------------------------"

# Check the availability of openssl.
readonly KEYTOOL=$(which keytool)
if [[ ! ${KEYTOOL} ]]
then
    echo "keytool is not installed. Exiting !"
    exit 1
fi

# Use keytool to generate keystores and truststores.
echo "Generating keystore for carparkserver.........."
${KEYTOOL} -genkey -alias carparkserver -keyalg RSA -keysize 2048 \
        -storetype PKCS12 \
        -keystore carparkserver-keystore.jks \
        -dname "CN=localhost" \
        -storepass 111111 \
        -keypass 111111

echo "Exporting carparkserver certificate........."
${KEYTOOL} -export -alias carparkserver \
        -keystore carparkserver-keystore.jks \
        -file carparkserver.crt \
        -rfc -storepass 111111

echo "Generating a dummy truststore for carparkserver........."
${KEYTOOL} -genkey -alias carparkserver -keyalg RSA -keysize 2048 \
        -storetype PKCS12 \
        -keystore carparkserver-truststore.jks \
        -dname "CN=localhost" \
        -storepass 111111 \
        -keypass 111111

echo "Generating keystore for carparkclient........."
${KEYTOOL} -genkey -alias carparkclient -keyalg RSA -keysize 2048 \
        -storetype PKCS12 \
        -keystore carparkclient-keystore.jks \
        -dname "CN=localhost" \
        -storepass 111111 \
        -keypass 111111

echo "Exporting carparkclient certificate........."
${KEYTOOL} -export -alias carparkclient \
        -keystore carparkclient-keystore.jks \
        -file carparkclient.crt \
        -rfc -storepass 111111

echo "Generating a dummy truststore for carparkclient........."
${KEYTOOL} -genkey -alias carparkclient -keyalg RSA -keysize 2048 \
        -storetype PKCS12 \
        -keystore carparkclient-truststore.jks \
        -dname "CN=localhost" \
        -storepass 111111 \
        -keypass 111111

echo "Importing carparkclient cert to carparkserver truststore........."
${KEYTOOL} -delete -alias carparkserver \
        -keystore carparkserver-truststore.jks \
        -storepass 111111

${KEYTOOL} -import -alias carparkclient -file carparkclient.crt \
        -keystore carparkserver-truststore.jks \
        -storepass 111111 \
        -noprompt

echo "Importing carparkserver cert to carparkclient truststore........."
${KEYTOOL} -delete -alias carparkclient \
        -keystore carparkclient-truststore.jks \
        -storepass 111111

${KEYTOOL} -import -alias carparkserver -file carparkserver.crt \
        -keystore carparkclient-truststore.jks \
        -storepass 111111 \
        -noprompt


