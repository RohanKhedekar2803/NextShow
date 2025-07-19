NextShow
NextShow is a microservices-based event booking platform designed for hosting and managing events such as comic shows, sports events, and more. The system allows users to seamlessly browse upcoming events, check real-time seat availability, book tickets, and make secure payments — all within a scalable and distributed architecture.

demo - https://youtu.be/VFzqAiExRNc
🔧 Key Features
Federated Authentication: Integrated Google OAuth 2.0 for user login, with custom JWT tokens issued for secure and stateless internal authorization across services.

Microservices Architecture: Independent services for events, shows, bookings, users, notifications, and payments, enabling modular development and scalability.

Service Communication:

Synchronous communication with Feign clients for REST-based interactions between dependent services.

Asynchronous communication using Kafka to publish and consume events across services such as Booking, Notification, and Payment, ensuring eventual consistency and resilience to failure.

Event-Driven Messaging:

Kafka topics used for decoupling major workflows like booking confirmation, ticket generation, and sending user notifications.

Custom Kafka consumers and producers implemented in multiple services to enable real-time updates and reliability.

Performance Optimization: Redis used as a caching layer to improve response times for frequently accessed data such as seat availability.

Secure Payment Integration: Stripe integration for handling payments, with callback handling and status updates via Kafka events.

Containerized Deployment: All services Dockerized and published as GitHub Packages to support seamless environment setup and deployment pipelines.

🛠️ Tech Stack
Backend: Spring Boot, Java, Hibernate, MySQL

Security: Google OAuth 2.0, JWT

Messaging: Apache Kafka, Redis

DevOps: Docker, Maven

(CI/CD and IaC integration with Helm, Terraform, Jenkins coming soon)
