
# News-Brief

**Your daily news — summarized instantly**



## 📌 Overview

**News-Brief** is an Android application that fetches real-time news and instantly produces concise summaries using a TF-based extractive NLP approach.
It solves a modern reality: people want to stay informed, but don’t have time to read long articles.

The app fetches live news via API, categorizes content, allows full-article reading, and generates summaries on a single tap — making it a fast, clean alternative to traditional news reading.

---

## ✨ Key Features

* **Real-time News Fetching** using News API
* **AI-Generated Summaries** via a TF-based extractive NLP technique
* **Category-Based News Browsing** (General, Sports, Business, Science, etc.)
* **Search Functionality** to find any news immediately
* **One-Tap Summarization**
* **Full-Article View** inside a built-in WebView
* **Clean, intuitive UI** with smooth navigation
* **Secure Login & Signup** flow

---

## 🧭 How It Works

1. **User opens the app** – The system fetches fresh news from the API.
2. **User selects an article** – Tapping a news card opens the summarization screen.
3. **Summary window appears** – A concise TF-based summary is instantly generated.
4. **Optional full-article mode** – Users can switch to the full content anytime.
5. **NLP Summary Generation** –

   * Tokenization
   * Word frequency calculation
   * Sentence scoring
   * Top-sentence extraction based on summary length
     (Full process shown below.)

📸 **Summarization flow illustration:**
![Summarization Process](https://github.com/ashfaq099/News-Brief/blob/master/Assets/Summarization.png)

---

## 🔍 Methodology

### **1. News Fetching Workflow**

* Obtain API key
* Choose endpoint
* Make HTTP request via `NewsApiClient`
* Parse JSON data
* Display headlines & articles
* Implement search + category filter

### **2. TF-Based Summarization Workflow**

* Tokenize text
* Compute normalized word frequencies
* Tokenize into sentences
* Score sentences based on TF sum
* Select top % sentences
* Generate final summary

---
## 🧩 Use-Case Diagram

![Use Case Diagram](https://github.com/ashfaq099/News-Brief/blob/master/Assets/Use%20case%20Diagram.png)

## 📱 App Screenshots

### Authentication & Onboarding

![Auth Screens](https://github.com/ashfaq099/News-Brief/blob/master/Assets/SS1.png)

### Main Screens & Features

![Main App Flow](https://github.com/ashfaq099/News-Brief/blob/master/Assets/ss2.png)
![Main App Flow](https://github.com/ashfaq099/News-Brief/blob/master/Assets/ss3.png)


---

## 🧩 System Architecture

```
News-Brief/
│
├── app/                                  # Android Java code
│   ├── activities/                        # UI screens
│   ├── adapters/                          # RecyclerView & models
│   ├── python/                            # Python TF summarizer via Chaquopy
│   └── build.gradle
│
├── news_summarization.py                  # Python summarization script (TF-based)
├── News_Summarization.ipynb               # NLP development notebook
│
└── README.md
```

---

## 🛠 Tech Stack

### **Android**

* Java
* AndroidX
* RecyclerView + WebView
* Chaquopy (Python interpreter)
* News API client

### **Python**

* NLTK
* Tokenizers
* TF-based summarizer logic

---

## 🚀 Getting Started

### Clone the repository

```bash
git clone https://github.com/ashfaq099/News-Brief.git
cd News-Brief
```

### Run the notebook (optional)

```bash
jupyter notebook News_Summarization.ipynb
```

### Open in Android Studio

* Sync Gradle
* Ensure Chaquopy is installed
* Run on a device/emulator

---


## 📄 License

This project is licensed under the **MIT License** .

The MIT License allows you to freely use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the software, provided that the original copyright notice and this permission notice are included in all copies or substantial portions of the software.


## 🙏 Credits

* News API — [https://newsapi.org](https://newsapi.org)
* Chaquopy — Python in Android
* Research reference:
  Christian et al. *“Automatic Text Summarization using TF–IDF”*, ComTech, 2016.


