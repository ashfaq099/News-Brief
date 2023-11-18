#  import spacy
#  import en_core_web_sm
#  from spacy.lang.en.stop_words import STOP_WORDS
#  from string import punctuation
#  from heapq import nlargest
#  from newspaper import Article
#
#  def generate_summary(url):
#      url1 = url
#      article = Article(url1)
#      article.download()
#      try:
#          article.parse()
#      except:
#          pass
#
#      rawtext = article.text
#      stopwords = list(STOP_WORDS)
#
#      nlp = en_core_web_sm.load()
#      doc = nlp(rawtext)
#
#      word_freq = {}
#      for word in doc:
#          if word.text.lower() not in stopwords and word.text.lower() not in punctuation:
#              if word.text not in word_freq.keys():
#                  word_freq[word.text] = 1
#              else:
#                  word_freq[word.text] += 1
#
#      try:
#          max_freq = max(word_freq.values())
#      except:
#          max_freq = 0
#
#      for word in word_freq.keys():
#          word_freq[word] = word_freq[word] / max_freq
#
#      sen_tokens = [sent for sent in doc.sents]
#
#      sent_scores = {}
#      for sent in sen_tokens:
#          for word in sent:
#              if word.text in word_freq.keys():
#                  if sent not in sent_scores.keys():
#                      sent_scores[sent] = word_freq[word.text]
#                  else:
#                      sent_scores[sent] += word_freq[word.text]
#
#      if len(rawtext.split()) > 1199:
#          select_len = int(len(sen_tokens) * 0.05)
#      elif 799 < len(rawtext.split()) < 1200:
#          select_len = int(len(sen_tokens) * 0.08)
#      elif 499 < len(rawtext.split()) < 800:
#          select_len = int(len(sen_tokens) * 0.15)
#      elif 199 < len(rawtext.split()) < 500:
#          select_len = int(len(sen_tokens) * 0.30)
#      elif len(rawtext.split()) < 200:
#          select_len = int(len(sen_tokens) * 0.50)
#      else:
#          select_len = int(len(sen_tokens) * 0.20)
#
#      summary = nlargest(select_len, sent_scores, key=sent_scores.get)
#      final_summary = [word.text for word in summary]
#      summary = ' '.join(final_summary)
#
#      return summary
#
#  result_summary = generate_summary(url)
#  print(result_summary)

import spacy
import en_core_web_sm
from spacy.lang.en.stop_words import STOP_WORDS
from string import punctuation
from heapq import nlargest
from newspaper import Article


def generate_summary(url):
    url1 = url
    article = Article(url1)
    article.download()

    try:
        article.parse()
    except:
        pass

    rawtext = article.text
    stopwords = list(STOP_WORDS)

    nlp = en_core_web_sm.load()
    doc = nlp(rawtext)

    word_freq = {}
    for word in doc:
        if word.text.lower() not in stopwords and word.text.lower() not in punctuation:
            if word.text not in word_freq.keys():
                word_freq[word.text] = 1
            else:
                word_freq[word.text] += 1

    try:
        max_freq = max(word_freq.values())
    except:
        max_freq = 0

    for word in word_freq.keys():
        word_freq[word] = word_freq[word] / max_freq

    sen_tokens = [sent for sent in doc.sents]

    sent_scores = {}
    for sent in sen_tokens:
        for word in sent:
            if word.text in word_freq.keys():
                if sent not in sent_scores.keys():
                    sent_scores[sent] = word_freq[word.text]
                else:
                    sent_scores[sent] += word_freq[word.text]

    if len(rawtext.split()) > 1199:
        select_len = int(len(sen_tokens) * 0.05)
    elif 799 < len(rawtext.split()) < 1200:
        select_len = int(len(sen_tokens) * 0.08)
    elif 499 < len(rawtext.split()) < 800:
        select_len = int(len(sen_tokens) * 0.15)
    elif 199 < len(rawtext.split()) < 500:
        select_len = int(len(sen_tokens) * 0.30)
    elif len(rawtext.split()) < 200:
        select_len = int(len(sen_tokens) * 0.50)
    else:
        select_len = int(len(sen_tokens) * 0.20)

    summary = nlargest(select_len, sent_scores, key=sent_scores.get)
    final_summary = [word.text for word in summary]
    summary = ' '.join(final_summary)

    return summary


result_summary = generate_summary(url)
print(result_summary)

