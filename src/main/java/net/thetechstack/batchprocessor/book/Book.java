package net.thetechstack.batchprocessor.book;

import java.math.BigDecimal;

public class Book {
    private Integer bookId;
    private Integer goodReadsBookId;
    private Integer bestBookId;
    private Integer workId;
    private Integer booksCount;
    private String isbn;
    private String isbn13;
    private String authors;
    private Integer originalPublicationYear;
    private String originalTitle;
    private String title;
    private String languageCode;
    private BigDecimal averageRating;
    private Integer ratingsCount;
    private Integer workRatingsCount;
    private Integer workTextReviewCount;
    private Integer ratings1;
    private Integer ratings2;
    private Integer ratings3;
    private Integer ratings4;
    private Integer ratings5;
    private String imageUrl;
    private String smallImageUrl;


    @Override
    public String toString() {
        return "Book [bookId=" + bookId + "]";
    }
    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public Integer getGoodReadsBookId() {
        return goodReadsBookId;
    }
    public void setGoodReadsBookId(Integer goodReadsBookId) {
        this.goodReadsBookId = goodReadsBookId;
    }
    public Integer getBestBookId() {
        return bestBookId;
    }
    public void setBestBookId(Integer bestBookId) {
        this.bestBookId = bestBookId;
    }
    public Integer getWorkId() {
        return workId;
    }
    public void setWorkId(Integer workId) {
        this.workId = workId;
    }
    public Integer getBooksCount() {
        return booksCount;
    }
    public void setBooksCount(Integer booksCount) {
        this.booksCount = booksCount;
    }
    public Integer getIsbn() {
        return isbn;
    }
    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }
    public String getIsbn13() {
        return isbn13;
    }
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }
    public String getAuthors() {
        return authors;
    }
    public void setAuthors(String authors) {
        this.authors = authors;
    }
    public Integer getOriginalPublicationYear() {
        return originalPublicationYear;
    }
    public void setOriginalPublicationYear(Integer originalPublicationYear) {
        this.originalPublicationYear = originalPublicationYear;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLanguageCode() {
        return languageCode;
    }
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
    public BigDecimal getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }
    public Integer getRatingsCount() {
        return ratingsCount;
    }
    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }
    public Integer getWorkRatingsCount() {
        return workRatingsCount;
    }
    public void setWorkRatingsCount(Integer workRatingsCount) {
        this.workRatingsCount = workRatingsCount;
    }
    public Integer getWorkTextReviewCount() {
        return workTextReviewCount;
    }
    public void setWorkTextReviewCount(Integer workTextReviewCount) {
        this.workTextReviewCount = workTextReviewCount;
    }
    public Integer getRatings1() {
        return ratings1;
    }
    public void setRatings1(Integer ratings1) {
        this.ratings1 = ratings1;
    }
    public Integer getRatings2() {
        return ratings2;
    }
    public void setRatings2(Integer ratings2) {
        this.ratings2 = ratings2;
    }
    public Integer getRatings3() {
        return ratings3;
    }
    public void setRatings3(Integer ratings3) {
        this.ratings3 = ratings3;
    }
    public Integer getRatings4() {
        return ratings4;
    }
    public void setRatings4(Integer ratings4) {
        this.ratings4 = ratings4;
    }
    public Integer getRatings5() {
        return ratings5;
    }
    public void setRatings5(Integer ratings5) {
        this.ratings5 = ratings5;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getSmallImageUrl() {
        return smallImageUrl;
    }
    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    
}
