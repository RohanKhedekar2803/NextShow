# 🎟️ NextShow

**NextShow** is a microservices-based event booking platform designed for hosting and managing events such as comic shows, sports events, and more. It allows users to seamlessly browse upcoming events, check real-time seat availability, book tickets, and make secure payments — all within a scalable, distributed architecture.

[![Demo Video](https://img.shields.io/badge/-Watch%20Demo-red?style=for-the-badge&logo=youtube&logoColor=white)](https://youtu.be/VFzqAiExRNc)

## Built With

[![Spring Boot](https://img.shields.io/badge/-springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/-java-007396?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Hibernate](https://img.shields.io/badge/-hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://hibernate.org/)
[![MySQL](https://img.shields.io/badge/-mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Kafka](https://img.shields.io/badge/-kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Redis](https://img.shields.io/badge/-redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io/)
[![Docker](https://img.shields.io/badge/-docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Stripe](https://img.shields.io/badge/-stripe-635BFF?style=for-the-badge&logo=stripe&logoColor=white)](https://stripe.com/)
[![OAuth2](https://img.shields.io/badge/-OAuth2-4285F4?style=for-the-badge&logo=google&logoColor=white)](https://developers.google.com/identity/protocols/oauth2)
[![JWT](https://img.shields.io/badge/-JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Maven](https://img.shields.io/badge/-maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

---

## 📖 Overview

NextShow is built as a collection of independently deployable microservices — each responsible for a specific domain (events, shows, bookings, users, notifications, payments). Services communicate synchronously via REST (Feign clients) for direct dependencies, and asynchronously via Kafka for decoupled, event-driven workflows like booking confirmations, ticket generation, and notifications.

## 🎥 Demo

Watch the full walkthrough here: **[NextShow Demo Video](https://youtu.be/VFzqAiExRNc)**

## 🔧 Key Features

### 🔐 Federated Authentication
- Google OAuth 2.0 integration for user login
- Custom JWT tokens issued for secure, stateless internal authorization across services

### 🧩 Microservices Architecture
- Independent services for **Events**, **Shows**, **Bookings**, **Users**, **Notifications**, and **Payments**
- Enables modular development, independent scaling, and isolated deployments

### 🔄 Service Communication
- **Synchronous:** Feign clients for REST-based inter-service calls
- **Asynchronous:** Kafka producers/consumers for cross-service event flows (Booking → Notification → Payment), ensuring eventual consistency and fault tolerance

### 📡 Event-Driven Messaging
- Kafka topics decouple major workflows: booking confirmation, ticket generation, and user notifications
- Custom Kafka consumers/producers across multiple services for real-time updates and reliability

### ⚡ Performance Optimization
- Redis caching layer for frequently accessed data (e.g., real-time seat availability)

### 💳 Secure Payment Integration
- Stripe integration for payment processing
- Callback handling and status updates propagated via Kafka events

### 📦 Containerized Deployment
- All services Dockerized
- Images published as GitHub Packages for seamless environment setup and CI/CD pipelines

## 🏗️ Architecture

```
                                   ┌───────────────────────┐
                                   │   Google OAuth 2.0    │
                                   │   (Login Provider)    │
                                   └───────────┬───────────┘
                                               │ OAuth login
                                               ▼
                                   ┌───────────────────────┐
                        Client ───►│      API Gateway      │
                                   │   (JWT Validation)    │
                                   └───────────┬───────────┘
                                               │
        ┌───────────────┬────────────────┬─────┴───────────┬───────────────┐
        ▼               ▼                ▼                 ▼               ▼
 ┌─────────────┐ ┌─────────────┐ ┌──────────────┐ ┌─────────────┐ ┌────────────────┐
 │   Events    │ │    Shows    │ │   Bookings   │ │    Users    │ │    Payments    │
 │   Service   │ │   Service   │ │   Service    │ │   Service   │ │    Service     │
 └──────┬──────┘ └──────┬──────┘ └───────┬──────┘ └──────┬──────┘ └────────┬───────┘
        │               │                │               │                 │
        ▼               ▼                ▼               ▼                 │
 ┌─────────────┐ ┌─────────────┐ ┌───────────────┐ ┌─────────────┐         │
 │   MySQL     │ │   MySQL     │ │    MySQL      │ │   MySQL     │         │
 │ (events_db) │ │ (shows_db)  │ │ (bookings_db) │ │ (users_db)  │         │
 └─────────────┘ └─────────────┘ └───────┬───────┘ └─────────────┘         │
                                          │                                │
                                          │ Feign (sync REST calls)        │
                                          │◄───────────────────────────────┘
                                          │
                              seat availability lookup
                                          │
                                          ▼
                                 ┌───────────────────────┐
                                 │         Redis         │
                                 │  (seat-availability   │
                                 │      cache)           │
                                 └───────────────────────┘

               Bookings, Users & Payments Services also publish/consume via:
                                          │
                                          ▼
                                 ┌──────────────────────┐
                                 │   Apache Kafka       │
                                 │   (Event Bus)        │
                                 │  topics: booking-    │
                                 │  confirmed, ticket-  │
                                 │  generated, payment- │
                                 │  status, notify-user │
                                 └────────────┬─────────┘
                                           │
                    ┌──────────────────────┼───────────────────────┐
                    ▼                      ▼                       ▼
          ┌──────────────────┐   ┌──────────────────┐   ┌─────────────────────┐
          │   Notification   │   │   Payments       │   │   Bookings          │
          │   Service        │   │   Service        │   │   Service (consumer)│
          │ (email/SMS push) │   │ (updates status) │   │ (ticket generation) │
          └──────────────────┘   └─────────┬────────┘   └─────────────────────┘
                                              │
                                              ▼
                                    ┌────────────────────┐
                                    │      Stripe        │
                                    │   (Payment API)    │
                                    └─────────┬──────────┘
                                              │ webhook callback
                                              │ (payment success/
                                              │  failure event)
                                              ▼
                                    ┌──────────────────────┐
                                    │  Payments Service    │
                                    │  Webhook Endpoint    │
                                    │ → publishes status   │
                                    │   to Kafka topic     │
                                    └──────────────────────┘
```

**Flow summary:**
1. **Auth:** User logs in via Google OAuth 2.0 → API Gateway issues/validates a custom JWT for all downstream requests.
2. **Sync calls:** Services that need direct data from one another (e.g., Bookings checking Events/Shows details) use **Feign clients** over REST.
3. **Caching:** The Bookings Service checks **Redis** first for real-time seat availability before hitting MySQL, reducing DB load.
4. **Async events:** Booking confirmations, ticket generation, and notifications are decoupled through **Kafka topics** — each service publishes/consumes independently.
5. **Payments:** The Payments Service calls **Stripe** to process a transaction; Stripe sends a **webhook callback** back to a dedicated endpoint on the Payments Service, which then publishes a `payment-status` event to Kafka so Bookings/Notification services can react.
6. **Data storage:** Each core service (Events, Shows, Bookings, Users) owns its own **MySQL database** — following the database-per-service pattern for microservices.

## 🛠️ Tech Stack

| Layer | Technologies |
|---|---|
| **Backend** | Spring Boot, Java, Hibernate, MySQL |
| **Security** | Google OAuth 2.0, JWT |
| **Messaging** | Apache Kafka, Redis |
| **Payments** | Stripe |
| **DevOps** | Docker, Maven, GitHub Packages |
| **Coming Soon** | Helm, Terraform, Jenkins (CI/CD & IaC) |

## 🚀 Roadmap

- [ ] CI/CD pipeline with Jenkins
- [ ] Infrastructure as Code with Terraform
- [ ] Kubernetes deployment via Helm charts

## 🙌 Acknowledgements

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Stripe API Docs](https://stripe.com/docs/api)
