package com.sapient.bookstore.domain;
/**
 * Model
 * @author Pankaj Sharma
 * @since 1.0
 */
public class BookSearchOption {
    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    private String searchBy;

    public String getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    private String searchKeywords;

    public enum SearchBy{
        TITLE("title"),ISBN("isbn"),PRICE("price"),AUTHOR("author");

        private String value;

        SearchBy(String value)
        {
            this.value=value;
        }

        public String getValue()
        {
            return value;
        }
    }
}
