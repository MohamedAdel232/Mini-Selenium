# MiniSelenium - Custom WebDriver Client

This project demonstrates a **minimal custom implementation** of a WebDriver client (like Selenium) using raw **HTTP requests** to communicate with **ChromeDriver** through the **W3C WebDriver protocol**.

It does **not** use Selenium libraries — instead, it builds all functionality from scratch using `HttpURLConnection` and JSON payloads.

---

## 🔧 What It Does

This project automates a simple login flow for the demo site [saucedemo.com](https://www.saucedemo.com):

1. Launches Chrome via ChromeDriver.
2. Navigates to the login page.
3. Finds the username, password, and login button elements.
4. Types credentials and clicks login.
5. Closes the browser session.

All actions are performed using raw HTTP calls to ChromeDriver following the W3C WebDriver standard.

---

## 📂 Project Structure

```
MiniSelenium/
├── MiniWebDriver.java   # Core class handling HTTP-based browser automation
├── Main.java            # Runs a basic login test using MiniWebDriver
```

---

## 🛠️ How It Works Internally

Each browser interaction is mapped to a WebDriver HTTP endpoint defined by the [W3C WebDriver spec](https://www.w3.org/TR/webdriver/):

| Action         | HTTP Method | Endpoint                              | Payload Example                           |
|----------------|-------------|----------------------------------------|--------------------------------------------|
| Start session  | POST        | `/session`                             | `{ "capabilities": { "alwaysMatch": { "browserName": "chrome" } } }` |
| Navigate       | POST        | `/session/{sessionId}/url`             | `{ "url": "https://example.com" }`         |
| Find element   | POST        | `/session/{sessionId}/element`         | `{ "using": "css selector", "value": "#id" }` |
| Send keys      | POST        | `/session/{sessionId}/element/{id}/value` | `{ "text": "abc", "value": ["a","b","c"] }` |
| Click element  | POST        | `/session/{sessionId}/element/{id}/click` | `{}` |
| Get text       | GET         | `/session/{sessionId}/element/{id}/text` | – |
| Quit session   | DELETE      | `/session/{sessionId}`                 | – |

All these endpoints are implemented manually in the `MiniWebDriver` class using `HttpURLConnection`.

---

## 🚀 How to Run

### 1. Prerequisites

- Java 11+
- Chrome browser installed
- [ChromeDriver](https://googlechromelabs.github.io/chrome-for-testing/) downloaded and placed in your system path

> 🔹 Ensure the version of ChromeDriver matches your installed Chrome version

---

### 2. Start ChromeDriver Server

Start the ChromeDriver HTTP server manually in a terminal:

```bash
chromedriver --port=64312
```

Make sure you see:

```
Starting ChromeDriver... on port 64312
```

This project expects ChromeDriver to run locally at `http://localhost:64312`

---

### 3. Compile & Run

Compile and run the Java project:

```bash
javac -cp . MiniSelenium/*.java
java -cp . MiniSelenium.Main
```

> This will open Chrome, navigate to [https://www.saucedemo.com](https://www.saucedemo.com), enter login credentials, click login, and then close the browser.

---

## ✅ What You Will Learn

This project teaches you:

- How Selenium communicates with ChromeDriver under the hood
- How to construct and send W3C WebDriver HTTP requests manually
- How browser automation really works (step-by-step)
- How to create your own WebDriver mini-client

---

## ⚠️ Notes & Limitations

- You must use correct locator strategies like `"css selector"`, `"id"`, etc.
- ChromeDriver must be running before executing your Java code.
- No parallel sessions or advanced error handling are implemented.
- This is a minimal educational tool — not a replacement for full Selenium.

---

## 📘 Resources

- [W3C WebDriver Spec](https://www.w3.org/TR/webdriver/)
- [ChromeDriver](https://chromedriver.chromium.org/)
- [SauceDemo Test Site](https://www.saucedemo.com/)

---
