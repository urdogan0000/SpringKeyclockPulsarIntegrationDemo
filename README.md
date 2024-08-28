# Pulsar-Keycloak-SpringBoot Integration

This repository provides a step-by-step guide to integrating Apache Pulsar with Keycloak for authentication and
authorization within a Spring Boot application. By following this guide, you will set up a secure messaging platform
that leverages OAuth2 for managing access and identities.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Keycloak Setup](#keycloak-setup)
- [Apache Pulsar Setup](#apache-pulsar-setup)
- [Spring Boot Application Setup](#spring-boot-application-setup)
- [Obtaining Keycloak Configuration Details](#obtaining-keycloak-configuration-details)
- [Running the Application](#running-the-application)
- [Sample Endpoints](#sample-endpoints)

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- Docker and Docker Compose
- Java Development Kit (JDK)
- Maven
- Apache Pulsar

## Keycloak Setup

1. **Navigate to the Keycloak Configuration:**
    - Go to the `keycloak_installation` folder in this repository.

2. **Start Keycloak using Docker Compose:**
    - Locate the `docker-compose.yml` file.
    - Run the following command to bring up Keycloak:

      ```bash
      docker-compose up -d
      ```

3. **Access Keycloak:**
    - Keycloak will be accessible at `http://localhost:8090`.
    - The default admin credentials are:
        - **Username**: `admin`
        - **Password**: `password`
    - You can log in with these credentials and proceed to configure realms, clients, and users as needed.

## Apache Pulsar Setup

1. **Configuration of Pulsar Broker:**
    - Navigate to the `pulsar_installation` folder.
    - Open the `customer-broker.conf` file and make the following changes:

      ```properties
      authenticationEnabled=true
      authenticationProviders=org.apache.pulsar.broker.authentication.AuthenticationProviderToken
      authenticationRefreshCheckSeconds=60
      authorizationEnabled=false
      authorizationProvider=
      authorizationAllowWildcardsMatching=false
      superUserRoles=
      brokerClientAuthenticationPlugin=org.apache.pulsar.client.impl.auth.oauth2.AuthenticationOAuth2
      brokerClientAuthenticationParameters={"issuerUrl": "http://{your-keycloak-url}/realms/{your-realm-name}", "privateKey": "file:///pulsar/oauth2.json", "audience": "{your-audience}"}
      tokenPublicKey=file:///pulsar/conf/public_key.der
      ```

    - Replace the placeholders `{your-keycloak-url}`, `{your-realm-name}`, and `{your-audience}` with your specific
      Keycloak configuration details.

2. **Configure OAuth2 for Pulsar:**
    - Open the `oauth2.json` file in the `pulsar_installation` folder.
    - Replace the content with your Keycloak details:

      ```json
      {
        "client_id": "{your-keycloak-client-id}",
        "client_secret": "{your-keycloak-client-secret}",
        "type": "client_credentials",
        "issuer_url": "http://{your-keycloak-url}/realms/{your-keycloak-realm}"
      }
      ```

    - Ensure that the `{your-keycloak-client-id}`, `{your-keycloak-client-secret}`, `{your-keycloak-url}`, and
      `{your-keycloak-realm}` fields are accurately filled in.

3. **Set Up Public Key for Authentication:**
    - Update the `public_key.pem` file by pasting your Keycloak public key between the `-----BEGIN PUBLIC KEY-----` and
      `-----END PUBLIC KEY-----` lines.
    - Convert this `.pem` file to `.der` format required by Pulsar:

      ```bash
      openssl x509 -outform der -in public_key.pem -out public_key.der
      ```

4. **Start Pulsar with Docker Compose:**
    - Run the following command to start Pulsar:

      ```bash
      docker-compose up -d
      ```

## Spring Boot Application Setup

1. **Configure OAuth2 Credentials:**
    - Navigate to the `src/main/java/demo` folder.
    - Open the `credentials.json` file and input your Keycloak client details:

      ```json
      {
        "client_id": "{your-client-id}",
        "client_secret": "{your-client-secret}"
      }
      ```

    - Replace `{your-client-id}` and `{your-client-secret}` with your actual credentials.

2. **Application Properties Configuration:**
    - Open the `application.properties` file and update the following settings:

      ```properties
      server.port=8081
      pulsar.issuer-url={your-keycloak-url}
      pulsar.credentials-url={your-credentials.json-file-path}
      pulsar.audience={your-audience}
      spring.pulsar.client.service-url={your-pulsar-url}
      ```

    - Replace the placeholders `{your-keycloak-url}`, `{your-credentials.json-file-path}`, `{your-audience}`, and
      `{your-pulsar-url}` with your specific configuration details.

## Obtaining Keycloak Configuration Details

To correctly configure your application, you need to obtain several Keycloak-specific details:

1. **Issuer URL:**
    - The issuer URL is the base URL of your Keycloak realm. It typically follows this pattern:
      ```
      http://{your-keycloak-url}/realms/{your-realm-name}
      ```
    - To find it, log in to the Keycloak admin console, navigate to your realm, and look for the "Endpoints" tab. The
      issuer URL is listed there.

2. **Public Key:**
    - The public key is used to verify the tokens issued by Keycloak.
    - To get the public key, go to the Keycloak admin console, navigate to your realm settings, and find the "Keys" tab.
      Here, you can view and download the public key in PEM format.

3. **Client ID and Client Secret:**
    - The client ID and secret are necessary for OAuth2 authentication.
    - To obtain these, navigate to the "Clients" section in your Keycloak realm, select your client, and go to the "
      Credentials" tab. The client ID is listed under the "Settings" tab, and the secret can be found in the "
      Credentials" tab.

## Running the Application

1. **Build and Run the Spring Boot Application:**
    - Use Maven to build and run the application:

      ```bash
      mvn spring-boot:run
      ```

2. **Access the Application:**
    - The application will be running on `http://localhost:8081`.

## Sample Endpoints

- **Send Message to Pulsar:**
    - `POST /pulsar/send`

  You can interact with the application using the above endpoint to send messages to Pulsar.

## Conclusion

This project provides a secure and scalable foundation for integrating Apache Pulsar with Keycloak using Spring Boot. By
following the steps outlined above, you will have a fully functioning system ready for further development or
deployment.

Feel free to customize this project to fit your specific needs, and don’t hesitate to contribute to the repository or
raise issues if you encounter any problems.
