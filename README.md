# WebSocket Demo with AES Encryption

This project demonstrates a secure WebSocket communication system using Spring Boot for the backend and vanilla JavaScript for the frontend. It features AES encryption for parameter passing and real-time bidirectional communication.

## Features

- WebSocket communication between client and server
- AES encryption for secure parameter passing
- Real-time messaging capabilities
- Robust error handling and logging

## Technology Stack

- Backend:
  - Spring Boot
  - Java WebSocket API
  - AES Encryption (ECB mode with PKCS5Padding)
- Frontend:
  - HTML5
  - Vanilla JavaScript
  - CryptoJS for AES encryption

## Key Components

1. `WebSocketServer`: Handles WebSocket connections, decrypts incoming messages, and manages sessions.
2. `CipherUtils`: Provides AES encryption and decryption functionalities.
3. Frontend HTML/JS: Implements the client-side WebSocket connection and encryption logic.

## How It Works

1. The client encrypts connection parameters using AES.
2. Encrypted parameters are sent as part of the WebSocket URL.
3. The server decrypts the parameters upon connection establishment.
4. Bidirectional communication is then enabled through the WebSocket.

## Setup and Running

1. Clone the repository:
   ```
   git clone [repository-url]
   ```

2. Navigate to the project directory and build the Spring Boot application:
   ```
   ./mvnw clean install
   ```

3. Run the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

4. Open the `index.html` file in a web browser to access the frontend.

## Usage

1. Enter the WebSocket URL (e.g., `ws://localhost:8080/ws`) in the frontend.
2. Provide parameters to be encrypted and sent with the connection.
3. Click "Connect" to establish a WebSocket connection.
4. Use the message input to send messages to the server.

## Security Considerations

- This demo uses AES/ECB/PKCS5Padding, which may not be suitable for all security requirements. Consider using a more secure mode like CBC or GCM for production.
- The encryption key is hardcoded for demonstration purposes. In a real-world scenario, implement proper key management.
- Always use HTTPS in production to secure the initial connection.

## Contributing

Contributions, issues, and feature requests are welcome. Feel free to check [issues page] if you want to contribute.

## License

[Specify your license here]

---

This project is intended for educational purposes and as a starting point for implementing secure WebSocket communications. Always review and adjust security measures for production use.
