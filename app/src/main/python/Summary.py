from newspaper import Article

 def generate_summary(url):    # Creating a newspaper Article object
    article = Article(url)

     Downloading and parsing the article
     article.download()
     article.parse()

     # Printing the extracted data
    print("Title: ", article.title)
     print("Authors: ", article.authors)
     print("Publish Date: ", article.publish_date)
    print("Article Text: ", article.text)     # Return the summary
   return article.text

    # Generate and print the summary
    summary = generate_summary(url)
   print("extracted: ", summary)
