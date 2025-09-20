# Multi-Threaded TCP Server in Java

## 📌 Overview
This project implements a **multi-threaded TCP server** in Java that can handle multiple clients concurrently.  
It simulates real-world client-server communication similar to how production servers (like Apache, Nginx) handle multiple connections.

Key features:
- Concurrent client handling using **thread pools** (`ExecutorService`).
- **Client simulator** to test server load with 50+ parallel clients.
- Support for simple commands:  
  - `STORE <id> <value>` → Stores a key-value pair.  
  - `GET <id>` → Retrieves stored values.  
  - `EXIT` → Disconnects the client.  
- **Thread-safe in-memory datastore** using `ConcurrentHashMap`.  
- **Centralized logging** to file (`server.log`) via `LoggerUtil`.  

---

## 🛠️ Tech Stack
- **Java (JDK 17+)**
- **Sockets (TCP/IP)**
- **Concurrency** (`ExecutorService`, `Runnable`)
- **ConcurrentHashMap** for shared storage

---

## 📂 Project Structure
.
├── Client.java # Interactive client (manual commands)
├── Client1.java # Alternate test client
├── ClientSimulator.java # Simulates multiple concurrent clients
├── ClientHandler.java # Handles individual client requests
├── LoggerUtil.java # Logging utility (writes to server.log)
├── RealClient.java # Extended client implementation
├── Server.java # TCP Server entry point




---

## ▶️ How to Run

1. **Start the server**
   ```bash
   javac Server.java
   java webs.Server
Output:



Server Started on port 5001
Run a single client


javac Client.java
java webs.Client
Example interaction:



Connected to Server. Type your Command (GET <id>, STORE <id> <value>, EXIT):
STORE id1 value1
STORED [id1]: value1
GET id1
Result for id1 : value1
EXIT
See YoU AgaIn
Simulate multiple clients



javac ClientSimulator.java
java webs.ClientSimulator
Output:


[Client-1] Connected to server
[Client-1] Server says: Connected to Server...
[Client-1] Completed in 25 ms
...


📊 Sample Log (server.log)

[2025-07-22 12:43:05] STORE key: id1, value: value1
[2025-07-22 12:43:08] GET key: id1, result: value1
[2025-07-22 12:43:12] Client /127.0.0.1 disconnected
