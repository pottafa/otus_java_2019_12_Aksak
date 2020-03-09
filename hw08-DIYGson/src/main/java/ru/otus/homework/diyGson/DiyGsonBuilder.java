package ru.otus.homework.diyGson;

 class DiyGsonBuilder {
   private String json = "";

     DiyGsonBuilder add(String value) {
        json += value;
        return this;
    }

     DiyGsonBuilder addWithQuotes(String value) {

        json += "\"" + value + "\"";
        return this;
    }

     DiyGsonBuilder add(char value) {
        json += value;
        return this;
    }

     String build() {
        return json;
    }

}
