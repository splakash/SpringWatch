# 🚀 SpringWatch

SpringWatch is a lightweight observability experiment built to deeply understand how logging works inside Spring Boot --- beyond just writing
`log.error()`.

The goal is simple:

> Capture application errors, store them in a database, and build the foundation for real-time monitoring and alerting --- without relying on heavy external tools like ELK or Sentry.

Currently, SpringWatch focuses on capturing ERROR logs and persisting
them asynchronously using a clean, event-driven architecture.
------------------------------------------------------------------------
## 🤔 Why This Project Exists
In most Spring Boot applications:
-   Logs go to console
-   Sometimes they go to a file
-   There is no centralized visibility

If something crashes in production, you manually inspect logs.

SpringWatch was built to: - Understand SLF4J and Logback internals -
Build a custom Logback appender - Safely bridge Logback with Spring -
Persist errors asynchronously - Lay the foundation for alerting &
monitoring

------------------------------------------------------------------------

## 🧠 How It Works

When this is executed:

``` java
log.error("Payment failed", ex);
```

The internal flow becomes:

    Application Code
       ↓
    SLF4J
       ↓
    Logback
       ↓
    DatabaseLogAppender (SpringWatch)
       ↓
    Publish Spring Event
       ↓
    Async Event Listener
       ↓
    Save to Database

Logs still print to console normally, but ERROR logs are also captured
and stored.

------------------------------------------------------------------------

## 🏗 Architecture Design

SpringWatch separates responsibilities clearly:

  Layer              Responsibility
  ------------------ ---------------------------
  Logback Appender   Captures ERROR logs
  Spring Event       Transfers log data
  Event Listener     Processes & persists logs
  Repository         Saves logs to DB

Instead of saving directly inside the appender (which would block
logging threads), SpringWatch:

1.  Captures the log event.
2.  Publishes a Spring event.
3.  Handles persistence asynchronously.

This ensures logging remains fast and safe.

------------------------------------------------------------------------

## 📦 Main Components

### 1️⃣ DatabaseLogAppender

-   Extends `AppenderBase<ILoggingEvent>`
-   Filters ERROR level logs
-   Extracts:
    -   Level
    -   Logger name
    -   Message
    -   Stack trace
    -   Timestamp
-   Publishes a Spring event

------------------------------------------------------------------------

### 2️⃣ SpringContextHolder

Because Logback creates the appender (not Spring), dependency injection
does not work directly.

SpringContextHolder: - Stores a static reference to ApplicationContext -
Allows appender to publish Spring events safely

------------------------------------------------------------------------

### 3️⃣ LogCapturedEvent

Immutable event object representing a captured log.

Why immutable? - Thread-safe - Safe for async processing - Represents a
fact that already happened

------------------------------------------------------------------------

### 4️⃣ LogEventListener

-   Uses `@EventListener`
-   Runs asynchronously
-   Saves log data to database

------------------------------------------------------------------------

## 📂 Project Structure

    springwatch/
     ├── logging/
     │    ├── DatabaseLogAppender.java
     │    └── SpringContextHolder.java
     │
     ├── events/
     │    └── LogCapturedEvent.java
     │
     ├── listener/
     │    └── LogEventListener.java
     │
     ├── entity/
     │    └── LogEvent.java
     │
     ├── repository/
     │    └── LogEventRepository.java
     │
     └── resources/
          └── logback-spring.xml

------------------------------------------------------------------------

## ⚙️ Logging Configuration

`logback-spring.xml` registers the custom appender:

``` xml
<configuration>

    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>

    <appender name="SPRINGWATCH"
              class="com.springwatch.logging.DatabaseLogAppender"/>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="SPRINGWATCH"/>
    </root>

</configuration>
```

Result: - Console logging continues normally - ERROR logs are persisted
to DB

------------------------------------------------------------------------

## 🛡 Design Decisions

Why not save directly inside the appender?

-   Logback initializes before Spring context
-   Dependency injection would fail
-   DB operations would block request threads
-   Logging failures should never crash business logic

Using events and async listeners keeps the system clean and scalable.

------------------------------------------------------------------------

## 🚀 Current Status

✔ Custom Logback integration\
✔ ERROR-level interception\
✔ Async DB persistence\
✔ Clean separation of concerns

------------------------------------------------------------------------

## 🔜 Next Steps

-   Capture full stack trace formatting
-   Add Email/Slack notifications
-   Add REST APIs to view logs
-   Add real-time WebSocket dashboard
-   Convert into a Spring Boot Starter

------------------------------------------------------------------------

## 🎯 Learning Outcomes

This project demonstrates understanding of:

-   SLF4J vs Logback
-   Custom Logback appenders
-   Spring ApplicationContext lifecycle
-   Event-driven architecture
-   Async processing
-   Infrastructure-level design

------------------------------------------------------------------------

SpringWatch is still evolving, but this is the foundation of a
lightweight Spring-native observability platform.
